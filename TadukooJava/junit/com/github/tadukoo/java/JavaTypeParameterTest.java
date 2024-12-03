package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
	
	@Test
	public void testEqualsNotTypeParam(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("String")
				.build();
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(typeParam, "testing");
	}
	
	@Test
	public void testEqualsDifferentBaseType(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("String")
				.build();
		JavaTypeParameter otherParam = JavaTypeParameter.builder()
				.baseType("Integer")
				.build();
		assertNotEquals(typeParam, otherParam);
	}
	
	@Test
	public void testEqualsDifferentExtendsType(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("String")
				.build();
		JavaTypeParameter otherParam = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("Integer")
				.build();
		assertNotEquals(typeParam, otherParam);
	}
	
	@Test
	public void testEqualsDifferentCanonicalName(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		JavaTypeParameter otherParam = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("String")
				.canonicalName("com.tadukoo.String")
				.build();
		assertNotEquals(typeParam, otherParam);
	}
	
	@Test
	public void testToString(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("String")
				.build();
		assertEquals("String", typeParam.toString());
	}
	
	@Test
	public void testToStringWithExtendsType(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType("?")
				.extendsType("String")
				.build();
		assertEquals("? extends String", typeParam.toString());
	}
}
