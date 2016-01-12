package com.coolweather.app.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.model.City;
import com.coolweather.app.model.CoolWeatherDB;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/1/11.
 */
public class ChooseAreaActivity extends Activity {
        private ProgressDialog progressDialog;
        private ListView listView;
        private EditText searchInput;
        private Button searchButton;
        private ArrayAdapter<String> adapter;
        private CoolWeatherDB coolWeatherDB;
        private List<String> dataList = new ArrayList<String>();

        private List<City> cityList;
        private City selectedCity;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.choose_area);
            listView = (ListView) findViewById(R.id.list_view);
            searchInput = (EditText) findViewById(R.id.search_input);
            searchButton = (Button) findViewById(R.id.search);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        if (!coolWeatherDB.hasData()) {
            initDatabase();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cityList.get(position);
                queryFromServer(selectedCity.getCityCode());
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryCities();
            }
        });
    }

    private void queryCities() {
        cityList = coolWeatherDB.loadCities(searchInput.getText().toString());
        Log.d("ddd", "queryCities: " + cityList.size());
        dataList.clear();
        if (cityList.size() > 0) {
            for (City city : cityList) {
                dataList.add(city.getCityName() + ", " + city.getPinyin() + ", " + city.getProvinceOrCountryName());
            }
        }
        adapter.notifyDataSetChanged();
        listView.setSelection(0);
    }

    private void initDatabase() {
        String address;
        address = "http://mobile.weather.com.cn/js/citylist.xml";
        Log.d("ddd", "initDatabase: start");
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                result = Utility.handleCityResponse(coolWeatherDB, response);
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                closeProgressDialog();
                Log.d("ddd", "error");
            }
        });
    }
    private void queryFromServer(final String code) {
        String address;
        address = "http://wthrcdn.etouch.cn/weather_mini?citykey=" + code;
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {

            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载中。。。");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void  closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
