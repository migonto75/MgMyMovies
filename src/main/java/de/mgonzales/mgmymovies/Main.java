package de.mgonzales.mgmymovies;

import de.mgonzales.mgmymovies.settings.AppTexts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Mainklasse für die JavaFX-Anwendung.
 * Hier startet die Anwendung
 */

public class Main extends Application {

    /**
     * Die Startmethode ist der Haupt-Einstiegspunkt für alle JavaFX-Anwendungen.
     *
     * @param stage Die primäre Bühne für diese Anwendung, auf die die Anwendungsszene gesetzt wird.
     * @throws IOException Wenn das Laden der FXML-Datei fehlschlägt.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("movie-controller.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle(AppTexts.TXT_APP_NAME);
        stage.setScene(scene);
        stage.show();

    }


    public static void main(String[] args) {
        launch();
    }
}
