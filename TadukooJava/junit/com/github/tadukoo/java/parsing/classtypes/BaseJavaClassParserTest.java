package com.github.tadukoo.java.parsing.classtypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaClassParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaClass, JavaParsingException> parseMethod;
	
	protected BaseJavaClassParserTest(ThrowingFunction<String, JavaClass, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	@Test
	public void testSimpleClass() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
	
	@Test
	public void testClassWithJavadoc() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				/**
				 * Something here
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.1
				 */
				class Test{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Something here")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithSingleAnnotation() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
	public void testAbstractClass() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				abstract class Test{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.isAbstract()
						.className("Test")
						.build(),
				clazz);
		assertEquals("""
				abstract class Test{
				\t
				}
				""", clazz.toString());
	}
	
	@Test
	public void testStaticClass() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
	public void testClassWithModifiersReversed() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				final static private class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.innerClass()
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.className("Test")
						.build(),
				clazz);
		assertEquals("""
				private static final class Test{
				\t
				}
				""", clazz.toString());
	}
	
	@Test
	public void testClassWithSuperClass() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
	public void testClassImplementsInterface() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test implements Derp{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.implementsInterfaceName("Derp")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassImplementsMultipleInterfaces() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test implements Derp, Blah{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.implementsInterfaceName("Derp")
						.implementsInterfaceName("Blah")
						.build(),
				clazz);
	}
	
	@Test
	public void testClassWithSingleLineComment() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test{
					// some comment
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("some comment")
								.build())
						.build(),
				clazz);
		assertEquals("""
				class Test{
				\t
					// some comment
				}
				""", clazz.toString());
	}
	
	@Test
	public void testClassWithMultiLineComment() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test{
					/*
					 * some comment
					 */
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("some comment")
								.build())
						.build(),
				clazz);
		assertEquals("""
				class Test{
				\t
					/*
					 * some comment
					 */
				}
				""", clazz.toString());
	}
	
	@Test
	public void testClassWithInnerClass() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
				package com.example;
				
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				/**
				 * Something here
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.1
				 */
				@Example
				@Test(true)
				@Yep(type=String.class)
				public static final class Test extends Derp implements Blah, Foo{
					
					// some comment
					/*
					 * some comment
					 */
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
				.javadoc(EditableJavadoc.builder()
						.content("Something here")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.1")
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
				.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("some comment")
						.build())
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
		JavaClass clazz = parseMethod.apply("""
				package com.example;
				
				import com.example.Something;
				
				import org.example.SomethingElse;
				
				import static com.example.SomethingStatic;
				
				import static org.example.SomethingElseStatic;
				
				/**
				 * Something here
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.1
				 */
				@Example
				@Test(true)
				@Yep(type=String.class)
				public final class Test extends Derp implements Blah, Foo{
					
					// some comment
					/*
					 * some comment
					 */
					/**
					 * Something here
					 *\s
					 * @author Logan Ferree (Tadukoo)
					 * @version Alpha v.0.1
					 */
					@Test
					@Yep(true)
					private static final class Derp extends Blah implements Blah, Foo{
						// some comment
						/*
						 * some comment
						 */
						@Test
						@Derp(type = String.class)
						protected static final String derp = "Blah";
						/** It's something alright */
						@Blah
						protected int something;
						
						/**
						 * @return the Version
						 */
						@Blah
						private int getVersion(){return version;}
						
						@Test
						@Derp(type=String.class)
						private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
					}
					
					/**
					 * Something here
					 *\s
					 * @author Logan Ferree (Tadukoo)
					 * @version Alpha v.0.1
					 */
					@Derp
					@Yep(something="no")
					protected static final class Yep extends Something implements Blah, Foo{
						// some comment
						/*
						 * some comment
						 */
						public static final String someName = "Test";
						/** This is something else */
						private int somethingElse;
						
						/**
						 * @return the Version
						 */
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
				.javadoc(EditableJavadoc.builder()
						.content("Something here")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.1")
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
				.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("some comment")
						.build())
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.javadoc(EditableJavadoc.builder()
								.content("Something here")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.build())
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
						.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("some comment")
								.build())
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("some comment")
								.build())
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
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("It's something alright")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Blah")
										.build())
								.visibility(Visibility.PROTECTED)
								.type("int").name("something")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.returnVal("the Version")
										.build())
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
						.javadoc(EditableJavadoc.builder()
								.content("Something here")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.build())
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
						.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("some comment")
								.build())
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("some comment")
								.build())
						.field(EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("someName")
								.value("\"Test\"")
								.build())
						.field(EditableJavaField.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("This is something else")
										.build())
								.visibility(Visibility.PRIVATE)
								.type("int").name("somethingElse")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.returnVal("the Version")
										.build())
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
	
	/*
	 * Error Cases
	 */
	
	@Test
	public void testClassNameFail(){
		try{
			parseMethod.apply("""
					class""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to find class name!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testExtendsAfterBlockOpen(){
		try{
			parseMethod.apply("""
					class Test{ extends
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"found 'extends' after hitting the block open token!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testSuperClassNameFail(){
		try{
			parseMethod.apply("""
					class Test extends""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to find super class name after 'extends'!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImplementsAfterBlockOpen(){
		try{
			parseMethod.apply("""
					class Test{ implements
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"found 'implements' after hitting the block open token!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImplementsInterfaceNameFail(){
		try{
			parseMethod.apply("""
					class Test implements""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to find implements interface name after 'implements' or ','!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImplementsCommaBeforeInterfaceName(){
		try{
			parseMethod.apply("""
					class Test implements , {
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered ',' before any interface names!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testCommaAsSeparateTokenInterfaceNamesThenNoFollowingInterfaceName(){
		try{
			parseMethod.apply("""
					class Test implements Test ,""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to find implements interface name after 'implements' or ','!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testBlockOpenTwice(){
		try{
			parseMethod.apply("""
					class Test{{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"We hit the block open token twice for the same class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testUnknownInnerType(){
		try{
			parseMethod.apply("""
					class Test{
						something yep it is
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Unable to determine token: 'something'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testHangingInnerJavadoc(){
		try{
			parseMethod.apply("""
					class Test{
						/** {@inheritDoc} */
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Found Javadoc at end of class with nothing to attach it to!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testHangingInnerAnnotation(){
		try{
			parseMethod.apply("""
					class Test{
						@Test
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Found annotations at end of class with nothing to attach them to!"),
					e.getMessage());
		}
	}
	
	/*
	 * Whitespace Base Cases
	 */
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
		JavaClass clazz = parseMethod.apply("""
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
	
	@Test
	public void testWhitespaceAnnotationInsane() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				\t  \t @ \t  \tTest\t  (\t \ttype \t =\s
				  \t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t
				
				class Test{
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build())
						.className("Test")
						.build(),
				clazz);
	}
	
	@Test
	public void testCommaAtStartOfInterfaceNameToken() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test implements Test ,Derp{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.implementsInterfaceName("Test")
						.implementsInterfaceName("Derp")
						.build(),
				clazz);
	}
	
	@Test
	public void testCommaAsSeparateTokenInterfaceNames() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test implements Test , Derp{
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.implementsInterfaceName("Test")
						.implementsInterfaceName("Derp")
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceFieldInsane() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test{
					\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
				\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
				  \t
				 \t    \t@       Derp   \t
				   \t public\t     \t
				   \t static\t     \t
				   \t final\t     \t  \t
				 \tString \t  \t
				 \t  type    \t   \t
				  \t =    \t   \t
				  \t "" \t  \t
				\t;     \t
				 \t  \s
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("type")
								.value("\"\"")
								.build())
						.build(),
				clazz);
	}
	
	@Test
	public void testWhitespaceMethodInsane() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				class Test{
					\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
					\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
					\t    \t@       Derp   \t
						\t   \t
						\t    private     \t   \t
					\t    static     \t   \t
					\t    String     \t   \t
					\t    test     \t   \t
					\t    (     \t   \t
					\t    String     \t   \t
					\t    type     \t   \t
					\t    ,     \t   \t
					\t    int     \t   \t
					\t    derp     \t   \t
					\t    )     \t   \t
					\t    throws     \t   \t
					\t    Exception     \t   \t
					\t    ,     \t   \t
					\t    Throwable     \t   \t
					\t    {     \t   \t
					\t    doSomething()     \t   \t
					\t    ;     \t   \t
					\t    doSomethingElse()     \t   \t
					\t    ;     \t   \t
					\t    }     \t   \t
					\t    \s
				}""");
		assertEquals(
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
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
						.build(),
				clazz);
	}
	
	/*
	 * Test them all combined
	 */
	
	@Test
	public void testClassWithInsaneWhitespaceEverywhere() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
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
				
				\t  \t @ \t  \tTest\t  (\t \ttype \t =\s
				  \t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t
				
				
				public\t  \t
				\t final\t  \t
				\t    class \t \t
				  \t \tTest{\t   \t
						
				\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
				\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
				  \t
				 \t    \t@       Derp   \t
				   \t public\t     \t
				   \t static\t     \t
				   \t final\t     \t  \t
				 \tString \t  \t
				 \t  type    \t   \t
				  \t =    \t   \t
				  \t "" \t  \t
				\t;     \t
				 \t  \s
				 \t  \t @ \t  \tTest\t  (\t \ttype \t = \t
					\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
					\t    \t@       Derp   \t
						\t   \t
						\t    private     \t   \t
					\t    static     \t   \t
					\t    String     \t   \t
					\t    test     \t   \t
					\t    (     \t   \t
					\t    String     \t   \t
					\t    type     \t   \t
					\t    ,     \t   \t
					\t    int     \t   \t
					\t    derp     \t   \t
					\t    )     \t   \t
					\t    throws     \t   \t
					\t    Exception     \t   \t
					\t    ,     \t   \t
					\t    Throwable     \t   \t
					\t    {     \t   \t
					\t    doSomething()     \t   \t
					\t    ;     \t   \t
					\t    doSomethingElse()     \t   \t
					\t    ;     \t   \t
					\t    }     \t   \t
					\t    \s
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
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build())
						.visibility(Visibility.PUBLIC)
						.isFinal()
						.className("Test")
						.field(EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("type")
								.value("\"\"")
								.build())
						.method(EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
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
						.build(),
				clazz);
	}
	
	/*
	 * Weird Issues
	 */
	
	/*
	 * This exists because when running the parser code in @UltimatePower, the Javadoc was considered condensed.
	 * It has to do with Windows' /r/n usage for newlines, which the \r was treated as a character before the newline,
	 * meaning the Javadoc content started with it
	 */
	@Test
	public void testLabelFormFieldIssue() throws JavaParsingException{
		JavaClass clazz = parseMethod.apply("""
				package com.github.tadukoo.view.form.field.string;
				
				import com.github.tadukoo.view.components.TadukooLabel;
				import com.github.tadukoo.view.form.field.FormField;
				import com.github.tadukoo.view.form.field.annotation.FormFieldBuilder;
				
				import javax.swing.JComponent;
				import javax.swing.JLabel;
				
				/**\r
				 * Represents a {@link FormField} for a Label
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				@FormFieldBuilder(type = String.class, defaultDefaultValue = "\\"\\"")
				public class BetterLabelFormField extends FormField<String>{
				\t
					/** {@inheritDoc} */
					@Override
					public JComponent getJustComponent() throws Throwable{
						return TadukooLabel.builder()
								.text(getDefaultValue())
								.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())
								.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())
								.shapeInfo(getLabelShape()).border(getLabelBorder())
								.fontResourceLoader(getFontResourceLoader())
								.build();
					}
				\t
					/** {@inheritDoc} */
					@Override
					public String getValueFromJustComponent(JComponent component){
						if(component instanceof JLabel label){
							return label.getText();
						}
						return null;
					}
				}
				""");
		assertEquals(
				EditableJavaClass.builder()
						.packageName("com.github.tadukoo.view.form.field.string")
						.importNames(ListUtil.createList(
								"com.github.tadukoo.view.components.TadukooLabel",
								"com.github.tadukoo.view.form.field.FormField",
								"com.github.tadukoo.view.form.field.annotation.FormFieldBuilder",
								"javax.swing.JComponent",
								"javax.swing.JLabel"
						), false)
						.javadoc(EditableJavadoc.builder()
										.content("Represents a {@link FormField} for a Label")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.4")
										.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("FormFieldBuilder")
								.parameter("type", "String.class")
								.parameter("defaultDefaultValue", "\"\\\"\\\"\"")
								.build())
						.visibility(Visibility.PUBLIC).className("BetterLabelFormField").superClassName("FormField<String>")
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder().condensed().content("{@inheritDoc}").build())
								.annotation(EditableJavaAnnotation.builder().name("Override").build())
								.visibility(Visibility.PUBLIC).returnType("JComponent").name("getJustComponent")
								.throwType("Throwable")
								.line("return TadukooLabel.builder()")
								.line("\t\t.text(getDefaultValue())")
								.line("\t\t.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())")
								.line("\t\t.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())")
								.line("\t\t.shapeInfo(getLabelShape()).border(getLabelBorder())")
								.line("\t\t.fontResourceLoader(getFontResourceLoader())")
								.line("\t\t.build();")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder().condensed().content("{@inheritDoc}").build())
								.annotation(EditableJavaAnnotation.builder().name("Override").build())
								.visibility(Visibility.PUBLIC).returnType("String").name("getValueFromJustComponent")
								.parameter("JComponent", "component")
								.line("if(component instanceof JLabel label){")
								.line("\treturn label.getText();")
								.line("}")
								.line("return null;")
								.build())
						.build(),
				clazz);
		assertEquals("""
				package com.github.tadukoo.view.form.field.string;
				
				import com.github.tadukoo.view.components.TadukooLabel;
				import com.github.tadukoo.view.form.field.FormField;
				import com.github.tadukoo.view.form.field.annotation.FormFieldBuilder;
				
				import javax.swing.JComponent;
				import javax.swing.JLabel;
				
				/**
				 * Represents a {@link FormField} for a Label
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				@FormFieldBuilder(type = String.class, defaultDefaultValue = "\\"\\"")
				public class BetterLabelFormField extends FormField<String>{
				\t
					/** {@inheritDoc} */
					@Override
					public JComponent getJustComponent() throws Throwable{
						return TadukooLabel.builder()
								.text(getDefaultValue())
								.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())
								.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())
								.shapeInfo(getLabelShape()).border(getLabelBorder())
								.fontResourceLoader(getFontResourceLoader())
								.build();
					}
				\t
					/** {@inheritDoc} */
					@Override
					public String getValueFromJustComponent(JComponent component){
						if(component instanceof JLabel label){
							return label.getText();
						}
						return null;
					}
				}
				""",
				clazz.toString());
	}
}
