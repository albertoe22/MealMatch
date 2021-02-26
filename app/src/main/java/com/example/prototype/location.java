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
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class location extends AppCompatActivity {
    String apiKey = "AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
    FusedLocationProviderClient fusedLocationProviderClient;
    private RequestQueue mQueue;
    TextView latTextView, lonTextView;
    String placeurl, placeN;
    double lat, lon;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        latTextView = findViewById(R.id.latTextView);
        lonTextView = findViewById(R.id.lonTextView);
       // Places.initialize(getApplicationContext(), apiKey);
        //PlacesClient placesClient = Places.createClient(this);
        mQueue = Volley.newRequestQueue(this);
        getLastLocation();

    }

    private void jsonParse() {
        api();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, placeurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Todo: Get all place IDs into an array
                            JSONArray jsonArray = response.getJSONArray("results");
                            //String[] arrayID = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject place = jsonArray.getJSONObject(i);
                                String placeID = place.getString("place_id");
                                placeN = "ChIJB_-Q0lTqwoARJrHlEv4DkTA";
                                System.out.println(placeID);
                            }
                            jsonParsePics();

                            //JSONObject jsonObject = new JSONObject(String.valueOf(response.getJSONObject("result")));
                            //String name = jsonObject.getString("name");
                            //String name = a.getString("name");
                            //System.out.println(name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);

    }

    private void jsonParsePics() {
        String detailsUrl = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeN + "&fields=name,rating,photos&key=AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
        System.out.println(detailsUrl);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, detailsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    // Todo: Use photo reference on this end point to change a picture https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=&key=AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("result");
                            JSONArray jsonArray = jsonObject.getJSONArray("photos");
                            /*JSONObject photos = jsonArray.getJSONObject(0);
                            String photoRef = photos.getString("photo_reference");
                            System.out.println("photo_REF: " + photoRef);*/
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject photos = jsonArray.getJSONObject(i);
                                String photoRef = photos.getString("photo_reference");
                                System.out.println("photo ref: " + photoRef);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    private void api() {
        String output = "json";
        String parameters = lat + "," + lon + "&radius=3000&type=restaurant&key=" +apiKey;
        placeurl = "https://maps.googleapis.com/maps/api/place/nearbysearch/" + output+ "?location=" + parameters;
        System.out.println(placeurl);
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
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    System.out.println(String.valueOf(location.getLatitude()));
                    System.out.println("Longitude: " + String.valueOf(location.getLongitude()));
                    jsonParse();

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
                            lat = (location1.getLatitude());
                            lon = (location1.getLongitude());
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