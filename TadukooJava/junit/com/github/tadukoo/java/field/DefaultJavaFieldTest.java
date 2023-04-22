package com.github.tadukoo.java.field;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaFieldTest<FieldType extends JavaField>{
	
	private final ThrowingSupplier<JavaFieldBuilder<FieldType>, NoException> builder;
	private final ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder;
	private final ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder;
	protected FieldType field;
	protected String type, name;
	
	protected DefaultJavaFieldTest(
			ThrowingSupplier<JavaFieldBuilder<FieldType>, NoException> builder,
			ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder,
			ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder){
		this.builder = builder;
		this.javadocBuilder = javadocBuilder;
		this.javaAnnotationBuilder = javaAnnotationBuilder;
	}
	
	@BeforeEach
	public void setup(){
		type = "int";
		name = "test";
		field = builder.get()
				.type(type).name(name)
				.build();
	}
	
	@Test
	public void testGetJavaType(){
		assertEquals(JavaTypes.FIELD, field.getJavaType());
	}
	
	@Test
	public void testDefaultJavadoc(){
		assertNull(field.getJavadoc());
	}
	
	@Test
	public void testDefaultAnnotations(){
		assertTrue(field.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		assertEquals(Visibility.NONE, field.getVisibility());
	}
	
	@Test
	public void testDefaultIsStatic(){
		assertFalse(field.isStatic());
	}
	
	@Test
	public void testDefaultIsFinal(){
		assertFalse(field.isFinal());
	}
	
	@Test
	public void testDefaultValue(){
		assertNull(field.getValue());
	}
	
	@Test
	public void testSetType(){
		assertEquals("int", field.getType());
	}
	
	@Test
	public void testSetName(){
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testBuilderSetJavadoc(){
		Javadoc doc = javadocBuilder.get().build();
		field = builder.get()
				.type(type).name(name)
				.javadoc(doc)
				.build();
		assertEquals(doc, field.getJavadoc());
	}
	
	@Test
	public void testBuilderSetAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		field = builder.get()
				.annotations(annotations)
				.type(type).name(name)
				.build();
		assertEquals(annotations, field.getAnnotations());
	}
	
	@Test
	public void testBuilderSetAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		field = builder.get()
				.annotation(test)
				.type(type).name("Test").build();
		List<JavaAnnotation> annotations = field.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testBuilderSetVisibility(){
		field = builder.get()
				.visibility(Visibility.PUBLIC).type(type).name(name)
				.build();
		
		assertEquals(Visibility.PUBLIC, field.getVisibility());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		field = builder.get()
				.type(type).name(name).isStatic(false)
				.build();
		assertFalse(field.isStatic());
		
		field = builder.get()
				.type(type).name(name).isStatic(true)
				.build();
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testBuilderIsStatic(){
		field = builder.get()
				.type(type).name(name).isStatic()
				.build();
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testBuilderSetIsFinal(){
		field = builder.get()
				.type(type).name(name).isFinal(false)
				.build();
		assertFalse(field.isFinal());
		
		field = builder.get()
				.type(type).name(name).isFinal(true)
				.build();
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testBuilderIsFinal(){
		field = builder.get()
				.type(type).name(name).isFinal()
				.build();
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testBuilderSetValue(){
		field = builder.get()
				.type(type).name(name)
				.value("42")
				.build();
		assertEquals("42", field.getValue());
	}
	
	@Test
	public void testNullVisibility(){
		try{
			field = builder.get()
					.type(type).name(name)
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!", e.getMessage());
		}
	}
	
	@Test
	public void testNullType(){
		try{
			field = builder.get()
					.name(name)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify type!", e.getMessage());
		}
	}
	
	@Test
	public void testNullName(){
		try{
			field = builder.get()
					.type(type)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderErrors(){
		try{
			field = builder.get()
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Visibility is required!
					Must specify type!
					Must specify name!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("int test", field.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		field = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.build();
		String javaString = """
				/**
				 */
				int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithSingleAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		field = builder.get()
				.type(type).name(name)
				.annotation(test)
				.build();
		String javaString = """
				@Test
				int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		field = builder.get()
				.type(type).name(name)
				.annotation(test).annotation(derp)
				.build();
		String javaString = """
				@Test
				@Derp
				int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithVisibility(){
		field = builder.get()
				.visibility(Visibility.PRIVATE)
				.type(type).name(name)
				.build();
		String javaString = """
				private int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithIsStatic(){
		field = builder.get()
				.type(type).name(name)
				.isStatic()
				.build();
		String javaString = """
				static int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithIsFinal(){
		field = builder.get()
				.type(type).name(name)
				.isFinal()
				.build();
		String javaString = """
				final int test""";
		assertEquals(javaString, field.toString());
	}
	
	@Test
	public void testToStringWithValue(){
		field = builder.get()
				.type(type).name(name)
				.value("42")
				.build();
		assertEquals("int test = 42", field.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		field = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic()
				.isFinal()
				.value("42")
				.build();
		String javaString = """
				/**
				 */
				@Test
				@Derp
				private static final int test = 42""";
		assertEquals(javaString, field.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		field = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic()
				.isFinal()
				.value("42")
				.build();
		JavaField otherField = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic()
				.isFinal()
				.value("42")
				.build();
		assertEquals(field, otherField);
	}
	
	@Test
	public void testEqualsNotEqual(){
		field = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic()
				.isFinal()
				.value("42")
				.build();
		JavaField otherField = builder.get()
				.type(type).name(name)
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PRIVATE)
				.isStatic()
				.isFinal()
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
