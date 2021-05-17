package com.example.sherwoodsuitesaigon.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.sherwoodsuitesaigon.Model.Detail;
import com.example.sherwoodsuitesaigon.R;


public class DetailVuiChoiFragment extends Fragment {

    TextView lblOpening,lblPrice,lblAtention;
    Detail detail;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_detail_vuichoi,container,false);

        lblOpening = view.findViewById(R.id.lblOpening);
        lblPrice = view.findViewById(R.id.lblPrice);
        lblAtention = view.findViewById(R.id.lblAtention);

        Bundle bundle = getArguments();

        if(bundle != null ){
            detail = (Detail) bundle.getSerializable("Detail");
            lblOpening.setText(detail.getOpentime());
            lblPrice.setText(detail.getTicketPrice() + "vnd");
            lblAtention.setText(detail.getAttention());
        }

        return view;
    }
}
