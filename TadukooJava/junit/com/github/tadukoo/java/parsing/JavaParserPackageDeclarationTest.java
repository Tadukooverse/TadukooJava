package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.JavaTypes;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaParserPackageDeclarationTest extends BaseJavaParserTest{
	
	@Test
	public void testSimplePackageDeclaration() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				package com.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testPackageDeclarationNoPackageName(){
		try{
			runParserForPackageDeclaration("""
					package ;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.PACKAGE_DECLARATION,
					"Failed to find package name in package declaration!"), e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationNoSemicolon(){
		try{
			runParserForPackageDeclaration("""
					package com.example""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.PACKAGE_DECLARATION,
					"Failed to find semicolon ending package declaration!"), e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationNoPackageNameOrSemicolon(){
		try{
			runParserForPackageDeclaration("""
					package""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.PACKAGE_DECLARATION,
					"Failed to find package name in package declaration!\n" +
							"Failed to find semicolon ending package declaration!"), e.getMessage());
		}
	}
}
