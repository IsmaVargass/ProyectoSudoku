package com.ejercicio02.model;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.generator.GeneradorSudoku;
import com.ejercicio02.interfaces.ISudoku;

public class Sudoku implements ISudoku {
    private int[][] tablero;
    private final boolean[][] celdasFijas;

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
    }

    @Override
    public void generarTablero(String dificultad) throws SudokuException {
        GeneradorSudoku generador = new GeneradorSudoku();
        this.tablero = generador.generar();

        // Marcar celdas fijas
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                celdasFijas[fila][col] = (tablero[fila][col] != 0);
            }
        }
    }

    /**
     * Comprueba si colocar valor en (fila, columna) (0-based) es válido.
     * No lanza excepción para backtracking: devuelve false si no es posible.
     */
    public boolean esMovimientoValido(int fila, int columna, int valor) {
        // rangos
        if (fila < 0 || fila > 8 || columna < 0 || columna > 8 || valor < 1 || valor > 9) {
            return false;
        }
        if (celdasFijas[fila][columna]) {
            return false;
        }
        //  fila
        for (int vEnFila : tablero[fila]) {
            if (vEnFila == valor) {
                return false;
            }
        }
        //  columna
        for (int i = 0; i < 9; i++) {
            if (tablero[i][columna] == valor) {
                return false;
            }
        }
        //  bloque 3x3
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

    @Override
    public void colocarNumero(int fila, int columna, int valor) throws SudokuException {
        // convierte a 0-based
        if (esMovimientoValido(fila - 1, columna - 1, valor)) {
            setValor(fila - 1, columna - 1, valor);
        }
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

    @Override
    public int getValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    @Override
    public void setValor(int fila, int columna, int valor) {
        // direct set (0-based fila/columna)
        tablero[fila][columna] = valor;
    }
}
