package com.touchdown.muchface.domain;

import android.graphics.Bitmap;

public interface DetectionManager {

  void send(Bitmap bitmap);

  void setOnSuccessListener(OnSuccessListener listener);

  interface OnSuccessListener {
    void onSuccess(PersonDetails details);
  }
}
