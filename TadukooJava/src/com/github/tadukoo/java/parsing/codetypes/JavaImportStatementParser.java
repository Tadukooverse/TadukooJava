package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser used for parsing an {@link JavaImportStatement import statement in Java}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaImportStatementParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaImportStatementParser} */
	private JavaImportStatementParser(){ }
	
	/**
	 * Parses an {@link JavaImportStatement import statement} from the given content String
	 *
	 * @param content The String of content to parse into an {@link JavaImportStatement import statement}
	 * @return The {@link JavaImportStatement import statement} parsed from the given String
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaImportStatement parseImportStatement(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Skip any leading newlines
		int startToken = skipLeadingNewlines(tokens);
		
		// Send the tokens to the main parsing method to get a result
		ParsingPojo result = parseImportStatement(tokens, startToken);
		
		// Make sure we reached the end of the tokens
		verifyEndOfTokens(tokens, result, JavaCodeTypes.IMPORT_STATEMENT);
		
		// Return the import statement that was parsed
		return (JavaImportStatement) result.parsedType();
	}
	
	/**
	 * Parses an {@link JavaImportStatement import statement} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaImportStatement import statement}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseImportStatement(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// Ensure the first token is "import"
		if(StringUtil.notEquals(tokens.get(startToken), IMPORT_TOKEN)){
			throw new JavaParsingException(JavaCodeTypes.IMPORT_STATEMENT,
					"First token of import statement must be '" + IMPORT_TOKEN + "'");
		}
		
		// Skip any newline tokens here
		int currentToken = startToken + 1;
		while(currentToken < tokens.size() && StringUtil.equals(tokens.get(currentToken), "\n")){
			currentToken++;
		}
		
		// Check if the next token is "static" (for a static import statement)
		boolean isStatic = false;
		if(currentToken < tokens.size()){
			isStatic = StringUtil.equalsIgnoreCase(tokens.get(currentToken), STATIC_MODIFIER);
			if(isStatic){
				currentToken++;
			}
		}
		
		// Build the import name from the remaining tokens
		boolean gotSemicolon = false;
		StringBuilder importName = new StringBuilder();
		for(; currentToken < tokens.size() && !gotSemicolon; currentToken++){
			String token = tokens.get(currentToken);
			
			// If we got a single semicolon, we're done
			if(StringUtil.equalsIgnoreCase(token, SEMICOLON)){
				gotSemicolon = true;
			}else if(token.endsWith(SEMICOLON)){
				importName.append(token, 0, token.length()-1);
				gotSemicolon = true;
			}else if(StringUtil.notEquals(token, "\n")){
				// Skip newlines
				importName.append(token);
			}
		}
		
		// If we got no import name, there's a problem
		if(importName.isEmpty()){
			errors.add("Failed to find import name in import statement!");
		}
		
		// If we got no semicolon, we got a problem
		if(!gotSemicolon){
			errors.add("Failed to find semicolon ending import statement!");
		}
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.IMPORT_STATEMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build the import statement and return
		return new ParsingPojo(currentToken, EditableJavaImportStatement.builder()
				.isStatic(isStatic)
				.importName(importName.toString())
				.build());
	}
}
