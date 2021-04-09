package com.example.prototype;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AddFriend extends AppCompatActivity {
    private EditText searchtext;
    private ImageButton searchbutton;
    private RecyclerView results;
    private DatabaseReference userdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        userdatabase = FirebaseDatabase.getInstance().getReference("users");
        searchtext = (EditText) findViewById(R.id.searchBar);
        searchbutton = (ImageButton) findViewById(R.id.searchBtn);

        results = (RecyclerView) findViewById(R.id.results);
        results.setHasFixedSize(true);
        results.setLayoutManager(new LinearLayoutManager(this));
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = searchtext.getText().toString();
                userSearch(search);
            }
        });

    }
    private void userSearch(String s){
        Query userSearch = userdatabase.orderByChild("email").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerAdapter<users,ProfileHolder> adapter  = new FirebaseRecyclerAdapter<users, ProfileHolder>(users.class, R.layout.activity_profile, ProfileHolder.class, userdatabase) {
            @Override
            protected void populateViewHolder(ProfileHolder profileHolder, users users, int i) {
                profileHolder.setUserInfo(users.getEmail());
            }
        };
        results.setAdapter(adapter);
    }
    public static class ProfileHolder extends RecyclerView.ViewHolder{
        View view;
        public ProfileHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setUserInfo(String userEmail){
            TextView email = (TextView) view.findViewById(R.id.email);

            email.setText(userEmail);
        }

    }

}