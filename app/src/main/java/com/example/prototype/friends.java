package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class friends extends AppCompatActivity {
    private ImageButton SearchforFriends;
    private RecyclerView myFriends;
    private DatabaseReference myFriendsDatabase;
    private FirebaseUser currentUser;
    private Button displayFriends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        SearchforFriends = findViewById(R.id.searchforfriends);
        SearchforFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(friends.this, AddFriend.class));
            }
        });
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("FriendList").child(currentUser.getUid());
        myFriends = (RecyclerView) findViewById(R.id.friendsList);
        myFriends.setHasFixedSize(true);
        myFriends.setLayoutManager(new LinearLayoutManager(this));
        displayFriends = (Button) findViewById(R.id.showFriends);
        displayFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayFriends();
            }
        });

    }
    public void DisplayFriends(){
        FirebaseRecyclerAdapter<users,userHolder> listofFriends = new FirebaseRecyclerAdapter<users, userHolder>(users.class,R.layout.activity_profile,userHolder.class,myFriendsDatabase) {
            @Override
            protected void populateViewHolder(userHolder profileHolder, users users, int i) {
                profileHolder.setUserInfo(users.getEmail());
            }
        };
        myFriends.setAdapter(listofFriends);
    }
    public static class userHolder extends RecyclerView.ViewHolder{
        View view;
        public userHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setUserInfo(String userEmail){
            TextView email1 = (TextView) view.findViewById(R.id.emailDisplay);

            email1.setText(userEmail);
        }

    }
}