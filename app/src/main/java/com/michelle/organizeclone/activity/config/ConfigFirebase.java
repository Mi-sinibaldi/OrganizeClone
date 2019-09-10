package com.michelle.organizeclone.activity.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfigFirebase {
    private static FirebaseAuth auth;

    //retorna a instancia do FirebaseAuth
    public static FirebaseAuth getAuth() {
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
}
