package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaSingleLineCommentParserTest extends BaseJavaParserTest{
	
	@Test
	public void testEmptyComment() throws JavaParsingException{
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runFullParserForSingleLineComment("""
				//something useful here""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful here")
						.build(),
				comment);
		assertEquals("""
				// something useful here""", comment.toString());
	}
}
