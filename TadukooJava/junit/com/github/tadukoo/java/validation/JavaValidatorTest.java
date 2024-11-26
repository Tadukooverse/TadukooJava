package com.github.tadukoo.java.validation;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaValidatorTest{
	
	private JavaValidator validator;
	
	@BeforeEach
	public void setup(){
		validator = new JavaValidator();
	}
	
	@Test
	public void testDetermineCanonicalNamesNotEditableJavaClass(){
		try{
			validator.determineCanonicalNames(UneditableJavaClass.builder()
					.className("Test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Need EditableJavaClass to modify canonical names", e.getMessage());
		}
	}
	
	@Test
	public void testDetermineCanonicalNamesSuccess(){
		JavaAnnotation annotation = EditableJavaAnnotation.builder()
				.name("Something")
				.build();
		String canonicalName = "com.example.Something";
		validator.determineCanonicalNames(EditableJavaClass.builder()
				.importName(canonicalName, false)
				.className("Test")
				.annotation(annotation)
				.build());
		assertEquals(canonicalName, annotation.getCanonicalName());
	}
}
