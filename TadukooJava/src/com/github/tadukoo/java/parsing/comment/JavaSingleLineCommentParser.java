package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.AbstractJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.java.parsing.ParsingPojo;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
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
	 * Parses a {@link JavaSingleLineComment single-line comment} from the given tokens and starting index
	 *
	 * @param tokens The List of tokens to be parsed
	 * @param startToken The index of the token to start parsing at
	 * @return A {@link ParsingPojo} containing where we stopped parsing and the {@link JavaSingleLineComment comment}
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	public static ParsingPojo parseSingleLineComment(List<String> tokens, int startToken) throws JavaParsingException{
		// Keep track of errors
		List<String> errors = new ArrayList<>();
		
		// First token must start with //
		String firstToken = tokens.get(startToken);
		if(!firstToken.startsWith(SINGLE_LINE_COMMENT_TOKEN)){
			errors.add("First token of single-line comment must start with '" + SINGLE_LINE_COMMENT_TOKEN + "'");
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
		
		// If we had any errors, throw 'em
		if(!errors.isEmpty()){
			throw new JavaParsingException(JavaCodeTypes.SINGLE_LINE_COMMENT, StringUtil.buildStringWithNewLines(errors));
		}
		
		// Build and return the single-line comment
		return new ParsingPojo(currentToken, EditableJavaSingleLineComment.builder()
				.content(content.toString())
				.build());
	}
}
