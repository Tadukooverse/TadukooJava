package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser to use for parsing {@link JavaAnnotation annotations in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaAnnotationParser extends AbstractJavaParser{
	
	/**
	 * A {@link Pattern} used to detect an {@link JavaAnnotation annotation}
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Description</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Annotation Name</td>
	 *         <td>"{@code Test}" from "{@code @Test(type = String.class)}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Annotation Parameters (parentheses excluded)</td>
	 *         <td>"{@code type = String.class}" from "{@code @Test(type = String.class)}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern ANNOTATION_PATTERN = Pattern.compile(
			"\\s*@\\s*([^\\s(]*)(?:\\s*\\((.*)\\)\\s*)?\\s*", Pattern.DOTALL);
	/**
	 * A {@link Pattern} used to detect the parameters of an {@link JavaAnnotation annotation}.
	 * This is used for the parameters inside the annotation (in a loop using {@link Matcher#find()})
	 * once the parentheses are removed
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Description</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Parameter Name</td>
	 *         <td>"{@code type}" and "{@code defaultValue}" from "{@code type = String.class, defaultValue = ""}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Parameter Value</td>
	 *         <td>"{@code String.class}" and "{@code ""}" from "{@code type = String.class, defaultValue = ""}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern ANNOTATION_PARAMETER_PATTERN = Pattern.compile("\\s*([^=,]*)\\s*=\\s*([^,]*),?\\s*");
	
	/** Not allowed to instantiate {@link JavaAnnotationParser} */
	private JavaAnnotationParser(){ }
	
	/**
	 * Parses an {@link JavaAnnotation annotation} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaAnnotation annotation}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseAnnotation(List<String> tokens, int startToken) throws JavaParsingException{
		// Ensure the first token starts with an @
		if(!tokens.get(startToken).startsWith(ANNOTATION_START_TOKEN)){
			throw new JavaParsingException(JavaCodeTypes.ANNOTATION,
					"First token of annotation must start with '" + ANNOTATION_START_TOKEN + "'");
		}
		
		// Start building the full annotation String - we're using regexes here
		String firstToken = tokens.get(startToken);
		StringBuilder fullAnnotation = new StringBuilder(firstToken);
		
		// Skip any whitespace
		int currentToken = startToken+1;
		while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
			currentToken++;
		}
		
		// Add the next token if we were missing the annotation name
		if(StringUtil.equals(firstToken, ANNOTATION_START_TOKEN)){
			String nextToken = tokens.get(currentToken);
			fullAnnotation.append(nextToken);
			currentToken++;
		}
		
		// Skip any whitespace
		while(currentToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(currentToken)).matches()){
			currentToken++;
		}
		
		// Only way to continue now is if we have the parameter open token next
		boolean parametersOpen = false, parametersDone = false;
		if(currentToken < tokens.size() && StringUtil.equals(tokens.get(currentToken), PARAMETER_OPEN_TOKEN)){
			fullAnnotation.append(PARAMETER_OPEN_TOKEN);
			parametersOpen = true;
			currentToken++;
		}
		
		// Keep going until we complete the parameters
		while(parametersOpen && !parametersDone && currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			fullAnnotation.append(token);
			if(StringUtil.equals(token, PARAMETER_CLOSE_TOKEN)){
				parametersDone = true;
			}
			currentToken++;
		}
		
		// If we opened and never finished parameters, that's an issue
		if(parametersOpen && !parametersDone){
			throw new JavaParsingException(JavaCodeTypes.ANNOTATION, "Didn't find end of parameters");
		}
		
		// Parse the annotation using the regex method
		JavaAnnotation annotation = parseAnnotation(fullAnnotation.toString());
		
		return new ParsingPojo(currentToken, annotation);
	}
	
	/**
	 * Parses the given text into an {@link JavaAnnotation annotation} if possible, or returns null
	 *
	 * @param content The text to be parsed into a {@link JavaAnnotation annotation}
	 * @return The {@link JavaAnnotation annotation} parsed from the text, or {@code null} if it can't be parsed
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static JavaAnnotation parseAnnotation(String content) throws JavaParsingException{
		Matcher annotationMatcher = ANNOTATION_PATTERN.matcher(content);
		if(annotationMatcher.matches()){
			JavaAnnotationBuilder<EditableJavaAnnotation> builder = EditableJavaAnnotation.builder();
			
			// Grab the name and add it to the builder
			String annotationName = StringUtil.trim(annotationMatcher.group(1));
			builder.name(annotationName);
			
			// Grab and parse the parameters
			String annotationParameters = StringUtil.trim(annotationMatcher.group(2));
			if(StringUtil.isNotBlank(annotationParameters)){
				Matcher parameterMatcher = ANNOTATION_PARAMETER_PATTERN.matcher(annotationParameters);
				boolean firstFind = parameterMatcher.find();
				if(!firstFind){
					builder.parameter("value", annotationParameters);
				}else{
					do{
						String parameterName = StringUtil.trim(parameterMatcher.group(1));
						String parameterValue = StringUtil.trim(parameterMatcher.group(2));
						builder.parameter(parameterName, parameterValue);
					}while(parameterMatcher.find());
				}
			}
			
			return builder.build();
		}else{
			throw new JavaParsingException(JavaCodeTypes.ANNOTATION, "Failed to parse annotation");
		}
	}
}
