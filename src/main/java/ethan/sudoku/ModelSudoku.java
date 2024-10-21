package ethan.sudoku;

import java.util.Random;

/**
 * Modelo del Sudoku que maneja la lógica del juego.
 */
public class ModelSudoku {
    private int[][][] boards; // Matriz de tableros, cada tablero es una matriz 6x6
    private int currentBoardIndex; // Índice del tablero actual
    private boolean[][] initialCells; // Indica qué celdas son editables

    /**
     * Constructor que inicializa los tableros de Sudoku.
     * Inicializa tres tableros diferentes y establece el índice del tablero actual en 0.
     */
    public ModelSudoku() {
        // Inicializa los tableros (puedes agregar más tableros)
        boards = new int[3][6][6]; // Suponiendo que tienes 3 tableros diferentes
        initialCells = new boolean[6][6]; // Inicializa las celdas editables
        currentBoardIndex = 0; // Comienza con el primer tablero
        initializeBoards();
    }

    /**
     * Inicializa los tableros asegurando que hay exactamente 2 números en cada bloque 2x3.
     */
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
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 2; blockCol++) {
                    int count = 0;
                    while (count < 2) {
                        int row = blockRow * 2 + rand.nextInt(2);
                        int col = blockCol * 3 + rand.nextInt(3);
                        if (boards[i][row][col] == 0) { // Si la celda está vacía
                            boards[i][row][col] = rand.nextInt(6) + 1; // Coloca un número aleatorio del 1 al 6
                            initialCells[row][col] = true; // Marca como no editable
                            count++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Obtiene el tablero actual.
     *
     * @return El tablero actual como una matriz 6x6.
     */
    public int[][] getBoard() {
        return boards[currentBoardIndex];
    }

    /**
     * Obtiene las celdas iniciales del tablero actual.
     *
     * @return Una matriz booleana que indica qué celdas son editables.
     */
    public boolean[][] getInitialCells() {
        return initialCells;
    }

    /**
     * Establece un valor en la celda especificada del tablero actual.
     *
     * @param row   Fila de la celda.
     * @param col   Columna de la celda.
     * @param value Valor a establecer en la celda.
     */
    public void setValue(int row, int col, int value) {
        boards[currentBoardIndex][row][col] = value;
    }

    /**
     * Verifica si el valor es válido en la posición especificada.
     *
     * @param row   Fila de la celda.
     * @param col   Columna de la celda.
     * @param value Valor a verificar.
     * @return Verdadero si el valor es válido, falso en caso contrario.
     */
    public boolean isValueValid(int row, int col, int value) {
        // Verificar fila
        for (int c = 0; c < 6; c++) {
            if (c != col && boards[currentBoardIndex][row][c] == value) {
                return false; // Repetido en la fila
            }
        }
        // Verificar columna
        for (int r = 0; r < 6; r++) {
            if (r != row && boards[currentBoardIndex][r][col] == value) {
                return false; // Repetido en la columna
            }
        }
        // Verificar bloque 2x3
        int blockRow = row / 2;
        int blockCol = col / 3;
        for (int r = blockRow * 2; r < blockRow * 2 + 2; r++) {
            for (int c = blockCol * 3; c < blockCol * 3 + 3; c++) {
                if ((r != row || c != col) && boards[currentBoardIndex][r][c] == value) {
                    return false; // Repetido en el bloque
                }
            }
        }
        return true; // El valor es válido
    }

    /**
     * Obtiene la solución del tablero actual.
     *
     * @return El tablero de solución.
     */
    public int[][] getSolution() {
        // Implementar la lógica para obtener la solución si es necesario.
        return boards[currentBoardIndex]; // Esto debe ser reemplazado con el tablero de solución real.
    }

    /**
     * Establece el índice del tablero actual.
     *
     * @param index El índice del nuevo tablero.
     */
    public void setCurrentBoardIndex(int index) {
        this.currentBoardIndex = index;
    }
}

















