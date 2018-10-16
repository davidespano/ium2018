package com.example.alessandro.esercitazione_2;

import java.io.Serializable;
import java.util.Calendar;

public class Persona implements Serializable {
    // nota: sono gli stessi dati che chiediamo nel form
    private String cognome;
    private String nome;
    private Calendar data;

    public Persona()
    {
        this.nome="";
        this.cognome="";
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

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
}
