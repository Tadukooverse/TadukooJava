package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaParserImportStatementTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleImport() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import com.example;""");
		assertFalse(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testStaticImport() throws JavaParsingException{
		JavaImportStatement importStatement = runParserForImportStatement("""
				import static com.example;""");
		assertTrue(importStatement.isStatic());
		assertEquals("com.example", importStatement.getImportName());
	}
	
	@Test
	public void testErrorNoImportName(){
		try{
			runParserForImportStatement("""
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
			runParserForImportStatement("""
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
			runParserForImportStatement("""
					import\s""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"Failed to find import name in import statement!\n" +
									"Failed to find semicolon ending import statement!"),
					e.getMessage());
		}
	}
}
