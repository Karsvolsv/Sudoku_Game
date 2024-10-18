package ethan.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación Sudoku.
 * Extiende la clase Application para establecer la interfaz gráfica del juego.
 */
public class Main extends Application {

    /**
     * Método que se llama al iniciar la aplicación.
     *
     * @param primaryStage La ventana principal de la aplicación.
     * @throws Exception Si ocurre un error durante la carga del archivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Sudoku Main Menu");
        primaryStage.setScene(new Scene(root, 600, 600)); // Establece el tamaño de la ventana
        primaryStage.show();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args Argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}



