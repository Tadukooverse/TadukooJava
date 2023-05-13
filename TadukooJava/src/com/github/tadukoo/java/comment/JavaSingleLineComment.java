package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents a Single-Line Comment in Java
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaSingleLineComment implements JavaCodeType{
	/** Whether the comment is editable or not */
	private final boolean editable;
	/** The content of the comment */
	protected String content;
	
	/**
	 * Constructs a new {@link JavaSingleLineComment single-line comment} with the given parameters
	 *
	 * @param editable Whether the comment is editable or not
	 * @param content The content of the comment
	 */
	protected JavaSingleLineComment(boolean editable, String content){
		this.editable = editable;
		this.content = content;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.SINGLE_LINE_COMMENT;
	}
	
	/**
	 * @return Whether the comment is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The content of the comment
	 */
	public String getContent(){
		return content;
	}
	
	/**
	 * @return The actual Java code this {@link JavaSingleLineComment comment} represents
	 */
	@Override
	public String toString(){
		return SINGLE_LINE_COMMENT_TOKEN + " " + content;
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherComment){
		if(otherComment instanceof JavaSingleLineComment comment){
			return StringUtil.equals(toString(), comment.toString());
		}else{
			return false;
		}
	}
}
