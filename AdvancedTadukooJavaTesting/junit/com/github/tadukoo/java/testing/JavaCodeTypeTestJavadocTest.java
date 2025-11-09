package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertJavadocEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findJavadocDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestJavadocTest{
	
	@ParameterizedTest
	@MethodSource("getJavadocDifferences")
	public void testFindJavadocDifferences(
			Javadoc expectedJavadoc, Javadoc actualJavadoc,
			List<String> differences){
		assertEquals(differences, findJavadocDifferences(expectedJavadoc, actualJavadoc));
	}
	
	@ParameterizedTest
	@MethodSource("getJavadocDifferences")
	public void testAssertJavadocEquals(
			Javadoc expectedJavadoc, Javadoc actualJavadoc,
			List<String> differences){
		try{
			assertJavadocEquals(expectedJavadoc, actualJavadoc);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedJavadoc, actualJavadoc)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getJavadocDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavadoc.builder()
								.build(),
						EditableJavadoc.builder()
								.build(),
						new ArrayList<>()
				),
				// Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null 2 Not
				Arguments.of(
						null,
						EditableJavadoc.builder()
								.build(),
						ListUtil.createList("One of the Javadocs is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavadoc.builder()
								.build(),
						null,
						ListUtil.createList("One of the Javadocs is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavadoc.builder()
								.build(),
						EditableJavadoc.builder()
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Condensed
				Arguments.of(
						EditableJavadoc.builder()
								.condensed()
								.build(),
						EditableJavadoc.builder()
								.condensed(false)
								.build(),
						ListUtil.createList("Condensed is different!")
				),
				// Content
				Arguments.of(
						EditableJavadoc.builder()
								.content("something")
								.build(),
						EditableJavadoc.builder()
								.content("yep")
								.build(),
						ListUtil.createList("Content differs on #1!")
				),
				// Content Length
				Arguments.of(
						EditableJavadoc.builder()
								.content("something")
								.build(),
						EditableJavadoc.builder()
								.content("something")
								.content("yep")
								.build(),
						ListUtil.createList("Content length is different!")
				),
				// Content Length 1 Longer
				Arguments.of(
						EditableJavadoc.builder()
								.content("something")
								.content("yep")
								.build(),
						EditableJavadoc.builder()
								.content("something")
								.build(),
						ListUtil.createList("Content length is different!",
								"Content differs on #2!")
				),
				// Author
				Arguments.of(
						EditableJavadoc.builder()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						EditableJavadoc.builder()
								.author("Logan Ferree")
								.build(),
						ListUtil.createList("Author is different!")
				),
				// Version
				Arguments.of(
						EditableJavadoc.builder()
								.version("Alpha v.0.1")
								.build(),
						EditableJavadoc.builder()
								.version("Pre-Alpha")
								.build(),
						ListUtil.createList("Version is different!")
				),
				// Since
				Arguments.of(
						EditableJavadoc.builder()
								.since("Pre-Alpha")
								.build(),
						EditableJavadoc.builder()
								.since("Alpha v.0.1")
								.build(),
						ListUtil.createList("Since is different!")
				),
				// Params Length
				Arguments.of(
						EditableJavadoc.builder()
								.param("String", "something")
								.build(),
						EditableJavadoc.builder()
								.param("String", "something")
								.param("int", "version")
								.build(),
						ListUtil.createList("Params length is different!")
				),
				// Params Length 1 Longer
				Arguments.of(
						EditableJavadoc.builder()
								.param("String", "something")
								.param("int", "version")
								.build(),
						EditableJavadoc.builder()
								.param("String", "something")
								.build(),
						ListUtil.createList("Params length is different!",
								"Params differs on #2!")
				),
				// Return Val
				Arguments.of(
						EditableJavadoc.builder()
								.returnVal("null")
								.build(),
						EditableJavadoc.builder()
								.returnVal("true if true")
								.build(),
						ListUtil.createList("Return is different!")
				),
				// Throws Length
				Arguments.of(
						EditableJavadoc.builder()
								.throwsInfo("IllegalStateException", "something")
								.build(),
						EditableJavadoc.builder()
								.throwsInfo("IllegalStateException", "something")
								.throwsInfo("IllegalArgumentException", "why not?")
								.build(),
						ListUtil.createList("Throws length is different!")
				),
				// Throws Length 1 Longer
				Arguments.of(
						EditableJavadoc.builder()
								.throwsInfo("IllegalStateException", "something")
								.throwsInfo("IllegalArgumentException", "why not?")
								.build(),
						EditableJavadoc.builder()
								.throwsInfo("IllegalStateException", "something")
								.build(),
						ListUtil.createList("Throws length is different!",
								"Throws differs on #2!")
				),
				// All
				Arguments.of(
						UneditableJavadoc.builder()
								.condensed()
								.content("something")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Pre-Alpha")
								.param("String", "somethingElse")
								.returnVal("null")
								.throwsInfo("IllegalStateException", "something")
								.build(),
						EditableJavadoc.builder()
								.condensed(false)
								.content("yep")
								.content("nope")
								.author("Logan Ferree")
								.version("Pre-Alpha")
								.since("Alpha v.0.1")
								.param("String", "something")
								.param("int", "version")
								.returnVal("true if true")
								.throwsInfo("IllegalStateException", "somethingElse")
								.throwsInfo("IllegalArgumentException", "why not?")
								.build(),
						ListUtil.createList(
								"Editable is different!",
								"Condensed is different!",
								"Content length is different!",
								"Content differs on #1!",
								"Author is different!",
								"Version is different!",
								"Since is different!",
								"Params length is different!",
								"Params differs on #1!",
								"Return is different!",
								"Throws length is different!",
								"Throws differs on #1!")
				)
		);
	}
}
