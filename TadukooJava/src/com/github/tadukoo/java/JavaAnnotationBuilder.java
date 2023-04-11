package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

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
 * @version Alpha v.0.4
 * @since Alpha v.0.3 (within JavaAnnotation), Alpha v.0.4 (on its own)
 */
public abstract class JavaAnnotationBuilder<AnnotationType extends JavaAnnotation>{
	
	/** The name of the annotation */
	protected String name;
	
	/**
	 * Constructs a new {@link JavaAnnotationBuilder}
	 */
	protected JavaAnnotationBuilder(){ }
	
	/**
	 * @param name The name of the annotation
	 * @return this, to continue building
	 */
	public JavaAnnotationBuilder<AnnotationType> name(String name){
		this.name = name;
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
