package entidades;

public class Tarea {
    private int id;
    private String titulo;
    private String descripcion;
    private String estado; // Guardará: 'pendiente', 'en progreso' o 'completada'
    private String fechaVencimiento;
    private String fechaCreacion;
    private String usuarioAsignado;

    // Constructor vacío obligatorio
    public Tarea() {}

    // Constructor completo alineado al documento de requerimientos
    public Tarea(int id, String titulo, String descripcion, String estado, String fechaVencimiento, String fechaCreacion, String usuarioAsignado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaCreacion = fechaCreacion;
        this.usuarioAsignado = usuarioAsignado;
    }

    // Métodos Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(String fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getUsuarioAsignado() { return usuarioAsignado; }
    public void setUsuarioAsignado(String usuarioAsignado) { this.usuarioAsignado = usuarioAsignado; }
}
