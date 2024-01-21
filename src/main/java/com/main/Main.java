package com.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.dao.utils.ConnectionManager;
import com.model.Rating;
import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.ActorVO;
import com.model.vo.FilmCastVO;
import com.model.vo.FilmVO;
import com.service.implementations.ActorServiceImpl;
import com.service.implementations.FilmActorServiceImpl;
import com.service.implementations.FilmServiceImpl;
import com.service.interfaces.ActorService;
import com.service.interfaces.FilmActorService;
import com.service.interfaces.FilmService;

/*
 * Implementazione delle CRUD sulle tabelle actor e film (n : n).
 * Viene ovviamente coinvolta anche la join table film_actor.
 */

public class Main {

	public static void main(String[] args) {

		ActorService actorService = new ActorServiceImpl();
		FilmService filmService = new FilmServiceImpl();
		FilmActorService filmActorService = new FilmActorServiceImpl();

		Integer result = null;
		boolean ok = false;

		RESET_STATE();

		/*
		 * CRUD su actor
		 */

		// Insert actor
		ActorDTO actorToInsert = new ActorDTO("Edward", "Norton");
		result = actorService.insertActor(actorToInsert); // id = 201
		ok = result == 1;
		System.out.println("insertActor OK? " + ok);

		// Update actor
		ActorDTO actorToUpdate = new ActorDTO("Brad", "Pitt");
		result = actorService.updateActor(201, actorToUpdate);
		ok = result == 1;
		System.out.println("updateActor OK? " + ok);

		// Delete actor
		result = actorService.deleteActor(201);
		ok = result == 1;
		System.out.println("deleteActor OK? " + ok);

		// Read actors
		Set<ActorVO> actors = actorService.readActors();
		ok = !actors.isEmpty() && actors.size() == 200;
		System.out.println("readActors OK? " + ok);
		actors.forEach(System.err::println);

		/*
		 * CRUD su film
		 */

		// Insert film
		FilmDTO filmToInsert = new FilmDTO("Fight Club", 1, 1, Rating.NC_17);
		result = filmService.insertFilm(filmToInsert); // id = 1001
		ok = result == 1;
		System.out.println("insertFilm OK? " + ok);

		// Update film
		FilmDTO filmToUpdate = new FilmDTO("Seven", 1, 1, Rating.PG_13);
		result = filmService.updateFilm(1001, filmToUpdate);
		ok = result == 1;
		System.out.println("updateFilm OK? " + ok);

		// Delete film
		result = filmService.deleteFilm(1001);
		ok = result == 1;
		System.out.println("deleteFilm OK? " + ok);

		// Read films
		Set<FilmVO> films = filmService.readFilms();
		ok = !films.isEmpty() && films.size() == 1000;
		System.out.println("readFilms OK? " + ok);
		films.forEach(System.err::println);

		/*
		 * DML sulle common
		 */
		RESET_STATE();

		// Insert film with cast
		Set<ActorDTO> cast = new LinkedHashSet<>();
		cast.add(new ActorDTO("Edward", "Norton")); // id = 201
		cast.add(new ActorDTO("Brad", "Pitt")); // id = 202
		cast.add(new ActorDTO("Helena", "Bonham Carter")); // id = 203
		result = filmActorService.insertFilmWithCast(filmToInsert, cast); // filmId = 1001
		ok = result == 1 + 2 * cast.size();
		System.out.println("insertFilmWithCast OK? " + ok);

		// Insert actor with filmography
		ActorDTO harrisonFord = new ActorDTO("Harrison", "Ford"); // id = 204
		Set<FilmDTO> filmography = new LinkedHashSet<>();
		filmography.add(new FilmDTO("Indiana Jones", 1, 1, Rating.G)); // id = 1002
		filmography.add(new FilmDTO("Star Wars", 1, 1, Rating.G)); // id = 1003
		filmography.add(new FilmDTO("Blade Runner", 1, 1, Rating.G)); // id = 1004
		filmography.add(new FilmDTO("Harry Potter", 1, 1, Rating.G)); // id = 1005
		result = filmActorService.insertActorWithFilmography(harrisonFord, filmography);
		ok = result == 1 + 2 * filmography.size();
		System.out.println("insertActorWithFilmography OK? " + ok);

		// Delete film-actor relation
		result = filmActorService.deleteFilmActorRelation(1005, 204);
		ok = result == 1;
		System.out.println("deleteFilmActorRelation OK? " + ok);

		// Read film with cast
		FilmCastVO filmCastVO = filmActorService.readFilmWithCast(1001);
		ok = "Fight Club".equals(filmCastVO.getFilm().getTitle()) && filmCastVO.getCast().size() == 3;
		System.out.println("readFilmWithCast OK? " + ok);
		System.out.println(filmCastVO);

		// Read actor with filmography
		ActorFilmographyVO actorFilmographyVO = filmActorService.readActorWithFilmography(204);
		ok = "Harrison Ford".equals(
				actorFilmographyVO.getActor().getFirstName() + " " + actorFilmographyVO.getActor().getLastName())
				&& actorFilmographyVO.getFilmography().size() == 3;
		System.out.println("readActorWithFilmography OK? " + ok);
		System.out.println(actorFilmographyVO);

//		RESET_STATE();
	}

	public static void RESET_STATE() {
		String deleteFilmActor = "DELETE FROM film_actor WHERE actor_id > 200 || film_id > 1000";
		String deleteActors = "DELETE FROM actor WHERE actor_id > 200";
		String deleteFilms = "DELETE FROM film WHERE film_id > 1000";
		String resetActorIdCounter = "ALTER TABLE `sakila`.`actor` AUTO_INCREMENT = 201;";
		String resetFilmIdCounter = "ALTER TABLE `sakila`.`film` AUTO_INCREMENT = 1001;";

		Connection connection = ConnectionManager.getConnection();

		try {
			ConnectionManager.executeSql(connection, deleteFilmActor);
			ConnectionManager.executeSql(connection, deleteActors);
			ConnectionManager.executeSql(connection, deleteFilms);
			ConnectionManager.executeSql(connection, resetActorIdCounter);
			ConnectionManager.executeSql(connection, resetFilmIdCounter);
		} catch (SQLException e) {
			System.err.println("Qualcosa e' andato storto nel RESET_STATE...");
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}
	}
}
