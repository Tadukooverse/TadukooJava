package com.github.tadukoo.java.code.staticcodeblock;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.List;

/**
 * Java Static Code Block represents a static code block in a Java class, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 */
public abstract class JavaStaticCodeBlock implements JavaCodeType{
	
	/** Whether the static code block is editable or not */
	private final boolean editable;
	/** The actual content of the static code block */
	protected List<String> lines;
	
	/**
	 * Constructs a new Static Code Block with the given parameters.
	 *
	 * @param editable Whether the static code block is editable or not
	 * @param lines The actual content of the static code block
	 */
	protected JavaStaticCodeBlock(boolean editable, List<String> lines){
		this.editable = editable;
		this.lines = lines;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.STATIC_CODE_BLOCK;
	}
	
	/**
	 * @return Whether the static code block is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The actual content of the static code block
	 */
	public List<String> getLines(){
		return lines;
	}
	
	/**
	 * @return This Java Static Code Block as a String, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		// Start with the static modifier and open the block
		StringBuilder builder = new StringBuilder(STATIC_MODIFIER).append(BLOCK_OPEN_TOKEN);
		
		// If we have lines, add them
		if(ListUtil.isNotBlank(lines)){
			for(String line: lines){
				builder.append("\n\t").append(line);
			}
			// Newline for the block close token to be put on
			builder.append("\n");
		}else{
			// Have an empty space for the block if no content is present
			builder.append(" ");
		}
		
		// End the block
		builder.append(BLOCK_CLOSE_TOKEN);
		
		return builder.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherStaticCodeBlock){
		if(otherStaticCodeBlock instanceof JavaStaticCodeBlock staticCodeBlock){
			return StringUtil.equals(this.toString(), staticCodeBlock.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start with className.builder()
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName())
				.append(".builder()");
		
		// Add lines if we have them
		if(ListUtil.isNotBlank(lines)){
			for(String line: lines){
				builder.append(NEWLINE_WITH_2_TABS).append(".line(\"").append(line).append("\")");
			}
		}
		
		// Finish with .build()
		builder.append(NEWLINE_WITH_2_TABS).append(".build()");
		
		return builder.toString();
	}
}
