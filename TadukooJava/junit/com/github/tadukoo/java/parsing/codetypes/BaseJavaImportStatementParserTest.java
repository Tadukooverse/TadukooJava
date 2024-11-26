package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaImportStatementParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaImportStatement, JavaParsingException> parseMethod;
	
	protected BaseJavaImportStatementParserTest(
			ThrowingFunction<String, JavaImportStatement, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Standard Tests
	 */
	
	@Test
	public void testSimpleImport() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testStaticImport() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import static com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	/*
	 * Error Tests
	 */
	
	@Test
	public void testErrorNoImportName(){
		try{
			parseMethod.apply("""
					import ;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"Failed to find import name in import statement!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testErrorNoSemicolon(){
		try{
			parseMethod.apply("""
					import com.example""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"Failed to find semicolon ending import statement!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAllErrors(){
		try{
			parseMethod.apply("""
					import\s""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"Failed to find import name in import statement!\n" +
									"Failed to find semicolon ending import statement!"),
					e.getMessage());
		}
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testWhitespaceLeading() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				\t     \t
				   \s
				\t import com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImport() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import\t     \t
				   \s
				\t com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceWithinImportName() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import com\t    \s
				\t     .   \s
				   \t    example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImportName() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import com.example   \s
				\t
				   ;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceBeforeStatic() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import    \s
				\t     \s
				    static com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterStatic() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				import static       \s
				\t    \s
				  com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceInsane() throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply("""
				\t     \t
				   \s
				\t import\t     \t
				   \s
				\t static       \s
				\t    \s
				  com\t    \s
				\t     .   \s
				   \t    example   \s
				\t
				   ;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
}
