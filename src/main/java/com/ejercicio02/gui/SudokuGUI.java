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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SudokuGUI extends JFrame implements ISudokuGUI {

    private Sudoku sudoku;
    private IResolverSudoku resolver;
    private JTextField[][] celdas;
    private JPanel tableroPanel;
    private JPanel menuPanel;
    private boolean validacionActiva = false;
    private int[][] solucionGrid;

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

        JCheckBoxMenuItem toggleValidacion = new JCheckBoxMenuItem("Validación en tiempo real");
        toggleValidacion.addItemListener(e -> {
            validacionActiva = toggleValidacion.isSelected();
            refrescarTablero();
        });

        nuevaPartida.addActionListener(e -> {
            seleccionarDificultad();
            refrescarTablero();
        });

        darPista.addActionListener(e -> {
            try {
                int[] pista = resolver.obtenerPista(sudoku);
                sudoku.setValor(pista[0], pista[1], pista[2]);
                refrescarTablero();
                if (sudoku.estaResuelto()) mostrarEnhorabuena();
            } catch (SudokuException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo dar pista: " + ex.getMessage());
            }
        });

        resolverTodo.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Quieres que resuelva todo el Sudoku?", "Resolver completo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    resolver.resolver(sudoku);
                    refrescarTablero();
                    if (sudoku.estaResuelto()) mostrarEnhorabuena();
                } catch (SudokuException ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo resolver: " + ex.getMessage());
                }
            }
        });

        salir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres salir?", "Salir del Sudoku", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        menuJuego.add(nuevaPartida);
        menuJuego.addSeparator();
        menuJuego.add(darPista);
        menuJuego.add(resolverTodo);
        menuJuego.addSeparator();
        menuJuego.add(toggleValidacion);
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

            Sudoku copia = new Sudoku(sudoku);
            resolver.resolver(copia);
            solucionGrid = copia.getGrid();
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
        if (tableroPanel != null) remove(tableroPanel);
        tableroPanel = new JPanel(new GridLayout(9, 9));
        tableroPanel.setBackground(Color.LIGHT_GRAY);

        celdas = new JTextField[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);
                campo.setFont(new Font("Courier New", Font.BOLD, 22));
                campo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DigitFilter());

                int valor = sudoku.getValor(fila, col);
                if (valor != 0) {
                    campo.setText(String.valueOf(valor));
                    campo.setEditable(false);
                    campo.setBackground(new Color(240, 248, 255));
                    campo.setForeground(new Color(0, 51, 102));
                } else {
                    if (validacionActiva) {
                        int finalF = fila, finalC = col;
                        campo.getDocument().addDocumentListener(new DocumentListener() {
                            @Override
                            public void insertUpdate(DocumentEvent e) {
                                validar(campo, finalF, finalC);
                            }
                            @Override
                            public void removeUpdate(DocumentEvent e) {
                                validar(campo, finalF, finalC);
                            }
                            @Override
                            public void changedUpdate(DocumentEvent e) {}
                        });
                    }
                }
                celdas[fila][col] = campo;
                tableroPanel.add(campo);
            }
        }
        add(tableroPanel, BorderLayout.CENTER);
    }

    @Override
    public void refrescarTablero() {
        if (tableroPanel != null) remove(tableroPanel);
        inicializarTableroVisual();
        revalidate();
        repaint();
    }

    @Override
    public void mostrarEnhorabuena() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/gifs/APLAUSOS.gif"));
        int opcion = JOptionPane.showOptionDialog(
                this,
                "¡Has resuelto el Sudoku!",
                "Sudoku Resuelto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                new Object[]{"Volver a jugar", "Salir"},
                "Volver a jugar"
        );

        if (opcion == JOptionPane.YES_OPTION) {
            seleccionarDificultad();
            refrescarTablero();
        } else {
            System.exit(0);
        }
    }


    private void validar(JTextField campo, int fila, int col) {
        String texto = campo.getText();
        campo.setBackground(Color.WHITE);

        if (texto.isEmpty()) return;

        try {
            int numero = Integer.parseInt(texto);

            if (solucionGrid != null && numero == solucionGrid[fila][col]) {
                campo.setBackground(new Color(144, 238, 144)); // Verde claro
            } else {
                campo.setBackground(new Color(255, 182, 193)); // Rojo claro
            }
        } catch (NumberFormatException e) {
            campo.setBackground(new Color(255, 182, 193)); // Rojo si no es válido
        }
    }

    private static class DigitFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            if (isValid(text)) fb.replace(0, fb.getDocument().getLength(), text, attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValid(text)) fb.replace(0, fb.getDocument().getLength(), text, attrs);
        }

        private boolean isValid(String text) {
            return text.matches("[1-9]") && text.length() == 1;
        }
    }
}
