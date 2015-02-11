package com.harris.codepathhw3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

  private static final int FETCH_SETTING = 67;
  private Button searchButton;
  private GridView imagesGrid;
  private EditText editText;
  private String imageType = null;
  private String imageSize= null;
  private String colorFilter= null;
  private List<ImageResult> imageResults;
  private ImageResultAdapter<ImageResult> imageResultAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar actionBar = getActionBar();
    actionBar.setTitle("Google Image Searcher");
    actionBar.setIcon(R.mipmap.ic_launcher);
    editText = (EditText) findViewById(R.id.editText);
    editText.setOnKeyListener(new View.OnKeyListener() {
      @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
          search(-1);
          return true;
        }
        return false;
      }
    });

    searchButton = (Button)findViewById(R.id.btn_search);
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        search(-1);
      }
    });

    imagesGrid = (GridView) findViewById(R.id.images_grid);
    imageResults = new ArrayList<>();
    imageResultAdapter =
        new ImageResultAdapter<>(this,  imageResults);
    imagesGrid.setAdapter(imageResultAdapter);
    imagesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MainActivity.this, ImageDisplayActivity.class);
        intent.putExtra("url", imageResults.get(position).getFullUrl());
        startActivity(intent);
      }
    });
    imagesGrid.setOnScrollListener(new EndlessScrollListener() {
      @Override public void onLoadMore(int page, int totalItemsCount) {
        customLoadMoreDataFromApi(totalItemsCount);
      }
    });
  }
  public void customLoadMoreDataFromApi(int offset) {
    search(offset);
  }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == FETCH_SETTING && resultCode == RESULT_OK) {
      imageType = data.getExtras().getString("image_type");
      imageSize = data.getExtras().getString("image_size");
      colorFilter = data.getExtras().getString("color_filter");
      if (editText.getText().length() > 0) {
        search(-1);
      }
    }
  }

  private void search(final int offset) {
    if (editText.getText().length() == 0) {
      Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show();
      return;
    }
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(populateUrl(offset), new JsonHttpResponseHandler(){
      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
          JSONArray imageResultData = response.getJSONObject("responseData").getJSONArray("results");
          if (offset == -1) {
            imageResultAdapter.clear();
          }
          imageResultAdapter.addAll(ImageResult.fromJSONArray(imageResultData));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });

  }

  private String populateUrl(int offset) {
    String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=" + editText.getText().toString();
    if (imageType != null) {
      url += "&imgtype=" + imageType;
    }

    if (imageSize!= null) {
      url += "&imgz=" + imageSize;
    }

    if (colorFilter!= null) {
      url += "&imgcolor=" + colorFilter;
    }
    if (offset != -1) {
      url += "&start=" + offset;
    }
    return url;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    MenuItem item = menu.getItem(0);
    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
      @Override public boolean onMenuItemClick(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        intent.putExtra("image_type", imageType == null ? "photo" : imageType);
        intent.putExtra("image_size", imageSize == null ? "icon": imageSize);
        intent.putExtra("color_filter", colorFilter == null ? "black" : colorFilter);
        startActivityForResult(intent, FETCH_SETTING);
        return true;
      }
    });
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
