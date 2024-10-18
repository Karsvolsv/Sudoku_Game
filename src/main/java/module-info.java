module ethan.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens ethan.sudoku to javafx.fxml;  // Ensure this line is present
    exports ethan.sudoku;  // This is necessary to export your main package
}

