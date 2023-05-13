package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaSingleLineCommentBuilderTest{
	
	private static class TestJavaSingleLineComment extends JavaSingleLineComment{
		
		protected TestJavaSingleLineComment(String content){
			super(false, content);
		}
	}
	
	private static class TestJavaSingleLineCommentBuilder extends JavaSingleLineCommentBuilder<TestJavaSingleLineComment>{
		
		@Override
		protected TestJavaSingleLineComment constructSingleLineComment(){
			return new TestJavaSingleLineComment(content);
		}
	}
	
	private JavaSingleLineComment comment;
	
	@BeforeEach
	public void setup(){
		comment = new TestJavaSingleLineCommentBuilder()
				.build();
	}
	
	@Test
	public void testDefaultContent(){
		assertEquals("", comment.getContent());
	}
	
	@Test
	public void testCopy(){
		JavaSingleLineComment otherComment = new TestJavaSingleLineCommentBuilder()
				.content("something useful")
				.build();
		comment = new TestJavaSingleLineCommentBuilder()
				.copy(otherComment)
				.build();
		assertEquals(otherComment, comment);
	}
	
	@Test
	public void testBuilderSetContent(){
		comment = new TestJavaSingleLineCommentBuilder()
				.content("something useful")
				.build();
		assertEquals("something useful", comment.getContent());
	}
}
