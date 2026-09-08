package com.senati.apptareas.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.senati.apptareas.R;
import com.senati.apptareas.basededatos.UsuarioDAO;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText etNombre, etUsuario, etCorreo, etPassword, etConfirmarPassword;
    private MaterialButton btnRegistrar;
    private TextView tvVolverLogin;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar la conexión a la base de datos
        usuarioDAO = new UsuarioDAO(this);

        // Enlazar las variables con los IDs del diseño XML
        etNombre = findViewById(R.id.etNombreRegistro);
        etUsuario = findViewById(R.id.etUsuarioRegistro);
        etCorreo = findViewById(R.id.etCorreoRegistro);
        etPassword = findViewById(R.id.etPasswordRegistro);
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvVolverLogin = findViewById(R.id.tvVolverLogin);

        // Evento para el botón de registrar
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarNuevoUsuario();
            }
        });

        // Evento para regresar a la pantalla de Login
        tvVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finaliza esta actividad y regresa a la anterior
                finish();
            }
        });
    }

    private void registrarNuevoUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmar = etConfirmarPassword.getText().toString().trim();

        // Validaciones básicas de campos vacíos
        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validación de coincidencia de contraseñas
        if (!password.equals(confirmar)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar en la base de datos mediante el DAO
        long id = usuarioDAO.registrarUsuario(nombre, usuario, correo, password);

        if (id > 0) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            // Regresar al login automáticamente después de registrarse
            finish();
        } else {
            Toast.makeText(this, "Error al registrar usuario. Intenta con otro correo.", Toast.LENGTH_LONG).show();
        }
    }
}