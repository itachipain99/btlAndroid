package com.example.sherwoodsuitesaigon.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;

import com.example.sherwoodsuitesaigon.Adapter.ImageDetailVuiChoiAdapter;
import com.example.sherwoodsuitesaigon.R;

public class ImageDetailVuiChoiFragment extends Fragment {
    GridView gvImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images_vuichoi,container,false);

        gvImage = view.findViewById(R.id.gridViewImage);

        Bundle bundle = getArguments();

        if (bundle != null ){

            Adapter adapter = new ImageDetailVuiChoiAdapter(getActivity(),bundle.getStringArrayList("Image"));
            gvImage.setAdapter((ListAdapter) adapter);
        }

        return view;
    }
}
