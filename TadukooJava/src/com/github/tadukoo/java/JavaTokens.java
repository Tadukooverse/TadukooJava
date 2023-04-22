package com.github.tadukoo.java;

import com.github.tadukoo.util.SetUtil;

import java.util.Set;

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
	 * Proper "Tokens"
	 */
	
	/** Used in package declarations - package */
	String PACKAGE_TOKEN = "package";
	/** Used in import statements - import */
	String IMPORT_TOKEN = "import";
	
	/*
	 * Not-So-Proper "Tokens"
	 */
	
	/** Used to start a javadoc - /** */
	String JAVADOC_START_TOKEN = "/**";
	/** Used to start a multi-line comment - /* */
	String MULTI_LINE_COMMENT_START_TOKEN = "/*";
	/** Used to start a single-line comment - // */
	String SINGLE_LINE_COMMENT_TOKEN = "//";
	/** Used to start an annotation - @ */
	String ANNOTATION_START_TOKEN = "@";
	
	/*
	 * Visibility "Modifiers"
	 */
	
	/** Used as a visibility modifier - private */
	String PRIVATE_MODIFIER = "private";
	/** Used as a visibility modifier - protected */
	String PROTECTED_MODIFIER = "protected";
	/** Used as a visibility modifier - public */
	String PUBLIC_MODIFIER = "public";
	
	/*
	 * Other "Modifiers"
	 */
	
	/** Used as a modifier on some types - static */
	String STATIC_MODIFIER = "static";
	
	/** All the modifiers that exist */
	Set<String> MODIFIERS = SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER,
			STATIC_MODIFIER);
}
