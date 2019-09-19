package com.michelle.organizeclone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfiguracaoFirebase;
import com.michelle.organizeclone.activity.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private TextView editTextEmail, editTextSenha;
    private Button buttonLogin;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadUi();
        loginUser();
    }

    private void loadUi() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    private void loginUser() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = editTextEmail.getText().toString();
                String textSenha = editTextSenha.getText().toString();

                if (!textEmail.isEmpty()) {
                    if (!textSenha.isEmpty()) {

                        usuario = new Usuario();
                        usuario.setSenha(textSenha);
                        usuario.setEmail(textEmail);
                        validarLogin();

                    } else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void validarLogin() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    telaPrincipla();
                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        exception = "Usuário não está cadastrado!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "E-mail e senha não corresponde ao usuário cadastrado";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário" + e.getMessage();
                        e.printStackTrace(); //printa no log
                    }

                    Toast.makeText(LoginActivity.this, exception,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void telaPrincipla() {
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}
