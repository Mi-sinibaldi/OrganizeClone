package com.michelle.organizeclone.activity.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.michelle.organizeclone.activity.config.ConfigFirebase;
import com.michelle.organizeclone.activity.helper.Base64Custom;
import com.michelle.organizeclone.activity.helper.DateUtil;

public class Movimentacao {

    private String data;
    private String categoria;
    private String desc;
    private String tipo;
    private double valor;

    public Movimentacao() {
    }

    public void salvar(String dataEscolhida) {
        FirebaseAuth autenticacao = ConfigFirebase.getAuth();
        String idUsuario = Base64Custom.codigicarBase64(autenticacao.getCurrentUser().getEmail());
        String mesAno = DateUtil.mesAnoDataEscolhida(dataEscolhida);

        DatabaseReference reference = ConfigFirebase.getFirebase();
        reference.child("movimentacao")
                .child(idUsuario)
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
