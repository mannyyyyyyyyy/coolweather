package com.coolweather.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/1/7.
 */
public class CoolWeatherDB {

    public static final String DB_NAME = "cool_weather";

    public static final int VERSION = 1;

    private static CoolWeatherDB coolWeatherDB;

    private SQLiteDatabase db;

    private CoolWeatherDB(Context context) {
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getInstance(Context context) {
        if (coolWeatherDB == null) {
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityName());
            values.put("city_code", city.getCityCode());
            values.put("pinyin", city.getPinyin());
            values.put("province_or_country_name", city.getProvinceOrCountryName());
            db.insert("City", null, values);
        }
    }

    public List<City> loadCities(String cityName) {
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City", null, "city_name like ?",
                new String[]{ "%" + String.valueOf(cityName) + "%"}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor
                        .getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor
                        .getColumnIndex("city_code")));
                city.setPinyin(cursor.getString(cursor.getColumnIndex("pinyin")));
                city.setProvinceOrCountryName(cursor.getString(cursor
                        .getColumnIndex("province_or_country_name")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean hasData() {
        Cursor cursor = db.query("City", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            return true;
        }
        return false;
    }
}
