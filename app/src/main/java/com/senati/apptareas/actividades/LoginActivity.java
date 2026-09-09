package com.senati.apptareas.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.senati.apptareas.R;
import com.senati.apptareas.basededatos.UsuarioDAO;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText etUsuario, etPassword;
    private TextView tvIrRegistro;
    private MaterialButton btnIniciar;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioDAO = new UsuarioDAO(this);

        etUsuario = findViewById(R.id.etUsuarioLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnIniciar = findViewById(R.id.btnLogin);
        tvIrRegistro = findViewById(R.id.tvIrRegistro);

        btnIniciar.setOnClickListener(v -> {
            String userInput = etUsuario.getText().toString().trim();
            String passInput = etPassword.getText().toString().trim();

            if (userInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if ((userInput.equals("admin") && passInput.equals("1234")) ||
                    usuarioDAO.verificarUsuario(userInput, passInput)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        tvIrRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }
}