package com.github.tadukoo.java.method;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class UneditableJavaMethodTest extends DefaultJavaMethodTest<UneditableJavaMethod>{
	
	public UneditableJavaMethodTest(){
		super(UneditableJavaMethod::builder, UneditableJavadoc::builder, UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(method.isEditable());
	}
	
	@Test
	public void testBuilderJavadocEditable(){
		try{
			UneditableJavaMethod.builder()
					.returnType(returnType)
					.javadoc(EditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not uneditable in this uneditable JavaMethod", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderJavaAnnotationEditable(){
		try{
			UneditableJavaMethod.builder()
					.returnType(returnType)
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not uneditable in this uneditable JavaMethod", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllSpecificErrors(){
		try{
			UneditableJavaMethod.builder()
					.returnType(returnType)
					.javadoc(EditableJavadoc.builder().build())
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not uneditable in this uneditable JavaMethod\n" +
					"some annotations are not uneditable in this uneditable JavaMethod", e.getMessage());
		}
	}
}
