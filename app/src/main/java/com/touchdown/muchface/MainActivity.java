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
import com.touchdown.muchface.util.Constants;

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
        mImageView = (ImageView) findViewById(R.id.photo_img_view);
    }

    public void onCameraClick(View view) {
        startCamera();
    }

    public void onSendClick(View view) {
        new DetectFaceAsyncTask().execute(mBitmap);
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

    private void showLoadingDialog() {
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mDialog.setTitleText("Loading");
        mDialog.show();
    }

    private void onDetailsArrived(PersonDetails personDetails) {
        mDialog.dismissWithAnimation();
        if (personDetails != null) {
            openPersonDetailsActivity(personDetails);
        }
    }

    private void openPersonDetailsActivity(PersonDetails personDetails) {
        Intent intent = new Intent(MainActivity.this, PersonDetailsActivity.class);
        intent.putExtra(Constants.DETAILS_EXTRA, personDetails);
        intent.putExtra(Constants.IMAGE_EXTRA, mBitmap);
        MainActivity.this.startActivity(intent);
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
