package com.berangerelatouche.geoskate;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingFragment extends Fragment {
    private static final int FRAME_W = 127;
    // frame height
    private static final int FRAME_H = 160;
    // number of frames
    private static final int NB_FRAMES = 6;
    // nb of frames in x
    private static final int COUNT_X = 6;

    // frame duration
    // we can slow animation by changing frame duration
    private static final int FRAME_DURATION = 200; // in ms !
    // scale factor for each frame
    private static final int SCALE_FACTOR = 5;
    private ImageView img;
    // stores each frame
    private Bitmap[] bmps;


    public LoadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_loading, container, false);
        img = v.findViewById(R.id.spriteSkater);


        // load bitmap from assets
        Bitmap birdBmp = null;
        try {
            birdBmp = getBitmapFromAssets("image/spriteskate.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (birdBmp != null) {
            // cut bitmaps from bird bmp to array of bitmaps
            bmps = new Bitmap[NB_FRAMES];
            int currentFrame = 0;

            for (int j = 0; j < COUNT_X; j++) {
                bmps[currentFrame] = Bitmap.createBitmap(birdBmp, FRAME_W * j, 0, FRAME_W, FRAME_H);

                // apply scale factor
                bmps[currentFrame] = Bitmap.createScaledBitmap(
                        bmps[currentFrame], FRAME_W * SCALE_FACTOR, FRAME_H
                                * SCALE_FACTOR, true);

                if (++currentFrame >= NB_FRAMES) {
                    break;
                }
            }
            // create animation programmatically
            final AnimationDrawable animation = new AnimationDrawable();
            animation.setOneShot(false); // repeat animation

            for (int i = 0; i < NB_FRAMES; i++) {
                animation.addFrame(new BitmapDrawable(getResources(), bmps[i]),
                        FRAME_DURATION);
            }
            img.setImageBitmap(null);
            img.setBackground(animation);


            // start animation on image
            img.post(new Runnable() {
                @Override
                public void run() {
                    animation.start();
                }

            });

        }
        return v;
    }

    private Bitmap getBitmapFromAssets(String filepath) throws IOException {
        AssetManager assetManager = getActivity().getResources().getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        for (String s :
                assetManager.list("image")) {
            System.out.println(s);
        }
        try {
            istr = assetManager.open(filepath);
            bitmap = BitmapFactory.decodeStream(istr);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // manage exception
        } finally {
            if (istr != null) {
                try {
                    istr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
}
