package ejercicio02.model;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.model.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuTest {

    private Sudoku sudoku;

    @BeforeEach
    void setUp() {
        sudoku = new Sudoku();
    }

    @Test
    void constructor_CreaTableroVacio() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(0, sudoku.getValor(fila, col),
                        "Cada casilla debe comenzar en 0");
            }
        }
    }

    @Test
    void copiarSudoku_CreaCopiaIndependiente() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        Sudoku copia = new Sudoku(sudoku);
        assertEquals(5, copia.getValor(0, 0),
                "La copia debe reflejar el valor inicial");

        sudoku.colocarNumero(0, 1, 3);
        assertEquals(0, copia.getValor(0, 1),
                "Modificar el original no debe afectar a la copia");
    }

    @Test
    void setYGetValor() {
        sudoku.setValor(2, 3, 7);
        assertEquals(7, sudoku.getValor(2, 3),
                "getValor debe devolver lo que setValor puso");
    }

    @Test
    void estaResuelto_CuandoHayCeros() {
        assertFalse(sudoku.estaResuelto(),
                "Con celdas vacías debe devolver false");
    }

    @Test
    void estaResuelto_CuandoEstaLleno() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                sudoku.setValor(fila, col, 1);
            }
        }
        assertTrue(sudoku.estaResuelto(),
                "Con todas las celdas no cero debe devolver true");
    }

    @Test
    void esMovimientoValido_CuandoEsCorrecto() throws SudokuException {
        assertTrue(sudoku.esMovimientoValido(0, 0, 5),
                "En tablero vacío cualquier 1-9 debe ser válido");
    }

    @Test
    void esMovimientoValido_ValorRepetidoFila() throws SudokuException {
        sudoku.colocarNumero(0, 1, 5);
        assertFalse(sudoku.esMovimientoValido(0, 2, 5),
                "No debe permitir duplicar en la misma fila");
    }

    @Test
    void esMovimientoValido_ValorRepetidoColumna() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        assertFalse(sudoku.esMovimientoValido(1, 0, 5),
                "No debe permitir duplicar en la misma columna");
    }

    @Test
    void esMovimientoValido_ValorRepetidoBloque() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        assertFalse(sudoku.esMovimientoValido(1, 1, 5),
                "No debe permitir duplicar en el mismo bloque 3x3");
    }

    @Test
    void colocarNumero_ValorFueraDeRango_LanzaExcepcion() {
        assertThrows(SudokuException.class,
                () -> sudoku.colocarNumero(0, 0, 0),
                "Valor 0 está fuera de rango");
        assertThrows(SudokuException.class,
                () -> sudoku.colocarNumero(0, 0, 10),
                "Valor 10 está fuera de rango");
    }

    @Test
    void colocarNumero_ConflictosFilaColumnaBloque() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);

        assertThrows(SudokuException.class,
                () -> sudoku.colocarNumero(0, 1, 5),
                "Repetido en fila");

        assertThrows(SudokuException.class,
                () -> sudoku.colocarNumero(1, 0, 5),
                "Repetido en columna");

        assertThrows(SudokuException.class,
                () -> sudoku.colocarNumero(1, 1, 5),
                "Repetido en bloque");
    }

    @Test
    void colocarNumero_Valido_ColocaCorrectamente() throws SudokuException {
        sudoku.colocarNumero(0, 0, 3);
        assertEquals(3, sudoku.getValor(0, 0),
                "Debe colocar el valor correctamente");
    }

    @Test
    void getGrid_DevuelveTablero() {
        int[][] grid = sudoku.getGrid();
        assertNotNull(grid, "getGrid no debe devolver null");
        assertEquals(9, grid.length,
                "Debe ser matriz 9x9");
        assertEquals(9, grid[0].length,
                "Debe ser matriz 9x9");
    }

    @Test
    void generarTablero_CreaCeldasFijasYResuelve() throws SudokuException {
        sudoku.generarTablero("facil");
        int[][] grid = sudoku.getGrid();

        boolean tieneValor = false;
        for (int[] fila : grid) {
            for (int val : fila) {
                if (val != 0) {
                    tieneValor = true;
                    break;
                }
            }
        }
        assertTrue(tieneValor,
                "Al generar tablero debe haber al menos una celda no vacía");
    }

    @Test
    void mostrarTablero_NoLanzaExcepcion() {
        assertDoesNotThrow(() -> sudoku.mostrarTablero(),
                "mostrarTablero no debe lanzar excepción");
    }
}
