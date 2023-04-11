package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.DefaultJavaClassTest;
import com.github.tadukoo.java.editable.EditableJavaAnnotation;
import com.github.tadukoo.java.editable.EditableJavaClass;
import com.github.tadukoo.java.editable.EditableJavaField;
import com.github.tadukoo.java.editable.EditableJavaMethod;
import com.github.tadukoo.java.editable.EditableJavadoc;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class UneditableJavaClassTest extends DefaultJavaClassTest<UneditableJavaClass>{
	
	public UneditableJavaClassTest(){
		super(UneditableJavaClass::builder, UneditableJavaAnnotation::builder, UneditableJavadoc::builder,
				UneditableJavaField::builder, UneditableJavaMethod::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(clazz.isEditable());
	}
	
	@Test
	public void testBuilderEditableJavadocError(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.javadoc(EditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableAnnotationError(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableInnerClassError(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.innerClass(EditableJavaClass.builder()
							.innerClass()
							.className(className)
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some inner classes are not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableFieldError(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.field(EditableJavaField.builder()
							.type("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some fields are not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableMethodError(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.method(EditableJavaMethod.builder()
							.returnType("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some methods are not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderSpecificErrors(){
		try{
			UneditableJavaClass.builder()
					.packageName(packageName).className(className)
					.javadoc(EditableJavadoc.builder().build())
					.annotation(EditableJavaAnnotation.builder().name("Test").build())
					.innerClass(EditableJavaClass.builder()
							.innerClass()
							.className(className)
							.build())
					.field(EditableJavaField.builder()
							.type("String").name("test")
							.build())
					.method(EditableJavaMethod.builder()
							.returnType("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					javadoc is not uneditable in this uneditable JavaClass
					some annotations are not uneditable in this uneditable JavaClass
					some inner classes are not uneditable in this uneditable JavaClass
					some fields are not uneditable in this uneditable JavaClass
					some methods are not uneditable in this uneditable JavaClass""", e.getMessage());
		}
	}
}
