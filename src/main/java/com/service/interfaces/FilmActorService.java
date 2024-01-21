package com.service.interfaces;

import java.util.Set;

import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.FilmCastVO;

public interface FilmActorService {

	// Sia il film che gli attori del cast NON devono gia' trovarsi sul DB
	Integer insertFilmWithCast(FilmDTO filmDTO, Set<ActorDTO> cast);

	// Sia l'attore che i film della filmografia NON devono gia' trovarsi sul DB
	Integer insertActorWithFilmography(ActorDTO actorDTO, Set<FilmDTO> filmography);

	Integer deleteFilmActorRelation(Integer filmId, Integer actorId);

	FilmCastVO readFilmWithCast(Integer filmId);

	ActorFilmographyVO readActorWithFilmography(Integer actorId);
}
