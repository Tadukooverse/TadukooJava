package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaParserWhitespaceImportStatementTest extends BaseJavaParserTest{
	
	@Test
	public void testWhitespaceLeading() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				\t     \t
				   \s
				\t import com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImport() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import\t     \t
				   \s
				\t com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceWithinImportName() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import com\t    \s
				\t     .   \s
				   \t    example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImportName() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import com.example   \s
				\t
				   ;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceBeforeStatic() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import    \s
				\t     \s
				    static com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterStatic() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import static       \s
				\t    \s
				  com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceInsane() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
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
