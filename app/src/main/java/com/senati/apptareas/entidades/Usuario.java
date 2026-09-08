package com.senati.apptareas.entidades;

public class Usuario {
    private int id;
    private String nombre;
    private String username;
    private String correo;
    private String password;

    // Constructor vacío obligatorio
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String username, String correo, String password) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.correo = correo;
        this.password = password;
    }

    // Métodos Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
