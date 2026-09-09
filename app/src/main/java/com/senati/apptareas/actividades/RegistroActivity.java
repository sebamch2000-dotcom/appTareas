package com.senati.apptareas.actividades;

import android.os.Bundle;
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

        usuarioDAO = new UsuarioDAO(this);

        etNombre = findViewById(R.id.etNombreRegistro);
        etUsuario = findViewById(R.id.etUsuarioRegistro);
        etCorreo = findViewById(R.id.etCorreoRegistro);
        etPassword = findViewById(R.id.etPasswordRegistro);
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        tvVolverLogin = findViewById(R.id.tvVolverLogin);

        btnRegistrar.setOnClickListener(v -> registrarNuevoUsuario());
        tvVolverLogin.setOnClickListener(v -> finish());
    }

    private void registrarNuevoUsuario() {
        String nombre = etNombre.getText().toString().trim();
        String usuario = etUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmar = etConfirmarPassword.getText().toString().trim();

        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmar.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmar)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        long id = usuarioDAO.registrarUsuario(nombre, usuario, correo, password);

        if (id > 0) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }
}