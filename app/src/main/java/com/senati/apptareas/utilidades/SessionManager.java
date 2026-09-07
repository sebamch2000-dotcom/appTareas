package com.senati.apptareas.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    // Nombre del archivo de preferencias y estado
    private static final String PREF_NAME = "AppTareasSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    // Claves para almacenar los datos del usuario
    public static final String KEY_ID = "idUsuario";
    public static final String KEY_NOMBRE = "nombreUsuario";
    public static final String KEY_CORREO = "correoUsuario";

    public SessionManager(Context context) {
        this.context = context;
        // MODO_PRIVATE asegura que solo tu aplicación pueda acceder a estos datos
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // 1. Guardar los datos al iniciar sesión exitosamente
    public void crearSesionLogin(int id, String nombre, String correo) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_CORREO, correo);
        editor.apply();
    }

    // 2. Verificar si el usuario ya tiene una sesión abierta
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    // 3. Obtener el nombre del usuario (Ideal para el campo Usuario Asignado del Formulario)
    public String getNombreUsuario() {
        return pref.getString(KEY_NOMBRE, "");
    }

    // 4. Obtener todos los detalles del usuario en caso de necesitarlos
    public HashMap<String, String> getDetallesUsuario() {
        HashMap<String, String> usuario = new HashMap<>();
        usuario.put(KEY_ID, String.valueOf(pref.getInt(KEY_ID, -1)));
        usuario.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE, null));
        usuario.put(KEY_CORREO, pref.getString(KEY_CORREO, null));
        return usuario;
    }

    // 5. Limpiar datos al cerrar sesión
    public void cerrarSesion() {
        editor.clear();
        editor.apply();
    }
}