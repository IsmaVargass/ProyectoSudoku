package ejercicio02.generator;

import com.ejercicio02.generator.GeneradorSudoku;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class GeneradorSudokuTest {

    private final GeneradorSudoku generador = new GeneradorSudoku();

    @Test
    void generar_Facil_GeneraTableroConMenosHuecos() {
        int[][] tablero = generador.generar("fácil");
        long vacias = contarVacias(tablero);
        assertTrue(vacias >= 30 && vacias <= 45,
                "Para dificultad fácil debe haber entre 30 y 45 celdas vacías");
    }

    @Test
    void generar_Dificil_GeneraTableroConMasHuecos() {
        int[][] tablero = generador.generar("difícil");
        long vacias = contarVacias(tablero);
        assertTrue(vacias >= 50 && vacias <= 65,
                "Para dificultad difícil debe haber entre 50 y 65 celdas vacías");
    }

    @Test
    void generar_ConDificultadInvalida_UsaPorDefecto() {
        int[][] tablero = generador.generar("desconocida");
        long vacias = contarVacias(tablero);
        assertTrue(vacias >= 30 && vacias <= 45,
                "Con dificultad desconocida debe usar el nivel fácil (30-45 celdas vacías)");
    }

    @Test
    void esSeguro_DevuelveTrueSiNoHayConflictos() throws Exception {
        int[][] grid = new int[9][9];
        Method metodo = GeneradorSudoku.class.getDeclaredMethod(
                "esSeguro", int[][].class, int.class, int.class, int.class);
        metodo.setAccessible(true);
        boolean resultado = (boolean) metodo.invoke(generador, grid, 0, 0, 5);
        assertTrue(resultado, "esSeguro debe devolver true en un grid vacío");
    }

    @Test
    void esSeguro_DevuelveFalseSiHayConflictoEnFila() throws Exception {
        int[][] grid = new int[9][9];
        grid[0][1] = 3;
        Method metodo = GeneradorSudoku.class.getDeclaredMethod(
                "esSeguro", int[][].class, int.class, int.class, int.class);
        metodo.setAccessible(true);
        boolean resultado = (boolean) metodo.invoke(generador, grid, 0, 0, 3);
        assertFalse(resultado, "esSeguro debe detectar conflicto en la fila");
    }

    @Test
    void rellenarSolucion_DevuelveGridCompletoYValido() throws Exception {
        int[][] grid = new int[9][9];
        Method metodo = GeneradorSudoku.class.getDeclaredMethod(
                "rellenarSolucion", int[][].class);
        metodo.setAccessible(true);
        boolean exito = (boolean) metodo.invoke(generador, new Object[]{grid});
        assertTrue(exito, "rellenarSolucion debería devolver true");

        for (int[] fila : grid) {
            for (int val : fila) {
                assertTrue(val >= 1 && val <= 9,
                        "Valor inválido en el tablero: " + val);
            }
        }
    }

    @Test
    void contarSoluciones_UnicaSolucionParaGridCompleto() throws Exception {
        int[][] grid = new int[9][9];
        Method metodoRellenar = GeneradorSudoku.class.getDeclaredMethod(
                "rellenarSolucion", int[][].class);
        metodoRellenar.setAccessible(true);
        boolean success = (boolean) metodoRellenar.invoke(generador, new Object[]{grid});
        assertTrue(success, "rellenarSolucion debería completar el grid");

        Method metodoContar = GeneradorSudoku.class.getDeclaredMethod(
                "contarSoluciones", int[][].class);
        metodoContar.setAccessible(true);
        int soluciones = (int) metodoContar.invoke(generador, new Object[]{grid});
        assertEquals(1, soluciones,
                "Un grid completo y válido debe tener exactamente una solución");
    }

    private long contarVacias(int[][] grid) {
        long count = 0;
        for (int[] fila : grid) {
            for (int val : fila) {
                if (val == 0) count++;
            }
        }
        return count;
    }
}
