package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationFullParserTest extends BaseJavaAnnotationParserTest{
	
	public JavaAnnotationFullParserTest(){
		super(JavaAnnotationFullParserTest::runFullParserForAnnotation);
	}
	
	@Test
	public void failedToEndParameters(){
		try{
			runFullParserForAnnotation("""
					@Test(""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"Didn't find end of parameters"),
					e.getMessage());
		}
	}
}
