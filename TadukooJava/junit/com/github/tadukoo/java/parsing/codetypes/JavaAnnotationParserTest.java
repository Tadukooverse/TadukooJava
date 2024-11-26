package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationParserTest extends BaseJavaAnnotationParserTest{
	
	public JavaAnnotationParserTest(){
		super(JavaAnnotationParser::parseAnnotation);
	}
	
	@Test
	public void testMissingStartToken(){
		try{
			JavaAnnotationParser.parseAnnotation(
					ListUtil.createList("Test", "(", "type", "=", "String.class", ")"), 0);
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"First token of annotation must start with '" + ANNOTATION_START_TOKEN + "'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationDoesNotMatchRegex(){
		try{
			JavaAnnotationParser.parseAnnotation("""
					Test(type = String.class)""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"Failed to parse annotation"),
					e.getMessage());
		}
	}
}
