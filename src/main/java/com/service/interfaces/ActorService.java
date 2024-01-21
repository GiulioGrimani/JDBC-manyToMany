package com.service.interfaces;

import java.util.Set;

import com.model.dto.ActorDTO;
import com.model.vo.ActorVO;

public interface ActorService {

	Integer insertActor(ActorDTO actorDTO);

	Integer updateActor(Integer actorId, ActorDTO actorDTO);

	// Delete di un attore che non e' referenziato nella join table
	Integer deleteActor(Integer actorId);

	Set<ActorVO> readActors();
}
