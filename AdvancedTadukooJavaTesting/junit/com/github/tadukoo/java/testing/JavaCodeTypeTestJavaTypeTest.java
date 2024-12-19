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

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertTypeEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findTypeDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestJavaTypeTest{
	
	@ParameterizedTest
	@MethodSource("getTypeDifferences")
	public void testFindTypeDifferences(JavaType expectedType, JavaType actualType, List<String> differences){
		assertEquals(differences, findTypeDifferences(expectedType, actualType));
	}
	
	@ParameterizedTest
	@MethodSource("getTypeDifferences")
	public void testAssertTypeEquals(JavaType expectedType, JavaType actualType, List<String> differences){
		try{
			assertTypeEquals(expectedType, actualType);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedType, actualType)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getTypeDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.build(),
						JavaType.builder()
								.baseType("String")
								.build(),
						new ArrayList<>()
				),
				// Base Type Different
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.build(),
						JavaType.builder()
								.baseType("int")
								.build(),
						ListUtil.createList("Base Type is different!")
				),
				// Type Parameter Different
				Arguments.of(
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.build(),
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("int")
										.build())
								.build(),
						ListUtil.createList("""
								Type Parameters differs on #1:
									Base Type is different!""")
				),
				// Type Parameters Different Length
				Arguments.of(
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.build(),
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType("int")
										.build())
								.build(),
						ListUtil.createList("Type Parameters length is different!")
				),
				// Canonical Name Different
				Arguments.of(
						JavaType.builder()
								.baseType("String")
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaType.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build(),
						ListUtil.createList("Canonical Name is different!")
				),
				// All
				Arguments.of(
						JavaType.builder()
								.baseType("List")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaType.builder()
								.baseType("String")
								.typeParameter(JavaTypeParameter.builder()
										.baseType("int")
										.build())
								.typeParameter(JavaTypeParameter.builder()
										.baseType("String")
										.build())
								.build(),
						ListUtil.createList("Base Type is different!",
								"Type Parameters length is different!",
								"""
								Type Parameters differs on #1:
									Base Type is different!""",
								"Canonical Name is different!")
				)
		);
	}
}
