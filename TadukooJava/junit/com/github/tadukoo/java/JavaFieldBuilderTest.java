package com.github.tadukoo.java;

import com.github.tadukoo.java.uneditable.UneditableJavaAnnotation;
import com.github.tadukoo.java.uneditable.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaFieldBuilderTest{
	
	private static class TestJavaField extends JavaField{
		
		protected TestJavaField(
				String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal,
				String type, String name, String value){
			super(false, sectionComment, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	private static class TestJavaFieldBuilder extends JavaFieldBuilder<TestJavaField>{
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaField constructField(){
			return new TestJavaField(sectionComment, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	private TestJavaField field;
	private String type, name;
	
	@BeforeEach
	public void setup(){
		type = "int";
		name = "test";
		field = new TestJavaFieldBuilder()
				.type(type).name(name)
				.build();
	}
	
	@Test
	public void testDefaultSectionComment(){
		assertNull(field.getSectionComment());
	}
	
	@Test
	public void testDefaultJavadoc(){
		assertNull(field.getJavadoc());
	}
	
	@Test
	public void testDefaultAnnotations(){
		assertTrue(field.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		assertEquals(Visibility.PRIVATE, field.getVisibility());
	}
	
	@Test
	public void testDefaultIsStatic(){
		assertFalse(field.isStatic());
	}
	
	@Test
	public void testDefaultIsFinal(){
		assertFalse(field.isFinal());
	}
	
	@Test
	public void testDefaultValue(){
		assertNull(field.getValue());
	}
	
	@Test
	public void testSetType(){
		assertEquals("int", field.getType());
	}
	
	@Test
	public void testSetName(){
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testSetSectionComment(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name)
				.sectionComment("Test comment")
				.build();
		assertEquals("Test comment", field.getSectionComment());
	}
	
	@Test
	public void testSetJavadoc(){
		Javadoc doc = UneditableJavadoc.builder().build();
		field = new TestJavaFieldBuilder()
				.type(type).name(name)
				.javadoc(doc)
				.build();
		assertEquals(doc, field.getJavadoc());
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		field = new TestJavaFieldBuilder()
				.annotations(annotations)
				.type(type).name(name)
				.build();
		assertEquals(annotations, field.getAnnotations());
	}
	
	@Test
	public void testSetAnnotation(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		field = new TestJavaFieldBuilder()
				.annotation(test)
				.type(type).name("Test").build();
		List<JavaAnnotation> annotations = field.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testSetVisibility(){
		field = new TestJavaFieldBuilder()
				.visibility(Visibility.PUBLIC).type(type).name(name)
				.build();
		
		assertEquals(Visibility.PUBLIC, field.getVisibility());
	}
	
	@Test
	public void testSetIsStatic(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isStatic(false)
				.build();
		assertFalse(field.isStatic());
		
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isStatic(true)
				.build();
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testIsStatic(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isStatic()
				.build();
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testSetIsFinal(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isFinal(false)
				.build();
		assertFalse(field.isFinal());
		
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isFinal(true)
				.build();
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testIsFinal(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name).isFinal()
				.build();
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testSetValue(){
		field = new TestJavaFieldBuilder()
				.type(type).name(name)
				.value("42")
				.build();
		assertEquals("42", field.getValue());
	}
	
	@Test
	public void testNullType(){
		try{
			field = new TestJavaFieldBuilder()
					.name(name)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify type!", e.getMessage());
		}
	}
	
	@Test
	public void testNullName(){
		try{
			field = new TestJavaFieldBuilder()
					.type(type)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testNullTypeAndName(){
		try{
			field = new TestJavaFieldBuilder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify type!\nMust specify name!", e.getMessage());
		}
	}
}
