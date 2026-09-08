package com.senati.apptareas.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.senati.apptareas.R;
import com.senati.apptareas.basededatos.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {
    EditText usuario, contrasena;
    TextView tvIrRegistro;
    Button btnIniciar;
    UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDAO = new UsuarioDAO(this);

        usuario = findViewById(R.id.etUsuarioLogin);
        contrasena = findViewById(R.id.etPasswordLogin);
        btnIniciar = findViewById(R.id.btnLogin);
        tvIrRegistro = findViewById(R.id.tvIrRegistro);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = usuario.getText().toString().trim();
                String passInput = contrasena.getText().toString().trim();

                if (userInput.isEmpty() || passInput.isEmpty()) {
                    return;
                }

                // Validar con base de datos, cuenta admin o cuenta usuario genérica
                if ((userInput.equals("admin") && passInput.equals("1234")) || 
                    (userInput.equals("usuario") && passInput.equals("1234")) ||
                    usuarioDAO.verificarUsuario(userInput, passInput)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvIrRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }
}
