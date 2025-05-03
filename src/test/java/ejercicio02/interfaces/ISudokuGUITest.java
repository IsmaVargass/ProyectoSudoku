package ejercicio02.interfaces;

import com.ejercicio02.interfaces.ISudokuGUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ISudokuGUITest {

    private ISudokuGUI gui;

    @BeforeEach
    void setUp() {
        gui = new ISudokuGUI() {
            @Override
            public void iniciarInterfaz() {}

            @Override
            public void seleccionarDificultad() {}

            @Override
            public void inicializarMenuSuperior() {}

            @Override
            public void inicializarMenu() {}

            @Override
            public void inicializarTableroVisual() {}

            @Override
            public void refrescarTablero() {}

            @Override
            public void mostrarEnhorabuena() {}

            @Override
            public void validar(JTextField campo, int fila, int col) {}
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
        assertDoesNotThrow(() -> gui.validar(new JTextField(), 0, 0));
    }
}
