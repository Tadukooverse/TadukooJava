package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.List;

/**
 * Represents a multi-line comment in Java code
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaMultiLineComment implements JavaCodeType{
	/** Whether this comment is editable or not */
	private final boolean editable;
	/** The content of the comment */
	protected List<String> content;
	
	/**
	 * Constructs a new {@link JavaMultiLineComment} with the given parameters
	 *
	 * @param editable Whether this comment is editable or not
	 * @param content The content of the comment
	 */
	protected JavaMultiLineComment(boolean editable, List<String> content){
		this.editable = editable;
		this.content = content;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.MULTI_LINE_COMMENT;
	}
	
	/**
	 * @return Whether this comment is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The content of the comment
	 */
	public List<String> getContent(){
		return content;
	}
	
	/**
	 * @return This multi-line comment as a string
	 */
	@Override
	public String toString(){
		StringBuilder text = new StringBuilder(MULTI_LINE_COMMENT_START_TOKEN);
		
		// Add content if we have it
		if(ListUtil.isNotBlank(content)){
			for(String line: content){
				text.append("\n ").append(JAVADOC_LINE_TOKEN).append(' ').append(line);
			}
		}
		
		// Finish the comment
		text.append("\n ").append(MULTI_LINE_COMMENT_CLOSE_TOKEN);
		
		return text.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj){
		if(obj instanceof JavaMultiLineComment comment){
			return StringUtil.equals(toString(), comment.toString());
		}
		return false;
	}
}
