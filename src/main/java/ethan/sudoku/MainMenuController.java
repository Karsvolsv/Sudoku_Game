package ethan.sudoku;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controlador del menú principal de la aplicación Sudoku.
 * Gestiona las acciones de los botones del menú.
 */
public class MainMenuController {

    @FXML
    private Button playButton, instructionsButton, exitButton;

    /**
     * Inicializa las acciones de los botones del menú principal.
     */
    @FXML
    public void initialize() {
        playButton.setOnAction(e -> switchToGame());
        instructionsButton.setOnAction(e -> showAlert("Instrucciones del Juego",
                "Cómo jugar Sudoku",
                "Completa una cuadrícula 6x6 con números del 1 al 6 sin repetir en filas, columnas y bloques 2x3."));
        exitButton.setOnAction(e -> System.exit(0));
    }

    /**
     * Cambia a la vista del tablero de Sudoku.
     * Crea un modelo de Sudoku y lo establece en el controlador.
     */
    private void switchToGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewSudoku.fxml"));
            Parent root = loader.load();
            ControllerSudoku controller = loader.getController();
            controller.setModel(new ModelSudoku());

            Stage stage = (Stage) playButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sudoku Game");
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Error al iniciar el juego", "Intente nuevamente.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra una alerta con el mensaje proporcionado.
     *
     * @param title   Título de la alerta.
     * @param header  Encabezado de la alerta.
     * @param content Contenido del mensaje.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


