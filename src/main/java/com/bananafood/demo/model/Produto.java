package com.bananafood.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A categoria é obrigatória")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String categoria;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço deve ser positivo")
    @Column(nullable = false)
    private Double preco;

    @Transient
    private String emoji = "🍌";

    @Size(max = 500)
    @Column(length = 500)
    private String descricao;

    @Transient
    private String calda;

    @Column(name = "imagem_url", length = 255)
    @Size(max = 255)
    private String imagemUrl;

    @Transient
    private boolean destaque;

    @Column(name = "data_criacao", insertable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public Produto() {
        this.emoji = "🍌";
    }

    public Produto(Long id, String nome, String categoria, Double preco, String emoji, String descricao, String calda, boolean destaque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.emoji = emoji != null && !emoji.trim().isEmpty() ? emoji : "🍌";
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

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public boolean isDestaque() {
        return destaque;
    }

    public void setDestaque(boolean destaque) {
        this.destaque = destaque;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPrecoFormatado() {
        if (preco == null) {
            return "0,00";
        }
        return String.format(Locale.forLanguageTag("pt-BR"), "%.2f", preco);
    }
}

