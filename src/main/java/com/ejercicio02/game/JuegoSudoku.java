package com.ejercicio02.game;

import com.ejercicio02.exception.SudokuException;
import com.ejercicio02.interfaces.IJuegoSudoku;
import com.ejercicio02.model.Sudoku;

import java.util.Scanner;

public class JuegoSudoku implements IJuegoSudoku {

    @Override
    public void iniciar() {
        Sudoku sudoku = new Sudoku();
        Scanner sc = new Scanner(System.in);

        String dificultad;
        while (true) {
            System.out.print("Elige dificultad [1/Easy/facil, 2/Medium/medio, 3/Hard/dificil]: ");
            String entrada = sc.nextLine().trim().toLowerCase();

            if (entrada.matches("^(1|easy|facil)$")) {
                dificultad = "facil";
                break;
            }
            if (entrada.matches("^(2|medium|medio)$")) {
                dificultad = "medio";
                break;
            }
            if (entrada.matches("^(3|hard|dificil|difícil)$")) {
                dificultad = "dificil";
                break;
            }
            System.err.println("  ❌ Opción no válida. Usa 1, 2, 3, easy, medium, hard, facil, medio o dificil.");
        }

        // 🧩 Generar el tablero según la dificultad
        try {
            sudoku.generarTablero(dificultad);
        } catch (SudokuException e) {
            System.err.println("Error al generar el tablero: " + e.getMessage());
            return;
        }


        while (!sudoku.estaResuelto()) {
            sudoku.mostrarTablero();

            int fila, columna;
            // 📐 Pedir y validar fila 1–9
            while (true) {
                System.out.print("Fila (1-9): ");
                if (sc.hasNextInt()) {
                    fila = sc.nextInt();
                    if (fila >= 1 && fila <= 9) break;
                } else {
                    sc.next(); // descartar no-int
                }
                System.err.println("  ❌ Introduce un número entre 1 y 9.");
            }
            // 📐 Pedir y validar columna 1–9
            while (true) {
                System.out.print("Columna (1-9): ");
                if (sc.hasNextInt()) {
                    columna = sc.nextInt();
                    if (columna >= 1 && columna <= 9) break;
                } else {
                    sc.next();
                }
                System.err.println("  ❌ Introduce un número entre 1 y 9.");
            }
            sc.nextLine();


            while (true) {
                System.out.printf("Fila %d, Columna %d – Valor (1-9): ", fila, columna);
                if (sc.hasNextInt()) {
                    int valor = sc.nextInt();
                    sc.nextLine();
                    try {
                        sudoku.colocarNumero(fila, columna, valor);
                        break;
                    } catch (SudokuException ex) {
                        System.err.println("  → " + ex.getMessage());
                    }
                } else {
                    sc.nextLine();
                    System.err.println(" ❌ Introduce un número entre 1 y 9.");
                }
            }
        }

        System.out.println("\n🎉 ¡Sudoku completado!");
    }
}
