package com.touchdown.muchface;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.cloudinary.Cloudinary;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.touchdown.muchface.domain.FaceDetector;
import com.touchdown.muchface.domain.FaceDetectorImpl;
import com.touchdown.muchface.domain.PersonDetails;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void onResume() {
    super.onResume();
    final FaceDetector faceDetector =
        new FaceDetectorImpl(createFaceServiceRestClient(), createCloudinary());

    new AsyncTask<Bitmap, Void, PersonDetails>() {
      @Override
      protected PersonDetails doInBackground(Bitmap... bitmaps) {
        return faceDetector.getDetails(bitmaps[0]);
      }

      @Override
      protected void onPostExecute(PersonDetails personDetails) {
        super.onPostExecute(personDetails);
        Toast.makeText(MainActivity.this, personDetails.toString(), Toast.LENGTH_SHORT).show();
      }
    }.execute(BitmapFactory.decodeResource(getResources(), R.drawable.test));
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
}
