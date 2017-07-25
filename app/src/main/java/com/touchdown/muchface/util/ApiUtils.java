package com.touchdown.muchface.util;

import com.cloudinary.Cloudinary;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.touchdown.muchface.domain.FaceDetectorImpl;

import java.util.HashMap;
import java.util.Map;

public class ApiUtils {
    private static final String SUBSCRIPTION_KEY = "9cf871789f8e4ebcab54a1b6cbe2dd1d";
    private static final String SERVICE_HOST = "https://southeastasia.api.cognitive.microsoft.com/face/v1.0";

    public static FaceDetectorImpl createFaceDetector() {
        return new FaceDetectorImpl(createFaceServiceRestClient(), createCloudinary());
    }

    private static FaceServiceRestClient createFaceServiceRestClient() {
        return new FaceServiceRestClient(SERVICE_HOST, SUBSCRIPTION_KEY);
    }

    private static Cloudinary createCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dzdz6bcb6");
        config.put("api_key", "421814359539652");
        config.put("api_secret", "tksYjyszMzH0nkV5yKwy3-l40mc");
        return new Cloudinary(config);
    }
}
