package com.touchdown.muchface;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

  private static final int CAMERA_TASK_OPERATION = 6;
  private ImageView mImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mImageView = (ImageView) findViewById(R.id.photo_img_view);
  }

  public void onCameraClick(View view) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(intent, CAMERA_TASK_OPERATION);
  }

  public void onSendClick(View view) {
    showLoadingAlert();
  }

  private void showLoadingAlert() {
    final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
    pDialog.setTitleText("Loading");
    pDialog.setCancelable(true);
    pDialog.show();
    pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
      @Override
      public void onDismiss(DialogInterface dialog) {
        Intent myIntent = new Intent(MainActivity.this, PersonInfo.class);
        MainActivity.this.startActivity(myIntent);
      }
    });

    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      public void run() {
        pDialog.dismissWithAnimation();
      }
    }, 2000);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == CAMERA_TASK_OPERATION && resultCode == RESULT_OK) {
      mImageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }
  }
}
