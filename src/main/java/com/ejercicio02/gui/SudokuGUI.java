package com.ejercicio02.gui;

import com.ejercicio02.model.Sudoku;
import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.ISudokuGUI;
import com.ejercicio02.interfaces.IResolverSudoku;
import com.ejercicio02.game.ResolverSudoku;

import javax.swing.*;
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

        // Acción: Nueva partida
        nuevaPartida.addActionListener(e -> {
            seleccionarDificultad();
            refrescarTablero();
        });

        // Acción: Salir
        salir.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres salir?", "Salir del Sudoku", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Acción: Pista
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

        // Acción: Resolver completo
        resolverTodo.addActionListener(e -> {
            int confirma = JOptionPane.showConfirmDialog(this,
                    "¿Quieres que resuelva todo el Sudoku?",
                    "Resolver completo", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
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
        menuJuego.add(salir);
        menuJuego.addSeparator();
        menuJuego.add(darPista);
        menuJuego.add(resolverTodo);

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

                int valor = sudoku.getValor(fila, columna);
                if (valor != 0) {
                    campo.setText(String.valueOf(valor));
                    campo.setEditable(false);
                    campo.setBackground(new Color(240, 248, 255));
                    campo.setForeground(new Color(0, 51, 102));
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
        // Carga un GIF animado
        ImageIcon icon = new ImageIcon(getClass().getResource("/gifs/APLAUSOS.gif"));
        // Muestra diálogo con solo botón "OK" y sale al pulsar
        JOptionPane.showOptionDialog(
                this,
                "¡Has resuelto el Sudoku!",
                "Sudoku Resuelto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                new Object[]{"Salir"},
                "Salir");
        // Al cerrar el diálogo, salimos
        System.exit(0);
    }
}
