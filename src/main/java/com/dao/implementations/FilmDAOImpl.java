package com.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.dao.interfaces.FilmDAO;
import com.dao.interfaces.FilmSQL;
import com.dao.utils.ConnectionManager;
import com.model.Rating;
import com.model.dto.FilmDTO;
import com.model.vo.FilmVO;

public class FilmDAOImpl implements FilmDAO, FilmSQL {

	@Override
	public Integer insertFilm(FilmDTO filmDTO) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, INSERT_FILM);

		ps.setString(1, filmDTO.getTitle());
		ps.setInt(2, filmDTO.getLanguageId());
		ps.setInt(3, filmDTO.getOriginalLanguageId());
		ps.setString(4, filmDTO.getRating().getLabel());

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	@Override
	public Integer updateFilm(Integer filmId, FilmDTO filmDTO) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, UPDATE_FILM);

		ps.setString(1, filmDTO.getTitle());
		ps.setString(2, filmDTO.getRating().getLabel());
		ps.setInt(3, filmId);

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	// Delete di un film che non e' referenziato nella join table
	@Override
	public Integer deleteFilm(Integer filmId) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, DELETE_FILM);

		ps.setInt(1, filmId);

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	@Override
	public Set<FilmVO> readFilms() throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		ResultSet rs = ConnectionManager.getResultSetOnSt(connection, READ_FILMS);

		Set<FilmVO> result = new LinkedHashSet<>();

		while (rs.next()) {
			FilmVO filmVO = mapToFilmVO(rs);
			result.add(filmVO);
		}

		ConnectionManager.closeConnection(connection);

		return result;
	}

	private FilmVO mapToFilmVO(ResultSet rs) throws SQLException {
		Integer filmId = rs.getInt("film_id");
		String title = rs.getString("title");
		Integer languageId = rs.getInt("language_id");
		Integer originalLanguageId = rs.getInt("original_language_id");
		Rating rating = Rating.getRatingByLabel(rs.getString("rating"));
		Timestamp lastUpdate = rs.getTimestamp("last_update");
		return new FilmVO(filmId, title, languageId, originalLanguageId, rating, lastUpdate);
	}

}
