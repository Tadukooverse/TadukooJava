package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.DefaultJavadocTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavadocTest extends DefaultJavadocTest<UneditableJavadoc>{
	public UneditableJavadocTest(){
		super(UneditableJavadoc::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(doc.isEditable());
	}
}
