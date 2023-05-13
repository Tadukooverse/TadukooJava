package com.github.tadukoo.java.parsing.odderrortests;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaParserCombinationErrorsTest extends BaseJavaParserTest{
	
	@Test
	public void testTwoPackageDeclarations(){
		try{
			parser.parseType("""
					package com.example;
					package com.example.other;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered two package declarations!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationAfterClass(){
		try{
			parser.parseType("""
					class Test{
					}
					package com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered package declaration after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementAfterClass(){
		try{
			parser.parseType("""
					class Test{
					}
					import com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered import statement after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterClass(){
		try{
			parser.parseType("""
					class Test{
					}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered annotation after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoFields(){
		try{
			parser.parseType("""
					String type;
					int derp;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered two fields!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testClassBeforeField(){
		try{
			parser.parseType("""
					class Test{
					}
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered field outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationBeforeField(){
		try{
			parser.parseType("""
					package some.package;
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered package declaration before field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementBeforeField(){
		try{
			parser.parseType("""
					import some.classname;
					String type;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered import statements before field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoMethods(){
		try{
			parser.parseType("""
					String something(){}
					int somethingElse(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered two methods!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testClassBeforeMethod(){
		try{
			parser.parseType("""
					class Test{
					}
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered method outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationBeforeMethod(){
		try{
			parser.parseType("""
					package some.package;
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered package declaration before method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementBeforeMethod(){
		try{
			parser.parseType("""
					import some.classname;
					String something(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered import statements before method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTwoOuterClasses(){
		try{
			parser.parseType("""
					class Test{
					}
					class Test2{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered two outer level classes!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldBeforeClass(){
		try{
			parser.parseType("""
					String type;
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered fields outside a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMethodBeforeClass(){
		try{
			parser.parseType("""
					String something(){}
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.UNKNOWN,
					"Encountered methods outside a class!"),
					e.getMessage());
		}
	}
}
