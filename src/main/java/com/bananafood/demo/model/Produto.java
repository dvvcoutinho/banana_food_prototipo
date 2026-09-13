package com.bananafood.demo.model;

import java.util.Locale;

public class Produto {
    private Long id;
    private String nome;
    private String categoria;
    private Double preco;
    private String emoji;
    private String descricao;
    private String calda;
    private boolean destaque;

    public Produto() {
        this.emoji = "🍌";
    }

    public Produto(Long id, String nome, String categoria, Double preco, String emoji, String descricao, String calda, boolean destaque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.emoji = emoji != null && !emoji.isBlank() ? emoji : "🍌";
        this.descricao = descricao;
        this.calda = calda;
        this.destaque = destaque;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCalda() {
        return calda;
    }

    public void setCalda(String calda) {
        this.calda = calda;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public String getPrecoFormatado() {
        if (preco == null) {
            return "0,00";
        }
        return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", preco);
    }
}
