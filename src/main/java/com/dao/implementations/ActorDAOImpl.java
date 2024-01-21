package com.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.dao.interfaces.ActorDAO;
import com.dao.interfaces.ActorSQL;
import com.dao.utils.ConnectionManager;
import com.model.dto.ActorDTO;
import com.model.vo.ActorVO;

/*
 * Dal momento che ogni metodo di questa classe esegue una singola DML, tale DML
 * viene gia' eseguita all'interno di un contesto transazionale, pertanto non
 * c'e' bisogno di gestire manualmente la transazione. Dalla documentazione:
 * "When a connection is created, it is in auto-commit mode"
 */
public class ActorDAOImpl implements ActorDAO, ActorSQL {

	@Override
	public Integer insertActor(ActorDTO actorDTO) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, INSERT_ACTOR);

		ps.setString(1, actorDTO.getFirstName());
		ps.setString(2, actorDTO.getLastName());

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	@Override
	public Integer updateActor(Integer actorId, ActorDTO actorDTO) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, UPDATE_ACTOR);

		ps.setString(1, actorDTO.getFirstName());
		ps.setString(2, actorDTO.getLastName());
		ps.setInt(3, actorId);

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	// Delete di un attore che non e' referenziato nella join table
	@Override
	public Integer deleteActor(Integer actorId) throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement ps = ConnectionManager.getPreparedStatement(connection, DELETE_ACTOR);

		ps.setInt(1, actorId);

		Integer result = ConnectionManager.executeSqlOnPs(ps);

		ConnectionManager.closeConnection(connection);

		return result;
	}

	@Override
	public Set<ActorVO> readActors() throws SQLException {
		Connection connection = ConnectionManager.getConnection();

		ResultSet rs = ConnectionManager.getResultSetOnSt(connection, READ_ACTORS);

		Set<ActorVO> result = new LinkedHashSet<>();

		while (rs.next()) {
			ActorVO actorVO = mapToActorVO(rs);
			result.add(actorVO);
		}

		ConnectionManager.closeConnection(connection);

		return result;
	}

	private ActorVO mapToActorVO(ResultSet rs) throws SQLException {
		Integer actorId = rs.getInt("actor_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		Timestamp lastUpdate = rs.getTimestamp("last_update");
		return new ActorVO(actorId, firstName, lastName, lastUpdate);
	}
}
