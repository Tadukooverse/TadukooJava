package com.github.tadukoo.java.annotation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaAnnotationTest extends DefaultJavaAnnotationTest<UneditableJavaAnnotation>{
	
	public UneditableJavaAnnotationTest(){
		super(UneditableJavaAnnotation.class, UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(annotation.isEditable());
	}
}
