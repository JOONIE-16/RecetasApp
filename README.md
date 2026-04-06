# 🍕 RecetasApp

Aplicación móvil Android para gestionar y compartir recetas de cocina.
Desarrollada como proyecto académico con arquitectura MVVM.

## 🛠️ Tech Stack

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje principal |
| Android Studio | IDE |
| Supabase | Base de datos en la nube (PostgreSQL) |
| Retrofit + OkHttp | Consumo de API REST |
| Gson | Serialización JSON |
| Coroutines | Operaciones asíncronas |
| ViewModel + LiveData | Arquitectura MVVM |
| Glide | Carga de imágenes |
| RecyclerView | Lista de recetas |
| CardView | Tarjetas de recetas |

## 📱 Funcionalidades

- ✅ Registro e inicio de sesión de usuarios
- ✅ Listado de recetas con búsqueda
- ✅ Crear nuevas recetas
- ✅ Eliminar recetas
- 🔜 Editar recetas
- 🔜 Filtros por categoría
- 🔜 Subida de imágenes

## 🏗️ Arquitectura MVVM

    com.yoonie.recetasapp
    ├── model/          → Clases de datos
    ├── network/        → Retrofit + ApiService
    ├── repository/     → Capa de datos
    ├── viewmodel/      → Lógica de negocio
    ├── ui/             → Activities
    └── MainActivity.kt → Login

## 🗄️ Base de Datos

Supabase (PostgreSQL) con 4 tablas:

- **users** — Usuarios registrados
- **categories** — Categorías de recetas
- **recipes** — Recetas creadas
- **likes** — Likes de usuarios

## 🚀 Configuración

1. Clona el repositorio
2. Abre el proyecto en Android Studio
3. Crea un proyecto en [Supabase](https://supabase.com)
4. En `network/RetrofitClient.kt` configura:

       private const val SUPABASE_URL = "TU_SUPABASE_URL"
       private const val SUPABASE_KEY = "TU_SUPABASE_ANON_KEY"

5. Conecta un dispositivo Android (API 24+)
6. Dale Run ▶

## 👨‍💻 Desarrollado por

**Jose Romero** — Proyecto académico
