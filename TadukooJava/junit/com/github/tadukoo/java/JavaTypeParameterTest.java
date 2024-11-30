package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTypeParameterTest{
	
	@Test
	public void testBaseType(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType("String")
				.build();
		assertEquals("String", typeParameter.getBaseType());
	}
	
	@Test
	public void testExtendsType(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("String")
				.build();
		assertEquals("String", typeParameter.getExtendsType());
	}
	
	@Test
	public void testCanonicalName(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), typeParameter.getCanonicalName());
	}
	
	@Test
	public void testBaseTypeMissing(){
		try{
			JavaTypeParameter.builder()
					.build();
		}catch(IllegalArgumentException e){
			assertEquals("baseType can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testSetCanonicalName(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), typeParameter.getCanonicalName());
		typeParameter.setCanonicalName("com.tadukoo.String");
		assertEquals("com.tadukoo.String", typeParameter.getCanonicalName());
	}
}
