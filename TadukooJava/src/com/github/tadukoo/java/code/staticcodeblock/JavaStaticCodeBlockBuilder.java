package com.github.tadukoo.java.code.staticcodeblock;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Static Code Block Builder is used to build a new {@link JavaStaticCodeBlock}. It contains the following parameters:
 *
 * <table>
 *     <caption>Java Static Code Block Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>lines</td>
 *         <td>The actual lines of code in the static code block</td>
 *         <td>An empty list</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
*/
public abstract class JavaStaticCodeBlockBuilder<StaticCodeBlockType extends JavaStaticCodeBlock>{
	
	/** The actual lines of code in the static code block */
	protected List<String> lines = new ArrayList<>();
	
	/**
	 * Constructs a new {@link JavaStaticCodeBlockBuilder}
	 */
	protected JavaStaticCodeBlockBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaStaticCodeBlock static code block} to this builder
	 *
	 * @param staticCodeBlock The {@link JavaStaticCodeBlock static code block} to copy settings from
	 * @return this, to continue building
	 */
	public JavaStaticCodeBlockBuilder<StaticCodeBlockType> copy(JavaStaticCodeBlock staticCodeBlock){
		this.lines = staticCodeBlock.getLines();
		return this;
	}
	
	/**
	 * @param lines The actual lines of code in the static code block
	 * @return this, to continue building
	 */
	public JavaStaticCodeBlockBuilder<StaticCodeBlockType> lines(List<String> lines){
		this.lines = lines;
		return this;
	}
	
	/**
	 * @param line A single line of code in the static code block, to add to the list
	 * @return this, to continue building
	 */
	public JavaStaticCodeBlockBuilder<StaticCodeBlockType> line(String line){
		lines.add(line);
		return this;
	}
	
	/**
	 * Builds a new {@link JavaStaticCodeBlock}
	 *
	 * @return A newly built {@link JavaStaticCodeBlock}
	 */
	public StaticCodeBlockType build(){
		return constructStaticCodeBlock();
	}
	
	/**
	 * Constructs a new {@link JavaStaticCodeBlock} using the set parameters
	 *
	 * @return The newly created {@link JavaStaticCodeBlock}
	 */
	protected abstract StaticCodeBlockType constructStaticCodeBlock();
}
