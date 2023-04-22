package com.github.tadukoo.java.method;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaMethodTest{
	
	private static class TestJavaMethod extends JavaMethod{
		
		private TestJavaMethod(
				boolean editable, String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, String returnType, String name,
				List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
			super(editable, sectionComment, javadoc, annotations,
					visibility, isStatic, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	private static class TestJavaMethodBuilder extends JavaMethodBuilder<TestJavaMethod>{
		
		private final boolean editable;
		
		private TestJavaMethodBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaMethod constructMethod(){
			return new TestJavaMethod(editable, sectionComment, javadoc, annotations,
					visibility, isStatic, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	private JavaMethod method;
	private String returnType;
	
	@BeforeEach
	public void setup(){
		returnType = "int";
		method = new TestJavaMethodBuilder(false)
				.returnType(returnType)
				.build();
	}
	
	@Test
	public void testGetJavaType(){
		assertEquals(JavaTypes.METHOD, method.getJavaType());
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(method.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		method = new TestJavaMethodBuilder(true)
				.returnType(returnType)
				.build();
		assertTrue(method.isEditable());
	}
	
	@Test
	public void testToString(){
		String javaString = """
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSectionComment(){
		method = new TestJavaMethodBuilder(false)
				.returnType(returnType)
				.sectionComment("Test comment")
				.build();
		String javaString = """
				/*
				 * Test comment
				 */
				
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		method = new TestJavaMethodBuilder(false)
				.returnType(returnType)
				.javadoc(UneditableJavadoc.builder().build())
				.build();
		String javaString = """
				/**
				 */
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleAnnotation(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		method = new TestJavaMethodBuilder(false).returnType(returnType).annotation(test).build();
		String javaString = """
				@Test
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		method = new TestJavaMethodBuilder(false).returnType(returnType).annotation(test).annotation(derp).build();
		String javaString = """
				@Test
				@Derp
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithIsStatic(){
		method = new TestJavaMethodBuilder(false)
				.returnType(returnType).isStatic()
				.build();
		String javaString = """
				public static int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithName(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).name("someMethod").build();
		String javaString = """
				public int someMethod(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleParameter(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).parameter("String", "text").build();
		String javaString = """
				public int(String text){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithParameters(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).parameter("String", "text")
				.parameter("int", "something").build();
		String javaString = """
				public int(String text, int something){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleThrowType(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).throwType("Throwable").build();
		String javaString = """
				public int() throws Throwable{
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithThrowTypes(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).throwType("Throwable").throwType("Exception").build();
		String javaString = """
				public int() throws Throwable, Exception{
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithLines(){
		method = new TestJavaMethodBuilder(false).returnType(returnType).line("doSomething();").line("return 42;").build();
		String javaString = """
				public int(){
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		method = new TestJavaMethodBuilder(false).returnType(returnType)
				.sectionComment("Test comment")
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		String javaString = """
				/*
				 * Test comment
				 */
				
				/**
				 */
				@Test
				@Derp
				public static int someMethod(String text, int something) throws Throwable, Exception{
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
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		method = new TestJavaMethodBuilder(false).returnType(returnType)
				.sectionComment("Test comment")
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		JavaMethod otherMethod = new TestJavaMethodBuilder(false).returnType(returnType)
				.sectionComment("Test comment")
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		assertEquals(method, otherMethod);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		method = new TestJavaMethodBuilder(false).returnType(returnType)
				.sectionComment("Test comment")
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic()
				.parameter("String", "text").parameter("int", "something")
				.throwType("Throwable").throwType("Exception")
				.line("doSomething();").line("return 42;").build();
		JavaMethod otherMethod = new TestJavaMethodBuilder(false).returnType(returnType)
				.sectionComment("Test comment")
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(test).annotation(derp).name("someMethod")
				.isStatic()
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
