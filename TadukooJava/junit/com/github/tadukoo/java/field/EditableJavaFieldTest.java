package com.github.tadukoo.java.field;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EditableJavaFieldTest extends DefaultJavaFieldTest<EditableJavaField>{
	
	protected EditableJavaFieldTest(){
		super(EditableJavaField::builder, EditableJavadoc::builder, EditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(field.isEditable());
	}
	
	@Test
	public void testBuilderErrorEditableJavadoc(){
		try{
			EditableJavaField.builder()
					.type("int").name("test")
					.javadoc(UneditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not editable in this editable JavaField", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderErrorEditableAnnotation(){
		try{
			EditableJavaField.builder()
					.type("int").name("test")
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not editable in this editable JavaField", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderErrors(){
		try{
			EditableJavaField.builder()
					.type("int").name("test")
					.javadoc(UneditableJavadoc.builder().build())
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not editable in this editable JavaField\n" +
					"some annotations are not editable in this editable JavaField", e.getMessage());
		}
	}
	
	@Test
	public void testSetSectionComment(){
		assertNull(field.getSectionComment());
		field.setSectionComment("Test");
		assertEquals("Test", field.getSectionComment());
		field.setSectionComment("Derp");
		assertEquals("Derp", field.getSectionComment());
	}
	
	@Test
	public void testSetJavadoc(){
		Javadoc doc1 = EditableJavadoc.builder().build();
		Javadoc doc2 = EditableJavadoc.builder().author("Me").build();
		assertNull(field.getJavadoc());
		field.setJavadoc(doc1);
		assertEquals(doc1, field.getJavadoc());
		field.setJavadoc(doc2);
		assertEquals(doc2, field.getJavadoc());
	}
	
	@Test
	public void testSetJavadocNotEditable(){
		try{
			Javadoc doc = UneditableJavadoc.builder().build();
			field.setJavadoc(doc);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires an editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.addAnnotation(test);
		assertEquals(ListUtil.createList(test), field.getAnnotations());
		field.addAnnotation(derp);
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationNotEditable(){
		try{
			JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
			field.addAnnotation(test);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.addAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
		field.addAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(test, derp, blah, yep), field.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationsNotEditable(){
		try{
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			field.addAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.setAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
		field.setAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(blah, yep), field.getAnnotations());
	}
	
	@Test
	public void testSetAnnotationsNotEditable(){
		try{
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			field.setAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		assertNull(field.getVisibility());
		field.setVisibility(Visibility.PUBLIC);
		assertEquals(Visibility.PUBLIC, field.getVisibility());
	}
	
	@Test
	public void testSetStatic(){
		assertFalse(field.isStatic());
		field.setStatic(true);
		assertTrue(field.isStatic());
		field.setStatic(true);
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		assertFalse(field.isFinal());
		field.setFinal(true);
		assertTrue(field.isFinal());
		field.setFinal(true);
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testSetType(){
		assertEquals(type, field.getType());
		field.setType("String");
		assertEquals("String", field.getType());
		field.setType("double");
		assertEquals("double", field.getType());
	}
	
	@Test
	public void testSetName(){
		assertEquals(name, field.getName());
		field.setName("derp");
		assertEquals("derp", field.getName());
		field.setName("blah");
		assertEquals("blah", field.getName());
	}
	
	@Test
	public void testSetValue(){
		assertNull(field.getValue());
		field.setValue("42");
		assertEquals("42", field.getValue());
		field.setValue("27");
		assertEquals("27", field.getValue());
	}
}
