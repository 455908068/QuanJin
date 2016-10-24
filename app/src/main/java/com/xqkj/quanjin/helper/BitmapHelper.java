package com.xqkj.quanjin.helper;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片解码辅助类
 */
public class BitmapHelper {

    /**
     * 压缩已有Bitmap后的文件
     */
    @Deprecated
    public static File compressBitmapAsFile(Context context, Bitmap bitmap, int quality) {
        File file;
        file = FileHelper.createFile(context, "Truck_Compress");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            CloseHelper.closeQuietly(fos);
        }
        return file;
    }

    /**
     * 设置图片缩放
     */
    public static Intent zoomPhoto(Uri uri, int size) {
        Intent intent;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        return intent;
    }

    /**
     * 设置图片缩放
     */
    public static Intent zoomPhoto(Uri uri, int width, int height) {
        Intent intent;

        intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", true);

        return intent;
    }

    /**
     * Add the Photo to a Gallery
     *
     * @param path
     * @param activity
     */
    public static void galleryAddPic(String path, Activity activity) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setImageBitmap(Uri uri, ImageView imageView) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
        if (bitmap == null) {
            options.inSampleSize = BitmapHelper.computeSampleSize(options, -1, imageView.getWidth() * imageView.getHeight());
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    /**
     * Uri转bitmap防止OOM
     */
    public static Bitmap getBitmap(Context context, Uri uri) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
        if (bitmap == null) {
            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
            options.inSampleSize = computeSampleSize(options, -1, widthPixels * heightPixels);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(uri.getPath(), options);
        }
        return bitmap;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 通过Uri获取文件
     *
     * @param context
     * @param uri
     * @return
     */
    public static File getFileFromMediaUri(Context context, Uri uri) {
        File file = null;
        if (uri.getScheme().compareTo("content") == 0) {
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, null, null, null, null);// 根据Uri从数据库中找
            if (cursor != null) {
                cursor.moveToFirst();
                String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                cursor.close();
                if (filePath != null) {
                    file = new File(filePath);
                }
            }
        } else if (uri.getScheme().compareTo("file") == 0) {
            file = new File(uri.toString().replace("file://", ""));
        }
        return file;
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param filename 图片绝对路径
     * @return 图片的旋转角度
     */
    public static int getBitmapDegree(String filename) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(filename);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bitmap  需要旋转的图片
     * @param degrees 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, float degrees) {
        Bitmap temp = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            temp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (temp == null) {
            temp = bitmap;
        }
        if (bitmap != temp) {
            bitmap.recycle();
        }
        return temp;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 把压缩后的Bitmap写入文件
     */
    public static File getFileByCompressedBitmap(Context context, Bitmap bitmap) {
        File file = FileHelper.createFile(context, "Truck_Compress");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 通过uri获取图片并进行压缩
     */
    public static Bitmap getCompressedBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            InputStream input = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);

            int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
            int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
            int minSideLength = -1;
            int maxNumOfPixels = widthPixels * heightPixels;
            options.inSampleSize = computeSampleSize(options, minSideLength, maxNumOfPixels);

            options.inJustDecodeBounds = false;
            input = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //再进行质量压缩
        return compress(bitmap);
    }

    /**
     * 质量压缩方法
     */
    public static Bitmap compress(Bitmap bitmap) {
        return compress(bitmap, 100);
    }

    /**
     * 质量压缩方法
     *
     * @param bitmap 需要压缩的Bitmap
     * @param length 压缩后图片文件长度不能超过这个值
     * @return
     */
    public static Bitmap compress(Bitmap bitmap, int length) {
        if (bitmap == null) {
//            throw new RuntimeException("bitmap不能为空");
            LogHelper.e("BitmapHelper", "bitmap=null");
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf;
        int quality = 100;
        //循环判断如果压缩后图片是否大于100kb,大于继续压缩
        do {
            baos.reset();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            buf = baos.toByteArray();
            quality -= 10;
        } while (buf.length / 1024 > length);
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        bitmap = BitmapFactory.decodeStream(bais, null, null);

        return bitmap;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(outWidth * outHeight / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(outWidth / minSideLength), Math.floor(outHeight / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 回收Bitmap
     */
    public static void recycleBitmap(Bitmap bitmap) {
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }
}
