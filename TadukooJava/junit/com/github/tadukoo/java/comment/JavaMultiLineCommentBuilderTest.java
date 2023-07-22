package com.github.tadukoo.java.comment;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaMultiLineCommentBuilderTest{
	
	private static class TestJavaMultiLineComment extends JavaMultiLineComment{
		
		protected TestJavaMultiLineComment(List<String> content){
			super(false, content);
		}
	}
	
	private static class TestJavaMultiLineCommentBuilder extends JavaMultiLineCommentBuilder<TestJavaMultiLineComment>{
		
		@Override
		protected TestJavaMultiLineComment constructComment(){
			return new TestJavaMultiLineComment(content);
		}
	}
	
	private JavaMultiLineComment comment;
	
	@BeforeEach
	public void setup(){
		comment = new TestJavaMultiLineCommentBuilder()
				.build();
	}
	
	@Test
	public void testDefaultContent(){
		assertEquals(ListUtil.createList(), comment.getContent());
	}
	
	@Test
	public void testCopy(){
		JavaMultiLineComment otherComment = new TestJavaMultiLineCommentBuilder()
				.content("something useful")
				.build();
		comment = new TestJavaMultiLineCommentBuilder()
				.copy(otherComment)
				.build();
		assertEquals(otherComment, comment);
	}
	
	@Test
	public void testBuilderSetContent(){
		comment = new TestJavaMultiLineCommentBuilder()
				.content("something useful")
				.build();
		assertEquals(ListUtil.createList("something useful"), comment.getContent());
	}
	
	@Test
	public void testBuilderSetMultiLineContent(){
		comment = new TestJavaMultiLineCommentBuilder()
				.content(ListUtil.createList("something useful", "something else"))
				.build();
		assertEquals(ListUtil.createList("something useful", "something else"), comment.getContent());
	}
}
