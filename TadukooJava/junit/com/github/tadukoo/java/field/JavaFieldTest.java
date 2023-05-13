package com.github.tadukoo.java.field;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaFieldTest{
	
	private static class TestJavaField extends JavaField{
		
		protected TestJavaField(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal,
				String type, String name, String value){
			super(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	private static class TestJavaFieldBuilder extends JavaFieldBuilder<TestJavaField>{
		
		private final boolean editable;
		
		public TestJavaFieldBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaField constructField(){
			return new TestJavaField(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	private JavaField field;
	private String type;
	private String name;
	
	@BeforeEach
	public void setup(){
		type = "int";
		name = "test";
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.build();
	}
	
	@Test
	public void testGetJavaType(){
		assertEquals(JavaCodeTypes.FIELD, field.getJavaCodeType());
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(field.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		field = new TestJavaFieldBuilder(true)
				.type(type).name(name)
				.build();
		assertTrue(field.isEditable());
	}
	
	@Test
	public void testToString(){
		assertEquals("int test;", field.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.build();
		String javaString = """
				/**
				 */
				int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithSingleAnnotation(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.annotation(test)
				.build();
		String javaString = """
				@Test
				int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.annotation(test).annotation(derp)
				.build();
		String javaString = """
				@Test
				@Derp
				int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithVisibility(){
		field = new TestJavaFieldBuilder(false)
				.visibility(Visibility.PRIVATE)
				.type(type).name(name)
				.build();
		String javaString = """
				private int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithIsStatic(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.isStatic()
				.build();
		String javaString = """
				static int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithIsFinal(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.isFinal()
				.build();
		String javaString = """
				final int test;""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithValue(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.value("42")
				.build();
		assertEquals("int test = 42;", field.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.value("42")
				.build();
		String javaString = """
				/**
				 */
				@Test
				@Derp
				private static final int test = 42;""";
		assertEquals(javaString, field.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.value("42")
				.build();
		JavaField otherField = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.value("42")
				.build();
		assertEquals(field, otherField);
	}
	
	@Test
	public void testEqualsNotEqual(){
		field = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.value("42")
				.build();
		JavaField otherField = new TestJavaFieldBuilder(false)
				.type(type).name(name)
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.value("41")
				.build();
		assertNotEquals(field, otherField);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(field, "testing");
	}
}
