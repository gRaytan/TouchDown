package com.touchdown.muchface;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.touchdown.muchface.domain.DetectionManager;
import com.touchdown.muchface.domain.PersonDetails;
import com.touchdown.muchface.util.ApiUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

  private static final int NUM_CONCURRENT_SENDING_THREADS = 5;
  private static String LOG_TAG = MyApplication.class.getSimpleName();

  private DetectionManager mDetectionManager;

  @Override
  public void onCreate() {
    super.onCreate();

    mDetectionManager = new MyDetectionManager();
  }

  public DetectionManager getDetectionManager() {
    return mDetectionManager;
  }

  private class MyDetectionManager implements DetectionManager {

    private OnSuccessListener mListener;
    private long mLastFireTime = 0;
    private ExecutorService mExecutor =
        Executors.newFixedThreadPool(NUM_CONCURRENT_SENDING_THREADS);

    @Override
    public void send(final Bitmap bitmap) {
      mExecutor.submit(new Runnable() {
        @Override
        public void run() {
          doSend(bitmap);
        }
      });
    }

    private void doSend(Bitmap bitmap) {
      try {
        long issueTime = System.currentTimeMillis();
        PersonDetails details = ApiUtils.createFaceDetector().getDetails(bitmap);
        if (mListener != null && issueTime > mLastFireTime) {
          mLastFireTime = System.currentTimeMillis();
          mListener.onSuccess(details, bitmap);
        }
      } catch (Exception e) {
        Log.w(LOG_TAG, "failed detecting bitmap", e);
      }
    }

    @Override
    public void setOnSuccessListener(OnSuccessListener listener) {
      mListener = listener;
    }
  }
}
