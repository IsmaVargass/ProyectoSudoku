package ejercicio02.interfaces;

import com.ejercicio02.generator.GeneradorSudoku;
import com.ejercicio02.interfaces.IGeneradorSudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IGeneradorSudokuTest {

    @Test
    void testGenerarSudokuFacil() {
        IGeneradorSudoku generador = new GeneradorSudoku();
        int[][] tablero = generador.generar("Fácil");

        assertNotNull(tablero);
        assertEquals(9, tablero.length);
        for (int[] fila : tablero) {
            assertEquals(9, fila.length);
        }
    }
}
