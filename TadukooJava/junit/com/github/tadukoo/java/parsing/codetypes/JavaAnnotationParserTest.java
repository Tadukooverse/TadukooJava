package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaAnnotationParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleAnnotation(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(new ArrayList<>(), annotation.getParameters());
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testSimpleAnnotationParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test");
		assertEquals("Test", annotation.getName());
		assertEquals(new ArrayList<>(), annotation.getParameters());
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithValueParameter(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(true)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("value", "true")), annotation.getParameters());
		assertEquals("@Test(value = true)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithValueParameterParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(true)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("value", "true")), annotation.getParameters());
		assertEquals("@Test(value = true)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithParameter(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")), annotation.getParameters());
		assertEquals("@Test(type = String.class)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithParameterParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")), annotation.getParameters());
		assertEquals("@Test(type = String.class)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithMultipleParameters(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class, defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
		assertEquals("@Test(type = String.class, defaultValue = \"\")", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithMultipleParametersParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class, defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
		assertEquals("@Test(type = String.class, defaultValue = \"\")", annotation.toString());
	}
	
	@Test
	public void testNotAnAnnotation(){
		assertNull(JavaAnnotationParser.parseAnnotation("Test(type = String.class)"));
	}
}
