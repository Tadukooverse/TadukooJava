package com.github.tadukoo.java.comment;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder used to build a {@link JavaMultiLineComment multi-line comment}. It takes the following parameters:
 *
 * <table>
 *     <caption>Java Multi-Line Comment Parameters</caption>
 *     <tr>
 *         <th>Parameter Name</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>content</td>
 *         <td>The content of the comment</td>
 *         <td>An empty list</td>
 *     </tr>
 * </table>
 *
 * @param <CommentType> The type of {@link JavaMultiLineComment} being built
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class JavaMultiLineCommentBuilder<CommentType extends JavaMultiLineComment>{
	
	/** The content of the comment */
	protected List<String> content = new ArrayList<>();
	
	/** Can only instantiate from subclasses */
	protected JavaMultiLineCommentBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaMultiLineComment comment} to this builder
	 *
	 * @param comment The {@link JavaMultiLineComment comment} to copy settings from
	 * @return this, to continue building
	 */
	public JavaMultiLineCommentBuilder<CommentType> copy(JavaMultiLineComment comment){
		this.content = comment.getContent();
		return this;
	}
	
	/**
	 * @param content A single line of content to add to the comment
	 * @return this, to continue building
	 */
	public JavaMultiLineCommentBuilder<CommentType> content(String content){
		this.content.add(content);
		return this;
	}
	
	/**
	 * @param content The content of the comment
	 * @return this, to continue building
	 */
	public JavaMultiLineCommentBuilder<CommentType> content(List<String> content){
		this.content = content;
		return this;
	}
	
	/**
	 * Used by subclasses to actually build the comment
	 *
	 * @return The newly constructed {@link JavaMultiLineComment comment}
	 */
	protected abstract CommentType constructComment();
	
	/**
	 * Builds a {@link JavaMultiLineComment comment} based on the set parameters
	 *
	 * @return The newly built {@link JavaMultiLineComment comment}
	 */
	public CommentType build(){
		return constructComment();
	}
}
