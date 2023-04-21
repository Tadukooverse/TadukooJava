package com.github.tadukoo.java;

import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;

/**
 * An enum for the various {@link JavaTypes} types of Java classes and elements.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public enum JavaTypes{
	
	/**
	 * Represents an unknown type
	 * Main {@link JavaType} class: None
	 */
	UNKNOWN(),
	
	/**
	 * Represents a package declaration in Java
	 * Main {@link JavaType} class: {@link JavaPackageDeclaration}
	 */
	PACKAGE_DECLARATION(JavaPackageDeclaration.class),
	
	/**
	 * Represents an import statement in Java
	 * Main {@link JavaType} class: {@link JavaImportStatement}
	 */
	IMPORT_STATEMENT(JavaImportStatement.class),
	
	/**
	 * Represents a class in Java
	 * Main {@link JavaType} class: {@link JavaClass}
	 */
	CLASS(JavaClass.class);
	
	/** The main {@link JavaType} class for the enum */
	private final Class<? extends JavaType> clazz;
	
	/**
	 * Constructs a new {@link JavaTypes} with no class
	 */
	JavaTypes(){
		this.clazz = null;
	}
	
	/**
	 * Constructs a new {@link JavaTypes} with the given parameters
	 *
	 * @param clazz The main {@link JavaType} class for the enum
	 */
	JavaTypes(Class<? extends JavaType> clazz){
		this.clazz = clazz;
	}
	
	/**
	 * @return The main {@link JavaType} class for the enum
	 */
	public Class<? extends JavaType> getJavaTypeClass(){
		return clazz;
	}
}
