package com.ejercicio02.game;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.IResolverSudoku;
import com.ejercicio02.model.Sudoku;

public class ResolverSudoku implements IResolverSudoku {

    @Override
    public int[] obtenerPista(Sudoku sudoku) throws SudokuException {
        // Hacemos una copia para no alterar el original al buscar la pista
        Sudoku copia = new Sudoku();
        if (!resolverBacktracking(copia)) {
            throw new SudokuException("El sudoku no es resoluble, no hay pista.");
        }
        // Recorremos las celdas buscando la primera vacía en el original
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudoku.getValor(i, j) == 0) {
                    int correcto = copia.getValor(i, j);
                    return new int[]{i, j, correcto};
                }
            }
        }
        throw new SudokuException("No hay celdas vacías para dar pista.");
    }

    @Override
    public void resolver(Sudoku sudoku) throws SudokuException {
        if (!resolverBacktracking(sudoku)) {
            throw new SudokuException("No se puede resolver este sudoku.");
        }
    }

    // Recorre todas las celdas del tablero de Sudoku (9x9) fila por fila y columna por columna.
    // Si encuentra una celda vacía (0), intenta colocarle un número del 1 al 9.
    // Prueba con todos los números del 1 al 9.
    //Solo intenta poner un número si es válido (es decir, no viola las reglas del Sudoku: fila, columna o bloque 3x3).

    /*
    Si el número es válido, lo coloca en la celda.
    Luego llama recursivamente al mismo método para continuar resolviendo el resto del tablero.
    Si esa llamada devuelve true, significa que encontró una solución completa y termina el proceso.
    Si no lleva a una solución, hace backtrack: borra el número (lo vuelve a poner en 0) y prueba con el siguiente número.
     */

    //Si probó todos los números del 1 al 9 y ninguno funcionó, significa que está en un camino sin salida: regresa false para retroceder (backtrack) al paso anterior.

    private boolean resolverBacktracking(Sudoku s) throws SudokuException {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (s.getValor(fila, col) == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (s.esMovimientoValido(fila, col, num)) {
                            s.setValor(fila, col, num);
                            if (resolverBacktracking(s)) {
                                return true;
                            }
                            s.setValor(fila, col, 0);
                        }
                    }
                    return false;
                }
            }
        }
        // Si no encuentra ninguna celda vacía, entonces el tablero ya está resuelto. Se devuelve true.
        return true;
    }
}
