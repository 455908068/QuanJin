package com.xqkj.quanjin.helper;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

/**
 * 相册工具
 */
public class GalleryHelper {
    public static final int REQUEST_GALLERY = 2;

    /**
     * 仅用于Activity中
     *
     * @param activity
     */
    public static void dispatchGallery(Activity activity) {
//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, REQUEST_GALLERY);
    }

    /**
     * 仅用于Fragment中
     *
     * @param fragment
     */
    public static void dispatchGallery(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
        fragment.startActivityForResult(intent, REQUEST_GALLERY);
    }
}
