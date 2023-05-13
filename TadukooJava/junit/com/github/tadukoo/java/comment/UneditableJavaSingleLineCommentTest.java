package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaSingleLineCommentTest extends DefaultJavaSingleLineCommentTest<UneditableJavaSingleLineComment>{
	public UneditableJavaSingleLineCommentTest(){
		super(UneditableJavaSingleLineComment::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(comment.isEditable());
	}
}
