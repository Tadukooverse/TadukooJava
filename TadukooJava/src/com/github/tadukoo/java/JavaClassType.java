package com.github.tadukoo.java;

/**
 * An enum for the various {@link JavaType} types of Java classes.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public enum JavaClassType{
	
	/**
	 * Represents a class in Java
	 * Main JavaType class: JavaClass
	 */
	CLASS(JavaClass.class);
	
	/** The main {@link JavaType} class for the enum */
	private final Class<? extends JavaType> clazz;
	
	/**
	 * Constructs a new {@link JavaClassType} with the given parameters
	 *
	 * @param clazz The main {@link JavaType} class for the enum
	 */
	JavaClassType(Class<? extends JavaType> clazz){
		this.clazz = clazz;
	}
	
	/**
	 * @return The main {@link JavaType} class for the enum
	 */
	public Class<? extends JavaType> getJavaTypeClass(){
		return clazz;
	}
}
