package com.berangerelatouche.geoskate;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpotFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SPOT_NAME = "spotName";


    private String spotName;
    private SharedPrefferenceManager sharedPrefferenceManager;


    public SpotFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param spotName String corresponding to the spot name.
     * @return A new instance of fragment SpotFragment.
     */

    public static SpotFragment newInstance(String spotName) {
        SpotFragment fragment = new SpotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SPOT_NAME, spotName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            spotName = getArguments().getString(ARG_SPOT_NAME);
        }
        sharedPrefferenceManager = SharedPrefferenceManager.getInstance();
        sharedPrefferenceManager.setContext(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_spot, container, false);
        ((TextView) v.findViewById(R.id.spot_name)).setText(spotName);
        ((TextView) v.findViewById(R.id.spot_desc)).setText(sharedPrefferenceManager.getDesc(spotName));
        String path = sharedPrefferenceManager.getPath(spotName);
        if (path != null && !path.contentEquals(""))
            ((ImageView) v.findViewById(R.id.spot_pic)).setImageBitmap(
                    BitmapFactory.decodeFile(path));

        return v;
    }


}
