# Documentación de Refactorización: Proyecto Pokédex


## 1. Nueva Estructura del Proyecto

Se ha organizado el código en paquetes específicos para evitar que los archivos crezcan demasiado y para que cada clase tenga una única responsabilidad clara:

```text
edu.uade.primerparcial
│
├── data
│   ├── Pokemon.kt             <-- Modelo de datos
│   └── PokemonRepository.kt   <-- Gestión de datos
│
├── logic
│   └── PokemonViewModel.kt    <-- Lógica de negocio y Estado de la UI
│
├── ui
│   └── PokemonScreens.kt      <-- Componentes visuales (Composables)
│
└── MainActivity.kt            <-- Punto de entrada y coordinación de navegación
```

---

## 2. Cambios por Capa

### 2.1. Capa de Datos (`data/`)
- **Separación del modelo:** La clase `Pokemon` fue extraída a su propio archivo `Pokemon.kt`, separándola del repositorio.
- **Responsabilidad única:** `PokemonRepository` queda exclusivamente a cargo de proveer los datos (lista estática, preparada para una API futura).

### 2.2. Capa de Lógica (`logic/`)
- **StateFlow directo:** El `ViewModel` expone `StateFlow<List<Pokemon>>` como `pokemons`. La UI observa la lista directamente.
- **Encapsulamiento:** Se usa el patrón de respaldo privado (`_pokemons`) con exposición pública de solo lectura (`pokemons`), garantizando que solo el `ViewModel` pueda modificar el estado.

### 2.3. Capa de Vista (`ui/`)
- **Pantallas pasivas:** `PokemonListScreen` y `PokemonItem` viven en `PokemonScreens.kt`.
- **Observación directa:** La pantalla observa `viewModel.pokemons` con `collectAsState()` y renderiza la lista sin lógica adicional.

---

## 3. Ajustes Lógicos y Buenas Prácticas

1. **Encapsulamiento del Estado:** Se utiliza `StateFlow` con respaldo privado (`_pokemons`) y versión pública de solo lectura (`pokemons`).
2. **Limpieza de la Activity:** `MainActivity` se redujo a su mínima expresión, delegando todo en Compose y el ViewModel.
3. **Preparación para Escalabilidad:** Al separar el Repositorio, el proyecto queda listo para reemplazar la lista estática por una llamada a la API (Retrofit) sin modificar la UI.

---

## 4. Conclusión del Refactor

El proyecto pasó de ser un archivo único con muchas responsabilidades mezcladas a un sistema modular donde:
- **La Vista** solo dibuja.
- **La Lógica** coordina el estado.
- **Los Datos** proveen la información.
