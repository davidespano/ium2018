package com.example.alessandro.esercitazione_2;

import java.io.Serializable;

public class Persona implements Serializable {
    // nota: sono gli stessi dati che chiediamo nel form
    private String cognome;
    private String nome;
    private String data;

    public Persona()
    {
        this.nome="";
        this.cognome="";
        this.data="";
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
