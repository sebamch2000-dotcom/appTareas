package com.senati.apptareas.actividades;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.senati.apptareas.R;
import com.senati.apptareas.basededatos.DatabaseHelper;
import com.senati.apptareas.basededatos.TareaDAO;
import com.senati.apptareas.basededatos.UsuarioDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FormularioActivity extends AppCompatActivity {
    EditText tituloTarea, descripcionTarea, etFechaVencimiento;
    AutoCompleteTextView spEstado, selecUsuarios;
    Button btnGuardarTarea;
    TareaDAO tareaDAO;
    UsuarioDAO usuarioDAO;
    private int idTareaExistente = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        tareaDAO = new TareaDAO(this);
        usuarioDAO = new UsuarioDAO(this);

        // 1. Inicializar vistas
        tituloTarea = findViewById(R.id.etTituloTarea);
        descripcionTarea = findViewById(R.id.etDescripcionTarea);
        spEstado = findViewById(R.id.spEstadoTarea);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        selecUsuarios = findViewById(R.id.etUsuarioAsignado);
        btnGuardarTarea = findViewById(R.id.btnGuardarTarea);

        // 2. Configurar el adaptador para el AutoCompleteTextView (que actúa como Spinner)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estados_tarea, android.R.layout.simple_dropdown_item_1line);
        spEstado.setAdapter(adapter);

        // 3. Configurar sugerencias de usuarios
        configurarSugerenciasUsuarios();

        // 4. Verificar si es edición o nueva tarea
        if (getIntent().hasExtra("ID_TAREA")) {
            idTareaExistente = getIntent().getIntExtra("ID_TAREA", -1);
            cargarDatosTarea(idTareaExistente);
            btnGuardarTarea.setText("Actualizar Tarea");
            findViewById(R.id.tvTituloFormulario).setAlpha(0.7f); // Opcional: Cambio visual
        }

        etFechaVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendarioFlotante();
            }
        });

        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = tituloTarea.getText().toString().trim();
                String desc = descripcionTarea.getText().toString().trim();
                String estado = spEstado.getText().toString().trim();
                String fechaVenc = etFechaVencimiento.getText().toString().trim();
                String usuario = selecUsuarios.getText().toString().trim();

                if (!titulo.isEmpty() && !estado.isEmpty()) {
                    if (idTareaExistente == -1) {
                        // Nueva Tarea
                        String fechaCrea = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                        tareaDAO.insertarTarea(titulo, desc, estado, fechaVenc, fechaCrea, usuario);
                    } else {
                        // Editar Tarea
                        tareaDAO.actualizarTarea(idTareaExistente, titulo, desc, estado, fechaVenc, usuario);
                    }
                    
                    finish();
                }
            }
        });
    }

    private void abrirCalendarioFlotante() {
        Calendar c = Calendar.getInstance();
        int anioActual = c.get(Calendar.YEAR);
        int mesActual = c.get(Calendar.MONTH);
        int diaActual = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog calendarioFlotante = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int mesReal = month + 1;
                        String diaFormateado = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String mesFormateado = (mesReal < 10) ? "0" + mesReal : String.valueOf(mesReal);
                        String fechaFinal = diaFormateado + "/" + mesFormateado + "/" + year;
                        etFechaVencimiento.setText(fechaFinal);
                    }
                }, anioActual, mesActual, diaActual);

        calendarioFlotante.show();
    }

    private void configurarSugerenciasUsuarios() {
        List<String> listaUsernames = usuarioDAO.obtenerTodosLosUsernames();
        
        // Cuentas por defecto
        if (!listaUsernames.contains("admin")) listaUsernames.add("admin");
        if (!listaUsernames.contains("usuario")) listaUsernames.add("usuario");

        ArrayAdapter<String> adapterUsers = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listaUsernames);
        
        selecUsuarios.setAdapter(adapterUsers);
        selecUsuarios.setThreshold(1); // Muestra sugerencias desde la primera letra
    }

    private void cargarDatosTarea(int id) {
        Cursor cursor = tareaDAO.obtenerTareaPorId(id);
        if (cursor != null && cursor.moveToFirst()) {
            tituloTarea.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_TITULO)));
            descripcionTarea.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_DESCRIPCION)));
            spEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_ESTADO)), false);
            etFechaVencimiento.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_FECHA_VENC)));
            selecUsuarios.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_USER_ASIG)), false);
            cursor.close();
        }
    }
}
