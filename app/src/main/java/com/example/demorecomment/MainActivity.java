package com.example.demorecomment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView btnBack;
    ListView tbvSearch;
    EditText tfSearch;
    Button btnTimKiem;
    List<String> list = new ArrayList<>();
    List<SearchModel> mList = new ArrayList<>();
    SearchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int id =  getIntent().getIntExtra("id", 0);
        this.mapping();

        btnTimKiem.setOnClickListener(v -> {
            setTbvSearch(tfSearch.getText().toString(),id);
        } );
    }

    private void mapping(){
        this.tbvSearch = findViewById(R.id.tbvSearch);
        this.btnBack = findViewById(R.id.btnBack);
        this.tfSearch = findViewById(R.id.tfSearch);
        this.btnTimKiem = findViewById(R.id.btnTimkiem);
    }

    private void setTbvSearch(String text,int id ) {
        String url = "";
        switch (id) {
            case 0:
                url = "https://nguyenkhanhson.pythonanywhere.com/?sys=1&id=" + text +"&opt=0";
            case 1 :
                url = "https ://nguyenkhanhson.pythonanywhere.com/?sys=2&movie_names=" + text;
        }
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responsed) {
                try {
                    for (int i = 0 ; i < responsed.length() ; i++) {
                        JSONObject response = (JSONObject) responsed.get(i);
                        SearchModel sm = new SearchModel();
                        sm.setTitle(response.getString("Movie_name"));
                        mList.add(sm);
                    }

                    Log.d("listds" ,mList.toString());
                    SearchAdapter adapter = new SearchAdapter(getApplicationContext(),mList);
                    tbvSearch.setAdapter(adapter);
                }
                catch (JSONException e ){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("listd" , error.toString());
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}