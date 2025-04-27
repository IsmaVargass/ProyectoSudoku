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

    private boolean resolverBacktracking(Sudoku s)  {
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
        // si no quedan ceros, está resuelto
        return true;
    }
}
