package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserSingleLineCommentTest extends BaseJavaParserTest{
	
	@Test
	public void testEmptyComment() throws JavaParsingException{
		JavaSingleLineComment comment = runParserForSingleLineComment("""
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
		JavaSingleLineComment comment = runParserForSingleLineComment("""
				// something useful here""");
		assertEquals(
				EditableJavaSingleLineComment.builder()
						.content("something useful here")
						.build(),
				comment);
		assertEquals("""
				// something useful here""", comment.toString());
	}
}
