package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaTypes;

/**
 * An exception encountered while parsing Java code
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaParsingException extends Exception{
	
	/**
	 * Makes a new {@link JavaParsingException} with no underlying other cause
	 *
	 * @param type The {@link JavaTypes Java type} encountered during parsing
	 * @param message The error message
	 */
	public JavaParsingException(JavaTypes type, String message){
		super("Failed parsing JavaType: '" + type + "': " + message);
	}
}
