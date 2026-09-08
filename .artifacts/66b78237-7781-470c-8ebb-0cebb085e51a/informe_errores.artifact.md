# Informe de Errores Corregidos - AppTareas

Este documento resume los fallos críticos encontrados y corregidos en la aplicación.

## 1. LoginActivity.java
*   **Error de Lógica (Comparación de Texto):** Se usaba `correo.toString()` en lugar de `correo.getText().toString()`. En Android, `toString()` sobre una vista devuelve la representación del objeto, no el texto ingresado.
*   **Error de Sintaxis (Operador ==):** Se comparaban Strings con `==`. En Java, se debe usar `.equals()` para comparar el contenido de dos cadenas.
*   **Funcionalidad Faltante:** No existía el evento para navegar a la pantalla de Registro (`tvIrRegistro`).
*   **Navegación Errónea:** El login exitoso enviaba a `FormularioActivity` en lugar de `MainActivity`.

## 2. FormularioActivity.java
*   **Crash por NullPointerException:** La variable `btnGuardarTarea` no estaba inicializada con `findViewById`, lo que causaba un cierre inmediato al intentar usar el botón.
*   **Crash por ClassCastException:** Se intentaba convertir un `AutoCompleteTextView` (definido en el XML) en un `Spinner` (definido en Java).
*   **Vistas Mal Declaradas:** Se usaba `TextView` para campos de entrada de texto (`EditText`), lo que impedía el uso correcto de métodos como `getText()`.
*   **Adaptador no Asignado:** Se creaba el `ArrayAdapter` pero no se vinculaba a la vista de selección de estado.

## 3. MainActivity.java
*   **Inconsistencia de Tipos:** Se declaraban los filtros como `Button` cuando en el XML son de tipo `Chip`.
*   **Navegación Faltante:** El botón flotante (FAB) para agregar tareas no tenía ninguna acción programada.

## 4. Pantalla de Carga y Login
*   **Nueva Clase (SplashActivity):** Se creó la lógica para mostrar el logo de la app por 2 segundos antes de ir al Login.
*   **Configuración del Manifest:** Se cambió el `LAUNCHER` de `LoginActivity` a `SplashActivity`.
*   **Flexibilidad en Login:** Ahora se permite ingresar con la cuenta de prueba `usuario` (clave: `1234`) además de `admin` y los usuarios registrados en la base de datos.
*   **Feedback al Usuario:** Se añadió un `Toast` para avisar si el login falló.

## 5. Cambio de Autenticación (Correo por Usuario)
*   **Base de Datos Actualizada:** Se añadió la columna `usuario` a la tabla de usuarios y se incrementó la versión de la DB a 2.
*   **Registro Mejorado:** Ahora se solicita un "Nombre de Usuario" único al registrarse.
*   **Login Simplificado:** El inicio de sesión ya no pide el correo, sino el nombre de usuario elegido, facilitando el acceso.

## 6. Asignación de Usuarios en Tareas
*   **Sugerencias Inteligentes:** El campo "Usuario asignado" en el formulario de tareas ahora muestra una lista desplegable con todos los usuarios registrados en la base de datos.
*   **Autocompletado:** Al empezar a escribir, la app te sugerirá nombres de usuario válidos (incluyendo los de prueba como `admin` y `usuario`), permitiéndote asignarte una tarea a ti mismo o a otros rápidamente.

## 7. Diseño Visual y Estados de Tarea
*   **Código de Colores Semánticos:** Se han implementado colores pastel para diferenciar los estados:
    *   **Verde Clarito:** Tareas Completadas.
    *   **Amarillo Clarito:** Tareas en Progreso.
    *   **Gris Clarito:** Tareas Pendientes.
    *   **Rojo Clarito:** Tareas que "No se pudieron completar".
*   **Nuevo Estado:** Se añadió la opción "No se pudo completar" en el formulario y un nuevo filtro en la pantalla principal ("No Logradas").
*   **Gestión de Tareas:** Se aseguró que el botón de eliminar esté disponible en todas las tareas, permitiendo una limpieza rápida de la lista.

---
## Pasos de Mantenimiento Realizados
*   **Gradle Sync:** Se ha ejecutado una sincronización completa de los archivos de Gradle para asegurar que las dependencias y las clases generadas (R.java) estén actualizadas tras las correcciones.
*   **Limpieza de Caché:** Se recomienda realizar un `Build > Clean Project` si alguna vista aún no es reconocida por el editor.

> [!IMPORTANT]
> Los cambios realizados en las actividades (`MainActivity`, `LoginActivity` y `FormularioActivity`) dependen de que el archivo `build.gradle` esté correctamente sincronizado. Este paso ya ha sido iniciado por el asistente.
