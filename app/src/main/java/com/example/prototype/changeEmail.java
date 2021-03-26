package com.example.prototype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changeEmail extends AppCompatActivity {

    public EditText newEmail;
    private FirebaseAuth fbAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeemail);
        EditText newEmail = (EditText) findViewById(R.id.newEmail);


        fbAuth = FirebaseAuth.getInstance();

    }

    //fbAuth = FirebaseAuth.getInstance();


    public void updateEmail(View view) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        EditText newEmail = (EditText) findViewById(R.id.newEmail);

        String e = newEmail.getText().toString().trim();


        user.updateEmail(e)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(changeEmail.this, "Email reset to "+e, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(changeEmail.this, settings.class));

                        }
                        else{
                            Toast.makeText(changeEmail.this, "Email not changed. ", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(changeEmail.this, settings.class));

                        }
                    }
                });
    }
}
