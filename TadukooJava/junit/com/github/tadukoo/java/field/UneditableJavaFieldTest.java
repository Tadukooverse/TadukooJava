package com.github.tadukoo.java.field;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class UneditableJavaFieldTest extends DefaultJavaFieldTest<UneditableJavaField>{
	
	public UneditableJavaFieldTest(){
		super(UneditableJavaField.class, UneditableJavaField::builder, UneditableJavadoc::builder, UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(field.isEditable());
	}
	
	@Test
	public void testBuilderErrorEditableJavadoc(){
		try{
			UneditableJavaField.builder()
					.type("int").name("test")
					.javadoc(EditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not uneditable in this uneditable JavaField", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderErrorEditableAnnotation(){
		try{
			UneditableJavaField.builder()
					.type("int").name("test")
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not uneditable in this uneditable JavaField", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderErrors(){
		try{
			UneditableJavaField.builder()
					.type("int").name("test")
					.javadoc(EditableJavadoc.builder().build())
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not uneditable in this uneditable JavaField\n" +
					"some annotations are not uneditable in this uneditable JavaField", e.getMessage());
		}
	}
}
