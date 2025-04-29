package com.ejercicio02.generator;

import com.ejercicio02.interfaces.IGeneradorSudoku;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneradorSudoku implements IGeneradorSudoku {

    @Override
    public int[][] generar(String dificultad) {
        return generarPuzzle(dificultad);
    }

    private int[][] generarPuzzle(String dificultad) {
        int[][] puzzleGrid = new int[9][9];
        rellenarSolucion(puzzleGrid);

        int vaciar;
        switch (dificultad.toLowerCase()) {
            case "fácil": vaciar = 35; break;
            case "medio": vaciar = 50; break;
            case "difícil": vaciar = 60; break;
            default: vaciar = 35;
        }

        int maxIntentos = vaciar * 5;

        while (vaciar > 0 && maxIntentos-- > 0) {
            int filaAleatoria = (int)(Math.random() * 9);
            int columnaAleatoria = (int)(Math.random() * 9);

            if (puzzleGrid[filaAleatoria][columnaAleatoria] == 0) continue;

            int respaldo = puzzleGrid[filaAleatoria][columnaAleatoria];
            puzzleGrid[filaAleatoria][columnaAleatoria] = 0;

            if (contarSoluciones(puzzleGrid) != 1) {
                puzzleGrid[filaAleatoria][columnaAleatoria] = respaldo;
            } else {
                vaciar--;
            }
        }

        return puzzleGrid;
    }

    private boolean rellenarSolucion(int[][] grid) {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (grid[fila][columna] == 0) {
                    List<Integer> candidatos = new ArrayList<>();
                    for (int i = 1; i <= 9; i++) candidatos.add(i);
                    Collections.shuffle(candidatos);

                    for (int candidato : candidatos) {
                        if (esSeguro(grid, fila, columna, candidato)) {
                            grid[fila][columna] = candidato;
                            if (rellenarSolucion(grid)) return true;
                            grid[fila][columna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean esSeguro(int[][] grid, int fila, int columna, int numero) {
        for (int valorEnFila : grid[fila]) if (valorEnFila == numero) return false;
        for (int filaIndex = 0; filaIndex < 9; filaIndex++) if (grid[filaIndex][columna] == numero) return false;

        int bloqueFila = (fila / 3) * 3;
        int bloqueCol  = (columna / 3) * 3;
        for (int r = bloqueFila; r < bloqueFila + 3; r++) {
            for (int c = bloqueCol; c < bloqueCol + 3; c++) {
                if (grid[r][c] == numero) return false;
            }
        }
        return true;
    }

    private int contarSoluciones(int[][] grid) {
        return contar(grid, 2, 0);
    }

    private int contar(int[][] grid, int limite, int contador) {
        if (contador >= limite) return contador;

        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                if (grid[fila][columna] == 0) {
                    for (int candidato = 1; candidato <= 9; candidato++) {
                        if (esSeguro(grid, fila, columna, candidato)) {
                            grid[fila][columna] = candidato;
                            contador = contar(grid, limite, contador);
                            grid[fila][columna] = 0;
                            if (contador >= limite) return contador;
                        }
                    }
                    return contador;
                }
            }
        }
        return contador + 1;
    }
}
