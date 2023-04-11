package com.github.tadukoo.java;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents an annotation in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.3 (as old version that is now more like UneditableJavaAnnotation), Alpha v.0.4 (as newer version)
 */
public abstract class JavaAnnotation{
	
	/** Whether the annotation is editable or not */
	private final boolean editable;
	/** The name of the annotation */
	protected String name;
	
	/**
	 * Constructs a new {@link JavaAnnotation} with the given parameters
	 *
	 * @param editable Whether the annotation is editable or not
	 * @param name The name of the annotation
	 */
	protected JavaAnnotation(boolean editable, String name){
		this.editable = editable;
		this.name = name;
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
	 * @return This Java Annotation as a string, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		return "@" + name;
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
