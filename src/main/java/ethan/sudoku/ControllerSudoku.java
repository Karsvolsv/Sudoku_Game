package ethan.sudoku.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class ControladorSudoku {
    private TableroSudoku modelo;

    @FXML
    private GridPane tableroGrid;

    public void setModelo(TableroSudoku modelo) {
        this.modelo = modelo;
        inicializarTablero();
    }

    private void inicializarTablero() {
        tableroGrid.getChildren().clear();
        int[][] tablero = modelo.getTablero();
        boolean[][] esEditable = modelo.getCasillasIniciales();

        for (int fila = 0; fila < 6; fila++) {
            for (int columna = 0; columna < 6; columna++) {
                TextField celda = new TextField();
                celda.setPrefSize(50, 50);

                int valor = tablero[fila][columna];
                if (valor != 0) {
                    celda.setText(String.valueOf(valor));
                    celda.setDisable(!esEditable[fila][columna]);  // Deshabilitar casillas fijas
                }

                celda.setOnKeyReleased(eventoValidarEntrada(fila, columna, celda));
                tableroGrid.add(celda, columna, fila);
            }
        }
    }

    private javafx.event.EventHandler<KeyEvent> eventoValidarEntrada(int fila, int columna, TextField celda) {
        return e -> {
            String texto = celda.getText();
            if (texto.isEmpty()) return;

            try {
                int valor = Integer.parseInt(texto);
                if (!modelo.setValor(fila, columna, valor)) {
                    celda.setText("");  // Valor no válido
                }
            } catch (NumberFormatException ex) {
                celda.setText("");  // Manejo de caracteres no numéricos
            }
        };
    }
}
