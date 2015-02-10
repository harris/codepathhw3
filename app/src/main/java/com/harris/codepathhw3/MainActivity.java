package com.harris.codepathhw3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

    imageType = "face";
    imageSize = "icon";
    colorFilter = "black";

    editText = (EditText) findViewById(R.id.editText);

    searchButton = (Button)findViewById(R.id.btn_search);
    searchButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        search();
      }
    });

    imagesGrid = (GridView) findViewById(R.id.images_grid);
    imageResults = new ArrayList<>();
    imageResultAdapter =
        new ImageResultAdapter<>(getApplicationContext(),  imageResults);
    imagesGrid.setAdapter(imageResultAdapter);
  }

  private void search() {
    AsyncHttpClient client = new AsyncHttpClient();
    client.get(populateUrl(), new JsonHttpResponseHandler(){
      @Override public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        try {
          JSONArray imageResultData = response.getJSONObject("responseData").getJSONArray("results");
          imageResultAdapter.clear();
          imageResultAdapter.addAll(ImageResult.fromJSONArray(imageResultData));
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });

  }

  private String populateUrl() {
    String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + editText.getText().toString();
    if (imageType != null) {
      url += "&imgtype=" + imageType;
    }

    if (imageSize!= null) {
      url += "&imgz=" + imageSize;
    }

    if (colorFilter!= null) {
      url += "&imgcolor=" + colorFilter;
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
