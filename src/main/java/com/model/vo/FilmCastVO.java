package com.model.vo;

import java.io.Serializable;
import java.util.Set;

public class FilmCastVO implements Serializable {

	private static final long serialVersionUID = -1826279069431962641L;

	private FilmVO film;

	Set<ActorVO> cast;

	public FilmCastVO() {
	}

	public FilmCastVO(FilmVO film, Set<ActorVO> cast) {
		this.film = film;
		this.cast = cast;
	}

	public FilmVO getFilm() {
		return film;
	}

	public void setFilm(FilmVO film) {
		this.film = film;
	}

	public Set<ActorVO> getCast() {
		return cast;
	}

	public void setCast(Set<ActorVO> cast) {
		this.cast = cast;
	}

	@Override
	public String toString() {
		String result = "{FilmCastVO:" + "\n" + film.toString();
		for (ActorVO a : cast) {
			result += "\n" + a.toString();
		}
		return result + " }";
	}

}
