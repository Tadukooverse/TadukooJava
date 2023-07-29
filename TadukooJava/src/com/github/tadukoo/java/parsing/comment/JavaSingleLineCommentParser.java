package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.List;

/**
 * A parser to use for parsing {@link JavaSingleLineComment single-line Java comments}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaSingleLineCommentParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaSingleLineCommentParser} */
	private JavaSingleLineCommentParser(){ }
	
	/**
	 * Parses a {@link JavaSingleLineComment single-line comment} from the given content String
	 *
	 * @param content The String to be parsed as a {@link JavaSingleLineComment single-line comment}
	 * @return The parsed {@link JavaSingleLineComment single-line comment}
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaSingleLineComment parseSingleLineComment(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Skip any leading newlines
		int startToken = skipLeadingNewlines(tokens);
		
		// Send the tokens to the main parsing method to get a result
		ParsingPojo result = parseSingleLineComment(tokens, startToken);
		
		// Make sure we reached the end of the tokens
		verifyEndOfTokens(tokens, result, JavaCodeTypes.SINGLE_LINE_COMMENT);
		
		// Return the single line comment that was parsed
		return (JavaSingleLineComment) result.parsedType();
	}
	
	/**
	 * Parses a {@link JavaSingleLineComment single-line comment} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaSingleLineComment comment}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseSingleLineComment(List<String> tokens, int startToken) throws JavaParsingException{
		// First token must start with //
		String firstToken = tokens.get(startToken);
		if(!firstToken.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
			throw new JavaParsingException(JavaCodeTypes.SINGLE_LINE_COMMENT,
					"First token of single-line comment must start with '" + SINGLE_LINE_COMMENT_TOKEN + "'");
		}
		
		// Add the first token to the content if it has anything other than the start token
		StringBuilder content = new StringBuilder();
		if(StringUtil.notEquals(firstToken, SINGLE_LINE_COMMENT_TOKEN)){
			content.append(firstToken, SINGLE_LINE_COMMENT_TOKEN.length(), firstToken.length());
		}
		
		// Continue adding to content
		int currentToken = startToken + 1;
		while(currentToken < tokens.size()){
			String token = tokens.get(currentToken);
			currentToken++;
			// We're done when we hit a newline
			if(StringUtil.equals(token, "\n")){
				break;
			}
			if(!content.isEmpty()){
				content.append(' ');
			}
			content.append(token);
		}
		
		// Build and return the single-line comment
		return new ParsingPojo(currentToken, EditableJavaSingleLineComment.builder()
				.content(content.toString())
				.build());
	}
}
