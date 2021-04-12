package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class setDistance extends AppCompatActivity {

    int progress;
    //int
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setdistance);

        SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar);
        progress = seekBar.getProgress();

    }
    SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar);


    public void update()
    {
        progress = seekBar.getProgress();
    }





    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }

}
