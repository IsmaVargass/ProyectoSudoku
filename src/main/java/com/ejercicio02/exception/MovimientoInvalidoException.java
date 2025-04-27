package com.ejercicio02.exception;

// Para movimientos que violan fila/columna/
public class MovimientoInvalidoException extends SudokuException {
    public MovimientoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
