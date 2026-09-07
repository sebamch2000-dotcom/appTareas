package com.senati.apptareas.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.senati.apptareas.R;

public class MainActivity extends AppCompatActivity {

    Button todas, pendientes, enProgreso, completadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todas =findViewById(R.id.chipTodos);
        pendientes =findViewById(R.id.chipPendientes);
        enProgreso =findViewById(R.id.chipProgreso);
        completadas =findViewById(R.id.chipCompletadas);

        todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}