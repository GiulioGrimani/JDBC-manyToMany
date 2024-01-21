package com.dao.interfaces;

import java.sql.SQLException;
import java.util.Set;

import com.model.dto.ActorDTO;
import com.model.vo.ActorVO;

public interface ActorDAO {

	Integer insertActor(ActorDTO actorDTO) throws SQLException;

	Integer updateActor(Integer actorId, ActorDTO actorDTO) throws SQLException;

	// Delete di un attore che non e' referenziato nella join table
	Integer deleteActor(Integer actorId) throws SQLException;

	Set<ActorVO> readActors() throws SQLException;

}
