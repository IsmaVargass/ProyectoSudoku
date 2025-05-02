package ejercicio02.exception;

import com.ejercicio02.exception.MovimientoInvalidoException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class MovimientoInvalidoExceptionTest {

    @Test
    void constructor_MensajeContieneCoordenadasYValor() {
        // Creamos la excepción para fila=2, columna=3, valor=5
        MovimientoInvalidoException ex = new MovimientoInvalidoException(2,3,5);
        String msg = ex.getMessage();
        assertTrue(msg.contains("(3, 4)"),
                "El mensaje debe mostrar fila+1 y columna+1 (3,4)");
        assertTrue(msg.contains("5"),
                "El mensaje debe mencionar el valor 5");
        assertTrue(msg.toLowerCase().contains("bloque"),
                "El mensaje debe mencionar bloque 3x3");
    }
}

