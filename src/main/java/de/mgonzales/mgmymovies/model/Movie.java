package de.mgonzales.mgmymovies.model;

import de.mgonzales.mgmymovies.settings.AppTexts;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;

/**
 * Die Movie-Klasse repräsentiert einen Film in der Anwendung.
 */

public class Movie implements Serializable {

    //region Attribute
    private IntegerProperty id;
    private final StringProperty title;
    private final StringProperty leadRole;
    private final StringProperty genre;
    private final IntegerProperty duration;
    private final StringProperty country;

    //endregion Attribute


    //region Konstruktoren
    public Movie() {
        this.id = new SimpleIntegerProperty(AppTexts.TXT_DEFAULT_INT_VALUE);
        this.title = new SimpleStringProperty(AppTexts.TXT_DEFAULT_STRING_VALUE);
        this.leadRole = new SimpleStringProperty(AppTexts.TXT_DEFAULT_STRING_VALUE);
        this.genre = new SimpleStringProperty(AppTexts.TXT_DEFAULT_STRING_VALUE);
        this.duration = new SimpleIntegerProperty(AppTexts.TXT_DEFAULT_INT_VALUE);
        this.country = new SimpleStringProperty(AppTexts.TXT_DEFAULT_STRING_VALUE);
    }

    // Konstruktor mit Parametern
    public Movie(String title, String leadRole, String genre, int duration, String country) {
        this.title = new SimpleStringProperty(title);
        this.leadRole = new SimpleStringProperty(leadRole);
        this.genre = new SimpleStringProperty(genre);
        this.duration = new SimpleIntegerProperty(duration);
        this.country = new SimpleStringProperty(country);
    }

    public Movie(int id, String title, String leadRole, String genre, int duration, String country) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.leadRole = new SimpleStringProperty(leadRole);
        this.genre = new SimpleStringProperty(genre);
        this.duration = new SimpleIntegerProperty(duration);
        this.country = new SimpleStringProperty(country);
    }
    //endregion Konstruktoren


    //region Methoden
    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getLeadRole() {
        return leadRole.get();
    }

    public StringProperty leadRoleProperty() {
        return leadRole;
    }

    public void setLeadRole(String leadRole) {
        this.leadRole.set(leadRole);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    //endregion Methoden

    /**
     * Gibt eine Zeichenkettendarstellung des Movie-Objekts zurück.
     *
     * @return Eine Zeichenkette, die den Zustand des Movie-Objekts darstellt.
     */
    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", leadRole='" + leadRole + '\'' +
                ", genre='" + genre + '\'' +
                ", length=" + duration +
                ", country='" + country + '\'' +
                '}';
    }
}
