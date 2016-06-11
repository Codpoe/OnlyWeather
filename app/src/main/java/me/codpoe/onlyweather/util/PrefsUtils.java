package me.codpoe.onlyweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import me.codpoe.onlyweather.model.entity.WeatherBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Codpoe on 2016/6/11.
 */
public class PrefsUtils {

    /**
     * 保存定位城市
     */
    public static void saveLocationCity(Context context, String cityName) {
        SharedPreferences.Editor editor = context.getSharedPreferences("location_city_prefs", Context.MODE_PRIVATE).edit();
        editor.putString("location_city", cityName).commit();
    }

    /**
     * 获取已保存的定位城市
     */
    public static String getLocationCity(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("location_city_prefs", Context.MODE_PRIVATE);
        return prefs.getString("location_city", "");
    }

    /**
     * 保存首页要展示的城市到 Prefs
     * @param cityName
     */
    public static void saveShowCityToPrefs(Context context, String cityName) {
        SharedPreferences.Editor editor = context.getSharedPreferences("show_city_prefs", Context.MODE_PRIVATE).edit();
        editor.putString("show_city", cityName);
        editor.commit();
    }

    /**
     * 获取已保存的首页展示的城市
     * @return
     */
    public static String getShowCityFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("show_city_prefs", Context.MODE_PRIVATE);
        return prefs.getString("show_city", "");
    }

    /**
     * 把 WeatherList 对象转换成 JSON 形式的字符串，并存进 SharedPreferences
     */
    public static void saveShowWeatherToPrefs(final Context context, WeatherBean weatherBean) {
        Observable.just(weatherBean)
                .map(new Func1<WeatherBean, Integer>() {
                    @Override
                    public Integer call(WeatherBean weatherBeans) {
                        SharedPreferences.Editor editor = context.getSharedPreferences("show_weather_prefs", Context.MODE_PRIVATE).edit();
                        String str = "";
                        Gson gson = new Gson();
                        str = gson.toJson(weatherBeans);
                        editor.putString("show_weather", str);
                        editor.commit();
                        return 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PrefsUtils", e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });

    }

    /**
     * 从 SharedPreferences 读取字符串，并把字符串解析成 WeatherList 对象
     */
    public static WeatherBean getShowWeatherFromPrefs(Context context) {
        WeatherBean weatherBean = null;
        SharedPreferences prefs = context.getSharedPreferences("show_weather_prefs", Context.MODE_PRIVATE);
        String str = prefs.getString("show_weather", "");
        if(str != null) {
            Gson gson = new Gson();
            weatherBean = gson.fromJson(str, new TypeToken<WeatherBean>(){}.getType());
        }
        return weatherBean;
    }
}
