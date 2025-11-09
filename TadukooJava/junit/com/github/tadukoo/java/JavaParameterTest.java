package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaParameterTest{
	
	@Test
	public void testGetCodeType(){
		assertEquals(JavaCodeTypes.PARAMETER, JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("text")
				.build()
				.getJavaCodeType());
	}
	
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
	public void testVarargSetFalse(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		JavaParameter parameter = JavaParameter.builder()
				.type(type)
				.name("text")
				.vararg(false)
				.build();
		assertFalse(parameter.isVararg());
	}
	
	@Test
	public void testTypeEmpty(){
		try{
			JavaParameter.builder()
					.name("text")
					.build();
			fail();
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
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("name can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testAllErrors(){
		try{
			JavaParameter.builder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					type can't be null!
					name can't be empty!""", e.getMessage());
		}
	}
	
	@ParameterizedTest
	@MethodSource("getNotEqualsData")
	public void testNotEquals(JavaParameter parameter, JavaParameter otherParameter){
		assertNotEquals(parameter, otherParameter);
	}
	
	public static Stream<Arguments> getNotEqualsData(){
		return Stream.of(
				// Different type
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("Integer")
										.build())
								.name("text")
								.build()
				),
				// Different varargs
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.vararg()
								.name("text")
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build()
				),
				// Different name
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("something")
								.build()
				)
		);
	}
	
	@Test
	public void testEqualsDifferentClassType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("text")
				.build(), "testing");
	}
	
	@Test
	public void testEqualsSame(){
		assertEquals(
				JavaParameter.builder()
						.type(JavaType.builder()
								.baseType("String")
								.build())
						.name("text")
						.build(),
				JavaParameter.builder()
						.type(JavaType.builder()
								.baseType("String")
								.build())
						.name("text")
						.build());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToString(JavaParameter parameter, String text, String ignored){
		assertEquals(text, parameter.toString());
	}
	
	@ParameterizedTest
	@MethodSource("getStringData")
	public void testToBuilderCode(JavaParameter parameter, String ignored, String builderCode){
		assertEquals(builderCode, parameter.toBuilderCode());
	}
	
	public static Stream<Arguments> getStringData(){
		return Stream.of(
				// No vararg
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						"String text",
						"""
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build()"""
				),
				// Vararg
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.vararg()
								.name("text")
								.build(),
						"String ... text",
						"""
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.vararg()
								.name("text")
								.build()"""
				)
		);
	}
}
