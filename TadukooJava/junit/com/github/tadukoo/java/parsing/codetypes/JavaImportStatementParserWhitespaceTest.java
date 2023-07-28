package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaImportStatementParserWhitespaceTest extends BaseJavaParserTest{
	
	@Test
	public void testWhitespaceLeading() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				\t     \t
				   \s
				\t import com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImport() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				import\t     \t
				   \s
				\t com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceWithinImportName() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				import com\t    \s
				\t     .   \s
				   \t    example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterImportName() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				import com.example   \s
				\t
				   ;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceBeforeStatic() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				import    \s
				\t     \s
				    static com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceAfterStatic() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
				import static       \s
				\t    \s
				  com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testWhitespaceInsane() throws JavaParsingException{
		JavaImportStatement importStatement = runFullParserForImportStatement("""
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
