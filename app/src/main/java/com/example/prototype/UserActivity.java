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
    private DatabaseReference FriendList;
    private DatabaseReference userDatabase2;
    public users u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String userid = getIntent().getStringExtra("user_id");
        userDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        friendRequestData = FirebaseDatabase.getInstance().getReference().child("FriendRequestDatabase");
        userDatabase2 = FirebaseDatabase.getInstance().getReference("users");
        String em = userDatabase2.child(userid).child("email").toString();
        String f = userDatabase2.child(userid).child("fname").toString();
        String l = userDatabase2.child(userid).child("lname").toString();
        System.out.println(em);
        u = new users(em,f,l);
        email = (TextView) findViewById(R.id.Email);
        fname = (TextView) findViewById(R.id.Firstname);
        lname = (TextView) findViewById(R.id.Lastname);
        sendRequest = (Button) findViewById(R.id.sendRequest);
        FriendList = FirebaseDatabase.getInstance().getReference().child("FriendList");
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

                //Friends list
                friendRequestData.child(current.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(userid)){
                            String request = snapshot.child(userid).child("reqType").getValue().toString();
                            if(request.equals("received")){
                                friendStatus = 2;//2 = request recieved
                                sendRequest.setText("ACCEPT REQUEST");
                            }else if(request.equals("sent")){
                                friendStatus = 3;//3 = request sent
                                sendRequest.setText("CANCEL REQUEST");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest.setEnabled(false);
                //NOT FRIENDS
                if(friendStatus==0){//means not friends
                    friendRequestData.child(current.getUid()).child(userid).child("reqType").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                friendRequestData.child(userid).child(current.getUid()).child("reqType").setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        sendRequest.setEnabled(true);
                                        friendStatus = 1;//1 = request sent
                                        sendRequest.setText("CANCEL REQUEST");
                                        Toast.makeText(UserActivity.this,"success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toast.makeText(UserActivity.this,"failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //CANCEL REQUEST
                if(friendStatus==1){
                    friendRequestData.child(current.getUid()).child(userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            friendRequestData.child(userid).child(current.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    sendRequest.setEnabled(true);
                                    friendStatus = 0;
                                    sendRequest.setText("SEND FRIEND REQUEST");
                                }
                            });
                        }
                    });
                }
                //ACCEPT REQUEST
                if(friendStatus==2){//you have to setvalue to a user class object so temp needs to be user class object 
                    FriendList.child(current.getUid()).child(userid).setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            FriendList.child(userid).child(current.getUid()).setValue(current).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    friendRequestData.child(current.getUid()).child(userid).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            friendRequestData.child(userid).child(current.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    sendRequest.setEnabled(true);
                                                    friendStatus = 4; //4 = friends
                                                    sendRequest.setText("UN-FRIEND");
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });

                }
            }
        });

    }
    public users GetUser(String id){
        userDatabase2.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                u = snapshot.getValue(users.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return u;
    }
}