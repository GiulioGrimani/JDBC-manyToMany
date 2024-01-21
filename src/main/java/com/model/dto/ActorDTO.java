package com.model.dto;

import java.io.Serializable;

public class ActorDTO implements Serializable {

	private static final long serialVersionUID = 3876308550508618320L;

	private String firstName;

	private String lastName;

	public ActorDTO() {
	}

	public ActorDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
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

}
