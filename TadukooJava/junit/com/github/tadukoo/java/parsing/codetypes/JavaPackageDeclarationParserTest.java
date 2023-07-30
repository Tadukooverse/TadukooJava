package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaPackageDeclarationParserTest extends BaseJavaPackageDeclarationParserTest{
	
	public JavaPackageDeclarationParserTest(){
		super(JavaPackageDeclarationParser::parsePackageDeclaration);
	}
	
	@Test
	public void testPackageTokenMissing(){
		try{
			JavaPackageDeclarationParser.parsePackageDeclaration("""
					com.example;
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					"First token of package declaration must be '" + PACKAGE_TOKEN + "'"),
					e.getMessage());
		}
	}
}
