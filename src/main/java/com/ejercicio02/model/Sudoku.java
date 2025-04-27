package com.ejercicio02.model;

import com.ejercicio02.exception.*;
import com.ejercicio02.generator.GeneradorSudoku;
import com.ejercicio02.interfaces.ISudoku;

public class Sudoku implements ISudoku {
    private int[][] tablero;
    private final boolean[][] celdasFijas;

    public Sudoku() {
        this.tablero = new int[9][9];
        this.celdasFijas = new boolean[9][9];
    }

    @Override
    public void generarTablero(String dificultad) throws SudokuException {
        GeneradorSudoku generador = new GeneradorSudoku();
        this.tablero = generador.generar();

        // Marcar celdas fijas (for-each anidado)
        for (int filaIndex = 0; filaIndex < 9; filaIndex++) {
            for (int colIndex = 0; colIndex < 9; colIndex++) {
                celdasFijas[filaIndex][colIndex] = (tablero[filaIndex][colIndex] != 0);
            }
        }
    }

    @Override
    public boolean esMovimientoValido(int fila, int columna, int valor) throws SudokuException {
        // 📏 Validar rango 1–9
        if (fila < 1 || fila > 9 || columna < 1 || columna > 9 || valor < 1 || valor > 9) {
            throw new EntradaFueraDeRangoException("Fila, columna y valor deben estar entre 1 y 9.");
        }
        int f = fila - 1;
        int c = columna - 1;

        if (celdasFijas[f][c]) {
            throw new MovimientoInvalidoException("¡Celda fija! No puedes modificarla.");
        }

        for (int vEnFila : tablero[f]) {
            if (vEnFila == valor) {
                throw new MovimientoInvalidoException("Repetido en fila.");
            }
        }
        for (int filaIdx = 0; filaIdx < 9; filaIdx++) {
            if (tablero[filaIdx][c] == valor) {
                throw new MovimientoInvalidoException("Repetido en columna.");
            }
        }

        int bloqueFila = (f / 3) * 3;
        int bloqueCol  = (c / 3) * 3;
        for (int r = bloqueFila; r < bloqueFila + 3; r++) {
            for (int col = bloqueCol; col < bloqueCol + 3; col++) {
                if (tablero[r][col] == valor) {
                    throw new MovimientoInvalidoException("Repetido en subcuadro 3x3.");
                }
            }
        }

        return true;
    }

    @Override
    public void colocarNumero(int fila, int columna, int valor) throws SudokuException {
        if (esMovimientoValido(fila, columna, valor)) {
            tablero[fila - 1][columna - 1] = valor;
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
}
