package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.Visibility;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaParserFieldTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleField(){
		JavaField field = parser.parseField("String name;");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("String name", field.toString());
	}
	
	@Test
	public void testSimpleFieldNoSemicolon(){
		JavaField field = parser.parseField("String name;");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("String name", field.toString());
	}
	
	@Test
	public void testFieldWithVisibility(){
		JavaField field = parser.parseField("private String name;");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.PRIVATE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("private String name", field.toString());
	}
	
	@Test
	public void testFieldWithVisibilityNoSemicolon(){
		JavaField field = parser.parseField("private String name");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.PRIVATE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("private String name", field.toString());
	}
	
	@Test
	public void testFieldWithStatic(){
		JavaField field = parser.parseField("static String name;");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertTrue(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("static String name", field.toString());
	}
	
	@Test
	public void testFieldWithStaticNoSemicolon(){
		JavaField field = parser.parseField("static String name");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertTrue(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("static String name", field.toString());
	}
	
	@Test
	public void testFieldWithFinal(){
		JavaField field = parser.parseField("final String name;");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertTrue(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("final String name", field.toString());
	}
	
	@Test
	public void testFieldWithFinalNoSemicolon(){
		JavaField field = parser.parseField("final String name");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertTrue(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertNull(field.getValue());
		assertEquals("final String name", field.toString());
	}
	
	@Test
	public void testFieldWithValue(){
		JavaField field = parser.parseField("String name = \"\";");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertEquals("\"\"", field.getValue());
		assertEquals("String name = \"\"", field.toString());
	}
	
	@Test
	public void testFieldWithValueNoSemicolon(){
		JavaField field = parser.parseField("String name = \"\"");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.NONE, field.getVisibility());
		assertFalse(field.isStatic());
		assertFalse(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertEquals("\"\"", field.getValue());
		assertEquals("String name = \"\"", field.toString());
	}
	
	@Test
	public void testFieldWithEverything(){
		JavaField field = parser.parseField("private static final String name = \"\";");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.PRIVATE, field.getVisibility());
		assertTrue(field.isStatic());
		assertTrue(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertEquals("\"\"", field.getValue());
		assertEquals("private static final String name = \"\"", field.toString());
	}
	
	@Test
	public void testFieldWithEverythingNoSemicolon(){
		JavaField field = parser.parseField("private static final String name = \"\"");
		assertNull(field.getJavadoc());
		assertEquals(new ArrayList<>(), field.getAnnotations());
		assertEquals(Visibility.PRIVATE, field.getVisibility());
		assertTrue(field.isStatic());
		assertTrue(field.isFinal());
		assertEquals("String", field.getType());
		assertEquals("name", field.getName());
		assertEquals("\"\"", field.getValue());
		assertEquals("private static final String name = \"\"", field.toString());
	}
}
