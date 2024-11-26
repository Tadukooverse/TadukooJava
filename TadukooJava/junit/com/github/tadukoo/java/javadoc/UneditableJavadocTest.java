package com.github.tadukoo.java.javadoc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavadocTest extends DefaultJavadocTest<UneditableJavadoc>{
	public UneditableJavadocTest(){
		super(UneditableJavadoc.class, UneditableJavadoc::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(doc.isEditable());
	}
}
