package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaTokens;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.StringUtil;

import java.util.List;

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
	protected static final String TOKEN_REGEX = "\n|\\(|\\)|\\{|}|[^\\s(){}]+";
	
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
	protected static int skipLeadingNewlines(List<String> tokens){
		int startToken = 0;
		while(StringUtil.equals(tokens.get(startToken), "\n")){
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
			while(lastTokenIndex < tokens.size() && StringUtil.equals(tokens.get(lastTokenIndex), "\n")){
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
		int thisToken = currentToken;
		// Skip any newlines
		while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
			thisToken++;
		}
		// Check we didn't reach the end of tokens
		if(thisToken >= tokens.size()){
			return JavaCodeTypes.UNKNOWN;
		}
		String token = tokens.get(thisToken);
		// First token is a type (possibly with start of parameters if a method)
		if(token.contains(PARAMETER_OPEN_TOKEN)){
			// We got a method
			return JavaCodeTypes.METHOD;
		}else if(token.contains(ASSIGNMENT_OPERATOR_TOKEN)){
			// We got a field
			return JavaCodeTypes.FIELD;
		}else{
			// Move to next token to check
			thisToken++;
			// Skip any newlines
			while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
				thisToken++;
			}
			// Check we didn't reach the end of tokens
			if(thisToken >= tokens.size()){
				return JavaCodeTypes.UNKNOWN;
			}
			String nextToken = tokens.get(thisToken);
			if(nextToken.contains(PARAMETER_OPEN_TOKEN)){
				// We got a method
				return JavaCodeTypes.METHOD;
			}else if(nextToken.contains(SEMICOLON)){
				// We got a field
				return JavaCodeTypes.FIELD;
			}else{
				// Move to next token to check
				thisToken++;
				// Skip any newlines
				while(thisToken < tokens.size() && StringUtil.equals(tokens.get(thisToken), "\n")){
					thisToken++;
				}
				// Check we didn't reach the end of tokens
				if(thisToken >= tokens.size()){
					return JavaCodeTypes.UNKNOWN;
				}
				String nextNextToken = tokens.get(thisToken);
				if(nextNextToken.startsWith(PARAMETER_OPEN_TOKEN)){
					// We got a method
					return JavaCodeTypes.METHOD;
				}else{
					// We got a field
					return JavaCodeTypes.FIELD;
				}
			}
		}
	}
}
