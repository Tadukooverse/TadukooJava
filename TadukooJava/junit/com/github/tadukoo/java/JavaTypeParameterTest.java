package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTypeParameterTest{
	
	@Test
	public void testGetJavaCodeType(){
		assertEquals(JavaCodeTypes.TYPE_PARAMETER, JavaTypeParameter.builder()
				.baseType("String")
				.build().getJavaCodeType());
	}
	
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
			fail();
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
	
	@ParameterizedTest
	@MethodSource("notEqualsData")
	public void testNotEquals(JavaTypeParameter typeParam, JavaTypeParameter otherParam){
		assertNotEquals(typeParam, otherParam);
	}
	
	public static Stream<Arguments> notEqualsData(){
		return Stream.of(
				// different base type
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						JavaTypeParameter.builder()
								.baseType("Integer")
								.build()),
				// different extends type
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.build(),
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("Integer")
								.build()),
				// different canonical name
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.canonicalName("com.tadukoo.String")
								.build())
		);
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
	public void testEqualsSame(){
		assertEquals(
				JavaTypeParameter.builder()
						.baseType("?")
						.extendsType("String")
						.canonicalName("com.tadukoo.String")
						.build(),
				JavaTypeParameter.builder()
						.baseType("?")
						.extendsType("String")
						.canonicalName("com.tadukoo.String")
						.build());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToString(JavaTypeParameter typeParam, String text, String ignored){
		assertEquals(text, typeParam.toString());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToBuilderCode(JavaTypeParameter typeParam, String ignored, String builderCode){
		assertEquals(builderCode, typeParam.toBuilderCode());
	}
	
	public static Stream<Arguments> getStringData(){
		return Stream.of(
				// Simple
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						"String",
						"""
						JavaTypeParameter.builder()
								.baseType("String")
								.build()"""
				),
				// With extends type
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.build(),
						"? extends String",
						"""
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.build()"""
				),
				// With canonical name
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build(),
						"String",
						"""
						JavaTypeParameter.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build()"""
				),
				// With everything
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("T")
								.extendsType("String")
								.canonicalName("com.tadukoo.String")
								.build(),
						"T extends String",
						"""
						JavaTypeParameter.builder()
								.baseType("T")
								.extendsType("String")
								.canonicalName("com.tadukoo.String")
								.build()"""
				)
		);
	}
}
