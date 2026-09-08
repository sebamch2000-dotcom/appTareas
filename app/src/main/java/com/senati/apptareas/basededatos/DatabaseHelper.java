package com.senati.apptareas.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Información de la Base de Datos
    private static final String DATABASE_NAME = "GestionTareas.db";
    private static final int DATABASE_VERSION = 2;

    // Constantes Tabla Usuarios
    public static final String TABLA_USUARIOS = "usuarios";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "nombre";
    public static final String COL_USER_USERNAME = "usuario";
    public static final String COL_USER_EMAIL = "correo";
    public static final String COL_USER_PASSWORD = "password";

    // Constantes Tabla Tareas (Alineado a los requerimientos del proyecto)
    public static final String TABLA_TAREAS = "tareas";
    public static final String COL_TAREA_ID = "id";
    public static final String COL_TAREA_TITULO = "titulo";
    public static final String COL_TAREA_DESCRIPCION = "descripcion";
    public static final String COL_TAREA_ESTADO = "estado"; // pendiente, en progreso, completada
    public static final String COL_TAREA_FECHA_VENC = "fecha_vencimiento";
    public static final String COL_TAREA_FECHA_CREA = "fecha_creacion";
    public static final String COL_TAREA_USER_ASIG = "usuario_asignado"; // Llave foránea o texto del usuario

    // Sentencia SQL para crear la tabla de Usuarios
    private static final String CREAR_TABLA_USUARIOS = "CREATE TABLE " + TABLA_USUARIOS + " ("
            + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_USER_NAME + " TEXT NOT NULL, "
            + COL_USER_USERNAME + " TEXT UNIQUE NOT NULL, "
            + COL_USER_EMAIL + " TEXT UNIQUE NOT NULL, "
            + COL_USER_PASSWORD + " TEXT NOT NULL);";

    // Sentencia SQL para crear la tabla de Tareas con todos tus campos obligatorios
    private static final String CREAR_TABLA_TAREAS = "CREATE TABLE " + TABLA_TAREAS + " ("
            + COL_TAREA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TAREA_TITULO + " TEXT NOT NULL, "
            + COL_TAREA_DESCRIPCION + " TEXT, "
            + COL_TAREA_ESTADO + " TEXT NOT NULL, " // almacena 'pendiente', 'en progreso' o 'completada'
            + COL_TAREA_FECHA_VENC + " TEXT, "
            + COL_TAREA_FECHA_CREA + " TEXT, "
            + COL_TAREA_USER_ASIG + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecutan los scripts para crear ambas tablas al iniciar la app
        db.execSQL(CREAR_TABLA_USUARIOS);
        db.execSQL(CREAR_TABLA_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TAREAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }
}
