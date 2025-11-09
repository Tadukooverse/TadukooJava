package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationParserTest extends BaseJavaParserTest{
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaAnnotation, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"@Test",
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						"@Test"
				),
				// With Value Parameter
				Triple.of(
						"@Test(true)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("value", "true")
								.build(),
						"@Test(true)"
				),
				// With Parameter
				Triple.of(
						"@Test(type = String.class)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// With Multiple Parameters
				Triple.of(
						"@Test(type = String.class, defaultValue = \"\")",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build(),
						"@Test(type = String.class, defaultValue = \"\")"
				),
				// Whitespace Before
				Triple.of(
						" \t@Test",
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						"@Test"
				),
				// Whitespace Before Name
				Triple.of(
						"@\t Test",
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						"@Test"
				),
				// Whitespace After Name
				Triple.of(
						"@Test \t",
						EditableJavaAnnotation.builder()
								.name("Test")
								.build(),
						"@Test"
				),
				// Whitespace Before Params
				Triple.of(
						"@Test \t(type = String.class)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace Before Param Name
				Triple.of(
						"@Test( \ttype = String.class)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace After Param Name
				Triple.of(
						"@Test(type \t = String.class)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace Before Param Value
				Triple.of(
						"@Test(type =  \tString.class)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace After Param Value
				Triple.of(
						"@Test(type = String.class \t)",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace After Params
				Triple.of(
						"@Test(type = String.class) \t",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.build(),
						"@Test(type = String.class)"
				),
				// Whitespace Before Comma
				Triple.of(
						"@Test(type = String.class  \t, defaultValue = \"\")",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build(),
						"@Test(type = String.class, defaultValue = \"\")"
				),
				// Whitespace After Comma
				Triple.of(
						"@Test(type = String.class, \t  \t defaultValue = \"\")",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build(),
						"@Test(type = String.class, defaultValue = \"\")"
				),
				// Whitespace Insane
				Triple.of(
						"  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
								"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t",
						EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build(),
						"@Test(type = String.class, defaultValue = \"\")"
				)
		);
		
		List<ThrowingFunction<String, JavaAnnotation, JavaParsingException>> parseMethods = ListUtil.createList(
				BaseJavaParserTest::runFullParserForAnnotation,
				JavaAnnotationParser::parseAnnotation
		);
		
		return parsingData.stream()
				.flatMap(triple -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), triple.getLeft(), triple.getMiddle(),
								triple.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getParsingData")
	public void testParsing(
			ThrowingFunction<String, JavaAnnotation, JavaParsingException> parseMethod, String textToParse,
			JavaAnnotation expectedAnnotation, String expectedText) throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply(textToParse);
		assertNotNull(annotation);
		assertEquals(expectedAnnotation, annotation);
		assertEquals(expectedText, annotation.toString());
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testMissingStartToken(){
		try{
			JavaAnnotationParser.parseAnnotation(
					ListUtil.createList("Test", "(", "type", "=", "String.class", ")"), 0);
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"First token of annotation must start with '" + ANNOTATION_START_TOKEN + "'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationDoesNotMatchRegex(){
		try{
			JavaAnnotationParser.parseAnnotation("""
					Test(type = String.class)""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"Failed to parse annotation"),
					e.getMessage());
		}
	}
	
	@Test
	public void failedToEndParameters(){
		try{
			runFullParserForAnnotation("""
					@Test(""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.ANNOTATION,
							"Didn't find end of parameters"),
					e.getMessage());
		}
	}
}
