package com.rokin.hero;

import java.io.Serializable;

import lombok.Data;

@Data
public class Hero implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public enum HeroType {
		AGILITY, INTELLIGENCE, STRENGTH
	}
	
	private String name;
	private HeroType type;
}
