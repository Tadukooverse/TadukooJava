package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaAnnotationParserWhitespaceTest extends BaseJavaParserTest{
	
	@Test
	public void testParseAnnotationWhitespaceBefore(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation(" \t@Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation(" \t@Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeName(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@\t Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@\t Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterName(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test \t");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParams(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test (type = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamsParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test (type = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamName(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test( \ttype = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test( \ttype = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamName(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type \t = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type \t = String.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamValue(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type =  \tString.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamValueParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type =  \tString.class)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamValue(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class \t)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamValueParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class \t)");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParams(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class) \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamsParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class) \t");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeComma(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class  \t, defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeCommaParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class  \t, defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterComma(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("@Test(type = String.class, \t  \t defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterCommaParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("@Test(type = String.class, \t  \t defaultValue = \"\")");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationInsaneWhitespace(){
		JavaAnnotation annotation = JavaAnnotationParser.parseAnnotation("  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
				"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
				Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationInsaneWhitespaceParseType() throws JavaParsingException{
		JavaAnnotation annotation = runFullParserForAnnotation("  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
				"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
}
