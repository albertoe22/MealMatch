package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Matches extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mMatchesAdapter;
    private RecyclerView.LayoutManager mMatchesLayoutManger;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        mMatchesLayoutManger = new LinearLayoutManager(Matches.this);
        mRecyclerView.setLayoutManager(mMatchesLayoutManger);
        mMatchesAdapter = new MatchesAdapter(getDataSetMatches(), Matches.this);
        mRecyclerView.setAdapter(mMatchesAdapter);

        getPlaceMatchId();


    }
    private ArrayList<MatchesObject> resultsMatches= new ArrayList<MatchesObject>();
    private List<MatchesObject> getDataSetMatches() {
        return resultsMatches;
    }
    private void getPlaceMatchId() {

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
                    double lat = 0, lon = 0;
                    // If the name value is not null, get name from the database
                    if (snapshot.child("name").getValue(String.class) != null) {
                        name = snapshot.child("name").getValue().toString();
                    }
                    // If the address value is not null, get address from the database
                    if (snapshot.child("address").getValue(String.class) != null) {
                        address = snapshot.child("address").getValue().toString();
                    }
                    if (snapshot.child("lat").getValue(Double.class) != null && snapshot.child("lon").getValue(Double.class) != null) {
                        lat = (double) snapshot.child("lat").getValue();
                        lon = (double) snapshot.child("lon").getValue();
                    }
                    // Create matches object from the database values
                    MatchesObject obj = new MatchesObject(placeId, name, address, lat, lon);
                    // Put matches into array adapter
                    resultsMatches.add(obj);
                    mMatchesAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }



}