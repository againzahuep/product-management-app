# Product Management App
Esta aplicación móvil Android desarrollada en Kotlin funciona como una herramienta de gestión de aprobación de productos (To-Do), permitiendo revisar productos, aprobarlos o rechazarlos, y gestionar listas de productos pendientes y revisados.


Estructura de la aplicación:

Arquitectura MVVM para una clara separación de responsabilidades
Organización modular del código para facilitar el mantenimiento
Uso de patrones de diseño como Repository y Adapter


Pantallas principales:

Productos por revisar: Lista de 10 productos de una API con checkboxes para aprobar/rechazar
Productos revisados: Lista con scroll infinito y paginación (7 productos por página)
Cards con diseño limpio basado en Material Design


Funcionalidades:

Eliminar productos de la lista
Ver detalles del producto en un diálogo modal
Almacenamiento local con Room Database
Consumo de API mediante Retrofit con un patrón adaptador


Tecnologías utilizadas:

Room para la base de datos local
Retrofit para la comunicación con la API
Paging 3 para implementar el scroll infinito
Navigation Component para la navegación entre fragmentos
Dagger Hilt para la inyección de dependencias
Glide para la carga de imágenes
Material Design para la interfaz de usuario

## Características principales

- **Pantalla de Productos por Revisar**: Muestra una lista de 10 productos obtenidos de una API externa, con opciones para aprobar o rechazar.
- **Pantalla de Productos Revisados**: Lista con scroll infinito y paginación (7 productos por página) de productos ya aprobados o rechazados.
- **Funcionalidades**:
  - Eliminar productos de la lista
  - Ver detalles del producto en un diálogo
  - Almacenamiento local con Room
  - Consumo de API mediante el patrón adaptador

## Arquitectura y tecnologías

- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Persistencia de datos**: Room Database
- **Networking**: Retrofit con patrón adaptador
- **Inyección de dependencias**: Dagger Hilt
- **UI**: Material Design
- **Navegación**: Navigation Component
- **Paginación**: Paging 3 Library
- **Carga de imágenes**: Glide

## Estructura del proyecto

```
app/
├── data/
│   ├── api/         - Interfaces y adaptadores para consumo de API
│   ├── db/          - Configuración de base de datos Room y DAOs
│   ├── model/       - Modelos de datos
│   └── repository/  - Repositorios que coordinan los datos
├── di/              - Módulos de inyección de dependencias
└── ui/              - Componentes de la interfaz de usuario
    ├── pendingreview/ - Pantalla de productos por revisar
    ├── reviewed/      - Pantalla de productos revisados
    └── components/    - Componentes reutilizables
```

## Requisitos

- Android Studio Arctic Fox o superior
- Android SDK 23 o superior
- Gradle 7.0 o superior

## Instalación

1. Clone este repositorio:
   ```bash
   git clone https://github.com/username/product-management-app.git

	Please add this address like API REST URL in src/AppModule.kt like baseUrl
	https://67dc716ce00db03c40681aac.mockapi.io/v1/products 
	
	Like this line
	@Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://67dc716ce00db03c40681aac.mockapi.io/v1/products")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


	Use Java 17 or Java 11+
	Add JDK location to JAVA_HOME SYSTEM_PATH Variables
	
	Important
	Use Gradle Version 8.12.1 or latest 
   ```

2. Abra el proyecto en Android Studio.

3. Sincronice el proyecto con los archivos Gradle.

4. Ejecute la aplicación en un emulador o dispositivo físico.

## Configuración de la API

La aplicación está configurada para utilizar [Fake Store API](https://fakestoreapi.com/) como fuente de datos. Si desea cambiar la API:

1. Modifique la URL base en `AppModule.kt`:
   ```kotlin
   fun provideRetrofit(): Retrofit {
       return Retrofit.Builder()
           .baseUrl("https://su-nueva-api.com/")
           // ...
   }
   ```

2. Ajuste el modelo `Product.kt` según la estructura de datos de su API.

