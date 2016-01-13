package com.coolweather.app.util;

import android.content.Context;
import android.util.Log;

import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.model.WeatherInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Created by apple on 16/1/11.
 */
public class Utility {
    public static boolean handleCityResponse(CoolWeatherDB coolWeatherDB, String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int eventType = xmlPullParser.getEventType();

            City city = new City();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG :
                        if ("d".equals(xmlPullParser.getName()) && "101".equals(xmlPullParser.getAttributeValue(0).substring(0, 3))) {
                            Log.d("ddd", "handleCityResponse: " + xmlPullParser.getAttributeValue(0));
                            city.setCityName(xmlPullParser.getAttributeValue(1));
                            city.setCityCode(xmlPullParser.getAttributeValue(0));
                            city.setPinyin(xmlPullParser.getAttributeValue(2));
                            city.setProvinceOrCountryName(xmlPullParser.getAttributeValue(3));
                            coolWeatherDB.saveCity(city);
                        }
                        break;
                    default:break;
                }
                eventType = xmlPullParser.next();
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static WeatherInfo handleWeatherResponse(Context context, City city, String  response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject dataInfo = jsonObject.getJSONObject("data");
            JSONArray jsonArray = dataInfo.getJSONArray("forecast");
            JSONObject weatherInfo = jsonArray.getJSONObject(0);
            WeatherInfo today = new WeatherInfo();
            today.setCityName(city.getCityName());
            today.setTemp(dataInfo.getString("wendu"));
            today.setDate(weatherInfo.getString("date"));
            String high = weatherInfo.getString("high").substring(3);
            today.setHigh(high);
            String low = weatherInfo.getString("low").substring(3);
            today.setLow(low);
            today.setWeather(weatherInfo.getString("type"));
            return today;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
