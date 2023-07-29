package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaMultiLineCommentParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaMultiLineComment, JavaParsingException> parseMethod;
	
	protected BaseJavaMultiLineCommentParserTest(
			ThrowingFunction<String, JavaMultiLineComment, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Base Tests
	 */
	
	@Test
	public void testEmptyComment() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.build(), comment);
	}
	
	@Test
	public void testCommentWithContent() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 * something useful
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithMultipleLinesOfContent() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 * something useful
				 * something else
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.content("something else")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithMultipleLinesOfContentAndBlankLine() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 * something useful
				 *\s
				 * something else
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.content("")
				.content("something else")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithAsteriskInContent() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 * something * useful
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something * useful")
				.build(), comment);
	}
	
	@Test
	public void testMissingEndToken(){
		try{
			parseMethod.apply("""
					/*""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.MULTI_LINE_COMMENT,
							"Failed to find closing multi-line comment token!"),
					e.getMessage());
		}
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testCommentWithContentOnFirstLine() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/* something useful
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithContentOnFirstLineNoSpace() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*something useful
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithContentOnLastLine() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 something useful */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.build(), comment);
	}
	
	@Test
	public void testCommentWithContentOnLastLineNoSpace() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 something useful*/""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something useful")
				.build(), comment);
	}
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				\t     \t  \t
				\t    \t
				\t     \t    /*
				 * something
				 */""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something")
				.build(), comment);
	}
	
	@Test
	public void testTrailingWhitespace() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				/*
				 * something
				 */    \t
				 \t     \t  \t
				\t    \t
				\t     \t    \t""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something")
				.build(), comment);
	}
	
	@Test
	public void testInsaneWhitespace() throws JavaParsingException{
		JavaMultiLineComment comment = parseMethod.apply("""
				\t     \t  \t
				\t    \t
				\t     \t    /*
				 * something
				 */    \t
				 \t     \t  \t
				\t    \t
				\t     \t    \t""");
		assertEquals(EditableJavaMultiLineComment.builder()
				.content("something")
				.build(), comment);
	}
}
