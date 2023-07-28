package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaPackageDeclarationParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimplePackageDeclaration() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runFullParserForPackageDeclaration("""
				package com.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testPackageDeclarationNoPackageName(){
		try{
			runFullParserForPackageDeclaration("""
					package ;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					"Failed to find package name in package declaration!"), e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationNoSemicolon(){
		try{
			runFullParserForPackageDeclaration("""
					package com.example""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					"Failed to find semicolon ending package declaration!"), e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationNoPackageNameOrSemicolon(){
		try{
			runFullParserForPackageDeclaration("""
					package""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					"Failed to find package name in package declaration!\n" +
							"Failed to find semicolon ending package declaration!"), e.getMessage());
		}
	}
}
