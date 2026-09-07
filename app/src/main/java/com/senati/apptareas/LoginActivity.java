package com.senati.apptareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity  extends AppCompatActivity {
    TextView correo, contrasena;

    String _correo = "admin", _contrasena = "1234";
    Button btnIniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        correo =findViewById(R.id.etCorreoLogin);
        contrasena =findViewById(R.id.etPasswordLogin);
        btnIniciar = findViewById(R.id.btnLogin);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correo.toString() == _correo && contrasena.toString() == _contrasena){
                    Intent intent = new Intent(LoginActivity.this, FormularioActivity.class);
                    startActivity(intent);
                }
            }
        });


    }
}
