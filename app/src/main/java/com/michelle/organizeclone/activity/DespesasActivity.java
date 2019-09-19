package com.michelle.organizeclone.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.config.ConfiguracaoFirebase;
import com.michelle.organizeclone.activity.helper.Base64Custom;
import com.michelle.organizeclone.activity.helper.DateUtil;
import com.michelle.organizeclone.activity.model.Movimentacao;
import com.michelle.organizeclone.activity.model.Usuario;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText editData, editCategoria, editDesc;
    private EditText editTextValor;
    private Movimentacao movimentacao;
    private DatabaseReference FirebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        loadUi();
        //Data atual
        editData.setText(DateUtil.dataAtual());

        recuperDespesaTotal();

    }

    public void salvarDespesa(View view) {

        if (validarCamposDespesas()) {
            movimentacao = new Movimentacao();
            String data = editData.getText().toString();
            Double valorRecuperado = Double.parseDouble(editTextValor.getText().toString());

            movimentacao.setValor(valorRecuperado);
            movimentacao.setCategoria(editCategoria.getText().toString());
            movimentacao.setDesc(editDesc.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("despesa");

            Double despesaAtualizada = despesaTotal + valorRecuperado;
            atualizarDespesas(despesaAtualizada);

            movimentacao.salvar(data);
            Toast.makeText(DespesasActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public Boolean validarCamposDespesas() {
        String textoValor = editTextValor.getText().toString();
        String textoData = editData.getText().toString();
        String textoCategoria = editCategoria.getText().toString();
        String textoDescricao = editDesc.getText().toString();

        if (!textoValor.isEmpty()) {
            if (!textoData.isEmpty()) {
                if (!textoCategoria.isEmpty()) {
                    if (!textoDescricao.isEmpty()) {
                        return true;
                    } else {
                        Toast.makeText(DespesasActivity.this,
                                "Preencha a descrição", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    Toast.makeText(DespesasActivity.this,
                            "Preencha a categoria", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else {
                Toast.makeText(DespesasActivity.this,
                        "Preencha a data", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(DespesasActivity.this,
                    "Preencha o valor", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void recuperDespesaTotal() {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = FirebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class); // converte o retorno do firebase em um objeto do tipo usuário
                despesaTotal = usuario.getDespesaTotal();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizarDespesas(Double despesa) {
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = FirebaseRef.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);
    }

    public void loadUi() {
        editData = findViewById(R.id.editData);
        editCategoria = findViewById(R.id.editCategoria);
        editDesc = findViewById(R.id.editDesc);
        editTextValor = findViewById(R.id.editTextValor);
    }
}
