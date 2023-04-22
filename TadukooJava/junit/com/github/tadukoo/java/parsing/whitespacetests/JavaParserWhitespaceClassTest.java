package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserWhitespaceClassTest extends BaseJavaParserTest{
	
	/*
	 * Whitespace Base Cases
	 */
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t  \t
				\t class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceAfterClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class \t \t
				  \t \tTest{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceAfterClassName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test  \t
				\t  {
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceAfterOpenBrace() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{\t   \t
				
				\t
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceAfterClosingBrace() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test  \t
				\t  {
				}\t    \t
				\t
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testInsaneWhitespaceSimpleClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t  \t
				\t class \t \t
				  \t \tTest{\t   \t
						
				\t
				}\t    \t
				\t
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				clazz);
	}
	
	/*
	 * Whitespace Package Declaration Cases
	 */
	
	@Test
	public void testWhitespacePackageDeclarationLeading() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t     \t
				\t  package com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespacePackageDeclarationBeforePackageName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package \t  \t
				\t  com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespacePackageDeclarationWithinPackageName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com     \t
				
				\t    .\t \t
				\t     example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespacePackageDeclarationAfterPackageName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example
				\t   \t;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespacePackageDeclarationTrailing() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example;\t  \t
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespacePackageDeclarationInsane() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t     \t
				\t  package \t  \t
				\t  com     \t
				
				\t    .\t \t
				\t     example
				\t   \t;\t  \t
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	/*
	 * Whitespace Import Statement Cases
	 */
	
	@Test
	public void testWhitespaceImportStatementLeading() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t   \t
				\t  import com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementBeforeImportName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import \t\s
				\t     com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementWithinImportName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import com     \t
				
				\t    .\t       \s
				\t      example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementAfterImportName() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import com.example     \t
				
				\t      ;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementBeforeStatic() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import    \t
				 \t     static com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementAfterStatic() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import static   \t
				 \t     com.example;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceImportStatementInsane() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t   \t
				\t  import    \t
				 \t     static   \t
				 \t     com     \t
					
				\t    .\t       \s
				\t      example     \t
				
				\t      ;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	/*
	 * Test them all combined
	 */
	
	@Test
	public void testClassWithInsaneWhitespaceEverywhere() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				\t     \t
				\t  package \t  \t
				\t  com     \t
				
				\t    .\t \t
				\t     example
				\t   \t;\t  \t
				
				\t   \t
				\t  import    \t
				 \t        \t
				 \t     com     \t
					
				\t    .\t       \s
				\t      example     \t
				   .Something   \t
				\t      ;
				\t import     com.example.SomethingElse      \t;\t     \t    \t
				
				\t   \t
				\t  import    \t
				 \t     static   \t
				 \t     com     \t
					
				\t    .\t       \s
				\t      example     \t
								.SomethingStatic     \t
				\t      ;
				\t  \t import \t      \t static\t  com.example.SomethingElseStatic\t \t  ;\t     \t
				
				public\t  \t
				\t class \t \t
				  \t \tTest{\t   \t
						
				\t
				}\t    \t
				\t
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example.Something")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example.SomethingElse")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingStatic")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingElseStatic")
								.build())
						.visibility(Visibility.PUBLIC)
						.className("Test")
						.build(),
				clazz);
	}
}
