package ejercicio02.gui;

import com.ejercicio02.gui.SudokuGUI;
import com.ejercicio02.model.Sudoku;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuGUITest {

    private SudokuGUI sudokuGUI;
    private Sudoku sudoku;

    @BeforeEach
    void setUp() {
        // Subclase anónima para evitar el diálogo de dificultad
        sudoku = new Sudoku();
        sudokuGUI = new SudokuGUI() {
            @Override
            public void seleccionarDificultad() {
                // No mostramos el diálogo en los tests
            }
        };
        sudokuGUI.sudoku = sudoku;
    }

    @Test
    void testIniciarInterfaz() {
        JMenuBar menuBar = sudokuGUI.getJMenuBar();
        assertNotNull(menuBar);

        JMenu menuJuego = menuBar.getMenu(0);
        assertNotNull(menuJuego);
        // Opciones: Nueva Partida, separator, Pista, Resolver, separator, Validación en tiempo real, Salir
        assertEquals(7, menuJuego.getItemCount());
    }

    @Test
    void testRefrescarTablero() {
        assertDoesNotThrow(() -> sudokuGUI.refrescarTablero());
        assertTrue(sudokuGUI.getContentPane().isVisible());
    }

    @Test
    void testMostrarEnhorabuena() {
        // Llamar al método no debe lanzar excepción
        assertDoesNotThrow(() -> sudokuGUI.mostrarEnhorabuena());
    }

    @Test
    void testValidarCorrectoSinColor() {
        JTextField campo = new JTextField("5");
        sudoku.setValor(0, 0, 5);
        sudokuGUI.validar(campo, 0, 0);
        // Validación desactivada por defecto: fondo blanco
        assertEquals(Color.WHITE, campo.getBackground());
    }

    @Test
    void testValidarIncorrectoSinColor() {
        JTextField campo = new JTextField("3");
        sudoku.setValor(0, 0, 5);
        sudokuGUI.validar(campo, 0, 0);
        assertEquals(Color.WHITE, campo.getBackground());
    }

    @Test
    void testValidarEntradaNoValidaSinColor() {
        JTextField campo = new JTextField("a");
        sudokuGUI.validar(campo, 0, 0);
        assertEquals(Color.WHITE, campo.getBackground());
    }
}
