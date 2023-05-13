package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaSingleLineCommentTest{
	
	private static class TestJavaSingleLineComment extends JavaSingleLineComment{
		
		protected TestJavaSingleLineComment(boolean editable, String content){
			super(editable, content);
		}
	}
	
	private static class TestJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<TestJavaSingleLineComment>{
		
		private final boolean editable;
		
		private TestJavaSingleLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaSingleLineComment constructSingleLineComment(){
			return new TestJavaSingleLineComment(editable, content);
		}
	}
	
	private JavaSingleLineComment comment;
	
	@BeforeEach
	public void setup(){
		comment = new TestJavaSingleLineCommentBuilder(true)
				.build();
	}
	
	@Test
	public void testJavaType(){
		assertEquals(JavaCodeTypes.SINGLE_LINE_COMMENT, comment.getJavaCodeType());
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(comment.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		comment = new TestJavaSingleLineCommentBuilder(false)
				.build();
		assertFalse(comment.isEditable());
	}
	
	@Test
	public void testDefaultContent(){
		assertEquals("", comment.getContent());
	}
	
	@Test
	public void testToString(){
		assertEquals("// ", comment.toString());
	}
	
	@Test
	public void testToStringWithContent(){
		comment = new TestJavaSingleLineCommentBuilder(false)
				.content("something useful")
				.build();
		assertEquals("// something useful", comment.toString());
	}
	
	@Test
	public void testEquals(){
		JavaSingleLineComment otherComment = new TestJavaSingleLineCommentBuilder(false)
				.build();
		assertEquals(comment, otherComment);
	}
	
	@Test
	public void testNotEquals(){
		JavaSingleLineComment otherComment = new TestJavaSingleLineCommentBuilder(false)
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
