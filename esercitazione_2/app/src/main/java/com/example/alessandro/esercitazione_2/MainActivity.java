package com.example.alessandro.esercitazione_2;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText nome, cognome, data;
    Button inserisci;
    Persona persona=new Persona();
    DatePickerFragment datePickerFragment = new DatePickerFragment();
    // nella vostra applicazione sarà qualcosa del tipo
    // "com.example.il_vostro_nome_utente.nome_progetto"
    public static final String
            PERSONA_EXTRA="com.example.alessandro.esercitazione_2";

    // GRUPPO B - lo vedremo il prossimo lunedì
    TextView errore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nome = (EditText)findViewById(R.id.nome);
        cognome = (EditText)findViewById(R.id.cognome);
        data = (EditText)findViewById(R.id.data);
        inserisci = (Button)findViewById(R.id.inserisci);

        // GRUPPO B - lo vedremo il prossimo lunedì
        errore = (TextView) findViewById(R.id.errore);
        errore.setVisibility(View.GONE); //di default la visibilità dell'error text è gone


        // setto il listener della data in modo tale che quando si clicca sull'EditText appaia il datePickerFragment
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment.show(getSupportFragmentManager(),
                        "datePicker"); // appare il datePickerFragment
            }
        });

        // configurazione Eventi Dialog Calendar
        datePickerFragment.setOnDatePickerFragmentChanged
                (new DatePickerFragment.DatePickerFragmentListener() {
            @Override
            public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date) {
                SimpleDateFormat format = new
                        SimpleDateFormat("dd/MM/yyyy");
                data.setText(format.format(date.getTime()));
            }
            @Override
            public void onDatePickerFragmentCancelButton(DialogFragment dialog) {
            }
        });

        inserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput()) {
                    // aggiorna il contenuto di persona
                    updatePersona();
                    // crea l'oggetto di tipo Intent, ci serve per far comunicare
                    // le due activity
                    Intent showResults = new Intent(MainActivity.this,
                            ShowResults.class);
                    // inserisci l'oggetto persona dentro l'Intent
                    // gruppo A - lo vedremo assieme la prossima volta
                    showResults.putExtra(PERSONA_EXTRA, persona);
                    // richiama l'activity ShowResult
                    startActivity(showResults);
                }
            }
        });
    }

    public void updatePersona()
    {
        // aggiorna il contenuto di persona usando i dati inseriti dall'utente
        persona.setNome(""+nome.getText());
        persona.setCognome(""+cognome.getText());
        persona.setData(this.datePickerFragment.getData());
    }

    private boolean checkInput()
    {
        int errors = 0;

        if(nome.getText()==null || nome.getText().length()==0) {
            nome.setError("Inserire il nome");
            errors++;
        }
        else
            nome.setError(null);

        if(cognome.getText()==null || cognome.getText().length()==0) {
            cognome.setError("Inserire il cognome");
            errors++;
        }
        else
            cognome.setError(null);

        if(data.getText()==null || data.getText().length()==0) {
            data.setError("Inserire la data di nascita");
            errors++;
        }
        else
            data.setError(null);

        switch (errors){
            case 0:
                errore.setVisibility(View.GONE);
                errore.setText("");
                break;
            case 1:
                errore.setVisibility(View.VISIBLE);
                errore.setText("Si è verificato un Errore");
                break;
            default:
                errore.setVisibility(View.VISIBLE);
                errore.setText("Si sono verificati " + errors+" errori" );
                break;
        }
        return errors==0; // ritorna true se non ci sono errori
    }
}
