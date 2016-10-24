package com.xqkj.quanjin.helper;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件辅助类
 */
public class FileHelper {
    private final static String TAG = FileHelper.class.getSimpleName();

    /**
     * 创建一个用于保存图片的文件
     */
    public static File createFile(Context context, String child) {
        File file;

        File directory = getExternalStoragePublicDirectory(child);
        String timeStamp = getTimeStamp("yyyyMMdd_HHmmss");
        file = new File(directory.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        return file;
    }

    /**
     * 获取或者创建内部存储目录
     */
    private static File getExternalFilesDir(Context context, String child) {
        File directory = null;
        if (!isExternalStorageWritable()) {
            Log.e(TAG, "没有读写权限");
            return directory;
        }
        directory = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), child);
        if (!directory.exists()) {
            Log.e(TAG, "directory.exists()==false");
            if (!directory.mkdirs()) {
                Log.e(TAG, "目录创建失败");
            } else {
                Log.e(TAG, directory.getPath());
            }
        }
        return directory;
    }

    /**
     * 获取或者创建外部存储目录
     */
    private static File getExternalStoragePublicDirectory(String child) {
        File directory = null;
        if (!isExternalStorageWritable()) {
            Log.e(TAG, "没有读写权限");
            return directory;
        }
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), child);
        if (!directory.exists()) {
            Log.e(TAG, "directory.exists()==false");
            if (!directory.mkdirs()) {
                Log.e(TAG, "目录创建失败");
            } else {
                Log.e(TAG, directory.getPath());
            }
        }
        return directory;
    }

    /**
     * 检测外部存储卡的可读写状态
     *
     * @return
     */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Checks if external storage is available to at least read
     *
     * @return
     */
    private static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    private static String getTimeStamp(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }
}
