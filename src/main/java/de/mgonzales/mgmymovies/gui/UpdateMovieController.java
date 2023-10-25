package de.mgonzales.mgmymovies.gui;

import de.mgonzales.mgmymovies.logic.database.DaoMovie;
import de.mgonzales.mgmymovies.model.Movie;
import de.mgonzales.mgmymovies.settings.AppTexts;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller-Klasse für das UpdateMovie-Fenster.
 * Diese Klasse handhabt alle Benutzerinteraktionen und aktualisiert die Daten in der Movie-Instanz.
 */
public class UpdateMovieController {

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

    private Stage dialogStage;
    private Movie movie;
    private boolean okClicked = false;

    private DaoMovie daoMovie;


    public UpdateMovieController() {
        daoMovie = DaoMovie.getInstance();
    }

    public void setMovie(Movie movie) {
        this.movie = movie;

        titleField.setText(movie.getTitle());
        leadRoleField.setText(movie.getLeadRole());
        genreField.setText(movie.getGenre());
        lengthField.setText(Integer.toString(movie.getDuration()));
        countryField.setText(movie.getCountry());
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            movie.setTitle(titleField.getText());
            movie.setLeadRole(leadRoleField.getText());
            movie.setGenre(genreField.getText());
            movie.setDuration(Integer.parseInt(lengthField.getText()));
            movie.setCountry(countryField.getText());

            okClicked = true;
            daoMovie.updateMovieInDatabase(movie);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = AppTexts.TXT_INVALID_INPUT;
        return true;
    }
}
