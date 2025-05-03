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
            @Override
            public int[] obtenerPista(Sudoku sudoku) throws SudokuException {
                return new int[]{0, 0, 5};
            }
            @Override
            public void resolver(Sudoku sudoku) throws SudokuException {
                // implementación vacía
            }
        };

        int[] pista = resolver.obtenerPista(new Sudoku());
        assertNotNull(pista, "La pista no debe ser nula");
        assertEquals(3, pista.length, "La pista debe contener 3 elementos");
    }

    @Test
    void testResolverNoLanzaExcepcion() {
        IResolverSudoku resolver = new IResolverSudoku() {
            @Override
            public int[] obtenerPista(Sudoku sudoku) throws SudokuException {
                return new int[0];
            }
            @Override
            public void resolver(Sudoku sudoku) throws SudokuException {
                // no lanza excepción
            }
        };

        assertDoesNotThrow(() -> resolver.resolver(new Sudoku()),
                "El método resolver no debe lanzar excepción");
    }
}
