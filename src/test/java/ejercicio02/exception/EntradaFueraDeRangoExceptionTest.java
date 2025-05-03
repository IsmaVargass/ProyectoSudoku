package ejercicio02.exception;

import com.ejercicio02.exception.EntradaFueraDeRangoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntradaFueraDeRangoExceptionTest {

    @Test
    public void constructor_MensajeIncluyeValor() {
        // Al crear la excepción con un valor inválido, el mensaje debe mencionar ese valor
        EntradaFueraDeRangoException ex = new EntradaFueraDeRangoException(0);

        // Verificamos que el mensaje contenga "0"
        assertTrue(ex.getMessage().contains("0"),
                "El mensaje debe contener el valor inválido");

        // Verificamos que el mensaje incluya el rango permitido
        assertTrue(ex.getMessage().contains("1 y 9"),
                "El mensaje debe indicar el rango permitido");
    }
}
