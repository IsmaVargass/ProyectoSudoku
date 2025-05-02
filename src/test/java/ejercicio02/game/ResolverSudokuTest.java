package ejercicio02.game;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.game.ResolverSudoku;
import com.ejercicio02.model.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ResolverSudokuTest {

    @Test
    void testResolverSudokuResuelveCorrectamente() throws SudokuException {
        ResolverSudoku resolver = new ResolverSudoku();
        Sudoku sudoku = new Sudoku();

        // Tablero con solo una celda vacía
        sudoku.setValor(0, 0, 0);
        for (int i = 1; i < 9; i++) {
            sudoku.setValor(0, i, i + 1);
        }

        // Ejecutamos el método
        resolver.resolver(sudoku);

        // Comprobamos que se ha llenado la celda
        assertTrue(sudoku.getValor(0, 0) > 0);
    }

    //TODO: El test tardó demasiado; probablemente hay recursión infinita
    @Test
    void testResolverSudokuLanzaExcepcionConTimeout() {
        ResolverSudoku resolver = new ResolverSudoku();
        Sudoku sudoku = new Sudoku();

        // Sudoku mal diseñado (mismo número en fila)
        sudoku.setValor(0, 0, 5);
        sudoku.setValor(0, 1, 5);

        Thread t = new Thread(() -> {
            assertThrows(SudokuException.class, () -> resolver.resolver(sudoku));
        });
        t.start();

        try {
            t.join(2000); // Espera 2 segundos
        } catch (InterruptedException e) {
            fail("Test interrumpido inesperadamente");
        }

        if (t.isAlive()) {
            fail("El test tardó demasiado; probablemente hay recursión infinita");
        }
    }

    @Test
    void testObtenerPistaDevuelveCoordenadasYValorCorrecto() throws SudokuException {
        ResolverSudoku resolver = new ResolverSudoku();
        Sudoku sudoku = new Sudoku();

        // Solo una celda vacía
        sudoku.setValor(0, 0, 0);
        for (int i = 1; i < 9; i++) {
            sudoku.setValor(0, i, i + 1);
        }

        int[] pista = resolver.obtenerPista(sudoku);

        // Comprobamos que la pista corresponde a la celda vacía (0,0) con un número válido
        assertEquals(0, pista[0]);
        assertEquals(0, pista[1]);
        assertTrue(pista[2] >= 1 && pista[2] <= 9);
    }

    @Test
    void testObtenerPistaLanzaExcepcionSiNoHayCeldasVacias() {
        ResolverSudoku resolver = new ResolverSudoku();
        Sudoku sudoku = new Sudoku();

        // Llenamos el tablero
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                sudoku.setValor(fila, col, 1); // Valor repetido a propósito
            }
        }

        // Debe lanzar excepción por no haber celdas vacías
        assertThrows(SudokuException.class, () -> resolver.obtenerPista(sudoku));
    }
}
