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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

// Clase principal de la interfaz gráfica del Sudoku
public class SudokuGUI extends JFrame implements ISudokuGUI {

    public Sudoku sudoku;                     // Modelo del Sudoku
    private IResolverSudoku resolver;         // Lógica de resolución
    private JTextField[][] celdas;            // Matriz de campos de texto para el tablero
    private JPanel tableroPanel;              // Panel donde van las celdas
    private JPanel menuPanel;                 // Panel del menú superior
    private boolean validacionActiva = false; // Indica si la validación en tiempo real está activada
    private int[][] solucionGrid;             // Guarda la solución completa para comparar

    // Constructor de la ventana
    public SudokuGUI() {
        setTitle("Sudoku Game");             // Título de la ventana
        setSize(600, 700);                     // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);           // Centrar la ventana
        setLayout(new BorderLayout());         // Distribución principal

        sudoku = new Sudoku();                 // Nuevo modelo de Sudoku
        resolver = new ResolverSudoku();      // Lógica para resolver/pistas

        iniciarInterfaz();                     // Monta la interfaz
        setVisible(true);                      // La hace visible
    }

    @Override
    public void iniciarInterfaz() {
        inicializarMenuSuperior();             // Crea la barra de menú
        seleccionarDificultad();               // Pide al usuario la dificultad
        inicializarMenu();                     // Crea el título superior
        inicializarTableroVisual();            // Dibuja el tablero
    }

    @Override
    public void inicializarMenuSuperior() {
        JMenuBar barraMenu = new JMenuBar();
        JMenu menuJuego = new JMenu("Menú");

        // Opciones del menú
        JMenuItem nuevaPartida = new JMenuItem("Nueva Partida");
        JMenuItem darPista = new JMenuItem("Pista");
        JMenuItem resolverTodo = new JMenuItem("Resolver");
        JMenuItem salir = new JMenuItem("Salir");

        // Checkbox para activar o desactivar validación en tiempo real
        JCheckBoxMenuItem toggleValidacion = new JCheckBoxMenuItem("Validación en tiempo real");
        toggleValidacion.addItemListener(e -> {
            validacionActiva = toggleValidacion.isSelected();
            refrescarTablero();                // Refresca para aplicar o quitar listeners de validación
        });

        // Acción de "Nueva Partida"
        nuevaPartida.addActionListener(e -> {
            seleccionarDificultad();           // Nueva dificultad
            refrescarTablero();                // Refrescar el tablero
        });

        // Acción de "Pista"
        darPista.addActionListener(e -> {
            try {
                int[] pista = resolver.obtenerPista(sudoku); // Obtener coordenada y valor
                sudoku.setValor(pista[0], pista[1], pista[2]);
                refrescarTablero();            // Refresca tablero con pista
                if (sudoku.estaResuelto())    // Si resolución completa
                    mostrarEnhorabuena();    // Muestra felicitación
            } catch (SudokuException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo dar pista: " + ex.getMessage());
            }
        });

        // Acción de "Resolver"
        resolverTodo.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Quieres que resuelva todo el Sudoku?", "Resolver completo",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    resolver.resolver(sudoku);  // Resuelve el tablero entero
                    refrescarTablero();        // Refresca con la solución
                    if (sudoku.estaResuelto())
                        mostrarEnhorabuena();
                } catch (SudokuException ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo resolver: " + ex.getMessage());
                }
            }
        });

        // Acción de "Salir"
        salir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Seguro que quieres salir?", "Salir del Sudoku",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        // Montar el menú en la barra
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
            System.exit(0);                   // Si cancela, sale del juego
        }

        try {
            sudoku.generarTablero(dificultad.toLowerCase()); // Genera tablero según dificultad

            // Creamos copia para conocer solución completa
            Sudoku copia = new Sudoku(sudoku);
            resolver.resolver(copia);
            solucionGrid = copia.getGrid(); // Guardamos matriz solución
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

                // Filtrar para solo permitir dígitos 1-9
                ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DigitFilter());

                int valor = sudoku.getValor(fila, col);
                if (valor != 0) {
                    // Si la celda ya tiene valor, la bloqueamos
                    campo.setText(String.valueOf(valor));
                    campo.setEditable(false);
                    campo.setBackground(new Color(240, 248, 255));
                    campo.setForeground(new Color(0, 51, 102));
                } else {
                    // Para cada celda vacía, añadimos listener de cambio
                    int finalF = fila, finalC = col;
                    campo.getDocument().addDocumentListener(new DocumentListener() {
                        @Override public void insertUpdate(DocumentEvent e) { onCampoCambiado(campo, finalF, finalC); }
                        @Override public void removeUpdate(DocumentEvent e) { onCampoCambiado(campo, finalF, finalC); }
                        @Override public void changedUpdate(DocumentEvent e) {}
                    });
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
        inicializarTableroVisual(); // Re-dibuja el tablero
        revalidate();
        repaint();
    }

    @Override
    public void mostrarEnhorabuena() {
        // Muestra un diálogo con un icono de aplausos
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
            seleccionarDificultad();           // Nueva partida si vuelve a jugar
            refrescarTablero();                // Refresca interfaz
        } else {
            System.exit(0);                   // Sale de la aplicación
        }
    }

    @Override
    public void validar(JTextField campo, int fila, int col) {

    }

    //Método centralizado que se ejecuta al cambiar cualquier celda vacía.
    //Actualiza el modelo, colorea si es necesario y chequea si el Sudoku está completo.
    private void onCampoCambiado(JTextField campo, int fila, int col) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            sudoku.setValor(fila, col, 0);    // Si borró el valor, lo ponemos a 0
            if (validacionActiva) campo.setBackground(Color.WHITE);
            return;
        }

        try {
            int numero = Integer.parseInt(texto);
            sudoku.setValor(fila, col, numero); // Actualizar modelo

            if (validacionActiva) {
                // Si está activo, colorear según sea correcto o no
                if (solucionGrid != null && numero == solucionGrid[fila][col]) {
                    campo.setBackground(new Color(144, 238, 144)); // Verde claro
                } else {
                    campo.setBackground(new Color(255, 182, 193)); // Rojo claro
                }
            }
        } catch (NumberFormatException ex) {
            sudoku.setValor(fila, col, 0);    // Entrada no válida -> reset modelo
            if (validacionActiva) campo.setBackground(new Color(255, 182, 193));
            return;
        }

        // Comprobar siempre si el Sudoku está resuelto tras cada cambio
        if (sudoku.estaResuelto()) {
            validacionActiva = false;           // Desactivar validación para no repetir
            mostrarEnhorabuena();               // Mostrar mensaje final
        }
    }

    // Filtro para permitir solo un dígito del 1 al 9 en cada JTextField
    private static class DigitFilter extends DocumentFilter {
        @Override public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                throws BadLocationException {
            if (isValid(text)) fb.replace(0, fb.getDocument().getLength(), text, attr);
        }
        @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (isValid(text)) fb.replace(0, fb.getDocument().getLength(), text, attrs);
        }
        private boolean isValid(String text) {
            return text.matches("[1-9]") && text.length() == 1; // Solo un dígito válido
        }
    }
}