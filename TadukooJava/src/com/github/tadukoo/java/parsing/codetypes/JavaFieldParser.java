package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

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
			"\\s*" + MODIFIERS_REGEX + "(\\S*)\\s*(\\S*?)\\s*;?\\s*");
	
	/** Not allowed to instantiate {@link JavaFieldParser} */
	private JavaFieldParser(){ }
	
	/**
	 * Parses a {@link JavaField field} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaField field}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseField(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of any errors
		List<String> errors = new ArrayList<>();
		
		StringBuilder field = new StringBuilder();
		// Iterate over tokens until we find the semicolon
		int currentToken = startToken;
		boolean foundSemicolon = false;
		for(; currentToken < tokens.size() && !foundSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			// Skip newline
			if(StringUtil.equals(token, "\n")){
				continue;
			}
			
			if(!field.isEmpty()){
				field.append(' ');
			}
			field.append(token);
			
			// Check if we got the semicolon
			if(token.endsWith(SEMICOLON)){
				foundSemicolon = true;
			}
		}
		
		// If we don't have a semicolon, it's a problem
		if(!foundSemicolon){
			errors.add("Failed to find semicolon at end of field");
		}
		
		// If we don't get a field, it's a problem
		JavaField javaField = parseField(field.toString());
		if(javaField == null){
			errors.add("Failed to parse a field");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.FIELD, StringUtil.buildStringWithNewLines(errors));
		}
		
		return new ParsingPojo(currentToken, javaField);
	}
	
	/**
	 * Parses a Java Field (not counting any Javadoc before it, just the field itself)
	 *
	 * @param content The text to be parsed into a {@link JavaField field)}
	 * @return The parsed {@link JavaField field}, or null if we don't have a field
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static JavaField parseField(String content) throws JavaParsingException{
		// Check if field ends with semicolon for failure
		if(!StringUtil.trim(content).endsWith(SEMICOLON)){
			throw new JavaParsingException(JavaCodeTypes.FIELD, "Failed to find semicolon at end of field");
		}
		
		// Find the equals signs in the field
		int equalsIndex = content.indexOf(ASSIGNMENT_OPERATOR_TOKEN);
		
		String value = null;
		String firstPart = content;
		
		// Anything after equals is the value (if we even have equals)
		if(equalsIndex != -1){
			value = StringUtil.trim(content.substring(equalsIndex + 1));
			
			// remove semicolon from value if it exists
			if(value.endsWith(SEMICOLON)){
				value = StringUtil.trim(value.substring(0, value.length()-1));
			}
			
			// Before equals is the modifiers, type, and name
			firstPart = content.substring(0, equalsIndex);
		}
		
		// Handle matching for the first part of the string (modifiers, type, name)
		Matcher matcher = FIELD_START_PATTERN.matcher(firstPart);
		if(matcher.matches()){
			Visibility visibility = Visibility.fromToken(StringUtil.trim(matcher.group(1)));
			boolean isStatic = StringUtil.isNotBlank(matcher.group(2));
			boolean isFinal = StringUtil.isNotBlank(matcher.group(3));
			String type = StringUtil.trim(matcher.group(4));
			String name = StringUtil.trim(matcher.group(5));
			
			return EditableJavaField.builder()
					.visibility(visibility)
					.isStatic(isStatic).isFinal(isFinal)
					.type(type).name(name)
					.value(value)
					.build();
		}
		
		return null;
	}
}
