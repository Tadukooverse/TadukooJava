package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleClass(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
	}
	
	@Test
	public void testClassWithSuperClass(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				public class Test extends Derp{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals("Derp", clazz.getSuperClassName());
	}
	
	@Test
	public void testClassWithImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something"), clazz.getImports());
	}
	
	@Test
	public void testClassWithMultipleImports(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				import com.example.SomethingElse;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something", "com.example.SomethingElse"), clazz.getImports());
	}
	
	@Test
	public void testClassWithMultipleImportsAndLineBetween(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				import com.example.SomethingElse;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.Something", "", "com.example.SomethingElse"),
				clazz.getImports());
	}
	
	@Test
	public void testClassWithStaticImport(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static com.example.SomethingStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic"), clazz.getStaticImports());
	}
	
	@Test
	public void testClassWithMultipleStaticImports(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static com.example.SomethingStatic;
				import static com.example.SomethingElseStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic", "com.example.SomethingElseStatic"),
				clazz.getStaticImports());
	}
	
	@Test
	public void testClassWithMultipleStaticImportsAndLineBetween(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import static com.example.SomethingStatic;
				
				import static com.example.SomethingElseStatic;
				
				public class Test{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals(ListUtil.createList("com.example.SomethingStatic", "", "com.example.SomethingElseStatic"),
				clazz.getStaticImports());
	}
	
	@Test
	public void testClassWithEverything(){
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				import com.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static com.example.SomethingElseStatic;
				
				public class Test extends Derp{
				}
				""");
		assertEquals("com.example", clazz.getPackageName());
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		assertEquals("Test", clazz.getClassName());
		assertEquals("Derp", clazz.getSuperClassName());
		assertEquals(ListUtil.createList("com.example.Something", "", "com.example.SomethingElse"),
				clazz.getImports());
		assertEquals(ListUtil.createList("com.example.SomethingStatic", "", "com.example.SomethingElseStatic"),
				clazz.getStaticImports());
	}
}
