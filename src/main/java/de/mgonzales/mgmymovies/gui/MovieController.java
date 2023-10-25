package de.mgonzales.mgmymovies.gui;

import de.mgonzales.mgmymovies.logic.database.DaoMovie;
import de.mgonzales.mgmymovies.model.Movie;
import de.mgonzales.mgmymovies.settings.AppTexts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Controller-Klasse für die Hauptoberfläche der Anwendung.
 * Diese Klasse handhabt alle Benutzerinteraktionen und Datenoperationen für Filme.
 */
public class MovieController implements AddMovieController.OnMovieAddedListener {

    public static final String TXT_URL = "jdbc:mariadb://localhost:3306/mymovies";
    public static final String TXT_USERNAME = "root";
    public static final String TXT_PASSWORD = "";
    @FXML
    private TableView<Movie> tableMovies;
    @FXML
    private TableColumn<Movie, String> colTitle;
    @FXML
    private TableColumn<Movie, String> colLeadRole;
    @FXML
    private TableColumn<Movie, String> colGenre;
    @FXML
    private TableColumn<Movie, Integer> colDuration;
    @FXML
    private TableColumn<Movie, String> colCountry;
    private final ObservableList<Movie> movieList = FXCollections.observableArrayList();
    private final DaoMovie daoMovie = new DaoMovie(); // oder erhalten Sie diese Instanz anders, falls erforderlich


    //region Methoden
    @FXML
    private void initialize() {
        setupTableColumns();
        loadMoviesFromDatabase();
    }

    @FXML
    protected void handleAddMovie() {
        showAddMovieDialog();
    }

    private void setupTableColumns() {
        // Hier wird die CellValueFactory für jede Spalte konfiguriert
        colTitle.setCellValueFactory(new PropertyValueFactory<>(AppTexts.TXT_COL_TITLE));
        colLeadRole.setCellValueFactory(new PropertyValueFactory<>(AppTexts.TXT_COL_LEADROLE));
        colGenre.setCellValueFactory(new PropertyValueFactory<>(AppTexts.TXT_COL_GENRE));
        colDuration.setCellValueFactory(new PropertyValueFactory<>(AppTexts.TXT_COL_DURATION));
        colCountry.setCellValueFactory(new PropertyValueFactory<>(AppTexts.TXT_COL_COUNTRY));
    }

    private void loadMoviesFromDatabase() {
        List<Movie> moviesFromDb = daoMovie.getAllMoviesFromDatabase();
        if (moviesFromDb == null || moviesFromDb.isEmpty()) {
            showAlert(Alert.AlertType.INFORMATION, AppTexts.ALERT_INFO, AppTexts.ALERT_NO_MOVIES_FOUND_IN_DB);
        } else {
            movieList.setAll(moviesFromDb); // Aktualisiert die Liste mit den Filmen aus der Datenbank
            tableMovies.setItems(movieList); // Setzt die aktualisierte Liste als Items für die TableView
            tableMovies.refresh(); // Erzwingt ein erneutes Zeichnen der Tabelle
        }
    }

    @FXML
    private void handleShowMovieList(ActionEvent event) {
        loadMoviesFromDatabase();
    }

    @FXML
    private void handleUpdateMovie() {
        Movie selectedMovie = tableMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            boolean okClicked = showUpdateMovieDialog(selectedMovie);
            if (okClicked) {
                loadMoviesFromDatabase(); // Aktualisieren Sie die Anzeige, um die gemachten Änderungen zu zeigen
            }
        } else {
            showAlert(Alert.AlertType.WARNING, AppTexts.ALERT_NO_CHOICE, AppTexts.ALERT_SELECT_MOVIE_FROM_TABLE);
        }
    }

    private boolean showUpdateMovieDialog(Movie movie) {
        try {
            // Laden der FXML-Datei für den UpdateMovieDialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/mgonzales/mgmymovies/update-movie-dialog.fxml")); // Pfad aktualisieren
            Stage stage = new Stage();
            stage.setTitle(AppTexts.TXT_STAGE_HEAD_EDIT_MOVIE);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Setzen des Films und der Bühne im UpdateMovieController
            UpdateMovieController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setMovie(movie);

            stage.showAndWait();

            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace(); // oder bessere Fehlerbehandlung
            return false;
        }
    }

    private void refreshMovieTable() {
        // Aktualisieren Sie die Filme in der Tabelle, z.B. durch erneutes Laden aus der Datenbank
        loadMoviesFromDatabase();
    }

    private void showAddMovieDialog() {
        try {
            // Laden der FXML-Datei für den AddMovieDialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/mgonzales/mgmymovies/add-movie-dialog.fxml")); // Pfad aktualisieren
            Stage stage = new Stage();
            stage.setTitle(AppTexts.TXT_STAGE_HEAD_ADD_MOVIE);
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Setzen des MovieControllers als Listener im AddMovieController
            AddMovieController controller = loader.getController();
            controller.setOnMovieAddedListener(this);

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace(); // oder bessere Fehlerbehandlung
        }
    }

    @FXML
    private void handleDeleteMovie() {
        Movie selectedMovie = tableMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null) {
            // Erstellen Sie ein neues Alert-Objekt
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(AppTexts.TXT_ALERT_DELETE_MOVIE);
            alert.setHeaderText(AppTexts.TXT_ALERT_CONFIRM_DELETION);
            alert.setContentText(AppTexts.TXT_ALERT_CONFIRM_TO_DELETE_MOVIE);
            // Datenbankoperation
//            String url = TXT_URL; // Pfad zu Ihrer Datenbank
//            String password = TXT_PASSWORD;
//            String sql = AppTexts.TXT_DELETE_MOVIE;

            try (Connection conn = DriverManager.getConnection(TXT_URL, TXT_USERNAME, TXT_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(AppTexts.TXT_DELETE_MOVIE)) {

                // Setzen Sie den Parameter
                pstmt.setInt(1, selectedMovie.getId());
                // Update ausführen
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            // Film aus der Tabelle entfernen
            tableMovies.getItems().remove(selectedMovie);
        } else {
            showAlert(Alert.AlertType.WARNING, AppTexts.ALERT_NO_CHOICE, AppTexts.ALERT_SELECT_MOVIE_FROM_TABLE);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void onMovieAdded(Movie movie) {
        // Fügt den neuen Film zur TableView hinzu
        tableMovies.getItems().add(movie);
    }
    //endregion Methoden
}
