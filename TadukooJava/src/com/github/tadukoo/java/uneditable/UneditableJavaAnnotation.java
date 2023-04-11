package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaAnnotationBuilder;

/**
 * Represents a {@link JavaAnnotation} that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.3 (as JavaAnnotation), Alpha v.0.4 (as UneditableJavaAnnotation)
 */
public class UneditableJavaAnnotation extends JavaAnnotation{
	
	/**
	 * A builder used to make an {@link UneditableJavaAnnotation}.
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaAnnotationBuilder
	 */
	public static class UneditableJavaAnnotationBuilder extends JavaAnnotationBuilder<UneditableJavaAnnotation>{
		
		/** Not allowed to create outside of Uneditable Java Annotation */
		private UneditableJavaAnnotationBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		public UneditableJavaAnnotation constructAnnotation(){
			return new UneditableJavaAnnotation(name);
		}
	}
	
	/**
	 * Constructs an {@link UneditableJavaAnnotation} with the given parameters
	 *
	 * @param name The name of the annotation
	 */
	private UneditableJavaAnnotation(String name){
		super(false, name);
	}
	
	/**
	 * @return A new {@link UneditableJavaAnnotationBuilder} to use to build an {@link UneditableJavaAnnotation}
	 */
	public static UneditableJavaAnnotationBuilder builder(){
		return new UneditableJavaAnnotationBuilder();
	}
}
