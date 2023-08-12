package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaTokens;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.StringUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A base parser for Java parsing that contains any shared logic
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public abstract class AbstractJavaParser implements JavaTokens{
	
	/** A regular expression used for {@link Visibility} */
	private static final String VISIBILITY_REGEX = "(?:(" + PUBLIC_MODIFIER + "|" + PROTECTED_MODIFIER + "|" +
			PRIVATE_MODIFIER + ")\\s*)?";
	/** A regular expression used for the static modifier */
	private static final String STATIC_REGEX = "(" + STATIC_MODIFIER + "\\s*)?";
	/** A regular expression used for the final modifier */
	private static final String FINAL_REGEX = "(" + FINAL_MODIFIER + "\\s*)?";
	/** A regular expression used for all the modifiers */
	protected static final String MODIFIERS_REGEX = VISIBILITY_REGEX + STATIC_REGEX + FINAL_REGEX;
	
	/** A regular expression used for tokens to match on when splitting tokens from a content String */
	protected static final String TOKEN_REGEX = "\n|\\(|\\)|\\{|}|=|[^\\S\n]+|[^\\s(){}=]+";
	/** A matcher to use to find whitespace (usually to skip it) */
	protected static final Matcher WHITESPACE_MATCHER = Pattern.compile("\\s+").matcher("");
	
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
	 * Used to determine if we have a field or method based on the token we're looking at in parsing.
	 * This is used in several places to determine which we're looking at, hence the need for a method to
	 * reduce repeated code :P
	 *
	 * @param tokens The tokens we're parsing
	 * @param currentToken The current token being looked at
	 * @return Either {@link JavaCodeTypes#FIELD}, {@link JavaCodeTypes#METHOD}, or {@link JavaCodeTypes#UNKNOWN}
	 */
	protected static JavaCodeTypes determineFieldOrMethod(List<String> tokens, int currentToken){
		// First token is a type, can't have parameter open or assignment, so skip it
		int thisToken = currentToken + 1;
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
		
		// Move to next token
		thisToken++;
		// Skip whitespace again
		while(thisToken < tokens.size() && WHITESPACE_MATCHER.reset(tokens.get(thisToken)).matches()){
			thisToken++;
		}
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
}
