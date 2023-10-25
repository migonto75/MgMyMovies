package de.mgonzales.mgmymovies.gui;

import de.mgonzales.mgmymovies.logic.database.DaoMovie;
import de.mgonzales.mgmymovies.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-Klasse für das Dialogfenster zum Hinzufügen von Filmen.
 * Diese Klasse handhabt die Benutzerinteraktionen beim Hinzufügen eines neuen Films.
 */
public class AddMovieController {

    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField leadRoleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField lengthField;
    @FXML
    private TextField countryField;

    private final DaoMovie daoMovie;
    private OnMovieAddedListener onMovieAddedListener; // Listener für das Hinzufügen von Filmen

    public AddMovieController() {
        daoMovie = DaoMovie.getInstance();
    }

    public void setOnMovieAddedListener(OnMovieAddedListener listener) {
        this.onMovieAddedListener = listener;
    }

    @FXML
    protected void handleSaveMovieButtonAction() {
        Movie newMovie = new Movie(
                titleField.getText(),
                leadRoleField.getText(),
                genreField.getText(),
                Integer.parseInt(lengthField.getText()), // Fehlerbehandlung hinzufügen
                countryField.getText()
        );
        daoMovie.addMovieToDatabase(newMovie);

        if (onMovieAddedListener != null) {
            onMovieAddedListener.onMovieAdded(newMovie); // Benachrichtigt den Listener
        }

        // Schließen Sie das Fenster nach dem Speichern
        ((Stage) titleField.getScene().getWindow()).close();
    }

    public interface OnMovieAddedListener {
        void onMovieAdded(Movie newMovie);
    }
}


