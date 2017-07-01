package com.berangerelatouche.geoskate;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);


        /*TextView title = (TextView) findViewById(R.id.textTitle);
        TextView subtitle = (TextView) findViewById(R.id.textSubTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/AmaticSC-Regular.ttf");

        title.setTypeface(typeface);
        subtitle.setTypeface(typeface);*/
    }
}
