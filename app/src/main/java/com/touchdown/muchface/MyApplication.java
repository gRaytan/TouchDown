package com.touchdown.muchface;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;
import com.cloudinary.Cloudinary;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.touchdown.muchface.domain.DetectionManager;
import com.touchdown.muchface.domain.FaceDetector;
import com.touchdown.muchface.domain.FaceDetectorImpl;
import com.touchdown.muchface.domain.PersonDetails;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application {

  private static final int NUM_CONCURRENT_SENDING_THREADS = 5;
  private static String LOG_TAG = MyApplication.class.getSimpleName();

  private DetectionManager mDetectionManager;
  private FaceDetector mFaceDetector;

  @Override
  public void onCreate() {
    super.onCreate();

    mFaceDetector = getFaceDetector();
    mDetectionManager = new MyDetectionManager();
  }

  private FaceDetectorImpl getFaceDetector() {
    return new FaceDetectorImpl(createFaceServiceRestClient(), createCloudinary());
  }

  public DetectionManager getDetectionManager() {
    return mDetectionManager;
  }

  private FaceServiceRestClient createFaceServiceRestClient() {
    return new FaceServiceRestClient("https://southeastasia.api.cognitive.microsoft.com/face/v1.0",
        "9cf871789f8e4ebcab54a1b6cbe2dd1d");
  }

  private Cloudinary createCloudinary() {
    Map config = new HashMap();
    config.put("cloud_name", "dzdz6bcb6");
    config.put("api_key", "421814359539652");
    config.put("api_secret", "tksYjyszMzH0nkV5yKwy3-l40mc");
    return new Cloudinary(config);
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
        PersonDetails details = getFaceDetector().getDetails(bitmap);
        if (mListener != null && issueTime > mLastFireTime) {
          mLastFireTime = System.currentTimeMillis();
          mListener.onSuccess(details);
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
