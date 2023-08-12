package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaMethodParserTest extends BaseJavaMethodParserTest{
	
	public JavaMethodParserTest(){
		super(JavaMethodParser::parseMethod);
	}
	
	/*
	 * Error Cases
	 */
	
	@Test
	public void testTooManyJavadocs(){
		try{
			JavaMethodParser.parseMethod("""
					/** {@inheritDoc} */
					/** some other Javadoc */
					Test(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Only one Javadoc allowed on a method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterMethod(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered Javadoc after method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterMethod(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered annotation after method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleMethods(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					Test(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered multiple methods!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testNotAMethod(){
		try{
			JavaMethodParser.parseMethod("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Failed to parse an actual method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testNotAMethodParseJustMethod(){
		assertNull(JavaMethodParser.parseJustMethod("@Test"));
	}
}
