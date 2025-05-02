package ejercicio02.interfaces;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.IResolverSudoku;
import com.ejercicio02.model.Sudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IResolverSudokuTest {

    @Test
    void testObtenerPistaDevuelveCoordenadas() throws SudokuException {
        IResolverSudoku resolver = new IResolverSudoku() {
            public int[] obtenerPista(Sudoku sudoku) { return new int[] {0, 0, 5}; }
            public void resolver(Sudoku sudoku) {}
        };

        int[] pista = resolver.obtenerPista(new Sudoku());
        assertNotNull(pista);
        assertEquals(3, pista.length);
    }

    @Test
    void testResolverNoLanzaExcepcion() {
        IResolverSudoku resolver = new IResolverSudoku() {
            public int[] obtenerPista(Sudoku sudoku) { return new int[0]; }
            public void resolver(Sudoku sudoku) {}
        };

        assertDoesNotThrow(() -> resolver.resolver(new Sudoku()));
    }
}
