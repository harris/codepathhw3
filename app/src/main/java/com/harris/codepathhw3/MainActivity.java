package com.harris.codepathhw3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

  private static final int FETCH_SETTING = 67;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ActionBar actionBar = getActionBar();
    actionBar.setTitle("Google Image Searcher");
    actionBar.setIcon(R.mipmap.ic_launcher);
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
