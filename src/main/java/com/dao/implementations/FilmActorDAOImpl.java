package com.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.dao.interfaces.FilmActorDAO;
import com.dao.interfaces.FilmActorSQL;
import com.dao.utils.ConnectionManager;
import com.model.Rating;
import com.model.dto.ActorDTO;
import com.model.dto.FilmDTO;
import com.model.vo.ActorFilmographyVO;
import com.model.vo.ActorVO;
import com.model.vo.FilmCastVO;
import com.model.vo.FilmVO;

public class FilmActorDAOImpl implements FilmActorDAO, FilmActorSQL {

	// Per questo metodo e' necessaria la gestione manuale della transazione.
	// Sia il film che gli attori del cast NON devono gia' trovarsi sul DB
	@Override
	public Integer insertFilmWithCast(FilmDTO filmDTO, Set<ActorDTO> cast) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		Integer result = 0;

		try {
			ConnectionManager.openTransaction(connection);
			ConnectionManager.setBestTransactionIsolationLevel(connection);

			Integer filmId = null;

			PreparedStatement insertFilmPs = ConnectionManager.getPreparedStatementWithKeys(connection, INSERT_FILM);
			insertFilmPs.setString(1, filmDTO.getTitle());
			insertFilmPs.setInt(2, filmDTO.getLanguageId());
			insertFilmPs.setInt(3, filmDTO.getOriginalLanguageId());
			insertFilmPs.setString(4, filmDTO.getRating().getLabel());

			// Insert del film
			result += ConnectionManager.executeSqlOnPs(insertFilmPs);

			filmId = getIdFromKeys(insertFilmPs);

			for (ActorDTO actor : cast) {
				PreparedStatement insertActorPs = ConnectionManager.getPreparedStatementWithKeys(connection,
						INSERT_ACTOR);
				insertActorPs.setString(1, actor.getFirstName());
				insertActorPs.setString(2, actor.getLastName());

				// Insert dell'n-esimo attore
				result += ConnectionManager.executeSqlOnPs(insertActorPs);

				Integer actorId = getIdFromKeys(insertActorPs);
				PreparedStatement insertFilmActorPs = ConnectionManager.getPreparedStatement(connection,
						INSERT_FILM_ACTOR);
				insertFilmActorPs.setInt(1, filmId);
				insertFilmActorPs.setInt(2, actorId);

				// Insert della relazione nella join table
				result += ConnectionManager.executeSqlOnPs(insertFilmActorPs);
			}

			try {
				ConnectionManager.doCommit(connection);
			} catch (SQLException e) {
				System.err.println("Transazione fallita, avvio rollback");
				result = 0;
				ConnectionManager.doRollback(connection);
			}

		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return result;
	}

	// Per questo metodo e' necessaria la gestione manuale della transazione.
	// Sia l'attore che i film della filmografia NON devono gia' trovarsi sul DB
	@Override
	public Integer insertActorWithFilmography(ActorDTO actorDTO, Set<FilmDTO> filmography) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		Integer result = 0;

		try {
			ConnectionManager.openTransaction(connection);
			ConnectionManager.setBestTransactionIsolationLevel(connection);

			Integer actorId = null;

			PreparedStatement insertActorPs = ConnectionManager.getPreparedStatementWithKeys(connection, INSERT_ACTOR);
			insertActorPs.setString(1, actorDTO.getFirstName());
			insertActorPs.setString(2, actorDTO.getLastName());

			result += ConnectionManager.executeSqlOnPs(insertActorPs);

			actorId = getIdFromKeys(insertActorPs);

			for (FilmDTO film : filmography) {
				PreparedStatement insertFilmPs = ConnectionManager.getPreparedStatementWithKeys(connection,
						INSERT_FILM);
				insertFilmPs.setString(1, film.getTitle());
				insertFilmPs.setInt(2, film.getLanguageId());
				insertFilmPs.setInt(3, film.getOriginalLanguageId());
				insertFilmPs.setString(4, film.getRating().getLabel());
				result += ConnectionManager.executeSqlOnPs(insertFilmPs);

				Integer filmId = getIdFromKeys(insertFilmPs);

				PreparedStatement insertFilmActorPs = ConnectionManager.getPreparedStatement(connection,
						INSERT_FILM_ACTOR);
				insertFilmActorPs.setInt(1, filmId);
				insertFilmActorPs.setInt(2, actorId);
				result += ConnectionManager.executeSqlOnPs(insertFilmActorPs);
			}

			try {
				ConnectionManager.doCommit(connection);
			} catch (SQLException e) {
				System.err.println("Transazione fallita, avvio rollback");
				result = 0;
				ConnectionManager.doRollback(connection);
			}

		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}
		return result;
	}

	@Override
	public Integer deleteFilmActorRelation(Integer filmId, Integer actorId) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		Integer result = 0;

		try {
			PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, DELETE_FILM_ACTOR_RELATION);
			ps.setInt(1, filmId);
			ps.setInt(2, actorId);

			result = ConnectionManager.executeSqlOnPs(ps);
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return result;
	}

	@Override
	public FilmCastVO readFilmWithCast(Integer filmId) throws SQLException {
		Connection connection = ConnectionManager.getConnection();
		FilmVO film = null;
		Set<ActorVO> actors = new LinkedHashSet<>();

		try {
			PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, READ_FILM_WITH_CAST);
			ps.setInt(1, filmId);
			ResultSet rs = ConnectionManager.getResultSetOnPS(ps);
			while (rs.next()) {
				if (film == null) {
					film = mapToFilmVO(rs);
				}
				ActorVO actor = mapToActorVO(rs);
				actors.add(actor);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return new FilmCastVO(film, actors);
	}

	@Override
	public ActorFilmographyVO readActorWithFilmography(Integer actorId) throws SQLException {
		Connection connection = ConnectionManager.getConnection();
		ActorVO actor = null;
		Set<FilmVO> films = new LinkedHashSet<>();

		try {
			PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, READ_ACTOR_WITH_FILMOGRAPHY);
			ps.setInt(1, actorId);
			ResultSet rs = ConnectionManager.getResultSetOnPS(ps);
			while (rs.next()) {
				if (actor == null) {
					actor = mapToActorVO(rs);
				}
				FilmVO film = mapToFilmVO(rs);
				films.add(film);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeConnection(connection);
		}

		return new ActorFilmographyVO(actor, films);
	}

	private Integer getIdFromKeys(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	private FilmVO mapToFilmVO(ResultSet rs) throws SQLException {
		Integer filmId = rs.getInt(1);
		String title = rs.getString(2);
		Integer languageId = rs.getInt(3);
		Integer originalLanguageId = rs.getInt(4);
		Rating rating = Rating.getRatingByLabel(rs.getString(5));
		Timestamp lastUpdate = rs.getTimestamp(6);
		return new FilmVO(filmId, title, languageId, originalLanguageId, rating, lastUpdate);
	}

	private ActorVO mapToActorVO(ResultSet rs) throws SQLException {
		Integer actorId = rs.getInt(7);
		String firstName = rs.getString(8);
		String lastName = rs.getString(9);
		Timestamp lastUpdate = rs.getTimestamp(10);
		return new ActorVO(actorId, firstName, lastName, lastUpdate);

	}

}
