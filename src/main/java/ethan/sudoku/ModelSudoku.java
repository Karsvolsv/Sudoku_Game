package ethan.sudoku.Model;

public class TableroSudoku {
    private final int[][] tablero;
    private final boolean[][] casillasIniciales;  // Identificar casillas fijas

    public TableroSudoku() {
        tablero = new int[6][6];
        casillasIniciales = new boolean[6][6];
        inicializarTablero();
    }

    // Ejemplo de valores iniciales (puede personalizarse)
    private void inicializarTablero() {
        tablero[0][1] = 4; casillasIniciales[0][1] = true;
        tablero[2][2] = 5; casillasIniciales[2][2] = true;
        // Más valores aquí...
    }

    public int getValor(int fila, int columna) {
        return tablero[fila][columna];
    }

    public boolean esEditable(int fila, int columna) {
        return !casillasIniciales[fila][columna];
    }

    public boolean setValor(int fila, int columna, int valor) {
        if (esEditable(fila, columna) && esValorValido(fila, columna, valor)) {
            tablero[fila][columna] = valor;
            return true;
        }
        return false;
    }

    private boolean esValorValido(int fila, int columna, int valor) {
        // Verificar filas, columnas y cuadrantes 3x2
        for (int i = 0; i < 6; i++) {
            if (tablero[fila][i] == valor || tablero[i][columna] == valor) {
                return false;
            }
        }
        // Verificación del bloque 3x2
        int inicioFila = (fila / 2) * 2;
        int inicioColumna = (columna / 3) * 3;
        for (int i = inicioFila; i < inicioFila + 2; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                if (tablero[i][j] == valor) {
                    return false;
                }
            }
        }
        return true;
    }
}
