package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.java.parsing.comment.JavadocParser;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction2;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser to use for parsing {@link JavaField fields in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaFieldParser extends AbstractJavaParser{
	
	/**
	 * A {@link Pattern} used for parsing a {@link JavaField field}.
	 * This is used for the field preceding the equals sign in a field
	 * <table>
	 *     <caption>Groups</caption>
	 *     <tr>
	 *         <th>#</th>
	 *         <th>Group</th>
	 *         <th>Example</th>
	 *     </tr>
	 *     <tr>
	 *         <td>1</td>
	 *         <td>Field {@link Visibility}</td>
	 *         <td>"{@code public}" from "{@code public String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>2</td>
	 *         <td>Field Static Modifier (or empty)</td>
	 *         <td>"{@code static}" from "{@code public static String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>3</td>
	 *         <td>Field Final Modifier (or empty)</td>
	 *         <td>"{@code final}" from "{@code public final String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>4</td>
	 *         <td>Field Type</td>
	 *         <td>"{@code String}" from "{@code public String test}"</td>
	 *     </tr>
	 *     <tr>
	 *         <td>5</td>
	 *         <td>Field Name</td>
	 *         <td>"{@code test}" from "{@code public String test}"</td>
	 *     </tr>
	 * </table>
	 */
	private static final Pattern FIELD_START_PATTERN = Pattern.compile(
			"\\s*" + MODIFIERS_REGEX + "(" + TYPE_REGEX + ")\\s*([^\\s;]+)?\\s*;?\\s*");
	
	/** Not allowed to instantiate {@link JavaFieldParser} */
	private JavaFieldParser(){ }
	
	/**
	 * Parses a {@link JavaField field} from the given content String
	 *
	 * @param content The String of content to parse into a {@link JavaField field}
	 * @return The {@link JavaField field} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaField parseField(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Iterate over the tokens to parse stuff - we could get Javadocs, Annotations, and the field itself
		int currentToken = 0;
		List<JavaCodeType> types = new ArrayList<>();
		while(currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			
			ThrowingFunction2<List<String>, Integer, ParsingPojo, JavaParsingException> parseMethod;
			
			if(WHITESPACE_MATCHER.reset(token).matches()){
				// Skip whitespace
				currentToken++;
				continue;
			}else if(token.startsWith(JAVADOC_START_TOKEN)){
				// Parse a javadoc
				parseMethod = JavadocParser::parseJavadoc;
			}else if(token.startsWith(ANNOTATION_START_TOKEN)){
				// Parse an annotation
				parseMethod = JavaAnnotationParser::parseAnnotation;
			}else{
				// Assume it's a field
				parseMethod = JavaFieldParser::parseField;
			}
			
			// Use the parse method and handle its results
			ParsingPojo pojo = parseMethod.apply(tokens, currentToken);
			types.add(pojo.parsedType());
			currentToken = pojo.nextTokenIndex();
		}
		
		// Combine the types
		Javadoc doc = null;
		List<JavaAnnotation> annotations = new ArrayList<>();
		JavaField field = null;
		for(JavaCodeType type: types){
			if(type instanceof Javadoc javadoc){
				if(doc != null){
					// Can't have multiple Javadocs
					throw new JavaParsingException(JavaCodeTypes.FIELD, "Only one Javadoc allowed on a field!");
				}else if(field != null){
					// Can't have Javadoc after the field
					throw new JavaParsingException(JavaCodeTypes.FIELD, "Encountered Javadoc after field!");
				}
				doc = javadoc;
			}else if(type instanceof JavaAnnotation annotation){
				// Can't have annotations after the field
				if(field != null){
					throw new JavaParsingException(JavaCodeTypes.FIELD, "Encountered annotation after field!");
				}
				annotations.add(annotation);
			}else if(type instanceof EditableJavaField javaField){
				// Can't have multiple fields
				if(field != null){
					throw new JavaParsingException(JavaCodeTypes.FIELD, "Encountered multiple fields!");
				}
				// Set Javadoc if we have it
				if(doc != null){
					javaField.setJavadoc(doc);
				}
				// Set annotations if we have them
				if(ListUtil.isNotBlank(annotations)){
					javaField.setAnnotations(annotations);
				}
				field = javaField;
			}
		}
		
		// Error if we didn't find a field
		if(field == null){
			throw new JavaParsingException(JavaCodeTypes.FIELD, "Failed to parse an actual field!");
		}
		
		return field;
	}
	
	/**
	 * Parses a {@link JavaField field} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaField field}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseField(List<String> tokens, int startToken) throws JavaParsingException{
		StringBuilder field = new StringBuilder();
		// Iterate over tokens until we find the semicolon
		int currentToken = startToken;
		boolean foundSemicolon = false;
		for(; currentToken < tokens.size() && !foundSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			field.append(token);
			
			// Check if we got the semicolon
			if(token.endsWith(SEMICOLON)){
				foundSemicolon = true;
			}
		}
		
		// If we don't have a semicolon, it's a problem
		if(!foundSemicolon){
			throw new JavaParsingException(JavaCodeTypes.FIELD, "Failed to find semicolon at end of field");
		}
		
		// If we don't get a field, it's a problem
		JavaField javaField = parseJustField(field.toString());
		
		return new ParsingPojo(currentToken, javaField);
	}
	
	/**
	 * Parses a Java Field (not counting any Javadoc before it, just the field itself)
	 *
	 * @param content The text to be parsed into a {@link JavaField field)}
	 * @return The parsed {@link JavaField field}, or null if we don't have a field
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	private static JavaField parseJustField(String content) throws JavaParsingException{
		// Find the equals signs in the field
		int equalsIndex = content.indexOf(ASSIGNMENT_OPERATOR_TOKEN);
		
		String value = null;
		String firstPart = content;
		
		// Anything after equals is the value (if we even have equals)
		if(equalsIndex != -1){
			// Remove semicolon from the end and trim the value
			value = StringUtil.trim(content.substring(equalsIndex + 1, content.length() - 1));
			
			// Before equals is the modifiers, type, and name
			firstPart = content.substring(0, equalsIndex);
		}
		
		// Handle matching for the first part of the string (modifiers, type, name)
		Matcher matcher = FIELD_START_PATTERN.matcher(firstPart);
		if(matcher.matches()){
			Visibility visibility = Visibility.NONE;
			boolean isStatic = false, isFinal = false;
			for(int i = 1; i <= 3; i++){
				String token = matcher.group(i);
				if(StringUtil.isNotBlank(token)){
					switch(token){
						case PUBLIC_MODIFIER -> visibility = Visibility.PUBLIC;
						case PROTECTED_MODIFIER -> visibility = Visibility.PROTECTED;
						case PRIVATE_MODIFIER -> visibility = Visibility.PRIVATE;
						case ABSTRACT_MODIFIER -> throw new JavaParsingException(JavaCodeTypes.FIELD,
								"'" + ABSTRACT_MODIFIER + "' is not a valid modifier on field!");
						case STATIC_MODIFIER -> isStatic = true;
						case FINAL_MODIFIER -> isFinal = true;
					}
				}
			}
			String type = StringUtil.trim(matcher.group(4));
			String name = StringUtil.trim(matcher.group(11));
			
			if(value != null){
				value = value.replaceAll("\n\t", "\n")
						.replaceAll(System.lineSeparator(), "\n");
			}
			
			return EditableJavaField.builder()
					.visibility(visibility)
					.isStatic(isStatic).isFinal(isFinal)
					.type(type).name(name)
					.value(value)
					.build();
		}else{
			throw new JavaParsingException(JavaCodeTypes.FIELD, "Failed to parse a field");
		}
	}
}
