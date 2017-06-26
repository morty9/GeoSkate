package com.berangerelatouche.geoskate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by berangerelatouche on 25/06/2017.
 */

public class Toolbar_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.toolbar_fragment, container, false);
    }

    public static Toolbar_Fragment newInstance() {
        
        Bundle args = new Bundle();
        
        Toolbar_Fragment fragment = new Toolbar_Fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
