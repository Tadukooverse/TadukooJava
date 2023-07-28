package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaFieldParserWhitespaceTest extends BaseJavaParserTest{
	
	@Test
	public void testWhitespaceBeforeType() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeTypeParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t  \t
				  \tString type;""");
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeName() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeNameParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String \t  \t
				  \t  type;""");
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforeSemicolon() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeSemicolonParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String type \t  \t
				 \t;""");
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceAfterSemicolon() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterSemicolonParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String type;     \t
				  \t  \s""");
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testWhitespaceBeforePrivate() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforePrivateParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t
				    \t private String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterPrivateParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				private\t     \t
				    \t String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeProtectedParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t
				    \t protected String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterProtectedParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				protected\t     \t
				    \t String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforePublicParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t
				    \t public String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterPublicParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				public\t     \t
				    \t String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeStaticParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t
				    \t static String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterStaticParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				static\t     \t
				    \t String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeFinalParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t
				    \t final String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterFinalParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				final\t     \t
				    \t String type;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeEqualsParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String type    \t   \t
				   \t = "";""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceBeforeValueParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String type =    \t   \t
				   \t "";""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testWhitespaceAfterValueParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				String type = ""    \t   \t
				   \t ;""");
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
		JavaField field = JavaFieldParser.parseField("""
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
	public void testInsaneWhitespaceSimpleFieldParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
				\t     \t  \t
				  \tString \t  \t
				  \t  type \t  \t
				 \t;     \t
				  \t  \s""");
		assertEquals(
				EditableJavaField.builder()
						.type("String").name("type")
						.build(),
				field);
		assertEquals("String type;", field.toString());
	}
	
	@Test
	public void testInsaneWhitespaceEverythingField() throws JavaParsingException{
		JavaField field = JavaFieldParser.parseField("""
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
	public void testInsaneWhitespaceEverythingFieldParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
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
	public void testInsaneWhitespaceEverythingFieldWithAnnotationsParseType() throws JavaParsingException{
		JavaField field = runFullParserForField("""
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
