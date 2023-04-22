package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class UneditableJavaClassTest extends DefaultJavaClassTest<UneditableJavaClass>{
	
	public UneditableJavaClassTest(){
		super(UneditableJavaClass::builder, UneditableJavaPackageDeclaration::builder, UneditableJavaImportStatement::builder,
				UneditableJavaAnnotation::builder, UneditableJavadoc::builder,
				UneditableJavaField::builder, UneditableJavaMethod::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(clazz.isEditable());
	}
	
	@Test
	public void testBuilderEditablePackageDeclarationError(){
		try{
			UneditableJavaClass.builder()
					.packageDeclaration(EditableJavaPackageDeclaration.builder()
							.packageName("some.package")
							.build())
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("package declaration is not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableImportStatementError(){
		try{
			UneditableJavaClass.builder()
					.importStatement(EditableJavaImportStatement.builder()
							.importName("com.example")
							.build())
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some import statements are not uneditable in this uneditable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderEditableJavadocError(){
		try{
			UneditableJavaClass.builder()
					.className(className)
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
					.className(className)
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
					.className(className)
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
					.className(className)
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
					.className(className)
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
					.packageDeclaration(EditableJavaPackageDeclaration.builder()
							.packageName("some.package")
							.build())
					.importStatement(EditableJavaImportStatement.builder()
							.importName("com.example")
							.build())
					.className(className)
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
					package declaration is not uneditable in this uneditable JavaClass
					some import statements are not uneditable in this uneditable JavaClass
					javadoc is not uneditable in this uneditable JavaClass
					some annotations are not uneditable in this uneditable JavaClass
					some inner classes are not uneditable in this uneditable JavaClass
					some fields are not uneditable in this uneditable JavaClass
					some methods are not uneditable in this uneditable JavaClass""", e.getMessage());
		}
	}
}
