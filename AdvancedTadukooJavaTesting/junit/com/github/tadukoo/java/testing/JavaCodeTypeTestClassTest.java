package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertClassEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findClassDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestClassTest{
	
	@ParameterizedTest
	@MethodSource("getClassDifferences")
	public void testFindClassDifferences(
			JavaClass expectedClass, JavaClass actualClass,
			List<String> differences){
		assertEquals(differences, findClassDifferences(expectedClass, actualClass));
	}
	
	@ParameterizedTest
	@MethodSource("getClassDifferences")
	public void testAssertClassEquals(
			JavaClass expectedClass, JavaClass actualClass,
			List<String> differences){
		try{
			assertClassEquals(expectedClass, actualClass);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedClass, actualClass)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getClassDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
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
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("One of the classes is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						null,
						ListUtil.createList("One of the classes is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Inner Class
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.innerClass()
								.className("Test")
								.build(),
						ListUtil.createList("Inner Class is different!")
				),
				// Package Declaration
				Arguments.of(
						EditableJavaClass.builder()
								.packageName("com.test")
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						ListUtil.createList("Package Declaration differs:\n" +
								"\tPackage Name is different!")
				),
				// Import Statements Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.importName("com.example.Thing", false)
								.className("Test")
								.build(),
						ListUtil.createList("Import Statements length is different!")
				),
				// Import Statements Length 1
				Arguments.of(
						EditableJavaClass.builder()
								.importName("com.example.Thing", false)
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Import Statements length is different!",
								"Import Statements differs on #1!")
				),
				// Import Statements Content
				Arguments.of(
						EditableJavaClass.builder()
								.importName("com.example.Thing", false)
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.importName("com.example.Thing", true)
								.className("Test")
								.build(),
						ListUtil.createList("Import Statements differs on #1:\n" +
								"\tStatic is different!")
				),
				// Javadoc
				Arguments.of(
						EditableJavaClass.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""")
				),
				// Annotations Length
				Arguments.of(
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.className("Test")
								.build(),
						ListUtil.createList("Annotations length is different!")
				),
				// Annotations Length 1
				Arguments.of(
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.className("Test")
								.build(),
						ListUtil.createList("Annotations length is different!",
								"Annotations differs on #2!")
				),
				// Annotations Content
				Arguments.of(
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.className("Test")
								.build(),
						ListUtil.createList("""
				Annotations differs on #1:
					Name is different!""")
				),
				// Visibility
				Arguments.of(
						EditableJavaClass.builder()
								.visibility(Visibility.PUBLIC)
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.visibility(Visibility.PRIVATE)
								.className("Test")
								.build(),
						ListUtil.createList("Visibility is different!")
				),
				// Abstract
				Arguments.of(
						EditableJavaClass.builder()
								.isAbstract()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Abstract is different!")
				),
				// Static
				Arguments.of(
						EditableJavaClass.builder()
								.innerClass()
								.isStatic()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.innerClass()
								.className("Test")
								.build(),
						ListUtil.createList("Static is different!")
				),
				// Final
				Arguments.of(
						EditableJavaClass.builder()
								.isFinal()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Final is different!")
				),
				// Class Name
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Derp")
								.build(),
						ListUtil.createList("Class Name differs:\n\tBase Type is different!")
				),
				// Super Class Name
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test").superClassName("Something")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Super Class Name differs:\n\tOne of the types is null, and the other isn't!")
				),
				// Implements Interface Names
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("Something")
								.build(),
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("SomethingElse")
								.build(),
						ListUtil.createList("Implements Interface Names differs on #1:\n\tBase Type is different!")
				),
				// Implements Interface Names Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("Something")
								.build(),
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
								.build(),
						ListUtil.createList("Implements Interface Names length is different!")
				),
				// Implements Interface Names Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
								.build(),
						EditableJavaClass.builder()
								.className("Test").implementsInterfaceName("Something")
								.build(),
						ListUtil.createList("Implements Interface Names length is different!",
								"Implements Interface Names differs on #2!")
				),
				// Single Line Comments
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("Something useful")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						ListUtil.createList("Single Line Comments differs on #1:\n" +
								"\tContent is different!")
				),
				// Single Line Comments Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						ListUtil.createList("Single Line Comments length is different!",
								"Inner Elements Order length is different!")
				),
				// Single Line Comments Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Single Line Comments length is different!",
								"Single Line Comments differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!")
				),
				// Multi Line Comments
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("Something useful")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						ListUtil.createList("Multi Line Comments differs on #1:\n" +
								"\tContent differs on #1!")
				),
				// Multi Line Comments Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						ListUtil.createList("Multi Line Comments length is different!",
								"Inner Elements Order length is different!")
				),
				// Multi Line Comments Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("Something different")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Multi Line Comments length is different!",
								"Multi Line Comments differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!")
				),
				// Inner Classes
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Something")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("SomethingElse")
										.build())
								.build(),
						ListUtil.createList("""
										Inner Classes differs on #1:
											Class Name differs:
											Base Type is different!""",
								"Inner Elements Order differs on #1!")
				),
				// Inner Classes Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Something")
										.build())
								.build(),
						ListUtil.createList("Inner Classes length is different!",
								"Inner Elements Order length is different!")
				),
				// Inner Classes Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Something")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Inner Classes length is different!",
								"Inner Classes differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!")
				),
				// Fields
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("version")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("test")
										.build())
								.build(),
						ListUtil.createList("Fields differs on #1:\n" +
										"\tName is different!",
								"Inner Elements Order differs on #1!")
				),
				// Fields Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("test")
										.build())
								.build(),
						ListUtil.createList("Fields length is different!",
								"Inner Elements Order length is different!")
				),
				// Fields Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("test")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Fields length is different!",
								"Fields differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!")
				),
				// Methods
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("getVersion")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("getTest")
										.build())
								.build(),
						ListUtil.createList("Methods differs on #1:\n" +
										"\tName is different!",
								"Inner Elements Order differs on #1!")
				),
				// Methods Length
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("getTest")
										.build())
								.build(),
						ListUtil.createList("Methods length is different!",
								"Inner Elements Order length is different!")
				),
				// Methods Length 1 Longer
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("getTest")
										.build())
								.build(),
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						ListUtil.createList("Methods length is different!",
								"Methods differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!")
				),
				// All
				Arguments.of(
						UneditableJavaClass.builder()
								.packageName("com.test")
								.importName("com.example.Thing", false)
								.javadoc(UneditableJavadoc.builder()
										.build())
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(UneditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PUBLIC)
								.isAbstract()
								.className("Test").superClassName("Something")
								.implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
								.singleLineComment(UneditableJavaSingleLineComment.builder()
										.content("Something different")
										.build())
								.multiLineComment(UneditableJavaMultiLineComment.builder()
										.content("Something different")
										.build())
								.innerClass(UneditableJavaClass.builder()
										.innerClass()
										.className("Something")
										.build())
								.field(UneditableJavaField.builder()
										.type("String").name("test")
										.build())
								.method(UneditableJavaMethod.builder()
										.returnType("String").name("getTest")
										.build())
								.build(),
						EditableJavaClass.builder()
								.innerClass()
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.className("Derp")
								.implementsInterfaceName("Something")
								.build(),
						ListUtil.createList("Editable is different!",
								"Inner Class is different!",
								"Package Declaration differs:\n" +
										"\tOne of the package declarations is null, and the other isn't!",
								"Import Statements length is different!",
								"Import Statements differs on #1!",
								"Javadoc differs:\n" +
										"\tOne of the Javadocs is null, and the other isn't!",
								"Annotations length is different!",
								"""
									Annotations differs on #1:
										Editable is different!
										Name is different!""",
								"Annotations differs on #2!",
								"Visibility is different!",
								"Abstract is different!",
								"Static is different!",
								"Final is different!",
								"Class Name differs:\n\tBase Type is different!",
								"Super Class Name differs:\n\tOne of the types is null, and the other isn't!",
								"Implements Interface Names length is different!",
								"Implements Interface Names differs on #2!",
								"Single Line Comments length is different!",
								"Single Line Comments differs on #1!",
								"Multi Line Comments length is different!",
								"Multi Line Comments differs on #1!",
								"Inner Classes length is different!",
								"Inner Classes differs on #1!",
								"Fields length is different!",
								"Fields differs on #1!",
								"Methods length is different!",
								"Methods differs on #1!",
								"Inner Elements Order length is different!",
								"Inner Elements Order differs on #1!",
								"Inner Elements Order differs on #2!",
								"Inner Elements Order differs on #3!",
								"Inner Elements Order differs on #4!",
								"Inner Elements Order differs on #5!")
				)
		);
	}
}
