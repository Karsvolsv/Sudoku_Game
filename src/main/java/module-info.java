module ethan.sudoku {
    requires javafx.controls;
    requires javafx.fxml;


    opens ethan.sudoku to javafx.fxml;
    exports ethan.sudoku;
    exports ethan.sudoku.Controller;
    opens ethan.sudoku.Controller to javafx.fxml;
}