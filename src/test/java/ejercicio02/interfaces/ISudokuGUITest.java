package ejercicio02.interfaces;

import com.ejercicio02.interfaces.ISudokuGUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ISudokuGUITest {

    private ISudokuGUI gui;

    @BeforeEach
    void setUp() {
        gui = new ISudokuGUI() {
            public void iniciarInterfaz() {}
            public void seleccionarDificultad() {}
            public void inicializarMenuSuperior() {}
            public void inicializarMenu() {}
            public void inicializarTableroVisual() {}
            public void refrescarTablero() {}
            public void mostrarEnhorabuena() {}
        };
    }

    @Test
    void testMetodosNoLanzanExcepciones() {
        assertDoesNotThrow(gui::iniciarInterfaz);
        assertDoesNotThrow(gui::seleccionarDificultad);
        assertDoesNotThrow(gui::inicializarMenuSuperior);
        assertDoesNotThrow(gui::inicializarMenu);
        assertDoesNotThrow(gui::inicializarTableroVisual);
        assertDoesNotThrow(gui::refrescarTablero);
        assertDoesNotThrow(gui::mostrarEnhorabuena);
    }
}
