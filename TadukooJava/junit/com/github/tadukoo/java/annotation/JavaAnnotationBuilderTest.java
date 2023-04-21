package com.github.tadukoo.java.annotation;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationBuilderTest{
	
	private static class TestJavaAnnotation extends JavaAnnotation{
		private TestJavaAnnotation(String name, List<Pair<String, String>> parameters){
			super(false, name, parameters);
		}
	}
	
	private static class TestJavaAnnotationBuilder extends JavaAnnotationBuilder<TestJavaAnnotation>{
		
		@Override
		protected TestJavaAnnotation constructAnnotation(){
			return new TestJavaAnnotation(name, parameters);
		}
	}
	
	private JavaAnnotation annotation;
	private String name;
	
	@BeforeEach
	public void setup(){
		name = "Test";
		annotation = new TestJavaAnnotationBuilder()
				.name(name)
				.build();
	}
	
	@Test
	public void testBuilderDefaultParameters(){
		assertEquals(new ArrayList<>(), annotation.getParameters());
	}
	
	@Test
	public void testBuilderName(){
		TestJavaAnnotation annotation = new TestJavaAnnotationBuilder().name("Test").build();
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testBuilderAddParameterPieces(){
		annotation = new TestJavaAnnotationBuilder()
				.name(name)
				.parameter("test", "true")
				.parameter("derp", "false")
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
	}
	
	@Test
	public void testBuilderAddParameterPair(){
		annotation = new TestJavaAnnotationBuilder()
				.name(name)
				.parameter(Pair.of("test", "true"))
				.parameter(Pair.of("derp", "false"))
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
	}
	
	@Test
	public void testBuilderSetParameters(){
		annotation = new TestJavaAnnotationBuilder()
				.name(name)
				.parameters(ListUtil.createList(
						Pair.of("test", "true"), Pair.of("derp", "false")))
				.build();
		assertEquals(ListUtil.createList(
				Pair.of("test", "true"), Pair.of("derp", "false")
		), annotation.getParameters());
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
