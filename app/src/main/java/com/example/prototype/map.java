package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

public class map extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap gmap;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        mAuth = FirebaseAuth.getInstance();
        mapFragment.getMapAsync(this);



        populate();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        gmap = googleMap;
//        LatLng LA = new LatLng(34.0522,-118.2437);
//        gmap.addMarker(new MarkerOptions().position(LA).title("LA"));
//        gmap.moveCamera(CameraUpdateFactory.newLatLng(LA));
        //update(ds);
        gmap = googleMap;
//
//        populate();

    }

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