package com.github.tadukoo.java.comment;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaMultiLineCommentTest extends DefaultJavaMultiLineCommentTest<EditableJavaMultiLineComment>{
	
	public EditableJavaMultiLineCommentTest(){
		super(EditableJavaMultiLineComment::builder);
	}
	
	@Test
	public void testEditable(){
		assertTrue(comment.isEditable());
	}
	
	@Test
	public void testAddContent(){
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.addContent("something");
		assertEquals(ListUtil.createList("something"), comment.getContent());
		comment.addContent("something else");
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
	}
	
	@Test
	public void testAddMultiComments(){
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.addContent(ListUtil.createList("something", "something else"));
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
		comment.addContent(ListUtil.createList("yep", "nope"));
		assertEquals(ListUtil.createList("something", "something else", "yep", "nope"), comment.getContent());
	}
	
	@Test
	public void testSetContent(){
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.setContent(ListUtil.createList("something", "something else"));
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
		comment.setContent(ListUtil.createList("yep", "nope"));
		assertEquals(ListUtil.createList("yep", "nope"), comment.getContent());
	}
}
