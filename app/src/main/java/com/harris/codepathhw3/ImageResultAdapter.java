package com.harris.codepathhw3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by harris on 2/10/15.
 */
public class ImageResultAdapter<T> extends ArrayAdapter<ImageResult>{

  private TextView description;
  private ImageView imageView;

  public ImageResultAdapter(Context context, List<ImageResult> objects) {
    super(context, android.R.layout.simple_list_item_1, objects);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ImageResult imageResult = getItem(position);
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.image_result_cell, parent, false);
    }

    description = (TextView) convertView.findViewById(R.id.description);
    imageView = (ImageView) convertView.findViewById(R.id.image_view);
    Picasso.with(getContext()).load(imageResult.getThumbUrl()).into(imageView);
    description.setText(imageResult.getTitle());
    return convertView;
  }
}
