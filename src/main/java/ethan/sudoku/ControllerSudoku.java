package ethan.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Importar Label
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * Controlador para la lógica del juego de Sudoku.
 * Maneja la interacción del usuario con la interfaz gráfica y
 * valida las entradas de los usuarios.
 */
public class ControllerSudoku {
    private ModelSudoku model;

    @FXML
    private GridPane boardGrid;

    @FXML
    private Button helpButton; // Asegúrate de tener el fx:id en el FXML

    @FXML
    private Label lastNumberLabel; // Declarar el Label para el último número

    /**
     * Método de inicialización del controlador.
     */
    @FXML
    public void initialize() {
        // Inicialización del controlador si es necesario.
    }

    /**
     * Establece el modelo de Sudoku y inicializa el tablero.
     *
     * @param model El modelo de Sudoku que contiene la lógica del juego.
     */
    public void setModel(ModelSudoku model) {
        this.model = model;
        initializeBoard();
    }

    /**
     * Inicializa el tablero de juego en la interfaz gráfica.
     */
    private void initializeBoard() {
        boardGrid.getChildren().clear();
        int[][] board = model.getBoard();
        boolean[][] isEditable = model.getInitialCells();
        double cellSize = 100.0;

        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 6; column++) {
                TextField cell = new TextField();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle(getCellStyle(row, column));

                int value = board[row][column];
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setDisable(!isEditable[row][column]);
                    cell.setStyle(cell.getStyle() + " -fx-background-color: #e0e0e0;");
                }

                cell.setOnKeyReleased(validateInputEvent(row, column, cell));
                boardGrid.add(cell, column, row);
            }
        }
    }

    /**
     * Devuelve el estilo de la celda basado en su posición.
     *
     * @param row    La fila de la celda.
     * @param column La columna de la celda.
     * @return El estilo de la celda.
     */
    private String getCellStyle(int row, int column) {
        String baseStyle = "-fx-font-size: 18; -fx-alignment: CENTER; " +
                "-fx-border-color: #000000; -fx-border-width: 1;";

        boolean isInGrayBlock = ((row / 2) + (column / 3)) % 2 == 0;
        return baseStyle + (isInGrayBlock ? " -fx-background-color: #d3d3d3;" : " -fx-background-color: #ffffff;");
    }

    /**
     * Valida la entrada del usuario y actualiza el modelo según sea necesario.
     *
     * @param row    La fila de la celda.
     * @param column La columna de la celda.
     * @param cell   El campo de texto de la celda.
     * @return Un controlador de eventos para el evento de liberación de tecla.
     */
    private javafx.event.EventHandler<KeyEvent> validateInputEvent(int row, int column, TextField cell) {
        return e -> {
            String text = cell.getText();
            if (text.isEmpty()) {
                resetCellStyle(cell, row, column);
                lastNumberLabel.setText("Último número: "); // Limpiar el Label si está vacío
                return;
            }

            try {
                int value = Integer.parseInt(text);
                if (value < 1 || value > 6) {
                    showError("Número fuera del rango (1-6).");
                    highlightError(cell);
                } else if (!model.isValueValid(row, column, value)) {
                    showError("Número repetido en el bloque 2x3.");
                    highlightError(cell);
                } else {
                    resetCellStyle(cell, row, column);
                    model.setValue(row, column, value);
                    lastNumberLabel.setText("Último número: " + value); // Actualizar el Label
                }
            } catch (NumberFormatException ex) {
                showError("Entrada inválida. Debe ser un número.");
                highlightError(cell);
            }
        };
    }

    /**
     * Resalta la celda con un borde rojo para indicar un error.
     *
     * @param cell La celda a resaltar.
     */
    private void highlightError(TextField cell) {
        cell.setStyle(cell.getStyle() + " -fx-border-color: red; -fx-border-width: 2;");
    }

    /**
     * Restablece el estilo de la celda a su estado original.
     *
     * @param cell   La celda a restablecer.
     * @param row    La fila de la celda.
     * @param column La columna de la celda.
     */
    private void resetCellStyle(TextField cell, int row, int column) {
        cell.setStyle(getCellStyle(row, column));
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param message El mensaje de error a mostrar.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Validación");
        alert.setHeaderText("Entrada Inválida");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de finalización del juego en un cuadro de diálogo.
     */
    public void showCompletionMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Juego Completado");
        alert.setHeaderText("¡Felicidades!");
        alert.setContentText("Has completado el Sudoku.");
        alert.showAndWait();
    }

    /**
     * Maneja el evento del botón de ayuda, proporcionando una sugerencia al usuario.
     */
    @FXML
    private void handleHelpButton() {
        Random random = new Random();
        int row, column;

        do {
            row = random.nextInt(6);
            column = random.nextInt(6);
        } while (model.getBoard()[row][column] != 0);

        int randomValue;
        do {
            randomValue = random.nextInt(6) + 1; // Generar un número aleatorio del 1 al 6
        } while (!model.isValueValid(row, column, randomValue));

        model.setValue(row, column, randomValue); // Rellenar la celda
        TextField cell = (TextField) boardGrid.getChildren().get(row * 6 + column);
        cell.setText(String.valueOf(randomValue)); // Mostrar el número en la celda
        cell.setDisable(true); // Deshabilitar la celda para que no se pueda modificar
        cell.setStyle(cell.getStyle() + " -fx-background-color: #e0e0e0;"); // Estilo para celda completada
        lastNumberLabel.setText("Último número: " + randomValue); // Actualizar el Label
    }
}


















