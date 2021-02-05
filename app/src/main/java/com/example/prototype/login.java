package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void toSwipe(View view) {
        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {

            startActivity(new Intent(this, SwipeActivity.class));
            //correct password
        } else {
            //wrong password
            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    public void toRegister(View view) {
        startActivity(new Intent(this, register.class));
    }
}