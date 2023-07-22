package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class DefaultJavaMultiLineCommentTest<CommentType extends JavaMultiLineComment>{
	
	private final ThrowingSupplier<JavaMultiLineCommentBuilder<CommentType>, NoException> builder;
	protected CommentType comment;
	
	protected DefaultJavaMultiLineCommentTest(ThrowingSupplier<JavaMultiLineCommentBuilder<CommentType>, NoException> builder){
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		comment = builder.get()
				.build();
	}
	
	@Test
	public void testJavaType(){
		assertEquals(JavaCodeTypes.MULTI_LINE_COMMENT, comment.getJavaCodeType());
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
		comment = builder.get()
				.content("something useful")
				.build();
		assertEquals("""
				/*
				 * something useful
				 */""", comment.toString());
	}
	
	@Test
	public void testToStringWithMultilineContent(){
		comment = builder.get()
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
		JavaMultiLineComment otherComment = builder.get()
				.build();
		assertEquals(comment, otherComment);
	}
	
	@Test
	public void testNotEquals(){
		JavaMultiLineComment otherComment = builder.get()
				.content("something useful")
				.build();
		assertNotEquals(comment, otherComment);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(comment, "testing");
	}
	
	@Test
	public void testCopy(){
		JavaMultiLineComment otherComment = builder.get()
				.content("something useful")
				.build();
		comment = builder.get()
				.copy(otherComment)
				.build();
		assertEquals(otherComment, comment);
	}
	
	@Test
	public void testBuilderSetContent(){
		comment = builder.get()
				.content("something useful")
				.build();
		assertEquals(ListUtil.createList("something useful"), comment.getContent());
	}
	
	@Test
	public void testBuilderSetMultiLineContent(){
		comment = builder.get()
				.content(ListUtil.createList("something useful", "something else"))
				.build();
		assertEquals(ListUtil.createList("something useful", "something else"), comment.getContent());
	}
}
