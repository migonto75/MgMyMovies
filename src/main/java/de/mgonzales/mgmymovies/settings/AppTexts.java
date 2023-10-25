package de.mgonzales.mgmymovies.settings;

import de.mgonzales.mgmymovies.logic.database.DaoMovie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Diese Klasse enthält Konstanten und Hilfsmethoden, die in der gesamten Anwendung verwendet werden.
 */

public class AppTexts {

    //region Konstanten
    // Standardwerte für verschiedene Datentypen
    public static final String TXT_DEFAULT_STRING_VALUE = "No Value set";
    public static final int TXT_DEFAULT_INT_VALUE = -1;

    // Fehlermeldungen und Informationen
    public static final String TXT_MARIA_DB_DRIVER_NOT_FOUND = "MariaDB JDBC-Treiber nicht gefunden: ";
    public static final String TXT_COL_TITLE = "title";
    public static final String TXT_COL_LEADROLE = "leadRole";
    public static final String TXT_COL_GENRE = "genre";
    public static final String TXT_COL_DURATION = "duration";
    public static final String TXT_COL_COUNTRY = "country";
    public static final String ALERT_INFO = "Info";
    public static final String ALERT_NO_MOVIES_FOUND_IN_DB = "Es wurden keine Filme in der Datenbank gefunden!";
    public static final String ALERT_NO_CHOICE = "Keine Auswahl";
    public static final String ALERT_SELECT_MOVIE_FROM_TABLE = "Bitte wählen Sie einen Film in der Tabelle aus.";
    public static final String TXT_STAGE_HEAD_EDIT_MOVIE = "Film bearbeiten...";
    public static final String TXT_STAGE_HEAD_ADD_MOVIE = "Film anlegen...";
    public static final String TXT_ALERT_DELETE_MOVIE = "Film löschen";
    public static final String TXT_ALERT_CONFIRM_DELETION = "Löschbestätigung";
    public static final String TXT_ALERT_CONFIRM_TO_DELETE_MOVIE = "Sind Sie sicher, dass Sie diesen Film löschen möchten?";
    public static final String TXT_INPUT_INTO_DATABASE_ERROR = "Fehler beim Einpflegen des Films in die Datenbank";
    public static final String TXT_DRIVER_NAME = "org.mariadb.jdbc.Driver";

    // Datenbankspaltennamen
    public static final String TXT_DATABASE_COL_ID = "ID";
    public static final String TXT_DATABASE_COL_TITLE = "Titel";
    public static final String TXT_DATABASE_COL_LEADROLE = "Hauptrolle";
    public static final String TXT_DATABASE_COL_GENRE = "Genre";
    public static final String TXT_DATABASE_COL_DURATION = "Dauer";
    public static final String TXT_DATABASE_COL_COUNTRY = "Land";

    // Fehlermeldungen und Benutzerhinweise
    public static final String TXT_INVALID_INPUT = "Die Eingabe ist nicht korrekt. Bitte überprüfen Sie Ihre Eingaben.";
    public static final String TXT_APP_NAME = "My Movies";

    // Datenbankinformationen
    private static final String TXT_URL = "jdbc:mariadb://localhost:3306/mymovies";
    private static final String TXT_USERNAME = "root";
    private static final String TXT_PASSWORD = "";

    // SQL-Abfragen
    public static final String TXT_ADD_MOVIE = "INSERT INTO movies (Titel, Hauptrolle, Genre, Dauer, Land) VALUES (?, ?, ?, ?, ?)";
    public static final String TXT_UPDATE_MOVIE = "UPDATE movies SET Titel = ?, Hauptrolle = ?, Genre = ?, Dauer = ?, Land = ? WHERE id = ?";
    public static final String TXT_DELETE_MOVIE = "DELETE FROM movies WHERE id = ?";
    public static final String TXT_SELECT_ALL_MOVIES = "SELECT * FROM movies";

    // Logger-Instanz für die Protokollierung
    public static final Logger logger = Logger.getLogger(DaoMovie.class.getName());

    // Fehlermeldung für Datenbankverbindungsfehler
    public static final String TXT_CONNECTION_TO_DATABASE_FAILED = "Verbindung zur Datenbank konnte nicht hergestellt werden: ";
    //endregion Konstanten

    //region Methoden
    //endregion Methoden

    /**
     * DatabaseManager ist verantwortlich für die Verwaltung der Datenbankverbindung.
     * Es verwendet das Singleton-Designmuster, um sicherzustellen, dass nur eine Instanz der Klasse existiert.
     */
    public static class DatabaseManager {
        private static DatabaseManager instance;
        private Connection connection;


        private DatabaseManager() {
            try {
                // Laden des MariaDB-Treibers
                Class.forName(AppTexts.TXT_URL);
                // Verbindung zur Datenbank herstellen
                this.connection = DriverManager.getConnection(TXT_URL, TXT_USERNAME, TXT_PASSWORD);
            } catch (ClassNotFoundException e) {
                System.out.println(TXT_MARIA_DB_DRIVER_NOT_FOUND + e.getMessage());
                // Hier können Sie die Anwendung beenden oder eine Benutzerbenachrichtigung anzeigen
            } catch (SQLException e) {
                System.out.println(TXT_CONNECTION_TO_DATABASE_FAILED + e.getMessage());
                // Hier können Sie die Anwendung beenden oder eine Benutzerbenachrichtigung anzeigen
            }
        }


        public static DatabaseManager getInstance() {
            if (instance == null) {
                instance = new DatabaseManager();
            }
            return instance;
        }

        public Connection getConnection() {
            return this.connection;
        }
    }
}
