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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sherwoodsuitesaigon.Adapter.EatPlaceAdapter;
import com.example.sherwoodsuitesaigon.Adapter.HaveFunAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SelectAdapter;
import com.example.sherwoodsuitesaigon.Model.Detail;
import com.example.sherwoodsuitesaigon.Model.HaveFunPlace;
import com.example.sherwoodsuitesaigon.Network.EatPlaceNetwork;
import com.example.sherwoodsuitesaigon.Network.HaveFunNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VuiChoiActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvVuiChoi,tbvDiaDiem,tbvSapXep;
    List<String> list = new ArrayList<>();
    List<HaveFunNetwork> mList = new ArrayList<>();
    HaveFunAdapter adapter;
    ConstraintLayout clDiaDiem,clSapXep,clNoiTieng;
    ArrayList<String> listDiaDiem = new ArrayList<>();
    ArrayList<String> listSapXep = new ArrayList<>();
    Boolean stateDiaDiem = false;
    Boolean stateSapXep = false;
    TextView lblDiaDiem,lblSapXep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vui_choi);
        this.mapping();
        this.setListeners();
        this.setData();
        getVuiChoi();
    }


    private void mapping(){
        this.tbvVuiChoi = findViewById(R.id.tbvVuichoi);
        this.btnBack = findViewById(R.id.btnBack);
        this.tbvDiaDiem = findViewById(R.id.tbvDiaDiem);
        this.tbvSapXep = findViewById(R.id.tbvSapXep);
        this.lblDiaDiem = findViewById(R.id.lblDiaDiem);
        this.lblSapXep = findViewById(R.id.lblSapXep);
        this.clDiaDiem = findViewById(R.id.viewDiaDiem);
        this.clSapXep = findViewById(R.id.viewSapXep);
        this.clNoiTieng = findViewById(R.id.viewNoiTieng);
    }

    private void setData() {
        String[] diadiem =  {"Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6"};
        String[] sapXep =  {"1","2","3"};
        listDiaDiem = new ArrayList<String>(Arrays.asList(diadiem));
        listSapXep = new ArrayList<String>(Arrays.asList(sapXep));
        SelectAdapter adapterDiaDiem = new SelectAdapter(getApplicationContext(), listDiaDiem);
        SelectAdapter adapterKieuMon = new SelectAdapter(getApplicationContext(), listSapXep);
        tbvDiaDiem.setAdapter(adapterDiaDiem);
        tbvSapXep.setAdapter(adapterKieuMon);
    }

    public void setListeners() {
        btnBack.setOnClickListener(V -> {
            startActivity(new Intent(VuiChoiActivity.this,HomeActivity.class));
        });

        tbvVuiChoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VuiChoiActivity.this,VuiChoiDetailActivity.class);

                HaveFunNetwork haveFunNetwork = mList.get(position);
                intent.putExtra("vuichoidata", (Serializable) haveFunNetwork);

                startActivity(intent);
            }
        });
        clDiaDiem.setOnClickListener(v ->{
            this.changeColorSelected(tbvDiaDiem,tbvSapXep,clDiaDiem,clSapXep,clNoiTieng,stateDiaDiem);
            stateDiaDiem = !stateDiaDiem;
        });

        clSapXep.setOnClickListener(v-> {
            this.changeColorSelected(tbvSapXep,tbvDiaDiem,clSapXep,clDiaDiem,clNoiTieng,stateSapXep);
            stateSapXep = !stateSapXep;
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

        tbvSapXep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kieuMon = listSapXep.get(position);
                lblSapXep.setText(kieuMon);
                tbvSapXep.setVisibility(View.INVISIBLE);
                clSapXep.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_constrain_search_selected));
                stateSapXep = !stateDiaDiem;
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

    private void getVuiChoi(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("vui_choi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        HaveFunNetwork haveFunNetwork = document.toObject(HaveFunNetwork.class);
                        mList.add(haveFunNetwork);
                    }
//                    Log.d("AnUongActivity", mList.toString(), task.getException());
                    HaveFunAdapter adapter = new HaveFunAdapter(getApplicationContext(),mList);
                    tbvVuiChoi.setAdapter(adapter);
                } else {
//                    Log.d("AnUongActivity", "false", task.getException());
                }
            }
        });
    }

}