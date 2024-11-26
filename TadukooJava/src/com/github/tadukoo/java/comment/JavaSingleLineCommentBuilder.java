package com.github.tadukoo.java.comment;

/**
 * A builder used to build a {@link JavaSingleLineComment}. It takes the following parameters:
 *
 * <table>
 *     <caption>Single Line Comment Parameters</caption>
 *     <tr>
 *         <th>Parameter Name</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>content</td>
 *         <td>The content of the {@link JavaSingleLineComment comment}</td>
 *         <td>Defaults to empty string</td>
 *     </tr>
 * </table>
 *
 * @param <CommentType> The type of {@link JavaSingleLineComment} to be built
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaSingleLineCommentBuilder<CommentType extends JavaSingleLineComment>{
	/** The content of the {@link JavaSingleLineComment comment} */
	protected String content = "";
	
	/**
	 * Constructs a new {@link JavaSingleLineCommentBuilder}
	 */
	protected JavaSingleLineCommentBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaSingleLineComment comment} into this builder
	 *
	 * @param comment The {@link JavaSingleLineComment comment} to copy settings from
	 * @return this, to continue building
	 */
	public JavaSingleLineCommentBuilder<CommentType> copy(JavaSingleLineComment comment){
		this.content = comment.getContent();
		return this;
	}
	
	/**
	 * @param content The content of the {@link JavaSingleLineComment comment}
	 * @return this, to continue building
	 */
	public JavaSingleLineCommentBuilder<CommentType> content(String content){
		this.content = content;
		return this;
	}
	
	/**
	 * Used by subclasses to actually construct the {@link JavaSingleLineComment}
	 *
	 * @return The newly constructed {@link JavaSingleLineComment}
	 */
	protected abstract CommentType constructSingleLineComment();
	
	/**
	 * Builds the {@link JavaSingleLineComment comment} based on the set parameters
	 *
	 * @return The newly built {@link JavaSingleLineComment comment}
	 */
	public CommentType build(){
		return constructSingleLineComment();
	}
}
