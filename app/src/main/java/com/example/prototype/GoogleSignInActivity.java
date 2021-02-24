package com.example.prototype;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class GoogleSignInActivity {

    // Configure Google Sign In
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
}
