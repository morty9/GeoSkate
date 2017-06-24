package com.zeke.geoskate;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    //private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_loading);
        TextView title = (TextView) findViewById(R.id.textTitle);
        TextView subtitle = (TextView) findViewById(R.id.textSubTitle);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/AmaticSC-Regular.ttf");

        title.setTypeface(typeface);
        subtitle.setTypeface(typeface);

        //TO FIX
        /*TranslateAnimation animation = new TranslateAnimation(0.0f, 0.50f, 0.0f, 0.0f);
        animation.setDuration(700);
        animation.setRepeatCount(3);
        animation.setRepeatMode(2);
        animation.setFillAfter(true);

        ImageView spriteSkater = (ImageView) findViewById(R.id.spriteSkater);
        spriteSkater.startAnimation(animation);*/

    }


}
