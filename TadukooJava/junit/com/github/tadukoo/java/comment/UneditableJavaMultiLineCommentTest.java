package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaMultiLineCommentTest extends DefaultJavaMultiLineCommentTest<UneditableJavaMultiLineComment>{
	
	public UneditableJavaMultiLineCommentTest(){
		super(UneditableJavaMultiLineComment::builder);
	}
	
	@Test
	public void testNotEditable(){
		assertFalse(comment.isEditable());
	}
}
