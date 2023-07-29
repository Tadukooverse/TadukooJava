package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaSingleLineCommentParserTest extends BaseJavaSingleLineCommentParserTest{
	
	public JavaSingleLineCommentParserTest(){
		super(JavaSingleLineCommentParser::parseSingleLineComment);
	}
	
	@Test
	public void testExtraContentFoundError(){
		try{
			JavaSingleLineCommentParser.parseSingleLineComment("""
					// some comment
					// some other comment""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(e.getMessage(),
					buildJavaParsingExceptionMessage(JavaCodeTypes.SINGLE_LINE_COMMENT,
							"Found extra content after the single-line comment!"));
		}
	}
	
	@Test
	public void testCommentDoesNotStartWithTokenError(){
		try{
			JavaSingleLineCommentParser.parseSingleLineComment("""
					/* some comment */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(e.getMessage(),
					buildJavaParsingExceptionMessage(JavaCodeTypes.SINGLE_LINE_COMMENT,
							"First token of single-line comment must start with '" +
									SINGLE_LINE_COMMENT_TOKEN + "'"));
		}
	}
}
