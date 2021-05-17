package com.example.sherwoodsuitesaigon.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Fragment.ImageDetailVuiChoiFragment;
import com.example.sherwoodsuitesaigon.Model.HaveFunPlace;
import com.example.sherwoodsuitesaigon.Network.HaveFunNetwork;
import com.example.sherwoodsuitesaigon.R;

public class ImageDetailVuiChoiAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> list;

    public ImageDetailVuiChoiAdapter(Context context, ArrayList<String> list) {
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
        View view;
        if (convertView == null ){
            view = View.inflate(parent.getContext(),R.layout.item_detail_image_vuichoi,null);
        }else view = convertView;

        String link = list.get(position);
        ImageView img = view.findViewById(R.id.imgPlaceDetail);

        Glide.with(view)
                .load(link)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(img);
        return view;
    }
}
