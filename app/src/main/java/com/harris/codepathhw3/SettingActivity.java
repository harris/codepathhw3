package com.harris.codepathhw3;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    ActionBar actionBar = getActionBar();
    actionBar.setTitle("Google Image Searcher");
    actionBar.setIcon(R.mipmap.ic_launcher);
  }

}
