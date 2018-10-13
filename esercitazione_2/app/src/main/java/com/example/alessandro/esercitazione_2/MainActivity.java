package com.example.alessandro.esercitazione_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText nome, cognome, data;
    Button inserisci;
    Persona persona=new Persona();
    // nella vostra applicazione sarà qualcosa del tipo
    // "com.example.il_vostro_nome_utente.nome_progetto.Persona"
    // gruppo A - lo vedremo assieme la prossima volta
    public static final String
            PERSONA_EXTRA="com.example.alessandro.esercitazione_2.Persona";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = (EditText)findViewById(R.id.nome);
        cognome = (EditText)findViewById(R.id.cognome);
        data = (EditText)findViewById(R.id.data);
        inserisci = (Button)findViewById(R.id.inserisci);

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // aggiorna il contenuto di persona
                updatePersona();
                // crea l'oggetto di tipo Intent, ci serve per far comunicare
                // le due activity
                Intent showResults = new Intent(MainActivity.this,
                        ShowResults.class);
                // inserisci l'oggetto persona dentro l'Intent
                // gruppo A - lo vedremo assieme la prossima volta
                showResults.putExtra(PERSONA_EXTRA,persona);
                // richiama l'activity ShowResult
                startActivity(showResults);
            }
        });
    }

    public void updatePersona()
    {
        // aggiorna il contenuto di persona usando i dati inseriti dall'utente
        // - gruppo A lo vedremo assieme la settimana prossima
        persona.setNome(""+nome.getText());
        persona.setCognome(""+cognome.getText());
        persona.setData(""+data.getText());
    }
}
