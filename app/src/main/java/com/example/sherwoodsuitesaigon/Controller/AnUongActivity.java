package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sherwoodsuitesaigon.Adapter.EatPlaceAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SelectAdapter;
import com.example.sherwoodsuitesaigon.Model.EatPlace;
import com.example.sherwoodsuitesaigon.Network.EatPlaceNetwork;
import com.example.sherwoodsuitesaigon.Network.HaveFunNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.opencensus.trace.Tracestate;

public class AnUongActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvEat,tbvDiaDiem,tbvKieuMon;
    List<String> list = new ArrayList<>();
    List<EatPlaceNetwork> mList = new ArrayList<>();
    ConstraintLayout clDiaDiem,clKieuMon,clNoiTieng;
    ArrayList<String> listDiaDiem = new ArrayList<>();
    ArrayList<String> listKieuMon = new ArrayList<>();
    FirebaseAuth mAuth;
    EatPlaceAdapter adapter;
    Boolean stateDiaDiem = false;
    Boolean stateKieuMon = false;
    TextView lblDiaDiem,lblKieuMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_uong);
        mAuth = FirebaseAuth.getInstance();
        this.mapping();
        this.setData();
        this.setListeners();
        this.getAnUong();
    }

    private void mapping(){
        this.btnBack = findViewById(R.id.btnBack);
        this.tbvEat = findViewById(R.id.tbvEat);
        this.tbvDiaDiem = findViewById(R.id.tbvDiaDiem);
        this.tbvKieuMon = findViewById(R.id.tbvKieuMon);
        this.lblDiaDiem = findViewById(R.id.lblDiaDiem);
        this.lblKieuMon = findViewById(R.id.lblKieuMon);
        this.clDiaDiem = findViewById(R.id.viewDiaDiem);
        this.clKieuMon = findViewById(R.id.viewKieuMon);
        this.clNoiTieng = findViewById(R.id.viewNoiTieng);
    }

    private void setData() {
        String[] diadiem =  {"Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6"};
        String[] kieumon =  {"1","2","3"};
        listDiaDiem = new ArrayList<String>(Arrays.asList(diadiem));
        listKieuMon = new ArrayList<String>(Arrays.asList(kieumon));
        SelectAdapter adapterDiaDiem = new SelectAdapter(getApplicationContext(), listDiaDiem);
        SelectAdapter adapterKieuMon = new SelectAdapter(getApplicationContext(), listKieuMon);
        tbvDiaDiem.setAdapter(adapterDiaDiem);
        tbvKieuMon.setAdapter(adapterKieuMon);
    }

    public void setListeners() {
        btnBack.setOnClickListener(V -> {
            startActivity(new Intent(AnUongActivity.this,HomeActivity.class));
        });

        tbvEat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AnUongActivity.this,VuiChoiDetailActivity.class);

                EatPlaceNetwork haveFunNetwork = mList.get(position);
                intent.putExtra("vuichoidata", (Serializable) haveFunNetwork);

                startActivity(intent);
            }
        });

        clDiaDiem.setOnClickListener(v ->{
            this.changeColorSelected(tbvDiaDiem,tbvKieuMon,clDiaDiem,clKieuMon,clNoiTieng,stateDiaDiem);
            stateDiaDiem = !stateDiaDiem;
        });

        clKieuMon.setOnClickListener(v-> {
            this.changeColorSelected(tbvKieuMon,tbvDiaDiem,clKieuMon,clDiaDiem,clNoiTieng,stateKieuMon);
            stateKieuMon = !stateKieuMon;
        });

        tbvDiaDiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String diaDiem = listDiaDiem.get(position);
                lblDiaDiem.setText(diaDiem);
                tbvDiaDiem.setVisibility(View.INVISIBLE);
                clDiaDiem.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search_selected));
                stateDiaDiem = !stateDiaDiem;
            }
        });

        tbvKieuMon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kieuMon = listKieuMon.get(position);
                lblKieuMon.setText(kieuMon);
                tbvKieuMon.setVisibility(View.INVISIBLE);
                clKieuMon.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search_selected));
                stateKieuMon = !stateDiaDiem;
            }
        });
    }


    public void changeColorSelected(ListView listView1,ListView listView2, ConstraintLayout constraintLayout1,ConstraintLayout constraintLayout2,ConstraintLayout constraintLayout3,Boolean state) {
        constraintLayout2.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search));
        constraintLayout3.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search));
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(state) {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                constraintLayout1.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search_selected) );
            } else {
                constraintLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search_selected));
            }
            listView1.setVisibility(View.INVISIBLE);
        }
        else {
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                constraintLayout1.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search) );
            } else {
                constraintLayout1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search));
            }
            listView1.setVisibility(View.VISIBLE);
        }
        listView2.setVisibility(View.INVISIBLE);
    }
    private void getAnUong(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("An_uong").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        EatPlaceNetwork eatPlaceNetwork = document.toObject(EatPlaceNetwork.class);
                        mList.add(eatPlaceNetwork);
                    }
                    Log.d("AnUongActivity", mList.toString(), task.getException());
                    EatPlaceAdapter adapter = new EatPlaceAdapter(getApplicationContext(),mList);
                    tbvEat.setAdapter(adapter);
                } else {
                    Log.d("AnUongActivity", "false", task.getException());
                }
            }
        });

    }


}