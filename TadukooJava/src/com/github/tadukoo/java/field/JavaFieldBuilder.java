package com.github.tadukoo.java.field;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Field Builder is a builder to create a {@link JavaField}. It has the following parameters:
 *
 * <table>
 *     <caption>Java Field Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>sectionComment</td>
 *         <td>The section comment above the field</td>
 *         <td>null</td>
 *     </tr>
 *     <tr>
 *         <td>javadoc</td>
 *         <td>The {@link Javadoc} on the field</td>
 *         <td>null</td>
 *     </tr>
 *     <tr>
 *         <td>annotations</td>
 *         <td>The {@link JavaAnnotation annotations} on the field</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>visibility</td>
 *         <td>The {@link Visibility} of the field</td>
 *         <td>{@link Visibility#NONE}</td>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>If the field is static or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>isFinal</td>
 *         <td>If the field is final or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>type</td>
 *         <td>The type of the field</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>name</td>
 *         <td>The name of the field</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>value</td>
 *         <td>The value assigned to the field</td>
 *         <td>null</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.2 (within JavaField), Alpha v.0.4 (as separate)
 */
public abstract class JavaFieldBuilder<FieldType extends JavaField>{
	/** The section comment above the field */
	protected String sectionComment = null;
	/** The {@link Javadoc} on the field */
	protected Javadoc javadoc = null;
	/** The {@link JavaAnnotation annotations} on the field */
	protected List<JavaAnnotation> annotations = new ArrayList<>();
	/** The {@link Visibility} of the field */
	protected Visibility visibility = Visibility.NONE;
	/** Whether the field is static or not */
	protected boolean isStatic = false;
	/** Whether the field is final or not */
	protected boolean isFinal = false;
	/** The type of the field */
	protected String type = null;
	/** The name of the field */
	protected String name = null;
	/** The value assigned to the field */
	protected String value = null;
	
	/**
	 * Constructs a new JavaFieldBuilder
	 */
	protected JavaFieldBuilder(){ }
	
	/**
	 * @param sectionComment The section comment above the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> sectionComment(String sectionComment){
		this.sectionComment = sectionComment;
		return this;
	}
	
	/**
	 * @param javadoc The {@link Javadoc} on the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> javadoc(Javadoc javadoc){
		this.javadoc = javadoc;
		return this;
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> annotations(List<JavaAnnotation> annotations){
		this.annotations = annotations;
		return this;
	}
	
	/**
	 * @param annotation A single {@link JavaAnnotation annotation} on the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> annotation(JavaAnnotation annotation){
		annotations.add(annotation);
		return this;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> visibility(Visibility visibility){
		this.visibility = visibility;
		return this;
	}
	
	/**
	 * Sets the field to be static
	 *
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> isStatic(){
		isStatic = true;
		return this;
	}
	
	/**
	 * @param isStatic Whether the field is static or not
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> isStatic(boolean isStatic){
		this.isStatic = isStatic;
		return this;
	}
	
	/**
	 * Sets the field to be final
	 *
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> isFinal(){
		isFinal = true;
		return this;
	}
	
	/**
	 * @param isFinal Whether the field is final or not
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> isFinal(boolean isFinal){
		this.isFinal = isFinal;
		return this;
	}
	
	/**
	 * @param type The type of the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> type(String type){
		this.type = type;
		return this;
	}
	
	/**
	 * @param name The name of the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> name(String name){
		this.name = name;
		return this;
	}
	
	/**
	 * @param value The value assigned to the field
	 * @return this, to continue building
	 */
	public JavaFieldBuilder<FieldType> value(String value){
		this.value = value;
		return this;
	}
	
	/**
	 * Checks for any errors in the current parameters
	 *
	 * @throws IllegalArgumentException if anything is wrong
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		// Visibility is required (none can be used)
		if(visibility == null){
			errors.add("Visibility is required!");
		}
		
		// Must specify type
		if(StringUtil.isBlank(type)){
			errors.add("Must specify type!");
		}
		
		// Must specify name
		if(StringUtil.isBlank(name)){
			errors.add("Must specify name!");
		}
		
		errors.addAll(checkForSpecificErrors());
		
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Checks for errors specific to the subclass
	 *
	 * @return The errors found in the subclass, or an empty List
	 */
	protected abstract List<String> checkForSpecificErrors();
	
	/**
	 * Checks for any errors in the current parameters, then builds a new {@link JavaField}
	 *
	 * @return A newly built {@link JavaField}
	 * @throws IllegalArgumentException if anything is wrong with the current parameters
	 */
	public FieldType build(){
		checkForErrors();
		
		return constructField();
	}
	
	/**
	 * Constructs a {@link JavaField} using the set parameters
	 *
	 * @return The newly built {@link JavaField}
	 */
	protected abstract FieldType constructField();
}
