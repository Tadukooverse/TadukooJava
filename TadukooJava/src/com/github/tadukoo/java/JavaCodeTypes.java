package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
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
 * @version Beta v.0.6
 * @since Beta v.0.5
 */
public enum JavaCodeTypes{
	
	/**
	 * Represents an unknown type
	 * <br>
	 * Main {@link JavaCodeType} class: None
	 * <br>
	 * Standard Name: unknown
	 */
	UNKNOWN("unknown"),
	
	/**
	 * Represents a type parameter in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaTypeParameter}
	 * <br>
	 * Standard Name: type parameter
	 */
	TYPE_PARAMETER(JavaTypeParameter.class, "type parameter"),
	
	/**
	 * Represents a type in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaType}
	 * <br>
	 * Standard Name: type
	 */
	TYPE(JavaType.class, "type"),
	
	/**
	 * Represents a parameter in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaParameter}
	 * <br>
	 * Standard Name: parameter
	 */
	PARAMETER(JavaParameter.class, "parameter"),
	
	/**
	 * Represents a package declaration in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaPackageDeclaration}
	 * <br>
	 * Standard Name: package declaration
	 */
	PACKAGE_DECLARATION(JavaPackageDeclaration.class, "package declaration"),
	
	/**
	 * Represents an import statement in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaImportStatement}
	 * <br>
	 * Standard name: import statement
	 */
	IMPORT_STATEMENT(JavaImportStatement.class, "import statement"),
	
	/**
	 * Represents a Javadoc in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link Javadoc}
	 * <br>
	 * Standard name: Javadoc
	 */
	JAVADOC(Javadoc.class, "Javadoc"),
	
	/**
	 * Represents a Multi-line Comment in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaMultiLineComment}
	 * <br>
	 * Standard name: multi-line comment
	 */
	MULTI_LINE_COMMENT(JavaMultiLineComment.class, "multi-line comment"),
	
	/**
	 * Represents a Single-line Comment in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaSingleLineComment}
	 * <br>
	 * Standard name: single-line comment
	 */
	SINGLE_LINE_COMMENT(JavaSingleLineComment.class, "single-line comment"),
	
	/**
	 * Represents an annotation in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaAnnotation}
	 * <br>
	 * Standard name: annotation
	 */
	ANNOTATION(JavaAnnotation.class, "annotation"),
	
	/**
	 * Represents a Java type with modifiers (e.g. field, method, class, interface)
	 * <br>
	 * Main {@link JavaCodeType} class: None
	 * <br>
	 * Standard name: type with modifiers
	 */
	TYPE_WITH_MODIFIERS("type with modifiers"),
	
	/**
	 * Represents a field in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaField}
	 * <br>
	 * Standard name: field
	 */
	FIELD(JavaField.class, "field"),
	
	/**
	 * Represents a method in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaMethod}
	 * <br>
	 * Standard name: method
	 */
	METHOD(JavaMethod.class, "method"),
	
	/**
	 * Represents a class in Java
	 * <br>
	 * Main {@link JavaCodeType} class: {@link JavaClass}
	 * <br>
	 * Standard name: class
	 */
	CLASS(JavaClass.class, "class");
	
	/** The main {@link JavaCodeType} class for the enum */
	private final Class<? extends JavaCodeType> clazz;
	/** A standard name for the type */
	private final String standardName;
	
	/**
	 * Constructs a new {@link JavaCodeTypes} with no class
	 *
	 * @param standardName A standard name for the type
	 */
	JavaCodeTypes(String standardName){
		this.clazz = null;
		this.standardName = standardName;
	}
	
	/**
	 * Constructs a new {@link JavaCodeTypes} with the given parameters
	 *
	 * @param clazz The main {@link JavaCodeType} class for the enum
	 * @param standardName A standard name for the type
	 */
	JavaCodeTypes(Class<? extends JavaCodeType> clazz, String standardName){
		this.clazz = clazz;
		this.standardName = standardName;
	}
	
	/**
	 * @return The main {@link JavaCodeType} class for the enum
	 */
	public Class<? extends JavaCodeType> getJavaTypeClass(){
		return clazz;
	}
	
	/**
	 * @return A standard name for the type
	 */
	public String getStandardName(){
		return standardName;
	}
}
