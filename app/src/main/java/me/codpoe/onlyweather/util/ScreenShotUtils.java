package me.codpoe.onlyweather.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Codpoe on 2016/5/30.
 * 截图工具类
 */
public class ScreenShotUtils {

    public static final String IMG_PATH = Environment.getExternalStorageDirectory() + "/only_weather/";

    /**
     * 进行截取屏幕
     * @param activity
     * @return bitmap
     */
    public static Bitmap takeScreenShot(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(bitmap, 0,
                statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }


    /**
     * 保存图片到sdcard中
     * @param bitmap
     */
    private static boolean savePic(Bitmap bitmap,String strName)
    {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strName);
            if(fos != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
                fos.flush();
                fos.close();
                return true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 截图
     * @param activity
     * @return 截图并且保存sdcard成功返回true，否则返回false
     */
    public static String shot(Activity activity)
    {
        String imgPath = IMG_PATH + System.currentTimeMillis() + "share.png";
        Log.d("ScreenShot", "shot_1");
        ScreenShotUtils.savePic(takeScreenShot(activity), imgPath);
        Log.d("ScreenShot", "shot_2");
        return imgPath;
    }
}
