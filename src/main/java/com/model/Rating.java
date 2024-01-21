package com.model;

import java.util.HashMap;
import java.util.Map;

public enum Rating {

	G("G"),
	PG("PG"),
	PG_13("PG-13"),
	R("R"),
	NC_17("NC-17");

	private String label;

	private static Map<String, Rating> BY_LABEL = new HashMap<>();

	static {
		for (Rating rating : Rating.values()) {
			BY_LABEL.put(rating.label, rating);
		}
	}

	private Rating(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	public static Rating getRatingByLabel(String label) {
		return BY_LABEL.get(label);
	}
}