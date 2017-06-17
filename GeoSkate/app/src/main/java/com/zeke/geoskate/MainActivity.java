package com.zeke.geoskate;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    RelativeLayout loadLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_loading);
        setContentView(R.layout.activity_main);

        /*loadLayout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_loading, null);
        TextView loadTitle = (TextView) loadLayout.findViewById(R.id.textTitle);
        Typeface typeTxt = Typeface.createFromAsset(getAssets(), "font/AmaticSC-Regular.ttf");
        loadTitle.setTypeface(typeTxt);*/
    }
}
