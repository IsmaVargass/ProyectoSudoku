package com.ejercicio02.game;

import com.ejercicio02.gui.SudokuGUI;

// Añado las dos maneras de jugar, por gui y en consola.
public class Main {
    public static void main(String[] args) {
        new SudokuGUI();
        new JuegoSudoku().iniciar();
    }
}

