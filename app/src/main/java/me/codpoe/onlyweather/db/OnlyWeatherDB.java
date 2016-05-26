package me.codpoe.onlyweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import me.codpoe.onlyweather.model.City;

/**
 * Created by Codpoe on 2016/5/12.
 */
public class OnlyWeatherDB {
    // 数据库名
    public static final String DB_NAME = "only_weather";
    // 数据库版本
    public static final int VERSION = 1;

    private static OnlyWeatherDB sOnlyWeatherDB;
    private SQLiteDatabase mSQLiteDatabase;

    // 构造方法私有化
    private OnlyWeatherDB(Context context) {
        OnlyWeatherOpenHelper dbHelper = new OnlyWeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        mSQLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * 对外的方法
     */

    // 获取 OnlyWeatherDB 的实例
    public synchronized static OnlyWeatherDB getInstance(Context context) {
        if(sOnlyWeatherDB == null) {
            sOnlyWeatherDB = new OnlyWeatherDB(context);
        }
        return sOnlyWeatherDB;
    }

    // 将 City 实例存储到数据库
    public Boolean saveCity(City city) {
        if(city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            mSQLiteDatabase.insert("OnlyCity", null, values);
            return true;
        }
        return false;
    }


    // 读取所有城市的信息
    public List<City> loadCities() {
        List<City> list = new ArrayList<>();
        Cursor cursor = mSQLiteDatabase.query("OnlyCity", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    // 从数据库删除 City
    public void deleteCity(City city) {
        mSQLiteDatabase.delete("OnlyCity", "city_name = ?", new String[]{city.getCityName()});
    }
}
