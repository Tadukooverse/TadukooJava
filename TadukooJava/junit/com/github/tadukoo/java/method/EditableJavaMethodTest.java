package com.github.tadukoo.java.method;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EditableJavaMethodTest extends DefaultJavaMethodTest<EditableJavaMethod>{
	
	public EditableJavaMethodTest(){
		super(EditableJavaMethod::builder, EditableJavadoc::builder, EditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(method.isEditable());
	}
	
	@Test
	public void testBuilderJavadocUneditable(){
		try{
			EditableJavaMethod.builder()
					.returnType(returnType)
					.javadoc(UneditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not editable in this editable JavaMethod", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderJavaAnnotationEditable(){
		try{
			EditableJavaMethod.builder()
					.returnType(returnType)
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not editable in this editable JavaMethod", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllSpecificErrors(){
		try{
			EditableJavaMethod.builder()
					.returnType(returnType)
					.javadoc(UneditableJavadoc.builder().build())
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not editable in this editable JavaMethod\n" +
					"some annotations are not editable in this editable JavaMethod", e.getMessage());
		}
	}
	
	@Test
	public void testSetJavadoc(){
		assertNull(method.getJavadoc());
		method.setJavadoc(EditableJavadoc.builder().build());
		assertEquals(EditableJavadoc.builder().build(), method.getJavadoc());
		method.setJavadoc(EditableJavadoc.builder().author("Me").build());
		assertEquals(EditableJavadoc.builder().author("Me").build(), method.getJavadoc());
	}
	
	@Test
	public void testSetJavadocUneditable(){
		try{
			method.setJavadoc(UneditableJavadoc.builder().build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.addAnnotation(EditableJavaAnnotation.builder().name("Test").build());
		assertEquals(ListUtil.createList(EditableJavaAnnotation.builder().name("Test").build()), method.getAnnotations());
		method.addAnnotation(EditableJavaAnnotation.builder().name("Derp").build());
		assertEquals(ListUtil.createList(EditableJavaAnnotation.builder().name("Test").build(),
				EditableJavaAnnotation.builder().name("Derp").build()), method.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationUneditable(){
		try{
			method.addAnnotation(UneditableJavaAnnotation.builder().name("Test").build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("Yep").build();
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.addAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), method.getAnnotations());
		method.addAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(test, derp, blah, yep), method.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationsUneditable(){
		try{
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			method.addAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("Yep").build();
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.setAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), method.getAnnotations());
		method.setAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(blah, yep), method.getAnnotations());
	}
	
	@Test
	public void testSetAnnotationsUneditable(){
		try{
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			method.setAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		assertEquals(Visibility.NONE, method.getVisibility());
		method.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, method.getVisibility());
	}
	
	@Test
	public void testSetAbstract(){
		assertFalse(method.isAbstract());
		method.setAbstract(true);
		assertTrue(method.isAbstract());
		method.setAbstract(true);
		assertTrue(method.isAbstract());
	}
	
	@Test
	public void testSetStatic(){
		assertFalse(method.isStatic());
		method.setStatic(true);
		assertTrue(method.isStatic());
		method.setStatic(true);
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		assertFalse(method.isFinal());
		method.setFinal(true);
		assertTrue(method.isFinal());
		method.setFinal(true);
		assertTrue(method.isFinal());
	}
	
	@Test
	public void testSetReturnType(){
		assertEquals(returnType, method.getReturnType());
		method.setReturnType("String");
		assertEquals("String", method.getReturnType());
		method.setReturnType("Derp");
		assertEquals("Derp", method.getReturnType());
	}
	
	@Test
	public void testSetName(){
		assertNull(method.getName());
		method.setName("something");
		assertEquals("something", method.getName());
		method.setName("somethingElse");
		assertEquals("somethingElse", method.getName());
	}
	
	@Test
	public void testAddParameterPieces(){
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameter("String", "test");
		assertEquals(ListUtil.createList(Pair.of("String", "test")), method.getParameters());
		method.addParameter("int", "something");
		assertEquals(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")),
				method.getParameters());
	}
	
	@Test
	public void testAddParameter(){
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameter(Pair.of("String", "test"));
		assertEquals(ListUtil.createList(Pair.of("String", "test")), method.getParameters());
		method.addParameter(Pair.of("int", "something"));
		assertEquals(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")),
				method.getParameters());
	}
	
	@Test
	public void testAddParameters(){
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameters(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")));
		assertEquals(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")),
				method.getParameters());
		method.addParameters(ListUtil.createList(Pair.of("String", "derp"), Pair.of("double", "blah")));
		assertEquals(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something"),
						Pair.of("String", "derp"), Pair.of("double", "blah")),
				method.getParameters());
	}
	
	@Test
	public void testSetParameters(){
		assertEquals(new ArrayList<>(), method.getParameters());
		method.setParameters(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")));
		assertEquals(ListUtil.createList(Pair.of("String", "test"), Pair.of("int", "something")),
				method.getParameters());
		method.setParameters(ListUtil.createList(Pair.of("String", "derp"), Pair.of("double", "blah")));
		assertEquals(ListUtil.createList(Pair.of("String", "derp"), Pair.of("double", "blah")),
				method.getParameters());
	}
	
	@Test
	public void testAddThrowType(){
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.addThrowType("NoException");
		assertEquals(ListUtil.createList("NoException"), method.getThrowTypes());
		method.addThrowType("Throwable");
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
	}
	
	@Test
	public void testAddThrowTypes(){
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.addThrowTypes(ListUtil.createList("NoException", "Throwable"));
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
		method.addThrowTypes(ListUtil.createList("String", "Exception"));
		assertEquals(ListUtil.createList("NoException", "Throwable", "String", "Exception"), method.getThrowTypes());
	}
	
	@Test
	public void testSetThrowTypes(){
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.setThrowTypes(ListUtil.createList("NoException", "Throwable"));
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
		method.setThrowTypes(ListUtil.createList("String", "Exception"));
		assertEquals(ListUtil.createList("String", "Exception"), method.getThrowTypes());
	}
	
	@Test
	public void testAddLine(){
		assertEquals(new ArrayList<>(), method.getLines());
		method.addLine("String test = \"42\";");
		assertEquals(ListUtil.createList("String test = \"42\";"), method.getLines());
		method.addLine("test.toString();");
		assertEquals(ListUtil.createList("String test = \"42\";", "test.toString();"), method.getLines());
	}
	
	@Test
	public void testAddLines(){
		String line1 = "String test = \"42\";";
		String line2 = "test.toString();";
		String line3 = "String line1 = \"String test = \"42\";\";";
		String line4 = "// yep";
		assertEquals(new ArrayList<>(), method.getLines());
		method.addLines(ListUtil.createList(line1, line2));
		assertEquals(ListUtil.createList(line1, line2), method.getLines());
		method.addLines(ListUtil.createList(line3, line4));
		assertEquals(ListUtil.createList(line1, line2, line3, line4), method.getLines());
	}
	
	@Test
	public void testSetLines(){
		String line1 = "String test = \"42\";";
		String line2 = "test.toString();";
		String line3 = "String line1 = \"String test = \"42\";\";";
		String line4 = "// yep";
		assertEquals(new ArrayList<>(), method.getLines());
		method.setLines(ListUtil.createList(line1, line2));
		assertEquals(ListUtil.createList(line1, line2), method.getLines());
		method.setLines(ListUtil.createList(line3, line4));
		assertEquals(ListUtil.createList(line3, line4), method.getLines());
	}
}
