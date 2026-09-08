package com.senati.apptareas.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {
    private DatabaseHelper dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Operación: Registrar un nuevo usuario
    public long registrarUsuario(String nombre, String username, String correo, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.COL_USER_NAME, nombre);
        valores.put(DatabaseHelper.COL_USER_USERNAME, username);
        valores.put(DatabaseHelper.COL_USER_EMAIL, correo);
        valores.put(DatabaseHelper.COL_USER_PASSWORD, password);

        long id = db.insert(DatabaseHelper.TABLA_USUARIOS, null, valores);
        db.close();
        return id;
    }

    // Operación: Validar Login (Verifica si coincide usuario y contraseña)
    public boolean verificarUsuario(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columnas = {DatabaseHelper.COL_USER_ID};
        String seleccion = DatabaseHelper.COL_USER_USERNAME + " = ?" + " AND " + DatabaseHelper.COL_USER_PASSWORD + " = ?";
        String[] seleccionArgs = {username, password};

        Cursor cursor = db.query(DatabaseHelper.TABLA_USUARIOS, columnas, seleccion, seleccionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        return cursorCount > 0;
    }

    // Operación: Obtener lista de todos los nombres de usuario
    public java.util.List<String> obtenerTodosLosUsernames() {
        java.util.List<String> usuarios = new java.util.ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLA_USUARIOS, new String[]{DatabaseHelper.COL_USER_USERNAME}, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                usuarios.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return usuarios;
    }
}
