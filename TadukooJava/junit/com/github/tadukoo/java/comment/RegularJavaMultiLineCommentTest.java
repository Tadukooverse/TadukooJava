package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaMultiLineCommentTest extends DefaultJavaMultiLineCommentTest<RegularJavaMultiLineCommentTest.TestJavaMultiLineComment>{
	
	public static class TestJavaMultiLineComment extends JavaMultiLineComment{
		
		protected TestJavaMultiLineComment(boolean editable, List<String> content){
			super(editable, content);
		}
		
		public static TestJavaMultiLineCommentBuilder builder(){
			return new TestJavaMultiLineCommentBuilder(false);
		}
	}
	
	public static class TestJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<TestJavaMultiLineComment>{
		
		private final boolean editable;
		
		private TestJavaMultiLineCommentBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaMultiLineComment constructComment(){
			return new TestJavaMultiLineComment(editable, content);
		}
	}
	
	public RegularJavaMultiLineCommentTest(){
		super(TestJavaMultiLineComment::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(comment.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		comment = new TestJavaMultiLineCommentBuilder(true)
				.build();
		assertTrue(comment.isEditable());
	}
}
