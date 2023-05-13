package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.Visibility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaParserFieldTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleField() throws JavaParsingException{
		JavaField field = parser.parseField("String name;");
		assertEquals(
				EditableJavaField.builder()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("String name;", field.toString());
	}
	
	@Test
	public void testSimpleFieldParseType() throws JavaParsingException{
		JavaField field = runParserForField("String name;");
		assertEquals(
				EditableJavaField.builder()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("String name;", field.toString());
	}
	
	@Test
	public void testFieldWithVisibility() throws JavaParsingException{
		JavaField field = parser.parseField("private String name;");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("private String name;", field.toString());
	}
	
	@Test
	public void testFieldWithVisibilityParseType() throws JavaParsingException{
		JavaField field = runParserForField("private String name;");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("private String name;", field.toString());
	}
	
	@Test
	public void testFieldWithProtectedVisibilityParseType() throws JavaParsingException{
		JavaField field = runParserForField("protected String name;");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PROTECTED)
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("protected String name;", field.toString());
	}
	
	@Test
	public void testFieldWithPublicVisibilityParseType() throws JavaParsingException{
		JavaField field = runParserForField("public String name;");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("public String name;", field.toString());
	}
	
	@Test
	public void testFieldWithStatic() throws JavaParsingException{
		JavaField field = parser.parseField("static String name;");
		assertEquals(
				EditableJavaField.builder()
						.isStatic()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("static String name;", field.toString());
	}
	
	@Test
	public void testFieldWithStaticParseType() throws JavaParsingException{
		JavaField field = runParserForField("static String name;");
		assertEquals(
				EditableJavaField.builder()
						.isStatic()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("static String name;", field.toString());
	}
	
	@Test
	public void testFieldWithFinal() throws JavaParsingException{
		JavaField field = parser.parseField("final String name;");
		assertEquals(
				EditableJavaField.builder()
						.isFinal()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("final String name;", field.toString());
	}
	
	@Test
	public void testFieldWithFinalParseType() throws JavaParsingException{
		JavaField field = runParserForField("final String name;");
		assertEquals(
				EditableJavaField.builder()
						.isFinal()
						.type("String")
						.name("name")
						.build(),
				field);
		assertEquals("final String name;", field.toString());
	}
	
	@Test
	public void testFieldWithValue() throws JavaParsingException{
		JavaField field = parser.parseField("String name = \"\";");
		assertEquals(
				EditableJavaField.builder()
						.type("String")
						.name("name")
						.value("\"\"")
						.build(),
				field);
		assertEquals("String name = \"\";", field.toString());
	}
	
	@Test
	public void testFieldWithValueParseType() throws JavaParsingException{
		JavaField field = runParserForField("String name = \"\";");
		assertEquals(
				EditableJavaField.builder()
						.type("String")
						.name("name")
						.value("\"\"")
						.build(),
				field);
		assertEquals("String name = \"\";", field.toString());
	}
	
	@Test
	public void testFieldWithAnnotationParseType() throws JavaParsingException{
		JavaField field = runParserForField("""
				@Test
				String name;""");
		assertEquals(
				EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.type("String").name("name")
						.build(),
				field);
		assertEquals("""
				@Test
				String name;""", field.toString());
	}
	
	@Test
	public void testFieldWithMultipleAnnotationsParseType() throws JavaParsingException{
		JavaField field = runParserForField("""
				@Test
				@Derp(type=String.class)
				String name;""");
		assertEquals(
				EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.parameter("type", "String.class")
								.build())
						.type("String").name("name")
						.build(),
				field);
		assertEquals("""
				@Test
				@Derp(type = String.class)
				String name;""", field.toString());
	}
	
	@Test
	public void testFieldWithEverything() throws JavaParsingException{
		JavaField field = parser.parseField("private static final String name = \"\";");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.type("String")
						.name("name")
						.value("\"\"")
						.build(),
				field);
		assertEquals("private static final String name = \"\";", field.toString());
	}
	
	@Test
	public void testFieldWithEverythingParseType() throws JavaParsingException{
		JavaField field = runParserForField("""
				@Test
				@Derp(type=String.class)
				private static final String name = "";""");
		assertEquals(
				EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.parameter("type", "String.class")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.type("String")
						.name("name")
						.value("\"\"")
						.build(),
				field);
		assertEquals("""
				@Test
				@Derp(type = String.class)
				private static final String name = "";""", field.toString());
	}
	
	/*
	 * No Semicolon parseField Tests
	 */
	
	@Test
	public void testSimpleFieldNoSemicolon(){
		try{
			parser.parseField("String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithVisibilityNoSemicolon(){
		try{
			parser.parseField("private String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithStaticNoSemicolon(){
		try{
			parser.parseField("static String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithFinalNoSemicolon(){
		try{
			parser.parseField("final String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithValueNoSemicolon(){
		try{
			parser.parseField("String name = \"\"");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithEverythingNoSemicolon(){
		try{
			parser.parseField("private static final String name = \"\"");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
}
