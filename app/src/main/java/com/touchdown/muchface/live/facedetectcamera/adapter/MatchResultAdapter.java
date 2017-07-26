package com.touchdown.muchface.live.facedetectcamera.adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touchdown.muchface.R;
import com.touchdown.muchface.domain.PersonDetails;
import java.util.ArrayList;
import java.util.List;

public class MatchResultAdapter
    extends RecyclerView.Adapter<MatchResultAdapter.MatchResultViewHolder> {

  private final List<Bitmap> bitmaps;
  private final List<PersonDetails> details;

  public MatchResultAdapter() {
    this.bitmaps = new ArrayList<>();
    this.details = new ArrayList<>();
  }

  public void add(Bitmap bitmap, PersonDetails details) {
    bitmaps.add(bitmap);
    this.details.add(details);
    notifyDataSetChanged();
  }

  public void add(Bitmap bitmap) {
    add(bitmap, PersonDetails.UNKNOWN);
  }

  @Override
  public MatchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_image_preview, parent, false);
    return new MatchResultViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(MatchResultViewHolder holder, int position) {
    holder.image.setImageBitmap(bitmaps.get(position));
    PersonDetails personDetails = details.get(position);
    holder.text.setText(personDetails.getName());
    holder.layout.setBackgroundColor(getBGColor(personDetails));
  }

  @Override
  public int getItemCount() {
    return bitmaps.size();
  }

  private int getBGColor(PersonDetails personDetails) {
    if (personDetails == PersonDetails.UNKNOWN) {
      return Color.RED;
    } else {
      return Color.GREEN;
    }
  }

  static class MatchResultViewHolder extends RecyclerView.ViewHolder {

    RelativeLayout layout;
    ImageView image;
    TextView text;

    MatchResultViewHolder(View itemView) {
      super(itemView);

      this.layout = (RelativeLayout) itemView.findViewById(R.id.match_result_layout);
      this.image = (ImageView) itemView.findViewById(R.id.match_result_image);
      this.text = (TextView) itemView.findViewById(R.id.match_result_label);
    }
  }
}