package com.github.tadukoo.java.parsing.whitespacetests;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
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
	public void testParseAnnotationWhitespaceBeforeParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation(" \t@Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeName(){
		JavaAnnotation annotation = parser.parseAnnotation("@\t Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@\t Test");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterName(){
		JavaAnnotation annotation = parser.parseAnnotation("@Test \t");
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test \t");
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
	public void testParseAnnotationWhitespaceBeforeParamsParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test (type = String.class)");
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
	public void testParseAnnotationWhitespaceBeforeParamNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test( \ttype = String.class)");
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
	public void testParseAnnotationWhitespaceAfterParamNameParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type \t = String.class)");
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
	public void testParseAnnotationWhitespaceBeforeParamValueParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type =  \tString.class)");
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
	public void testParseAnnotationWhitespaceAfterParamValueParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class \t)");
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
	public void testParseAnnotationWhitespaceAfterParamsParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class) \t");
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
	public void testParseAnnotationWhitespaceBeforeCommaParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class  \t, defaultValue = \"\")");
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
	public void testParseAnnotationWhitespaceAfterCommaParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("@Test(type = String.class, \t  \t defaultValue = \"\")");
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
	
	@Test
	public void testParseAnnotationInsaneWhitespaceParseType() throws JavaParsingException{
		JavaAnnotation annotation = runParserForAnnotation("  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
				"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t");
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
}
