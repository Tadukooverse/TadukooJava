package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used for parsing {@link Javadoc Javadocs}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavadocParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavadocParser} */
	private JavadocParser(){ }
	
	/**
	 * Parses a {@link Javadoc} from the given content String
	 *
	 * @param content The String of content to parse into a {@link Javadoc}
	 * @return The {@link Javadoc} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static Javadoc parseJavadoc(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Skip any leading newlines
		int startToken = skipLeadingNewlines(tokens);
		
		// Send the tokens to the main parsing method to get a result
		ParsingPojo result = parseJavadoc(tokens, startToken);
		
		// Make sure we reached the end of the tokens
		verifyEndOfTokens(tokens, result, JavaCodeTypes.JAVADOC);
		
		// Return the Javadoc that was parsed
		return (Javadoc) result.parsedType();
	}
	
	/**
	 * Parses a {@link Javadoc} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link Javadoc}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseJavadoc(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token starts with /**
		if(!tokens.get(startToken).startsWith(JAVADOC_START_TOKEN)){
			errors.add("First token of Javadoc must start with '" + JAVADOC_START_TOKEN + "'");
		}
		
		// Determine condensed
		boolean condensed = StringUtil.notEquals(tokens.get(startToken), JAVADOC_START_TOKEN) ||
				StringUtil.notEquals(tokens.get(startToken+1), "\n");
		
		// Parsing in-progress content
		String annotation = null;
		StringBuilder currentContent = new StringBuilder();
		
		// Parsing complete content
		List<String> content = new ArrayList<>();
		String author = null, version = null, since = null, returnVal = null;
		List<Pair<String, String>> parameters = new ArrayList<>();
		
		// Error flags
		boolean multipleAuthor = false, multipleVersion = false, multipleSince = false, multipleReturn = false;
		
		// Parsing flags/token
		boolean justStarting = true;
		boolean justHadNewline = true;
		int currentToken = startToken;
		boolean annotationDone = false;
		for(; currentToken < tokens.size() && !annotationDone; currentToken++){
			String token = tokens.get(currentToken);
			
			// If we have the close token, we're done
			if(StringUtil.equals(token, MULTI_LINE_COMMENT_CLOSE_TOKEN)){
				annotationDone = true;
				continue;
			}
			
			// Handle start
			if(token.startsWith(JAVADOC_START_TOKEN) && justStarting){
				if(StringUtil.notEquals(token, JAVADOC_START_TOKEN)){
					token = StringUtil.trim(token.substring(JAVADOC_START_TOKEN.length()));
					if(token.startsWith(ANNOTATION_START_TOKEN)){
						annotation = token.substring(ANNOTATION_START_TOKEN.length());
					}else{
						currentContent.append(token);
					}
					justHadNewline = false;
				}
				justStarting = false;
				continue;
			}
			
			// Handle annotations we look for
			if(justHadNewline){
				if(token.startsWith(ANNOTATION_START_TOKEN)){
					annotation = token.substring(ANNOTATION_START_TOKEN.length());
				}else if(StringUtil.equalsAny(token, "\n", JAVADOC_LINE_TOKEN)){
					continue;
				}else{
					currentContent.append(token);
				}
				justHadNewline = false;
			}else{
				if(StringUtil.equals(token, "\n")){
					// For newline, we finish any current content / annotations
					if(annotation != null){
						switch(annotation){
							case JAVADOC_AUTHOR_TOKEN -> {
								if(StringUtil.isNotBlank(author)){
									multipleAuthor = true;
								}
								author = currentContent.toString();
							}
							case JAVADOC_VERSION_TOKEN -> {
								if(StringUtil.isNotBlank(version)){
									multipleVersion = true;
								}
								version = currentContent.toString();
							}
							case JAVADOC_SINCE_TOKEN -> {
								if(StringUtil.isNotBlank(since)){
									multipleSince = true;
								}
								since = currentContent.toString();
							}
							case JAVADOC_PARAM_TOKEN -> {
								String paramString = currentContent.toString();
								String paramName = paramString.split("\\s+")[0];
								parameters.add(Pair.of(paramName, StringUtil.trim(
										paramString.substring(paramName.length()))));
							}
							case JAVADOC_RETURN_TOKEN -> {
								if(StringUtil.isNotBlank(returnVal)){
									multipleReturn = true;
								}
								returnVal = currentContent.toString();
							}
							default -> content.add(ANNOTATION_START_TOKEN + annotation + " " + currentContent);
						}
						annotation = null;
					}else{
						content.add(currentContent.toString());
					}
					currentContent = new StringBuilder();
					justHadNewline = true;
				}else{
					// If the currentContent is empty, quit now
					if(currentContent.isEmpty()){
						currentContent.append(token);
					}else{
						// If current content is not empty, check if we should add a space before the current token
						boolean notParameterStart = currentContent.charAt(currentContent.length() - 1) !=
								PARAMETER_OPEN_TOKEN.charAt(0);
						boolean notParameterEnd = !token.startsWith(PARAMETER_CLOSE_TOKEN);
						boolean notInlineAnnotationStart = !currentContent.toString().endsWith(BLOCK_OPEN_TOKEN);
						boolean notInlineAnnotationEnd = !token.endsWith(BLOCK_CLOSE_TOKEN);
						if(notParameterStart && notParameterEnd && notInlineAnnotationStart && notInlineAnnotationEnd){
							currentContent.append(' ');
						}
						currentContent.append(token);
					}
				}
			}
		}
		
		// Handle any dangling stuff
		if(annotation != null){
			switch(annotation){
				case JAVADOC_AUTHOR_TOKEN -> {
					if(StringUtil.isNotBlank(author)){
						multipleAuthor = true;
					}
					author = currentContent.toString();
				}
				case JAVADOC_VERSION_TOKEN -> {
					if(StringUtil.isNotBlank(version)){
						multipleVersion = true;
					}
					version = currentContent.toString();
				}
				case JAVADOC_SINCE_TOKEN -> {
					if(StringUtil.isNotBlank(since)){
						multipleSince = true;
					}
					since = currentContent.toString();
				}
				case JAVADOC_PARAM_TOKEN -> {
					String paramString = currentContent.toString();
					String paramName = paramString.split("\\s+")[0];
					parameters.add(Pair.of(paramName, StringUtil.trim(
							paramString.substring(paramName.length()))));
				}
				case JAVADOC_RETURN_TOKEN -> {
					if(StringUtil.isNotBlank(returnVal)){
						multipleReturn = true;
					}
					returnVal = currentContent.toString();
				}
				default -> content.add(ANNOTATION_START_TOKEN + annotation + " " + currentContent);
			}
		}else if(!currentContent.isEmpty()){
			content.add(currentContent.toString());
		}
		
		// Handle error flags
		if(multipleAuthor){
			errors.add("Found multiple author strings");
		}
		if(multipleVersion){
			errors.add("Found multiple version strings");
		}
		if(multipleSince){
			errors.add("Found multiple since strings");
		}
		if(multipleReturn){
			errors.add("Found multiple return strings");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.JAVADOC, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build and return the Javadoc
		return new ParsingPojo(currentToken, EditableJavadoc.builder()
				.condensed(condensed)
				.content(content)
				.author(author)
				.version(version)
				.since(since)
				.params(parameters)
				.returnVal(returnVal)
				.build());
	}
}
