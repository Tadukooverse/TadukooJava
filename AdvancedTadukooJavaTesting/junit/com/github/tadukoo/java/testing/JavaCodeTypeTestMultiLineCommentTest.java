package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertMultiLineCommentEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findMultiLineCommentDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestMultiLineCommentTest{
	
	@ParameterizedTest
	@MethodSource("getCommentDifferences")
	public void testFindMultiLineCommentDifferences(
			JavaMultiLineComment expectedComment, JavaMultiLineComment actualComment,
			List<String> differences){
		assertEquals(differences, findMultiLineCommentDifferences(expectedComment, actualComment));
	}
	
	@ParameterizedTest
	@MethodSource("getCommentDifferences")
	public void testAssertMultiLineCommentEquals(
			JavaMultiLineComment expectedComment, JavaMultiLineComment actualComment,
			List<String> differences){
		try{
			assertMultiLineCommentEquals(expectedComment, actualComment);
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
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
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
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						ListUtil.createList("One of the multi-line comments is null, and the other isn't!")
				),
				// 2 Null, 1 Not
				Arguments.of(
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						null,
						ListUtil.createList("One of the multi-line comments is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Content
				Arguments.of(
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaMultiLineComment.builder()
								.content("Something else useful")
								.build(),
						ListUtil.createList("Content differs on #1!")
				),
				// Content Length
				Arguments.of(
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.content("Another line")
								.build(),
						EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						ListUtil.createList("Content length is different!",
								"Content differs on #2!")
				),
				// All
				Arguments.of(
						UneditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build(),
						EditableJavaMultiLineComment.builder()
								.content("Something else useful")
								.content("Another line")
								.build(),
						ListUtil.createList("Editable is different!",
								"Content length is different!", "Content differs on #1!")
				)
		);
	}
}
