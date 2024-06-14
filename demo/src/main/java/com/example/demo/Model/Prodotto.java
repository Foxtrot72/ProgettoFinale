package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private double prezzo;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCategoria() {
        return categoria;
    }public double getPrezzo() {
        return prezzo;
    }public void setCategoria(String categoria) {
        this.categoria = categoria;
    }public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
   
    public Utente getVenditore() {
        return utente;
    }

    public void setVenditore(Utente utente) {
        this.utente = utente;
    }
}