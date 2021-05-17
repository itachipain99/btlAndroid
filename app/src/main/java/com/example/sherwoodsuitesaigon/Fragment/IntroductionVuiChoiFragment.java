package com.example.sherwoodsuitesaigon.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sherwoodsuitesaigon.R;

public class IntroductionVuiChoiFragment extends Fragment {

    TextView lblIntroduct;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_introduct_vuichoi,container,false);
        lblIntroduct = view.findViewById(R.id.lblIntroduct);
        Bundle bundle = getArguments();

        if (bundle != null ){
            lblIntroduct.setText(bundle.getString("Introduct"));
        }

        return view;
    }
}
