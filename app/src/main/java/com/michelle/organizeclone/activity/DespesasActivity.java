package com.michelle.organizeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.helper.DateUtil;
import com.michelle.organizeclone.activity.model.Movimentacao;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText editData, editCategoria, editDesc;
    private EditText editTextValor;
    private Movimentacao movimentacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);


        editData = findViewById(R.id.editData);
        editCategoria = findViewById(R.id.editCategoria);
        editDesc = findViewById(R.id.editDesc);
        editTextValor = findViewById(R.id.editTextValor);

        //Data atual
        editData.setText(DateUtil.dataAtual());

    }

    public void salvarDespesa(View view) {
        movimentacao = new Movimentacao();
        String data  = editData.getText().toString();
        movimentacao.setValor(Double.parseDouble(editTextValor.getText().toString()));
        movimentacao.setCategoria(editCategoria.getText().toString());
        movimentacao.setDesc(editDesc.getText().toString());
        movimentacao.setData(data);
        movimentacao.setTipo("despesa");

        movimentacao.salvar(data);
        Toast.makeText(DespesasActivity.this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show();
    }
}
