package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertParameterEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findParameterDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestParameterTest{
	
	@ParameterizedTest
	@MethodSource("getParameterDifferences")
	public void testFindParameterDifferences(
			JavaParameter expectedParameter, JavaParameter actualParameter, List<String> differences){
		assertEquals(differences, findParameterDifferences(expectedParameter, actualParameter));
	}
	
	@ParameterizedTest
	@MethodSource("getParameterDifferences")
	public void testAssertParameterEquals(
			JavaParameter expectedParameter, JavaParameter actualParameter, List<String> differences){
		try{
			assertParameterEquals(expectedParameter, actualParameter);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedParameter, actualParameter)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getParameterDifferences(){
		return Stream.of(
				// None
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
								.name("text")
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
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						ListUtil.createList("One of the parameters is null, and the other isn't!")
				),
				// Second null
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						null,
						ListUtil.createList("One of the parameters is null, and the other isn't!")
				),
				// Type Different
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("int")
										.build())
								.name("text")
								.build(),
						ListUtil.createList("""
								Type differs:
									Base Type is different!""")
				),
				// Vararg Different
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.vararg()
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.build(),
						ListUtil.createList("Vararg is different!")
				),
				// Name Different
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
								.name("text1")
								.build(),
						ListUtil.createList("Name is different!")
				),
				// All
				Arguments.of(
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("String")
										.build())
								.name("text")
								.vararg()
								.build(),
						JavaParameter.builder()
								.type(JavaType.builder()
										.baseType("int")
										.build())
								.name("text1")
								.build(),
						ListUtil.createList("""
								Type differs:
									Base Type is different!""",
								"Vararg is different!",
								"Name is different!")
				)
		);
	}
}
