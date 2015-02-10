package com.harris.codepathhw3;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by harris on 2/9/15.
 */
public class ImageResult {

  String fullUrl;
  String thumbUrl;
  String title;

  public ImageResult(JSONObject json) {
    try {
      this.fullUrl = json.getString("url");
      this.thumbUrl = json.getString("tbUrl");
      this.title = json.getString("title");
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public String getFullUrl() {
    return fullUrl;
  }

  public String getThumbUrl() {
    return thumbUrl;
  }

  public String getTitle() {
    return title;
  }

  public static List<ImageResult> fromJSONArray(JSONArray array) {
    ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    for (int i = 0; i < array.length(); i ++) {
      try {
        imageResults.add(new ImageResult(array.getJSONObject(i)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return imageResults;
  }
}
