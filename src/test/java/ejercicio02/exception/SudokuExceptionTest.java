package ejercicio02.exception;

import com.ejercicio02.exception.SudokuException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SudokuExceptionTest {

    @Test
    void constructor_YConstructorConCausa() {
        // Excepción base con mensaje
        SudokuException ex1 = new SudokuException("Error general");
        assertEquals("Error general", ex1.getMessage());

        // Excepción con causa
        RuntimeException cause = new RuntimeException("causa");
        SudokuException ex2 = new SudokuException("Otro error", cause);
        assertEquals("Otro error", ex2.getMessage());
        assertSame(cause, ex2.getCause());
    }
}
