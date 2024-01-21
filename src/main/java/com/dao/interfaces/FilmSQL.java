package com.dao.interfaces;

public interface FilmSQL {

	String INSERT_FILM = "INSERT INTO film(title, language_id, original_language_id, rating) VALUES(?, ?, ?, ?);";

	String UPDATE_FILM = "UPDATE film SET title = ?, rating = ? WHERE film_id = ?;";

	// Funziona solo se il film non e' referenziato nella join table
	String DELETE_FILM = "DELETE FROM film WHERE film_id = ?;";

	String READ_FILMS = "SELECT * FROM film;";

}
