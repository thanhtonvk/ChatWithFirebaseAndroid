package com.example.myapplication.FirebaseAPI;

import com.google.firebase.auth.FirebaseAuth;

public class AuthApi {
    public FirebaseAuth auth;

    public AuthApi() {
        auth = FirebaseAuth.getInstance();
    }
}
