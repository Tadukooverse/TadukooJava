package com.github.tadukoo.java.comment;

/**
 * Represents a {@link JavaSingleLineComment single-line comment} in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class EditableJavaSingleLineComment extends JavaSingleLineComment{
	
	/**
	 * A builder used to build an {@link EditableJavaSingleLineComment}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaSingleLineCommentBuilder
	 */
	public static class EditableJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<EditableJavaSingleLineComment>{
		
		/** Not allowed to instantiate outside {@link EditableJavaSingleLineComment} */
		private EditableJavaSingleLineCommentBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaSingleLineComment constructSingleLineComment(){
			return new EditableJavaSingleLineComment(content);
		}
	}
	
	/**
	 * Constructs a new {@link JavaSingleLineComment single-line comment} with the given parameters
	 *
	 * @param content  The content of the comment
	 */
	private EditableJavaSingleLineComment(String content){
		super(true, content);
	}
	
	/**
	 * @return A new {@link EditableJavaSingleLineCommentBuilder builder} to use to build an {@link EditableJavaSingleLineComment}
	 */
	public static EditableJavaSingleLineCommentBuilder builder(){
		return new EditableJavaSingleLineCommentBuilder();
	}
	
	/**
	 * @param content The content of the {@link JavaSingleLineComment comment}
	 */
	public void setContent(String content){
		this.content = content;
	}
}
