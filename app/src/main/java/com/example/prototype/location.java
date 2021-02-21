package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.net.PlacesClient;

public class location extends AppCompatActivity {
    String apiKey = "AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView latTextView, lonTextView;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        latTextView = findViewById(R.id.latTextView);
        lonTextView = findViewById(R.id.lonTextView);
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);
        getLastLocation();


    }

    private void api() {
        String output = "json";
        String parameters = latTextView.getText().toString() + "," + lonTextView.getText().toString() + "&radius=3000&type=restaurant&key=" +apiKey;
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output+ "?location=" + parameters;
        System.out.println(url);
        //https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJB_-Q0lTqwoARJrHlEv4DkTA&fields=name,rating,photos&key=AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc
        //https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=&key=AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc
        //ChIJhdFvgE_qwoAR_WiMjKcIamM
    }


    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(location.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(location.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When both permissions are granted
            getCurrentLocation();
        }
        else {
            //when permission is not granted, request permission
            ActivityCompat.requestPermissions(location.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            , Manifest.permission.ACCESS_COARSE_LOCATION}
                            , 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Check condition
        //when permission granted
        //call method
        if (requestCode == 100 && grantResults.length >0 && (grantResults[0]+grantResults[1]
        == PackageManager.PERMISSION_GRANTED)) getCurrentLocation();
        else {
            //when permission are denied
            // Display toast
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            //GEt last location
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                Location location = task.getResult();
                if (location != null) {
                    // When location result is not null
                    // set latitude
                    latTextView.setText(String.valueOf(location.getLatitude()));
                    lonTextView.setText(String.valueOf(location.getLongitude()));
                    System.out.println("Latitude: " + String.valueOf(location.getLatitude()));
                    System.out.println("Longitude: " + String.valueOf(location.getLongitude()));
                    api();
                } else {
                    // When location result is null
                    // request location
                    LocationRequest locationRequest = new LocationRequest()
                            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                            .setInterval(10000)
                            .setFastestInterval(1000)
                            .setNumUpdates(1);
                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            Location location1 = locationResult.getLastLocation();
                            // Initialize location
                            latTextView.setText(String.valueOf(location1.getLatitude()));
                            lonTextView.setText(String.valueOf(location1.getLongitude()));
                        }

                    };
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest
                            , locationCallback, Looper.myLooper());
                }
            });
        }else {
            //
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}