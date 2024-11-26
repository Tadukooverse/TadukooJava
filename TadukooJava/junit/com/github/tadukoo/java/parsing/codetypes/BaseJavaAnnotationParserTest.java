package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class BaseJavaAnnotationParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaAnnotation, JavaParsingException> parseMethod;
	
	protected BaseJavaAnnotationParserTest(
			ThrowingFunction<String, JavaAnnotation, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Standard Tests
	 */
	
	@Test
	public void testSimpleAnnotation() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(new ArrayList<>(), annotation.getParameters());
		assertEquals("@Test", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithValueParameter() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(true)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("value", "true")), annotation.getParameters());
		assertEquals("@Test(true)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithParameter() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")), annotation.getParameters());
		assertEquals("@Test(type = String.class)", annotation.toString());
	}
	
	@Test
	public void testAnnotationWithMultipleParameters() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class, defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
		assertEquals("@Test(type = String.class, defaultValue = \"\")", annotation.toString());
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testParseAnnotationWhitespaceBefore() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply(" \t@Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeName() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@\t Test");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterName() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParams() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test (type = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamName() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test( \ttype = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamName() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type \t = String.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeParamValue() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type =  \tString.class)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParamValue() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class \t)");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterParams() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class) \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(Pair.of("type", "String.class")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceBeforeComma() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class  \t, defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationWhitespaceAfterComma() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("@Test(type = String.class, \t  \t defaultValue = \"\")");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
	
	@Test
	public void testParseAnnotationInsaneWhitespace() throws JavaParsingException{
		JavaAnnotation annotation = parseMethod.apply("  \t  \t @ \t  \tTest\t  (\t \ttype \t = " +
				"\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t\"\"  \t )    \t");
		assertNotNull(annotation);
		assertEquals("Test", annotation.getName());
		assertEquals(ListUtil.createList(
						Pair.of("type", "String.class"), Pair.of("defaultValue", "\"\"")),
				annotation.getParameters());
	}
}
