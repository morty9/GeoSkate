package com.berangerelatouche.geoskate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by berangerelatouche on 25/06/2017.
 */

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_ADD_PLACE = 3;
    public GoogleMap mMap;
    public UiSettings uiSettings;
    public Mylocation mylocation;
    public Marker marker;
    public Location location;
    public ImageButton add;
    public ImageButton search;
    public ImageButton settings;
    public LatLng adding;
    double lat, lng;
    private FrameLayout frameLayout;
    private LoadingFragment loadingFragment;
    private final int WAITING_TIME = 5000;
    static final int REQUEST_FIND_PLACE = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    final Handler handler = new Handler();
    String spotPhotoPath;

    LatLng spotLocation;
    String spotName;
    String spotDesc;
    boolean currentLocation = true;
    static final int REQUEST_TAKE_PHOTO = 2;

    SharedPrefferenceManager sharedPrefferenceManager;
    AddSpotFragment addSpotFragment;
    SpotFragment spotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    android.Manifest.permission.ACCESS_FINE_LOCATION, true);
        }

        frameLayout = (FrameLayout) findViewById(R.id.fragment_contenair);
        loadingFragment = new LoadingFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_contenair, loadingFragment)
                .commit();
        frameLayout.setVisibility(View.VISIBLE);
        sharedPrefferenceManager = SharedPrefferenceManager.getInstance();
        sharedPrefferenceManager.setContext(this);

        GoogleApiClient mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#243744"));
        getSupportActionBar().setBackgroundDrawable(colorDrawable);

        add = (ImageButton) findViewById(R.id.addButton);
        search = (ImageButton) findViewById(R.id.searchButton);
        settings = (ImageButton) findViewById(R.id.settingsButton);
        mylocation = new Mylocation(getApplicationContext());
        location = mylocation.getLocation();
        lat = location.getLatitude();
        lng = location.getLongitude();
        setUpFragment();


    }


    public void setUpFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, Toolbar_Fragment.newInstance())
                .commit();

        getSupportFragmentManager().beginTransaction().add(R.id.gplaces, Gplaces_Fragment.newInstance()).commit();
    }

    public void changeFragmentVisibility(int visibility) {
        if (add != null)
            add.setVisibility(visibility);
        if (search != null)
            search.setVisibility(visibility);
        if (settings != null)
            settings.setVisibility(visibility);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        uiSettings = mMap.getUiSettings();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                spotFragment = SpotFragment.newInstance(marker.getTitle());
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_contenair, spotFragment)
                        .commit();
                changeFragmentVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
                return false;
            }
        });
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setCompassEnabled(true);

        LatLng mylocation = new LatLng(lat, lng);
        this.marker = mMap.addMarker(new MarkerOptions().position(mylocation).draggable(true)
                .title("You are here"));
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
        setMarkerOnMap(mMap);
        loading(WAITING_TIME);
    }

    public void setMarkerOnMap(GoogleMap googleMap) {
        for (String s : sharedPrefferenceManager.getSpotName()) {
            googleMap.addMarker(createMarker(sharedPrefferenceManager.getLatLng(s),
                    s,
                    sharedPrefferenceManager.getPath(s)));
        }
    }

    public MarkerOptions createMarker(LatLng position, String name, String path) {
        return new MarkerOptions().position(position)
                .draggable(false).title(name);
    }

    private void loading(int time) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction().remove(loadingFragment).commit();
                frameLayout.setVisibility(View.GONE);
            }
        }, time);
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
            startActivityForResult(intent, REQUEST_FIND_PLACE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }
    public void addPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, REQUEST_ADD_PLACE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    public void backspot(View view) {
        getSupportFragmentManager().beginTransaction().remove(spotFragment).commit();
        frameLayout.setVisibility(View.GONE);
        changeFragmentVisibility(View.VISIBLE);
    }

    // A place has been received; use requestCode to track the request.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

        }
        if (requestCode == REQUEST_FIND_PLACE) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress()
                        + place.getPhoneNumber());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),13));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        if (requestCode == REQUEST_ADD_PLACE) {
            if (resultCode == RESULT_OK) {
                // retrive the data by using getPlace() method.
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.e("Tag", "Place: " + place.getAddress()
                        + place.getPhoneNumber());
                spotLocation = place.getLatLng();
                currentLocation = false;
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        spotPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.berangerelatouche.geoskate.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void getInfoFromAdd(String name, String desc) {
        spotName = name;
        spotDesc = desc;
        save();
    }
    private void initVar(){
        spotDesc = "";
        spotName = "";
        spotLocation = null;
        currentLocation = true;
        spotPhotoPath = "";
    }
    public void onBackArrow(View view) {
        getSupportFragmentManager().beginTransaction().remove(addSpotFragment).commit();
        initVar();
        frameLayout.setVisibility(View.GONE);
        changeFragmentVisibility(View.VISIBLE);
    }
    public void save() {
        if (currentLocation)
            sharedPrefferenceManager.putSpotName(spotName, spotDesc, spotPhotoPath, mylocation.getLocation());
        else
            sharedPrefferenceManager.putSpotName(spotName, spotDesc, spotPhotoPath, spotLocation);
        mMap.addMarker(createMarker(sharedPrefferenceManager.getLatLng(spotName), spotName,
                sharedPrefferenceManager.getPath(spotName)));
        spotPhotoPath = "";
        getSupportFragmentManager().beginTransaction().remove(addSpotFragment).commit();
        frameLayout.setVisibility(View.GONE);
        changeFragmentVisibility(View.VISIBLE);

        Toast.makeText(this, "Un marker été placé:"
                + spotName , Toast.LENGTH_LONG).show();
        initVar();
    }

    public void addCoordinate(View view) {
        System.out.println("TEST BUTTON");
        changeFragmentVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        addSpotFragment = new AddSpotFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_contenair, addSpotFragment)
                .commit();

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Google Place API connection error", Toast.LENGTH_SHORT).show();
    }


}
