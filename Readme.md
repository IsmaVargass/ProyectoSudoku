# 📚 Documentación del Proyecto Sudoku



[![Stars](https://img.shields.io/github/stars/IsmaVargass/ProyectoSudoku.svg?style=social&label=Stars)](https://github.com/IsmaVargass/ProyectoSudoku/stargazers)

[![Forks](https://img.shields.io/github/forks/IsmaVargass/ProyectoSudoku.svg?style=social&label=Forks)](https://github.com/IsmaVargass/ProyectoSudoku/network/members)

[![Issues](https://img.shields.io/github/issues/IsmaVargass/ProyectoSudoku.svg)](https://github.com/IsmaVargass/ProyectoSudoku/issues)

[![Last Commit](https://img.shields.io/github/last-commit/IsmaVargass/ProyectoSudoku.svg)](https://github.com/IsmaVargass/ProyectoSudoku/commits/master/)

[![Repo Size](https://img.shields.io/github/repo-size/IsmaVargass/ProyectoSudoku.svg)](https://github.com/IsmaVargass/ProyectoSudoku)

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Made with Java](https://img.shields.io/badge/Made%20with-Java-orange.svg)](https://www.java.com/)

## 📝 Documentación Técnica

> **Importante:** Puedes descargar la documentación completa (incluye diagrama UML):
> [📄 Documentación Técnica (PDF)](docs/Documentacion_Tecnica_Sudoku.pdf)


## 📑 Tabla de Contenidos
1. [Visión General](#-visión-general)
2. [Estructura de Carpetas](#-estructura-de-carpetas)
3. [Paquetes y Clases Principales](#️-paquetes-y-clases-principales)
4. [Pruebas Unitarias (JUnit)](#-pruebas-unitarias-junit)
5. [Cómo jugar](#️-cómo-jugar)
6. [Recursos y Agradecimientos](#-recursos-y-agradecimientos)

## 🎯 Visión General
[Visualizar esquema UML](docs/UML.md) 🖼️

Este proyecto implementa un **juego de Sudoku** en Java con:
- Generación aleatoria de puzzles según dificultad.
- Resolución automática y obtención de pistas.
- Interfaz gráfica (Swing) con validación en tiempo real.
- Manejo de excepciones personalizado.
- Pruebas unitarias con JUnit.

---

## 📁 Estructura de Carpetas
```
ProyectoSudoku/
├── src/
│   ├── main/java/com/ejercicio02/
│   │   ├── exception/            ⚠️ Excepciones personalizadas
│   │   ├── game/                 🎮 Lógica del juego y resolución
│   │   ├── generator/            🧩 Generación de puzzles
│   │   ├── gui/                  🖼️ Interfaz gráfica (Swing)
│   │   ├── interfaces/           📝 Contratos (interfaces)
│   │   └── model/                📦 Modelo de datos (clase Sudoku)
│   └── resources/                🎵 Recursos (gifs, imágenes)
└── test/java/                   ✅ Pruebas unitarias JUnit
    └── com/ejercicio02/
        ├── GeneradorSudokuTest.java
        ├── IGeneradorSudokuTest.java
        ├── IJuegoSudokuTest.java
        ├── IResolverSudokuTest.java
        ├── ISudokuTest.java
        ├── ISudokuGUITest.java
        ├── JuegoSudokuTest.java
        ├── ResolverSudokuTest.java
        ├── SudokuGUITest.java
        └── SudokuTest.java
```

---

## ⚙️ Paquetes y Clases Principales

### 1. `com.ejercicio02.exception` ⚠️
- **SudokuException.java**: Excepción base para errores generales.
- **MovimientoInvalidoException.java**: Para movimientos que violan reglas de fila/columna/bloque.
- **EntradaFueraDeRangoException.java**: Para valores fuera del rango 1–9.

> **Tip:** Cada excepción incluye un mensaje descriptivo y sugerencias de corrección.

### 2. `com.ejercicio02.generator` 🧩
- **GeneradorSudoku.java** (IGeneradorSudoku):
    - `generar(String dificultad)`: Genera un puzzle con diferentes celdas vacías según dificultad.
    - `rellenarSolucion(int[][] grid)`: Algoritmo backtracking **aleatorio** para crear solución.
    - `contarSoluciones(...)`: Comprueba unicidad de la solución.

> **Nota:** Se usa `Collections.shuffle` para variabilidad en cada ejecución.

### 3. `com.ejercicio02.model` 📦
- **Sudoku.java** (ISudoku):
    - Modelo del Sudoku con tablero 9×9.
    - Métodos: `generarTablero`, `getValor`, `setValor`, `colocarNumero`, `estaResuelto`, `mostrarTablero`.
    - Lógica de validación y backtracking。

### 4. `com.ejercicio02.game` 🎮
- **ResolverSudoku.java** (IResolverSudoku): Algoritmo para resolver un Sudoku existente.
- **JuegoSudoku.java** (IJuegoSudoku): Lógica de partida: iniciar, pista, resolver completo.
- **Main.java**: Punto de entrada (opcional).

### 5. `com.ejercicio02.gui` 🖼️
- **SudokuGUI.java** (ISudokuGUI): Interfaz Swing:
    - Selección de dificultad.
    - Botones: Nueva partida, Pista, Resolver, Validación en tiempo real, Salir.
    - Tablero interactivo con **DocumentListeners** y colores: ✅ Verde, ❌ Rojo.
    - Mensaje final con **Volver a jugar**.

### 6. `com.ejercicio02.interfaces` 📝
- **IGeneradorSudoku.java**
- **ISudoku.java**
- **IResolverSudoku.java**
- **IJuegoSudoku.java**
- **ISudokuGUI.java**

> **Beneficio:** Facilita pruebas unitarias con inyección de dependencias.

---

## ✅ Pruebas Unitarias (JUnit)
Carpeta: `test/java/com/ejercicio02`

**Tipos de pruebas:**
- 🔹 **Positivas:** Entradas válidas y comportamiento esperado.
- 🔹 **Negativas:** Valores fuera de rango, celdas fijas.
- 🔹 **Borde:** Validación de límites de fila/columna y subcuadro.

Ejemplos:
- **SudokuTest.java**: `setValor` válido/inválido, `estaResuelto()`.
- **GeneradorSudokuTest.java**: Tablero de salida único, cantidad de vacíos según dificultad.
- **ResolverSudokuTest.java**: Resolver tableros resueltos/irresolubles.

> **Nota:** Uso `@BeforeEach` para inicializar Sudoku limpio.

---

## ▶️ Cómo jugar
1. **Clona el repositorio** desde GitHub:
   ```bash
   git clone https://github.com/TuUsuario/ProyectoSudoku.git
   ```
2. **Abre la carpeta del proyecto** y navega a la ruta `src/main/java/com/ejercicio02/game`.
3. **Ejecuta la clase `Main.java`** para iniciar el juego:
   ```bash
   cd src/main/java/com/ejercicio02/game
   javac *.java
   java Main
   ```

---

## 🙌 Recursos y Agradecimientos

Durante el desarrollo de este proyecto, me he apoyado en las siguientes fuentes de conocimiento y herramientas:

#### 📺 YouTube
- [Lógica de Backtracking para resolver Sudoku](https://youtu.be/3NOYR2T8zTo?si=SKSaknWNNFnpzejA)
- [Resolviendo Sudoku en Java](https://youtu.be/N-eoEQiZWw0?si=5R7EB-1Q-G7mNbQI)
- [Aplicación Sudoku Java](https://youtu.be/-AgMyQmSvNc?si=nqwmaPAyF5I9bsi7)
- [Creación Sudoku desde cero](https://youtu.be/IIb-kxfH4Lw?si=iUjSz9wXj9dBrdot)   

#### 🤖 ChatGPT
- [ChatGPT de OpenAI](https://chat.openai.com): Asistencia en la organización de clases, patrones de diseño y resolución de dudas sobre algoritmos y pruebas.

#### 📘 Documentación oficial de Java
- [Collections](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html)
- [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Manejo de excepciones en Java](https://docs.oracle.com/javase/tutorial/essential/exceptions/)

#### 💬 Stack Overflow
- [https://stackoverflow.com](https://stackoverflow.com): Consulta de errores comunes y mejores prácticas en Java.

> ¡Gracias a todos los creadores de contenido y comunidades que comparten su conocimiento libremente! 🙌
>
¡Disfruta tu partida de Sudoku! 🎉
