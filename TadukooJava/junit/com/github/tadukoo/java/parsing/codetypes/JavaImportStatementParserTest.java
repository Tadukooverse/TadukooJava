package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaImportStatementParserTest extends BaseJavaImportStatementParserTest{
	
	public JavaImportStatementParserTest(){
		super(JavaImportStatementParser::parseImportStatement);
	}
	
	@Test
	public void testMissingImportToken(){
		try{
			JavaImportStatementParser.parseImportStatement("""
					com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"First token of import statement must be '" + IMPORT_TOKEN + "'"),
					e.getMessage());
		}
	}
}
