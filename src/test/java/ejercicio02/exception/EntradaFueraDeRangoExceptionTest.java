package ejercicio02.exception;

import com.ejercicio02.exception.EntradaFueraDeRangoException;
import org.junit.Test;
import static org.testng.Assert.assertTrue;


public class EntradaFueraDeRangoExceptionTest {
    @Test
    public void constructor_MensajeIncluyeValor() {
        // Al crear la excepción con un valor inválido, el mensaje debe mencionar ese valor
        EntradaFueraDeRangoException ex = new EntradaFueraDeRangoException(0);
        assertTrue(ex.getMessage().contains("0"),
                "El mensaje debe contener el valor inválido");
        assertTrue(ex.getMessage().contains("1 y 9"),
                "El mensaje debe indicar el rango permitido");
    }
}

