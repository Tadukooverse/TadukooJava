package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Javadoc represents a Javadoc in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.3.3 (as old version that is now more like UneditableJavadoc), Alpha v.0.4 (as newer version)
 */
public abstract class Javadoc implements JavaCodeType{
	
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
	/** The throws info in the {@link Javadoc} */
	protected List<Pair<String, String>> throwsInfos;
	
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
	 * @param throwsInfos The throws info in the {@link Javadoc}
	 */
	protected Javadoc(
			boolean editable, boolean condensed, List<String> content, String author, String version, String since,
			List<Pair<String, String>> params, String returnVal, List<Pair<String, String>> throwsInfos){
		this.editable = editable;
		this.condensed = condensed;
		this.content = content;
		this.author = author;
		this.version = version;
		this.since = since;
		this.params = params;
		this.returnVal = returnVal;
		this.throwsInfos = throwsInfos;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.JAVADOC;
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
	 * @return The throws info for the {@link Javadoc}
	 */
	public List<Pair<String, String>> getThrowsInfos(){
		return throwsInfos;
	}
	
	/**
	 * @return The actual Javadoc text this class represents
	 */
	@Override
	public String toString(){
		// Start with the opening
		StringBuilder doc = new StringBuilder(JAVADOC_START_TOKEN);
		
		// Check what we have
		boolean haveContent = ListUtil.isNotBlank(content);
		boolean haveInfoAnnotations = StringUtil.anyNotBlank(author, version, since);
		boolean haveCodeAnnotations = ListUtil.isNotBlank(params) || StringUtil.isNotBlank(returnVal) ||
				ListUtil.isNotBlank(throwsInfos);
		boolean prevContent = false;
		
		// If not condensed, go to the next line if we have content or annotations coming up
		if(!condensed && (haveContent || haveInfoAnnotations || haveCodeAnnotations)){
			doc.append("\n ").append(JAVADOC_LINE_TOKEN);
		}
		
		// Add the content (if we have it)
		if(haveContent){
			for(String line: content){
				doc.append(" ").append(line).append("\n ").append(JAVADOC_LINE_TOKEN);
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
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.append(' ').append(ANNOTATION_START_TOKEN).append(JAVADOC_AUTHOR_TOKEN).append(' ').append(author);
			prevContent = true;
		}
		
		// Add the version (if we have it)
		if(StringUtil.isNotBlank(version)){
			if(prevContent){
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.append(' ').append(ANNOTATION_START_TOKEN).append(JAVADOC_VERSION_TOKEN).append(' ').append(version);
			prevContent = true;
		}
		
		// Add the since (if we have it)
		if(StringUtil.isNotBlank(since)){
			if(prevContent){
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.append(' ').append(ANNOTATION_START_TOKEN).append(JAVADOC_SINCE_TOKEN).append(' ').append(since);
			prevContent = true;
		}
		
		// Add extra line
		if(haveInfoAnnotations && haveCodeAnnotations){
			doc.append("\n ").append(JAVADOC_LINE_TOKEN).append(' ');
		}
		
		// Add the parameters
		if(ListUtil.isNotBlank(params)){
			if(prevContent){
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			for(Pair<String, String> param: params){
				doc.append(' ').append(ANNOTATION_START_TOKEN).append(JAVADOC_PARAM_TOKEN).append(' ')
						.append(param.getLeft()).append(' ').append(param.getRight())
						.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.delete(doc.length()-3, doc.length());
			prevContent = true;
		}
		
		// Add the return value
		if(StringUtil.isNotBlank(returnVal)){
			if(prevContent){
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.append(' ').append(ANNOTATION_START_TOKEN).append(JAVADOC_RETURN_TOKEN).append(' ').append(returnVal);
			prevContent = true;
		}
		
		// Add the throws info
		if(ListUtil.isNotBlank(throwsInfos)){
			if(prevContent){
				doc.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			for(Pair<String, String> throwsInfo: throwsInfos){
				doc.append(' ').append(ANNOTATION_START_TOKEN).append(THROWS_TOKEN).append(' ')
						.append(throwsInfo.getLeft()).append(' ').append(throwsInfo.getRight())
						.append("\n ").append(JAVADOC_LINE_TOKEN);
			}
			doc.delete(doc.length()-3, doc.length());
		}
		
		// If not condensed, go to the next line for the closing
		if(!condensed){
			doc.append("\n");
		}
		
		// End with the closing
		doc.append(' ').append(MULTI_LINE_COMMENT_CLOSE_TOKEN);
		
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
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start builder
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Set condensed if present
		if(condensed){
			codeString.append(NEWLINE_WITH_2_TABS).append(".condensed()");
		}
		
		// Set content if present
		if(ListUtil.isNotBlank(content)){
			for(String line: content){
				codeString.append(NEWLINE_WITH_2_TABS).append(".content(\"").append(escapeQuotes(line)).append("\")");
			}
		}
		
		// Set author if present
		if(StringUtil.isNotBlank(author)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".author(\"").append(escapeQuotes(author)).append("\")");
		}
		
		// Set version if present
		if(StringUtil.isNotBlank(version)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".version(\"").append(escapeQuotes(version)).append("\")");
		}
		
		// Set since if present
		if(StringUtil.isNotBlank(since)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".since(\"").append(escapeQuotes(since)).append("\")");
		}
		
		// Set params if present
		if(ListUtil.isNotBlank(params)){
			for(Pair<String, String> param: params){
				codeString.append(NEWLINE_WITH_2_TABS).append(".param(\"")
						.append(escapeQuotes(param.getLeft())).append("\", \"")
						.append(escapeQuotes(param.getRight())).append("\")");
			}
		}
		
		// Set return if present
		if(StringUtil.isNotBlank(returnVal)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".returnVal(\"").append(escapeQuotes(returnVal)).append("\")");
		}
		
		// Set throws info if present
		if(ListUtil.isNotBlank(throwsInfos)){
			for(Pair<String, String> throwsInfo: throwsInfos){
				codeString.append(NEWLINE_WITH_2_TABS).append(".throwsInfo(\"")
						.append(escapeQuotes(throwsInfo.getLeft())).append("\", \"")
						.append(escapeQuotes(throwsInfo.getRight())).append("\")");
			}
		}
		
		// Finish building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
