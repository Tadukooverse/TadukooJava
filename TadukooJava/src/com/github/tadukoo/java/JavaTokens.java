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
	
	/** A semicolon - used commonly at the end of lines - {@value} */
	String SEMICOLON = ";";
	
	/*
	 * Proper "Tokens"
	 */
	
	/** Used in package declarations - {@value} */
	String PACKAGE_TOKEN = "package";
	/** Used in import statements - {@value} */
	String IMPORT_TOKEN = "import";
	/** Used to declare a class - {@value} */
	String CLASS_TOKEN = "class";
	/** Used for throwing throwables - {@value} */
	String THROWS_TOKEN = "throws";
	/** Used for extending class types - {@value} */
	String EXTENDS_TOKEN = "extends";
	/** Used for implementing interfaces - {@value} */
	String IMPLEMENTS_TOKEN = "implements";
	
	/*
	 * Not-So-Proper "Tokens"
	 */
	
	/** Used to start a javadoc - {@value} */
	String JAVADOC_START_TOKEN = "/**";
	/** Used at the start of lines in a javadoc - {@value} */
	String JAVADOC_LINE_TOKEN = "*";
	/** Used to start a multi-line comment - {@value} */
	String MULTI_LINE_COMMENT_START_TOKEN = "/*";
	/** Used at the end of javadocs and multi-line comments - {@value} */
	String MULTI_LINE_COMMENT_CLOSE_TOKEN = "*/";
	/** Used to start a single-line comment - {@value} */
	String SINGLE_LINE_COMMENT_TOKEN = "//";
	/** Used to start an annotation - {@value} */
	String ANNOTATION_START_TOKEN = "@";
	/** Used to start a list of parameters - {@value} */
	String PARAMETER_OPEN_TOKEN = "(";
	/** Used to end a list of parameters - {@value} */
	String PARAMETER_CLOSE_TOKEN = ")";
	/** Used to separate items in a list - {@value} */
	String LIST_SEPARATOR_TOKEN = ",";
	/** Used to start a block of code - {@value} */
	String BLOCK_OPEN_TOKEN = "{";
	/** Used to end a block of code - {@value} */
	String BLOCK_CLOSE_TOKEN = "}";
	/** Used to make an assignment - {@value} */
	String ASSIGNMENT_OPERATOR_TOKEN = "=";
	
	/*
	 * Javadoc "Tokens"
	 */
	
	/** Used in Javadocs for author string - {@value} */
	String JAVADOC_AUTHOR_TOKEN = "author";
	/** Used in Javadocs for version string - {@value} */
	String JAVADOC_VERSION_TOKEN = "version";
	/** Used in Javadocs for since string - {@value} */
	String JAVADOC_SINCE_TOKEN = "since";
	/** Used in Javadocs for param strings - {@value} */
	String JAVADOC_PARAM_TOKEN = "param";
	/** Used in Javadocs for return string - {@value} */
	String JAVADOC_RETURN_TOKEN = "return";
	
	/*
	 * Visibility "Modifiers"
	 */
	
	/** Used as a visibility modifier - {@value} */
	String PRIVATE_MODIFIER = "private";
	/** Used as a visibility modifier - {@value} */
	String PROTECTED_MODIFIER = "protected";
	/** Used as a visibility modifier - {@value} */
	String PUBLIC_MODIFIER = "public";
	
	/** All the Visibility modifiers */
	Set<String> VISIBILITY_MODIFIERS = SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER);
	
	/*
	 * Other "Modifiers"
	 */
	
	/** Used as a modifier on some types - {@value} */
	String ABSTRACT_MODIFIER = "abstract";
	/** Used as a modifier on some types - {@value} */
	String STATIC_MODIFIER = "static";
	/** Used as a modifier on some types - {@value} */
	String FINAL_MODIFIER = "final";
	
	/** All the modifiers that exist */
	Set<String> MODIFIERS = SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER,
			ABSTRACT_MODIFIER, STATIC_MODIFIER, FINAL_MODIFIER);
}
