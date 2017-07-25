package com.touchdown.muchface.domain;

import android.graphics.Bitmap;
import android.util.Log;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.Person;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class FaceDetectorImpl implements FaceDetector {

  private static final String GROUP_ID = "test";
  private final FaceServiceClient mFaceServiceClient;
  private final Cloudinary mCloudinary;

  public FaceDetectorImpl(FaceServiceClient faceServiceClient, Cloudinary cloudinary) {
    mFaceServiceClient = faceServiceClient;
    mCloudinary = cloudinary;
  }

  @Override
  public PersonDetails getDetails(Bitmap image) {
    try {
      String imageUrl = uploadBitmap(image);
      Log.d("FaceDetectorImpl", "uploaded image to " + imageUrl);
      Face[] faces = mFaceServiceClient.detect(imageUrl, true, false, null);
      IdentifyResult[] identifyResults =
          mFaceServiceClient.identity(GROUP_ID, new UUID[] { faces[0].faceId }, 1);
      UUID personId = identifyResults[0].candidates.get(0).personId;
      Person person = mFaceServiceClient.getPerson(GROUP_ID, personId);
      return parse(person.userData);
    } catch (Exception e) {
      throw new RuntimeException("Failed getting details", e);
    }
  }

  private String uploadBitmap(Bitmap image) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    image.compress(Bitmap.CompressFormat.JPEG, 75, outputStream);
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    try {
      Map<String, String> upload =
          mCloudinary.uploader().upload(inputStream, ObjectUtils.emptyMap());
      String public_id = upload.get("public_id");
      return mCloudinary.url().generate(public_id + ".jpg");
    } catch (IOException e) {
      throw new RuntimeException("Couldn't upload image", e);
    }
  }

  private PersonDetails parse(String userData) {
    PersonDetails personDetails = new Gson().fromJson(userData, PersonDetails.class);
    return personDetails;
  }
}
