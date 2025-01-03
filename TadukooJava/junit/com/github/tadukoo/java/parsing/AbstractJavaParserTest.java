package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AbstractJavaParserTest{
	
	private static Stream<Arguments> getTypeParameterData(){
		return Stream.of(
				// Empty
				Arguments.of(
						"",
						new ArrayList<>()
				),
				// Whitespace
				Arguments.of(
						"\t\n  \t",
						new ArrayList<>()
				),
				// Simple
				Arguments.of(
						"String",
						ListUtil.createList(
								JavaTypeParameter.builder()
										.baseType("String")
										.build()
						)
				),
				// With Extends
				Arguments.of(
						"? extends String",
						ListUtil.createList(
								JavaTypeParameter.builder()
										.baseType("?")
										.extendsType("String")
										.build()
						)
				),
				// With Multiple
				Arguments.of(
						"? extends String, ? extends Object",
						ListUtil.createList(
								JavaTypeParameter.builder()
										.baseType("?")
										.extendsType("String")
										.build(),
								JavaTypeParameter.builder()
										.baseType("?")
										.extendsType("Object")
										.build()
						)
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getTypeParameterData")
	public void testParseJavaTypeParameters(String text, List<JavaTypeParameter> expectedParameters){
		assertEquals(expectedParameters, AbstractJavaParser.parseJavaTypeParameters(text));
	}
	
	private static Stream<Arguments> getTypeData(){
		return Stream.of(
				// Basic
				Arguments.of(
						"String",
						JavaType.builder()
								.baseType("String")
								.build()
				),
				// With Type Parameters
				Arguments.of(
						"Map<String, Object>",
						JavaType.builder()
								.baseType("Map")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType("Object")
										.build())
								.build()
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getTypeData")
	public void testParseJavaType(String text, JavaType expectedType){
		assertEquals(expectedType, AbstractJavaParser.parseJavaType(text));
	}
	
	private static Stream<Arguments> getParameterData(){
		return Stream.of(
				// Basic
				Arguments.of(
						"String text",
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build()
				),
				// Vararg
				Arguments.of(
						"String ... text",
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.vararg()
								.name("text")
								.build()
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getParameterData")
	public void testParseJavaParameter(String text, JavaParameter expectedParameter){
		assertEquals(expectedParameter, AbstractJavaParser.parseJavaParameter(text));
	}
	
	private static Stream<Arguments> getErrorData(){
		return Stream.of(
				// Not a type parameter
				Arguments.of(
						"Something extends Something extends Yes",
						(ThrowingFunction<String, ?, NoException>)
								AbstractJavaParser::parseJavaTypeParameters,
						"'Something extends Something extends Yes' is not a valid type parameter"
				),
				// Not a type
				Arguments.of(
						"Something>Truly Garbage<",
						(ThrowingFunction<String, ?, NoException>)
								AbstractJavaParser::parseJavaType,
						"'Something>Truly Garbage<' is not a valid type"
				),
				// Not a parameter
				Arguments.of(
						"Something>Truly Garbage< text ...",
						(ThrowingFunction<String, ?, NoException>)
								AbstractJavaParser::parseJavaParameter,
						"'Something>Truly Garbage< text ...' is not a valid parameter"
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testErrors(String text, ThrowingFunction<String, ?, NoException> method, String error){
		try{
			method.apply(text);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(error, e.getMessage());
		}
	}
}
