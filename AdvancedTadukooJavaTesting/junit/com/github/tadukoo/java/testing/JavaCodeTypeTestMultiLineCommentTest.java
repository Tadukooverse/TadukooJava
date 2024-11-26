package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertMultiLineCommentEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findMultiLineCommentDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestMultiLineCommentTest{
	
	@Test
	public void testFindMultiLineCommentDifferencesNone(){
		assertEquals(new ArrayList<>(), findMultiLineCommentDifferences(
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findMultiLineCommentDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesComment1NullComment2Not(){
		assertEquals(ListUtil.createList("One of the multi-line comments is null, and the other isn't!"),
				findMultiLineCommentDifferences(
				null,
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesComment2NullComment1Not(){
		assertEquals(ListUtil.createList("One of the multi-line comments is null, and the other isn't!"),
				findMultiLineCommentDifferences(
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findMultiLineCommentDifferences(
				UneditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesContent(){
		assertEquals(ListUtil.createList("Content differs on #1!"), findMultiLineCommentDifferences(
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something else useful")
						.build()
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesContentLength(){
		assertEquals(ListUtil.createList("Content length is different!",
				"Content differs on #2!"), findMultiLineCommentDifferences(
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.content("Another line")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindMultiLineCommentDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!",
				"Content length is different!", "Content differs on #1!"), findMultiLineCommentDifferences(
				UneditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something else useful")
						.content("Another line")
						.build()
		));
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsNone(){
		assertMultiLineCommentEquals(
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build()
		);
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsBothNull(){
		assertMultiLineCommentEquals(null, null);
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsComment1NullComment2Not(){
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertMultiLineCommentEquals(null, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the multi-line comments is null, and the other isn't!",
					buildAssertError(null, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsComment2NullComment1Not(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertMultiLineCommentEquals(comment1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the multi-line comments is null, and the other isn't!",
					buildAssertError(comment1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsEditable(){
		JavaMultiLineComment comment1 = UneditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertMultiLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsContent(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("Something else useful")
				.build();
		try{
			assertMultiLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content differs on #1!",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsContentLength(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.content("Another line")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertMultiLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Content length is different!
					Content differs on #2!""",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMultiLineCommentEqualsAll(){
		JavaMultiLineComment comment1 = UneditableJavaMultiLineComment.builder()
				.content("Something useful")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("Something else useful")
				.content("Another line")
				.build();
		try{
			assertMultiLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
							Editable is different!
							Content length is different!
							Content differs on #1!""",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
}
