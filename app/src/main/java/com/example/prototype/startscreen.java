package com.example.prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class startscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
    }

    public void toLogin(View view) {
        Intent intent2 = new Intent(this, login.class);
        startActivity(intent2);
    }

    public void toRegister(View view) {
        Intent intent2 = new Intent(this, register.class);
        startActivity(intent2);
    }
}