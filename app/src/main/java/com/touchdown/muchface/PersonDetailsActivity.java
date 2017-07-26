package com.touchdown.muchface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.skyfishjy.library.RippleBackground;
import com.touchdown.muchface.domain.PersonDetails;
import java.text.SimpleDateFormat;

public class PersonDetailsActivity extends AppCompatActivity {

  public static final String DETAILS_EXTRA = "details_extra";
  private TextView mNameText;
  private ImageView mProfileImage;
  private ImageView mSourceImage;
  private TextView mSsnText;
  private TextView mBirthDayText;
  private TextView mDescriptionText;

  public static void startActivity(Activity fromActivity, PersonDetails details, Bitmap bitmap) {
    Intent intent = new Intent(fromActivity, PersonDetailsActivity.class);
    intent.putExtra(DETAILS_EXTRA, details);
    ((MyApplication) fromActivity.getApplication()).setBitmap(bitmap);
    //intent.putExtra(IMAGE_EXTRA, bitmap);
    fromActivity.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person_info);
    ((RippleBackground) findViewById(R.id.ripple1)).startRippleAnimation();
    ((RippleBackground) findViewById(R.id.ripple2)).startRippleAnimation();
    initViews();
    updatePersonDetails();
  }

  private void initViews() {
    mNameText = (TextView) findViewById(R.id.details_name);
    mProfileImage = (ImageView) findViewById(R.id.details_profile);
    mSourceImage = (ImageView) findViewById(R.id.details_source_image);
    mSsnText = (TextView) findViewById(R.id.details_ssn);
    mBirthDayText = (TextView) findViewById(R.id.details_birth_date);
    mDescriptionText = (TextView) findViewById(R.id.details_description);
  }

  private void updatePersonDetails() {
    PersonDetails details = extractPersonDetails();
    mNameText.setText(details.getName());
    mSourceImage.setImageBitmap(extractSourceImage());
    mSsnText.setText("SSN - " + details.getSsn());
    String birthDate = new SimpleDateFormat("dd-MM-yyyy").format(details.getBirthDate());
    mBirthDayText.setText("Birthday - " + birthDate);
    mDescriptionText.setText(details.getDescription());
    Glide.with(this).load(details.getImageUrl()).into(mProfileImage);
  }

  private PersonDetails extractPersonDetails() {
    return (PersonDetails) getIntent().getSerializableExtra(DETAILS_EXTRA);
  }

  private Bitmap extractSourceImage() {
    return ((MyApplication) getApplication()).getBitmap();
  }
}
