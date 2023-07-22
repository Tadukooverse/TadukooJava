package com.github.tadukoo.java.comment;

import java.util.List;

/**
 * Represents a {@link JavaMultiLineComment} that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class UneditableJavaMultiLineComment extends JavaMultiLineComment{
	
	/**
	 * A builder to build an {@link UneditableJavaMultiLineComment}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaMultiLineCommentBuilder
	 */
	public static class UneditableJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<UneditableJavaMultiLineComment>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaMultiLineComment} */
		private UneditableJavaMultiLineCommentBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaMultiLineComment constructComment(){
			return new UneditableJavaMultiLineComment(content);
		}
	}
	
	/**
	 * Constructs a new {@link JavaMultiLineComment} with the given parameters
	 *
	 * @param content  The content of the comment
	 */
	private UneditableJavaMultiLineComment(List<String> content){
		super(false, content);
	}
	
	/**
	 * @return A new {@link UneditableJavaMultiLineCommentBuilder builder} to use to build an {@link UneditableJavaMultiLineComment}
	 */
	public static UneditableJavaMultiLineCommentBuilder builder(){
		return new UneditableJavaMultiLineCommentBuilder();
	}
}
