package com.github.tadukoo.java.annotation;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaAnnotationTest extends DefaultJavaAnnotationTest<EditableJavaAnnotation>{
	
	public EditableJavaAnnotationTest(){
		super(EditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(annotation.isEditable());
	}
	
	@Test
	public void testSetName(){
		assertEquals("Test", annotation.getName());
		annotation.setName("Derp");
		assertEquals("Derp", annotation.getName());
	}
	
	@Test
	public void testAddParameterPieces(){
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameter("test", "true");
		assertEquals(ListUtil.createList(Pair.of("test", "true")), annotation.getParameters());
		annotation.addParameter("derp", "String.class");
		assertEquals(ListUtil.createList(Pair.of("test", "true"), Pair.of("derp", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testAddParameterPair(){
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameter(Pair.of("test", "true"));
		assertEquals(ListUtil.createList(Pair.of("test", "true")), annotation.getParameters());
		annotation.addParameter(Pair.of("derp", "String.class"));
		assertEquals(ListUtil.createList(Pair.of("test", "true"), Pair.of("derp", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testAddParameters(){
		Pair<String, String> param1 = Pair.of("test", "true");
		Pair<String, String> param2 = Pair.of("derp", "String.class");
		Pair<String, String> param3 = Pair.of("yep", "false");
		Pair<String, String> param4 = Pair.of("nah", "\"\"");
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.addParameters(ListUtil.createList(param1, param2));
		assertEquals(ListUtil.createList(param1, param2), annotation.getParameters());
		annotation.addParameters(ListUtil.createList(param3, param4));
		assertEquals(ListUtil.createList(param1, param2, param3, param4), annotation.getParameters());
	}
	
	@Test
	public void testSetParameters(){
		Pair<String, String> param1 = Pair.of("test", "true");
		Pair<String, String> param2 = Pair.of("derp", "String.class");
		Pair<String, String> param3 = Pair.of("yep", "false");
		Pair<String, String> param4 = Pair.of("nah", "\"\"");
		assertEquals(new ArrayList<>(), annotation.getParameters());
		annotation.setParameters(ListUtil.createList(param1, param2));
		assertEquals(ListUtil.createList(param1, param2), annotation.getParameters());
		annotation.setParameters(ListUtil.createList(param3, param4));
		assertEquals(ListUtil.createList(param3, param4), annotation.getParameters());
	}
}
