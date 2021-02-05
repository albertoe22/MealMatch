package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity {
    EditText email;
    EditText uname;
    EditText pass;
    EditText fname;
    EditText lname;
    Button register;
    FirebaseAuth fbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailAddress);
        uname = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        fname = findViewById(R.id.firstName);
        lname = findViewById(R.id.lastName);
        register = findViewById(R.id.registerButton);

        fbAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}