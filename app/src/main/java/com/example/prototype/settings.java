package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.UserRecord;
import com.google.firebase.firestore.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class settings extends AppCompatActivity {

    private static final String TAG = "";
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

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //String email;

    String email = user.getEmail();
    String uid = user.getUid();
    String newEmail = "";


    public void toChangeEmail(View view) {
        startActivity(new Intent(settings.this, changeEmail.class));
    }




//    public void updateEmail() {
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateEmail("user@example.com")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User email address updated.");
//                        }
//                    }
//                });
//    }

//    // Get auth credentials from the user for re-authentication
//    AuthCredential credential = EmailAuthProvider
//            .getCredential(email, pass); // Current Login Credentials \\
//    // Prompt the user to re-provide their sign-in credentials
//        user.reauthenticate(credential)
//            .addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            Log.d(TAG, "User re-authenticated.");
//            //Now change your email address \\
//            //----------------Code for Changing Email Address----------\\
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//            user.updateEmail("user@example.com")
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "User email address updated.");
//                            }
//                        }
//                    });
//        }
//    });



    public void resetPassword(View v){
        Toast.makeText(settings.this,"Password Reset sent to "+email, Toast.LENGTH_LONG).show();

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);
    }

    public void toSwipe(View view) {
        startActivity(new Intent(this, SwipeActivity.class));
    }

}