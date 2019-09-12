package com.michelle.organizeclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfigFirebase;

public class MainActivity extends IntroActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_orange_light)
                .fragment(R.layout.intro_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_blue_light)
                .fragment(R.layout.intro_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_green_light)
                .fragment(R.layout.intro_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_purple)
                .fragment(R.layout.intro_4)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.holo_orange_light)
                .fragment(R.layout.intro_cadastro)
                .build()
        );
    }

    public void btnCadastrar(View view) {

        Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btnEntrar(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void verificarUsuarioLogado() {
        auth = ConfigFirebase.getAuth();
        //auth.signOut();
        if (auth.getCurrentUser() != null) { // recupera o usuário atual
            telaPrincipla();
        }
    }

    public void telaPrincipla() {
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}
