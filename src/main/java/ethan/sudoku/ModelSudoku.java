package ethan.sudoku;

import java.util.Random;

/**
 * Modelo de datos para el juego de Sudoku.
 * Maneja el tablero, las celdas iniciales y la lógica del juego.
 */
public class ModelSudoku {
    private final int[][] board; // Tablero de Sudoku
    private final boolean[][] initialCells; // Celdas que son iniciales
    private final MovimientoStack movimientos; // Pila para deshacer movimientos
    private ControllerSudoku controller; // Controlador del juego

    /**
     * Constructor que inicializa el tablero y las celdas iniciales.
     */
    public ModelSudoku() {
        board = new int[6][6];
        initialCells = new boolean[6][6];
        movimientos = new MovimientoStack(100);
        initializeBoard();
    }

    /**
     * Inicializa el tablero con un número aleatorio de celdas iniciales.
     */
    private void initializeBoard() {
        Random random = new Random();
        int numberOfInitialCells = 5; // Cambia este número según tus necesidades

        int count = 0;
        while (count < numberOfInitialCells) {
            int row = random.nextInt(6);
            int column = random.nextInt(6);

            // Solo llenar celdas que estén vacías
            if (board[row][column] == 0) {
                int randomValue = random.nextInt(6) + 1; // Número aleatorio del 1 al 6
                board[row][column] = randomValue; // Establecer el valor en el tablero
                initialCells[row][column] = true; // Marcar la celda como inicial
                count++; // Incrementar el contador de celdas iniciales
            }
        }
    }

    /**
     * Establece el controlador para interactuar con la vista.
     *
     * @param controller Controlador del Sudoku
     */
    public void setController(ControllerSudoku controller) {
        this.controller = controller;
    }

    /**
     * Obtiene el tablero actual de Sudoku.
     *
     * @return Tablero de Sudoku
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Obtiene las celdas iniciales del tablero.
     *
     * @return Matriz de celdas iniciales
     */
    public boolean[][] getInitialCells() {
        return initialCells;
    }

    /**
     * Verifica si una celda es editable.
     *
     * @param row Fila de la celda
     * @param column Columna de la celda
     * @return true si la celda es editable, false en caso contrario
     */
    public boolean isEditable(int row, int column) {
        return !initialCells[row][column];
    }

    /**
     * Establece un valor en una celda del tablero.
     *
     * @param row Fila de la celda
     * @param column Columna de la celda
     * @param value Valor a establecer
     * @return true si se pudo establecer el valor y se completó el tablero, false en caso contrario
     */
    public boolean setValue(int row, int column, int value) {
        if (isEditable(row, column) && isValueValid(row, column, value)) {
            board[row][column] = value;
            movimientos.push(new int[]{row, column, value});

            if (isBoardComplete()) {
                controller.showCompletionMessage();
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina el valor de una celda del tablero.
     *
     * @param row Fila de la celda
     * @param column Columna de la celda
     */
    public void clearValue(int row, int column) {
        if (isEditable(row, column)) {
            board[row][column] = 0; // Eliminar el valor completamente
        }
    }

    /**
     * Verifica si un valor es válido para una celda específica.
     *
     * @param row Fila de la celda
     * @param column Columna de la celda
     * @param value Valor a validar
     * @return true si el valor es válido, false en caso contrario
     */
    public boolean isValueValid(int row, int column, int value) {
        int startRow = (row / 2) * 2;
        int startColumn = (column / 3) * 3;

        // Verificar duplicados en el bloque 2x3
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startColumn; j < startColumn + 3; j++) {
                if (board[i][j] == value && !(i == row && j == column)) {
                    return false; // No permitir el mismo número en el mismo bloque
                }
            }
        }
        return true;
    }

    /**
     * Verifica si el tablero está completo.
     *
     * @return true si el tablero está completo, false en caso contrario
     */
    public boolean isBoardComplete() {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 6; column++) {
                if (board[row][column] == 0) {
                    return false; // Hay al menos una celda vacía
                }
            }
        }
        return true; // Todas las celdas están llenas
    }

    /**
     * Clase interna que representa una pila para manejar los movimientos.
     */
    private class MovimientoStack {
        private final int[][] stack; // Pila para almacenar movimientos
        private int top; // Índice de la parte superior de la pila

        /**
         * Constructor que inicializa la pila con la capacidad dada.
         *
         * @param capacity Capacidad máxima de la pila
         */
        public MovimientoStack(int capacity) {
            stack = new int[capacity][3];
            top = -1;
        }

        /**
         * Agrega un movimiento a la pila.
         *
         * @param movimiento Movimiento a agregar
         */
        public void push(int[] movimiento) {
            if (top < stack.length - 1) {
                stack[++top] = movimiento;
            }
        }

        /**
         * Elimina y retorna el último movimiento de la pila.
         *
         * @return Último movimiento o null si la pila está vacía
         */
        public int[] pop() {
            if (top >= 0) {
                return stack[top--];
            }
            return null;
        }

        /**
         * Verifica si la pila está vacía.
         *
         * @return true si la pila está vacía, false en caso contrario
         */
        public boolean isEmpty() {
            return top == -1;
        }
    }
}










