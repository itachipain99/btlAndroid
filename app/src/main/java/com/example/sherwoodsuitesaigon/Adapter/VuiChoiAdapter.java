package com.example.sherwoodsuitesaigon.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Network.VuiChoiNetwork;
import com.example.sherwoodsuitesaigon.R;

import java.util.List;

public class VuiChoiAdapter extends BaseAdapter {

    Context context;
    List<VuiChoiNetwork> list;

    public VuiChoiAdapter(Context context, List<VuiChoiNetwork> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewPlace;
        if(convertView == null){
            viewPlace = View.inflate(parent.getContext(), R.layout.item_vuichoi, null);
        } else viewPlace = convertView;
        VuiChoiNetwork hfp = list.get(position);
        ImageView imgPlace = viewPlace.findViewById(R.id.imgPlace);
        TextView lblNamePlace = viewPlace.findViewById(R.id.lblNamePlace);
        TextView lblLoction = viewPlace.findViewById(R.id.lblLoction);
//        Log.d("AppLog",hfp.getImageList().get(0));
            Glide.with(viewPlace)
                    .load(hfp.getImageUrls().get(0))
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(imgPlace);


        lblNamePlace.setText(hfp.getTitle());
        lblLoction.setText(hfp.getAddress());
        return viewPlace;
    }
}
