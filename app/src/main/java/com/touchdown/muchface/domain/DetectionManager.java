package com.touchdown.muchface.domain;

import android.graphics.Bitmap;

public interface DetectionManager {

  void send(Bitmap bitmap);

  void setOnResultListener(onResultListener listener);

  interface onResultListener {
    void onSuccess(PersonDetails details, Bitmap source);

    void onFailure(Bitmap source);
  }
}
