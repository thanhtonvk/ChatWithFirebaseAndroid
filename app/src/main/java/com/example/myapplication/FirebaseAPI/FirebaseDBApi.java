package com.example.myapplication.FirebaseAPI;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDBApi {
    public FirebaseDatabase database;
    public DatabaseReference reference;
    public FirebaseDBApi(String ref){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(ref);
    }
}
