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
				.baseType(JavaType.builder()
						.baseType("String")
						.build())
				.build().getJavaCodeType());
	}
	
	@Test
	public void testBaseType(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType(JavaType.builder()
						.baseType("String")
						.build())
				.build();
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), typeParameter.getBaseType());
	}
	
	@Test
	public void testExtendsType(){
		JavaTypeParameter typeParameter = JavaTypeParameter.builder()
				.baseType(JavaType.builder()
						.baseType("?")
						.build())
				.extendsType(JavaType.builder()
						.baseType("String")
						.build())
				.build();
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), typeParameter.getExtendsType());
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
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("Integer")
										.build())
								.build()),
				// different extends type
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("?")
										.build())
								.extendsType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("?")
										.build())
								.extendsType(JavaType.builder()
										.baseType("Integer")
										.build())
								.build())
		);
	}
	
	@Test
	public void testEqualsNotTypeParam(){
		JavaTypeParameter typeParam = JavaTypeParameter.builder()
				.baseType(JavaType.builder()
						.baseType("String")
						.build())
				.build();
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(typeParam, "testing");
	}
	
	@Test
	public void testEqualsSame(){
		assertEquals(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("?")
								.build())
						.extendsType(JavaType.builder()
								.baseType("String")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("?")
								.build())
						.extendsType(JavaType.builder()
								.baseType("String")
								.build())
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
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						"String",
						"""
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build()"""
				),
				// With extends type
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("?")
										.build())
								.extendsType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						"? extends String",
						"""
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("?")
										.build())
								.extendsType(JavaType.builder()
										.baseType("String")
										.build())
								.build()"""
				),
				// With everything
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("T")
										.build())
								.extendsType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						"T extends String",
						"""
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("T")
										.build())
								.extendsType(JavaType.builder()
										.baseType("String")
										.build())
								.build()"""
				)
		);
	}
}
