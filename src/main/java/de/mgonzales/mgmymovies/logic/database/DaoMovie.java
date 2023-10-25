package de.mgonzales.mgmymovies.logic.database;

import de.mgonzales.mgmymovies.model.Movie;
import de.mgonzales.mgmymovies.settings.AppTexts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Die DaoMovie-Klasse implementiert die Datenzugriffsmethoden für die Movie-Entität.
 * Sie stellt Methoden bereit, um Filme in der Datenbank zu erstellen, abzurufen, zu aktualisieren und zu löschen.
 */
public class DaoMovie {
    //region Konstanten
    private static final String TXT_URL = "jdbc:mariadb://localhost:3306/mymovies";
    private static final String TXT_USERNAME = "root";
    private static final String TXT_PASSWORD = "";
    //endregion Konstanten

    //region Attribute
    private static DaoMovie instance;
    //endregion Attribute

    //region Methoden
    public DaoMovie() {
        // Hier können Sie den JDBC-Treiber laden und eine Verbindung zur Datenbank herstellen, falls erforderlich
        try {
            Class.forName(AppTexts.TXT_DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DaoMovie getInstance() {
        if (instance == null) {
            instance = new DaoMovie();
        }
        return instance;
    }

    private Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(TXT_URL, TXT_USERNAME, TXT_PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public List<Movie> getAllMoviesFromDatabase() {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(TXT_URL, TXT_USERNAME, TXT_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(AppTexts.TXT_SELECT_ALL_MOVIES);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt(AppTexts.TXT_DATABASE_COL_ID);
                String title = resultSet.getString(AppTexts.TXT_DATABASE_COL_TITLE);
                String mainRole = resultSet.getString(AppTexts.TXT_DATABASE_COL_LEADROLE);
                String genre = resultSet.getString(AppTexts.TXT_DATABASE_COL_GENRE);
                int duration = resultSet.getInt(AppTexts.TXT_DATABASE_COL_DURATION);
                String country = resultSet.getString(AppTexts.TXT_DATABASE_COL_COUNTRY);

                movies.add(new Movie(id, title, mainRole, genre, duration, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Hier könnten Sie den Fehler loggen und/oder weitergeben, damit er von der aufrufenden Methode behandelt werden kann
        }

        return movies;
    }

    public void addMovieToDatabase(Movie movie) {
        String sql = AppTexts.TXT_ADD_MOVIE;

        try (Connection conn = this.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getLeadRole());
            pstmt.setString(3, movie.getGenre());
            pstmt.setInt(4, movie.getDuration());
            pstmt.setString(5, movie.getCountry());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            AppTexts.logger.log(Level.SEVERE, AppTexts.TXT_INPUT_INTO_DATABASE_ERROR, e);
        }
    }

    public void updateMovieInDatabase(Movie movie) {
        String sql = AppTexts.TXT_UPDATE_MOVIE;

        try (Connection conn = this.connectToDatabase();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Setzen Sie die Werte basierend auf dem Movie-Objekt
            pstmt.setString(1, movie.getTitle());
            pstmt.setString(2, movie.getLeadRole());
            pstmt.setString(3, movie.getGenre());
            pstmt.setInt(4, movie.getDuration());
            pstmt.setString(5, movie.getCountry());
            pstmt.setInt(6, movie.getId());

            // Update ausführen
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

//    public static boolean deleteMovieFromDatabase(int id) {
//        boolean deleted = false;
//        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
//             PreparedStatement preparedStatement = connection.prepareStatement(AppTexts.TXT_DELETE_MOVIE)) {
//            preparedStatement.setInt(1, id);
//            deleted = preparedStatement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return deleted;
//    }
    //endregion Methoden
}





