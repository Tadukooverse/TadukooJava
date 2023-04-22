package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserClassTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
					.className("Test")
					.build(),
				clazz);
	}
	
	@Test
	public void testClassWithPackageDeclaration() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example;
				
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
	public void testClassWithImport() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import com.example;
				
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
	public void testClassWithMultipleImports() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import com.example.Something;
				import com.example.SomethingElse;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example.Something")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example.SomethingElse")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleImportsAndLineBetween() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.importName("com.example.Something")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.importName("org.example.SomethingElse")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithStaticImport() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import static com.example.SomethingStatic;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingStatic")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleStaticImports() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import static com.example.SomethingStatic;
				import static com.example.SomethingElseStatic;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingStatic")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingElseStatic")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleStaticImportsAndLineBetween() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.SomethingStatic")
								.build())
						.importStatement(EditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.example.SomethingElseStatic")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	// TODO: Test with Javadoc
	
	// TODO: Test with Annotations
	
	@Test
	public void testClassWithVisibility() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				protected class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.visibility(Visibility.PROTECTED)
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithVisibilityPrivate() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				private class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.visibility(Visibility.PRIVATE)
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithVisibilityPublic() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				public class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.visibility(Visibility.PUBLIC)
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testStaticClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				static class Test{
				}
				""");
		// Do this slightly hacky because an outer class would not be static like this, but an inner class could be
		EditableJavaClass javaClass = EditableJavaClass.builder()
				.className("Test")
				.build();
		javaClass.setStatic(true);
		assertEquals(javaClass, clazz);
	}
	
	@Test
	public void testClassWithSuperClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test extends Derp{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.superClassName("Derp")
						.build(),
				clazz);
	}
	
	// TODO: Test with inner Classes
	
	// TODO: Test with Fields
	
	// TODO: Test with Methods
	
	@Test
	public void testClassWithEverything() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				public static class Test extends Derp{
				}
				""");
		// TODO: Add Javadoc
		// TODO: Add Annotations
		// TODO: Add Inner Classes
		// TODO: Add Fields
		// TODO: Add Methods
		EditableJavaClass javaClass = EditableJavaClass.builder()
				.packageDeclaration(EditableJavaPackageDeclaration.builder()
						.packageName("com.example")
						.build())
				.importStatement(EditableJavaImportStatement.builder()
						.importName("com.example.Something")
						.build())
				.importStatement(EditableJavaImportStatement.builder()
						.importName("org.example.SomethingElse")
						.build())
				.importStatement(EditableJavaImportStatement.builder()
						.isStatic()
						.importName("com.example.SomethingStatic")
						.build())
				.importStatement(EditableJavaImportStatement.builder()
						.isStatic()
						.importName("org.example.SomethingElseStatic")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("Test")
				.superClassName("Derp")
				.build();
		// We have to be hacky about static since it's an outer class
		javaClass.setStatic(true);
		assertEquals(javaClass, clazz);
	}
}
