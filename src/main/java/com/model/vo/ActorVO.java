package com.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class ActorVO implements Serializable {

	private static final long serialVersionUID = -8896892312344756560L;

	private Integer actorId;

	private String firstName;

	private String lastName;

	private Timestamp lastUpdate;

	public ActorVO() {
	}

	public ActorVO(Integer actorId, String firstName, String lastName, Timestamp lastUpdate) {
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastUpdate = lastUpdate;
	}

	public Integer getActorId() {
		return actorId;
	}

	public void setActorId(Integer actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "ActorVO [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

}
