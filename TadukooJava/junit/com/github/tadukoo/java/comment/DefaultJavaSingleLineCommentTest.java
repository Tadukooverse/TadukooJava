package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public abstract class DefaultJavaSingleLineCommentTest<CommentType extends JavaSingleLineComment>{
	private final ThrowingSupplier<JavaSingleLineCommentBuilder<CommentType>, NoException> builder;
	protected CommentType comment;
	
	protected DefaultJavaSingleLineCommentTest(ThrowingSupplier<JavaSingleLineCommentBuilder<CommentType>, NoException> builder){
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		comment = builder.get()
				.build();
	}
	
	@Test
	public void testJavaType(){
		assertEquals(JavaCodeTypes.SINGLE_LINE_COMMENT, comment.getJavaCodeType());
	}
	
	@Test
	public void testDefaultContent(){
		assertEquals("", comment.getContent());
	}
	
	@Test
	public void testCopy(){
		JavaSingleLineComment otherComment = builder.get()
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
		assertEquals("something useful", comment.getContent());
	}
	
	@Test
	public void testToString(){
		assertEquals("// ", comment.toString());
	}
	
	@Test
	public void testToStringWithContent(){
		comment = builder.get()
				.content("something useful")
				.build();
		assertEquals("// something useful", comment.toString());
	}
	
	@Test
	public void testEquals(){
		JavaSingleLineComment otherComment = builder.get()
				.build();
		assertEquals(comment, otherComment);
	}
	
	@Test
	public void testNotEquals(){
		JavaSingleLineComment otherComment = builder.get()
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
