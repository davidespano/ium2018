package com.example.alessandro.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // variabili di classe
    TextView textView;
    Button incremento,decremento;
    EditText input;
    SeekBar seekBar;

    // inizializzazione parametri calcolatrice
    int minValue = 0;
    int maxValue = 100;
    int modelValue = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // chiamata super (sempre necessaria)
        super.onCreate(savedInstanceState);
        // caricamento elementi grafici (il file .xml a cui è associato)
        setContentView(R.layout.activity_main);

        // recupero widget tramite id
        textView = (TextView)findViewById(R.id.titolo);
        incremento = (Button)findViewById(R.id.incremento);
        decremento = (Button)findViewById(R.id.decremento);
        input = (EditText)findViewById(R.id.input);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        // visualizza valore di default (=50)
        updateValue(modelValue);
        // handler incremento
        incremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textView.setText("+1"); prima parte esercitazione
                updateValue(++modelValue);
            }
        });
        // handler decremento
        decremento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //textView.setText("-1"); prima parte esercitazione
                updateValue(--modelValue);
            }
        });
        // handler seekBar - per il gruppo B: lo vedremo assieme lunedì 8
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateValue(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateValue(seekBar.getProgress());
            }
        });
    }

    protected void updateValue(int newValue)
    {
        // verifica che newValue sia compreso nel range specificato (0 >= newValue <= 100)
        // > max
        newValue = newValue > maxValue ? maxValue : newValue;
        // < min
        newValue = newValue < minValue ? minValue : newValue;
        // aggiorno la variabile che indica il valore attuale della calcolatrice
        this.modelValue = newValue;
        // aggiorno il contenuto dell'EditText
        input.setText(""+this.modelValue);

        // aggiorno il valore visualizzato dalla seekBar
        if(this.seekBar.getProgress() != modelValue - minValue)
        {
            this.seekBar.setProgress(modelValue-minValue);
        }
    }


    // esempio funzionamento del circolo di vita di un'activity
    @Override
    protected void onStop()
    {
        super.onStop();

        textView.setText("Ciao Gianni");
    }

}
