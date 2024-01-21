package com.dao.interfaces;

public interface ActorSQL {

	String INSERT_ACTOR = "INSERT INTO actor(first_name, last_name) VALUES(?, ?);";

	String UPDATE_ACTOR = "UPDATE actor SET first_name = ?, last_name = ? WHERE actor_id = ?;";

	// Funziona solo se l'attore non e' referenziato nella join table
	String DELETE_ACTOR = "DELETE FROM actor WHERE actor_id = ?;";

	String READ_ACTORS = "SELECT * FROM actor;";
}
