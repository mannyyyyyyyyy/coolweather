package com.coolweather.app.model;

/**
 * Created by apple on 16/1/7.
 */
public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private String pinyin;
    private String provinceOrCountryName;

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getProvinceOrCountryName() {
        return provinceOrCountryName;
    }

    public void setProvinceOrCountryName(String provinceOrCountryName) {
        this.provinceOrCountryName = provinceOrCountryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
