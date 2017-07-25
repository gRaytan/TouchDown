/*
package com.touchdown.muchface.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.touchdown.muchface.R;
import java.io.File;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(RobolectricTestRunner.class)
public class FaceDetectorImplTest {

  private static final String SUBSCRIPTION_KEY = "";
  private FaceDetectorImpl mTestSubject;

  @Before
  public void setUp() throws Exception {
    mTestSubject = new FaceDetectorImpl(new FaceServiceRestClient(SUBSCRIPTION_KEY));
  }

  @Test
  public void get() throws Exception {
    Bitmap bitmap = BitmapFactory.decodeFile("C:\\test.jpg");
    PersonDetails pd = mTestSubject.getDetails(bitmap);

    PersonDetails details =
        new PersonDetails("name", "ssn", "imageurl", "description", new Date(1));
    Assert.assertThat(pd, equalTo(details));
  }

  //@Test
  //public void onLowConfidence_throwRuntimeException() throws Exception {
  //  //Arrange
  //  Bitmap lowConfBitmap = mock(Bitmap.class);
  //  mTestSubject.getDetails(lowConfBitmap);
  //  when(mFaceServiceClientMock.detect(Mockito.any(InputStream.class), Mockito.any(Boolean.class),
  //      Mockito.any(Boolean.class),
  //      Mockito.any(FaceServiceClient.FaceAttributeType[].class))).thenReturn(null);
  //
  //  //Act
  //
  //  //Assert
  //}
  //
  //@Test
  //public void get() throws Exception {
  //  //Arrange
  //  PersonDetails personDetails =
  //      new PersonDetails("name", "ssn", "imageurl", "description", new Date(1));
  //  mockClientResponse(personDetails);
  //
  //  //Act
  //  PersonDetails pd = mTestSubject.getDetails(mImage1);
  //
  //  //Assert
  //  Assert.assertThat(pd, equalTo(personDetails));
  //}
  //
  //@Test
  //public void get2() throws Exception {
  //  //Arrange
  //  PersonDetails personDetails =
  //      new PersonDetails("name2", "ssn2", "imageurl2", "description2", new Date(2));
  //  mockClientResponse(personDetails);
  //
  //  //Act
  //  PersonDetails pd = mTestSubject.getDetails(mImage2);
  //
  //  //Assert
  //  Assert.assertThat(pd, equalTo(personDetails));
  //}
  //
  //private void mockClientResponse(PersonDetails personDetails) throws ClientException, IOException {
  //  when(mFaceServiceClientMock.getPerson(any(String.class), any(UUID.class))).thenReturn(
  //      getPerson(personDetails));
  //}
  //
  //private Person getPerson(PersonDetails personDetails) {
  //  Person value = new Person();
  //  value.userData = new Gson().toJson(personDetails);
  //  return value;
  //}
}*/
