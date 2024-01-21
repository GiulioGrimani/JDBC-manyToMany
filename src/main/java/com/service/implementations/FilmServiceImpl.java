package com.service.implementations;

import java.sql.SQLException;
import java.util.Set;

import com.dao.implementations.FilmDAOImpl;
import com.dao.interfaces.FilmDAO;
import com.model.dto.FilmDTO;
import com.model.vo.FilmVO;
import com.service.interfaces.FilmService;
import com.service.utils.MyCustomValidator;

public class FilmServiceImpl implements FilmService {

	private FilmDAO dao = new FilmDAOImpl();

	@Override
	public Integer insertFilm(FilmDTO filmDTO) {
		if (MyCustomValidator.isInvalidFilm(filmDTO)) {
			throw new IllegalArgumentException("Insert film failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.insertFilm(filmDTO);
			if (result == null || result != 1) {
				throw new SQLException("Insert film failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer updateFilm(Integer filmId, FilmDTO filmDTO) {
		if (MyCustomValidator.isInvalidId(filmId) || MyCustomValidator.isInvalidFilm(filmDTO)) {
			throw new IllegalArgumentException("Update film failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.updateFilm(filmId, filmDTO);
			if (result == null || result != 1) {
				throw new SQLException("Update film failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer deleteFilm(Integer filmId) {
		if (MyCustomValidator.isInvalidId(filmId)) {
			throw new IllegalArgumentException("Delete film failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.deleteFilm(filmId);
			if (result == null || result != 1) {
				throw new SQLException("Delete film failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Set<FilmVO> readFilms() {
		Set<FilmVO> result = null;
		try {
			result = dao.readFilms();
			if (MyCustomValidator.isInvalidFilmVOList(result)) {
				throw new SQLException();
			}
		} catch (SQLException sqle) {
			System.err.println("Read films failed: query not correctly executed");
			sqle.printStackTrace();
		}
		return result;
	}

}
