package com.service.interfaces;

import java.util.Set;

import com.model.dto.FilmDTO;
import com.model.vo.FilmVO;

public interface FilmService {

	Integer insertFilm(FilmDTO filmDTO);

	Integer updateFilm(Integer filmId, FilmDTO filmDTO);

	// Delete di un film che non e' referenziato nella join table
	Integer deleteFilm(Integer filmId);

	Set<FilmVO> readFilms();
}
