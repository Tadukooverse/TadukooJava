package com.github.tadukoo.java;

/**
 * Java Type represents a main Java type (class, interface, enum, record, annotation class)
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public interface JavaType{
	
	/**
	 * @return What {@link JavaClassType} this is
	 */
	JavaClassType getType();
}
