package me.codpoe.onlyweather.base;

import android.app.Application;
import android.content.Context;

import im.fir.sdk.FIR;


/**
 * Created by Codpoe on 2016/5/23.
 */
public class BaseApplication extends Application {

    public static Context mAppContext = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = getApplicationContext();
        FIR.init(this);
    }

    public static Context getAppContext() {
        return mAppContext;
    }

}
