package com.berangerelatouche.geoskate;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by berangerelatouche on 25/06/2017.
 */

public class Toolbar_Fragment extends Fragment {

    public ImageButton add;
    public ImageButton search;
    public ImageButton settings;
    public HomeActivity homeData;
    public LatLng adding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.toolbar_fragment, container, false);

        add = view.findViewById(R.id.addButton);

        //toolBarFunctions();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //toolBarFunctions();
    }

    public static Toolbar_Fragment newInstance() {

        Bundle args = new Bundle();

        Toolbar_Fragment fragment = new Toolbar_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

}
