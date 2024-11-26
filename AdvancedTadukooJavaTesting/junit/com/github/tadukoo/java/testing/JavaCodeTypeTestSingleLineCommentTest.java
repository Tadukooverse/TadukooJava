package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertSingleLineCommentEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findSingleLineCommentDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestSingleLineCommentTest{
	
	@Test
	public void testFindSingleLineCommentDifferencesNone(){
		assertEquals(new ArrayList<>(), findSingleLineCommentDifferences(
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findSingleLineCommentDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesComment1NullComment2Not(){
		assertEquals(ListUtil.createList("One of the single-line comments is null, and the other isn't!"),
				findSingleLineCommentDifferences(
				null,
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesComment2NullComment1Not(){
		assertEquals(ListUtil.createList("One of the single-line comments is null, and the other isn't!"),
				findSingleLineCommentDifferences(
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findSingleLineCommentDifferences(
				UneditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build()
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesContent(){
		assertEquals(ListUtil.createList("Content is different!"), findSingleLineCommentDifferences(
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaSingleLineComment.builder()
						.content("Something else useful")
						.build()
		));
	}
	
	@Test
	public void testFindSingleLineCommentDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!",
				"Content is different!"), findSingleLineCommentDifferences(
				UneditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaSingleLineComment.builder()
						.content("Something else useful")
						.build()
		));
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsNone(){
		assertSingleLineCommentEquals(
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build(),
				EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build()
		);
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsBothNull(){
		assertSingleLineCommentEquals(null, null);
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsComment1NullComment2Not(){
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertSingleLineCommentEquals(null, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the single-line comments is null, and the other isn't!",
					buildAssertError(null, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsComment2NullComment1Not(){
		JavaSingleLineComment comment1 = EditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertSingleLineCommentEquals(comment1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the single-line comments is null, and the other isn't!",
					buildAssertError(comment1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsEditable(){
		JavaSingleLineComment comment1 = UneditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		try{
			assertSingleLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsContent(){
		JavaSingleLineComment comment1 = EditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("Something else useful")
				.build();
		try{
			assertSingleLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content is different!",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertSingleLineCommentEqualsAll(){
		JavaSingleLineComment comment1 = UneditableJavaSingleLineComment.builder()
				.content("Something useful")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("Something else useful")
				.build();
		try{
			assertSingleLineCommentEquals(comment1, comment2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
							Editable is different!
							Content is different!""",
					buildAssertError(comment1, comment2)), e.getMessage());
		}
	}
}
