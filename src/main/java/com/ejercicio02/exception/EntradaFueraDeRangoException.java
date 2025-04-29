package com.ejercicio02.exception;

//TODO NO USO LOS EXCEPTION YA QUE CONTROLO TODO DENTRO DEL GUI, PERO SE VERÍA ASÍ:

//TODO:      if (!esSeguro(grid, fila, columna, valor)) {
//        throw new MovimientoInvalidoException(fila, columna, valor);
//    }



// Para índices o valores fuera de 0–8 (índices) o 1–9 (números)
public class EntradaFueraDeRangoException extends SudokuException {
    public EntradaFueraDeRangoException(int valor) {
        super("Entrada fuera de rango: el valor '" + valor +
                "' no está entre 1 y 9.\n" +
                "Por favor, introduce un número del 1 al 9.");
    }
}