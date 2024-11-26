package com.github.tadukoo.java;

/**
 * Utilities for dealing with Java code
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaCodeUtil{
	
	/** Not allowed to instantiate {@link JavaCodeUtil} */
	private JavaCodeUtil(){ }
	
	/**
	 * Converts the given Object to a String, including proper
	 * null handling. This will convert it to a String to be used in Java code,
	 * rather than mainly relying on the toString method.
	 *
	 * @param obj The Object to convert to a String
	 * @return null if obj is null, or a String representing the Object
	 */
	public static String convertToJavaString(Object obj){
		// If obj is null, just return null
		if(obj == null){
			return null;
		}
		
		// If obj is a String, cast it to a String
		if(obj instanceof String s){
			return s;
		}
		
		// If obj is an Enum, handle it special
		if(obj instanceof Enum<?> e){
			return e.getClass().getSimpleName() + "." + e.name();
		}
		
		// Otherwise just use String.valueOf
		return String.valueOf(obj);
	}
	
	/**
	 * Escapes characters in the string for use in e.g. builderCode so that you have \n instead of a newline
	 *
	 * @param theString The string to have characters escaped
	 * @return The given String, but with the characters escaped
	 */
	public static String escapeString(String theString){
		return theString.replaceAll("\n", "\\\\n")
				.replaceAll("\r", "\\\\r")
				.replaceAll("\t", "\\\\t");
	}
}
