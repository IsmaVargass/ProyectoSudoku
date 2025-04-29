package com.ejercicio02.interfaces;

import com.ejercicio02.exception.SudokuException;

// Genera un tablero completo (solución) usando backtracking
public interface IGeneradorSudoku {
    int[][] generar(String dificultad);

}
