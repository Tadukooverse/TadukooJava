package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertMethodEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findMethodDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestMethodTest{
	
	@Test
	public void testFindMethodDifferencesNone(){
		assertEquals(new ArrayList<>(), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findMethodDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindMethodDifferencesMethod1NullMethod2Not(){
		assertEquals(ListUtil.createList("One of the methods is null, and the other isn't!"), findMethodDifferences(
				null,
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesMethod2NullMethod1Not(){
		assertEquals(ListUtil.createList("One of the methods is null, and the other isn't!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindMethodDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findMethodDifferences(
				UneditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesJavadoc(){
		assertEquals(ListUtil.createList("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!"""), findMethodDifferences(
				EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.build())
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesAnnotationsLength(){
		assertEquals(ListUtil.createList("Annotations length is different!"), findMethodDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesAnnotationsLength1(){
		assertEquals(ListUtil.createList("Annotations length is different!",
				"Annotations differs on #2!"), findMethodDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesAnnotationsContent(){
		assertEquals(ListUtil.createList("""
				Annotations differs on #1:
					Name is different!"""), findMethodDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesVisibility(){
		assertEquals(ListUtil.createList("Visibility is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesAbstract(){
		assertEquals(ListUtil.createList("Abstract is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.isAbstract()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesStatic(){
		assertEquals(ListUtil.createList("Static is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesFinal(){
		assertEquals(ListUtil.createList("Final is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesReturnType(){
		assertEquals(ListUtil.createList("Return Type is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("int").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesName(){
		assertEquals(ListUtil.createList("Name is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesParamsLength(){
		assertEquals(ListUtil.createList("Parameters length is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.parameter("String", "version")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.parameter("String", "version")
						.parameter("int", "version")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesParamsLength1Longer(){
		assertEquals(ListUtil.createList("Parameters length is different!",
				"Parameters differs on #2!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.parameter("String", "version")
						.parameter("int", "version")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.parameter("String", "version")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesThrowsTypes(){
		assertEquals(ListUtil.createList("Throw Types differs on #1!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("Exception")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("NoException")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesThrowsTypesLength(){
		assertEquals(ListUtil.createList("Throw Types length is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("Exception")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("Exception")
						.throwType("NoException")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesThrowsTypesLength1Longer(){
		assertEquals(ListUtil.createList("Throw Types length is different!",
				"Throw Types differs on #2!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("Exception")
						.throwType("NoException")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.throwType("Exception")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesContent(){
		assertEquals(ListUtil.createList("Content differs on #1!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return version;")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return this;")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesContentLength(){
		assertEquals(ListUtil.createList("Content length is different!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return version;")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return version;")
						.line("return this;")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesContentLength1Longer(){
		assertEquals(ListUtil.createList("Content length is different!",
				"Content differs on #2!"), findMethodDifferences(
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return version;")
						.line("return this;")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.line("return version;")
						.build()
		));
	}
	
	@Test
	public void testFindMethodDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!", """
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
				"Throw Types differs on #2!"), findMethodDifferences(
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
						.parameter("String", "version")
						.parameter("int", "version")
						.throwType("Exception")
						.throwType("NoException")
						.build(),
				UneditableJavaMethod.builder()
						.annotation(UneditableJavaAnnotation.builder()
								.name("getDerp")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("String").name("getTest")
						.parameter("String", "version")
						.throwType("Exception")
						.build()
		));
	}
	
	@Test
	public void testAssertMethodEqualsNone(){
		assertMethodEquals(
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build(),
				EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build()
		);
	}
	
	@Test
	public void testAssertMethodEqualsBothNull(){
		assertMethodEquals(
				null, null
		);
	}
	
	@Test
	public void testAssertMethodEqualsMethod1NullMethod2Not(){
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(null, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the methods is null, and the other isn't!",
					buildAssertError(null, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsMethod2NullMethod1Not(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the methods is null, and the other isn't!",
					buildAssertError(method1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsEditable(){
		JavaMethod method1 = UneditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsJavadoc(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.javadoc(EditableJavadoc.builder()
						.build())
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsAnnotationsLength(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Annotations length is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsAnnotationsLength1(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Annotations length is different!
					Annotations differs on #2!""",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsAnnotationsContent(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Annotations differs on #1:
					Name is different!""",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsVisibility(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.visibility(Visibility.PUBLIC)
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.visibility(Visibility.PRIVATE)
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Visibility is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsAbstract(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.isAbstract()
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Abstract is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsStatic(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.isStatic()
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Static is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsFinal(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.isFinal()
				.returnType("String").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Final is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsReturnType(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("int").name("getTest")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Return Type is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsName(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getTest")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Name is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsParamsLength(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.parameter("String", "version")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.parameter("String", "version")
				.parameter("int", "version")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Parameters length is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsParamsLength1Longer(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.parameter("String", "version")
				.parameter("int", "version")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.parameter("String", "version")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Parameters length is different!
					Parameters differs on #2!""",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsThrowsTypes(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("Exception")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("NoException")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Throw Types differs on #1!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsThrowsTypesLength(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("Exception")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("Exception")
				.throwType("NoException")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Throw Types length is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsThrowsTypesLength1Longer(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("Exception")
				.throwType("NoException")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.throwType("Exception")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Throw Types length is different!\n" +
							"Throw Types differs on #2!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsContent(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return version;")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return this;")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content differs on #1!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsContentLength(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return version;")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return version;")
				.line("return this;")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content length is different!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsContentLength1Longer(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return version;")
				.line("return this;")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("getVersion")
				.line("return version;")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content length is different!\n" +
							"Content differs on #2!",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertMethodEqualsAll(){
		JavaMethod method1 = EditableJavaMethod.builder()
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
				.parameter("String", "version")
				.parameter("int", "version")
				.throwType("Exception")
				.throwType("NoException")
				.build();
		JavaMethod method2 = UneditableJavaMethod.builder()
				.annotation(UneditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.visibility(Visibility.PRIVATE)
				.returnType("String").name("getTest")
				.parameter("String", "version")
				.throwType("Exception")
				.build();
		try{
			assertMethodEquals(method1, method2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Editable is different!
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!
				Annotations length is different!
				Annotations differs on #1:
					Editable is different!
					Name is different!
				Annotations differs on #2!
				Visibility is different!
				Abstract is different!
				Return Type is different!
				Name is different!
				Parameters length is different!
				Parameters differs on #2!
				Throw Types length is different!
				Throw Types differs on #2!""",
					buildAssertError(method1, method2)), e.getMessage());
		}
	}
}
