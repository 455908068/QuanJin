package com.xqkj.quanjin.helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * <!--Getting access to external storage-->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class CameraHelper {
    private final static String TAG = CameraHelper.class.getSimpleName();

    public static final int REQUEST_IMAGE_CAPTURE = 1;

    /**
     * 在一个Activity中进行拍照
     */
    public static Uri takePicture(Activity activity, String child) {
        Uri uri = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            File file = FileHelper.createFile(activity, child);
            if (file != null) {
                uri = Uri.fromFile(file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        return uri;
    }

    /**
     * 在一个Fragment中进行拍照
     */
    public static Uri takePicture(Fragment fragment, String child) {
        Uri uri = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(fragment.getActivity().getPackageManager()) != null) {
            File imageFile = FileHelper.createFile(fragment.getActivity(), "Truck");
            if (imageFile != null) {
                uri = Uri.fromFile(imageFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                fragment.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
        return uri;
    }
}
