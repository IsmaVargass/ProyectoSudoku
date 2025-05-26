package com.ejercicio02.model;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.generator.GeneradorSudoku;
import com.ejercicio02.interfaces.ISudoku;

public class Sudoku implements ISudoku {
    private int[][] tablero;
    private final boolean[][] celdasFijas;
    private int[][] solucionGrid;  // Aquí guardaremos la solución

    public Sudoku() {
        this.tablero = new int[9][9];
        this.celdasFijas = new boolean[9][9];
    }

    public Sudoku(Sudoku otro) {
        this.tablero = new int[9][9];
        this.celdasFijas = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(otro.tablero[i], 0, this.tablero[i], 0, 9);
            System.arraycopy(otro.celdasFijas[i], 0, this.celdasFijas[i], 0, 9);
        }
        // Copiamos la solución también si estamos haciendo una copia
        this.solucionGrid = otro.solucionGrid != null ? otro.solucionGrid.clone() : null;
    }

    @Override
    public void generarTablero(String dificultad) throws SudokuException {
        GeneradorSudoku generador = new GeneradorSudoku();
        this.tablero = generador.generar(dificultad);

        // Marcar celdas fijas
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                celdasFijas[fila][col] = (tablero[fila][col] != 0);
            }
        }

        // Resolvemos el Sudoku y guardamos la solución
        resolverSudoku();
    }

    private void resolverSudoku() {
        // Resuelve el Sudoku usando un algoritmo de backtracking
        solucionGrid = new int[9][9];

        // Copia del tablero actual para poder resolverlo sin alterar el tablero original
        int[][] copiaTablero = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(tablero[i], 0, copiaTablero[i], 0, 9);
        }

        // Resolver el Sudoku (por ejemplo, usando backtracking)
        // Este es un ejemplo de cómo resolverlo
        backtrackResolver(copiaTablero);

        // Guardamos la solución final
        this.solucionGrid = copiaTablero;
    }

    private boolean backtrackResolver(int[][] grid) {
        // Recorre el tablero buscando la primera casilla vacía (0)
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (grid[fila][col] == 0) {
                    // Intentar valores del 1 al 9
                    for (int num = 1; num <= 9; num++) {
                        if (esMovimientoValido(fila, col, num)) {
                            grid[fila][col] = num;
                            // Recursión
                            if (backtrackResolver(grid)) {
                                return true;
                            }
                            // Deshacer asignación si no condujo a solución
                            grid[fila][col] = 0;
                        }
                    }
                    // Ningún número válido en esta celda; retroceder
                    return false;
                }
            }
        }
        // Si no quedan cerillas vacías, copiamos la solución
        copiarSolucion(grid);
        return true;
    }

    private void copiarSolucion(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            System.arraycopy(grid[i], 0, solucionGrid[i], 0, 9);
        }
    }


    @Override
    public int getValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    @Override
    public void setValor(int fila, int columna, int valor) {
        tablero[fila][columna] = valor;
    }

    // Método para obtener la solución del Sudoku
    public int[][] getGrid() {
        return tablero;  // el array interno de 9×9
    }

    @Override
    public boolean estaResuelto() {
        for (int[] filaArr : tablero) {
            for (int casilla : filaArr) {
                if (casilla == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void mostrarTablero() {
        System.out.println("+-------+-------+-------+");
        for (int filaIdx = 0; filaIdx < 9; filaIdx++) {
            System.out.print("|");
            for (int colIdx = 0; colIdx < 9; colIdx++) {
                String celda = (tablero[filaIdx][colIdx] == 0)
                        ? " _"
                        : " " + tablero[filaIdx][colIdx];
                System.out.print(celda);
                if ((colIdx + 1) % 3 == 0) {
                    System.out.print(" |");
                }
            }
            System.out.println();
            if ((filaIdx + 1) % 3 == 0) {
                System.out.println("+-------+-------+-------+");
            }
        }
    }

    // Método que valida si un valor es válido para colocar en una celda
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        if (fila < 0 || fila > 8 || columna < 0 || columna > 8 || valor < 1 || valor > 9) {
            return false;
        }
        if (celdasFijas[fila][columna]) {
            return false;
        }
        for (int vEnFila : tablero[fila]) {
            if (vEnFila == valor) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (tablero[i][columna] == valor) {
                return false;
            }
        }
        int bloqueFila = (fila / 3) * 3;
        int bloqueCol  = (columna / 3) * 3;
        for (int r = bloqueFila; r < bloqueFila + 3; r++) {
            for (int c = bloqueCol; c < bloqueCol + 3; c++) {
                if (tablero[r][c] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    public void colocarNumero(int fila, int columna, int valor) throws SudokuException {
        // Verifica que el valor esté dentro del rango permitido (1-9)
        if (valor < 1 || valor > 9) {
            throw new SudokuException("Valor fuera del rango permitido (1-9).");
        }

        // Verifica que el número no esté repetido en la fila
        for (int col = 0; col < 9; col++) {
            if (getValor(fila, col) == valor) {
                throw new SudokuException("El número ya está en la fila.");
            }
        }

        // Verifica que el número no esté repetido en la columna
        for (int row = 0; row < 9; row++) {
            if (getValor(row, columna) == valor) {
                throw new SudokuException("El número ya está en la columna.");
            }
        }

        // Verifica que el número no esté repetido en el bloque 3x3
        int bloqueInicioFila = (fila / 3) * 3;
        int bloqueInicioColumna = (columna / 3) * 3;

        for (int i = bloqueInicioFila; i < bloqueInicioFila + 3; i++) {
            for (int j = bloqueInicioColumna; j < bloqueInicioColumna + 3; j++) {
                if (getValor(i, j) == valor) {
                    throw new SudokuException("El número ya está en el bloque 3x3.");
                }
            }
        }

        // Si pasa todas las validaciones, se coloca el número
        setValor(fila, columna, valor);
    }
}
