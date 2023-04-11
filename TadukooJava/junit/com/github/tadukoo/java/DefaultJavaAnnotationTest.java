package com.github.tadukoo.java;

import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaAnnotationTest<AnnotationType extends JavaAnnotation>{
	private final ThrowingSupplier<JavaAnnotationBuilder<AnnotationType>, NoException> builder;
	protected String name;
	protected AnnotationType annotation;
	
	protected DefaultJavaAnnotationTest(ThrowingSupplier<JavaAnnotationBuilder<AnnotationType>, NoException> builder){
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		name = "Test";
		annotation = builder.get().name(name).build();
	}
	
	@Test
	public void testBuilderName(){
		assertEquals(name, annotation.getName());
	}
	
	@Test
	public void testBuilderMissingName(){
		try{
			annotation = builder.get().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("@Test", annotation.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		AnnotationType otherAnnotation = builder.get().name(name).build();
		assertEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsNotEqual(){
		AnnotationType otherAnnotation = builder.get().name("Testing").build();
		assertNotEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(annotation, "testing");
	}
}
