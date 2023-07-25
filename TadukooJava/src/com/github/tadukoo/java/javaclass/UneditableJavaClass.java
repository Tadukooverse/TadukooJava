package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class in Java that is not modifiable
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (as JavaClass), Alpha v.0.4 (as UneditableJavaClass)
 */
public class UneditableJavaClass extends JavaClass{
	
	/**
	 * A builder used to make an {@link UneditableJavaClass}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @since Alpha v.0.4
	 * @see JavaClassBuilder
	 */
	public static class UneditableJavaClassBuilder extends JavaClassBuilder<UneditableJavaClass>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaClass} */
		private UneditableJavaClassBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		protected JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder(){
			return UneditableJavaPackageDeclaration.builder();
		}
		
		/** {@inheritDoc} */
		protected JavaImportStatementBuilder<?> getImportStatementBuilder(){
			return UneditableJavaImportStatement.builder();
		}
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// Package Declaration can't be editable
			if(packageDeclaration != null && packageDeclaration.isEditable()){
				errors.add("package declaration is not uneditable in this uneditable JavaClass");
			}
			
			// Import Statements can't be editable
			for(JavaImportStatement importStatement: importStatements){
				if(importStatement.isEditable()){
					errors.add("some import statements are not uneditable in this uneditable JavaClass");
					break;
				}
			}
			
			// Javadoc can't be editable
			if(javadoc != null && javadoc.isEditable()){
				errors.add("javadoc is not uneditable in this uneditable JavaClass");
			}
			
			// Annotations can't be editable
			for(JavaAnnotation annotation: annotations){
				if(annotation.isEditable()){
					errors.add("some annotations are not uneditable in this uneditable JavaClass");
					break;
				}
			}
			
			// Inner Classes can't be editable
			for(JavaClass clazz: innerClasses){
				if(clazz.isEditable()){
					errors.add("some inner classes are not uneditable in this uneditable JavaClass");
					break;
				}
			}
			
			// Fields can't be editable
			for(JavaField field: fields){
				if(field.isEditable()){
					errors.add("some fields are not uneditable in this uneditable JavaClass");
					break;
				}
			}
			
			// Methods can't be editable
			for(JavaMethod method: methods){
				if(method.isEditable()){
					errors.add("some methods are not uneditable in this uneditable JavaClass");
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaClass constructClass(){
			return new UneditableJavaClass(isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
					innerClasses, fields, methods);
		}
	}
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param isInnerClass Whether this is an inner class or not
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class
	 * @param importStatements The {@link JavaImportStatement import statements} of the class
	 * @param javadoc The {@link Javadoc} for the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param isStatic Whether this is a static class or not
	 * @param isFinal Whether this is a final class or not
	 * @param className The name of the class
	 * @param superClassName The name of the class this one extends (can be null)
	 * @param implementsInterfaceNames The names of interfaces this class implements
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 */
	private UneditableJavaClass(
			boolean isInnerClass, JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal, String className,
			String superClassName, List<String> implementsInterfaceNames,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods){
		super(false, isInnerClass, packageDeclaration, importStatements,
				javadoc, annotations,
				visibility, isStatic, isFinal, className,
				superClassName, implementsInterfaceNames,
				innerClasses, fields, methods);
	}
	
	/**
	 * @return A new {@link UneditableJavaClassBuilder} to use to build a new {@link UneditableJavaClass}
	 */
	public static UneditableJavaClassBuilder builder(){
		return new UneditableJavaClassBuilder();
	}
}
