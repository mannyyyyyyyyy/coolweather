package com.coolweather.app.util;

import android.util.Log;

import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;

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
}
