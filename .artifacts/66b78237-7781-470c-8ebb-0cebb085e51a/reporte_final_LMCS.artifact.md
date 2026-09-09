# Reporte Maestro de Exposición Técnica: Proyecto appTareas
**Grupo: LMCS (Levano, Joel, Chanta, Sebastian)**

---

## 🟢 PARTE 1: Liderazgo, Caso de Uso y Resultados Finales (Sebastian)
*Enfoque: Visión estratégica y demostración funcional.*

### 1. Concepto de Negocio
"Desarrollamos un **Gestor de Flujo de Trabajo Local**. En nuestro escenario, el Administrador centraliza la creación de tareas y asignación de responsables, garantizando que nada se pierda gracias a la persistencia en SQLite."

### 2. Demostración en Vivo y Resultados
*   **Identidad:** Splash Screen con marca del grupo y créditos.
*   **Seguridad:** Sistema de login que protege el acceso a la base de datos.
*   **Operatividad:** Demostración del **Doble Picker** (Fecha/Hora), filtros dinámicos y feedback visual (tachado).
*   **Git y Colaboración:** "Gestionamos el proyecto mediante ramas `feature/sebastian` para asegurar integraciones limpias del código de mis compañeros."
*   **Entrega:** APK final optimizado y funcional en dispositivos físicos.

---

## 🔵 PARTE 2: Arquitectura de Datos, Persistencia y Utilidades (Chanta)
*Enfoque: El corazón del sistema y la gestión de información.*

### 1. DatabaseHelper.java (El motor SQL)
*   **Lógica de Versiones:** `DATABASE_VERSION = 2`. "Al subir la versión, el método `onUpgrade` ejecuta `DROP TABLE` y recrea la estructura, permitiéndonos añadir el campo 'usuario' sin conflictos."
*   **Estructura:** Uso de constantes para los nombres de columnas (ej. `COL_TAREA_ESTADO`). Esto previene errores de escritura en las consultas.

### 2. Capa DAO (Data Access Object)
*   **TareaDAO.java:**
    *   `insertarTarea()`: Mapea los 7 campos del formulario usando `ContentValues` y `db.insert()`.
    *   `obtenerTareasPorEstado(String filtro)`: Implementa lógica dinámica. Si el filtro es "Todas", ejecuta un `SELECT` global; si no, aplica una selección `WHERE estado = ?`.
*   **UsuarioDAO.java:**
    *   `verificarUsuario()`: Clave para el Login. Realiza un `query` con una condición `AND` para validar usuario y clave simultáneamente.
    *   `obtenerTodosLosUsernames()`: Consulta vital para alimentar las sugerencias del formulario de tareas.

### 3. Entidades y Utilidades
*   **Tarea.java / Usuario.java:** Clases POJO con constructores completos y Getters/Setters que permiten la manipulación de datos como objetos reales.
*   **SessionManager.java:**
    *   **Lógica:** Usa `SharedPreferences` con `MODE_PRIVATE` para guardar el estado del login (`isLoggedIn`) y el nombre del usuario en la memoria del teléfono, evitando que el usuario deba loguearse cada vez que abre la app.

---

## 🟡 PARTE 3: Actividades y Lógica de Adaptadores (Santivallez)
*Enfoque: Control de flujo y sincronización de datos.*

### 1. Control de Actividades (.java)
*   **SplashActivity:** Uso de `Handler().postDelayed` para controlar la pausa de 2 segundos.
*   **LoginActivity:** Procesa las entradas con `getText().toString().trim()` y gestiona la navegación con `Intent` tras validar contra el DAO.
*   **MainActivity:** El centro neurálgico. Implementa `RecyclerView.setLayoutManager` y gestiona el refresco de la lista en `onResume()`.
*   **FormularioActivity:**
    *   **Lógica de Edición:** `getIntent().hasExtra("ID_TAREA")`. Si el ID existe, carga los datos de la DB para edición; de lo contrario, inicia una nueva inserción.
    *   **Encadenamiento de Diálogos:** Lógica técnica donde el `onDateSet` del calendario dispara automáticamente el `TimePickerDialog`.

### 2. TareaAdapter.java (El motor de la lista)
*   **ViewHolder:** Estructura técnica que 'infla' el layout `item_tarea.xml` y mantiene las referencias a las vistas para mejorar el rendimiento.
*   **onBindViewHolder (Feedback Visual):**
    *   Implementa el tachado de texto usando `Paint.STRIKE_THRU_TEXT_FLAG`.
    *   Aplica colores dinámicos al fondo de la tarjeta (`cardTarea.setCardBackgroundColor`) según el estado recuperado de la base de datos.

---

## 🔴 PARTE 4: Diseño de Interfaz y Recursos XML (Levano)
*Enfoque: Arquitectura visual y experiencia de usuario (UI/UX).*

### 1. Diseño de Layouts (Estructura XML)
*   **activity_splash.xml:** Uso de `RelativeLayout` para centrar el logo y `LinearLayout` para los créditos del grupo.
*   **activity_login.xml:** Diseño vertical con `TextInputLayout` de Material Design y botones con `cornerRadius` de 8dp para una estética moderna.
*   **activity_main.xml:** Implementa `CoordinatorLayout` para el botón flotante y un `AppBarLayout` con un `ChipGroup` deslizable horizontalmente.
*   **activity_formulario.xml:** Uso de `RelativeLayout` con un contenedor inferior fijo (`layoutBottom`) para asegurar que el botón de guardado siempre sea accesible.
*   **item_tarea.xml:** El diseño de la celda. Usa `MaterialCardView` con elevación y margen para crear profundidad visual.

### 2. Recursos y Psicología del Diseño
*   **colors.xml (Colores Pastel):**
    *   **Gris (#E0E0E0):** Pendiente.
    *   **Amarillo (#FFF9C4):** Progreso.
    *   **Verde (#C8E6C9):** Completada.
    *   **Rojo (#FFCDD2):** No Lograda.
*   **Fondo de Tarjetas:** Uso de variantes casi blancas (ej. `card_completada` en `#F8FFF8`) para un diseño limpio "Premium".
*   **Logo Vectorial:** Uso de `notepad_seeklogo.xml` para garantizar que el icono no se pixele en ninguna resolución de pantalla.

---

## 📋 Resumen Final del Grupo LMCS
"Nuestra aplicación integra una arquitectura sólida basada en **SQLite**, una lógica de negocio fluida en **Java** y una interfaz de usuario vanguardista en **XML**. El resultado es un producto listo para el mercado local."
