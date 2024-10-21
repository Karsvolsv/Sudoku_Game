package ethan.sudoku;

import java.util.Random;

/**
 * Modelo del Sudoku que maneja la lógica del juego.
 */
public class ModelSudoku {
    private int[][][] boards; // Matriz de tableros, cada tablero es una matriz 6x6
    private int currentBoardIndex; // Índice del tablero actual
    private boolean[][] initialCells; // Indica qué celdas son editables

    public ModelSudoku() {
        // Inicializa los tableros (puedes agregar más tableros)
        boards = new int[3][6][6]; // Suponiendo que tienes 3 tableros diferentes
        initialCells = new boolean[6][6]; // Inicializa las celdas editables
        currentBoardIndex = 0; // Comienza con el primer tablero
        initializeBoards();
    }

    public void initializeBoards() {
        Random rand = new Random();

        // Inicializa cada tablero asegurando que hay exactamente 2 números en cada bloque 2x3
        for (int i = 0; i < boards.length; i++) {
            // Llena el tablero con ceros (celdas vacías)
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    boards[i][row][col] = 0; // Celdas vacías
                    initialCells[row][col] = false; // Marca las celdas como editables
                }
            }

            // Coloca 2 números aleatorios en cada bloque 2x3
            for (int blockRow = 0; blockRow < 3; blockRow++) { // Hay 3 bloques de 2 filas
                for (int blockCol = 0; blockCol < 2; blockCol++) { // Hay 2 bloques de 3 columnas
                    // Almacena los números que se colocarán en el bloque
                    int[] numbers = new int[2];
                    numbers[0] = rand.nextInt(6) + 1; // Elige un número aleatorio (del 1 al 6)
                    do {
                        numbers[1] = rand.nextInt(6) + 1; // Elige otro número aleatorio
                    } while (numbers[0] == numbers[1]); // Asegúrate de que sean diferentes

                    // Coloca los números en posiciones aleatorias dentro del bloque 2x3
                    for (int j = 0; j < 2; j++) {
                        int row, col;
                        do {
                            row = blockRow * 2 + rand.nextInt(2); // Selecciona fila aleatoria dentro del bloque
                            col = blockCol * 3 + rand.nextInt(3); // Selecciona columna aleatoria dentro del bloque
                        } while (boards[i][row][col] != 0); // Asegúrate de que la celda esté vacía

                        boards[i][row][col] = numbers[j]; // Coloca el número
                        initialCells[row][col] = true; // Marca la celda como no editable
                    }
                }
            }
        }
    }

    public int[][] getBoard() {
        return boards[currentBoardIndex]; // Retorna el tablero actual
    }

    public boolean[][] getInitialCells() {
        return initialCells; // Devuelve las celdas editables
    }

    public void setValue(int row, int col, int value) {
        boards[currentBoardIndex][row][col] = value; // Establece el valor en la celda
    }

    public boolean isValueValid(int row, int col, int value) {
        // Lógica para validar si el valor es válido
        // Verifica si el valor ya está en la fila, columna o bloque

        // Verificar filas y columnas
        for (int i = 0; i < 6; i++) {
            if (boards[currentBoardIndex][row][i] == value || boards[currentBoardIndex][i][col] == value) {
                return false;
            }
        }

        // Verifica el bloque 2x3 correspondiente
        int blockRowStart = (row / 2) * 2;
        int blockColStart = (col / 3) * 3;
        int count = 0;

        for (int r = blockRowStart; r < blockRowStart + 2; r++) {
            for (int c = blockColStart; c < blockColStart + 3; c++) {
                if (boards[currentBoardIndex][r][c] == value) {
                    count++;
                }
            }
        }

        // Solo se permiten 2 números del mismo tipo en el bloque 2x3
        if (count >= 2) {
            return false;
        }

        return true; // El valor es válido
    }

    public void setCurrentBoardIndex(int index) {
        if (index >= 0 && index < boards.length) {
            currentBoardIndex = index; // Cambia el índice del tablero actual
        } else {
            throw new IndexOutOfBoundsException("Índice de tablero fuera de rango.");
        }
    }

    public int[][] getSolution() {
        // Método para obtener la solución del tablero actual (si se implementa)
        return boards[currentBoardIndex]; // Aquí puedes devolver la solución real
    }
}






















