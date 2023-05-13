package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;

/**
 * An enum for the various {@link JavaCodeTypes} types of Java classes and elements.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public enum JavaCodeTypes{
	
	/**
	 * Represents an unknown type
	 * Main {@link JavaCodeType} class: None
	 */
	UNKNOWN(),
	
	/**
	 * Represents a package declaration in Java
	 * Main {@link JavaCodeType} class: {@link JavaPackageDeclaration}
	 */
	PACKAGE_DECLARATION(JavaPackageDeclaration.class),
	
	/**
	 * Represents an import statement in Java
	 * Main {@link JavaCodeType} class: {@link JavaImportStatement}
	 */
	IMPORT_STATEMENT(JavaImportStatement.class),
	
	/**
	 * Represents a Javadoc in Java
	 * Main {@link JavaCodeType} class: {@link Javadoc}
	 */
	JAVADOC(Javadoc.class),
	
	/**
	 * Represents a Multi-line Comment in Java
	 */
	MULTI_LINE_COMMENT(),
	
	/**
	 * Represents a Single-line Comment in Java
	 * Main {@link JavaCodeType} class: {@link JavaSingleLineComment}
	 */
	SINGLE_LINE_COMMENT(JavaSingleLineComment.class),
	
	/**
	 * Represents an annotation in Java
	 * Main {@link JavaCodeType} class: {@link JavaAnnotation}
	 */
	ANNOTATION(JavaAnnotation.class),
	
	/**
	 * Represents a Java type with modifiers (e.g. field, method, class, interface)
	 * Main {@link JavaCodeType} class: None
	 */
	TYPE_WITH_MODIFIERS(),
	
	/**
	 * Represents a field in Java
	 * Main {@link JavaCodeType} class: {@link JavaField}
	 */
	FIELD(JavaField.class),
	
	/**
	 * Represents a method in Java
	 * Main {@link JavaCodeType} class: {@link JavaMethod}
	 */
	METHOD(JavaMethod.class),
	
	/**
	 * Represents a class in Java
	 * Main {@link JavaCodeType} class: {@link JavaClass}
	 */
	CLASS(JavaClass.class);
	
	/** The main {@link JavaCodeType} class for the enum */
	private final Class<? extends JavaCodeType> clazz;
	
	/**
	 * Constructs a new {@link JavaCodeTypes} with no class
	 */
	JavaCodeTypes(){
		this.clazz = null;
	}
	
	/**
	 * Constructs a new {@link JavaCodeTypes} with the given parameters
	 *
	 * @param clazz The main {@link JavaCodeType} class for the enum
	 */
	JavaCodeTypes(Class<? extends JavaCodeType> clazz){
		this.clazz = clazz;
	}
	
	/**
	 * @return The main {@link JavaCodeType} class for the enum
	 */
	public Class<? extends JavaCodeType> getJavaTypeClass(){
		return clazz;
	}
}
