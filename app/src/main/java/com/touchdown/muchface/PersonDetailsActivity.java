package com.touchdown.muchface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchdown.muchface.domain.PersonDetails;
import com.touchdown.muchface.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PersonDetailsActivity extends AppCompatActivity {

    private TextView mNameText;
    private ImageView mProfileImage;
    private ImageView mSourceImage;
    private TextView mSsnText;
    private TextView mBirthDayText;
    private TextView mDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        initViews();
        updateDataFromIntent();
    }

    private void initViews() {
        mNameText = (TextView) findViewById(R.id.textview_name);
        mProfileImage = (ImageView) findViewById(R.id.server_img);
        mSourceImage = (ImageView) findViewById(R.id.selfie);
        mSsnText = (TextView) findViewById(R.id.ssn_text_view);
        mBirthDayText = (TextView) findViewById(R.id.birth_date);
        mDescriptionText = (TextView) findViewById(R.id.description);
    }

    private void updateDataFromIntent() {
        updateSourceImage();
        updatePersonDetails();
    }

    private void updateSourceImage() {
        mSourceImage.setImageBitmap(extractSourceImage());
    }

    private Bitmap extractSourceImage() {
        return getIntent().getParcelableExtra(Constants.IMAGE_EXTRA);
    }

    private void updatePersonDetails() {
        PersonDetails details = extractPersonDetails();
        mSsnText.setText(details.getSsn());
        mBirthDayText.setText(details.getBirthDate().toString());
        mDescriptionText.setText(details.getDescription());
        mNameText.setText(details.getName());
        new LoadBitmapFromUrl(mProfileImage).execute(details.getImageUrl());
    }

    private PersonDetails extractPersonDetails() {
        return (PersonDetails) getIntent().getSerializableExtra(Constants.DETAILS_EXTRA);
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
