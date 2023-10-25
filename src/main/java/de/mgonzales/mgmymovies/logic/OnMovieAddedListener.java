package de.mgonzales.mgmymovies.logic;

import de.mgonzales.mgmymovies.model.Movie;


/**
 * Das OnMovieAddedListener-Interface dient als Listener für Ereignisse, die auftreten,
 * wenn ein neuer Film hinzugefügt wird.
 * Klassen, die dieses Interface implementieren, müssen die onMovieAdded-Methode definieren,
 * um auf das Hinzufügen eines neuen Films zu reagieren.
 */
public interface OnMovieAddedListener {
    void onMovieAdded(Movie newMovie);
}
