package com.senati.apptareas.actividades;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.senati.apptareas.R;
import com.senati.apptareas.basededatos.DatabaseHelper;
import com.senati.apptareas.basededatos.TareaDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FormularioActivity extends AppCompatActivity {
    private TextInputEditText tituloTarea, descripcionTarea, etFechaVencimiento;
    private AutoCompleteTextView spEstado, etUsuarioAsignado;
    private MaterialButton btnGuardarTarea;
    private TareaDAO tareaDAO;
    private int idTareaExistente = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        tareaDAO = new TareaDAO(this);

        tituloTarea = findViewById(R.id.etTituloTarea);
        descripcionTarea = findViewById(R.id.etDescripcionTarea);
        spEstado = findViewById(R.id.spEstadoTarea);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);
        etUsuarioAsignado = findViewById(R.id.etUsuarioAsignado);
        btnGuardarTarea = findViewById(R.id.btnGuardarTarea);

        String[] estados = new String[]{"pendiente", "en progreso", "completada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, estados);
        spEstado.setAdapter(adapter);

        if (getIntent().hasExtra("ID_TAREA")) {
            idTareaExistente = getIntent().getIntExtra("ID_TAREA", -1);
            cargarDatosTarea(idTareaExistente);
            btnGuardarTarea.setText("Actualizar Tarea");
        }

        etFechaVencimiento.setOnClickListener(v -> abrirCalendarioFlotante());

        btnGuardarTarea.setOnClickListener(v -> guardarTarea());
    }

    private void guardarTarea() {
        String titulo = tituloTarea.getText().toString().trim();
        String desc = descripcionTarea.getText().toString().trim();
        String estado = spEstado.getText().toString().trim();
        String fechaVenc = etFechaVencimiento.getText().toString().trim();
        String usuario = etUsuarioAsignado.getText().toString().trim();

        if (titulo.isEmpty() || estado.isEmpty()) {
            Toast.makeText(this, "Título y Estado son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (idTareaExistente == -1) {
            String fechaCrea = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            tareaDAO.insertarTarea(titulo, desc, estado, fechaVenc, fechaCrea, usuario);
        } else {
            tareaDAO.actualizarTarea(idTareaExistente, titulo, desc, estado, fechaVenc, usuario);
        }
        finish();
    }

    private void abrirCalendarioFlotante() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog calendario = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String fecha = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
            etFechaVencimiento.setText(fecha);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        calendario.show();
    }

    private void cargarDatosTarea(int id) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.TABLA_TAREAS + " WHERE id = " + id, null);
        if (cursor != null && cursor.moveToFirst()) {
            tituloTarea.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_TITULO)));
            descripcionTarea.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_DESCRIPCION)));
            spEstado.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_ESTADO)), false);
            etFechaVencimiento.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_FECHA_VENC)));
            etUsuarioAsignado.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_USER_ASIG)));
            cursor.close();
        }
    }
}