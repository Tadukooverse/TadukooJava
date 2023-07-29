package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaMultiLineCommentParserTest extends BaseJavaMultiLineCommentParserTest{
	
	public JavaMultiLineCommentParserTest(){
		super(JavaMultiLineCommentParser::parseMultiLineComment);
	}
	
	@Test
	public void testMissingStartToken(){
		try{
			JavaMultiLineCommentParser.parseMultiLineComment("""
					*/""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.MULTI_LINE_COMMENT,
							"First token of multi-line comment must start with '" +
									MULTI_LINE_COMMENT_START_TOKEN + "'"),
					e.getMessage());
		}
	}
}
