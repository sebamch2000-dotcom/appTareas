package com.senati.apptareas.actividades;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.senati.apptareas.R;

import java.util.Calendar;

public class FormularioActivity extends AppCompatActivity {
    TextView tituloTarea, descripcionTarea;
    Spinner spEstado;
    EditText etFechaVencimiento;
    Button btnGuardarTarea;
    TextView selecUsuarios;
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        tituloTarea = findViewById(R.id.etTituloTarea);
        descripcionTarea = findViewById(R.id.etDescripcionTarea);
        spEstado = findViewById(R.id.spEstadoTarea);
        etFechaVencimiento = findViewById(R.id.etFechaVencimiento);

        // 2. Crear el adaptador usando el string-array que definiste en strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.estados_tarea, android.R.layout.simple_spinner_item);

        selecUsuarios = findViewById(R.id.etUsuarioAsignado);

        etFechaVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendarioFlotante();
            }
        });

        btnGuardarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tituloTarea.toString() != "" && descripcionTarea.toString() != "" && spEstado.getSelectedItem().toString() != "Estado"){
                    Intent intent = new Intent(FormularioActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void abrirCalendarioFlotante() {
        // Tomamos la fecha actual del sistema para que el calendario se abra en el día de hoy
        Calendar c = Calendar.getInstance();
        int anioActual = c.get(Calendar.YEAR);
        int mesActual = c.get(Calendar.MONTH);
        int diaActual = c.get(Calendar.DAY_OF_MONTH);

        // Creamos la ventana del calendario (DatePickerDialog)
        DatePickerDialog calendarioFlotante = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // EXPLICACIÓN: El sistema cuenta los meses del 0 al 11 (Enero es 0).
                        // Por eso le sumamos +1 para que se visualice correctamente en formato humano (1 al 12).
                        int mesReal = month + 1;

                        // Damos formato visual al texto agregando ceros a la izquierda si el día o mes es menor a 10
                        String diaFormateado = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                        String mesFormateado = (mesReal < 10) ? "0" + mesReal : String.valueOf(mesReal);

                        // Juntamos el Día, Mes y Año con barras diagonales
                        String fechaFinal = diaFormateado + "/" + mesFormateado + "/" + year;

                        // ¡AQUÍ SE VISUALIZA EN LA CASILLA! Introducimos el texto en el EditText
                        etFechaVencimiento.setText(fechaFinal);
                    }
                }, anioActual, mesActual, diaActual);

        // Mostramos el calendario visual en la pantalla
        calendarioFlotante.show();
    }

}
