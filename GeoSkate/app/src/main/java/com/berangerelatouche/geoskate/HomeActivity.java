package com.berangerelatouche.geoskate;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by berangerelatouche on 25/06/2017.
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public UiSettings uiSettings;
    public Mylocation mylocation;
    public Marker marker;
    public MarkerOptions markerOptions;
    public Location location;
    public ImageButton add;
    public ImageButton search;
    public ImageButton settings;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#243744"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        add = (ImageButton) findViewById(R.id.addButton);
        search = (ImageButton) findViewById(R.id.searchButton);
        settings = (ImageButton) findViewById(R.id.settingsButton);

        mylocation = new Mylocation(getApplicationContext());
        location = mylocation.getLocation();
        lat = location.getLatitude();
        lng = location.getLongitude();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, Toolbar_Fragment.newInstance())
                .commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);

        LatLng mylocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(mylocation).draggable(true).title("You are here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 13));

        toolBarFunctions();
    }

    public void toolBarFunctions() {

        add.ad

    }

}
