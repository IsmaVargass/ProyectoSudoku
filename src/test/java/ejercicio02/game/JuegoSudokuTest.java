package ejercicio02.game;

import com.ejercicio02.game.JuegoSudoku;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JuegoSudokuTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream)); // Captura System.out
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);  // Restaura System.in
        System.setOut(originalOut); // Restaura System.out
    }

    @Test
    void testIniciarMuestraMenusYLeeEntradas() {
        // Simula entradas: dificultad válida + algunas jugadas válidas
        String input = String.join(System.lineSeparator(),
                "1", // dificultad: fácil
                "1", "1", "1", // jugada 1
                "2", "2", "2"  // jugada 2
        );
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            // Ejecuta el juego. Al agotarse la entrada, lanzará una excepción (esperada)
            JuegoSudoku juego = new JuegoSudoku();
            juego.iniciar();
        });

        String output = outputStream.toString();
        assertTrue(output.contains("Elige dificultad"));
        assertTrue(output.contains("Fila (1-9):"));
        assertTrue(output.contains("Columna (1-9):"));
    }
}
