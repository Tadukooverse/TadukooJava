package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertFieldEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findFieldDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestFieldTest{
	
	@ParameterizedTest
	@MethodSource("getFieldDifferences")
	public void testFindFieldDifferences(
			JavaField expectedField, JavaField actualField,
			List<String> differences){
		assertEquals(differences, findFieldDifferences(expectedField, actualField));
	}
	
	@ParameterizedTest
	@MethodSource("getFieldDifferences")
	public void testAssertFieldEquals(
			JavaField expectedField, JavaField actualField,
			List<String> differences){
		try{
			assertFieldEquals(expectedField, actualField);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedField, actualField)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getFieldDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						new ArrayList<>()
				),
				// Both Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null 2 Not
				Arguments.of(
						null,
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("One of the fields is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						null,
						ListUtil.createList("One of the fields is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Javadoc
				Arguments.of(
						EditableJavaField.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""")
				),
				// Annotations Length
				Arguments.of(
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Annotations length is different!")
				),
				// Annotations Length 1
				Arguments.of(
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Annotations length is different!",
								"Annotations differs on #2!")
				),
				// Annotations Content
				Arguments.of(
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.type("String").name("Test")
								.build(),
						ListUtil.createList("""
				Annotations differs on #1:
					Name is different!""")
				),
				// Visibility
				Arguments.of(
						EditableJavaField.builder()
								.visibility(Visibility.PUBLIC)
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.visibility(Visibility.PRIVATE)
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Visibility is different!")
				),
				// Static
				Arguments.of(
						EditableJavaField.builder()
								.isStatic()
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Static is different!")
				),
				// Final
				Arguments.of(
						EditableJavaField.builder()
								.isFinal()
								.type("String").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Final is different!")
				),
				// Type
				Arguments.of(
						EditableJavaField.builder()
								.type("int").name("Test")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Type differs:\n\tBase Type is different!")
				),
				// Name
				Arguments.of(
						EditableJavaField.builder()
								.type("String").name("version")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Name is different!")
				),
				// Value
				Arguments.of(
						EditableJavaField.builder()
								.type("String").name("Test")
								.value("\"Something\"")
								.build(),
						EditableJavaField.builder()
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Value is different!")
				),
				// All
				Arguments.of(
						EditableJavaField.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.isFinal()
								.type("int").name("version")
								.value("25")
								.build(),
						UneditableJavaField.builder()
								.annotation(UneditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PRIVATE)
								.type("String").name("Test")
								.build(),
						ListUtil.createList("Editable is different!", """
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""",
								"Annotations length is different!",
								"""
								Annotations differs on #1:
									Editable is different!
									Name is different!""",
								"Annotations differs on #2!",
								"Visibility is different!",
								"Static is different!",
								"Final is different!",
								"Type differs:\n\tBase Type is different!",
								"Name is different!",
								"Value is different!")
				)
		);
	}
}
