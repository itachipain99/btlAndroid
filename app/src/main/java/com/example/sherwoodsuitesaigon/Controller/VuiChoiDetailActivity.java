package com.example.sherwoodsuitesaigon.Controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.tv.TvContract;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Fragment.DetailVuiChoiFragment;
import com.example.sherwoodsuitesaigon.Fragment.ImageDetailVuiChoiFragment;
import com.example.sherwoodsuitesaigon.Fragment.IntroductionVuiChoiFragment;
import com.example.sherwoodsuitesaigon.Model.Detail;
import com.example.sherwoodsuitesaigon.Network.EatPlaceNetwork;
import com.example.sherwoodsuitesaigon.Network.HaveFunNetwork;
import com.example.sherwoodsuitesaigon.R;

import java.io.Serializable;
import java.util.ArrayList;

public class VuiChoiDetailActivity extends AppCompatActivity {

    TextView lblIntroduct,lblDetail,lblImage;
    TextView lblNamePlace,lblLocation;
    ImageView imgMap,btnBack,imgHeader;
    ConstraintLayout constraintLayoutIntroduct;
    View viewIntroduct,viewDetail,viewImage;
    EatPlaceNetwork haveFunNetwork;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vui_choi_detail);
        this.getDataFromVuiChoi();
        this.mapping();
        this.setDataUI();
        View view = constraintLayoutIntroduct;
        this.switchFragment(view);
        this.setListeners();
    }

    private void getDataFromVuiChoi() {
        Intent intent = getIntent();
        haveFunNetwork = (EatPlaceNetwork) intent.getSerializableExtra("vuichoidata");
    }


    private void mapping (){
        constraintLayoutIntroduct = findViewById(R.id.contrainlayoutIntroduct);
        lblIntroduct = findViewById(R.id.lblIntroduct);
        lblDetail = findViewById(R.id.lblDetail);
        lblImage = findViewById(R.id.lblImage);
        viewIntroduct = findViewById(R.id.viewIntroduct);
        viewDetail = findViewById(R.id.viewDetail);
        viewImage = findViewById(R.id.viewImage);
        lblNamePlace = findViewById(R.id.lblNamePlace);
        lblLocation = findViewById(R.id.lblLocation);
        imgMap = findViewById(R.id.imgMap);
        imgHeader = findViewById(R.id.imgHeader);
        btnBack = findViewById(R.id.btnBack);
    }

    private void setDataUI() {
        lblNamePlace.setText(haveFunNetwork.getTitle());
        lblLocation.setText(haveFunNetwork.getAddress());
        Glide.with(this)
                .load(haveFunNetwork.getImageUrls().get(0))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imgHeader);
    }

    public void setListeners() {
        btnBack.setOnClickListener(V -> {
            startActivity(new Intent(VuiChoiDetailActivity.this, AnUongActivity.class));
        });
    }

    public void switchFragment(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        Bundle bundle = new Bundle() ;
        switch (view.getId()) {

            case R.id.contrainlayoutIntroduct:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblIntroduct.setTextAppearance(getApplicationContext(),R.style.boldText);
                    lblDetail.setTextAppearance(getApplicationContext(),R.style.normalText);
                    lblImage.setTextAppearance(getApplicationContext(),R.style.normalText);
                }
                viewIntroduct.setBackgroundColor(0x80125FBA);
                viewDetail.setBackgroundColor(0xFFFFFFFF);
                viewImage.setBackgroundColor(0xFFFFFFFF);
                fragment = new IntroductionVuiChoiFragment();
                bundle.putString("Introduct",haveFunNetwork.getDescription());
                break;
            case R.id.contrainlayoutDetail :
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblIntroduct.setTextAppearance(getApplicationContext(),R.style.normalText);
                    lblDetail.setTextAppearance(getApplicationContext(),R.style.boldText);
                    lblImage.setTextAppearance(getApplicationContext(),R.style.normalText);
                }
                viewDetail.setBackgroundColor(0x80125FBA);
                viewImage.setBackgroundColor(0xFFFFFFFF);
                viewIntroduct.setBackgroundColor(0xFFFFFFFF);

                Detail detail = new Detail(haveFunNetwork.getPrice(),"10h-20h","Khong co");
                bundle.putSerializable("Detail",(Serializable) detail);

                fragment = new DetailVuiChoiFragment();
                break;
            case R.id.contrainlayoutImage :
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    lblIntroduct.setTextAppearance(getApplicationContext(),R.style.normalText);
                    lblDetail.setTextAppearance(getApplicationContext(),R.style.normalText);
                    lblImage.setTextAppearance(getApplicationContext(),R.style.boldText);
                }
                viewImage.setBackgroundColor(0x80125FBA);
                viewDetail.setBackgroundColor(0xFFFFFFFF);
                viewIntroduct.setBackgroundColor(0xFFFFFFFF);
                bundle.putStringArrayList("Image",(ArrayList<String>) haveFunNetwork.getImageUrls());
                fragment = new ImageDetailVuiChoiFragment();
                break;
        }
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameContent,fragment);
        fragmentTransaction.commit();
    }





}