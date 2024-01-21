package com.service.implementations;

import java.sql.SQLException;
import java.util.Set;

import com.dao.implementations.FilmActorDAOImpl;
import com.dao.interfaces.FilmActorDAO;
import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.FilmCastVO;
import com.service.interfaces.FilmActorService;
import com.service.utils.MyCustomValidator;

public class FilmActorServiceImpl implements FilmActorService {

	private FilmActorDAO dao = new FilmActorDAOImpl();

	@Override
	public Integer insertFilmWithCast(FilmDTO filmDTO, Set<ActorDTO> cast) {
		if (MyCustomValidator.isInvalidFilm(filmDTO) || MyCustomValidator.isInvalidCast(cast)) {
			throw new IllegalArgumentException("Insert film with cast failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.insertFilmWithCast(filmDTO, cast);
			if (result == null || result != 1 + cast.size() * 2) {
				throw new SQLException("Insert film with cast failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer insertActorWithFilmography(ActorDTO actorDTO, Set<FilmDTO> filmography) {
		if (MyCustomValidator.isInvalidActor(actorDTO) || MyCustomValidator.isInvalidFilmography(filmography)) {
			throw new IllegalArgumentException("Insert actor with filmography failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.insertActorWithFilmography(actorDTO, filmography);
			if (result == null || result != 1 + filmography.size() * 2) {
				throw new SQLException("Insert actor with filmography failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer deleteFilmActorRelation(Integer filmId, Integer actorId) {
		if (MyCustomValidator.isInvalidId(filmId) || MyCustomValidator.isInvalidId(actorId)) {
			throw new IllegalArgumentException("Delete film-actor relation failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.deleteFilmActorRelation(filmId, actorId);
			if (result == null || result != 1) {
				throw new SQLException("Delete film-actor relation failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public FilmCastVO readFilmWithCast(Integer filmId) {
		if (MyCustomValidator.isInvalidId(filmId)) {
			throw new IllegalArgumentException("Read film with cast failed: invalid input");
		}
		FilmCastVO result = null;
		try {
			result = dao.readFilmWithCast(filmId);
			if (MyCustomValidator.isInvalidFilmCastVO(result)) {
				throw new SQLException();
			}
		} catch (SQLException sqle) {
			System.err.println("Read film with cast failed: query not correctly executed");
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public ActorFilmographyVO readActorWithFilmography(Integer actorId) {
		if (MyCustomValidator.isInvalidId(actorId)) {
			throw new IllegalArgumentException("Read actor with filmography failed: invalid input");
		}
		ActorFilmographyVO result = null;
		try {
			result = dao.readActorWithFilmography(actorId);
			if (MyCustomValidator.isInvalidActorFilmographyVO(result)) {
				throw new SQLException();
			}
		} catch (SQLException sqle) {
			System.err.println("Read actor with filmography failed: query not correctly executed");
			sqle.printStackTrace();
		}
		return result;
	}

}
