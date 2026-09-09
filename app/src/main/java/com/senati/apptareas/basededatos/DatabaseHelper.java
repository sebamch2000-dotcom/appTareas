package com.senati.apptareas.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Información de la Base de Datos
    private static final String DATABASE_NAME = "GestionTareas.db";
    private static final int DATABASE_VERSION = 5;

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
        
        // Insertar datos de prueba para la exposición
        insertarDatosPrueba(db);
    }

    private void insertarDatosPrueba(SQLiteDatabase db) {
        // 20 Usuarios de LogiExpress
        String[][] usuarios = {
            {"Carlos Ruiz", "cruiz", "cruiz@logiexpress.pe", "1234"},
            {"Ana Torres", "atorres", "atorres@logiexpress.pe", "1234"},
            {"Luis Meza", "lmeza", "lmeza@logiexpress.pe", "1234"},
            {"Marta Jara", "mjara", "mjara@logiexpress.pe", "1234"},
            {"Jorge Solis", "jsolis", "jsolis@logiexpress.pe", "1234"},
            {"Elena Vega", "evega", "evega@logiexpress.pe", "1234"},
            {"Pedro Diaz", "pdiaz", "pdiaz@logiexpress.pe", "1234"},
            {"Sofia Luna", "sluna", "sluna@logiexpress.pe", "1234"},
            {"Raul Castro", "rcastro", "rcastro@logiexpress.pe", "1234"},
            {"Lucia Sanz", "lsanz", "lsanz@logiexpress.pe", "1234"},
            {"Victor Paz", "vpaz", "vpaz@logiexpress.pe", "1234"},
            {"Diana Rios", "drios", "drios@logiexpress.pe", "1234"},
            {"Hugo Lara", "hlara", "hlara@logiexpress.pe", "1234"},
            {"Rosa Polo", "rpolo", "rpolo@logiexpress.pe", "1234"},
            {"Felix Soto", "fsoto", "fsoto@logiexpress.pe", "1234"},
            {"Gaby Mora", "gmora", "gmora@logiexpress.pe", "1234"},
            {"Ivan Cruz", "icruz", "icruz@logiexpress.pe", "1234"},
            {"Julia Vera", "jvera", "jvera@logiexpress.pe", "1234"},
            {"Mario Gil", "mgil", "mgil@logiexpress.pe", "1234"},
            {"Nidia Leon", "nleon", "nleon@logiexpress.pe", "1234"}
        };

        for (String[] u : usuarios) {
            db.execSQL("INSERT INTO " + TABLA_USUARIOS + " (" + COL_USER_NAME + "," + COL_USER_USERNAME + "," + COL_USER_EMAIL + "," + COL_USER_PASSWORD + ") VALUES ('"+u[0]+"','"+u[1]+"','"+u[2]+"','"+u[3]+"')");
        }

        // 20 Tareas asignadas (19 Pendientes y 1 'No se pudo completar' para ejemplo)
        String[][] tareas = {
            {"Despacho de Carga A1", "Verificar guías de remisión en almacén norte", "Pendiente", "10/09/2026 18:00", "09/09/2026", "cruiz"},
            {"Mantenimiento Camión 4", "Cambio de aceite y revisión de frenos", "Pendiente", "09/09/2026 15:00", "09/09/2026", "atorres"},
            {"Inventario de Repuestos", "Conteo cíclico de neumáticos", "Pendiente", "08/09/2026 12:00", "07/09/2026", "lmeza"},
            {"Reunión de Seguridad", "Charla de 5 minutos sobre EPP", "Pendiente", "11/09/2026 08:30", "09/09/2026", "mjara"},
            {"Entrega Cliente Mayorista", "Ruta Sur - Incidencia en vehículo", "No se pudo completar", "08/09/2026 20:00", "08/09/2026", "jsolis"},
            {"Limpieza de Almacén", "Ordenamiento de pallets en zona B", "Pendiente", "09/09/2026 10:00", "09/09/2026", "evega"},
            {"Revisión de Neumáticos", "Inspección de presión y desgaste", "Pendiente", "09/09/2026 11:00", "09/09/2026", "pdiaz"},
            {"Auditoría de Carga", "Cruce de stock físico vs sistema", "Pendiente", "08/09/2026 14:00", "08/09/2026", "sluna"},
            {"Capacitación Montacargas", "Curso teórico-práctico zona sur", "Pendiente", "12/09/2026 09:00", "09/09/2026", "rcastro"},
            {"Control de Temperatura", "Registro cada 2 horas en frigoríficos", "Pendiente", "09/09/2026 22:00", "09/09/2026", "lsanz"},
            {"Verificación de Rutas", "Validación de peajes y cierres", "Pendiente", "08/09/2026 17:00", "08/09/2026", "vpaz"},
            {"Abastecimiento Combustible", "Llenado de tanques flota pesada", "Pendiente", "10/09/2026 05:00", "09/09/2026", "drios"},
            {"Registro Devoluciones", "Ingreso de productos dañados al sistema", "Pendiente", "08/09/2026 16:30", "08/09/2026", "hlara"},
            {"Etiquetado Mercadería", "Colocación de códigos de barra lote 45", "Pendiente", "09/09/2026 13:00", "09/09/2026", "rpolo"},
            {"Inspección de EPP", "Verificación de cascos y botas operarios", "Pendiente", "10/09/2026 08:00", "09/09/2026", "fsoto"},
            {"Actualización Software", "Update de sistema de escaneo", "Pendiente", "07/09/2026 19:00", "07/09/2026", "gmora"},
            {"Desinfección Vehículos", "Protocolo sanitario flota reparto", "Pendiente", "09/09/2026 14:30", "09/09/2026", "icruz"},
            {"Monitoreo GPS", "Seguimiento de unidades en tiempo real", "Pendiente", "09/09/2026 23:59", "09/09/2026", "jvera"},
            {"Archivo de Facturas", "Digitalización de guías mes Agosto", "Pendiente", "08/09/2026 11:00", "08/09/2026", "mgil"},
            {"Planificación Semanal", "Definición de rutas Lima Metropolitana", "Pendiente", "10/09/2026 10:00", "09/09/2026", "nleon"}
        };

        for (String[] t : tareas) {
            db.execSQL("INSERT INTO " + TABLA_TAREAS + " (" + COL_TAREA_TITULO + "," + COL_TAREA_DESCRIPCION + "," + COL_TAREA_ESTADO + "," + COL_TAREA_FECHA_VENC + "," + COL_TAREA_FECHA_CREA + "," + COL_TAREA_USER_ASIG + ") VALUES ('"+t[0]+"','"+t[1]+"','"+t[2]+"','"+t[3]+"','"+t[4]+"','"+t[5]+"')");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_TAREAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_USUARIOS);
        onCreate(db);
    }
}
