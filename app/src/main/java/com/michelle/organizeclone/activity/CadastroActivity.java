package com.michelle.organizeclone.activity;

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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfigFirebase;
import com.michelle.organizeclone.activity.helper.Base64Custom;
import com.michelle.organizeclone.activity.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private TextView editTexteNome, editTextEmail, editTexSenha;
    private Button buttonNovoCadastro;
    private FirebaseAuth auth;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        loadUi();
        cadastro();
    }

    private void cadastro() {
        buttonNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNome = editTexteNome.getText().toString();
                String textEmail = editTextEmail.getText().toString();
                String textSenha = editTexSenha.getText().toString();

                //validação se os campos foram preenchidos
                if (!textNome.isEmpty()) {
                    if (!textEmail.isEmpty()) {
                        if (!textSenha.isEmpty()) {
                            usuario = new Usuario();
                            usuario.setNome(textNome);
                            usuario.setEmail(textEmail);
                            usuario.setSenha(textSenha);

                            cadastrarUsuario();
                        } else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o e-mail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUi() {
        editTexteNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTexSenha = findViewById(R.id.editTextSenha);
        buttonNovoCadastro = findViewById(R.id.buttonNovoCadastro);
    }

    public void cadastrarUsuario() {

        auth = ConfigFirebase.getAuth();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    String idUsuario = Base64Custom.codigicarBase64(usuario.getEmail());
                    usuario.setIdUsuario(idUsuario);
                    usuario.salvar();
                    finish();

                } else {

                    String exception = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Digite um senha forte:" +
                                "Minimo 8 caracteres e " +
                                "p  elo menos um caracter especial.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        exception = "Digite um e-mail válido!";
                    } catch (FirebaseAuthUserCollisionException e) {
                        exception = "Essa conta já foi cadastrada!";
                    } catch (Exception e) {
                        exception = "Erro ao cadastrar usuário" + e.getMessage();
                        e.printStackTrace(); //printa no log
                    }

                    Toast.makeText(CadastroActivity.this,
                            exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
