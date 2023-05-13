package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseJavaParserTest{
	
	protected JavaParser parser;
	
	@BeforeEach
	public void setup(){
		parser = new JavaParser();
	}
	
	/**
	 * Used to build a {@link JavaParsingException} error message to check that proper error messages
	 * are returned for error cases
	 *
	 * @param type The {@link JavaCodeTypes Java type} encountered during the error
	 * @param message The base part of the error message
	 * @return A properly formatted {@link JavaParsingException} error message
	 */
	protected String buildJavaParsingExceptionMessage(JavaCodeTypes type, String message){
		return "Failed parsing JavaType: '" + type + "': " + message;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaPackageDeclaration}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaPackageDeclaration}
	 * @return The parsed {@link JavaPackageDeclaration}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaPackageDeclaration runParserForPackageDeclaration(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.PACKAGE_DECLARATION, type.getJavaCodeType());
		assertTrue(type instanceof JavaPackageDeclaration);
		return (JavaPackageDeclaration) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaImportStatement}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaImportStatement}
	 * @return The parsed {@link JavaImportStatement}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaImportStatement runParserForImportStatement(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.IMPORT_STATEMENT, type.getJavaCodeType());
		assertTrue(type instanceof JavaImportStatement);
		return (JavaImportStatement) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaSingleLineComment}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaSingleLineComment}
	 * @return The parsed {@link JavaSingleLineComment}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaSingleLineComment runParserForSingleLineComment(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.SINGLE_LINE_COMMENT, type.getJavaCodeType());
		assertTrue(type instanceof JavaSingleLineComment);
		return (JavaSingleLineComment) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaAnnotation}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaAnnotation}
	 * @return The parsed {@link JavaAnnotation}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaAnnotation runParserForAnnotation(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.ANNOTATION, type.getJavaCodeType());
		assertTrue(type instanceof JavaAnnotation);
		return (JavaAnnotation) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaField}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaField}
	 * @return The parsed {@link JavaField}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaField runParserForField(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.FIELD, type.getJavaCodeType());
		assertTrue(type instanceof JavaField);
		return (JavaField) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaMethod}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaMethod}
	 * @return The parsed {@link JavaMethod}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaMethod runParserForMethod(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.METHOD, type.getJavaCodeType());
		assertTrue(type instanceof JavaMethod);
		return (JavaMethod) type;
	}
	
	/**
	 * Used to run the {@link JavaParser} and get back a {@link JavaClass}. Will fail some
	 * assertions if some other thing is returned instead
	 *
	 * @param content The String to be parsed into a {@link JavaClass}
	 * @return The parsed {@link JavaClass}
	 * @throws JavaParsingException If anything goes wrong with parsing
	 */
	protected JavaClass runParserForClass(String content) throws JavaParsingException{
		JavaCodeType type = parser.parseType(content);
		assertEquals(JavaCodeTypes.CLASS, type.getJavaCodeType());
		assertTrue(type instanceof JavaClass);
		return (JavaClass) type;
	}
}
