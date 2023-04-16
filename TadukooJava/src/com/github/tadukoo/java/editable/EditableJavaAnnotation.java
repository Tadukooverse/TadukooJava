package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaAnnotationBuilder;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Represents an annotation in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.4
 */
public class EditableJavaAnnotation extends JavaAnnotation{
	
	/**
	 * A builder used to make an {@link EditableJavaAnnotation}.
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @since Alpha v.0.4
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
			return new EditableJavaAnnotation(name, parameters);
		}
	}
	
	/**
	 * Constructs a new {@link EditableJavaAnnotation} with the given parameters
	 *
	 * @param name The name of the annotation
	 * @param parameters The parameters of the annotation (as Pairs of name and value)
	 */
	private EditableJavaAnnotation(String name, List<Pair<String, String>> parameters){
		super(true, name, parameters);
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
	
	/**
	 * @param name The name of the parameter to be added to the annotation
	 * @param value The value of the parameter to be added to the annotation
	 */
	public void addParameter(String name, String value){
		parameters.add(Pair.of(name, value));
	}
	
	/**
	 * @param parameter A parameter to be added to the annotation (as a Pair of name and value)
	 */
	public void addParameter(Pair<String, String> parameter){
		parameters.add(parameter);
	}
	
	/**
	 * @param parameters Parameters to be added to the annotation (as Pairs of name and value)
	 */
	public void addParameters(List<Pair<String, String>> parameters){
		this.parameters.addAll(parameters);
	}
	
	/**
	 * @param parameters The parameters of the annotation (as Pairs of name and value)
	 */
	public void setParameters(List<Pair<String, String>> parameters){
		this.parameters = parameters;
	}
}
