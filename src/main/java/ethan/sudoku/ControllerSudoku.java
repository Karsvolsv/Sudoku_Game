package ethan.sudoku;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.util.Random;

/**
 * Controlador del Sudoku que maneja la interfaz y la lógica del juego.
 */
public class ControllerSudoku {
    private ModelSudoku model;

    @FXML
    private GridPane boardGrid;
    @FXML
    private Button helpButton;
    @FXML
    private Label lastNumberLabel;
    @FXML
    private Button newGameButton; // Botón para nuevo juego

    /**
     * Inicializa el controlador.
     * Configuraciones iniciales si es necesario.
     */
    @FXML
    public void initialize() {
        // Configuraciones iniciales si es necesario.
    }

    /**
     * Establece el modelo del juego.
     *
     * @param model El modelo del juego a establecer.
     */
    public void setModel(ModelSudoku model) {
        this.model = model;
        initializeBoard();
    }

    /**
     * Inicializa el tablero de Sudoku en la interfaz gráfica.
     * Crea las celdas del tablero y establece su estilo y comportamiento.
     */
    private void initializeBoard() {
        boardGrid.getChildren().clear();
        int[][] board = model.getBoard();
        boolean[][] isEditable = model.getInitialCells();
        double cellSize = 100.0;

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                TextField cell = new TextField();
                cell.setPrefSize(cellSize, cellSize);
                cell.setStyle(getCellStyle(row, col));

                int value = board[row][col];
                if (value != 0) {
                    cell.setText(String.valueOf(value));
                    cell.setDisable(!isEditable[row][col]);
                }

                cell.setOnKeyReleased(validateInputEvent(row, col, cell));
                boardGrid.add(cell, col, row);
            }
        }
    }

    /**
     * Obtiene el estilo de la celda según su posición en el tablero.
     *
     * @param row Fila de la celda.
     * @param col Columna de la celda.
     * @return El estilo CSS de la celda.
     */
    private String getCellStyle(int row, int col) {
        boolean isInGrayBlock = ((row / 2) + (col / 3)) % 2 == 0;
        return "-fx-font-size: 18; -fx-alignment: CENTER; " +
                "-fx-border-color: #000; -fx-border-width: 1; " +
                (isInGrayBlock ? "-fx-background-color: #d3d3d3;" : "-fx-background-color: #fff;");
    }

    /**
     * Valida la entrada del usuario en una celda.
     *
     * @param row  Fila de la celda.
     * @param col  Columna de la celda.
     * @param cell La celda de texto a validar.
     * @return Un EventHandler para manejar el evento de liberación de teclas.
     */
    private javafx.event.EventHandler<KeyEvent> validateInputEvent(int row, int col, TextField cell) {
        return e -> {
            String text = cell.getText();
            if (text.isEmpty()) {
                resetCellStyle(cell, row, col);
                lastNumberLabel.setText("Último número: ");
                return;
            }

            try {
                int value = Integer.parseInt(text);
                if (value < 1 || value > 6 || !model.isValueValid(row, col, value)) {
                    showError("Número inválido o repetido.");
                    highlightError(cell);
                } else {
                    resetCellStyle(cell, row, col);
                    model.setValue(row, col, value);
                    lastNumberLabel.setText("Último número: " + value);
                }
            } catch (NumberFormatException ex) {
                showError("Entrada inválida. Debe ser un número.");
                highlightError(cell);
            }
        };
    }

    /**
     * Resalta un error en la celda.
     *
     * @param cell La celda que contiene el error.
     */
    private void highlightError(TextField cell) {
        cell.setStyle(cell.getStyle() + " -fx-border-color: red; -fx-border-width: 2;");
    }

    /**
     * Restablece el estilo de la celda a su valor predeterminado.
     *
     * @param cell La celda a restablecer.
     * @param row  Fila de la celda.
     * @param col  Columna de la celda.
     */
    private void resetCellStyle(TextField cell, int row, int col) {
        cell.setStyle(getCellStyle(row, col));
    }

    /**
     * Maneja el evento del botón de ayuda.
     * Revela un número de una celda vacía en el tablero.
     */
    @FXML
    private void handleHelpButton() {
        Random random = new Random();
        int row, col;

        // Buscar una celda vacía en el tablero actual
        do {
            row = random.nextInt(6);
            col = random.nextInt(6);
        } while (model.getBoard()[row][col] != 0); // Asegurarse de que sea una celda vacía

        // Obtener el valor de la solución del tablero
        int[][] solution = model.getSolution();
        int value = solution[row][col];

        // Mostrar el valor en la celda correspondiente
        model.setValue(row, col, value); // Actualizar el modelo si es necesario

        TextField cell = (TextField) boardGrid.getChildren().get(row * 6 + col);
        cell.setText(String.valueOf(value)); // Revelar el número
        cell.setDisable(true); // Deshabilitar la celda para que no pueda ser editada
    }

    /**
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param message El mensaje de error a mostrar.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Cambia el tablero actual.
     *
     * @param index El índice del nuevo tablero.
     */
    @FXML
    private void changeBoard(int index) {
        try {
            model.setCurrentBoardIndex(index); // Cambia el índice del tablero actual
            initializeBoard(); // Reinicializa el tablero con el nuevo índice
        } catch (IndexOutOfBoundsException e) {
            showError("Índice de tablero inválido: " + index);
        }
    }

    /**
     * Maneja el evento del botón para iniciar un nuevo juego.
     * Reinicia los tableros y el estado del juego.
     */
    @FXML
    private void handleNewGameButton() {
        model.initializeBoards(); // Reinicia los tableros
        initializeBoard(); // Reinicializa el tablero
        lastNumberLabel.setText("Último número: "); // Resetea el label
    }
}


























