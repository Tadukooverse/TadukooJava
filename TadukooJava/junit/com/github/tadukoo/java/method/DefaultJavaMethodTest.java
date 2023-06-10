package com.github.tadukoo.java.method;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaMethodTest<MethodType extends JavaMethod>{
	
	private final ThrowingSupplier<JavaMethodBuilder<MethodType>, NoException> builder;
	private final ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder;
	private final ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder;
	
	protected MethodType method;
	protected String returnType;
	
	protected DefaultJavaMethodTest(
			ThrowingSupplier<JavaMethodBuilder<MethodType>, NoException> builder,
			ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder,
			ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder){
		this.builder = builder;
		this.javadocBuilder = javadocBuilder;
		this.javaAnnotationBuilder = javaAnnotationBuilder;
	}
	
	@BeforeEach
	public void setup(){
		returnType = "int";
		method = builder.get().returnType(returnType).build();
	}
	
	@Test
	public void testGetJavaType(){
		assertEquals(JavaCodeTypes.METHOD, method.getJavaCodeType());
	}
	
	@Test
	public void testDefaultJavadoc(){
		assertNull(method.getJavadoc());
	}
	
	@Test
	public void testDefaultAnnotations(){
		assertTrue(method.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		Assertions.assertEquals(Visibility.NONE, method.getVisibility());
	}
	
	@Test
	public void testDefaultIsStatic(){
		assertFalse(method.isStatic());
	}
	
	@Test
	public void testDefaultIsFinal(){
		assertFalse(method.isFinal());
	}
	
	@Test
	public void testDefaultName(){
		assertNull(method.getName());
	}
	
	@Test
	public void testDefaultParameters(){
		assertTrue(method.getParameters().isEmpty());
	}
	
	@Test
	public void testDefaultThrowTypes(){
		assertTrue(method.getThrowTypes().isEmpty());
	}
	
	@Test
	public void testDefaultLines(){
		assertTrue(method.getLines().isEmpty());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaMethod otherMethod = builder.get()
				.javadoc(javadocBuilder.get()
						.build())
				.annotation(javaAnnotationBuilder.get()
						.name("Test")
						.build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.returnType(returnType).name("test")
				.parameter("String", "type")
				.parameter("int", "derp")
				.throwType("Throwable")
				.throwType("Exception")
				.line("doSomething();")
				.line("doSomethingElse();")
				.build();
		method = builder.get()
				.copy(otherMethod)
				.build();
		assertEquals(otherMethod, method);
	}
	
	@Test
	public void testBuilderSetJavadoc(){
		Javadoc javadoc = javadocBuilder.get().build();
		method = builder.get()
				.returnType(returnType)
				.javadoc(javadoc)
				.build();
		assertEquals(javadoc, method.getJavadoc());
	}
	
	@Test
	public void testBuilderSetAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		method = builder.get().annotations(annotations).returnType("String").build();
		assertEquals(annotations, method.getAnnotations());
	}
	
	@Test
	public void testBuilderSetAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		method = builder.get().annotation(test).returnType("String").build();
		List<JavaAnnotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testBuilderSetVisibility(){
		method = builder.get().visibility(Visibility.PRIVATE).returnType("String").build();
		assertEquals(Visibility.PRIVATE, method.getVisibility());
	}
	
	@Test
	public void testBuilderIsStatic(){
		method = builder.get()
				.isStatic()
				.returnType("String")
				.build();
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		method = builder.get()
				.isStatic(false).returnType("String")
				.build();
		assertFalse(method.isStatic());
		
		method = builder.get()
				.isStatic(true).returnType("String")
				.build();
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testBuilderIsFinal(){
		method = builder.get()
				.isFinal()
				.returnType("String")
				.build();
		assertTrue(method.isFinal());
	}
	
	@Test
	public void testBuilderSetIsFinal(){
		method = builder.get()
				.isFinal(false).returnType("String")
				.build();
		assertFalse(method.isFinal());
		
		method = builder.get()
				.isFinal(true).returnType("String")
				.build();
		assertTrue(method.isFinal());
	}
	
	@Test
	public void testBuilderSetReturnType(){
		assertEquals("int", method.getReturnType());
	}
	
	@Test
	public void testBuilderSetName(){
		method = builder.get().name("someName").returnType(returnType).build();
		assertEquals("someName", method.getName());
	}
	
	@Test
	public void testBuilderSetParameters(){
		List<Pair<String, String>> parameters = ListUtil.createList(Pair.of("int", "someInt"),
				Pair.of("String", "someText"));
		method = builder.get().returnType(returnType).parameters(parameters).build();
		assertEquals(parameters, method.getParameters());
	}
	
	@Test
	public void testBuilderSetParameterPair(){
		method = builder.get().returnType(returnType).parameter(Pair.of("String", "someText")).build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testBuilderSetParameter(){
		method = builder.get().returnType(returnType).parameter("String", "someText").build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testBuilderSetThrowTypes(){
		List<String> throwTypes = ListUtil.createList("Throwable", "Exception");
		method = builder.get()
				.returnType(returnType).throwTypes(throwTypes)
				.build();
		assertEquals(throwTypes, method.getThrowTypes());
	}
	
	@Test
	public void testBuilderSetThrowType(){
		method = builder.get()
				.returnType(returnType).throwType("Throwable")
				.build();
		List<String> throwTypes = method.getThrowTypes();
		assertEquals(1, throwTypes.size());
		assertEquals("Throwable", throwTypes.get(0));
	}
	
	@Test
	public void testBuilderSetLines(){
		List<String> lines = ListUtil.createList("doSomething();", "return 42;");
		method = builder.get().returnType(returnType).lines(lines).build();
		assertEquals(lines, method.getLines());
	}
	
	@Test
	public void testBuilderSetLine(){
		method = builder.get().returnType(returnType).line("return 42;").build();
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testBuilderNullVisibility(){
		try{
			method = builder.get()
					.visibility(null)
					.returnType(returnType)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderNullReturnType(){
		try{
			method = builder.get().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify returnType!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			method = builder.get()
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!\n" +
					"Must specify returnType!", e.getMessage());
		}
	}
	
	@Test
	public void testGetUniqueNameConstructor(){
		assertEquals("init()", method.getUniqueName());
	}
	
	@Test
	public void testGetUniqueNameConstructorWithParameter(){
		method = builder.get()
				.returnType(returnType)
				.parameter("String", "name")
				.build();
		assertEquals("init(String name)", method.getUniqueName());
	}
	
	@Test
	public void testGetUniqueNameConstructorWithMultipleParameters(){
		method = builder.get()
				.returnType(returnType)
				.parameter("String", "name")
				.parameter("int", "version")
				.build();
		assertEquals("init(String name, int version)", method.getUniqueName());
	}
	
	@Test
	public void testGetUniqueName(){
		method = builder.get()
				.returnType(returnType).name("test")
				.build();
		assertEquals("test()", method.getUniqueName());
	}
	
	@Test
	public void testGetUniqueNameWithParameter(){
		method = builder.get()
				.returnType(returnType).name("test")
				.parameter("String", "name")
				.build();
		assertEquals("test(String name)", method.getUniqueName());
	}
	
	@Test
	public void testGetUniqueNameWithMultipleParameters(){
		method = builder.get()
				.returnType(returnType).name("test")
				.parameter("String", "name")
				.parameter("int", "version")
				.build();
		assertEquals("test(String name, int version)", method.getUniqueName());
	}
	
	@Test
	public void testToString(){
		String javaString = """
				int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		method = builder.get()
				.returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.build();
		String javaString = """
				/**
				 */
				int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		method = builder.get().returnType(returnType).annotation(test).build();
		String javaString = """
				@Test
				int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		method = builder.get().returnType(returnType).annotation(test).annotation(derp).build();
		String javaString = """
				@Test
				@Derp
				int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithVisibility(){
		method = builder.get()
				.visibility(Visibility.PRIVATE)
				.returnType(returnType)
				.build();
		String javaString = """
				private int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithIsStatic(){
		method = builder.get()
				.returnType(returnType).isStatic()
				.build();
		String javaString = """
				static int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithIsFinal(){
		method = builder.get()
				.returnType(returnType).isFinal()
				.build();
		String javaString = """
				final int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithName(){
		method = builder.get().returnType(returnType).name("someMethod").build();
		String javaString = """
				int someMethod(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleParameter(){
		method = builder.get().returnType(returnType).parameter("String", "text").build();
		String javaString = """
				int(String text){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithParameters(){
		method = builder.get().returnType(returnType).parameter("String", "text")
				.parameter("int", "something").build();
		String javaString = """
				int(String text, int something){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleThrowType(){
		method = builder.get().returnType(returnType).throwType("Throwable").build();
		String javaString = """
				int() throws Throwable{
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithThrowTypes(){
		method = builder.get().returnType(returnType).throwType("Throwable").throwType("Exception").build();
		String javaString = """
				int() throws Throwable, Exception{
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithLines(){
		method = builder.get().returnType(returnType).line("doSomething();").line("return 42;").build();
		String javaString = """
				int(){
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		method = builder.get().returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.annotation(test).annotation(derp).name("someMethod")
				.visibility(Visibility.PUBLIC)
				.isStatic().isFinal()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		String javaString = """
				/**
				 */
				@Test
				@Derp
				public static final int someMethod(String text, int something) throws Throwable, Exception{
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		method = builder.get().returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic().isFinal()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		JavaMethod otherMethod = builder.get().returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic().isFinal()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		assertEquals(method, otherMethod);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		method = builder.get().returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic().isFinal()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		JavaMethod otherMethod = builder.get().returnType(returnType)
				.javadoc(javadocBuilder.get().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic().isFinal()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 41;").build();
		assertNotEquals(method, otherMethod);
	}
	
	@Test
	public void testEqualsDifferentTypes(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(method, "testing");
	}
}
