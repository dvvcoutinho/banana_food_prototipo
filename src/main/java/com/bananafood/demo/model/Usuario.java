package com.bananafood.demo.model;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private String caldaFavorita;
    private boolean aceitouTermos;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, String caldaFavorita) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.caldaFavorita = caldaFavorita;
        this.aceitouTermos = true;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCaldaFavorita() {
        return caldaFavorita;
    }

    public void setCaldaFavorita(String caldaFavorita) {
        this.caldaFavorita = caldaFavorita;
    }

    public boolean isAceitouTermos() {
        return aceitouTermos;
    }

    public void setAceitouTermos(boolean aceitouTermos) {
        this.aceitouTermos = aceitouTermos;
    }
}
