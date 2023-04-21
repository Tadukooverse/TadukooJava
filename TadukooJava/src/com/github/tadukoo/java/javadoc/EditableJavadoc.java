package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Represents a {@link Javadoc} that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class EditableJavadoc extends Javadoc{
	
	/**
	 * A builder used to make an {@link EditableJavadoc}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavadocBuilder
	 */
	public static class EditableJavadocBuilder extends JavadocBuilder<EditableJavadoc>{
		
		/** Not allowed to instantiate outside EditableJavadoc */
		private EditableJavadocBuilder(){ }
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavadoc constructJavadoc(){
			return new EditableJavadoc(condensed, content, author, version, since, params, returnVal);
		}
	}
	
	/**
	 * Constructs a new {@link EditableJavadoc} using the given parameters
	 *
	 * @param condensed Whether the {@link Javadoc} is condensed or not
	 * @param content The content of the {@link Javadoc}
	 * @param author The author of the {@link Javadoc}
	 * @param version The version for the {@link Javadoc}
	 * @param since The "since" value for the {@link Javadoc}
	 * @param params The parameters in the {@link Javadoc}
	 * @param returnVal The return string in the {@link Javadoc}
	 */
	private EditableJavadoc(
			boolean condensed, List<String> content, String author, String version, String since,
			List<Pair<String, String>> params, String returnVal){
		super(true, condensed, content, author, version, since, params, returnVal);
	}
	
	/**
	 * @return A new {@link EditableJavadocBuilder} to use to build a new {@link EditableJavadoc}
	 */
	public static EditableJavadocBuilder builder(){
		return new EditableJavadocBuilder();
	}
	
	/**
	 * @param condensed Whether the {@link Javadoc} is condensed or not
	 */
	public void setCondensed(boolean condensed){
		this.condensed = condensed;
	}
	
	/**
	 * @param content Content to be added to the {@link Javadoc}
	 */
	public void addContent(String content){
		this.content.add(content);
	}
	
	/**
	 * @param content Content to be added to the {@link Javadoc}
	 */
	public void addContent(List<String> content){
		this.content.addAll(content);
	}
	
	/**
	 * @param content The content of the {@link Javadoc}
	 */
	public void setContent(List<String> content){
		this.content = content;
	}
	
	/**
	 * @param author The author of the {@link Javadoc}
	 */
	public void setAuthor(String author){
		this.author = author;
	}
	
	/**
	 * @param version The version for the {@link Javadoc}
	 */
	public void setVersion(String version){
		this.version = version;
	}
	
	/**
	 * @param since The "since" value for the {@link Javadoc}
	 */
	public void setSince(String since){
		this.since = since;
	}
	
	/**
	 * @param name The name of the parameter
	 * @param description The description of the parameter
	 */
	public void addParam(String name, String description){
		params.add(Pair.of(name, description));
	}
	
	/**
	 * @param param A parameter as a pair of the name and javadoc text for it
	 */
	public void addParam(Pair<String, String> param){
		params.add(param);
	}
	
	/**
	 * @param params The parameters to add to the {@link Javadoc}
	 */
	public void addParams(List<Pair<String, String>> params){
		this.params.addAll(params);
	}
	
	/**
	 * @param params The parameters in the {@link Javadoc}
	 */
	public void setParams(List<Pair<String, String>> params){
		this.params = params;
	}
	
	/**
	 * @param returnVal The return string in the {@link Javadoc}
	 */
	public void setReturnVal(String returnVal){
		this.returnVal = returnVal;
	}
}
