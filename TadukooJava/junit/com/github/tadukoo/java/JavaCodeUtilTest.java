package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaCodeUtilTest{
	
	@Test
	public void testConvertToJavaStringNull(){
		assertNull(JavaCodeUtil.convertToJavaString(null));
	}
	
	@Test
	public void testConvertToJavaStringString(){
		assertEquals("testing", JavaCodeUtil.convertToJavaString("testing"));
	}
	
	@Test
	public void testConvertToJavaStringEnum(){
		assertEquals("JavaCodeTypes.CLASS", JavaCodeUtil.convertToJavaString(JavaCodeTypes.CLASS));
	}
	
	@Test
	public void testConvertToJavaStringAssertion(){
		JavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		assertEquals(annotation.toString(), JavaCodeUtil.convertToJavaString(annotation));
	}
}
