package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class map extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gmap;
    private FirebaseAuth mAuth;
    private String currentUserID;
    private ArrayList<MapMarkerObject> MapMarkers = new ArrayList<MapMarkerObject>();
    private ArrayList<MapMarkerObject> resultsMarkers= new ArrayList<MapMarkerObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        //mAuth = FirebaseAuth.getInstance();
        mapFragment.getMapAsync(this);
        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //getMarkers();
        List<MapMarkerObject> markers = getDataSetMatches();
        //Toast.makeText(this, "markers size is1 "+markers.size(), Toast.LENGTH_SHORT).show();
        //populate();
    }


    public MapMarkerObject oof = new MapMarkerObject("LA", 34.0522, -118.2437);
    public void addG()
    {
        MapMarkers.add(oof);
    }
    //bigOof.add(oof);


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        getMarkers();
        //gmap.animateCamera(CameraUpdateFactory.zoomTo(21));

        if (resultsMarkers.size() == 0) {
            // Default marker for no markers
            LatLng LA = new LatLng(34.0522, -118.2437);
            String la = "LA";
            float zoomLevel = 14.0f;
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(LA, zoomLevel));
            //gmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
        }


        //ArrayList<MapMarkerObject> markers = getDataSetMatches();


       /* Toast.makeText(this, "markers size is2 "+markers.size(), Toast.LENGTH_SHORT).show();
*/

        /*for(int i = 0; i < markers.size(); i++){
            LatLng temp = new LatLng(markers.get(i).getLat(), markers.get(i).getLng());
            gmap.addMarker(new MarkerOptions().position(temp).title(markers.get(i).getName()));
            //gmap.moveCamera(CameraUpdateFactory.newLatLng(temp));
        }

        LatLng LA = new LatLng(34.0522, -118.2437);
        String la = "LA";
        gmap.addMarker(new MarkerOptions().position(LA).title(la+markers.size()));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(LA));*/
        //Toast.makeText(this, "markers size is3 "+markers.size(), Toast.LENGTH_SHORT).show();

    }

    private ArrayList<MapMarkerObject> getDataSetMatches() {
        return resultsMarkers;
    }
    private void getMarkers() {

        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches");
        //System.out.println("hello" + matchDb.getKey());

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    FetchMatchInformation(snapshot.getKey());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void FetchMatchInformation(String key) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches").child(key);
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If the user matched with the restaurant
                if (snapshot.hasChild("name")) {
                    // Get place id
                    String placeId = snapshot.getKey();
                    String name = "";
                    String address = "";
                    double lat = 0;
                    double lng = 0;
                    // If the name value is not null, get name from the database
                    if (snapshot.child("name").getValue(String.class) != null) {
                        name = snapshot.child("name").getValue().toString();
                    }
                    // If the address value is not null, get address from the database
                    /*if (snapshot.child("address").getValue(String.class) != null) {
                        address = snapshot.child("address").getValue().toString();
                    }*/

                    if (snapshot.child("lat").getValue(Double.class) != null){
                        lat = ((double) snapshot.child("lat").getValue());
                    }

                    if (snapshot.child("lon").getValue(Double.class) != null){
                        lng = ((double) snapshot.child("lon").getValue());
                    }

                    // Create matches object from the database values
                    MapMarkerObject obj = new MapMarkerObject(name, lat, lng);
                    LatLng temp = new LatLng(lat, lng);
                    gmap.addMarker(new MarkerOptions().position(temp).title(name));
                    //gmap.moveCamera(CameraUpdateFactory.newLatLng(temp));

                    //fix for zoom
                    float zoomLevel = 14.0f;
                    gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(temp, zoomLevel));


                    // Put matches into array adapter
                    resultsMarkers.add(obj);

                    //mMatchesAdapter.notifyDataSetChanged();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }





}
/*
    DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("users");
    String currentUserID = mAuth.getCurrentUser().getUid();
    //Task<DataSnapshot> ds = usersDb.child(currentUserId).child("matches").get();


    private void populate() {


        LatLng LA = new LatLng(34.0522,-118.2437);
        gmap.addMarker(new MarkerOptions().position(LA).title("LA"));
        gmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches");
        //System.out.println("hello" + matchDb.getKey());

        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    FetchMatchInformation(snapshot.getKey());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void FetchMatchInformation(String key) {
        DatabaseReference matchDb = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserID).child("matches").child(key);
        matchDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If the user matched with the restaurant
                if (snapshot.hasChild("name")) {
                    // Get place id
                    String placeId = snapshot.getKey();
                    String name = "";
                    String address = "";
                    int lat = 0;
                    int lon = 0;
                    // If the name value is not null, get name from the database
                    if (snapshot.child("name").getValue(String.class) != null) {
                        name = snapshot.child("name").getValue().toString();
                    }
                    // If the address value is not null, get address from the database
                    if (snapshot.child("lat").getValue(int.class) != null) {
                        lat = ((Integer) snapshot.child("address").getValue());

                    }

                    if (snapshot.child("lon").getValue(int.class) != null) {
                        lon = ((Integer) snapshot.child("address").getValue());

                    }
                    // Create matches object from the database values

                    LatLng LA = new LatLng(lat,lon);
                    gmap.addMarker(new MarkerOptions().position(LA).title(name));

                    // Put matches into array adapter
                    //resultsMatches.add(obj);
                    //mMatchesAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }













    //Toast.makeText(this, ""+ds, Toast.LENGTH_SHORT).show();
    //public void update(DataSnapshot dataSnapshot){
//
//
//        for(DataSnapshot ds: dataSnapshot.getChildren()){
//            String restName = ds.child("name").toString();
//            String slat = ds.child("lat").toString();
//            String slon = ds.child("lon").toString();
//            int lat = Integer.valueOf(slat);
//            int lon = Integer.valueOf(slon);
//            Toast.makeText(this, ""+restName, Toast.LENGTH_SHORT).show();
//
//        }
//
//
//    }

}



 */