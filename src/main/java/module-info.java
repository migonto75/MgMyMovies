module de.mgonzales.mgmymovies {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;


    opens de.mgonzales.mgmymovies to javafx.fxml;
    exports de.mgonzales.mgmymovies;
    exports de.mgonzales.mgmymovies.logic.database;
    opens de.mgonzales.mgmymovies.logic.database to javafx.fxml;
    exports de.mgonzales.mgmymovies.gui;
    opens de.mgonzales.mgmymovies.gui to javafx.fxml;
    exports de.mgonzales.mgmymovies.model;
    opens de.mgonzales.mgmymovies.model to javafx.fxml;
}