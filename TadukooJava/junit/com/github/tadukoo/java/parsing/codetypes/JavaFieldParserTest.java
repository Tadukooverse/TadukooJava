package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaFieldParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleField() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("String name;");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("String name;");
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
		JavaField field = JavaFieldParser.parseField("private String name;");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("private String name;");
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
		JavaField field = runFullParserForField("protected String name;");
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
		JavaField field = runFullParserForField("public String name;");
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
		JavaField field = JavaFieldParser.parseField("static String name;");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("static String name;");
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
		JavaField field = JavaFieldParser.parseField("final String name;");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("final String name;");
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
		JavaField field = JavaFieldParser.parseField("String name = \"\";");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("String name = \"\";");
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
		JavaField field = runFullParserForField("""
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
		JavaField field = runFullParserForField("""
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
		JavaField field = JavaFieldParser.parseField("private static final String name = \"\";");
		assertNotNull(field);
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
		JavaField field = runFullParserForField("""
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
			JavaFieldParser.parseField("String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithVisibilityNoSemicolon(){
		try{
			JavaFieldParser.parseField("private String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithStaticNoSemicolon(){
		try{
			JavaFieldParser.parseField("static String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithFinalNoSemicolon(){
		try{
			JavaFieldParser.parseField("final String name");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithValueNoSemicolon(){
		try{
			JavaFieldParser.parseField("String name = \"\"");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFieldWithEverythingNoSemicolon(){
		try{
			JavaFieldParser.parseField("private static final String name = \"\"");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
}
