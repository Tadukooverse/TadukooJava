package com.github.tadukoo.java;

/**
 * Represents a generic type in Java
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public interface JavaCodeType extends JavaTokens{
	
	/** Newline followed by 2 tabs, for use in {@link #toBuilderCode()} implementations */
	String NEWLINE_WITH_2_TABS = "\n\t\t";
	/** Newline followed by 4 tabs, for use in {@link #toBuilderCode()} implementations */
	String NEWLINE_WITH_4_TABS = "\n\t\t\t\t";
	
	/**
	 * Takes the given String and escapes any quotes in them
	 *
	 * @param str The string to escape quotes in
	 * @return A version of the given string with quotes escaped
	 */
	default String escapeQuotes(String str){
		return str.replace("\"", "\\\"");
	}
	
	/**
	 * @return What {@link JavaCodeTypes type} this is
	 */
	JavaCodeTypes getJavaCodeType();
	
	/**
	 * @return The String of code to build the {@link JavaCodeType}
	 */
	String toBuilderCode();
}
