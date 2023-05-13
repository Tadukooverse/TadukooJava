package com.github.tadukoo.java.method;

import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Method represents a method in a Java class or interface, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaMethod), Alpha v.0.4 (as newer version)
 */
public abstract class JavaMethod implements JavaType{
	
	/** Whether the method is editable or not */
	private final boolean editable;
	/** The {@link Javadoc} on the method */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the method */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the method */
	protected Visibility visibility;
	/** Whether the method is static or not */
	protected boolean isStatic;
	/** Whether the method is final or not */
	protected boolean isFinal;
	/** The return type of the method */
	protected String returnType;
	/** The name of the method */
	protected String name;
	/** The parameters used in the method - pairs of type, then name */
	protected List<Pair<String, String>> parameters;
	/** The types that can be thrown by the method */
	protected List<String> throwTypes;
	/** The actual lines of code in the method */
	protected List<String> lines;
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param editable Whether the method is editable or not
	 * @param javadoc The {@link Javadoc} on the method
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @param visibility The {@link Visibility} of the method
	 * @param isStatic Whether the method is static or not
	 * @param isFinal Whether the method is final or not
	 * @param returnType The return type of the method
	 * @param name The name of the method
	 * @param parameters The parameters used in the method - pairs of type, then name
	 * @param throwTypes The types that can be thrown by the method
	 * @param lines The actual lines of code in the method
	 */
	protected JavaMethod(
			boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal, String returnType, String name,
			List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
		this.editable = editable;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.throwTypes = throwTypes;
		this.lines = lines;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaTypes getJavaType(){
		return JavaTypes.METHOD;
	}
	
	/**
	 * @return Whether the method is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The {@link Javadoc} on the method
	 */
	public Javadoc getJavadoc(){
		return javadoc;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the method
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the method
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return Whether the method is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @return Whether the method is final or not
	 */
	public boolean isFinal(){
		return isFinal;
	}
	
	/**
	 * @return The return type of the method
	 */
	public String getReturnType(){
		return returnType;
	}
	
	/**
	 * @return The name of the method
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The parameters used in the method - pairs of type, then name
	 */
	public List<Pair<String, String>> getParameters(){
		return parameters;
	}
	
	/**
	 * @return The types that can be thrown by the method
	 */
	public List<String> getThrowTypes(){
		return throwTypes;
	}
	
	/**
	 * @return The actual lines of code in the method
	 */
	public List<String> getLines(){
		return lines;
	}
	
	/**
	 * @return This Java Method as a String, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		List<String> content = new ArrayList<>();
		
		// Javadoc
		if(javadoc != null){
			content.add(javadoc.toString());
		}
		
		// Annotations
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				content.add(annotation.toString());
			}
		}
		
		/*
		 * Declaration
		 */
		// Start with the visibility
		StringBuilder declaration = new StringBuilder(visibility.getToken());
		if(!declaration.isEmpty()){
			// Add a space is visibility is not empty
			declaration.append(' ');
		}
		
		// optionally add static to the declaration
		if(isStatic){
			declaration.append(STATIC_MODIFIER).append(' ');
		}
		
		// Optionally add final to the declaration
		if(isFinal){
			declaration.append(FINAL_MODIFIER).append(' ');
		}
		
		// add return type to the declaration
		declaration.append(returnType);
		
		// Add name to declaration if we have it
		if(StringUtil.isNotBlank(name)){
			declaration.append(' ').append(name);
		}
		
		// Start of parameter section
		declaration.append(PARAMETER_OPEN_TOKEN);
		
		// Add parameters to the declaration
		if(ListUtil.isNotBlank(parameters)){
			for(Pair<String, String> parameter: parameters){
				declaration.append(parameter.getLeft()).append(' ').append(parameter.getRight())
						.append(LIST_SEPARATOR_TOKEN).append(' ');
			}
			// Remove final comma + space
			declaration.setLength(declaration.length()-2);
		}
		
		// If we have throw types, add them to the end of the declaration
		if(ListUtil.isNotBlank(throwTypes)){
			declaration.append(PARAMETER_CLOSE_TOKEN).append(' ').append(THROWS_TOKEN).append(' ');
			for(String throwType: throwTypes){
				declaration.append(throwType).append(LIST_SEPARATOR_TOKEN).append(' ');
			}
			// Remove the final comma and space
			declaration.delete(declaration.length() - 2, declaration.length());
			// Add the opening brace
			declaration.append(BLOCK_OPEN_TOKEN);
		}else{
			// If no throw types, just end the declaration
			declaration.append(PARAMETER_CLOSE_TOKEN).append(BLOCK_OPEN_TOKEN);
		}
		// Add the declaration to the content
		content.add(declaration.toString());
		
		// Add the lines to the method
		if(ListUtil.isNotBlank(lines)){
			for(String line: lines){
				content.add("\t" + line);
			}
		}
		
		// Closing brace of the method
		content.add(BLOCK_CLOSE_TOKEN);
		
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherMethod){
		if(otherMethod instanceof JavaMethod method){
			return StringUtil.equals(this.toString(), method.toString());
		}else{
			return false;
		}
	}
}
