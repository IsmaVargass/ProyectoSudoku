package ejercicio02.model;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.model.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
                assertEquals(0, sudoku.getValor(fila, col));
            }
        }
    }

    @Test
    void copiarSudoku_CreaCopiaIndependiente() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        Sudoku copia = new Sudoku(sudoku);
        assertEquals(5, copia.getValor(0, 0));
        sudoku.colocarNumero(0, 1, 3);  // modificamos original
        assertEquals(0, copia.getValor(0, 1));  // la copia no cambia
    }

    @Test
    void setYGetValor() {
        sudoku.setValor(2, 3, 7);
        assertEquals(7, sudoku.getValor(2, 3));
    }

    @Test
    void estaResuelto_CuandoHayCeros() {
        assertFalse(sudoku.estaResuelto());
    }

    @Test
    void estaResuelto_CuandoEstaLleno() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                sudoku.setValor(fila, col, 1);  // valor no importa
            }
        }
        assertTrue(sudoku.estaResuelto());
    }

    @Test
    void esMovimientoValido_CuandoEsCorrecto() {
        assertTrue(sudoku.esMovimientoValido(0, 0, 5));
    }

    @Test
    void esMovimientoValido_ValorRepetidoFila() throws SudokuException {
        sudoku.colocarNumero(0, 1, 5);
        assertFalse(sudoku.esMovimientoValido(0, 2, 5));
    }

    @Test
    void esMovimientoValido_ValorRepetidoColumna() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        assertFalse(sudoku.esMovimientoValido(1, 0, 5));
    }

    @Test
    void esMovimientoValido_ValorRepetidoBloque() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        assertFalse(sudoku.esMovimientoValido(1, 1, 5)); // en el mismo bloque 3x3
    }

    @Test
    void colocarNumero_ValorFueraDeRango_LanzaExcepcion() {
        assertThrows(SudokuException.class, () -> sudoku.colocarNumero(0, 0, 0));
        assertThrows(SudokuException.class, () -> sudoku.colocarNumero(0, 0, 10));
    }

    @Test
    void colocarNumero_ConflictosFilaColumnaBloque() throws SudokuException {
        sudoku.colocarNumero(0, 0, 5);
        assertThrows(SudokuException.class, () -> sudoku.colocarNumero(0, 1, 5), "Repetido en fila");
        assertThrows(SudokuException.class, () -> sudoku.colocarNumero(1, 0, 5), "Repetido en columna");
        assertThrows(SudokuException.class, () -> sudoku.colocarNumero(1, 1, 5), "Repetido en bloque");
    }

    @Test
    void colocarNumero_Valido_ColocaCorrectamente() throws SudokuException {
        sudoku.colocarNumero(0, 0, 3);
        assertEquals(3, sudoku.getValor(0, 0));
    }

    @Test
    void getGrid_DevuelveTablero() {
        assertNotNull(sudoku.getGrid());
        assertEquals(9, sudoku.getGrid().length);
        assertEquals(9, sudoku.getGrid()[0].length);
    }

    @Test
    void generarTablero_CreaCeldasFijasYResuelve() throws SudokuException {
        sudoku.generarTablero("facil");
        int[][] grid = sudoku.getGrid();
        boolean tieneAlMenosUnNumero = false;
        for (int[] fila : grid) {
            for (int val : fila) {
                if (val != 0) {
                    tieneAlMenosUnNumero = true;
                    break;
                }
            }
        }
        assertTrue(tieneAlMenosUnNumero, "Debe tener algunos valores generados");
    }

    @Test
    void mostrarTablero_NoLanzaExcepcion() {
        assertDoesNotThrow(() -> sudoku.mostrarTablero());
    }
}
