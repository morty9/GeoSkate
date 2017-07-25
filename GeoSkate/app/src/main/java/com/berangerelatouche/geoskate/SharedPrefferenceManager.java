package com.berangerelatouche.geoskate;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashSet;
import java.util.Set;

class SharedPrefferenceManager {
    private static final SharedPrefferenceManager ourInstance = new SharedPrefferenceManager();
    private Context context;
    private Set<String> spotName;
    SharedPreferences sharedPreferences;

    static SharedPrefferenceManager getInstance() {
        return ourInstance;
    }

    public void setContext(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("GeoSkate", Context.MODE_PRIVATE);
        this.spotName = sharedPreferences.getStringSet("spotName", null);
        if (this.spotName == null)
            this.spotName = new HashSet<String>();
    }

    public Set<String> getSpotName() {
        return spotName;
    }

    public LatLng getLatLng(String spotName) {
        String lat = sharedPreferences.getString(spotName + "lat", null);
        String lng = sharedPreferences.getString(spotName + "lng", null);
        return new LatLng(Double.valueOf(lat), Double.valueOf(lng));
    }

    public String getDesc(String spotName) {
        return sharedPreferences.getString(spotName+"desc", null);
    }
    public String getPath(String spotName) {
        return sharedPreferences.getString(spotName+"path", null);
    }

    public boolean putSpotName(String spotName, String desc, String picturePath, Location location) {
        if (this.spotName.add(spotName)) {
            sharedPreferences.edit()
                    .putStringSet("spotName", this.spotName)
                    .apply();
            sharedPreferences.edit()
                    .putString(spotName + "desc", desc)
                    .apply();
            sharedPreferences.edit()
                    .putString(spotName + "path", picturePath)
                    .apply();
            sharedPreferences.edit().putString(spotName + "lng", String.valueOf(location.getLongitude())).apply();
            sharedPreferences.edit().putString(spotName + "lat", String.valueOf(location.getLatitude())).apply();
            return true;
        } else
            return false;
    }
    public boolean putSpotName(String spotName, String desc, String picturePath, LatLng location) {
        if (this.spotName.add(spotName)) {
            sharedPreferences.edit()
                    .putStringSet("spotName", this.spotName)
                    .apply();
            sharedPreferences.edit()
                    .putString(spotName + "desc", desc)
                    .apply();
            sharedPreferences.edit()
                    .putString(spotName + "path", picturePath)
                    .apply();
            sharedPreferences.edit().putString(spotName + "lng", String.valueOf(location.longitude)).apply();
            sharedPreferences.edit().putString(spotName + "lat", String.valueOf(location.latitude)).apply();
            return true;
        } else
            return false;
    }


    private SharedPrefferenceManager() {

    }
}
