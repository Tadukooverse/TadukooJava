package com.github.tadukoo.java.method;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaMethodBuilderTest{
	
	private static class TestJavaMethod extends JavaMethod{
		
		private TestJavaMethod(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal, String returnType, String name,
				List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
			super(editable, javadoc, annotations,
					visibility, isStatic, isFinal, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	private static class TestJavaMethodBuilder extends JavaMethodBuilder<TestJavaMethod>{
		
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaMethod constructMethod(){
			return new TestJavaMethod(false, javadoc, annotations,
					visibility, isStatic, isFinal, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	private JavaMethod method;
	private String returnType;
	
	@BeforeEach
	public void setup(){
		returnType = "int";
		method = new TestJavaMethodBuilder()
				.returnType(returnType)
				.build();
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
		assertEquals(Visibility.NONE, method.getVisibility());
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
	public void testBuilderSetJavadoc(){
		Javadoc javadoc = UneditableJavadoc.builder().build();
		method = new TestJavaMethodBuilder()
				.returnType(returnType)
				.javadoc(javadoc)
				.build();
		assertEquals(javadoc, method.getJavadoc());
	}
	
	@Test
	public void testBuilderSetAnnotations(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		method = new TestJavaMethodBuilder().annotations(annotations).returnType("String").build();
		assertEquals(annotations, method.getAnnotations());
	}
	
	@Test
	public void testBuilderSetAnnotation(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		method = new TestJavaMethodBuilder().annotation(test).returnType("String").build();
		List<JavaAnnotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testBuilderSetVisibility(){
		method = new TestJavaMethodBuilder().visibility(Visibility.PRIVATE).returnType("String").build();
		assertEquals(Visibility.PRIVATE, method.getVisibility());
	}
	
	@Test
	public void testBuilderIsStatic(){
		method = new TestJavaMethodBuilder()
				.isStatic()
				.returnType("String")
				.build();
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		method = new TestJavaMethodBuilder()
				.isStatic(false).returnType("String")
				.build();
		assertFalse(method.isStatic());
		
		method = new TestJavaMethodBuilder()
				.isStatic(true).returnType("String")
				.build();
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testBuilderIsFinal(){
		method = new TestJavaMethodBuilder()
				.isFinal()
				.returnType("String")
				.build();
		assertTrue(method.isFinal());
	}
	
	@Test
	public void testBuilderSetIsFinal(){
		method = new TestJavaMethodBuilder()
				.isFinal(false).returnType("String")
				.build();
		assertFalse(method.isFinal());
		
		method = new TestJavaMethodBuilder()
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
		method = new TestJavaMethodBuilder().name("someName").returnType(returnType).build();
		assertEquals("someName", method.getName());
	}
	
	@Test
	public void testBuilderSetParameters(){
		List<Pair<String, String>> parameters = ListUtil.createList(Pair.of("int", "someInt"),
				Pair.of("String", "someText"));
		method = new TestJavaMethodBuilder().returnType(returnType).parameters(parameters).build();
		assertEquals(parameters, method.getParameters());
	}
	
	@Test
	public void testBuilderSetParameterPair(){
		method = new TestJavaMethodBuilder().returnType(returnType).parameter(Pair.of("String", "someText")).build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testBuilderSetParameter(){
		method = new TestJavaMethodBuilder().returnType(returnType).parameter("String", "someText").build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testBuilderSetThrowTypes(){
		List<String> throwTypes = ListUtil.createList("Throwable", "Exception");
		method = new TestJavaMethodBuilder()
				.returnType(returnType).throwTypes(throwTypes)
				.build();
		assertEquals(throwTypes, method.getThrowTypes());
	}
	
	@Test
	public void testBuilderSetThrowType(){
		method = new TestJavaMethodBuilder()
				.returnType(returnType).throwType("Throwable")
				.build();
		List<String> throwTypes = method.getThrowTypes();
		assertEquals(1, throwTypes.size());
		assertEquals("Throwable", throwTypes.get(0));
	}
	
	@Test
	public void testBuilderSetLines(){
		List<String> lines = ListUtil.createList("doSomething();", "return 42;");
		method = new TestJavaMethodBuilder().returnType(returnType).lines(lines).build();
		assertEquals(lines, method.getLines());
	}
	
	@Test
	public void testBuilderSetLine(){
		method = new TestJavaMethodBuilder().returnType(returnType).line("return 42;").build();
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testBuilderNullVisibility(){
		try{
			method = new TestJavaMethodBuilder()
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
			method = new TestJavaMethodBuilder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify returnType!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllErrors(){
		try{
			method = new TestJavaMethodBuilder()
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!\n" +
					"Must specify returnType!", e.getMessage());
		}
	}
}
