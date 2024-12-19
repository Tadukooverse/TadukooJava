package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
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

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertMethodEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findMethodDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestMethodTest{
	
	@ParameterizedTest
	@MethodSource("getMethodDifferences")
	public void testFindMethodDifferences(JavaMethod expectedMethod, JavaMethod actualMethod, List<String> differences){
		assertEquals(differences, findMethodDifferences(expectedMethod, actualMethod));
	}
	
	@ParameterizedTest
	@MethodSource("getMethodDifferences")
	public void testAssertMethodEquals(JavaMethod expectedMethod, JavaMethod actualMethod, List<String> differences){
		try{
			assertMethodEquals(expectedMethod, actualMethod);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedMethod, actualMethod)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getMethodDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						new ArrayList<>()
				),
				// Both null
				Arguments.of(
						null,
						null,
						new ArrayList<>()
				),
				// Method 1 null, Method 2 not
				Arguments.of(
						null,
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("One of the methods is null, and the other isn't!")
				),
				// Method 2 null, Method 1 not
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						null,
						ListUtil.createList("One of the methods is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						UneditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Javadoc
				Arguments.of(
						EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("""
								Javadoc differs:
									One of the Javadocs is null, and the other isn't!""")
				),
				// Annotations length
				Arguments.of(
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Annotations length is different!")
				),
				// Annotations Length 1
				Arguments.of(
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Annotations length is different!",
								"Annotations differs on #2!")
				),
				// Annotations Content
				Arguments.of(
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("""
								Annotations differs on #1:
									Name is different!""")
				),
				// Visibility
				Arguments.of(
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Visibility is different!")
				),
				// Abstract
				Arguments.of(
						EditableJavaMethod.builder()
								.isAbstract()
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Abstract is different!")
				),
				// Static
				Arguments.of(
						EditableJavaMethod.builder()
								.isStatic()
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Static is different!")
				),
				// Final
				Arguments.of(
						EditableJavaMethod.builder()
								.isFinal()
								.returnType("String").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Final is different!")
				),
				// Return Type
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("int").name("getTest")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Return Type is different!")
				),
				// Name
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build(),
						ListUtil.createList("Name is different!")
				),
				// Params Length
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.parameter("String version")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.parameter("String version")
								.parameter("int version")
								.build(),
						ListUtil.createList("Parameters length is different!")
				),
				// Params Length 1 Longer
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.parameter("String version")
								.parameter("int version")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.parameter("String version")
								.build(),
						ListUtil.createList("Parameters length is different!",
								"Parameters differs on #2!")
				),
				// Throws Types
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("Exception")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("NoException")
								.build(),
						ListUtil.createList("Throw Types differs on #1!")
				),
				// Throws Types Length
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("Exception")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("Exception")
								.throwType("NoException")
								.build(),
						ListUtil.createList("Throw Types length is different!")
				),
				// Throws Types Length 1 Longer
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("Exception")
								.throwType("NoException")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.throwType("Exception")
								.build(),
						ListUtil.createList("Throw Types length is different!",
								"Throw Types differs on #2!")
				),
				// Content
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return version;")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return this;")
								.build(),
						ListUtil.createList("Content differs on #1!")
				),
				// Content Length
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return version;")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return version;")
								.line("return this;")
								.build(),
						ListUtil.createList("Content length is different!")
				),
				// Content Length 1 Longer
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return version;")
								.line("return this;")
								.build(),
						EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.line("return version;")
								.build(),
						ListUtil.createList("Content length is different!",
								"Content differs on #2!")
				),
				// All
				Arguments.of(
						EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getTest")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.visibility(Visibility.PUBLIC)
								.isAbstract()
								.returnType("int").name("getVersion")
								.parameter("String version")
								.parameter("int version")
								.throwType("Exception")
								.throwType("NoException")
								.build(),
						UneditableJavaMethod.builder()
								.annotation(UneditableJavaAnnotation.builder()
										.name("getDerp")
										.build())
								.visibility(Visibility.PRIVATE)
								.returnType("String").name("getTest")
								.parameter("String version")
								.throwType("Exception")
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
								"Abstract is different!",
								"Return Type is different!",
								"Name is different!",
								"Parameters length is different!",
								"Parameters differs on #2!",
								"Throw Types length is different!",
								"Throw Types differs on #2!")
				)
		);
	}
}
