package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.libraries.places.api.Places;

import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import android.os.Looper;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SwipeActivity extends AppCompatActivity {
    String apiKey = "AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
    private ArrayList<String> example;
    private ArrayAdapter<String> arrayAdapter;
    private int i;
    FusedLocationProviderClient fusedLocationProviderClient;
    private RequestQueue mQueue;
    private ImageView imageView;

    private String placeurl, placeN, imageurl;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        mQueue = Volley.newRequestQueue(this);

        example = new ArrayList<>();
        example.add("Chipotle");
        example.add("Chickfila");
        example.add("Wendys");
        example.add("Mcdonalds");
        example.add("BurgerKing");
        example.add("KrustyKrab");
        arrayAdapter = new ArrayAdapter<>(this, R.layout.card, R.id.test, example );
        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.cards);
        flingContainer.setAdapter(arrayAdapter);

        //getLastLocation();

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                example.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(SwipeActivity.this,"left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(SwipeActivity.this,"right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                example.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }

        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(SwipeActivity.this,"click", Toast.LENGTH_SHORT).show();
            }
        });
        FloatingActionButton left = (FloatingActionButton) findViewById(R.id.dislike);
        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                flingContainer.getTopCardListener().selectLeft();
            }
        });
        FloatingActionButton right = (FloatingActionButton) findViewById(R.id.like);
        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                flingContainer.getTopCardListener().selectRight();
            }
        });
    }
    private void jsonParse() {
        api();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, placeurl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Todo: 0. integrate this into the swipe actvity with cards
                            // Todo: 1. Get all place IDs into an array/arraylist and save the result
                            // Todo: 1.5 Check to see if it has atleast 1 photo, else don't add to array
                            // Todo: Also need to see how you can change the range in api if you run out of places?

                            JSONArray jsonArray = response.getJSONArray("results");
                            //String[] arrayID = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject place = jsonArray.getJSONObject(i);
                                String placeID = place.getString("place_id");
                                placeN = "ChIJB_-Q0lTqwoARJrHlEv4DkTA";
                                //System.out.println(placeID);
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
        Context context = SwipeActivity.this;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, detailsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    // TODO: 2. Save photo links into an arraylist/hashmap, make it so that when you tap, it goes to the next image
                    // TODO: 3. If you swipe, go to next place_id reference with photos
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject jsonObject = response.getJSONObject("result");
                            JSONArray jsonArray = jsonObject.getJSONArray("photos");
                            //for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject photos = jsonArray.getJSONObject(0);
                            String photoRef = photos.getString("photo_reference");
                            imageurl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=600&photoreference=" + photoRef + "&key=AIzaSyDgMhZAjvbssW3MFNWJ5yTgoJkLj2PHQuc";
                            System.out.println(imageurl);
                            ImageView img = (ImageView) findViewById(R.id.imageView5);
                            Glide.with(context).load(imageurl).into(img);
                            //}

                        }
                        catch (JSONException e) {
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
    }


    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(SwipeActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(SwipeActivity.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // When both permissions are granted
            getCurrentLocation();
        }
        else {
            //when permission is not granted, request permission
            ActivityCompat.requestPermissions(SwipeActivity.this,
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

                            lat = (location1.getLatitude());
                            lon = (location1.getLongitude());
                        }

                    };
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest
                            , locationCallback, Looper.myLooper());
                }
            });
        }else {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    public void toMatches(View view) {
        startActivity(new Intent(this, Matches.class));
    }

    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }
}