package me.codpoe.onlyweather.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Codpoe on 2016/5/23.
 */
public class BaseApplication extends Application {

    public static Context mAppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
