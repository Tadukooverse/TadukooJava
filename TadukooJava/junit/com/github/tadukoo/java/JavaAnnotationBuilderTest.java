package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationBuilderTest{
	
	private static class TestJavaAnnotation extends JavaAnnotation{
		private TestJavaAnnotation(String name){
			super(false, name);
		}
	}
	
	private static class TestJavaAnnotationBuilder extends JavaAnnotationBuilder<TestJavaAnnotation>{
		
		@Override
		protected TestJavaAnnotation constructAnnotation(){
			return new TestJavaAnnotation(name);
		}
	}
	
	@Test
	public void testBuilderName(){
		TestJavaAnnotation annotation = new TestJavaAnnotationBuilder().name("Test").build();
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testBuilderMissingName(){
		try{
			new TestJavaAnnotationBuilder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
}
