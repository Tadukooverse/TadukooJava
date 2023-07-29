package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser to use for parsing {@link JavaMultiLineComment multi-line Java comments}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public class JavaMultiLineCommentParser extends AbstractJavaParser{
	
	/** Not allowed to instantiate {@link JavaMultiLineCommentParser} */
	private JavaMultiLineCommentParser(){ }
	
	/**
	 * Parses a {@link JavaMultiLineComment multi-line comment} from the given content String
	 *
	 * @param content The String to parse into a {@link JavaMultiLineComment multi-line comment}
	 * @return The parsed {@link JavaMultiLineComment multi-line comment}
	 * @throws JavaParsingException If anything goes wrong in parsing
	 */
	public static JavaMultiLineComment parseMultiLineComment(String content) throws JavaParsingException{
		// Split the content into "tokens"
		List<String> tokens = splitContentIntoTokens(content);
		
		// Skip any leading newlines
		int startToken = skipLeadingNewlines(tokens);
		
		// Send the tokens to the main parsing method to get a result
		ParsingPojo result = parseMultiLineComment(tokens, startToken);
		
		// Make sure we reached the end of the tokens
		verifyEndOfTokens(tokens, result, JavaCodeTypes.MULTI_LINE_COMMENT);
		
		// Return the multi-line comment that was parsed
		return (JavaMultiLineComment) result.parsedType();
	}
	
	/**
	 * Parses a {@link JavaMultiLineComment multi-line comment} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaMultiLineComment comment}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseMultiLineComment(List<String> tokens, int startToken) throws JavaParsingException{
		// First token must start with /*
		String firstToken = tokens.get(startToken);
		if(!firstToken.startsWith(MULTI_LINE_COMMENT_START_TOKEN)){
			throw new JavaParsingException(JavaCodeTypes.MULTI_LINE_COMMENT,
					"First token of multi-line comment must start with '" + MULTI_LINE_COMMENT_START_TOKEN + "'");
		}
		
		// Start building the first line (we may have some in the first token)
		StringBuilder line = new StringBuilder();
		if(StringUtil.notEquals(firstToken, MULTI_LINE_COMMENT_START_TOKEN)){
			line.append(firstToken.substring(MULTI_LINE_COMMENT_START_TOKEN.length()));
		}
		
		// Skip the next token if it's a newline (this avoids having an extra blank line in the content)
		boolean justHadNewline = false;
		int currentToken = startToken + 1;
		if(currentToken < tokens.size() && StringUtil.equals(tokens.get(currentToken), "\n")){
			currentToken++;
			justHadNewline = true;
		}
		
		// Parsing
		List<String> content = new ArrayList<>();
		boolean foundClosing = false;
		for(; currentToken < tokens.size() && !foundClosing; currentToken++){
			String token = tokens.get(currentToken);
			
			String toAddToLine = "";
			if(StringUtil.equals(token, "\n")){
				// Newline means we can finish the current line of content
				justHadNewline = true;
				content.add(line.toString());
				line = new StringBuilder();
			}else if(StringUtil.equals(token, JAVADOC_LINE_TOKEN) && justHadNewline){
				// Can skip this token if we just had a newline
				justHadNewline = false;
			}else if(token.endsWith(MULTI_LINE_COMMENT_CLOSE_TOKEN)){
				foundClosing = true;
				if(StringUtil.notEquals(token, MULTI_LINE_COMMENT_CLOSE_TOKEN)){
					toAddToLine = token.substring(0, token.length() - MULTI_LINE_COMMENT_CLOSE_TOKEN.length());
				}
			}else{
				toAddToLine = token;
				justHadNewline = false;
			}
			
			// Add to line if we have something
			if(StringUtil.isNotBlank(toAddToLine)){
				// Add space if line is not empty
				if(!line.isEmpty()){
					line.append(' ');
				}
				line.append(toAddToLine);
			}
		}
		
		// Add the last line if it's still dangling
		if(!line.isEmpty()){
			content.add(line.toString());
		}
		
		// Error if we didn't find the closing token
		if(!foundClosing){
			throw new JavaParsingException(JavaCodeTypes.MULTI_LINE_COMMENT,
					"Failed to find closing multi-line comment token!");
		}
		
		return new ParsingPojo(currentToken, EditableJavaMultiLineComment.builder()
				.content(content)
				.build());
	}
}
