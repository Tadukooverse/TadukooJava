package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaSingleLineCommentParserWhitespaceTest extends BaseJavaParserTest{
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
	public void testInsideWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
