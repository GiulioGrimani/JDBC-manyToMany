package com.dao.interfaces;

import java.sql.SQLException;
import java.util.Set;

import com.model.dto.FilmDTO;
import com.model.vo.FilmVO;

public interface FilmDAO {

	Integer insertFilm(FilmDTO filmDTO) throws SQLException;

	Integer updateFilm(Integer filmId, FilmDTO filmDTO) throws SQLException;

	// Delete di un film che non e' referenziato nella join table
	Integer deleteFilm(Integer filmId) throws SQLException;

	Set<FilmVO> readFilms() throws SQLException;

}
