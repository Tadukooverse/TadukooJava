package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.JavaClassBuilder;
import com.github.tadukoo.java.JavaField;
import com.github.tadukoo.java.JavaMethod;
import com.github.tadukoo.java.Javadoc;
import com.github.tadukoo.java.Visibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a class in Java that is not modifiable
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.2 (as JavaClass), Alpha v.0.4 (as UneditableJavaClass)
 */
public class UneditableJavaClass extends JavaClass{
	
	/**
	 * A builder used to make an {@link UneditableJavaClass}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaClassBuilder
	 */
	public static class UneditableJavaClassBuilder extends JavaClassBuilder<UneditableJavaClass>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaClass} */
		private UneditableJavaClassBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
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
			return new UneditableJavaClass(isInnerClass, packageName, imports, staticImports,
					javadoc, annotations,
					visibility, isStatic, className, superClassName,
					innerClasses, fields, methods);
		}
	}
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param isInnerClass Whether this is an inner class or not
	 * @param packageName The name of the package the class is in
	 * @param imports The classes imported by the class
	 * @param staticImports The classes imported statically by the class
	 * @param javadoc The {@link Javadoc} for the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param isStatic Whether this is a static class or not
	 * @param className The name of the class
	 * @param superClassName The name of the class this one extends (may be null)
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 */
	private UneditableJavaClass(
			boolean isInnerClass, String packageName, List<String> imports, List<String> staticImports,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, String className, String superClassName,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods){
		super(false, isInnerClass, packageName, imports, staticImports,
				javadoc, annotations,
				visibility, isStatic, className, superClassName,
				innerClasses, fields, methods);
	}
	
	/**
	 * @return A new {@link UneditableJavaClassBuilder} to use to build a new {@link UneditableJavaClass}
	 */
	public static UneditableJavaClassBuilder builder(){
		return new UneditableJavaClassBuilder();
	}
}
