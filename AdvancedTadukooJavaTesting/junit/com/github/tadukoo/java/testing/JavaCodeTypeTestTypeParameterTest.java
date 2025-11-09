package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertTypeParameterEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findTypeParameterDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestTypeParameterTest{
	
	@ParameterizedTest
	@MethodSource("getTypeParameterDifferences")
	public void testFindTypeParameterDifferences(
			JavaTypeParameter expectedTypeParameter, JavaTypeParameter actualTypeParameter, List<String> differences){
		assertEquals(differences, findTypeParameterDifferences(expectedTypeParameter, actualTypeParameter));
	}
	
	@ParameterizedTest
	@MethodSource("getTypeParameterDifferences")
	public void testAssertTypeParameterEquals(
			JavaTypeParameter expectedTypeParameter, JavaTypeParameter actualTypeParameter, List<String> differences){
		try{
			assertTypeParameterEquals(expectedTypeParameter, actualTypeParameter);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedTypeParameter, actualTypeParameter)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getTypeParameterDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						new ArrayList<>()
				),
				// Both null
				Arguments.of(
						null,
						null,
						new ArrayList<>()
				),
				// First null
				Arguments.of(
						null,
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						ListUtil.createList("One of the type parameters is null, and the other isn't!")
				),
				// Second null
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						null,
						ListUtil.createList("One of the type parameters is null, and the other isn't!")
				),
				// Base Type Different
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("String")
										.build())
								.build(),
						JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("int")
										.build())
								.build(),
						ListUtil.createList("""
												Base Type differs:
													Base Type is different!""")
				),
				// Extends Type Different
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
										.baseType("Object")
										.build())
								.build(),
						ListUtil.createList("""
												Extends Type differs:
													Base Type is different!""")
				),
				// All
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
										.baseType("String")
										.build())
								.build(),
						ListUtil.createList("""
									Base Type differs:
										Base Type is different!""",
								"""
									Extends Type differs:
										One of the types is null, and the other isn't!""")
				)
		);
	}
}
