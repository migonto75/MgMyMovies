package de.mgonzales.mgmymovies.logic.database;

import java.sql.Connection;
import java.util.List;

/**
 * Das Dao-Interface definiert eine standardisierte Methode für den Zugriff auf Datenbanken,
 * unabhängig von den Details der Datenbankoperationen.
 * Es bietet eine Abstraktionsschicht zwischen den Datenzugriffsoperationen und der Geschäftslogik der Anwendung.
 *
 * @param <T> Der Typ der Entität, auf die das DAO angewendet wird.
 */
public interface Dao<T> {
    void create(Connection dbConnection, T object);
    List<T> readAll(Connection dbConnection);
    T readById(Connection dbConnection, int id);
    void update(Connection dbConnection, T object);
    void delete(Connection dbConnection, T object);
}
