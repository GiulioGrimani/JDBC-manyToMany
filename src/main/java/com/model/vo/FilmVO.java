package com.model.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.model.Rating;

public class FilmVO implements Serializable {

	private static final long serialVersionUID = 3180540225596086069L;

	private Integer filmId;

	private String title;

	private Integer languageId;

	private Integer originalLanguageId;

	private Rating rating;

	private Timestamp lastUpdate;

	public FilmVO() {
	}

	public FilmVO(Integer filmId, String title, Integer languageId, Integer originalLanguageId, Rating rating,
			Timestamp lastUpdate) {
		this.filmId = filmId;
		this.title = title;
		this.languageId = languageId;
		this.originalLanguageId = originalLanguageId;
		this.rating = rating;
		this.lastUpdate = lastUpdate;
	}

	public Integer getFilmId() {
		return filmId;
	}

	public void setFilmId(Integer filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public Integer getOriginalLanguageId() {
		return originalLanguageId;
	}

	public void setOriginalLanguageId(Integer originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "FilmVO [filmId=" + filmId + ", title=" + title + ", rating=" + rating + ", lastUpdate=" + lastUpdate
				+ "]";
	}

}
