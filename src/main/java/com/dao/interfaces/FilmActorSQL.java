package com.dao.interfaces;

public interface FilmActorSQL {

	// premessa: attori e film dei due metodi (insertFilmWithCast e
	// insertActorWithFilmography) che usano i tre seguenti script SQL
	// NON devono gia' trovarsi sul DB
	String INSERT_FILM = "INSERT INTO film(title, language_id, original_language_id, rating) VALUES(?, ?, ?, ?);";
	String INSERT_ACTOR = "INSERT INTO actor(first_name, last_name) VALUES(?, ?);";
	String INSERT_FILM_ACTOR = "INSERT INTO film_actor(film_id, actor_id) VALUES(?, ?);";

	// readFilmWithCast
	String READ_FILM_WITH_CAST = "SELECT film.film_id, film.title, film.language_id, film.original_language_id, film.rating, film.last_update, actor.* FROM film JOIN film_actor ON film.film_id = film_actor.film_id JOIN actor ON actor.actor_id = film_actor.actor_id WHERE film.film_id = ?;";

	// readActorWithFilmography
	String READ_ACTOR_WITH_FILMOGRAPHY = "SELECT film.film_id, film.title, film.language_id, film.original_language_id, film.rating, film.last_update, actor.* FROM film JOIN film_actor ON film.film_id = film_actor.film_id JOIN actor ON actor.actor_id = film_actor.actor_id WHERE actor.actor_id = ?;";

	// deleteFilmActorRelation
	String DELETE_FILM_ACTOR_RELATION = "DELETE FROM film_actor WHERE film_id = ? AND actor_id = ?;";

}
