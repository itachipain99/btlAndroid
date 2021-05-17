package com.example.sherwoodsuitesaigon.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

public class HomeActivity extends AppCompatActivity {

    ConstraintLayout clVuiChoi;
    ConstraintLayout clAnUong;
    ConstraintLayout clTimKiem;
    TextView lblDegree,lblStateWeather,lblTimeUpdate,lblRemindUser;
    ImageView imgWeather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.mapping();
        this.getWeatherDetail();
        setListeners();
    }

    private void setListeners() {
        clAnUong.setOnClickListener(v ->{
            startActivity(new Intent(HomeActivity.this,AnUongActivity.class));
        });

        clVuiChoi.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this,VuiChoiActivity.class));
        });

        clTimKiem.setOnClickListener(v ->{
            startActivity(new Intent(HomeActivity.this, SearchActivity.class));
        });
    }

    private void mapping() {
        clAnUong = findViewById(R.id.viewAnUong);
        clVuiChoi = findViewById(R.id.viewVuiChoi);
        clTimKiem = findViewById(R.id.viewTimKiem);
        lblDegree = findViewById(R.id.lblDegree);
        lblStateWeather = findViewById(R.id.lblStateWeather);
        lblRemindUser = findViewById(R.id.lblRemindUser);
        lblTimeUpdate = findViewById(R.id.lblTimeUpdate);
        imgWeather = findViewById(R.id.imgWeather);
    }

    private void getWeatherDetail() {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=b1f6b6e5acd4373c85a79cecf0436f8d&fbclid=IwAR1BSbdrLga3wN8Ful_oSRy17SSt-WYy1sWI10qldb4bIUy7IoVnHOZaRfc";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("weather" , response.toString());
                    JSONObject mainObject = response.getJSONObject("main");
                    String temp = String.valueOf(mainObject.getDouble("temp"));


                    double temp_int = Double.parseDouble(temp) - 273.15;
                    double centi = Math.round(temp_int);
                    int degree = (int)centi;

                    JSONArray arrayWeather = response.getJSONArray("weather");
                    JSONObject weatherJSONObject = arrayWeather.getJSONObject(0);
                    String mainWeather = String.valueOf(weatherJSONObject.getString("main"));
                    String description = weatherJSONObject.getString("description");
                    String city = response.getString("name");
                    String iconWeather = weatherJSONObject.getString("icon");

                    setIconWeather(iconWeather);
                    remindUser(mainWeather);
                    lblDegree.setText("" + degree + "\u00B0");

                }
                catch (JSONException e ){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("weather" , error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);


    }

    private void remindUser(String weatherDescription) {
        String remind = "";
        String descriptionVN = "";
        switch (weatherDescription) {
            case "Clear":
                descriptionVN = "Bầu trời quang đãng";
                remind = "Ngày đẹp trời, chúc bạn vui vẻ nhé";
                break;
            case "Clouds":
                descriptionVN = "Vài nơi có mây";
                remind = "Ngày đẹp trời, chúc bạn vui vẻ nhé";
                break;
            case "Rain":
                descriptionVN = "Trời có mưa rào";
                remind = "Nhớ đem theo ô hoặc áo mưa nhé";
                break;
            case "Thunderstorm":
                descriptionVN = "Có sấm chớp";
                remind = "Chú ý khi ra khỏi nhà nhé";
                break;
            default:
                descriptionVN = "Bầu trời quang đãng";
                remind = "Ngày đẹp trời, chúc bạn vui vẻ nhé";
                break;
        }
        lblRemindUser.setText(remind);
        lblStateWeather.setText(descriptionVN);
    }

    private void setIconWeather(String icon) {
        String url = "https://openweathermap.org/img/wn/" + icon + "@2x.png";
        Glide.with(this)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imgWeather);
    }



}