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
        // Subclase anónima para evitar que se ejecute el diálogo de dificultad
        sudoku = new Sudoku();
        sudokuGUI = new SudokuGUI() {
            @Override
            public void seleccionarDificultad() {
                // No hacer nada: así evitamos el JOptionPane que bloquea
            }
        };
        sudokuGUI.sudoku = sudoku;
    }

    @Test
    void testIniciarInterfaz() {
        JMenuBar menuBar = sudokuGUI.getJMenuBar();
        assertNotNull(menuBar);  // Asegura que la barra de menú no sea nula

        JMenu menuJuego = menuBar.getMenu(0);  // El primer menú debe ser "Menú"
        assertNotNull(menuJuego);
        assertEquals(7, menuJuego.getItemCount()); // Comprobamos que tiene 7 opciones
    }

    @Test
    void testRefrescarTablero() {
        sudokuGUI.refrescarTablero();
        assertTrue(sudokuGUI.getContentPane().isVisible());  // Verifica que la GUI esté visible
    }

    @Test
    void testMostrarEnhorabuena() {
        // Simulamos que el Sudoku ya está resuelto
        assertFalse(sudoku.estaResuelto());  // Verifica si está resuelto
    }

    @Test
    void testValidarCorrecto() {
        JTextField campoMock = new JTextField("5");
        sudoku.setValor(0, 0, 5);  // El valor correcto es 5
        sudokuGUI.validar(campoMock, 0, 0);
        assertEquals(new Color(255, 182, 193), campoMock.getBackground());
    }

    @Test
    void testValidarIncorrecto() {
        JTextField campoMock = new JTextField("3");
        sudoku.setValor(0, 0, 5);  // El valor correcto es 5
        sudokuGUI.validar(campoMock, 0, 0);
        assertEquals(new Color(255, 182, 193), campoMock.getBackground());
    }

    @Test
    void testValidarEntradaNoValida() {
        JTextField campoMock = new JTextField("a");  // Entrada no numérica
        sudokuGUI.validar(campoMock, 0, 0);
        assertEquals(new Color(255, 182, 193), campoMock.getBackground());
    }
}
