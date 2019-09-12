package com.michelle.organizeclone.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.michelle.organizeclone.R;
import com.michelle.organizeclone.activity.helper.DateUtil;

public class DespesasActivity extends AppCompatActivity {

    private TextInputEditText editData, editSalario, editDesc;
    private EditText editTextValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);


        editData = findViewById(R.id.editData);
        editSalario = findViewById(R.id.editSalario);
        editDesc = findViewById(R.id.editDesc);
        editTextValor = findViewById(R.id.editTextValor);

        //Data atuale
        editData.setText(DateUtil.dataAtual());

    }
}
