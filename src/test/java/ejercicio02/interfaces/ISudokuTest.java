package ejercicio02.interfaces;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.ISudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ISudokuTest {

    private ISudoku sudoku;

    @BeforeEach
    void setUp() {
        sudoku = new ISudoku() {
            private int[][] grid = new int[9][9];

            public void generarTablero(String dificultad) {}
            public boolean esMovimientoValido(int fila, int columna, int valor) { return true; }
            public void colocarNumero(int fila, int columna, int valor) {}
            public boolean estaResuelto() { return false; }
            public void mostrarTablero() {}
            public int getValor(int fila, int columna) { return grid[fila][columna]; }
            public void setValor(int fila, int columna, int valor) { grid[fila][columna] = valor; }
        };
    }

    @Test
    void testSetYGetValor() {
        sudoku.setValor(0, 0, 7);
        assertEquals(7, sudoku.getValor(0, 0));
    }

    @Test
    void testEsMovimientoValido() throws SudokuException {
        assertTrue(sudoku.esMovimientoValido(0, 1, 5));
    }

    @Test
    void testEstaResuelto() {
        assertFalse(sudoku.estaResuelto());
    }
}
