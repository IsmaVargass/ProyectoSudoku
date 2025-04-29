package com.ejercicio02.exception;

//TODO NO USO LOS EXCEPTION YA QUE CONTROLO TODO DENTRO DEL GUI, PERO SE VERÍA ASÍ:

//TODO:     if (valor < 1 || valor > 9) {
//        throw new EntradaFueraDeRangoException(valor);
//    }

// Excepción base para cualquier error en reglas de Sudoku
public class SudokuException extends Exception {
    public SudokuException(String mensaje) {
        super(mensaje);
    }

    public SudokuException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
