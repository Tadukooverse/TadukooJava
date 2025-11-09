package com.github.tadukoo.java.code.staticcodeblock;

import java.util.List;

/**
 * Represents a static code block in Java that is not modifiable
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class UneditableJavaStaticCodeBlock extends JavaStaticCodeBlock{
	
	/**
	 * A builder used to make a {@link UneditableJavaStaticCodeBlock}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 * @see JavaStaticCodeBlockBuilder
	 */
	public static class UneditableJavaStaticCodeBlockBuilder extends JavaStaticCodeBlockBuilder<UneditableJavaStaticCodeBlock>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaStaticCodeBlock} */
		private UneditableJavaStaticCodeBlockBuilder(){ }
		
		@Override
		protected UneditableJavaStaticCodeBlock constructStaticCodeBlock(){
			return new UneditableJavaStaticCodeBlock(lines);
		}
	}
	
	/**
	 * Constructs a new Java Static Code Block with the given parameters
	 *
	 * @param lines The actual lines of code in the static code block
	 */
	private UneditableJavaStaticCodeBlock(List<String> lines){
		super(false, lines);
	}
	
	/**
	 * @return A new {@link UneditableJavaStaticCodeBlockBuilder} to use to build a new {@link UneditableJavaStaticCodeBlock}
	 */
	public static UneditableJavaStaticCodeBlockBuilder builder(){
		return new UneditableJavaStaticCodeBlockBuilder();
	}
}
