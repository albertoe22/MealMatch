package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText email;
    EditText uname;
    EditText pass;
    EditText fname;
    EditText lname;
    Button register;
    FirebaseAuth fbAuth;
    FirebaseDatabase userDatabase;
    DatabaseReference rUserDatabase;
    static final String users = "users";
    private users u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.emailAddress);
        pass = findViewById(R.id.password);
        fname = findViewById(R.id.firstName);
        lname = findViewById(R.id.lastName);
        register = findViewById(R.id.registerButton);
        userDatabase = FirebaseDatabase.getInstance();
        rUserDatabase = userDatabase.getReference(users);
        fbAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = new users(email.getText().toString(),fname.getText().toString(),lname.getText().toString());
                fbAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Registration Successful", Toast.LENGTH_LONG).show();
                            FirebaseUser user = fbAuth.getCurrentUser();
                            updateDatabase(user,v);
                        }
                        else{
                            Toast.makeText(register.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    public void toLogin(View view) {
        startActivity(new Intent(this, login.class));
    }
    public void updateDatabase(FirebaseUser currentUser,View v){
        String keyID = fbAuth.getCurrentUser().getUid();
        rUserDatabase.child(keyID).setValue(u);
        toLogin(v);
    }
}