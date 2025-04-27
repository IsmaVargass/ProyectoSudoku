package com.ejercicio02.exception;

// Excepción base para cualquier error en reglas de Sudoku
public class SudokuException extends Exception {
    public SudokuException(String mensaje) {
        super(mensaje);
    }
}
