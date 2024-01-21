package com.dao.interfaces;

import java.sql.SQLException;
import java.util.Set;

import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.FilmCastVO;

public interface FilmActorDAO {

	// Sia il film che gli attori del cast NON devono gia' trovarsi sul DB
	Integer insertFilmWithCast(FilmDTO filmDTO, Set<ActorDTO> cast) throws SQLException;

	// Sia l'attore che i film della filmografia NON devono gia' trovarsi sul DB
	Integer insertActorWithFilmography(ActorDTO actorDTO, Set<FilmDTO> filmography) throws SQLException;

	Integer deleteFilmActorRelation(Integer filmId, Integer actorId) throws SQLException;

	FilmCastVO readFilmWithCast(Integer filmId) throws SQLException;

	ActorFilmographyVO readActorWithFilmography(Integer actorId) throws SQLException;

}
