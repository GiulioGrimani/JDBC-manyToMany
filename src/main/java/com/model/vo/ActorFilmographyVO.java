package com.model.vo;

import java.io.Serializable;
import java.util.Set;

public class ActorFilmographyVO implements Serializable {

	private static final long serialVersionUID = 6492231304860799206L;

	private ActorVO actor;

	private Set<FilmVO> filmography;

	public ActorFilmographyVO() {
	}

	public ActorFilmographyVO(ActorVO actor, Set<FilmVO> filmography) {
		this.actor = actor;
		this.filmography = filmography;
	}

	public ActorVO getActor() {
		return actor;
	}

	public void setActor(ActorVO actor) {
		this.actor = actor;
	}

	public Set<FilmVO> getFilmography() {
		return filmography;
	}

	public void setFilmography(Set<FilmVO> filmography) {
		this.filmography = filmography;
	}

	@Override
	public String toString() {
		String result = "{ActorFilmographyVO" + "\n" + actor.toString();
		for (FilmVO f : filmography) {
			result += "\n" + f.toString();
		}
		return result + " }";
	}

}
