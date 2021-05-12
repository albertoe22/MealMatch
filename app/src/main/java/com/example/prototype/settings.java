package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class settings extends AppCompatActivity {

    private static final String TAG = "";
    private FirebaseAuth mAuth;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar1);
        seekBar.setProgress(SwipeActivity.globalRadius/1000);
        progress = seekBar.getProgress();
        TextView text = findViewById(R.id.textView14);
        text.setText("Current Distance is "+progress+" Kilometers.");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress = seekBar.getProgress();
                text.setText("Current Distance is "+progress+" Kilometers.");
            }
        });

    }
    public void updateDistance(View view)
    {
        SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar1);
        progress = seekBar.getProgress();
        Toast.makeText(settings.this,"Distance set to "+progress+" kilometer(s).", Toast.LENGTH_LONG).show();
        System.out.println("TEST: " );
        SwipeActivity.globalRadius = progress * 1000;

    }



    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, login.class));
    }

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    String email = user.getEmail();



    public void toChangeEmail(View view) {
        startActivity(new Intent(settings.this, changeEmail.class));
    }

    public void toSetDistance(View view){
        startActivity(new Intent(settings.this, setDistance.class));
    }

    public void resetMatches(View view) {
        Toast.makeText(settings.this,"Matches have been reset.", Toast.LENGTH_SHORT).show();

        DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        String currentUId = mAuth.getCurrentUser().getUid();
        usersDb.child(currentUId).child("matches").removeValue();
    }



    public void resetPassword(View view){
        Toast.makeText(settings.this,"Password Reset sent to "+email, Toast.LENGTH_LONG).show();

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

    public void contactUs(View view){
        Toast.makeText(settings.this,"Feel free to contact us at 491MealMatch@gmail.com!", Toast.LENGTH_LONG).show();
    }

    public void toSwipe(View view) {
        // terminates current activity to go back to previous activity

        finish();

        updateDistance(view);
       // startActivity(new Intent(this, SwipeActivity.class));
    }

}