package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaPackageDeclarationParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaPackageDeclaration, JavaParsingException> parseMethod;
	
	protected BaseJavaPackageDeclarationParserTest(
			ThrowingFunction<String, JavaPackageDeclaration, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Regular Tests
	 */
	
	@Test
	public void testSimplePackageDeclaration() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				package com.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	/*
	 * Error Tests
	 */
	
	@Test
	public void testPackageDeclarationNoPackageName(){
		try{
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
					package""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					"Failed to find package name in package declaration!\n" +
							"Failed to find semicolon ending package declaration!"), e.getMessage());
		}
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				\t    \t
				package com.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceBeforePackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				package\t  \t
				\tcom.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceInPackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				package com\t \t
				\t.
				\t  example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceAfterPackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				package com.example  \t
				;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testTrailingWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				package com.example;\t
				  \t""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testInsaneWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply("""
				\t    \tpackage\t  \t
				\tcom\t \t
				\t.
				\t  example  \t
				;\t
				  \t""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
}
