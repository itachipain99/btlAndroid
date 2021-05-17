package com.example.sherwoodsuitesaigon.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Model.SearchModel;
import com.example.sherwoodsuitesaigon.R;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
    Context context;
    ArrayList<SearchModel> list;

    public SearchAdapter(Context context, ArrayList<SearchModel> list) {
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
        if(convertView == null ) {
            view = View.inflate(parent.getContext(), R.layout.item_search,null);
        }else view = convertView;

        SearchModel sm = list.get(position);

        TextView lblTitle = view.findViewById(R.id.lblTitle);
        ImageView imgSearch = view.findViewById(R.id.imgSearch);

        Glide.with(view)
                .load(sm.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imgSearch);

        lblTitle.setText(sm.getTitle());


        return view;
    }
}
