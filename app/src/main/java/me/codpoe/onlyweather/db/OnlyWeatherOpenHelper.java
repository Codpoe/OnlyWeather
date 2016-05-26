package me.codpoe.onlyweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Codpoe on 2016/5/12.
 */
public class OnlyWeatherOpenHelper extends SQLiteOpenHelper{

    // City 建表语句
    public static final String CREATE_CITY = "create table OnlyCity ("
            + "id integer primary key autoincrement, "
            + "city_name text)";

    public OnlyWeatherOpenHelper(Context context,
                                 String name,
                                 SQLiteDatabase.CursorFactory factory,
                                 int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
