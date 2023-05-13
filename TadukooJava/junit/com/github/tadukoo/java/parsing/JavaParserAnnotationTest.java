package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaParserAnnotationTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleAnnotation(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test");
		assertEquals("Test", annotation.getName());
		assertEquals(new ArrayList<>(), annotation.getParameters());
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testSimpleAnnotationParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test");
		assertEquals("Test", annotation.getName());
		assertEquals(new ArrayList<>(), annotation.getParameters());
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithValueParameter(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(true)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("value", "true")), annotation.getParameters());
		assertEquals("@Test(value = true)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithValueParameterParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(true)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("value", "true")), annotation.getParameters());
		assertEquals("@Test(value = true)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithParameter(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")), annotation.getParameters());
		assertEquals("@Test(type = String.class)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithParameterParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")), annotation.getParameters());
		assertEquals("@Test(type = String.class)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithMultipleParameters(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class, defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
		assertEquals("@Test(type = String.class, defaultValue = \"\")", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithMultipleParametersParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class, defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
		assertEquals("@Test(type = String.class, defaultValue = \"\")", annotation.toString());
	}
	
	@Test
	public void testNotAnAnnotation(){
		assertNull(parser.parseAnnotation("Test(type = String.class)"));
	}
}
