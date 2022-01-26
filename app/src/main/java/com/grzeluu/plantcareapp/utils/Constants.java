package com.grzeluu.plantcareapp.utils;

import java.text.SimpleDateFormat;

public class Constants {

    public static final String PLANT_INTENT_EXTRAS_KEY = "Plant";

    public static final int PICK_IMAGE_CAMERA = 4321;
    public static final int PICK_IMAGE_GALLERY = 1234;

    public static final int PERMISSION_CAMERA = 1111;
    public static final int PERMISSION_STORAGE = 2222;
    public static final int WRITE_EXTERNAL_STORAGE = 3333;

    public static final SimpleDateFormat iso_8601_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
}
