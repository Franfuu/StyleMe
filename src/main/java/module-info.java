module com.github.Franfuu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires org.mariadb.jdbc;
    requires java.sql;
    requires org.checkerframework.checker.qual;
    requires java.desktop;

    opens com.github.Franfuu.model.connection to java.xml.bind;
    opens com.github.Franfuu to javafx.fxml;
    exports com.github.Franfuu;

    exports com.github.Franfuu.view to javafx.fxml;
    opens com.github.Franfuu.view to javafx.fxml;

    opens com.github.Franfuu.model.entity to javafx.base;
    exports com.github.Franfuu.model.entity;

}
