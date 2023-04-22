package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserWhitespaceAnnotationTest extends BaseJavaParserTest{
	
	@Test
	public void testParseAnnotationWhitespaceBefore(){
		JavaAnnotation annotation = parser.parseAnnotation(" \t@Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeName(){
		JavaAnnotation annotation = parser.parseAnnotation("@\t Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterName(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test \t");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParams(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test (type = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamName(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test( \ttype = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamName(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type \t = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamValue(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type =  \tString.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamValue(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class \t)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParams(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class) \t");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeComma(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class  \t, defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterComma(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test(type = String.class, \t  \t defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationInsaneWhitespace(){
		JavaAnnotation annotation = parser.parseAnnotation("  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
				"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
}
