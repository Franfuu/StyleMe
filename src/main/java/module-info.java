module com.github.Franfuu {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.github.Franfuu to javafx.fxml;
    exports com.github.Franfuu;
}
