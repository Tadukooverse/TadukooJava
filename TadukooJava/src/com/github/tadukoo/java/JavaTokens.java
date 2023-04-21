package com.github.tadukoo.java;

/**
 * An interface containing constants for various tokens in Java
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public interface JavaTokens{
	
	/** A semicolon - used commonly at the end of lines - ; */
	String SEMICOLON = ";";
	
	/*
	 * "Tokens"
	 */
	
	/** Used in package declarations - package */
	String PACKAGE_TOKEN = "package";
	/** Used in import statements - import */
	String IMPORT_TOKEN = "import";
	
	/*
	 * "Modifiers"
	 */
	
	/** Used as a modifier on some types - static */
	String STATIC_MODIFIER = "static";
}
