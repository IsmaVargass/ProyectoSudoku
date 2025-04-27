package com.ejercicio02.exception;

// Para índices o valores fuera de 0–8 (índices) o 1–9 (números)
public class EntradaFueraDeRangoException extends SudokuException {
    public EntradaFueraDeRangoException(String mensaje) {
        super(mensaje);
    }
}