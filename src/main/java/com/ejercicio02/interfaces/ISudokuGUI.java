package com.ejercicio02.interfaces;

import javax.swing.*;

// Abstrae la GUI Swing
public interface ISudokuGUI {
    // iniciar la interfaz
    void iniciarInterfaz();
    // seleccionar la dificultad
    void seleccionarDificultad();
    // inicializar el menú superior
    void inicializarMenuSuperior();
    // inicializar el menú
    void inicializarMenu();
    // inicializar el tablero visual
    void inicializarTableroVisual();
    // refrescar el tablero
    void refrescarTablero();
    // enhorabuena por ganar
    void mostrarEnhorabuena();

    // ------ Modificación principal: validar() actualizada ------
    void validar(JTextField campo, int fila, int col);
}
