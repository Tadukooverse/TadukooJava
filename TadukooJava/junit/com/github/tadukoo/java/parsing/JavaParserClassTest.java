package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.method.EditableJavaMethod;
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
	
	@Test
	public void testClassWithSingleAnnotation() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				@Test
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleAnnotations() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				@Test
				@Derp(type=String.class)
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.parameter("type", "String.class")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
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
	public void testFinalClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				final class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.isFinal()
						.className("Test")
						.build(),
				clazz);
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
	
	@Test
	public void testClassWithInnerClass() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					class Derp{
					}
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Derp")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleInnerClasses() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					class Derp{
					}
					
					class Yep{
					}
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Derp")
								.build())
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Yep")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithField() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					String name;
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("name")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleFields() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					String name;
					int derp;
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("name")
								.build())
						.field(EditableJavaField.builder()
								.type("int").name("derp")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMethod() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					String type(){}
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("type")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithMultipleMethods() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{
					String type(){}
					int getVersion(){}
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("type")
								.build())
						.method(EditableJavaMethod.builder()
								.returnType("int").name("getVersion")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithEverything() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				@Example
				@Test(true)
				@Yep(type=String.class)
				public static final class Test extends Derp{
				
					class Derp{
					}
					
					class Yep{
					}
					
					String name;
					int derp;
					
					String type(){}
					int getVersion(){}
				}
				""");
		// TODO: Add Javadoc
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
				.annotation(EditableJavaAnnotation.builder()
						.name("Example")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("value", "true")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Yep")
						.parameter("type", "String.class")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("Test")
				.superClassName("Derp")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("Derp")
						.build())
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("Yep")
						.build())
				.field(EditableJavaField.builder()
						.type("String").name("name")
						.build())
				.field(EditableJavaField.builder()
						.type("int").name("derp")
						.build())
				.method(EditableJavaMethod.builder()
						.returnType("String").name("type")
						.build())
				.method(EditableJavaMethod.builder()
						.returnType("int").name("getVersion")
						.build())
				.build();
		// We have to be hacky about static since it's an outer class
		javaClass.setStatic(true);
		assertEquals(javaClass, clazz);
	}
	
	@Test
	public void testClassWithEverythingAndInnerClassesHaveEverythingToo() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				package com.example;
				
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				@Example
				@Test(true)
				@Yep(type=String.class)
				public final class Test extends Derp{
				
					@Test
					@Yep(true)
					private static final class Derp extends Blah{
						@Test
						@Derp(type = String.class)
						protected static final String derp = "Blah";
						@Blah
						protected int something;
						
						@Blah
						private int getVersion(){return version;}
						
						@Test
						@Derp(type=String.class)
						private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
					}
					
					@Derp
					@Yep(something="no")
					protected static final class Yep extends Something{
						public static final String someName = "Test";
						private int somethingElse;
						
						@Blah
						private int getVersion(){return version;}
						
						@Test
						@Derp(type=String.class)
						private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
					}
					
					@Test
					@Derp(type = String.class)
					private static final String name = "Test";
					@Blah
					public int version;
					
					@Blah
					private int getVersion(){return version;}
					
					@Test
					@Derp(type=String.class)
					private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
				}
				""");
		// TODO: Add Javadoc
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
				.annotation(EditableJavaAnnotation.builder()
						.name("Example")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("value", "true")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Yep")
						.parameter("type", "String.class")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("Test")
				.superClassName("Derp")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Yep")
								.parameter("value", "true")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.className("Derp").superClassName("Blah")
						.field(EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PROTECTED)
								.isStatic().isFinal()
								.type("String").name("derp")
								.value("\"Blah\"")
								.build())
						.field(EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Blah")
										.build())
								.visibility(Visibility.PROTECTED)
								.type("int").name("something")
								.build())
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Blah")
										.build())
								.visibility(Visibility.PRIVATE)
								.returnType("int").name("getVersion")
								.line("return version;")
								.build())
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.returnType("String").name("test")
								.parameter("String", "type")
								.parameter("int", "derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build())
						.build())
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Yep")
								.parameter("something", "\"no\"")
								.build())
						.visibility(Visibility.PROTECTED)
						.isStatic().isFinal()
						.className("Yep").superClassName("Something")
						.field(EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("someName")
								.value("\"Test\"")
								.build())
						.field(EditableJavaField.builder()
								.visibility(Visibility.PRIVATE)
								.type("int").name("somethingElse")
								.build())
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Blah")
										.build())
								.visibility(Visibility.PRIVATE)
								.returnType("int").name("getVersion")
								.line("return version;")
								.build())
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.returnType("String").name("test")
								.parameter("String", "type")
								.parameter("int", "derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build())
						.build())
				.field(EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.parameter("type", "String.class")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.type("String").name("name")
						.value("\"Test\"")
						.build())
				.field(EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Blah")
								.build())
						.visibility(Visibility.PUBLIC)
						.type("int").name("version")
						.build())
				.method(EditableJavaMethod.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Blah")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("int").name("getVersion")
						.line("return version;")
						.build())
				.method(EditableJavaMethod.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.parameter("type", "String.class")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic()
						.returnType("String").name("test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build())
				.build();
		assertEquals(javaClass, clazz);
	}
}
