package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.sherwoodsuitesaigon.Adapter.AnUongAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SearchAdapter;
import com.example.sherwoodsuitesaigon.Adapter.VuiChoiAdapter;
import com.example.sherwoodsuitesaigon.Network.AnUongNetwork;
import com.example.sherwoodsuitesaigon.Network.VuiChoiNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvSearch;
    EditText tfSearch;
    List<String> list = new ArrayList<>();
    List<VuiChoiNetwork> mList = new ArrayList<>();
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
                String text = s.subSequence(start, start+count).toString();
                setTbvSearch(text);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnBack.setOnClickListener(V -> {
            startActivity(new Intent(SearchActivity.this,HomeActivity.class));
        });

        tbvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,VuiChoiDetailActivity.class);

                VuiChoiNetwork vuichoiNetwork = mList.get(position);
                intent.putExtra("vuichoidata", (Serializable) vuichoiNetwork);

                startActivity(intent);
            }
        });
    }

    private void setTbvSearch(String text) {
        mList.removeAll(mList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vui_choi").orderBy("title").startAt(text).endAt(text + "\uf8ff").limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        VuiChoiNetwork vuiChoiNetwork = document.toObject(VuiChoiNetwork.class);
                        mList.add(vuiChoiNetwork);
                    }
                    VuiChoiAdapter adapter = new VuiChoiAdapter(getApplicationContext(),mList);
                    tbvSearch.setAdapter(adapter);
                } else {
                    Log.d("AnUongActivity", "false", task.getException());
                }
            }
        });

    }

}