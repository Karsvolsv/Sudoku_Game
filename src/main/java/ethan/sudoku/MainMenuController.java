package ethan.sudoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; // Importa la clase Button
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador del menú principal de la aplicación Sudoku.
 * Maneja las interacciones del usuario con los botones del menú.
 */
public class MainMenuController {

    @FXML
    private Button playButton;
    @FXML
    private Button instructionsButton;
    @FXML
    private Button exitButton;

    /**
     * Inicializa el controlador y asigna las acciones a los botones.
     */
    @FXML
    public void initialize() {
        playButton.setOnAction(event -> startGame());
        instructionsButton.setOnAction(event -> showInstructions());
        exitButton.setOnAction(event -> exitApplication());
    }

    /**
     * Inicia el juego al cambiar a la vista del tablero de Sudoku.
     * Crea un nuevo modelo de Sudoku y lo establece en el controlador correspondiente.
     */
    private void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewSudoku.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y establecer el modelo
            ControllerSudoku controller = loader.getController();
            ModelSudoku model = new ModelSudoku();
            controller.setModel(model);
            model.setController(controller);

            // Cambiar a la nueva escena
            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sudoku Game");
            stage.show();
        } catch (IOException e) {
            showError("Error al iniciar el juego. Intente nuevamente.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra un cuadro de diálogo con las instrucciones del juego.
     */
    private void showInstructions() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instrucciones del Juego");
        alert.setHeaderText("Cómo jugar Sudoku");
        alert.setContentText("Debes completar una cuadrícula de 6x6 con números del 1 al 6, de manera que cada fila," +
                             "columna y bloque de 2x3 contenga todos los números sin repetir");
        alert.showAndWait();
    }

    /**
     * Cierra la aplicación.
     */
    private void exitApplication() {
        System.exit(0);
    }

    /**
     * Muestra un mensaje de error.
     *
     * @param message Mensaje de error a mostrar.
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}


