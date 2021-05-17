package com.example.sherwoodsuitesaigon.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sherwoodsuitesaigon.Adapter.EatPlaceAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SearchAdapter;
import com.example.sherwoodsuitesaigon.Network.EatPlaceNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvSearch;
    EditText tfSearch;
    List<String> list = new ArrayList<>();
    List<EatPlaceNetwork> mList = new ArrayList<>();
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.mapping();
        this.setListeners();
    }

    private void mapping(){
        this.tbvSearch = findViewById(R.id.tbvSearch);
        this.btnBack = findViewById(R.id.btnBack);
        this.tfSearch = findViewById(R.id.tfSearch);
    }

    private void setListeners(){
        tfSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBack.setOnClickListener(V -> {
            startActivity(new Intent(SearchActivity.this,HomeActivity.class));
        });
    }

    private void setTbvSearch(String text) {


    }

}