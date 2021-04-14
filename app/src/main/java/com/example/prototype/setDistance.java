package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

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
        seekBar.setProgress(SwipeActivity.globalRadius/1000);
        progress = seekBar.getProgress();

    }
    //SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar);



    public void update(View view)
    {
        SeekBar seekBar= (SeekBar) findViewById(R.id.seekBar);
        progress = seekBar.getProgress();
        Toast.makeText(setDistance.this,"Distance set to "+progress+" kilometer(s).", Toast.LENGTH_LONG).show();
        SwipeActivity.globalRadius = progress * 1000;
        //Toast.makeText(setDistance.this,"static variable globaclradius = "+SwipeActivity.globalRadius, Toast.LENGTH_LONG).show();

    }



    public void toSettings(View view) {
        startActivity(new Intent(this, settings.class));
    }

    public void backButton(View view){
        toSettings(view);
        update(view);
    }
}
