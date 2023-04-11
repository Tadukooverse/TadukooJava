package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.DefaultJavaAnnotationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaAnnotationTest extends DefaultJavaAnnotationTest<EditableJavaAnnotation>{
	
	public EditableJavaAnnotationTest(){
		super(EditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(annotation.isEditable());
	}
	
	@Test
	public void testSetName(){
		assertEquals("Test", annotation.getName());
		annotation.setName("Derp");
		assertEquals("Derp", annotation.getName());
	}
}
