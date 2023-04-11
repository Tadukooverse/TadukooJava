package com.github.tadukoo.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaAnnotationTest{
	
	private static class TestJavaAnnotation extends JavaAnnotation{
		private TestJavaAnnotation(boolean editable, String name){
			super(editable, name);
		}
	}
	
	private static class TestJavaAnnotationBuilder extends JavaAnnotationBuilder<TestJavaAnnotation>{
		
		private final boolean editable;
		
		private TestJavaAnnotationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaAnnotation constructAnnotation(){
			return new TestJavaAnnotation(editable, name);
		}
	}
	private JavaAnnotation annotation;
	private String name;
	
	@BeforeEach
	public void setup(){
		name = "Test";
		annotation = new TestJavaAnnotationBuilder(true).name(name).build();
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(annotation.isEditable());
	}
	
	@Test
	public void testSetNotEditable(){
		annotation = new TestJavaAnnotationBuilder(false).name(name).build();
		assertFalse(annotation.isEditable());
	}
	
	@Test
	public void testGetName(){
		assertEquals(name, annotation.getName());
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
		JavaAnnotation otherAnnotation = new TestJavaAnnotationBuilder(true).name(name).build();
		assertEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaAnnotation otherAnnotation = new TestJavaAnnotationBuilder(true).name("Testing").build();
		assertNotEquals(annotation, otherAnnotation);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(annotation, "testing");
	}
}
