package com.touchdown.muchface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.touchdown.muchface.domain.PersonDetails;
import com.touchdown.muchface.util.ApiUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_TASK_OPERATION = 0;
    private ImageView mImageView;
    private Bitmap mBitmap;
    private SweetAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.captured_image);
    }

    public void onCameraClick(View view) {
        startCamera();
    }

    public void onSendClick(View view) {
        if (mBitmap != null) {
            new DetectFaceAsyncTask().execute(mBitmap);
        } else {
            showErrorDialog("There is no image to recognize!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_TASK_OPERATION && resultCode == RESULT_OK) {
            mBitmap = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(mBitmap);
        }
    }

    private void startCamera() {
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_TASK_OPERATION);
    }

    private void showErrorDialog(String text) {
        SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("Error");
        dialog.setContentText(text);
        dialog.show();
    }

    private void showLoadingDialog() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Loading");
        mDialog.show();
    }

    private void onDetailsArrived(PersonDetails details) {
        mDialog.dismissWithAnimation();
        if (details != null) {
            PersonDetailsActivity.startActivity(MainActivity.this, details, mBitmap);
        } else {
            mImageView.setImageDrawable(null);
            mBitmap = null;
            showErrorDialog("Could not recognize your face, sorry :(");
        }
    }

    private class DetectFaceAsyncTask extends AsyncTask<Bitmap, Void, PersonDetails> {
        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        @Override
        protected PersonDetails doInBackground(Bitmap... bitmaps) {
            try {
                return ApiUtils.createFaceDetector().getDetails(bitmaps[0]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(PersonDetails personDetails) {
            onDetailsArrived(personDetails);
        }
    }
}
