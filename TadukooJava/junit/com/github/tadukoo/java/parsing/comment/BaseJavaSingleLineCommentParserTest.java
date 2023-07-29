package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class BaseJavaSingleLineCommentParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaSingleLineComment, JavaParsingException> parseMethod;
	
	protected BaseJavaSingleLineCommentParserTest(
			ThrowingFunction<String, JavaSingleLineComment, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Base Parsing Tests
	 */
	
	@Test
	public void testEmptyComment() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				//""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.build(),
				comment);
		assertEquals("""
				//\s""", comment.toString());
	}
	
	@Test
	public void testCommentWithContent() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				// something useful here""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful here")
						.build(),
				comment);
		assertEquals("""
				// something useful here""", comment.toString());
	}
	
	@Test
	public void testCommentWithImmediateContent() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				//something useful here""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful here")
						.build(),
				comment);
		assertEquals("""
				// something useful here""", comment.toString());
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				\t     \t  \t
				\t    // something useful""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment);
		assertEquals("""
				// something useful""", comment.toString());
	}
	
	@Test
	public void testTrailingWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				// something useful \t    \t \t
				\t     \t""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment);
		assertEquals("""
				// something useful""", comment.toString());
	}
	
	@Test
	public void testMoreTrailingWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				// something useful \t      \t \t
				\t     \t
				\t  \t
				\t  \t""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment
		);
		assertEquals("""
				// something useful""", comment.toString());
	}
	
	@Test
	public void testInsideWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				// something \t     \t   useful""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment);
		assertEquals("""
				// something useful""", comment.toString());
	}
	
	@Test
	public void testWhitespaceAfterStartToken() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				// \t     \t   something useful""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment);
		assertEquals("""
				// something useful""", comment.toString());
	}
	
	@Test
	public void testInsaneWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = parseMethod.apply("""
				\t     \t  \t
				\t    // \t     \t   something \t     \t   useful \t     \t  \t
				\t     \t""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful")
						.build(),
				comment);
		assertEquals("""
				// something useful""", comment.toString());
	}
}
