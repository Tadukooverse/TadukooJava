package com.github.tadukoo.java.comment;

/**
 * Represents a {@link JavaSingleLineComment single-line comment} in Java that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class UneditableJavaSingleLineComment extends JavaSingleLineComment{
	
	/**
	 * A builder used to make an {@link UneditableJavaSingleLineComment}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @see JavaSingleLineCommentBuilder
	 */
	public static class UneditableJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<UneditableJavaSingleLineComment>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaSingleLineComment} */
		private UneditableJavaSingleLineCommentBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaSingleLineComment constructSingleLineComment(){
			return new UneditableJavaSingleLineComment(content);
		}
	}
	
	/**
	 * Constructs a new {@link JavaSingleLineComment single-line comment} with the given parameters
	 *
	 * @param content  The content of the comment
	 */
	private UneditableJavaSingleLineComment(String content){
		super(false, content);
	}
	
	/**
	 * @return A new {@link UneditableJavaSingleLineCommentBuilder builder} to use to build a {@link UneditableJavaSingleLineComment}
	 */
	public static UneditableJavaSingleLineCommentBuilder builder(){
		return new UneditableJavaSingleLineCommentBuilder();
	}
}
