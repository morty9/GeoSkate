package com.berangerelatouche.geoskate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thomas.painsurget on 28/06/2017.
 */

public class Gplaces_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.googleplaces_fragment, container, false);
    }

    public static Gplaces_Fragment newInstance() {

        Bundle args = new Bundle();

        Gplaces_Fragment fragment = new Gplaces_Fragment();
        fragment.setArguments(args);
        return fragment;
    }



}
