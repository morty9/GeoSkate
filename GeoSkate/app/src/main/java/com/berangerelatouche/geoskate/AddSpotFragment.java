package com.berangerelatouche.geoskate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;


public class AddSpotFragment extends Fragment {


    EditText spotName, spotDesc;
    ImageView imageView;
    Button buttonSave;
    ToggleButton current, address, picture;

    public AddSpotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_spot, container, false);
        spotDesc = (EditText) v.findViewById(R.id.edit_desc);
        spotName = v.findViewById(R.id.edit_name);
        current = (v.findViewById(R.id.but_use_current_pos));
        picture = (ToggleButton) v.findViewById(R.id.but_take_pic);
        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture.setChecked(true);
                ((HomeActivity) getActivity()).dispatchTakePictureIntent(view);
            }
        });
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.setChecked(false);
            }
        });
        address = v.findViewById(R.id.but_add_by_address);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).addPlace(view);
                current.setChecked(false);
            }
        });
        buttonSave = v.findViewById(R.id.save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity) getActivity()).getInfoFromAdd(spotName.getText().toString(),
                        spotDesc.getText().toString());
            }
        });

        return v;
    }


}
