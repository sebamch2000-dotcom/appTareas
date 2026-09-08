package com.senati.apptareas.actividades;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.senati.apptareas.R;
import com.senati.apptareas.adaptadores.TareaAdapter;
import com.senati.apptareas.basededatos.DatabaseHelper;
import com.senati.apptareas.basededatos.TareaDAO;
import com.senati.apptareas.entidades.Tarea;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TareaAdapter.OnTareaListener {

    private RecyclerView rvTareas;
    private TareaAdapter adapter;
    private TareaDAO tareaDAO;
    private List<Tarea> listaTareas;
    private FloatingActionButton fabAgregar;
    private Chip chipTodos, chipPendientes, chipProgreso, chipCompletadas, chipFallidas;
    private String filtroActual = "Todas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tareaDAO = new TareaDAO(this);
        listaTareas = new ArrayList<>();

        rvTareas = findViewById(R.id.rvTareas);
        fabAgregar = findViewById(R.id.fabAgregarTarea);
        chipTodos = findViewById(R.id.chipTodos);
        chipPendientes = findViewById(R.id.chipPendientes);
        chipProgreso = findViewById(R.id.chipProgreso);
        chipCompletadas = findViewById(R.id.chipCompletadas);
        chipFallidas = findViewById(R.id.chipFallidas);

        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TareaAdapter(this, listaTareas, this);
        rvTareas.setAdapter(adapter);

        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            startActivity(intent);
        });

        configurarFiltros();
        cargarTareas(filtroActual);
    }

    private void configurarFiltros() {
        chipTodos.setOnClickListener(v -> { filtroActual = "Todas"; cargarTareas(filtroActual); });
        chipPendientes.setOnClickListener(v -> { filtroActual = "Pendiente"; cargarTareas(filtroActual); });
        chipProgreso.setOnClickListener(v -> { filtroActual = "En Progreso"; cargarTareas(filtroActual); });
        chipCompletadas.setOnClickListener(v -> { filtroActual = "Completada"; cargarTareas(filtroActual); });
        chipFallidas.setOnClickListener(v -> { filtroActual = "No se pudo completar"; cargarTareas(filtroActual); });
    }

    private void cargarTareas(String filtro) {
        filtroActual = filtro;
        listaTareas.clear();
        Cursor cursor = tareaDAO.obtenerTareasPorEstado(filtro);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_ID));
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_TITULO));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_DESCRIPCION));
                String estado = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_ESTADO));
                String venc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_FECHA_VENC));
                String crea = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_FECHA_CREA));
                String user = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TAREA_USER_ASIG));

                listaTareas.add(new Tarea(id, titulo, desc, estado, venc, crea, user));
            } while (cursor.moveToNext());
            cursor.close();
        }
        adapter.actualizarLista(listaTareas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarTareas(filtroActual);
    }

    @Override
    public void onEditarClick(Tarea tarea) {
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        intent.putExtra("ID_TAREA", tarea.getId());
        startActivity(intent);
    }

    @Override
    public void onEliminarClick(Tarea tarea) {
        tareaDAO.eliminarTarea(tarea.getId());
        cargarTareas(filtroActual);
    }

    @Override
    public void onEstadoCambio(Tarea tarea, boolean isChecked) {
        String nuevoEstado = isChecked ? "Completada" : "Pendiente";
        tareaDAO.actualizarEstadoTarea(tarea.getId(), nuevoEstado);
        cargarTareas(filtroActual);
    }
}