package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A builder class to build a {@link Javadoc}. It has the following parameters:
 *
 * <table>
 *     <caption>Javadoc Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>condensed</td>
 *         <td>Whether the {@link Javadoc} is condensed or not</td>
 *         <td>Defaults to false</td>
 *     </tr>
 *     <tr>
 *         <td>content</td>
 *         <td>The content of the {@link Javadoc}</td>
 *         <td>Defaults to an empty List</td>
 *     </tr>
 *     <tr>
 *         <td>author</td>
 *         <td>The author of the {@link Javadoc}</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>version</td>
 *         <td>The version of the {@link Javadoc}</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>since</td>
 *         <td>The "since" value of the {@link Javadoc}</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>params</td>
 *         <td>The parameters in the {@link Javadoc}</td>
 *         <td>Defaults to an empty list</td>
 *     </tr>
 *     <tr>
 *         <td>returnVal</td>
 *         <td>The return string for the {@link Javadoc}</td>
 *         <td>Defaults to null</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.3.3 (within Javadoc), Alpha v.0.4 (as its own class)
 */
public abstract class JavadocBuilder<JavadocType extends Javadoc>{
	/** Whether the {@link Javadoc} is condensed or not */
	protected boolean condensed = false;
	/** The content of the {@link Javadoc} */
	protected List<String> content = new ArrayList<>();
	/** The author of the {@link Javadoc} */
	protected String author = null;
	/** The version for the {@link Javadoc} */
	protected String version = null;
	/** The "since" value for the {@link Javadoc} */
	protected String since = null;
	/** The parameters in the {@link Javadoc} */
	protected List<Pair<String, String>> params = new ArrayList<>();
	/** The return string for the {@link Javadoc} */
	protected String returnVal = null;
	
	/**
	 * Constructs a new JavadocBuilder
	 */
	protected JavadocBuilder(){ }
	
	/**
	 * @param condensed Whether the {@link Javadoc} is condensed or not
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> condensed(boolean condensed){
		this.condensed = condensed;
		return this;
	}
	
	/**
	 * Sets that the {@link Javadoc} is condensed
	 *
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> condensed(){
		this.condensed = true;
		return this;
	}
	
	/**
	 * @param content The content of the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> content(List<String> content){
		this.content = content;
		return this;
	}
	
	/**
	 * @param content Content for the {@link Javadoc} (to add to the List)
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> content(String content){
		this.content.add(content);
		return this;
	}
	
	/**
	 * @param author The author of the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> author(String author){
		this.author = author;
		return this;
	}
	
	/**
	 * @param version The version for the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> version(String version){
		this.version = version;
		return this;
	}
	
	/**
	 * @param since The "since" value for the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> since(String since){
		this.since = since;
		return this;
	}
	
	/**
	 * @param params The parameters in the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> params(List<Pair<String, String>> params){
		this.params = params;
		return this;
	}
	
	/**
	 * @param param A parameter in the {@link Javadoc} to add to the list
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> param(Pair<String, String> param){
		this.params.add(param);
		return this;
	}
	
	/**
	 * @param name The name of the parameter in the {@link Javadoc} to add to the list
	 * @param description The description of the parameter in the {@link Javadoc} to add to the list
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> param(String name, String description){
		this.params.add(Pair.of(name, description));
		return this;
	}
	
	/**
	 * @param returnVal The return string for the {@link Javadoc}
	 * @return this, to continue building
	 */
	public JavadocBuilder<JavadocType> returnVal(String returnVal){
		this.returnVal = returnVal;
		return this;
	}
	
	/**
	 * Builds a new {@link Javadoc} using the set parameters
	 *
	 * @return The newly built {@link Javadoc}
	 */
	public JavadocType build(){
		return constructJavadoc();
	}
	
	/**
	 * Constructs a new {@link Javadoc} using the set parameters
	 *
	 * @return The newly built {@link Javadoc}
	 */
	protected abstract JavadocType constructJavadoc();
}
