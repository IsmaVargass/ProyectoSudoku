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

        int celdasVacias = 0;

        for (int[] fila : tablero) {
            assertEquals(9, fila.length);
            for (int val : fila) {
                assertTrue(val >= 0 && val <= 9, "Valor fuera de rango: " + val);
                if (val == 0) celdasVacias++;
            }
        }

        assertTrue(celdasVacias >= 30 && celdasVacias <= 45, "Cantidad de celdas vacías no adecuada para dificultad fácil");
    }
}
