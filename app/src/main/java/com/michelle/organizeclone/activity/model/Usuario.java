package com.michelle.organizeclone.activity.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.michelle.organizeclone.activity.config.ConfigFirebase;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String idUsuario;
    private Double receitaTotal = 0.0;
    private Double despesaTotal = 0.0;

    public Usuario() {
    }

    public void salvar() {
        DatabaseReference firebase = ConfigFirebase.getFirebase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude // anotação do firebase
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Exclude // anotação para excluir o dado do firebase. Esse dado sera desconsiderado
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }
}
