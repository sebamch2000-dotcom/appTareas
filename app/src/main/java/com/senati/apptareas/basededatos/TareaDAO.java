package com.senati.apptareas.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class TareaDAO {
    private DatabaseHelper dbHelper;

    public TareaDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 1. Operación: Crear nueva tarea
    public long insertarTarea(String titulo, String descripcion, String estado, String fechaVenc, String fechaCrea, String usuarioAsig) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.COL_TAREA_TITULO, titulo);
        valores.put(DatabaseHelper.COL_TAREA_DESCRIPCION, descripcion);
        valores.put(DatabaseHelper.COL_TAREA_ESTADO, estado); // 'pendiente', 'en progreso', 'completada'
        valores.put(DatabaseHelper.COL_TAREA_FECHA_VENC, fechaVenc);
        valores.put(DatabaseHelper.COL_TAREA_FECHA_CREA, fechaCrea);
        valores.put(DatabaseHelper.COL_TAREA_USER_ASIG, usuarioAsig);

        long id = db.insert(DatabaseHelper.TABLA_TAREAS, null, valores);
        db.close();
        return id;
    }

    // 2. Operación: Editar tarea existente
    public int actualizarTarea(int id, String titulo, String descripcion, String estado, String fechaVenc, String usuarioAsig) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.COL_TAREA_TITULO, titulo);
        valores.put(DatabaseHelper.COL_TAREA_DESCRIPCION, descripcion);
        valores.put(DatabaseHelper.COL_TAREA_ESTADO, estado);
        valores.put(DatabaseHelper.COL_TAREA_FECHA_VENC, fechaVenc);
        valores.put(DatabaseHelper.COL_TAREA_USER_ASIG, usuarioAsig);

        int filasAfectadas = db.update(DatabaseHelper.TABLA_TAREAS, valores, DatabaseHelper.COL_TAREA_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return filasAfectadas;
    }

    // 3. Operación: Marcar estado individual (Para cambiar rápidamente a "en progreso" o "completada")
    public int actualizarEstadoTarea(int id, String nuevoEstado) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.COL_TAREA_ESTADO, nuevoEstado);

        int filasAfectadas = db.update(DatabaseHelper.TABLA_TAREAS, valores, DatabaseHelper.COL_TAREA_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return filasAfectadas;
    }

    // 4. Operación: Eliminar tarea
    public int eliminarTarea(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filasEliminadas = db.delete(DatabaseHelper.TABLA_TAREAS, DatabaseHelper.COL_TAREA_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return filasEliminadas;
    }

    // 5. Operación: Filtrar tareas según su estado (pendiente, en progreso, completada) o traer todas
    public Cursor obtenerTareasPorEstado(String estadoFiltro) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (estadoFiltro == null || estadoFiltro.isEmpty() || estadoFiltro.equalsIgnoreCase("Todas")) {
            // Devuelve todas las tareas sin filtrar
            return db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLA_TAREAS, null);
        } else {
            // Filtra estrictamente por el estado enviado (ej. 'pendiente')
            String seleccion = DatabaseHelper.COL_TAREA_ESTADO + " = ?";
            return db.query(DatabaseHelper.TABLA_TAREAS, null, seleccion, new String[]{estadoFiltro}, null, null, null);
        }
    }
}
