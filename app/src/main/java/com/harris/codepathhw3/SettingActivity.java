package com.harris.codepathhw3;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingActivity extends Activity {

  private Button submit;
  private Spinner imageType;
  private Spinner colorFilter;
  private Spinner imageSize;
  private EditText siteFilter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    ActionBar actionBar = getActionBar();
    actionBar.setTitle("Google Image Searcher");
    actionBar.setIcon(R.mipmap.ic_launcher);
    Bundle extras = getIntent().getExtras();
    submit = (Button) findViewById(R.id.btn_submit);

    imageType = (Spinner) findViewById(R.id.image_type_spinner);
    setDefaultSelection(imageType, extras.getString("image_type"));
    imageSize = (Spinner) findViewById(R.id.image_size_spinner);
    setDefaultSelection(imageSize, extras.getString("image_size"));
    colorFilter = (Spinner) findViewById(R.id.color_filter_spinner);
    setDefaultSelection(colorFilter, extras.getString("color_filter"));
    siteFilter = (EditText) findViewById(R.id.et_site_filter);

    String siteFilterFromIntent = extras.getString("site_filter");
    if (siteFilterFromIntent == null) {
      siteFilter.setText("google");
    } else {
      siteFilter.setText(siteFilterFromIntent);
    }

    submit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("image_type", imageType.getSelectedItem().toString());
        intent.putExtra("image_size", imageSize.getSelectedItem().toString());
        intent.putExtra("color_filter", colorFilter.getSelectedItem().toString());
        intent.putExtra("site_filter", siteFilter.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
      }
    });
  }

  private void setDefaultSelection(Spinner spinner, String defaultValue) {
    if (defaultValue == null) {
      return;
    }
    for (int i = 0; i < spinner.getCount(); i ++ ) {
      String option = (String) spinner.getItemAtPosition(i);
      if (option.equals(defaultValue)) {
        spinner.setSelection(i);
      }
    }
  }
}
