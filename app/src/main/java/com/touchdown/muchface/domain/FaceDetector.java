package com.touchdown.muchface.domain;

import android.graphics.Bitmap;

public interface FaceDetector {

  PersonDetails getDetails(Bitmap image);
}
