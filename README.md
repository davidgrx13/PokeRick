# PokeRick - Rick and Morty Android App

Aplicación nativa de Android desarrollada como prueba técnica. Permite ver los personajes de Rick y Morty mediante un listado infinito de personajes, ver sus detalles específicos y consultar los episodios en los que aparecen, consumiendo la [Rick and Morty API](https://rickandmortyapi.com/documentation).

## Características Principales

*   **Listado Infinito (Paginación):** Carga dinámica de personajes en bloques de 20 mediante scroll infinito.
*   **Búsqueda en tiempo real:** Filtrado de personajes por nombre con sistema de debounce para optimizar las peticiones de red.
*   **Vista de Detalle:** Información ampliada del personaje, encadenando llamadas a REST para obtener los datos de sus episodios donde aparece.
*   **Gestión de Estados de UI:** Manejo de estados de carga (loading y skeleton), éxito y error.

## Stack Tecnológico

*   **Lenguaje:** Kotlin
*   **UI:** Jetpack Compose
*   **Arquitectura:** MVVM + Clean Architecture
*   **Inyección de Dependencias:** Dagger Hilt
*   **Red:** Retrofit & Gson
*   **Imágenes:** Coil (carga asíncrona)

## Arquitectura

El proyecto está estructurado siguiendo los principios de Clean Architecture, dividiendo el código en tres capas principales:

1.  **Data (`data`):** Contiene los DTOs, la interfaz de Retrofit, los Mappers y la implementación del Repositorio.
2.  **Domain (`domain`):** Contiene los modelos puros de la app, la interfaz del repositorio y los `UseCases`.
3.  **Presentation (`ui`):** Implementada con Compose y ViewModels. Utiliza un patrón de estado exponiendo un único `StateFlow` hacia las vistas.

## Capturas de Pantalla
<img width="250" alt="Screenshot_20260731_074236" src="https://github.com/user-attachments/assets/a986676c-d955-4f37-8aab-33ce2b6748f9" /> <img width="250" alt="Screenshot_20260731_080124" src="https://github.com/user-attachments/assets/fb93198d-6e15-4a18-8359-d24bf010eb3f" /> 
---
**Autor:** David García Romero
