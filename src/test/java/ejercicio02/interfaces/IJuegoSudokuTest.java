package ejercicio02.interfaces;

import com.ejercicio02.interfaces.IJuegoSudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class IJuegoSudokuTest {


    @Test
    void testIniciarNoLanzaExcepcion() {
        IJuegoSudoku juego = () -> {
            // Simulación vacía
        };

        assertDoesNotThrow(juego::iniciar);
    }
}
