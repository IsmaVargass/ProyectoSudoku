package ejercicio02.interfaces;

import com.ejercicio02.interfaces.IJuegoSudoku;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class IJuegoSudokuTest {

    @Test
    void testIniciarNoLanzaExcepcion() {
        // Implementación simulada de IJuegoSudoku usando lambda
        IJuegoSudoku juego = () -> {
            // Simulación vacía del inicio del juego
        };

        // Verifica que no se lance ninguna excepción al ejecutar iniciar()
        assertDoesNotThrow(juego::iniciar);
    }
}
