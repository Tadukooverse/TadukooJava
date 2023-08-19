package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaSingleLineCommentTest extends DefaultJavaSingleLineCommentTest<RegularJavaSingleLineCommentTest.TestJavaSingleLineComment>{
	
	public static class TestJavaSingleLineComment extends JavaSingleLineComment{
		
		protected TestJavaSingleLineComment(boolean editable, String content){
			super(editable, content);
		}
		
		public static TestJavaSingleLineCommentBuilder builder(){
			return new TestJavaSingleLineCommentBuilder(false);
		}
	}
	
	public static class TestJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<TestJavaSingleLineComment>{
		
		private final boolean editable;
		
		private TestJavaSingleLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaSingleLineComment constructSingleLineComment(){
			return new TestJavaSingleLineComment(editable, content);
		}
	}
	
	public RegularJavaSingleLineCommentTest(){
		super(TestJavaSingleLineComment.class, TestJavaSingleLineComment::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(comment.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		comment = new TestJavaSingleLineCommentBuilder(true)
				.build();
		assertTrue(comment.isEditable());
	}
}
