package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaFieldParserTest extends BaseJavaFieldParserTest{
	
	public JavaFieldParserTest(){
		super(JavaFieldParser::parseField);
	}
	
	@Test
	public void testNoFieldParsed(){
		try{
			JavaFieldParser.parseField("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to parse an actual field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleJavadocs(){
		try{
			JavaFieldParser.parseField("""
					/** {@inheritDoc} */
					/** something */
					String name;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Only one Javadoc allowed on a field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterField(){
		try{
			JavaFieldParser.parseField("""
					String name;
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered Javadoc after field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterField(){
		try{
			JavaFieldParser.parseField("""
					String name;
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered annotation after field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleFields(){
		try{
			JavaFieldParser.parseField("""
					String name;
					int version;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered multiple fields!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testSimpleFieldNoSemicolon(){
		try{
			JavaFieldParser.parseField("String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithVisibilityNoSemicolon(){
		try{
			JavaFieldParser.parseField("private String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithStaticNoSemicolon(){
		try{
			JavaFieldParser.parseField("static String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithFinalNoSemicolon(){
		try{
			JavaFieldParser.parseField("final String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testGarbage(){
		try{
			JavaFieldParser.parseField("derp bloop scoop loop;");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to parse a field"),
					e.getMessage());
		}
	}
}
