package com.github.tadukoo.java.annotation;

import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Represents a {@link JavaAnnotation} that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.3 (as JavaAnnotation), Alpha v.0.4 (as UneditableJavaAnnotation)
 */
public class UneditableJavaAnnotation extends JavaAnnotation{
	
	/**
	 * A builder used to make an {@link UneditableJavaAnnotation}.
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @since Alpha v.0.4
	 * @see JavaAnnotationBuilder
	 */
	public static class UneditableJavaAnnotationBuilder extends JavaAnnotationBuilder<UneditableJavaAnnotation>{
		
		/** Not allowed to create outside Uneditable Java Annotation */
		private UneditableJavaAnnotationBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		public UneditableJavaAnnotation constructAnnotation(){
			return new UneditableJavaAnnotation(name, canonicalName, parameters);
		}
	}
	
	/**
	 * Constructs an {@link UneditableJavaAnnotation} with the given parameters
	 *
	 * @param name The name of the annotation
	 * @param canonicalName The canonical name of the annotation
	 * @param parameters The parameters of the annotation (as Pairs of name and value)
	 */
	private UneditableJavaAnnotation(String name, String canonicalName, List<Pair<String, String>> parameters){
		super(false, name, canonicalName, parameters);
	}
	
	/**
	 * @return A new {@link UneditableJavaAnnotationBuilder} to use to build an {@link UneditableJavaAnnotation}
	 */
	public static UneditableJavaAnnotationBuilder builder(){
		return new UneditableJavaAnnotationBuilder();
	}
}
