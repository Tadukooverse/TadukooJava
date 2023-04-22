package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserWhitespacePackageDeclarationTest extends BaseJavaParserTest{
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				\t    \t
				package com.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceBeforePackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				package\t  \t
				\tcom.example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceInPackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				package com\t \t
				\t.
				\t  example;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testWhitespaceAfterPackageName() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				package com.example  \t
				;""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testTrailingWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				package com.example;\t
				  \t""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
	
	@Test
	public void testInsaneWhitespace() throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = runParserForPackageDeclaration("""
				\t    \tpackage\t  \t
				\tcom\t \t
				\t.
				\t  example  \t
				;\t
				  \t""");
		assertEquals("com.example", packageDeclaration.getPackageName());
	}
}
