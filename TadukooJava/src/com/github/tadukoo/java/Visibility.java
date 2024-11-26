package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

/**
 * Visibility represents the visibility of a given Java class, method, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2
 */
public enum Visibility implements JavaTokens{
	/** Used for public visibility - anything can see it */
	PUBLIC(PUBLIC_MODIFIER),
	/** Used for protected visibility - only the current class and subclasses can see it */
	PROTECTED(PROTECTED_MODIFIER),
	/** Used for private visibility - only the current class can see it */
	PRIVATE(PRIVATE_MODIFIER),
	/** Used when there's no specified visibility (e.g. interface methods, where public is implied) */
	NONE("");
	
	/** The text to use for the visibility */
	private final String token;
	
	/**
	 * Creates a new Visibility enum with the given text
	 *
	 * @param token The text to use for the visibility
	 */
	Visibility(String token){
		this.token = token;
	}
	
	/**
	 * @return The text to use for the visibility
	 */
	public String getToken(){
		return token;
	}
	
	/**
	 * Grabs the {@link Visibility} that corresponds to the given token
	 *
	 * @param token The token used for the visibility (to use in searching)
	 * @return The found {@link Visibility}, or null
	 */
	public static Visibility fromToken(String token){
		if(StringUtil.isBlank(token)){
			return Visibility.NONE;
		}
		for(Visibility visibility: values()){
			if(StringUtil.equalsIgnoreCase(visibility.getToken(), token)){
				return visibility;
			}
		}
		return null;
	}
}
