package com.touchdown.muchface.live.facedetectcamera.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.touchdown.muchface.MyApplication;
import com.touchdown.muchface.PersonDetailsActivity;
import com.touchdown.muchface.R;
import com.touchdown.muchface.domain.PersonDetails;
import java.util.List;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class MatchResultAdapter
    extends RecyclerView.Adapter<MatchResultAdapter.MatchResultViewHolder> {

  private final List<Bitmap> bitmaps;
  private final List<PersonDetails> details;
  private final List<String> names;
  private final Activity activity;
  private final KonfettiView mKonfettiView;

  public MatchResultAdapter(Activity activity, KonfettiView konfettiView) {
    this.activity = activity;
    mKonfettiView = konfettiView;
    MyApplication application = (MyApplication) activity.getApplication();
    this.bitmaps = application.getBitmaps();
    this.details = application.getDetails();
    this.names = application.getNames();
  }

  public void add(Bitmap bitmap, PersonDetails details) {
    String name = details.getName();
    if (names.contains(name) && details != PersonDetails.UNKNOWN) {
      updateExisting(bitmap, name);
    } else {
      addNew(bitmap, details, name);
      if (details != PersonDetails.UNKNOWN) {
        konfetti();
      }
    }
    notifyDataSetChanged();
  }

  private void konfetti() {
    mKonfettiView.build()
        .addColors(getColor(R.color.konfetti1), getColor(R.color.konfetti2),
            getColor(R.color.konfetti3), getColor(R.color.konfetti4), getColor(R.color.konfetti5))
        .setDirection(290, 340)
        .setSpeed(1f, 10f)
        .setFadeOutEnabled(true)
        .setTimeToLive(2000)
        .addShapes(Shape.RECT, Shape.CIRCLE)
        .addSizes(new Size(12, 5f))
        .setPosition(0, mKonfettiView.getHeight())
        .stream(300, 5000L);
  }

  private int getColor(int colorID) {
    return activity.getColor(colorID);
  }

  private void updateExisting(Bitmap bitmap, String name) {
    int i = this.names.indexOf(name);
    this.bitmaps.remove(i);
    this.bitmaps.add(i, bitmap);
  }

  private void addNew(Bitmap bitmap, PersonDetails details, String name) {
    if (details != PersonDetails.UNKNOWN) {
      this.bitmaps.add(0, bitmap);
      this.names.add(0, name);
      this.details.add(0, details);
    } else {
      this.bitmaps.add(bitmap);
      this.names.add(name);
      this.details.add(details);
    }
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
    final Bitmap bm = bitmaps.get(position);
    holder.image.setImageBitmap(bm);
    final PersonDetails personDetails = details.get(position);
    holder.text.setText(personDetails.getName());
    holder.layout.setBackgroundColor(getBGColor(personDetails));
    holder.layout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (personDetails != PersonDetails.UNKNOWN) {
          PersonDetailsActivity.startActivity(activity, personDetails, bm);
        }
      }
    });
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
