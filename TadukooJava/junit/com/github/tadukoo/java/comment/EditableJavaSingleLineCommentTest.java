package com.github.tadukoo.java.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaSingleLineCommentTest extends DefaultJavaSingleLineCommentTest<EditableJavaSingleLineComment>{
	public EditableJavaSingleLineCommentTest(){
		super(EditableJavaSingleLineComment.class, EditableJavaSingleLineComment::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(comment.isEditable());
	}
	
	@Test
	public void testSetContent(){
		assertEquals("", comment.getContent());
		comment.setContent("something dumb");
		assertEquals("something dumb", comment.getContent());
	}
}
