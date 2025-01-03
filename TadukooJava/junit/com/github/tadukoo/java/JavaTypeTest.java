package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTypeTest{
	
	@Test
	public void testGetJavaCodeType(){
		assertEquals(JavaCodeTypes.TYPE, JavaType.builder()
				.baseType("String")
				.build().getJavaCodeType());
	}
	
	@Test
	public void testBaseType(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		assertEquals("String", type.getBaseType());
	}
	
	@Test
	public void testTypeParameters(){
		List<JavaTypeParameter> typeParams = ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("String")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("Object")
								.build())
						.build());
		JavaType type = JavaType.builder()
				.baseType("Map")
				.typeParameters(typeParams)
				.build();
		assertEquals(typeParams, type.getTypeParameters());
	}
	
	@Test
	public void testTypeParameter(){
		JavaTypeParameter stringTypeParam = JavaTypeParameter.builder()
				.baseType(JavaType.builder()
						.baseType("String")
						.build())
				.build();
		JavaTypeParameter objectTypeParam = JavaTypeParameter.builder()
				.baseType(JavaType.builder()
						.baseType("Object")
						.build())
				.build();
		JavaType type = JavaType.builder()
				.baseType("Map")
				.typeParameter(stringTypeParam)
				.typeParameter(objectTypeParam)
				.build();
		assertEquals(ListUtil.createList(stringTypeParam, objectTypeParam), type.getTypeParameters());
	}
	
	@Test
	public void testCanonicalName(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), type.getCanonicalName());
	}
	
	@Test
	public void testBaseTypeMissing(){
		try{
			JavaType.builder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("baseType can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testSetCanonicalName(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), type.getCanonicalName());
		type.setCanonicalName("com.tadukoo.String");
		assertEquals("com.tadukoo.String", type.getCanonicalName());
	}
	
	@ParameterizedTest
	@MethodSource("getEqualsData")
	public void testNotEquals(JavaType type, JavaType otherType){
		assertNotEquals(type, otherType);
	}
	
	public static Stream<Arguments> getEqualsData(){
		return Stream.of(
				// Different base type
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.build(),
						JavaType.builder()
								.baseType("Integer")
								.build()
				),
				// Different type parameters
				Arguments.of(
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.build(),
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("Integer")
												.build())
										.build())
								.build()
				),
				// Different canonical name
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaType.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build()
				)
		);
	}
	
	@Test
	public void testEqualsDifferentThing(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(JavaType.builder()
				.baseType("String")
				.build(), "testing");
	}
	
	@Test
	public void testEqualsSame(){
		assertEquals(
				JavaType.builder()
						.baseType("List")
						.typeParameter(JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build())
						.canonicalName(String.class.getCanonicalName())
						.build(),
				JavaType.builder()
						.baseType("List")
						.typeParameter(JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build())
						.canonicalName(String.class.getCanonicalName())
						.build());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToString(JavaType type, String text, String ignored){
		assertEquals(text, type.toString());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToBuilderCode(JavaType type, String ignored, String builderText){
		assertEquals(builderText, type.toBuilderCode());
	}
	
	public static Stream<Arguments> getStringData(){
		return Stream.of(
				// Simple
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.build(),
						"String",
						"""
						JavaType.builder()
								.baseType("String")
								.build()"""
				),
				// Single type parameter
				Arguments.of(
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.build(),
						"List<String>",
						"""
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.build()"""
				),
				// Multiple type parameters
				Arguments.of(
						JavaType.builder()
								.baseType("Map")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("Object")
												.build())
										.build())
								.build(),
						"Map<String, Object>",
						"""
						JavaType.builder()
								.baseType("Map")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("Object")
												.build())
										.build())
								.build()"""
				),
				// Canonical name
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build(),
						"String",
						"""
						JavaType.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build()"""
				),
				// Everything
				Arguments.of(
						JavaType.builder()
								.baseType("Map")
								.canonicalName("com.tadukoo.Map")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("Object")
												.build())
										.build())
								.build(),
						"Map<String, Object>",
						"""
						JavaType.builder()
								.baseType("Map")
								.canonicalName("com.tadukoo.Map")
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("String")
												.build())
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("Object")
												.build())
										.build())
								.build()"""
				)
		);
	}
}
