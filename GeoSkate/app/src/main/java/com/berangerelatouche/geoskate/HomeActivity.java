package com.berangerelatouche.geoskate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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
    public LatLng adding;
    double lat, lng;
    public Gplaces_Fragment fragment;

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

        getSupportFragmentManager().beginTransaction().add(R.id.gplaces, Gplaces_Fragment.newInstance()).commit();

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
        this.marker = mMap.addMarker(new MarkerOptions().position(mylocation).draggable(true).title("You are here"));
        System.out.println("MARKER POSITION" + marker.getPosition());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 13));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
        });

    }

    /*
     * In this method, Start PlaceAutocomplete activity
     * PlaceAutocomplete activity provides a -
     * search box to search Google places
     */
    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, 1);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                ((TextView) findViewById(R.id.searched_address))
                        .setText(place.getName()+",\n"+
                                place.getAddress() +"\n" + place.getPhoneNumber());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void addCoordinate(View view) {

        System.out.println("TEST BUTTON");
        if (marker.getPosition() != null) {
            double latitude = marker.getPosition().latitude;
            double longitude = marker.getPosition().longitude;
            adding = new LatLng(latitude, longitude);
        } else {
            System.out.println("Error");
        }

        System.out.println("ADDING LOCATION" + adding);
    }

    public void settingsTouched(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
