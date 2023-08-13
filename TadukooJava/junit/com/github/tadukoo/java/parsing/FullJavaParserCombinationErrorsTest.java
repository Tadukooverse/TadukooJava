package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FullJavaParserCombinationErrorsTest extends BaseJavaParserTest{
	
	@Test
	public void testTwoPackageDeclarations(){
		try{
			FullJavaParser.parseType("""
					package com.example;
					package com.example.other;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered two package declarations!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationAfterClass(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					package com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered package declaration after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementAfterClass(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					import com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered import statement after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoJavadocs(){
		try{
			FullJavaParser.parseType("""
					/** {@inheritDoc} */
					/** something */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
							"Encountered two Javadocs!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterField(){
		try{
			FullJavaParser.parseType("""
					String type;
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
							"Encountered a Javadoc after field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterMethod(){
		try{
			FullJavaParser.parseType("""
					void test(){}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
							"Encountered a Javadoc after method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterClass(){
		try{
			FullJavaParser.parseType("""
					class Test{}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
							"Encountered a Javadoc after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterClass(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered annotation after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoFields(){
		try{
			FullJavaParser.parseType("""
					String type;
					int derp;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered two fields!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testClassBeforeField(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered field outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationBeforeField(){
		try{
			FullJavaParser.parseType("""
					package some.package;
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered package declaration before field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementBeforeField(){
		try{
			FullJavaParser.parseType("""
					import some.classname;
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered import statements before field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoMethods(){
		try{
			FullJavaParser.parseType("""
					String something(){}
					int somethingElse(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered two methods!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testClassBeforeMethod(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered method outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationBeforeMethod(){
		try{
			FullJavaParser.parseType("""
					package some.package;
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered package declaration before method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementBeforeMethod(){
		try{
			FullJavaParser.parseType("""
					import some.classname;
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered import statements before method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoOuterClasses(){
		try{
			FullJavaParser.parseType("""
					class Test{
					}
					class Test2{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered two outer level classes!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldBeforeClass(){
		try{
			FullJavaParser.parseType("""
					String type;
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered fields outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMethodBeforeClass(){
		try{
			FullJavaParser.parseType("""
					String something(){}
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					"Encountered methods outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFailedToDetermineFieldOrMethodAfterWhitespace(){
		try{
			FullJavaParser.parseType("""
					String something""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
							"Failed to determine type from token 'String'"),
					e.getMessage());
		}
	}
}
