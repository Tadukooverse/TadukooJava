package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTypeWithModifiersParserErrorsTest extends BaseJavaParserTest{
	
	@Test
	public void testDuplicateModifiersError(){
		try{
			FullJavaParser.parseType("""
					static static class Test{
					}
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
					"Found duplicate modifier: 'static'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleDuplicateModifiersError(){
		try{
			FullJavaParser.parseType("""
					static static final final class Test{
					}
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Found duplicate modifier: 'static'\n" +
									"Found duplicate modifier: 'final'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleVisibilityModifiersError(){
		try{
			FullJavaParser.parseType("""
					private public class Test{
					}
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Found multiple visibility modifiers"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFailedToDetermineTypeError(){
		try{
			FullJavaParser.parseType("""
					private
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.TYPE_WITH_MODIFIERS,
							"Failed to determine result type"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAllErrors(){
		try{
			FullJavaParser.parseType("""
					private public static static final final
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.TYPE_WITH_MODIFIERS,
							"""
									Found duplicate modifier: 'static'
									Found duplicate modifier: 'final'
									Found multiple visibility modifiers
									Failed to determine result type"""),
					e.getMessage());
		}
	}
}