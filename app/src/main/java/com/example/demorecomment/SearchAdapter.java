package com.example.demorecomment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.demorecomment.R;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends BaseAdapter {
    Context context;
    List<SearchModel> list;

    public SearchAdapter(Context context, List<SearchModel> list) {
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

//        Glide.with(view)
//                .load(sm.getImageUrl())
//                .placeholder(R.mipmap.ic_launcher)
//                .centerCrop()
//                .into(imgSearch);
        imgSearch.setImageResource(R.drawable.example_image);

        lblTitle.setText(sm.getTitle());


        return view;
    }
}
