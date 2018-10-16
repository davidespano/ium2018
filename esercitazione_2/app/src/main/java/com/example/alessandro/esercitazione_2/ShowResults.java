package com.example.alessandro.esercitazione_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class ShowResults extends AppCompatActivity {

    Persona persona;
    TextView nome, cognome, data;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(
                MainActivity.PERSONA_EXTRA
        );

        if(obj instanceof Persona)
        {
            persona = (Persona)obj;
        }
        else{
            persona = new Persona();
        }

        nome = (TextView) findViewById(R.id.nome);
        cognome = (TextView) findViewById(R.id.cognome);
        data = (TextView) findViewById(R.id.data);
        ok = (Button) findViewById(R.id.ok);

        nome.setText(persona.getNome());
        cognome.setText(persona.getCognome());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        data.setText(format.format(persona.getData().getTime()));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();// termina la prima activity
                // oppure
                Intent showResults = new Intent(ShowResults.this,
                        MainActivity.class);
                startActivity(showResults);
            }
        });
    }


}
