package com.ejercicio02.exception;

//TODO NO USO LOS EXCEPTION YA QUE CONTROLO TODO DENTRO DEL GUI, PERO SE VERÍA ASÍ:

//TODO:      if (celdasFijas[fila][columna]) {
//       throw new MovimientoInvalidoException(fila, columna, valor);
//    }


// Para movimientos que violan fila/columna/
public class MovimientoInvalidoException extends SudokuException {
    public MovimientoInvalidoException(int fila, int columna, int valor) {
        super("Movimiento inválido: no se puede colocar el valor "
                + valor + " en la posición (" + (fila+1) + ", " + (columna+1) + ").\n" +
                "Verifica que no exista ya en la misma fila, columna o bloque 3x3.");
    }
}
