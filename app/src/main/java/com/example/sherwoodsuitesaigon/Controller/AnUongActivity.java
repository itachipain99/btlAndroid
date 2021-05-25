package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sherwoodsuitesaigon.Adapter.AnUongAdapter;
import com.example.sherwoodsuitesaigon.Adapter.SelectAdapter;
import com.example.sherwoodsuitesaigon.Model.AnUongModel;
import com.example.sherwoodsuitesaigon.Network.AnUongNetwork;
import com.example.sherwoodsuitesaigon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnUongActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView tbvEat,tbvDiaDiem,tbvKieuMon;
    List<String> list = new ArrayList<>();
    List<AnUongNetwork> mList = new ArrayList<>();
    ConstraintLayout clDiaDiem,clKieuMon,clNoiTieng;
    ArrayList<String> listDiaDiem = new ArrayList<>();
    ArrayList<String> listKieuMon = new ArrayList<>();
    FirebaseAuth mAuth;
    AnUongAdapter adapter;
    Boolean stateDiaDiem = false;
    Boolean stateKieuMon = false;
    TextView lblDiaDiem,lblKieuMon;
    String diaDiem = "";
    String kieuMon = "";
    LocationManager mLocationManager;
    String lon = "";
    String lat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_an_uong);
        mAuth = FirebaseAuth.getInstance();
        this.mapping();
        this.setData();
        this.setListeners();
        this.getAnUongByLocation();
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1,
                1, mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
//            lon = location.getLongitude() + "";
//            lat = location.getLatitude() + "";
        }
    };

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
        String[] diadiem =  {"Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6","Thủ Đức","Quận 12"};
        String[] kieumon =  {"Quán mì","Món nướng","Cửa hàng sandwich","Quán bia","Quán cà phê"};
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
                String url = mList.get(position).getUrl();
                Intent mapBrower = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(mapBrower);
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
                diaDiem = listDiaDiem.get(position);
                getAnUong(diaDiem,kieuMon);
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
                kieuMon = listKieuMon.get(position);
                getAnUong(diaDiem,kieuMon);
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
    private void getAnUong(String diadiem,String kieumon){
        mList.removeAll(mList);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(diadiem == "" && kieumon == "") {
            db.collection("An_uong").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            AnUongNetwork anUongNetwork = document.toObject(AnUongNetwork.class);
                            mList.add(anUongNetwork);
                        }
                        Log.d("AnUongActivity", mList.toString(), task.getException());
                        AnUongAdapter adapter = new AnUongAdapter(getApplicationContext(),mList);
                        tbvEat.setAdapter(adapter);
                    } else {
                        Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }
        else if (diadiem != "" && kieumon == "") {
            db.collection("An_uong").whereEqualTo("state",diadiem).limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            AnUongNetwork anUongNetwork = document.toObject(AnUongNetwork.class);
                            mList.add(anUongNetwork);
                        }
                        Log.d("AnUongActivity", mList.toString(), task.getException());
                        AnUongAdapter adapter = new AnUongAdapter(getApplicationContext(),mList);
                        tbvEat.setAdapter(adapter);
                    } else {
                        Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }
        else if (diadiem == "" && kieumon != "") {
            db.collection("An_uong").whereEqualTo("categoryName", kieumon).limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            AnUongNetwork anUongNetwork = document.toObject(AnUongNetwork.class);
                            mList.add(anUongNetwork);
                        }
                        Log.d("AnUongActivity", mList.toString(), task.getException());
                        AnUongAdapter adapter = new AnUongAdapter(getApplicationContext(),mList);
                        tbvEat.setAdapter(adapter);
                    } else {
                        Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }
        else  {
            db.collection("An_uong").whereEqualTo("state",diadiem).whereEqualTo("categoryName", kieumon).limit(30).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            AnUongNetwork anUongNetwork = document.toObject(AnUongNetwork.class);
                            mList.add(anUongNetwork);
                        }
                        Log.d("AnUongActivity", mList.toString(), task.getException());
                        AnUongAdapter adapter = new AnUongAdapter(getApplicationContext(),mList);
                        tbvEat.setAdapter(adapter);
                    } else {
                        Log.d("AnUongActivity", "false", task.getException());
                    }
                }
            });
        }

    }

    private void getAnUongByLocation() {
        lon = "106.6267626";
        lat = "10.7723097";
        int sys = 1;
        String url = "https://nguyenkhanhson.pythonanywhere.com/?" + "long=" + lon + "&lat=" + lat + "&sys=" + sys;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responsed) {
                try {
                    for (int i = 0 ; i < responsed.length() ; i++) {
                        JSONObject response = (JSONObject) responsed.get(i);
                        AnUongNetwork anUongNetwork = new AnUongNetwork();
                        anUongNetwork.setTitle(response.getString("title"));
                        anUongNetwork.setAddress(response.getString("address"));
                        anUongNetwork.setCategoryName(response.getString("categoryName"));
                        List<String> imageUrls = new ArrayList<String>();

                        JSONArray cast = response.getJSONArray("imageUrls");
                        for (int j = 0; j < cast.length() ; j++) {
                            String url = cast.getString(j);
                            imageUrls.add(url);
                        }
                        anUongNetwork.setImageUrls(imageUrls);
                        anUongNetwork.setPhone(response.getString("phone"));
                        anUongNetwork.setState(response.getString("state"));
                        anUongNetwork.setTotalScore(response.getDouble("totalScore"));
                        anUongNetwork.setUrl(response.getString("url"));
//
                        mList.add(anUongNetwork);
                    }

                    Log.d("listds" ,mList.toString());
                    AnUongAdapter adapter = new AnUongAdapter(getApplicationContext(),mList);
                    tbvEat.setAdapter(adapter);
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