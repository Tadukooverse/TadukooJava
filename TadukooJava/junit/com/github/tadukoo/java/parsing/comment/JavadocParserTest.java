package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.tuple.Pair;
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

public class JavadocParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, Javadoc, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForJavadoc,
					JavadocParser::parseJavadoc
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, Javadoc, String>> parsingData = ListUtil.createList(
				// Simple Condensed
				Triple.of(
						"""
								/** */""",
						EditableJavadoc.builder()
								.condensed()
								.build(),
						"""
								/** */"""
				),
				// Not Condensed
				Triple.of(
						"""
								/**
								 */""",
						EditableJavadoc.builder()
								.build(),
						"""
								/**
								 */"""
				),
				// Condensed With Content
				Triple.of(
						"""
								/** some content */""",
						EditableJavadoc.builder()
								.condensed()
								.content("some content")
								.build(),
						"""
								/** some content */"""
				),
				// Not Condensed With Content
				Triple.of(
						"""
								/**
								 * some content
								 */""",
						EditableJavadoc.builder()
								.content("some content")
								.build(),
						"""
								/**
								 * some content
								 */"""
				),
				// Condensed With Author
				Triple.of(
						"""
								/** @author Logan Ferree (Tadukoo) */""",
						EditableJavadoc.builder()
								.condensed()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						"""
								/** @author Logan Ferree (Tadukoo) */"""
				),
				// Not Condensed With Author
				Triple.of(
						"""
								/**
								 * @author Logan Ferree (Tadukoo)
								 */""",
						EditableJavadoc.builder()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						"""
								/**
								 * @author Logan Ferree (Tadukoo)
								 */"""
				),
				// Condensed With Version
				Triple.of(
						"""
								/** @version Beta v.0.5 */""",
						EditableJavadoc.builder()
								.condensed()
								.version("Beta v.0.5")
								.build(),
						"""
								/** @version Beta v.0.5 */"""
				),
				// Not Condensed With Version
				Triple.of(
						"""
								/**
								 * @version Beta v.0.5
								 */""",
						EditableJavadoc.builder()
								.version("Beta v.0.5")
								.build(),
						"""
								/**
								 * @version Beta v.0.5
								 */"""
				),
				// Condensed With Since
				Triple.of(
						"""
								/** @since Alpha v.0.1 */""",
						EditableJavadoc.builder()
								.condensed()
								.since("Alpha v.0.1")
								.build(),
						"""
								/** @since Alpha v.0.1 */"""
				),
				// Not Condensed With Since
				Triple.of(
						"""
								/**
								 * @since Alpha v.0.1
								 */""",
						EditableJavadoc.builder()
								.since("Alpha v.0.1")
								.build(),
						"""
								/**
								 * @since Alpha v.0.1
								 */"""
				),
				// Condensed With Param
				Triple.of(
						"""
								/** @param type The type */""",
						EditableJavadoc.builder()
								.condensed()
								.param("type", "The type")
								.build(),
						"""
								/** @param type The type */"""
				),
				// Not Condensed With Param
				Triple.of(
						"""
								/**
								 * @param type The type
								 */""",
						EditableJavadoc.builder()
								.param("type", "The type")
								.build(),
						"""
								/**
								 * @param type The type
								 */"""
				),
				// Condensed With Return
				Triple.of(
						"""
								/** @return this, to continue building */""",
						EditableJavadoc.builder()
								.condensed()
								.returnVal("this, to continue building")
								.build(),
						"""
								/** @return this, to continue building */"""
				),
				// Not Condensed With Return
				Triple.of(
						"""
								/**
								 * @return this, to continue building
								 */""",
						EditableJavadoc.builder()
								.returnVal("this, to continue building")
								.build(),
						"""
								/**
								 * @return this, to continue building
								 */"""
				),
				// Condensed With Throws
				Triple.of(
						"""
								/** @throws Exception Because I can */""",
						EditableJavadoc.builder()
								.condensed()
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/** @throws Exception Because I can */"""
				),
				// Not Condensed With Throws
				Triple.of(
						"""
								/**
								 * @throws Exception Because I can
								 */""",
						EditableJavadoc.builder()
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/**
								 * @throws Exception Because I can
								 */"""
				),
				// Condensed With Everything
				Triple.of(
						"""
								/** some content
								 * @author Logan Ferree (Tadukoo)
								 * @version Beta v.0.5
								 * @since Alpha v.0.1
								 * @param type The type
								 * @param derp The int
								 * @return this, to continue building
								 * @throws Exception Because I can */""",
						EditableJavadoc.builder()
								.condensed()
								.content("some content")
								.author("Logan Ferree (Tadukoo)")
								.version("Beta v.0.5")
								.since("Alpha v.0.1")
								.param("type", "The type")
								.param("derp", "The int")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/** some content
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Beta v.0.5
								 * @since Alpha v.0.1
								 *\s
								 * @param type The type
								 * @param derp The int
								 * @return this, to continue building
								 * @throws Exception Because I can */"""
				),
				// Not Condensed With Everything
				Triple.of(
						"""
								/**
								 * some content
								 * @author Logan Ferree (Tadukoo)
								 * @version Beta v.0.5
								 * @since Alpha v.0.1
								 * @param type The type
								 * @param derp The int
								 * @return this, to continue building
								 * @throws Exception Because I can
								 */""",
						EditableJavadoc.builder()
								.content("some content")
								.author("Logan Ferree (Tadukoo)")
								.version("Beta v.0.5")
								.since("Alpha v.0.1")
								.param("type", "The type")
								.param("derp", "The int")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/**
								 * some content
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Beta v.0.5
								 * @since Alpha v.0.1
								 *\s
								 * @param type The type
								 * @param derp The int
								 * @return this, to continue building
								 * @throws Exception Because I can
								 */"""
				),
				// Unknown Annotation Condensed
				Triple.of(
						"""
								/** @garbage something */""",
						EditableJavadoc.builder()
								.condensed()
								.content("@garbage something")
								.build(),
						"""
								/** @garbage something */"""
				),
				// Unknown Annotation Not Condensed
				Triple.of(
						"""
								/**
								 * @garbage something
								 */""",
						EditableJavadoc.builder()
								.content("@garbage something")
								.build(),
						"""
								/**
								 * @garbage something
								 */"""
				),
				// Blank Content Line
				Triple.of(
						"""
								/**
								 * something
								 *\s
								 * something else
								 */""",
						// Note that we ignore the blank line - this is partly because Javadoc will ignore it anyway
						// (because of HTML rules), though we do not mimic Javadoc removing the newline between the 2 lines of content
						EditableJavadoc.builder()
								.content("something")
								.content("something else")
								.build(),
						"""
								/**
								 * something
								 * something else
								 */"""
				),
				// Start Token in Content
				Triple.of(
						"""
								/**
								 * /**
								 */""",
						EditableJavadoc.builder()
								.content("/**")
								.build(),
						"""
								/**
								 * /**
								 */"""
				),
				// Inherit Doc
				Triple.of(
						"""
								/** {@inheritDoc} */""",
						EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build(),
						"/** {@inheritDoc} */"
				),
				// Braced Content
				Triple.of(
						"""
								/** {content} */""",
						EditableJavadoc.builder()
								.condensed()
								.content("{content}")
								.build(),
						"/** {content} */"
				),
				// Content Right after Start Token
				Triple.of(
						"""
								/**some content */""",
						EditableJavadoc.builder()
								.condensed()
								.content("some content")
								.build(),
						"""
								/** some content */"""
				),
				// Annotation Right after Start Token
				Triple.of(
						"""
								/**@author Logan Ferree (Tadukoo) */""",
						EditableJavadoc.builder()
								.condensed()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						"""
								/** @author Logan Ferree (Tadukoo) */"""
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t     \t   \t
								\t    \t  \t
								\t   /**
								 * some content
								 */""",
						EditableJavadoc.builder()
								.content("some content")
								.build(),
						"""
								/**
								 * some content
								 */"""
				),
				// Trailing Whitespace
				Triple.of(
						"""
								/**
								 * some content
								 */   \t
								 \t     \t   \t
								\t    \t  \t
								\t   \s""",
						EditableJavadoc.builder()
								.content("some content")
								.build(),
						"""
								/**
								 * some content
								 */"""
				),
				/*
				 * Label Form Field Issue
				 * This exists because when running the parser code in @UltimatePower, the Javadoc was considered condensed.
				 * It has to do with Windows' /r/n usage for newlines, which the \r was treated as a character before the newline,
				 * meaning the Javadoc content started with it
				 */
				Triple.of(
						"""
								/**\r
								 * Represents a {@link FormField} for a Label
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.4
								 */
								""",
						EditableJavadoc.builder()
								.content("Represents a {@link FormField} for a Label")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.4")
								.build(),
						"""
								/**
								 * Represents a {@link FormField} for a Label
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.4
								 */"""
				)
		);
		
		return parsingData.stream()
				.flatMap(triple -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), triple.getLeft(), triple.getMiddle(),
								triple.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getParsingData")
	public void testParsing(
			ThrowingFunction<String, Javadoc, JavaParsingException> parseMethod, String textToParse,
			Javadoc expectedDoc, String expectedText) throws JavaParsingException{
		Javadoc doc = parseMethod.apply(textToParse);
		assertNotNull(doc);
		assertEquals(expectedDoc, doc);
		assertEquals(expectedText, doc.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// Double Authors
				Pair.of(
						"""
								/** @author Logan Ferree (Tadukoo)
								 * @author Shea */""",
						"Found multiple author strings"
				),
				// Triple Authors
				Pair.of(
						"""
								/** @author Logan Ferree (Tadukoo)
								 * @author Shea
								 * @author Zack */""",
						"Found multiple author strings"
				),
				// Double Versions
				Pair.of(
						"""
								/** @version Beta v.0.5
								 * @version Beta v.0.4 */""",
						"Found multiple version strings"
				),
				// Triple Versions
				Pair.of(
						"""
								/** @version Beta v.0.5
								 * @version Beta v.0.4
								 * @version Alpha v.0.1 */""",
						"Found multiple version strings"
				),
				// Double Since
				Pair.of(
						"""
								/** @since Beta v.0.5
								 * @since Beta v.0.4 */""",
						"Found multiple since strings"
				),
				// Triple Since
				Pair.of(
						"""
								/** @since Beta v.0.5
								 * @since Beta v.0.4
								 * @since Alpha v.0.1 */""",
						"Found multiple since strings"
				),
				// Double Returns
				Pair.of(
						"""
								/** @return this, to continue building
								 * @return The type */""",
						"Found multiple return strings"
				),
				// Triple Returns
				Pair.of(
						"""
								/** @return this, to continue building
								 * @return The type
								 * @return I don't even know */""",
						"Found multiple return strings"
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, Javadoc, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testNoJavadocStartToken(){
		try{
			JavadocParser.parseJavadoc("""
					* some content
					*/""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"First token of Javadoc must start with '" + JAVADOC_START_TOKEN + "'"),
					e.getMessage());
		}
	}
}
