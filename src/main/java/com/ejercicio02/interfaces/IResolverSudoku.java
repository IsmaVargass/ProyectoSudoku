package com.ejercicio02.interfaces;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.model.Sudoku;

public interface IResolverSudoku {

    int[] obtenerPista(Sudoku sudoku) throws SudokuException;
    void resolver(Sudoku sudoku) throws SudokuException;
}
