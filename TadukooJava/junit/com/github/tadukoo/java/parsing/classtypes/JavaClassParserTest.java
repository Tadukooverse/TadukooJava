package com.github.tadukoo.java.parsing.classtypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaClassParserTest extends BaseJavaClassParserTest{
	
	public JavaClassParserTest(){
		super(JavaClassParser::parseClass);
	}
	
	@Test
	public void testMultiplePackageDeclarations(){
		try{
			JavaClassParser.parseClass("""
					package some.package;
					package something.else;
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Only one package declaration allowed on a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					package some.package;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered package declaration after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					import some.name;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered import statement after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleJavadocs(){
		try{
			JavaClassParser.parseClass("""
					/** {@inheritDoc} */
					/** something */
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Only one Javadoc allowed on a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered Javadoc after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered annotation after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleClasses(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					class Derp{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered multiple classes!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testParsedNotAClass(){
		try{
			JavaClassParser.parseClass("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to parse an actual class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFirstTokenNotClass(){
		try{
			JavaClassParser.parseClass("""
					Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"The first token of a class must be 'class'"),
					e.getMessage());
		}
	}
}
