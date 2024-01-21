package com.model.dto;

import java.io.Serializable;

import com.model.Rating;

public class FilmDTO implements Serializable {

	private static final long serialVersionUID = 3180540225596086069L;

	private String title;

	private Integer languageId;

	private Integer originalLanguageId;

	private Rating rating;

	public FilmDTO() {
	}

	public FilmDTO(String title, Integer languageId, Integer originalLanguageId, Rating rating) {
		this.title = title;
		this.languageId = languageId;
		this.originalLanguageId = originalLanguageId;
		this.rating = rating;
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

	@Override
	public String toString() {
		return "FilmDTO [title=" + title + ", languageId=" + languageId + ", originalLanguageId=" + originalLanguageId
				+ ", rating=" + rating + "]";
	}

}
