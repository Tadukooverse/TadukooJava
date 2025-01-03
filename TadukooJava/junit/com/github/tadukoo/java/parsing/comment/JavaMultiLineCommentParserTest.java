package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
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

public class JavaMultiLineCommentParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaMultiLineComment, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForMultiLineComment,
					JavaMultiLineCommentParser::parseMultiLineComment
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaMultiLineComment, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"""
								/*
								 */""",
						EditableJavaMultiLineComment.builder()
								.build(),
						"""
								/*
								 */"""
				),
				// With Content
				Triple.of(
						"""
								/*
								 * something useful
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */"""
				),
				// With Multiple Lines of Content
				Triple.of(
						"""
								/*
								 * something useful
								 * something else
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.content("something else")
								.build(),
						"""
								/*
								 * something useful
								 * something else
								 */"""
				),
				// With Multiple Lines of Content and Blank Line
				Triple.of(
						"""
								/*
								 * something useful
								 *\s
								 * something else
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.content("")
								.content("something else")
								.build(),
						"""
								/*
								 * something useful
								 *\s
								 * something else
								 */"""
				),
				// With Asterisk in Comment
				Triple.of(
						"""
								/*
								 * something * useful
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something * useful")
								.build(),
						"""
								/*
								 * something * useful
								 */"""
				),
				// Content on First Line
				Triple.of(
						"""
								/* something useful
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */"""
				),
				// Content on First Line No Space
				Triple.of(
						"""
								/*something useful
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */"""
				),
				// Content on Last Line
				Triple.of(
						"""
								/*
								 * something useful */""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */"""
				),
				// Content on Last Line No Space
				Triple.of(
						"""
								/*
								 * something useful*/""",
						EditableJavaMultiLineComment.builder()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */"""
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t     \t  \t
								\t    \t
								\t     \t    /*
								 * something
								 */""",
						EditableJavaMultiLineComment.builder()
								.content("something")
								.build(),
						"""
								/*
								 * something
								 */"""
				),
				// Trailing Whitespace
				Triple.of(
						"""
								/*
								 * something
								 */    \t
								 \t     \t  \t
								\t    \t
								\t     \t    \t""",
						EditableJavaMultiLineComment.builder()
								.content("something")
								.build(),
						"""
								/*
								 * something
								 */"""
				),
				// Insane Whitespace
				Triple.of(
						"""
								\t     \t  \t
								\t    \t
								\t     \t    /*
								 * something
								 */    \t
								 \t     \t  \t
								\t    \t
								\t     \t    \t""",
						EditableJavaMultiLineComment.builder()
								.content("something")
								.build(),
						"""
								/*
								 * something
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
			ThrowingFunction<String, JavaMultiLineComment, JavaParsingException> parseMethod, String textToParse,
			JavaMultiLineComment expectedMultiLineComment, String expectedText) throws JavaParsingException{
		JavaMultiLineComment multiLineComment = parseMethod.apply(textToParse);
		assertNotNull(multiLineComment);
		assertEquals(expectedMultiLineComment, multiLineComment);
		assertEquals(expectedText, multiLineComment.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// Missing End Token
				Pair.of(
						"/*",
						"Failed to find closing multi-line comment token!"
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaMultiLineComment, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.MULTI_LINE_COMMENT,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testMissingStartToken(){
		try{
			JavaMultiLineCommentParser.parseMultiLineComment("""
					*/""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.MULTI_LINE_COMMENT,
							"First token of multi-line comment must start with '" +
									MULTI_LINE_COMMENT_START_TOKEN + "'"),
					e.getMessage());
		}
	}
}
