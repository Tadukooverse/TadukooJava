package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Javadoc represents a Javadoc in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.3.3 (as old version that is now more like UneditableJavadoc), Alpha v.0.4 (as newer version)
 */
public abstract class Javadoc{
	
	/** Whether the {@link Javadoc} is editable or not */
	private final boolean editable;
	/** Whether the {@link Javadoc} is condensed or not */
	protected boolean condensed;
	/** The content of the {@link Javadoc} */
	protected List<String> content;
	/** The author of the {@link Javadoc} */
	protected String author;
	/** The version for the {@link Javadoc} */
	protected String version;
	/** The "since" value for the {@link Javadoc} */
	protected String since;
	/** The parameters in the {@link Javadoc} */
	protected List<Pair<String, String>> params;
	/** The return string in the {@link Javadoc} */
	protected String returnVal;
	
	/**
	 * Constructs a new {@link Javadoc} using the given parameters
	 *
	 * @param editable Whether the {@link Javadoc} is editable or not
	 * @param condensed Whether the {@link Javadoc} is condensed or not
	 * @param content The content of the {@link Javadoc}
	 * @param author The author of the {@link Javadoc}
	 * @param version The version for the {@link Javadoc}
	 * @param since The "since" value for the {@link Javadoc}
	 * @param params The parameters in the {@link Javadoc}
	 * @param returnVal The return string in the {@link Javadoc}
	 */
	protected Javadoc(
			boolean editable, boolean condensed, List<String> content, String author, String version, String since,
			List<Pair<String, String>> params, String returnVal){
		this.editable = editable;
		this.condensed = condensed;
		this.content = content;
		this.author = author;
		this.version = version;
		this.since = since;
		this.params = params;
		this.returnVal = returnVal;
	}
	
	/**
	 * @return Whether the {@link Javadoc} is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return Whether the {@link Javadoc} is condensed or not
	 */
	public boolean isCondensed(){
		return condensed;
	}
	
	/**
	 * @return The content of the {@link Javadoc}
	 */
	public List<String> getContent(){
		return content;
	}
	
	/**
	 * @return The author of the {@link Javadoc}
	 */
	public String getAuthor(){
		return author;
	}
	
	/**
	 * @return The version for the {@link Javadoc}
	 */
	public String getVersion(){
		return version;
	}
	
	/**
	 * @return The "since" value for the {@link Javadoc}
	 */
	public String getSince(){
		return since;
	}
	
	/**
	 * @return The parameters in the {@link Javadoc}
	 */
	public List<Pair<String, String>> getParams(){
		return params;
	}
	
	/**
	 * @return The return string for the {@link Javadoc}
	 */
	public String getReturnVal(){
		return returnVal;
	}
	
	/**
	 * @return The actual Javadoc text this class represents
	 */
	@Override
	public String toString(){
		// Start with the opening
		StringBuilder doc = new StringBuilder("/**");
		
		// Check what we have
		boolean haveContent = ListUtil.isNotBlank(content);
		boolean haveInfoAnnotations = StringUtil.anyNotBlank(author, version, since);
		boolean haveCodeAnnotations = ListUtil.isNotBlank(params) || StringUtil.isNotBlank(returnVal);
		boolean prevContent = false;
		
		// If not condensed, go to the next line if we have content or annotations coming up
		if(!condensed && (haveContent || haveInfoAnnotations || haveCodeAnnotations)){
			doc.append("\n *");
		}
		
		// Add the content (if we have it)
		if(haveContent){
			for(String line: content){
				doc.append(" ").append(line).append("\n *");
			}
			if(!haveInfoAnnotations  && !haveCodeAnnotations){
				doc.delete(doc.length()-3, doc.length());
			}else{
				doc.append(" ");
			}
			prevContent = true;
		}
		
		// Add the author (if we have it)
		if(StringUtil.isNotBlank(author)){
			if(prevContent){
				doc.append("\n *");
			}
			doc.append(" @author ").append(author);
			prevContent = true;
		}
		
		// Add the version (if we have it)
		if(StringUtil.isNotBlank(version)){
			if(prevContent){
				doc.append("\n *");
			}
			doc.append(" @version ").append(version);
			prevContent = true;
		}
		
		// Add the since (if we have it)
		if(StringUtil.isNotBlank(since)){
			if(prevContent){
				doc.append("\n *");
			}
			doc.append(" @since ").append(since);
			prevContent = true;
		}
		
		// Add extra line
		if(haveInfoAnnotations && haveCodeAnnotations){
			doc.append("\n * ");
		}
		
		// Add the parameters
		if(ListUtil.isNotBlank(params)){
			if(prevContent){
				doc.append("\n *");
			}
			for(Pair<String, String> param: params){
				doc.append(" @param ").append(param.getLeft()).append(" ").append(param.getRight()).append("\n *");
			}
			doc.delete(doc.length()-3, doc.length());
			prevContent = true;
		}
		
		// Add the return value
		if(StringUtil.isNotBlank(returnVal)){
			if(prevContent){
				doc.append("\n *");
			}
			doc.append(" @return ").append(returnVal);
		}
		
		// If not condensed, go to the next line for the closing
		if(!condensed){
			doc.append("\n");
		}
		
		// End with the closing
		doc.append(" */");
		
		return doc.toString();
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherJavadoc){
		if(otherJavadoc instanceof Javadoc doc){
			return StringUtil.equals(this.toString(), doc.toString());
		}else{
			return false;
		}
	}
}
