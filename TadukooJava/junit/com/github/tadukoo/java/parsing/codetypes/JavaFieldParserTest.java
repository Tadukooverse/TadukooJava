package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaFieldParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaField, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForField,
					JavaFieldParser::parseField
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaField, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"String name;",
						EditableJavaField.builder()
								.type("String").name("name")
								.build(),
						"String name;"
				),
				// With Private Visibility
				Triple.of(
						"private String name;",
						EditableJavaField.builder()
								.visibility(Visibility.PRIVATE)
								.type("String").name("name")
								.build(),
						"private String name;"
				),
				// With Protected Visibility
				Triple.of(
						"protected String name;",
						EditableJavaField.builder()
								.visibility(Visibility.PROTECTED)
								.type("String").name("name")
								.build(),
						"protected String name;"
				),
				// With Public Visibility
				Triple.of(
						"public String name;",
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.type("String").name("name")
								.build(),
						"public String name;"
				),
				// With Static
				Triple.of(
						"static String name;",
						EditableJavaField.builder()
								.isStatic()
								.type("String").name("name")
								.build(),
						"static String name;"
				),
				// With Final
				Triple.of(
						"final String name;",
						EditableJavaField.builder()
								.isFinal()
								.type("String").name("name")
								.build(),
						"final String name;"
				),
				// With Modifiers Reversed
				Triple.of(
						"final static public String name;",
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("name")
								.build(),
						"public static final String name;"
				),
				// With Value
				Triple.of(
						"String name = \"\";",
						EditableJavaField.builder()
								.type("String").name("name")
								.value("\"\"")
								.build(),
						"String name = \"\";"
				),
				// With Annotation
				Triple.of(
						"""
								@Test
								String name;""",
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.type("String").name("name")
								.build(),
						"""
								@Test
								String name;"""
				),
				// With Multiple Annotations
				Triple.of(
						"""
								@Test
								@Derp(type=String.class)
								String name;""",
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
						"""
								@Test
								@Derp(type = String.class)
								String name;"""
				),
				// With Javadoc
				Triple.of(
						"""
								/** {@inheritDoc} */
								String name;""",
						EditableJavaField.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("{@inheritDoc}")
										.build())
								.type("String").name("name")
								.build(),
						"""
								/** {@inheritDoc} */
								String name;"""
				),
				// With Everything
				Triple.of(
						"""
								/** {@inheritDoc} */
								@Test
								@Derp(type=String.class)
								private static final String name = "";""",
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
						"""
								/** {@inheritDoc} */
								@Test
								@Derp(type = String.class)
								private static final String name = "";"""
				),
				// Whitespace before Type
				Triple.of(
						"""
								\t     \t  \t
								  \tString type;""",
						EditableJavaField.builder()
								.type("String").name("type")
								.build(),
						"String type;"
				),
				// Whitespace before Name
				Triple.of(
						"""
								String \t  \t
								  \t  type;""",
						EditableJavaField.builder()
								.type("String").name("type")
								.build(),
						"String type;"
				),
				// Whitespace before Semicolon
				Triple.of(
						"""
								String type \t  \t
								 \t;""",
						EditableJavaField.builder()
								.type("String").name("type")
								.build(),
						"String type;"
				),
				// Whitespace after Semicolon
				Triple.of(
						"""
								String type;     \t
								  \t  \s""",
						EditableJavaField.builder()
								.type("String").name("type")
								.build(),
						"String type;"
				),
				// Whitespace before Private
				Triple.of(
						"""
								\t     \t
								    \t private String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PRIVATE)
								.type("String").name("type")
								.build(),
						"private String type;"
				),
				// Whitespace after Private
				Triple.of(
						"""
								private\t     \t
								    \t String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PRIVATE)
								.type("String").name("type")
								.build(),
						"private String type;"
				),
				// Whitespace before Protected
				Triple.of(
						"""
								\t     \t
								    \t protected String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PROTECTED)
								.type("String").name("type")
								.build(),
						"protected String type;"
				),
				// Whitespace after Protected
				Triple.of(
						"""
								protected\t     \t
								    \t String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PROTECTED)
								.type("String").name("type")
								.build(),
						"protected String type;"
				),
				// Whitespace before Public
				Triple.of(
						"""
								\t     \t
								    \t public String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.type("String").name("type")
								.build(),
						"public String type;"
				),
				// Whitespace after Public
				Triple.of(
						"""
								public\t     \t
								    \t String type;""",
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.type("String").name("type")
								.build(),
						"public String type;"
				),
				// Whitespace before Static
				Triple.of(
						"""
								\t     \t
								    \t static String type;""",
						EditableJavaField.builder()
								.isStatic()
								.type("String").name("type")
								.build(),
						"static String type;"
				),
				// Whitespace after Static
				Triple.of(
						"""
								static\t     \t
								    \t String type;""",
						EditableJavaField.builder()
								.isStatic()
								.type("String").name("type")
								.build(),
						"static String type;"
				),
				// Whitespace before Final
				Triple.of(
						"""
								\t     \t
								    \t final String type;""",
						EditableJavaField.builder()
								.isFinal()
								.type("String").name("type")
								.build(),
						"final String type;"
				),
				// Whitespace after Final
				Triple.of(
						"""
								final\t     \t
								    \t String type;""",
						EditableJavaField.builder()
								.isFinal()
								.type("String").name("type")
								.build(),
						"final String type;"
				),
				// Whitespace before Equals
				Triple.of(
						"""
								String type    \t   \t
								   \t = "";""",
						EditableJavaField.builder()
								.type("String").name("type")
								.value("\"\"")
								.build(),
						"String type = \"\";"
				),
				// Whitespace before Value
				Triple.of(
						"""
								String type =    \t   \t
								   \t "";""",
						EditableJavaField.builder()
								.type("String").name("type")
								.value("\"\"")
								.build(),
						"String type = \"\";"
				),
				// Whitespace after Value
				Triple.of(
						"""
								String type = ""    \t   \t
								   \t ;""",
						EditableJavaField.builder()
								.type("String").name("type")
								.value("\"\"")
								.build(),
						"String type = \"\";"
				),
				// Whitespace in value
				Triple.of(
						"""
								/** An array containing 0-9 and then A-F, used for converting to hex */\r
								\t\t\t\t\tpublic static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',\r
								\t\t\t\t\t\t\t\t\t\t\t\t\t'A', 'B', 'C', 'D', 'E', 'F'};""",
						EditableJavaField.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("An array containing 0-9 and then A-F, used for converting to hex")
										.build())
								.visibility(Visibility.PUBLIC).isStatic().isFinal()
								.type("char[]").name("hexChars")
								.value("new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',\n" +
										"\t\t\t\t\t\t\t\t\t\t\t\t'A', 'B', 'C', 'D', 'E', 'F'}")
								.build(),
						"""
								/** An array containing 0-9 and then A-F, used for converting to hex */
								public static final char[] hexChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
								\t\t\t\t\t\t\t\t\t\t\t\t'A', 'B', 'C', 'D', 'E', 'F'};"""
				),
				// Insane Whitespace Simple Field
				Triple.of(
						"""
								\t     \t  \t
								  \tString \t  \t
								  \t  type \t  \t
								 \t;     \t
								  \t  \s""",
						EditableJavaField.builder()
								.type("String").name("type")
								.build(),
						"String type;"
				),
				// Insane Whitespace Everything Field
				Triple.of(
						"""
								\t     \t
								    \t public\t     \t
								    \t static\t     \t
								    \t final\t     \t  \t
								  \tString \t  \t
								  \t  type    \t   \t
								   \t =    \t   \t
								   \t "" \t  \t
								 \t;     \t
								  \t  \s""",
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.type("String").name("type")
								.value("\"\"")
								.build(),
						"public static final String type = \"\";"
				),
				// Insane Whitespace Everything Field with Annotations
				Triple.of(
						"""
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
								 \t  \s""",
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
						"""
								@Test(type = String.class, defaultValue = "")
								@Derp
								public static final String type = "";"""
				)
		);
		
		return parsingData.stream()
				.flatMap(triple -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), triple.getLeft(), triple.getMiddle(),
								triple.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getParsingData")
	public void testParsing(
			ThrowingFunction<String, JavaField, JavaParsingException> parseMethod, String textToParse,
			JavaField expectedField, String expectedText) throws JavaParsingException{
		JavaField field = parseMethod.apply(textToParse);
		assertNotNull(field);
		assertEquals(expectedField, field);
		assertEquals(expectedText, field.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// With abstract
				Pair.of(
						"abstract String name;",
						"'abstract' is not a valid modifier on field!"
				),
				// No Semicolon
				Pair.of(
						"String name = \"\"",
						"Failed to find semicolon at end of field"
				),
				// Everything No Semicolon
				Pair.of(
						"private static final String name = \"\"",
						"Failed to find semicolon at end of field"
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaField, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testNoFieldParsed(){
		try{
			JavaFieldParser.parseField("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to parse an actual field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleJavadocs(){
		try{
			JavaFieldParser.parseField("""
					/** {@inheritDoc} */
					/** something */
					String name;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Only one Javadoc allowed on a field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterField(){
		try{
			JavaFieldParser.parseField("""
					String name;
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered Javadoc after field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterField(){
		try{
			JavaFieldParser.parseField("""
					String name;
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered annotation after field!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleFields(){
		try{
			JavaFieldParser.parseField("""
					String name;
					int version;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Encountered multiple fields!"),
					e.getMessage());
		}
	}
	
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
	public void testGarbage(){
		try{
			JavaFieldParser.parseField("derp bloop scoop loop;");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.FIELD,
							"Failed to parse a field"),
					e.getMessage());
		}
	}
}
