# appTareas - Gestor de Tareas 📝

Aplicación móvil nativa para Android desarrollada en Java que permite gestionar actividades diarias mediante un sistema de estados. El proyecto implementa persistencia de datos offline, autenticación de usuarios y una arquitectura modular, desarrollado como parte de las prácticas de Ingeniería de Software con Inteligencia Artificial en SENATI.

## 🚀 Características Principales

*   🔐 **Autenticación Local:** Sistema de registro e inicio de sesión seguro.
*   💾 **Persistencia de Sesión:** Uso de `SharedPreferences` (SessionManager) para mantener la sesión del usuario activa.
*   ✅ **Gestión Completa (CRUD):** Creación, lectura, actualización y eliminación de tareas.
*   🏷️ **Filtrado Dinámico:** Organización de tareas mediante Chips interactivos según su estado: *Pendiente*, *En Progreso* y *Completada*.
*   🛡️ **UX Defensiva:** Implementación de `AlertDialog` nativos para confirmar acciones destructivas (eliminar tareas o cerrar sesión).
*   🎨 **Diseño Moderno:** Interfaz construida con Material Components, incluyendo `MaterialCardView`, menús desplegables sin teclado y `FloatingActionButton`.

## 🏗️ Arquitectura del Proyecto

El código fuente está estructurado de manera modular para separar responsabilidades y facilitar su escalabilidad:

*   📂 **`actividades`**: Controladores de la interfaz de usuario (Splash, Login, Registro, Main, Formulario).
*   📂 **`adaptadores`**: Clases puente como `TareaAdapter` para renderizar listas dinámicas en el `RecyclerView`.
*   📂 **`basededatos`**: Configuración de SQLite (`DatabaseHelper`) y abstracción de consultas SQL mediante el **Patrón DAO** (`TareaDAO`, `UsuarioDAO`).
*   📂 **`entidades`**: Modelos de datos del sistema (`Tarea`, `Usuario`).
*   📂 **`utilidades`**: Herramientas transversales como el manejo de sesiones.

## 🛠️ Tecnologías Utilizadas

*   **Lenguaje:** Java
*   **Entorno de Desarrollo:** Android Studio
*   **Base de Datos:** SQLite (Local/Offline)
*   **UI/UX:** XML, Material Design 3

## ⚙️ Instalación y Ejecución

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone [https://github.com/tu-usuario/appTareas.git](https://github.com/tu-usuario/appTareas.git)
