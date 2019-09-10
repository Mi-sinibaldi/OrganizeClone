package com.michelle.organizeclone.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfigFirebase;
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
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroActivity.this,
                            "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
