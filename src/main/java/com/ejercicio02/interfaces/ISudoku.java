package com.ejercicio02.interfaces;

import com.ejercicio02.exception.SudokuException;

// Define todas las operaciones del tablero
public interface ISudoku {
    void generarTablero(String dificultad) throws SudokuException;
    boolean esMovimientoValido(int fila, int columna, int valor) throws SudokuException;
    void colocarNumero(int fila, int columna, int valor) throws SudokuException;
    boolean estaResuelto();
    void mostrarTablero();
}
