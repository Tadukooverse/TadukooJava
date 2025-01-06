package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.JavaTokens;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A base parser for Java parsing that contains any shared logic
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Beta v.0.5
 */
public abstract class AbstractJavaParser implements JavaTokens{
	
	/** A regular expression for a single modifier */
	private static final String SINGLE_MODIFIER_REGEX = "(?:(" + PUBLIC_MODIFIER + "|" + PROTECTED_MODIFIER + "|" +
			PRIVATE_MODIFIER + "|" + STATIC_MODIFIER + "|" + FINAL_MODIFIER + "|" + ABSTRACT_MODIFIER + ")\\s*)?";
	/** A regular expression used for all the modifiers */
	protected static final String MODIFIERS_REGEX = SINGLE_MODIFIER_REGEX + SINGLE_MODIFIER_REGEX + SINGLE_MODIFIER_REGEX;
	
	/** A regular expression used for tokens to match on when splitting tokens from a content String */
	protected static final String TOKEN_REGEX = "\n|\\(|\\)|\\{|}|<|>|=|,|[^\\S\n]+|[^\\s(){}=,]+";
	/** A matcher to use to find whitespace (usually to skip it) */
	protected static final Matcher WHITESPACE_MATCHER = Pattern.compile("\\s+").matcher("");
	
	/** A regular expression for a {@link JavaTypeParameter} */
	protected static final String TYPE_PARAMETER_REGEX = "\\s*([^\\s)]+)(?:\\s*extends\\s*([^\\s)]*))?\\s*";
	/** A {@link Pattern} to use for parsing {@link JavaTypeParameter type parameters} */
	protected static final Pattern TYPE_PARAMETER_PATTERN = Pattern.compile(TYPE_PARAMETER_REGEX);
	/** A regular expression for a {@link JavaType} */
	protected static final String TYPE_REGEX = "\\s*([^<.\\s)]+)(?:\\s*<((?:" +
			TYPE_PARAMETER_REGEX + ",)*" + TYPE_PARAMETER_PATTERN + ")>)?\\s*";
	/** A {@link Pattern} to use for parsing a {@link JavaType} */
	protected static final Pattern TYPE_PATTERN = Pattern.compile(TYPE_REGEX);
	/** A regular expression for a {@link JavaParameter} */
	protected static final String PARAMETER_REGEX = "(" + TYPE_REGEX + ")(?:\\s+|\\s*(\\.\\.\\.)\\s*)([^\\s)]*)\\s*";
	/** A {@link Pattern} to use for parsing a {@link JavaParameter} */
	protected static final Pattern PARAMETER_PATTERN = Pattern.compile(PARAMETER_REGEX);
	
	/** Not allowed to instantiate {@link AbstractJavaParser} */
	protected AbstractJavaParser(){ }
	
	/**
	 * Takes the given String content and splits it into a List of tokens to be parsed
	 *
	 * @param content The content to be split into tokens
	 * @return The List of tokens to be parsed
	 */
	protected static List<String> splitContentIntoTokens(String content){
		return StringUtil.parseListFromStringWithPattern(content, TOKEN_REGEX, false).stream()
				.filter(StringUtil::isNotBlank)
				.toList();
	}
	
	/**
	 * Determines the token index to start at for the given tokens List, skipping any leading newlines
	 *
	 * @param tokens The List of tokens to be parsed
	 * @return The token to start at (skipping leading newlines)
	 */
	protected static int skipLeadingWhitespace(List<String> tokens){
		int startToken = 0;
		while(WHITESPACE_MATCHER.reset(tokens.get(startToken)).matches()){
			startToken++;
		}
		return startToken;
	}
	
	/**
	 * Checks that we've made it to the end of the tokens during parsing (to verify we're really done).
	 * This handles any trailing newlines by ignoring them, but any other remaining tokens will cause
	 * a {@link JavaParsingException} to be thrown
	 *
	 * @param tokens The List of tokens being parsed
	 * @param result The {@link ParsingPojo} from what we've finished parsing
	 * @param type The {@link JavaCodeTypes type} being parsed
	 * @throws JavaParsingException If we're not at the end of the tokens
	 */
	protected static void verifyEndOfTokens(List<String> tokens, ParsingPojo result, JavaCodeTypes type)
			throws JavaParsingException{
		if(result.nextTokenIndex() != tokens.size()){
			// Check if the remaining stuff is just newlines (they don't matter)
			int lastTokenIndex = result.nextTokenIndex();
			while(lastTokenIndex < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(lastTokenIndex)).matches()){
				lastTokenIndex++;
			}
			if(lastTokenIndex != tokens.size()){
				throw new JavaParsingException(type, "Found extra content after the " + type.getStandardName() + "!");
			}
		}
	}
	
	/**
	 * Parse the tokens from {@code currentToken} onward to get a {@link JavaType} string, where we have all the
	 * type parameters in the String for it. We leave the parsing of that type to the caller, but return the
	 * next token index (after the type string) as well.
	 *
	 * @param tokens The List of tokens to parse a type string from
	 * @param currentToken The current token index to start at to get a type
	 * @return A Pair of the type string and the next token index
	 */
	protected static Pair<String, Integer> parseOutType(List<String> tokens, int currentToken){
		// Check that we're not already at end of tokens
		if(currentToken >= tokens.size()){
			return null;
		}
		
		// Start grabbing type
		String type = tokens.get(currentToken);
		int thisToken = currentToken + 1;
		
		// Skip any whitespace
		while(thisToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(thisToken)).matches()){
			thisToken++;
		}
		
		// Check if we hit end of tokens
		if(thisToken >= tokens.size()){
			return null;
		}
		
		// Have to handle type parameters potential
		if(!type.contains(TYPE_PARAMETER_OPEN_TOKEN)){
			// Check for next token being open token
			String nextToken = tokens.get(thisToken);
			if(nextToken.startsWith(TYPE_PARAMETER_OPEN_TOKEN)){
				type += nextToken;
				thisToken++;
			}
		}
		
		// If we have type parameters, have to make sure we get to the end of it
		if(type.contains(TYPE_PARAMETER_OPEN_TOKEN)){
			int openTokens = StringUtil.countSubstringInstances(type, TYPE_PARAMETER_OPEN_TOKEN);
			int closeTokens = StringUtil.countSubstringInstances(type, TYPE_PARAMETER_CLOSE_TOKEN);
			while(openTokens != closeTokens){
				// Check if we hit end of tokens
				if(thisToken >= tokens.size()){
					return null;
				}
				
				String nextToken = tokens.get(thisToken);
				type += nextToken;
				thisToken++;
				openTokens = StringUtil.countSubstringInstances(type, TYPE_PARAMETER_OPEN_TOKEN);
				closeTokens = StringUtil.countSubstringInstances(type, TYPE_PARAMETER_CLOSE_TOKEN);
			}
		}
		
		return Pair.of(type, thisToken);
	}
	
	/**
	 * Used to determine if we have a field or method based on the token we're looking at in parsing.
	 * This is used in several places to determine which we're looking at, hence the need for a method to
	 * reduce repeated code :P
	 *
	 * @param tokens The tokens we're parsing
	 * @param currentToken The current token being looked at
	 * @return Either {@link JavaCodeTypes#FIELD}, {@link JavaCodeTypes#METHOD}, or {@link JavaCodeTypes#UNKNOWN}
	 */
	protected static JavaCodeTypes determineFieldOrMethod(List<String> tokens, int currentToken){
		// Start would be a type, have to handle type parameter tokens
		Pair<String, Integer> typeStringAndNextToken = parseOutType(tokens, currentToken);
		if(typeStringAndNextToken == null){
			return JavaCodeTypes.UNKNOWN;
		}
		int thisToken = typeStringAndNextToken.getRight();
		
		// Skip whitespace
		while(thisToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(thisToken)).matches()){
			thisToken++;
		}
		// Check we're not at the end of tokens
		if(thisToken >= tokens.size()){
			return JavaCodeTypes.UNKNOWN;
		}
		
		// Next token is either open parameter (for constructors) or method name or variable name
		// - variable name can end with semicolon for field
		String token = tokens.get(thisToken);
		if(StringUtil.equals(token, PARAMETER_OPEN_TOKEN)){
			return JavaCodeTypes.METHOD;
		}else if(token.endsWith(SEMICOLON)){
			return JavaCodeTypes.FIELD;
		}
		
		// Move to next token and skip whitespace again
		do{
			thisToken++;
		}while(thisToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(thisToken)).matches());
		// Check we're not at the end of tokens
		if(thisToken >= tokens.size()){
			return JavaCodeTypes.UNKNOWN;
		}
		
		// Next token is either parameter open token or assignment operator token, or semicolon
		token = tokens.get(thisToken);
		if(StringUtil.equals(token, PARAMETER_OPEN_TOKEN)){
			return JavaCodeTypes.METHOD;
		}else if(StringUtil.equalsAny(token, ASSIGNMENT_OPERATOR_TOKEN, SEMICOLON)){
			return JavaCodeTypes.FIELD;
		}else{
			// If we haven't hit parameter open or assignment operator at this point, it's not a field or method
			return JavaCodeTypes.UNKNOWN;
		}
	}
	
	/**
	 * Parses a {@link JavaParameter} from the given String
	 *
	 * @param parameterContent The String to parse
	 * @return The {@link JavaParameter} that was parsed
	 */
	public static JavaParameter parseJavaParameter(String parameterContent){
		// Use a pattern to ensure we have a parameter and to get the relevant info for it
		Matcher paramMatch = PARAMETER_PATTERN.matcher(parameterContent);
		if(paramMatch.matches()){
			// Actually create the parameter with the parts of the pattern
			String typeContent = paramMatch.group(1);
			JavaType type = parseJavaType(typeContent);
			boolean vararg = StringUtil.isNotBlank(StringUtil.trim(paramMatch.group(8)));
			String name = paramMatch.group(9);
			return JavaParameter.builder()
					.type(type)
					.name(name)
					.vararg(vararg)
					.build();
		}else{
			throw new IllegalArgumentException("'" + parameterContent + "' is not a valid parameter");
		}
	}
	
	/**
	 * Parses a {@link JavaType} from the given String
	 *
	 * @param typeContent The String to parse
	 * @return The {@link JavaType} that was parsed
	 */
	public static JavaType parseJavaType(String typeContent){
		// Use a pattern to ensure we have a type and to get the relevant info for it
		Matcher typeMatch = TYPE_PATTERN.matcher(typeContent);
		if(typeMatch.matches()){
			// Actually create the type with the parts of the pattern
			String baseType = typeMatch.group(1);
			String typeParamsContent = typeMatch.group(2);
			List<JavaTypeParameter> typeParams = parseJavaTypeParameters(typeParamsContent);
			return JavaType.builder()
					.baseType(baseType)
					.typeParameters(typeParams)
					.build();
		}else{
			throw new IllegalArgumentException("'" + typeContent + "' is not a valid type");
		}
	}
	
	/**
	 * Parses the given String into a List of {@link JavaTypeParameter Java Type Parameters}
	 *
	 * @param typeParametersContent The String to be parsed
	 * @return The List of {@link JavaTypeParameter Java Type Parameters} that were parsed from the String
	 */
	public static List<JavaTypeParameter> parseJavaTypeParameters(String typeParametersContent){
		List<JavaTypeParameter> typeParams = new ArrayList<>();
		
		// Check for an empty string / only whitespace
		if(StringUtil.isBlank(StringUtil.trim(typeParametersContent))){
			return typeParams;
		}
		
		// Split type parameters on comma
		String[] paramSplit = typeParametersContent.split(",");
		String paramContent = "";
		for(String paramPiece: paramSplit){
			// Logic to make sure we have the full type with < and >'s
			if(StringUtil.isNotBlank(paramContent)){
				paramContent += ",";
			}
			paramContent += StringUtil.trim(paramPiece);
			int startTokenCount = StringUtil.countSubstringInstances(paramContent, TYPE_PARAMETER_OPEN_TOKEN);
			int endTokenCount = StringUtil.countSubstringInstances(paramContent, TYPE_PARAMETER_CLOSE_TOKEN);
			if(startTokenCount != endTokenCount){
				continue;
			}
			
			// Use a pattern to ensure we have a type parameter and to get the relevant info for it
			Matcher typeParamMatch = TYPE_PARAMETER_PATTERN.matcher(paramContent);
			if(typeParamMatch.matches()){
				// Actually create the type parameter with the parts of the pattern
				JavaType baseType = null, extendsType = null;
				String baseTypeStr = typeParamMatch.group(1);
				if(StringUtil.isNotBlank(baseTypeStr)){
					baseType = parseJavaType(baseTypeStr);
				}
				String extendsTypeStr = typeParamMatch.group(2);
				if(StringUtil.isNotBlank(extendsTypeStr)){
					extendsType = parseJavaType(extendsTypeStr);
				}
				typeParams.add(JavaTypeParameter.builder()
						.baseType(baseType)
						.extendsType(extendsType)
						.build());
			}else{
				throw new IllegalArgumentException("'" + paramContent + "' is not a valid type parameter");
			}
			// Reset param content
			paramContent = "";
		}
		
		// Check if we didn't parse some param content
		if(StringUtil.isNotBlank(paramContent)){
			throw new IllegalArgumentException("Failed to parse remaining type parameter content: '" + paramContent + "'");
		}
		
		// Return all the type parameters we parsed
		return typeParams;
	}
}
