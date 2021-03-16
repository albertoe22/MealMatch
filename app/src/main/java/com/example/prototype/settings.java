package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;


public class settings extends AppCompatActivity {

    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public void signOut(View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, login.class));
    }

    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
    //String email;

    String email = user.getEmail();
//        photoUrl = user.photoURL;


    public void resetPassword(View v){
        Toast.makeText(settings.this,"Password Reset sent to "+email, Toast.LENGTH_LONG).show();

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

    public void toSwipe(View view) {
        startActivity(new Intent(this, SwipeActivity.class));
    }

}