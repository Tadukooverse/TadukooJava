package com.github.tadukoo.java.annotation;

import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Represents an annotation in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.3 (as old version that is now more like UneditableJavaAnnotation), Alpha v.0.4 (as newer version)
 */
public abstract class JavaAnnotation implements JavaType{
	
	/** Whether the annotation is editable or not */
	private final boolean editable;
	/** The name of the annotation */
	protected String name;
	/** The parameters of the annotation (as Pairs of name and value) */
	protected List<Pair<String, String>> parameters;
	
	/**
	 * Constructs a new {@link JavaAnnotation} with the given parameters
	 *
	 * @param editable Whether the annotation is editable or not
	 * @param name The name of the annotation
	 * @param parameters The parameters of the annotation (as Pairs of name and value)
	 */
	protected JavaAnnotation(boolean editable, String name, List<Pair<String, String>> parameters){
		this.editable = editable;
		this.name = name;
		this.parameters = parameters;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaTypes getJavaType(){
		return JavaTypes.ANNOTATION;
	}
	
	/**
	 * @return Whether the annotation is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The name of the annotation
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The parameters of the annotation (as Pairs of name and value)
	 */
	public List<Pair<String, String>> getParameters(){
		return parameters;
	}
	
	/**
	 * @return This Java Annotation as a string, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		// Start with @Name
		StringBuilder annotation = new StringBuilder(ANNOTATION_START_TOKEN).append(name);
		
		// Add parameters if we have them
		if(ListUtil.isNotBlank(parameters)){
			annotation.append('(');
			for(Pair<String, String> parameter: parameters){
				annotation.append(parameter.getLeft()).append(" = ").append(parameter.getRight()).append(", ");
			}
			annotation.delete(annotation.length()-2, annotation.length()).append(')');
		}
		
		return annotation.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherAnnotation){
		if(otherAnnotation instanceof JavaAnnotation annotation){
			return StringUtil.equals(this.toString(), annotation.toString());
		}else{
			return false;
		}
	}
}