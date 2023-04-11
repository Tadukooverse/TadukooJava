package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.DefaultJavaAnnotationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaAnnotationTest extends DefaultJavaAnnotationTest<UneditableJavaAnnotation>{
	
	public UneditableJavaAnnotationTest(){
		super(UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(annotation.isEditable());
	}
}
