package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.net.PlacesClient;

public class SwipeActivity extends AppCompatActivity {
    String apiKey = "AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Todo: Get the location of a user
        // Todo: Get nearby places by using Google Places Search
        // Todo: Get place details by using Place ID from Place Search
        // Todo: Display photos by using photo link
    }


    public void toMatches(View view) {
        startActivity(new Intent(this, Matches.class));
    }

    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }
}