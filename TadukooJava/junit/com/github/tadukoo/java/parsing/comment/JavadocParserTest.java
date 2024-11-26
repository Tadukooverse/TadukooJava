package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavadocParserTest extends BaseJavadocParserTest{
	
	public JavadocParserTest(){
		super(JavadocParser::parseJavadoc);
	}
	
	@Test
	public void testNoJavadocStartToken(){
		try{
			JavadocParser.parseJavadoc("""
					* some content
					*/""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"First token of Javadoc must start with '" + JAVADOC_START_TOKEN + "'"),
					e.getMessage());
		}
	}
}
