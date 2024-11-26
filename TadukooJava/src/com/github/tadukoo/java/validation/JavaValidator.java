package com.github.tadukoo.java.validation;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Java Validator is used to validate that the code is correct and/or propagate information down to the lower
 * objects from higher ones to ensure everything is rightly formatted for Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaValidator{
	
	/**
	 * This method will set canonical names for objects inside the given {@link JavaClass class}
	 *
	 * @param javaClass The {@link JavaClass class} to determine canonical names for its objects
	 */
	public void determineCanonicalNames(JavaClass javaClass){
		// Ensure we have an editable class
		if(!(javaClass instanceof EditableJavaClass clazz)){
			throw new IllegalArgumentException("Need EditableJavaClass to modify canonical names");
		}
		
		// Grab all the imports
		List<JavaImportStatement> imports = clazz.getImportStatements();
		Map<String, String> simpleNameToCanonicalName = new HashMap<>();
		imports.forEach(importStmt -> {
			String importName = importStmt.getImportName();
			String[] split = importName.split("\\.");
			String simpleName = split[split.length-1];
			simpleNameToCanonicalName.put(simpleName, importName);
		});
		
		// Handle determining canonical names for annotations
		List<JavaAnnotation> annotations = clazz.getAnnotations();
		for(JavaAnnotation javaAnnotation: annotations){
			// Ensure annotation is editable
			if(!(javaAnnotation instanceof EditableJavaAnnotation annotation)){
				throw new IllegalArgumentException("Need EditableJavaAnnotations to modify canonical names");
			}
			
			// Set canonical name based on simple name
			String canonicalName = simpleNameToCanonicalName.get(annotation.getName());
			annotation.setCanonicalName(canonicalName);
		}
	}
}
