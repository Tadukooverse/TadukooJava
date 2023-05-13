package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserWhitespaceSingleLineCommentTest extends BaseJavaParserTest{
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
