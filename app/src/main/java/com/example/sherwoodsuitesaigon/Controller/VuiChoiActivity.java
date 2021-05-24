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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.sherwoodsuitesaigon.Adapter.AnUongAdapter;
import com.example.sherwoodsuitesaigon.Adapter.VuiChoiAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SelectAdapter;
import com.example.sherwoodsuitesaigon.Network.AnUongNetwork;
import com.example.sherwoodsuitesaigon.Network.VuiChoiNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VuiChoiActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvVuiChoi,tbvDiaDiem,tbvSapXep;
    List<String> list = new ArrayList<>();
    List<VuiChoiNetwork> mList = new ArrayList<>();
    VuiChoiAdapter adapter;
    ConstraintLayout clDiaDiem,clSapXep,clNoiTieng;
    ArrayList<String> listDiaDiem = new ArrayList<>();
    ArrayList<String> listSapXep = new ArrayList<>();
    Boolean stateDiaDiem = false;
    Boolean stateSapXep = false;
    TextView lblDiaDiem,lblSapXep;
    String diaDiem = "";
    String sapXep = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vui_choi);
        this.mapping();
        this.setListeners();
        this.setData();
        getVuiChoiByLocation();
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
        String[] diadiem = {"Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6","Thủ Đức","Quận 12"};
        String[] sapXep = {"Nổi tiếng","Gần bạn"};
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

                VuiChoiNetwork vuiChoiNetwork = mList.get(position);
                intent.putExtra("vuichoidata", (Serializable) vuiChoiNetwork);

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
                diaDiem = listDiaDiem.get(position);
                getVuiChoi(diaDiem,sapXep);
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
                sapXep = listSapXep.get(position);
                getVuiChoi(diaDiem,sapXep);
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

    private void getVuiChoi(String diadiem,String sapxep){
        mList.removeAll(mList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(diadiem == "" && sapxep == "") {
            db.collection("vui_choi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            VuiChoiNetwork vuiChoiNetwork = document.toObject(VuiChoiNetwork.class);
                            mList.add(vuiChoiNetwork);
                        }
//                    Log.d("AnUongActivity", mList.toString(), task.getException());
                        VuiChoiAdapter adapter = new VuiChoiAdapter(getApplicationContext(),mList);
                        tbvVuiChoi.setAdapter(adapter);
                    } else {
//                    Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }
        else if (diadiem != "" && sapxep == "") {
            db.collection("vui_choi").whereEqualTo("state",diadiem).limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            VuiChoiNetwork vuiChoiNetwork = document.toObject(VuiChoiNetwork.class);
                            mList.add(vuiChoiNetwork);
                        }
                        VuiChoiAdapter adapter = new VuiChoiAdapter(getApplicationContext(),mList);
                        tbvVuiChoi.setAdapter(adapter);
                    } else {
                    }
                }
            });
        }
        else if (diadiem == "" && sapxep != "") {
            this.getVuiChoiByLocation();
        }
        else  {
            db.collection("vui_choi").whereEqualTo("state",diadiem).limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            VuiChoiNetwork vuiChoiNetwork = document.toObject(VuiChoiNetwork.class);
                            mList.add(vuiChoiNetwork);
                        }
//                    Log.d("AnUongActivity", mList.toString(), task.getException());
                        VuiChoiAdapter adapter = new VuiChoiAdapter(getApplicationContext(),mList);
                        tbvVuiChoi.setAdapter(adapter);
                    } else {
//                    Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }
    }

    private void getVuiChoiByLocation() {
        String lon = "106.6267626";
        String lat = "10.7723097";
        int sys = 1;
        String url = "https://nguyenkhanhson.pythonanywhere.com/?" + "long=" + lon + "&lat=" + lat + "&sys=" + sys;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responsed) {
                try {
                    for (int i = 0 ; i < responsed.length() ; i++) {
                        JSONObject response = (JSONObject) responsed.get(i);
                        VuiChoiNetwork vuiChoiNetwork = new VuiChoiNetwork();
                        vuiChoiNetwork.setTitle(response.getString("title"));
                        vuiChoiNetwork.setAddress(response.getString("address"));
                        vuiChoiNetwork.setCategoryName(response.getString("categoryName"));
                        ArrayList<String> imageUrls = new ArrayList<String>();

                        JSONArray cast = response.getJSONArray("imageUrls");
                        for (int j = 0; j < cast.length() ; j++) {
                            String url = cast.getString(j);
                            imageUrls.add(url);
                        }
                        vuiChoiNetwork.setImageUrls(imageUrls);
                        vuiChoiNetwork.setPhone(response.getString("phone"));
//                        vuiChoiNetwork.setState(response.getString("state"));
                        vuiChoiNetwork.setTotalScore(response.getDouble("totalScore"));
                        vuiChoiNetwork.setUrl(response.getString("url"));
//
                        mList.add(vuiChoiNetwork);
                    }

                    Log.d("listds" ,mList.toString());
                    VuiChoiAdapter adapter = new VuiChoiAdapter(getApplicationContext(),mList);
                    tbvVuiChoi.setAdapter(adapter);
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