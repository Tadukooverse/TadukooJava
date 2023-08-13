package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaFieldParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaField, JavaParsingException> parseMethod;
	
	protected BaseJavaFieldParserTest(ThrowingFunction<String, JavaField, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	@Test
	public void testSimpleField() throws JavaParsingException{
		JavaField field = parseMethod.apply("String name;");
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
	public void testFieldWithVisibility() throws JavaParsingException{
		JavaField field = parseMethod.apply("private String name;");
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
	public void testFieldWithProtectedVisibility() throws JavaParsingException{
		JavaField field = parseMethod.apply("protected String name;");
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
	public void testFieldWithPublicVisibility() throws JavaParsingException{
		JavaField field = parseMethod.apply("public String name;");
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
		JavaField field = parseMethod.apply("static String name;");
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
	public void testFieldWithFinal() throws JavaParsingException{
		JavaField field = parseMethod.apply("final String name;");
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
	public void testFieldWithModifiersReversed() throws JavaParsingException{
		JavaField field = parseMethod.apply("final static public String name;");
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.isStatic().isFinal()
						.type("String").name("name")
						.build(),
				field);
		assertEquals("public static final String name;", field.toString());
	}
	
	@Test
	public void testFieldWithValue() throws JavaParsingException{
		JavaField field = parseMethod.apply("String name = \"\";");
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
	public void testFieldWithAnnotation() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
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
	public void testFieldWithMultipleAnnotations() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
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
	public void testFieldWithJavadoc() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				/** {@inheritDoc} */
				String name;""");
		assertEquals(
				EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
						.type("String").name("name")
						.build(),
				field);
		assertEquals("""
				/** {@inheritDoc} */
				String name;""", field.toString());
	}
	
	@Test
	public void testFieldWithEverything() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
                /** {@inheritDoc} */
                @Test
                @Derp(type=String.class)
                private static final String name = "";""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
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
                /** {@inheritDoc} */
                @Test
                @Derp(type = String.class)
                private static final String name = "";""", field.toString());
	}
	
	/*
	 * No Semicolon Tests
	 */
	
	@Test
	public void testFieldWithValueNoSemicolon(){
		try{
			parseMethod.apply("String name = \"\"");
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
			parseMethod.apply("private static final String name = \"\"");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to find semicolon at end of field"),
					e.getMessage());
		}
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testWhitespaceBeforeType() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t  \t
				  \tString type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeName() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String \t  \t
				  \t  type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeSemicolon() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String type \t  \t
				 \t;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterSemicolon() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String type;     \t
				  \t  \s""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforePrivate() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t private String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("private String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterPrivate() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				private\t     \t
				    \t String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("private String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeProtected() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t protected String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PROTECTED)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("protected String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterProtected() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				protected\t     \t
				    \t String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PROTECTED)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("protected String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforePublic() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t public String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("public String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterPublic() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				public\t     \t
				    \t String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.type("String").name("type")
						.build(),
				field);
		assertEquals("public String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeStatic() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t static String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.isStatic()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("static String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterStatic() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				static\t     \t
				    \t String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.isStatic()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("static String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeFinal() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t final String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.isFinal()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("final String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterFinal() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				final\t     \t
				    \t String type;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.isFinal()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("final String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeEquals() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String type    \t   \t
				   \t = "";""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.value("\"\"")
						.build(),
				field);
		assertEquals("String type = \"\";", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeValue() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String type =    \t   \t
				   \t "";""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.value("\"\"")
						.build(),
				field);
		assertEquals("String type = \"\";", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterValue() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				String type = ""    \t   \t
				   \t ;""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.value("\"\"")
						.build(),
				field);
		assertEquals("String type = \"\";", field.toString());
	}
	
	@Test
	public void testInsaneWhitespaceSimpleField() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t  \t
				  \tString \t  \t
				  \t  type \t  \t
				 \t;     \t
				  \t  \s""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testInsaneWhitespaceEverythingField() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t     \t
				    \t public\t     \t
				    \t static\t     \t
				    \t final\t     \t  \t
				  \tString \t  \t
				  \t  type    \t   \t
				   \t =    \t   \t
				   \t "" \t  \t
				 \t;     \t
				  \t  \s""");
		assertNotNull(field);
		assertEquals(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.isStatic().isFinal()
						.type("String").name("type")
						.value("\"\"")
						.build(),
				field);
		assertEquals("public static final String type = \"\";", field.toString());
	}
	
	@Test
	public void testInsaneWhitespaceEverythingFieldWithAnnotations() throws JavaParsingException{
		JavaField field = parseMethod.apply("""
				\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
				\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
				  \t
				 \t    \t@       Derp   \t
				   \t public\t     \t
				   \t static\t     \t
				   \t final\t     \t  \t
				 \tString \t  \t
				 \t  type    \t   \t
				  \t =    \t   \t
				  \t "" \t  \t
				\t;     \t
				 \t  \s""");
		assertEquals(
				EditableJavaField.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.parameter("type", "String.class")
								.parameter("defaultValue", "\"\"")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic().isFinal()
						.type("String").name("type")
						.value("\"\"")
						.build(),
				field);
		assertEquals("""
				@Test(type = String.class, defaultValue = "")
				@Derp
				public static final String type = "";""", field.toString());
	}
}
