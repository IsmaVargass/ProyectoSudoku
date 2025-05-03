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

            @Override
            public void generarTablero(String dificultad) throws SudokuException {
                // implementación vacía para pruebas
            }

            @Override
            public boolean esMovimientoValido(int fila, int columna, int valor) throws SudokuException {
                return true;
            }

            @Override
            public void colocarNumero(int fila, int columna, int valor) throws SudokuException {
                grid[fila][columna] = valor;
            }

            @Override
            public boolean estaResuelto() {
                return false;
            }

            @Override
            public void mostrarTablero() {
                // no hace nada
            }

            @Override
            public int getValor(int fila, int columna) {
                return grid[fila][columna];
            }

            @Override
            public void setValor(int fila, int columna, int valor) {
                grid[fila][columna] = valor;
            }
        };
    }

    @Test
    void testSetYGetValor() {
        sudoku.setValor(0, 0, 7);
        assertEquals(7, sudoku.getValor(0, 0),
                "getValor debe devolver el valor seteado con setValor");
    }

    @Test
    void testEsMovimientoValido() throws SudokuException {
        assertTrue(sudoku.esMovimientoValido(0, 1, 5),
                "esMovimientoValido debe devolver true en este stub");
    }

    @Test
    void testEstaResuelto() {
        assertFalse(sudoku.estaResuelto(),
                "estaResuelto debe devolver false en este stub");
    }
}
