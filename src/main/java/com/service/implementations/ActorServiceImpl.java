package com.service.implementations;

import java.sql.SQLException;
import java.util.Set;

import com.dao.implementations.ActorDAOImpl;
import com.dao.interfaces.ActorDAO;
import com.model.dto.ActorDTO;
import com.model.vo.ActorVO;
import com.service.interfaces.ActorService;
import com.service.utils.MyCustomValidator;

public class ActorServiceImpl implements ActorService {

	private ActorDAO dao = new ActorDAOImpl();

	@Override
	public Integer insertActor(ActorDTO actorDTO) {
		if (MyCustomValidator.isInvalidActor(actorDTO)) {
			throw new IllegalArgumentException("Insert actor failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.insertActor(actorDTO);
			if (result == null || result != 1) {
				throw new SQLException("Insert actor failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer updateActor(Integer actorId, ActorDTO actorDTO) {
		if (MyCustomValidator.isInvalidId(actorId) || MyCustomValidator.isInvalidActor(actorDTO)) {
			throw new IllegalArgumentException("Update actor failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.updateActor(actorId, actorDTO);
			if (result == null || result != 1) {
				throw new SQLException("Update actor failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Integer deleteActor(Integer actorId) {
		if (MyCustomValidator.isInvalidId(actorId)) {
			throw new IllegalArgumentException("Delete actor failed: invalid input");
		}
		Integer result = null;
		try {
			result = dao.deleteActor(actorId);
			if (result == null || result != 1) {
				throw new SQLException("Delete actor failed: DML not correctly executed");
			}
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return result;
	}

	@Override
	public Set<ActorVO> readActors() {
		Set<ActorVO> result = null;
		try {
			result = dao.readActors();
			if (MyCustomValidator.isInvalidActorVOList(result)) {
				throw new SQLException();
			}
		} catch (SQLException sqle) {
			System.err.println("Read actors failed: query not correctly executed");
			sqle.printStackTrace();
		}
		return result;
	}

}
