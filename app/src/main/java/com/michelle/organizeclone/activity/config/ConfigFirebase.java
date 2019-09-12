package com.michelle.organizeclone.activity.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference firebase;

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getAuth() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    //retorna a instancia do FirebaseDatabase
    public static DatabaseReference getFirebase() {
        if (firebase == null) {
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return firebase;
    }
}
