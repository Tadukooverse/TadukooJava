package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
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

public class JavaSingleLineCommentParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaSingleLineComment, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForSingleLineComment,
					JavaSingleLineCommentParser::parseSingleLineComment
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaSingleLineComment, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"//",
						EditableJavaSingleLineComment.builder()
								.build(),
						"// "
				),
				// With Content
				Triple.of(
						"// something useful here",
						EditableJavaSingleLineComment.builder()
								.content("something useful here")
								.build(),
						"// something useful here"
				),
				// Simple
				Triple.of(
						"//something useful here",
						EditableJavaSingleLineComment.builder()
								.content("something useful here")
								.build(),
						"// something useful here"
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t     \t  \t
								\t    // something useful""",
						EditableJavaSingleLineComment.builder()
								.content("something useful")
								.build(),
						"// something useful"
				),
				// Trailing Whitespace
				Triple.of(
						"""
								// something useful \t    \t \t
								\t     \t""",
						EditableJavaSingleLineComment.builder()
								.content("something useful")
								.build(),
						"// something useful"
				),
				// More Trailing Whitespace
				Triple.of(
						"""
								// something useful \t      \t \t
								\t     \t
								\t  \t
								\t  \t""",
						EditableJavaSingleLineComment.builder()
								.content("something useful")
								.build(),
						"// something useful"
				),
				// Inside Whitespace
				Triple.of(
						"""
								// something \t     \t   useful""",
						EditableJavaSingleLineComment.builder()
								.content("something \t     \t   useful")
								.build(),
						"// something \t     \t   useful"
				),
				// Whitespace after Start Token
				Triple.of(
						"""
								// \t     \t   something useful""",
						EditableJavaSingleLineComment.builder()
								.content("something useful")
								.build(),
						"// something useful"
				),
				// Insane Whitespace
				Triple.of(
						"""
								\t     \t  \t
								\t    // \t     \t   something \t     \t   useful \t     \t  \t
								\t     \t""",
						EditableJavaSingleLineComment.builder()
								.content("something \t     \t   useful")
								.build(),
						"// something \t     \t   useful"
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
			ThrowingFunction<String, JavaSingleLineComment, JavaParsingException> parseMethod, String textToParse,
			JavaSingleLineComment expectedSingleLineComment, String expectedText) throws JavaParsingException{
		JavaSingleLineComment SingleLineComment = parseMethod.apply(textToParse);
		assertNotNull(SingleLineComment);
		assertEquals(expectedSingleLineComment, SingleLineComment);
		assertEquals(expectedText, SingleLineComment.toString());
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testExtraContentFoundError(){
		try{
			JavaSingleLineCommentParser.parseSingleLineComment("""
					// some comment
					// some other comment""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.SINGLE_LINE_COMMENT,
							"Found extra content after the single-line comment!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testCommentDoesNotStartWithTokenError(){
		try{
			JavaSingleLineCommentParser.parseSingleLineComment("""
					/* some comment */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.SINGLE_LINE_COMMENT,
							"First token of single-line comment must start with '" +
									SINGLE_LINE_COMMENT_TOKEN + "'"),
					e.getMessage());
		}
	}
}
