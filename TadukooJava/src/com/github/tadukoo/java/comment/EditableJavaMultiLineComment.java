package com.github.tadukoo.java.comment;

import java.util.List;

/**
 * Represents a {@link JavaMultiLineComment} that may be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class EditableJavaMultiLineComment extends JavaMultiLineComment{
	
	/**
	 * A builder to build an {@link EditableJavaMultiLineComment}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaMultiLineCommentBuilder
	 */
	public static class EditableJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<EditableJavaMultiLineComment>{
		
		/** Not allowed to instantiate outside {@link EditableJavaMultiLineComment} */
		private EditableJavaMultiLineCommentBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaMultiLineComment constructComment(){
			return new EditableJavaMultiLineComment(content);
		}
	}
	
	/**
	 * Constructs a new {@link JavaMultiLineComment} with the given parameters
	 *
	 * @param content  The content of the comment
	 */
	private EditableJavaMultiLineComment(List<String> content){
		super(true, content);
	}
	
	/**
	 * @return A new {@link EditableJavaMultiLineCommentBuilder builder} to use to build an {@link EditableJavaMultiLineComment}
	 */
	public static EditableJavaMultiLineCommentBuilder builder(){
		return new EditableJavaMultiLineCommentBuilder();
	}
	
	/**
	 * @param content A single line of content to add to this comment
	 */
	public void addContent(String content){
		this.content.add(content);
	}
	
	/**
	 * @param content Content to be added to this comment
	 */
	public void addContent(List<String> content){
		this.content.addAll(content);
	}
	
	/**
	 * @param content The content of the comment
	 */
	public void setContent(List<String> content){
		this.content = content;
	}
}
