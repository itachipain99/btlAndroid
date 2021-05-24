package com.example.sherwoodsuitesaigon.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sherwoodsuitesaigon.Network.AnUongNetwork;
import com.example.sherwoodsuitesaigon.R;

import java.util.List;

public class AnUongAdapter extends ArrayAdapter<AnUongNetwork> {

    Context context;
    List<AnUongNetwork> list;

    public AnUongAdapter(Context context, List<AnUongNetwork> object) {
        super(context,0, object);
        this.list = object;
        //Rxjava
        //ProgressBar
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View eatPlace;
        if(convertView == null) {
            eatPlace = View.inflate(parent.getContext(), R.layout.item_anuong, null);
        }else  eatPlace = convertView;

        AnUongNetwork ep = list.get(position);
        ImageView imgPlace = eatPlace.findViewById(R.id.imgEatPlace);
        TextView lblNamePlace = eatPlace.findViewById(R.id.lblNamePlace);
        TextView lblTypeFood = eatPlace.findViewById(R.id.lbltypeFood);
        TextView lblPrice = eatPlace.findViewById(R.id.lblPrice);
        TextView lblOpening = eatPlace.findViewById(R.id.lblOpening);

        TextView lblLoctionEat = eatPlace.findViewById(R.id.lblLocationEat);

        lblNamePlace.setText(ep.getTitle());
        lblTypeFood.setText(ep.getCategoryName());
        lblPrice.setText((ep.getPrice() + " vnd"));
        lblOpening.setText("10h - 21h");
        lblLoctionEat.setText(ep.getAddress());

        Glide.with(eatPlace)
                .load(ep.getImageUrls().get(0))
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(imgPlace);


        return eatPlace;
    }
}
