package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertSingleLineCommentEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findSingleLineCommentDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestSingleLineCommentTest{
	
	@ParameterizedTest
	@MethodSource("getCommentDifferences")
	public void testFindSingleLineCommentDifferences(
			JavaSingleLineComment expectedComment, JavaSingleLineComment actualComment,
			List<String> differences){
		assertEquals(differences, findSingleLineCommentDifferences(expectedComment, actualComment));
	}
	
	@ParameterizedTest
	@MethodSource("getCommentDifferences")
	public void testAssertSingleLineCommentEquals(
			JavaSingleLineComment expectedComment, JavaSingleLineComment actualComment,
			List<String> differences){
		try{
			assertSingleLineCommentEquals(expectedComment, actualComment);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedComment, actualComment)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getCommentDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						new ArrayList<>()
				),
				// Both Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null 2 Not
				Arguments.of(
						null,
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						ListUtil.createList("One of the single-line comments is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						null,
						ListUtil.createList("One of the single-line comments is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Content
				Arguments.of(
						EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaSingleLineComment.builder()
								.content("Something else useful")
								.build(),
						ListUtil.createList("Content is different!")
				),
				// All
				Arguments.of(
						UneditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaSingleLineComment.builder()
								.content("Something else useful")
								.build(),
						ListUtil.createList("Editable is different!",
								"Content is different!")
				)
		);
	}
}
