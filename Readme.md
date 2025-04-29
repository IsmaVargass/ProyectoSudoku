# 📚 Documentación del Proyecto Sudoku

## 🎯 Visión General
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

¡Disfruta tu partida de Sudoku! 🎉
