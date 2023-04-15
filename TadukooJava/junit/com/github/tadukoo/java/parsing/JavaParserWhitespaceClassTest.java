package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserWhitespaceClassTest extends BaseJavaParserTest{
	
	/*
	 * Whitespace Package Name Cases
	 */
	
	@Test
	public void testPackageNameLeadingWhitespace(){
		JavaClass clazz = runParserForClass("""
				\tpackage com.example;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	@Test
	public void testPackageNameWhitespaceBeforePackageName(){
		JavaClass clazz = runParserForClass("""
				package \tcom.example;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	@Test
	public void testPackageNameWhitespaceAfterPackageName(){
		JavaClass clazz = runParserForClass("""
				package com.example\t;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	@Test
	public void testPackageNameTrailingWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;\t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	@Test
	public void testPackageNameInsaneWhitespace(){
		JavaClass clazz = runParserForClass("""
				\t  package \t  com.example\t  \t;\t  \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	/*
	 * Whitespace Regular Import Cases
	 */
	
	@Test
	public void testImportWithLeadingWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				\timport com.example.Something;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	@Test
	public void testImportWithWhitespaceBeforeImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import \t com.example.Something;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	@Test
	public void testImportWithWhitespaceAfterImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something\t ;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	@Test
	public void testImportWithTrailingWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something; \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	@Test
	public void testImportWithInsaneWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				\t  import \t \tcom.example.Something\t   ;\t     \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	/*
	 * Whitespace Static Import Cases
	 */
	
	@Test
	public void testStaticImportWithLeadingWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				\t  import static com.example.SomethingStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testStaticImportWithWhitespaceBeforeStatic(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import \t static com.example.SomethingStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testStaticImportWithWhitespaceBeforeImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static      com.example.SomethingStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testStaticImportWithWhitespaceAfterImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static com.example.SomethingStatic   \t;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testStaticImportWithTrailingWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static com.example.SomethingStatic;    \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testStaticImportWithInsaneWhitespace(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				\t     import \t  \t static\t    \t com.example.SomethingStatic  \t  ;\t      \t \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	/*
	 * Test them all combined
	 */
	
	@Test
	public void testClassWithInsaneWhitespaceEverywhere(){
		JavaClass clazz = runParserForClass("""
				\t  package \t  com.example\t  \t;\t  \t
				
				\t  import \t \tcom.example.Something\t   ;\t     \t
				\t import     com.example.SomethingElse      \t;\t     \t    \t
				
				\t     import \t  \t static\t    \t com.example.SomethingStatic  \t  ;\t      \t \t
				\t  \t import \t      \t static\t  com.example.SomethingElseStatic\t \t  ;\t     \t
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something", "com.example.SomethingElse"), clazz.getImports());
		assertEquals(ListUtil.createList("com.example.SomethingStatic", "com.example.SomethingElseStatic"),
				clazz.getStaticImports());
	}
}
