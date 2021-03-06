package com.touchdown.muchface;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import com.touchdown.muchface.domain.DetectionManager;
import com.touchdown.muchface.domain.PersonDetails;
import com.touchdown.muchface.util.ApiUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

  private static final int NUM_CONCURRENT_SENDING_THREADS = 5;
  private static String LOG_TAG = MyApplication.class.getSimpleName();

  private DetectionManager mDetectionManager;
  private List<Bitmap> bitmaps;
  private List<PersonDetails> details;
  private List<String> names;
  private Bitmap mBit;

  @Override
  public void onCreate() {
    super.onCreate();

    mDetectionManager = new MyDetectionManager();
    bitmaps = new ArrayList<>();
    details = new ArrayList<>();
    names = new ArrayList<>();
  }

  public List<Bitmap> getBitmaps() {
    return bitmaps;
  }

  public List<PersonDetails> getDetails() {
    return details;
  }

  public List<String> getNames() {
    return names;
  }

  public DetectionManager getDetectionManager() {
    return mDetectionManager;
  }

  public void setBitmap(Bitmap bit) {
    mBit = bit;
  }

  public Bitmap getBitmap() {
    return mBit;
  }

  private class MyDetectionManager implements DetectionManager {

    private onResultListener mListener;
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
        PersonDetails details = ApiUtils.createFaceDetector().getDetails(bitmap);
        if (mListener != null) {
          mListener.onSuccess(details, bitmap);
        }
      } catch (Exception e) {
        Log.w(LOG_TAG, "failed detecting bitmap", e);
        if (mListener != null) {
          mListener.onFailure(bitmap);
        }
      }
    }

    @Override
    public void setOnResultListener(onResultListener listener) {
      mListener = listener;
    }
  }
}
