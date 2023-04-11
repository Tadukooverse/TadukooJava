package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaAnnotationBuilder;

/**
 * Represents an annotation in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class EditableJavaAnnotation extends JavaAnnotation{
	
	/**
	 * A builder used to make an {@link EditableJavaAnnotation}.
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaAnnotationBuilder
	 */
	public static class EditableJavaAnnotationBuilder extends JavaAnnotationBuilder<EditableJavaAnnotation>{
		
		/** Not allowed to create outside Editable Java Annotation */
		private EditableJavaAnnotationBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		public EditableJavaAnnotation constructAnnotation(){
			return new EditableJavaAnnotation(name);
		}
	}
	
	/**
	 * Constructs a new {@link EditableJavaAnnotation} with the given parameters
	 *
	 * @param name The name of the annotation
	 */
	private EditableJavaAnnotation(String name){
		super(true, name);
	}
	
	/**
	 * @return A new {@link EditableJavaAnnotationBuilder} to use to make an {@link EditableJavaAnnotation}
	 */
	public static EditableJavaAnnotationBuilder builder(){
		return new EditableJavaAnnotationBuilder();
	}
	
	/**
	 * @param name The name of the annotation
	 */
	public void setName(String name){
		this.name = name;
	}
}
