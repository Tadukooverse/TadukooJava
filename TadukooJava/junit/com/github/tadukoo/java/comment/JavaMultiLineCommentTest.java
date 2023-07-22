package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaMultiLineCommentTest{
	
	private static class TestJavaMultiLineComment extends JavaMultiLineComment{
		
		protected TestJavaMultiLineComment(boolean editable, List<String> content){
			super(editable, content);
		}
	}
	
	private static class TestJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<TestJavaMultiLineComment>{
		
		private final boolean editable;
		
		private TestJavaMultiLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaMultiLineComment constructComment(){
			return new TestJavaMultiLineComment(editable, content);
		}
	}
	
	private JavaMultiLineComment comment;
	
	@BeforeEach
	public void setup(){
		comment = new TestJavaMultiLineCommentBuilder(true)
				.build();
	}
	
	@Test
	public void testJavaType(){
		assertEquals(JavaCodeTypes.MULTI_LINE_COMMENT, comment.getJavaCodeType());
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(comment.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		comment = new TestJavaMultiLineCommentBuilder(false)
				.build();
		assertFalse(comment.isEditable());
	}
	
	@Test
	public void testDefaultContent(){
		assertEquals(ListUtil.createList(), comment.getContent());
	}
	
	@Test
	public void testToString(){
		assertEquals("/*\n */", comment.toString());
	}
	
	@Test
	public void testToStringWithContent(){
		comment = new TestJavaMultiLineCommentBuilder(false)
				.content("something useful")
				.build();
		assertEquals("""
				/*
				 * something useful
				 */""", comment.toString());
	}
	
	@Test
	public void testToStringWithMultilineContent(){
		comment = new TestJavaMultiLineCommentBuilder(false)
				.content("something useful")
				.content("something else")
				.build();
		assertEquals("""
				/*
				 * something useful
				 * something else
				 */""", comment.toString());
	}
	
	@Test
	public void testEquals(){
		JavaMultiLineComment otherComment = new TestJavaMultiLineCommentBuilder(false)
				.build();
		assertEquals(comment, otherComment);
	}
	
	@Test
	public void testNotEquals(){
		JavaMultiLineComment otherComment = new TestJavaMultiLineCommentBuilder(false)
				.content("something useful")
				.build();
		assertNotEquals(comment, otherComment);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(comment, "testing");
	}
}
