package javaTest;

import com.ejercicio02.exception.EntradaFueraDeRangoException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class EntradaFueraDeRangoExceptionTest {
    @Test
    void constructor_MensajeIncluyeValor() {
        // Al crear la excepción con un valor inválido, el mensaje debe mencionar ese valor
        EntradaFueraDeRangoException ex = new EntradaFueraDeRangoException(0);
        assertTrue(ex.getMessage().contains("0"),
                "El mensaje debe contener el valor inválido");
        assertTrue(ex.getMessage().contains("1 y 9"),
                "El mensaje debe indicar el rango permitido");
    }
}

