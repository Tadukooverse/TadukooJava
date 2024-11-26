package com.github.tadukoo.java.annotation;

import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Annotation Builder is used to build a new {@link JavaAnnotation}. It contains the following parameters:
 *
 * <table>
 *     <caption>Java Annotation Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>name</td>
 *         <td>The name of the annotation</td>
 *         <td>Required</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.3 (within JavaAnnotation), Alpha v.0.4 (on its own)
 */
public abstract class JavaAnnotationBuilder<AnnotationType extends JavaAnnotation>{
	
	/** The name of the annotation */
	protected String name;
	/** The canonical name of the annotation */
	protected String canonicalName = "";
	/** The parameters of the annotation (as Pairs of name and value) */
	protected List<Pair<String, String>> parameters = new ArrayList<>();
	
	/**
	 * Constructs a new {@link JavaAnnotationBuilder}
	 */
	protected JavaAnnotationBuilder(){ }
	
	/**
	 * Copies the settings on the given {@link JavaAnnotation annotation} into the builder
	 *
	 * @param annotation The {@link JavaAnnotation annotation} to copy settings from
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> copy(JavaAnnotation annotation){
		this.name = annotation.getName();
		this.canonicalName = annotation.getCanonicalName();
		this.parameters = annotation.getParameters();
		return this;
	}
	
	/**
	 * @param name The name of the annotation
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> name(String name){
		this.name = name;
		return this;
	}
	
	/**
	 * @param canonicalName The canonical name of the annotation
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> canonicalName(String canonicalName){
		this.canonicalName = canonicalName;
		return this;
	}
	
	/**
	 * @param name The name of the parameter to be added
	 * @param value The value of the parameter to be added
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> parameter(String name, String value){
		parameters.add(Pair.of(name, value));
		return this;
	}
	
	/**
	 * @param parameter The parameter to be added (as a Pair of name and value)
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> parameter(Pair<String, String> parameter){
		parameters.add(parameter);
		return this;
	}
	
	/**
	 * @param parameters The parameters of the annotation (as Pairs of name and value)
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> parameters(List<Pair<String, String>> parameters){
		this.parameters = parameters;
		return this;
	}
	
	/**
	 * Checks for any errors in the current parameters
	 *
	 * @throws IllegalArgumentException if anything is wrong
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		if(StringUtil.isBlank(name)){
			errors.add("Must specify name!");
		}
		
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Checks for any errors in the current parameters, then builds a new {@link JavaAnnotation}
	 *
	 * @return A newly built {@link JavaAnnotation}
	 * @throws IllegalArgumentException if anything is wrong with the current parameters
	 */
	public AnnotationType build(){
		checkForErrors();
		
		return constructAnnotation();
	}
	
	/**
	 * Constructs a new {@link JavaAnnotation} with the given parameters
	 *
	 * @return The newly constructed {@link JavaAnnotation}
	 */
	protected abstract AnnotationType constructAnnotation();
}
