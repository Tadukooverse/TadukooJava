package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used to parse a {@link JavaPackageDeclaration package declaration in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaPackageDeclarationParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaPackageDeclarationParser} */
	private JavaPackageDeclarationParser(){ }
	
	/**
	 * Parses a {@link JavaPackageDeclaration package declaration} from the given content String
	 *
	 * @param content The String of content to parse into a {@link JavaPackageDeclaration package declaration}
	 * @return The {@link JavaPackageDeclaration package declaration} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaPackageDeclaration parsePackageDeclaration(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Skip any leading newlines
		int startToken = skipLeadingWhitespace(tokens);
		
		// Send the tokens to the main parsing method to get a result
		ParsingPojo result = parsePackageDeclaration(tokens, startToken);
		
		// Make sure we reached the end of the tokens
		verifyEndOfTokens(tokens, result, JavaCodeTypes.PACKAGE_DECLARATION);
		
		// Return the package declaration that was parsed
		return (JavaPackageDeclaration) result.parsedType();
	}
	
	/**
	 * Parses a {@link JavaPackageDeclaration package declaration} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaPackageDeclaration package declaration}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parsePackageDeclaration(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure first token is "package"
		if(StringUtil.notEquals(tokens.get(startToken), PACKAGE_TOKEN)){
			throw new JavaParsingException(JavaCodeTypes.PACKAGE_DECLARATION,
					"First token of package declaration must be '" + PACKAGE_TOKEN + "'");
		}
		
		// We will start iterating at startToken+1 (since startToken is the "package" token)
		// We have it here because we'll be returning this
		int currentToken = startToken+1;
		
		// Get the actual package name
		boolean gotSemicolon = false;
		StringBuilder packageName = new StringBuilder();
		for(; currentToken < tokens.size() && !gotSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			// If we got a single semicolon, we're done
			if(StringUtil.equals(token, SEMICOLON)){
				gotSemicolon = true;
			}else if(token.endsWith(SEMICOLON)){
				packageName.append(token, 0, token.length()-1);
				gotSemicolon = true;
			}else if(!WHITESPACE_MATCHER.reset(token).matches()){
				// If we got a newline, skip it
				packageName.append(token);
			}
		}
		
		// If we got no package name, there's a problem
		if(packageName.isEmpty()){
			errors.add("Failed to find package name in package declaration!");
		}
		
		// If we got no semicolon, we got a problem
		if(!gotSemicolon){
			errors.add("Failed to find semicolon ending package declaration!");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.PACKAGE_DECLARATION, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the package declaration and return
		return new ParsingPojo(currentToken, EditableJavaPackageDeclaration.builder()
				.packageName(packageName.toString())
				.build());
	}
}
