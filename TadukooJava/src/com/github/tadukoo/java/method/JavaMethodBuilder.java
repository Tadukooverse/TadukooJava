package com.github.tadukoo.java.method;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Method Builder is used to build a new {@link JavaMethod}. It contains the following parameters:
 *
 * <table>
 *     <caption>Java Method Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>javadoc</td>
 *         <td>The {@link Javadoc} on the method</td>
 *         <td>null</td>
 *     </tr>
 *     <tr>
 *         <td>annotations</td>
 *         <td>The {@link JavaAnnotation annotations} on the method</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>visibility</td>
 *         <td>The {@link Visibility} of the method</td>
 *         <td>{@link Visibility#NONE}</td>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>Whether the method is static or not</td>
 *         <td>Defaults to false</td>
 *     </tr>
 *     <tr>
 *         <td>isFinal</td>
 *         <td>Whether the method is final or not</td>
 *         <td>Defaults to false</td>
 *     </tr>
 *     <tr>
 *         <td>returnType</td>
 *         <td>The return type of the method</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>name</td>
 *         <td>The name of the method</td>
 *         <td>null (used for constructors)</td>
 *     </tr>
 *     <tr>
 *         <td>parameters</td>
 *         <td>The parameters used in the method - pairs of type, then name</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>throwTypes</td>
 *         <td>The types that can be thrown by the method</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>lines</td>
 *         <td>The actual lines of code in the method</td>
 *         <td>An empty list</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (within JavaMethod), Alpha v.0.4 (as a separate class)
 */
public abstract class JavaMethodBuilder<MethodType extends JavaMethod>{
	
	/** The {@link Javadoc} on the method */
	protected Javadoc javadoc = null;
	/** The {@link JavaAnnotation annotations} on the method */
	protected List<JavaAnnotation> annotations = new ArrayList<>();
	/** The {@link Visibility} of the method */
	protected Visibility visibility = Visibility.NONE;
	/** Whether the method is static or not */
	protected boolean isStatic = false;
	/** Whether the method is final or not */
	protected boolean isFinal = false;
	/** The return type of the method */
	protected String returnType = null;
	/** The name of the method */
	protected String name = null;
	/** The parameters used in the method - pairs of type, then name */
	protected List<Pair<String, String>> parameters = new ArrayList<>();
	/** The types that can be thrown by the method */
	protected List<String> throwTypes = new ArrayList<>();
	/** The actual lines of code in the method */
	protected List<String> lines = new ArrayList<>();
	
	/**
	 * Constructs a new JavaMethodBuilder
	 */
	protected JavaMethodBuilder(){ }
	
	/**
	 * @param javadoc The {@link Javadoc} on the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> javadoc(Javadoc javadoc){
		this.javadoc = javadoc;
		return this;
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> annotations(List<JavaAnnotation> annotations){
		this.annotations = annotations;
		return this;
	}
	
	/**
	 * @param annotation A single {@link JavaAnnotation annotation} on the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> annotation(JavaAnnotation annotation){
		annotations.add(annotation);
		return this;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> visibility(Visibility visibility){
		this.visibility = visibility;
		return this;
	}
	
	/**
	 * Sets the method as static
	 *
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> isStatic(){
		isStatic = true;
		return this;
	}
	
	/**
	 * @param isStatic Whether the method is static or not
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> isStatic(boolean isStatic){
		this.isStatic = isStatic;
		return this;
	}
	
	/**
	 * Sets the method as final
	 *
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> isFinal(){
		isFinal = true;
		return this;
	}
	
	/**
	 * @param isFinal Whether the method is final or not
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> isFinal(boolean isFinal){
		this.isFinal = isFinal;
		return this;
	}
	
	/**
	 * @param returnType The return type of the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> returnType(String returnType){
		this.returnType = returnType;
		return this;
	}
	
	/**
	 * @param name The name of the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> name(String name){
		this.name = name;
		return this;
	}
	
	/**
	 * @param parameters The parameters used in the method - pairs of type, then name
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> parameters(List<Pair<String, String>> parameters){
		this.parameters = parameters;
		return this;
	}
	
	/**
	 * @param parameter A single parameter, with type first, then name - to add to the list
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> parameter(Pair<String, String> parameter){
		parameters.add(parameter);
		return this;
	}
	
	/**
	 * @param type The type of the parameter to be added
	 * @param name The name of the parameter to be added
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> parameter(String type, String name){
		parameters.add(Pair.of(type, name));
		return this;
	}
	
	/**
	 * @param throwTypes The types the method can throw
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> throwTypes(List<String> throwTypes){
		this.throwTypes = throwTypes;
		return this;
	}
	
	/**
	 * @param throwType A type the method can throw - to add to the list
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> throwType(String throwType){
		throwTypes.add(throwType);
		return this;
	}
	
	/**
	 * @param lines The actual lines of code in the method
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> lines(List<String> lines){
		this.lines = lines;
		return this;
	}
	
	/**
	 * @param line A single line of code in the method, to add to the list
	 * @return this, to continue building
	 */
	public JavaMethodBuilder<MethodType> line(String line){
		lines.add(line);
		return this;
	}
	
	/**
	 * Checks for any errors in the current parameters
	 *
	 * @throws IllegalArgumentException if anything is wrong
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		// Visibility is required
		if(visibility == null){
			errors.add("Visibility is required!");
		}
		
		// Must specify return type
		if(StringUtil.isBlank(returnType)){
			errors.add("Must specify returnType!");
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
	 * Checks for any errors in the current parameters, then builds a new {@link JavaMethod}
	 *
	 * @return A newly built {@link JavaMethod}
	 * @throws IllegalArgumentException if anything is wrong with the current parameters
	 */
	public MethodType build(){
		checkForErrors();
		
		return constructMethod();
	}
	
	/**
	 * Constructs a new {@link JavaMethod} using the set parameters
	 *
	 * @return The newly created {@link JavaMethod}
	 */
	protected abstract MethodType constructMethod();
}
