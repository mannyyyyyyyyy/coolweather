package com.coolweather.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coolweather.app.R;
import com.coolweather.app.model.WeatherInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by apple on 16/1/13.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {

    private LinearLayout weatherInfoLayout;
    private TextView cityNameText;
    private TextView publishText;
    private TextView weatherDespText;
    private TextView temp1Text;
    private TextView temp2Text;
    private TextView currentDateText;
    private Button switchCity;
    private Button refreshWeather;
    private TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityNameText = (TextView) findViewById(R.id.city_name);
        publishText = (TextView) findViewById(R.id.publish_text);
        weatherDespText = (TextView) findViewById(R.id.weather_desp);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        temp = (TextView) findViewById(R.id.temp);
        currentDateText = (TextView) findViewById(R.id.current_date);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);
        WeatherInfo weatherInfo = (WeatherInfo) getIntent().getSerializableExtra("weather_info");
        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);
        cityNameText.setText(weatherInfo.getCityName());
        temp1Text.setText(weatherInfo.getHigh());
        temp2Text.setText(weatherInfo.getLow());
        weatherDespText.setText(weatherInfo.getWeather());
        publishText.setText(weatherInfo.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日");
        currentDateText.setText(simpleDateFormat.format(new Date()));
        temp.setText(weatherInfo.getTemp() + " ℃");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switch_city:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                break;
            case R.id.refresh_weather:
                break;
            default:
                break;
        }
    }
}
