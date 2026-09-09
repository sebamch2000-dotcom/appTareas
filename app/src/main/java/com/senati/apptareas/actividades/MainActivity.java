package com.senati.apptareas.actividades;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

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
    private FloatingActionButton fabCerrarSesion;
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
        fabCerrarSesion = findViewById(R.id.fabCerrarSesion);
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

        // Evento para el botón de cerrar sesión
        fabCerrarSesion.setOnClickListener(v -> {
            new android.app.AlertDialog.Builder(MainActivity.this)
                    .setTitle("Cerrar Sesión")
                    .setMessage("¿Estás seguro de que deseas salir de tu cuenta?")
                    .setPositiveButton("Sí, salir", (dialog, which) -> {
                        // 1. Limpiar la sesión usando tu SessionManager
                        com.senati.apptareas.utilidades.SessionManager session = new com.senati.apptareas.utilidades.SessionManager(MainActivity.this);
                        session.cerrarSesion();

                        // 2. Redirigir al Login
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        // 3. Limpiar el historial para que no pueda volver atrás con el botón de retroceso del celular
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
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
        // Crear y mostrar el AlertDialog
        new android.app.AlertDialog.Builder(this)
                .setTitle("Eliminar Tarea")
                .setMessage("¿Estás seguro de que deseas eliminar la tarea: '" + tarea.getTitulo() + "'?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Si el usuario confirma, procedemos a borrar y recargar la lista
                    tareaDAO.eliminarTarea(tarea.getId());
                    cargarTareas(filtroActual);
                    android.widget.Toast.makeText(this, "Tarea eliminada", android.widget.Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                    // Si cancela, solo cerramos el cuadro de diálogo
                    dialog.dismiss();
                })
                .setIcon(android.R.drawable.ic_dialog_alert) // Ícono de advertencia nativo
                .show();
    }

    @Override
    public void onEstadoCambio(Tarea tarea, boolean isChecked) {
        String nuevoEstado = isChecked ? "completada" : "pendiente";
        tareaDAO.actualizarEstadoTarea(tarea.getId(), nuevoEstado);
        cargarTareas(filtroActual);
    }
}