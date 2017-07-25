package com.touchdown.muchface;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchdown.muchface.domain.PersonDetails;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

public class PersonDetailsActivity extends AppCompatActivity {

    public static final String DETAILS_EXTRA = "details_extra";
    public static final String IMAGE_EXTRA = "image_extra";
    private TextView mNameText;
    private ImageView mProfileImage;
    private ImageView mSourceImage;
    private TextView mSsnText;
    private TextView mBirthDayText;
    private TextView mDescriptionText;

    public static void startActivity(Activity fromActivity, PersonDetails details, Bitmap bitmap) {
        Intent intent = new Intent(fromActivity, PersonDetailsActivity.class);
        intent.putExtra(DETAILS_EXTRA, details);
        intent.putExtra(IMAGE_EXTRA, bitmap);
        fromActivity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
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
        Bitmap bitmap = extractSourceImage();
        mSsnText.setText(details.getSsn());
        String birthDate = new SimpleDateFormat("dd-MM-yyyy").format(details.getBirthDate());
        mBirthDayText.setText(birthDate);
        mDescriptionText.setText(details.getDescription());
        mNameText.setText(details.getName());
        mSourceImage.setImageBitmap(bitmap);
        new LoadBitmapFromUrl(mProfileImage).execute(details.getImageUrl());
    }

    private PersonDetails extractPersonDetails() {
        return (PersonDetails) getIntent().getSerializableExtra(DETAILS_EXTRA);
    }

    private Bitmap extractSourceImage() {
        return getIntent().getParcelableExtra(IMAGE_EXTRA);
    }

    private class LoadBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {

        private ImageView mTargetView;

        LoadBitmapFromUrl(ImageView targetView) {
            mTargetView = targetView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return getBitmapFromURL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mTargetView.setImageBitmap(bitmap);
        }

        private Bitmap getBitmapFromURL(String src) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) new URL(src).openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        }
    }
}
