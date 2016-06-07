package me.codpoe.onlyweather.util;

import android.content.Context;
import android.content.SharedPreferences;

import me.codpoe.onlyweather.base.BaseApplication;

/**
 * Created by Codpoe on 2016/5/23.
 */
public class SettingUtils {

    private static SettingUtils mSettingUtils;
    private SharedPreferences mPrefs;

    // 私有化构造方法
    private SettingUtils() {
        mPrefs = BaseApplication.getAppContext().getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    // 获取单例
    public static SettingUtils getInstance() {
        if(mSettingUtils == null) {
            mSettingUtils = new SettingUtils();
        }
        return mSettingUtils;
    }

    // 是否自动更新
    public void setIsAutoUpdate(boolean b) {
        mPrefs.edit().putBoolean("is_auto_update", b).apply();
    }
    public boolean getIsAutoUpdate() {
        return mPrefs.getBoolean("is_auto_update", true);
    }

    // 自动更新频率
    public void setAutoUpdate(int i) {
        mPrefs.edit().putInt("auto_update", i).apply();
    }
    public int getAutoUpdate() {
        return mPrefs.getInt("auto_update", 0);
    }

    // 通知栏天气
    public void setIsShowNotification(boolean b) {
        mPrefs.edit().putBoolean("is_show_notification", b).apply();
    }
    public boolean getIsShowNotification() {
        return mPrefs.getBoolean("is_show_notification", true);
    }

    // 是否显示黄历卡片
    public void setIsShowHuangLi(boolean b) {
        mPrefs.edit().putBoolean("is_show_huang_li", b).apply();
    }
    public boolean getIsShowHuangLi() {
        return mPrefs.getBoolean("is_show_huang_li", false);
    }

    // 是否显示星座卡片
    public void setIsShowCons(boolean b) {
        mPrefs.edit().putBoolean("is_show_cons", b).apply();
    }
    public boolean getIsShowCons() {
        return mPrefs.getBoolean("is_show_cons", false);
    }

    // 选择星座
    public void setSelectedCons(String s) {
        mPrefs.edit().putString("selected_cons", s).apply();
    }
    public String getSelectedCons() {
        return mPrefs.getString("selected_cons", "白羊座");
    }
}
