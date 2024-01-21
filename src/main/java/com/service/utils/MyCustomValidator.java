package com.service.utils;

import java.util.Set;

import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.ActorVO;
import com.model.vo.FilmCastVO;
import com.model.vo.FilmVO;

public class MyCustomValidator {

	/*
	 * Validazioni DTO
	 */

	public static boolean isInvalidId(Integer id) {
		return id == null || id < 1;
	}

	// Niente controlli su originalLanguageId poiche' sul DB puo' anche essere null
	public static boolean isInvalidFilm(FilmDTO filmDTO) {
		return filmDTO == null || filmDTO.getTitle() == null || filmDTO.getLanguageId() == null
				|| filmDTO.getRating() == null;
	}

	public static boolean isInvalidActor(ActorDTO actorDTO) {
		return actorDTO == null || actorDTO.getFirstName() == null || actorDTO.getLastName() == null;
	}

	public static boolean isInvalidCast(Set<ActorDTO> cast) {
		for (ActorDTO actorDTO : cast) {
			if (isInvalidActor(actorDTO)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInvalidFilmography(Set<FilmDTO> filmography) {
		for (FilmDTO filmDTO : filmography) {
			if (isInvalidFilm(filmDTO)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Validazioni VO
	 */

	public static boolean isInvalidActorVOList(Set<ActorVO> actors) {
		if (actors == null) {
			return true;
		}
		for (ActorVO actorVO : actors) {
			if (isInvalidActor(actorVO)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInvalidFilmVOList(Set<FilmVO> films) {
		if (films == null) {
			return true;
		}
		for (FilmVO filmVO : films) {
			if (isInvalidFilm(filmVO)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isInvalidFilmCastVO(FilmCastVO filmCastVO) {
		return (filmCastVO == null || isInvalidFilm(filmCastVO.getFilm())
				|| isInvalidActorVOList(filmCastVO.getCast()));
	}

	public static boolean isInvalidActorFilmographyVO(ActorFilmographyVO actorFilmographyVO) {
		return (actorFilmographyVO == null || isInvalidActor(actorFilmographyVO.getActor())
				|| isInvalidFilmVOList(actorFilmographyVO.getFilmography()));
	}

	// Niente controlli su originalLanguageId poiche' sul DB puo' anche essere null
	private static boolean isInvalidFilm(FilmVO filmVO) {
		return filmVO == null || filmVO.getFilmId() == null || filmVO.getFilmId() < 1 || filmVO.getTitle() == null
				|| filmVO.getLanguageId() == null || filmVO.getRating() == null || filmVO.getLastUpdate() == null;
	}

	private static boolean isInvalidActor(ActorVO actorVO) {
		return actorVO == null || actorVO.getActorId() == null || actorVO.getActorId() < 1
				|| actorVO.getFirstName() == null || actorVO.getLastName() == null || actorVO.getLastUpdate() == null;
	}

}
