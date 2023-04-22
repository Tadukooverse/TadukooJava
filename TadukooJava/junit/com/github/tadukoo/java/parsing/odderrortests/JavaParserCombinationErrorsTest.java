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
}
