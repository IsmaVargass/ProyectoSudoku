package com.ejercicio02.gui;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.ISudokuGUI;
import com.ejercicio02.interfaces.IResolverSudoku;
import com.ejercicio02.game.ResolverSudoku;
import com.ejercicio02.model.Sudoku;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.DocumentFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;

public class SudokuGUI extends JFrame implements ISudokuGUI {

    private Sudoku sudoku;
    private IResolverSudoku resolver;
    private JTextField[][] celdas;
    private JPanel tableroPanel;
    private JPanel menuPanel;

    public SudokuGUI() {
        setTitle("Sudoku Game");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sudoku = new Sudoku();
        resolver = new ResolverSudoku();

        iniciarInterfaz();

        setVisible(true);
    }

    @Override
    public void iniciarInterfaz() {
        inicializarMenuSuperior();
        seleccionarDificultad();
        inicializarMenu();
        inicializarTableroVisual();
    }

    @Override
    public void inicializarMenuSuperior() {
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuJuego = new JMenu("Menú");

        JMenuItem nuevaPartida = new JMenuItem("Nueva Partida");
        JMenuItem darPista = new JMenuItem("Pista");
        JMenuItem resolverTodo = new JMenuItem("Resolver");
        JMenuItem salir = new JMenuItem("Salir");

        nuevaPartida.addActionListener(e -> {
            seleccionarDificultad();
            refrescarTablero();
        });

        salir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que quieres salir?",
                    "Salir del Sudoku",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        darPista.addActionListener(e -> {
            try {
                int[] pista = resolver.obtenerPista(sudoku);
                sudoku.setValor(pista[0], pista[1], pista[2]);
                refrescarTablero();
                if (sudoku.estaResuelto()) {
                    mostrarEnhorabuena();
                }
            } catch (SudokuException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo dar pista: " + ex.getMessage());
            }
        });

        resolverTodo.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Quieres que resuelva todo el Sudoku?",
                    "Resolver completo",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    resolver.resolver(sudoku);
                    refrescarTablero();
                    if (sudoku.estaResuelto()) {
                        mostrarEnhorabuena();
                    }
                } catch (SudokuException ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo resolver: " + ex.getMessage());
                }
            }
        });

        menuJuego.add(nuevaPartida);
        menuJuego.addSeparator();
        menuJuego.add(darPista);
        menuJuego.add(resolverTodo);
        menuJuego.addSeparator();
        menuJuego.add(salir);

        barraMenu.add(menuJuego);
        setJMenuBar(barraMenu);
    }

    @Override
    public void seleccionarDificultad() {
        String[] opciones = {"Fácil", "Medio", "Difícil"};
        String dificultad = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona dificultad:",
                "Dificultad del Sudoku",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (dificultad == null) {
            System.exit(0);
        }

        try {
            sudoku.generarTablero(dificultad.toLowerCase());
        } catch (SudokuException e) {
            JOptionPane.showMessageDialog(this, "Error generando tablero: " + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void inicializarMenu() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(220, 220, 220));
        menuPanel.setLayout(new FlowLayout());

        JLabel titulo = new JLabel("Sudoku Game");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        menuPanel.add(titulo);

        add(menuPanel, BorderLayout.NORTH);
    }

    @Override
    public void inicializarTableroVisual() {
        if (tableroPanel != null) {
            remove(tableroPanel);
        }
        tableroPanel = new JPanel(new GridLayout(9, 9));
        tableroPanel.setBackground(Color.LIGHT_GRAY);

        celdas = new JTextField[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setFont(new Font("Courier New", Font.BOLD, 22));
                campo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                // Aplicamos el filtro para permitir solo 1-9
                ((AbstractDocument) campo.getDocument())
                        .setDocumentFilter(new DigitFilter());

                int valor = sudoku.getValor(fila, columna);
                if (valor != 0) {
                    campo.setText(String.valueOf(valor));
                    campo.setEditable(false);
                    campo.setBackground(new Color(240, 248, 255));
                    campo.setForeground(new Color(0, 51, 102));
                } else {
                    campo.setText("");
                }

                celdas[fila][columna] = campo;
                tableroPanel.add(campo);
            }
        }

        add(tableroPanel, BorderLayout.CENTER);
    }

    @Override
    public void refrescarTablero() {
        tableroPanel.removeAll();
        inicializarTableroVisual();
        tableroPanel.revalidate();
        tableroPanel.repaint();
    }

    @Override
    public void mostrarEnhorabuena() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/gifs/APLAUSOS.gif"));
        JOptionPane.showOptionDialog(
                this,
                "¡Has resuelto el Sudoku!",
                "Sudoku Resuelto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                new Object[]{"Salir"},
                "Salir");
        System.exit(0);
    }

    // Filtro personalizado para JTextField que solo permite un dígito entre 1 y 9
    // ejecuta cuando el usuario intenta insertar texto en un JTextField vacío
    private static class DigitFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {
            // Solo insertamos si el texto es válido (un solo dígito del 1 al 9)
            if (isValidInput(fb, text)) {
                // el contenido del campo con el nuevo valor
                fb.replace(0, fb.getDocument().getLength(), text, attr);
            }
        }

        @Override
        // se ejecuta cuando el usuario intenta reemplazar el contenido del JTextField
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            // Solo permitimos el reemplazo si el texto es válido (1 dígito entre 1 y 9)
            if (isValidInput(fb, text)) {
                // el contenido del campo con el nuevo valor
                fb.replace(0, fb.getDocument().getLength(), text, attrs);
            }
        }

        // auxiliar que valida si el texto ingresado es exactamente un carácter entre 1 y 9
        private boolean isValidInput(FilterBypass fb, String text) {
            return text.matches("[1-9]") && text.length() == 1;
        }
    }
}
