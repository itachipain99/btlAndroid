package com.example.sherwoodsuitesaigon.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Model.SearchModel;
import com.example.sherwoodsuitesaigon.R;

import java.util.ArrayList;

public class SelectAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;

    public SelectAdapter(Context context, ArrayList<String> list) {
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
            view = View.inflate(parent.getContext(), R.layout.item_select,null);
        }else view = convertView;

        String text = list.get(position);

        TextView lblTitleSelect = view.findViewById(R.id.lblTitleSeclect);

        lblTitleSelect.setText(text);


        return view;
    }


}