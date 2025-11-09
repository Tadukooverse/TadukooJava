package com.github.tadukoo.java.code.staticcodeblock;

import java.util.List;

/**
 * Represents a static code block in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public class EditableJavaStaticCodeBlock extends JavaStaticCodeBlock{
	
	/**
	 * A builder used to make an {@link EditableJavaStaticCodeBlock}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 * @see JavaStaticCodeBlockBuilder
	 */
	public static class EditableJavaStaticCodeBlockBuilder extends JavaStaticCodeBlockBuilder<EditableJavaStaticCodeBlock>{
		
		/** Not allowed to instantiate outside {@link EditableJavaStaticCodeBlock} */
		private EditableJavaStaticCodeBlockBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaStaticCodeBlock constructStaticCodeBlock(){
			return new EditableJavaStaticCodeBlock(lines);
		}
	}
	
	/**
	 * Constructs a new Java Static Code Block with the given parameters
	 *
	 * @param lines The actual lines of code in the static code block
	 */
	private EditableJavaStaticCodeBlock(List<String> lines){
		super(true, lines);
	}
	
	/**
	 * @return A new {@link EditableJavaStaticCodeBlockBuilder} to use to build a new {@link EditableJavaStaticCodeBlock}
	 */
	public static EditableJavaStaticCodeBlockBuilder builder(){
		return new EditableJavaStaticCodeBlockBuilder();
	}
	
	/**
	 * @param line An actual line of code to add to the static code block
	 */
	public void addLine(String line){
		lines.add(line);
	}
	
	/**
	 * @param lines Actual lines of code to add to the static code block
	 */
	public void addLines(List<String> lines){
		this.lines.addAll(lines);
	}
	
	/**
	 * @param lines The actual lines of code in the static code block
	 */
	public void setLines(List<String> lines){
		this.lines = lines;
	}
}
