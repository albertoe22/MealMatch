package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserActivity extends AppCompatActivity {
    private TextView email;
    private TextView fname;
    private TextView lname;
    private Button sendRequest;
    private DatabaseReference userDatabase;
    private int friendStatus;
    private DatabaseReference friendRequestData;
    private FirebaseUser current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String userid = getIntent().getStringExtra("user_id");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        friendRequestData = FirebaseDatabase.getInstance().getReference().child("FriendRequestDatabase");
        email = (TextView) findViewById(R.id.Email);
        fname = (TextView) findViewById(R.id.Firstname);
        lname = (TextView) findViewById(R.id.Lastname);
        sendRequest = (Button) findViewById(R.id.sendRequest);
        //0 = not friends
        friendStatus = 0;
        current = FirebaseAuth.getInstance().getCurrentUser();
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String first = snapshot.child("fname").getValue().toString();
                String last = snapshot.child("lname").getValue().toString();
                String userEmail = snapshot.child("email").getValue().toString();

                email.setText(userEmail);
                fname.setText(first);
                lname.setText(last);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendStatus==0){//means not friends
                    friendRequestData.child(current.getUid()).child(userid).child("reqType").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                friendRequestData.child(userid).child(current.getUid()).child("reqType").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(UserActivity.this,"success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(UserActivity.this,"failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}