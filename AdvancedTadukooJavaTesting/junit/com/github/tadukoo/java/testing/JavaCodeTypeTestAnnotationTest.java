package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertAnnotationEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findAnnotationDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestAnnotationTest{
	
	@ParameterizedTest
	@MethodSource("getAnnotationDifferences")
	public void testFindAnnotationDifferences(
			JavaAnnotation expectedAnnotation, JavaAnnotation actualAnnotation,
			List<String> differences){
		assertEquals(differences, findAnnotationDifferences(expectedAnnotation, actualAnnotation));
	}
	
	@ParameterizedTest
	@MethodSource("getAnnotationDifferences")
	public void testAssertAnnotationEquals(
			JavaAnnotation expectedAnnotation, JavaAnnotation actualAnnotation,
			List<String> differences){
		try{
			assertAnnotationEquals(expectedAnnotation, actualAnnotation);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedAnnotation, actualAnnotation)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getAnnotationDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						new ArrayList<>()
				),
				// Both Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null, 2 Not
				Arguments.of(
						null,
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						ListUtil.createList("One of the annotations is null, and the other isn't!")
				),
				// 2 Null, 1 Not
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						null,
						ListUtil.createList("One of the annotations is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Name
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Derp")
								.build(),
						ListUtil.createList("Name is different!")
				),
				// Canonical Name
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.canonicalName("com.test.Test")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Test")
								.canonicalName("com.derp.Test")
								.build(),
						ListUtil.createList("Canonical Name is different!")
				),
				// Params Length
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("something", "We doing something with it")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("something", "We doing something with it")
								.parameter("version", "It a version")
								.build(),
						ListUtil.createList("Parameters length is different!")
				),
				// Params Length 1 Longer
				Arguments.of(
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("something", "We doing something with it")
								.parameter("version", "It a version")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("something", "We doing something with it")
								.build(),
						ListUtil.createList("Parameters length is different!",
								"Parameters differs on #2!")
				),
				// All
				Arguments.of(
						UneditableJavaAnnotation.builder()
								.name("Test")
								.canonicalName("com.test.Test")
								.parameter("something", "We doing something with it")
								.parameter("version", "It a version")
								.build(),
						EditableJavaAnnotation.builder()
								.name("Derp")
								.canonicalName("com.derp.Test")
								.parameter("something", "We doing something with it")
								.build(),
						ListUtil.createList("Editable is different!",
								"Name is different!",
								"Canonical Name is different!",
								"Parameters length is different!",
								"Parameters differs on #2!")
				)
		);
	}
}
