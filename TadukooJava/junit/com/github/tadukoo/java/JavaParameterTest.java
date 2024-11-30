package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaParameterTest{
	
	@Test
	public void testType(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		JavaParameter parameter = JavaParameter.builder()
				.type(type)
				.name("text")
				.build();
		assertEquals(type, parameter.getType());
	}
	
	@Test
	public void testName(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		JavaParameter parameter = JavaParameter.builder()
				.type(type)
				.name("text")
				.build();
		assertEquals("text", parameter.getName());
	}
	
	@Test
	public void testVarargDefault(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		JavaParameter parameter = JavaParameter.builder()
				.type(type)
				.name("text")
				.build();
		assertFalse(parameter.isVararg());
	}
	
	@Test
	public void testVarargSet(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		JavaParameter parameter = JavaParameter.builder()
				.type(type)
				.name("text")
				.vararg()
				.build();
		assertTrue(parameter.isVararg());
	}
	
	@Test
	public void testTypeEmpty(){
		try{
			JavaParameter.builder()
					.name("text")
					.build();
		}catch(IllegalArgumentException e){
			assertEquals("type can't be null!", e.getMessage());
		}
	}
	
	@Test
	public void testNameEmpty(){
		try{
			JavaParameter.builder()
					.type(JavaType.builder()
							.baseType("String")
							.build())
					.build();
		}catch(IllegalArgumentException e){
			assertEquals("name can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testAllErrors(){
		try{
			JavaParameter.builder()
					.build();
		}catch(IllegalArgumentException e){
			assertEquals("""
					type can't be null!
					name can't be empty!""", e.getMessage());
		}
	}
}
