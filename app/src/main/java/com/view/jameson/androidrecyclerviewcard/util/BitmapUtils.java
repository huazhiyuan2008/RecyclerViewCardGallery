package com.view.jameson.androidrecyclerviewcard.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtils {
    public static int MIN_WIDTH = 100;

    /**
     * 按最大边按一定大小缩放图片
     *
     * @param resources
     * @param resId
     * @param maxSize 压缩后最大长度
     * @return
     */
    public static Bitmap scaleImage(Resources resources, int resId, int maxSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resId, options);
        options.inSampleSize = calculateInSampleSize(options, maxSize, maxSize);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeResource(resources, resId, options);
    }

    /**
     * 计算inSampleSize
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (width < MIN_WIDTH) {
            return inSampleSize;
        } else {
            int heightRatio;
            if (width > height && reqWidth < reqHeight || width < height && reqWidth > reqHeight) {
                heightRatio = reqWidth;
                reqWidth = reqHeight;
                reqHeight = heightRatio;
            }

            if (height > reqHeight || width > reqWidth) {
                heightRatio = Math.round((float) height / (float) reqHeight);
                int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
            }

            return inSampleSize;
        }
    }
}