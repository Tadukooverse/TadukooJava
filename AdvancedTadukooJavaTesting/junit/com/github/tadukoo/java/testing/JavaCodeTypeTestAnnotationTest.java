package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertAnnotationEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findAnnotationDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestAnnotationTest{
	
	@Test
	public void testFindAnnotationDifferencesNone(){
		assertEquals(new ArrayList<>(), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findAnnotationDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindAnnotationDifferencesDoc1NullDoc2Not(){
		assertEquals(ListUtil.createList("One of the annotations is null, and the other isn't!"), findAnnotationDifferences(
				null,
				EditableJavaAnnotation.builder()
						.name("Test")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesDoc2NullDoc1Not(){
		assertEquals(ListUtil.createList("One of the annotations is null, and the other isn't!"), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.build(),
				null));
	}
	
	@Test
	public void testFindAnnotationDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findAnnotationDifferences(
				UneditableJavaAnnotation.builder()
						.name("Test")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesName(){
		assertEquals(ListUtil.createList("Name is different!"), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Derp")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesCanonicalName(){
		assertEquals(ListUtil.createList("Canonical Name is different!"), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.canonicalName("com.test.Test")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.canonicalName("com.derp.Test")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesParamsLength(){
		assertEquals(ListUtil.createList("Parameters length is different!"), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("something", "We doing something with it")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("something", "We doing something with it")
						.parameter("version", "It a version")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesParamsLengthDoc1Longer(){
		assertEquals(ListUtil.createList("Parameters length is different!",
				"Parameters differs on #2!"), findAnnotationDifferences(
				EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("something", "We doing something with it")
						.parameter("version", "It a version")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.parameter("something", "We doing something with it")
						.build()));
	}
	
	@Test
	public void testFindAnnotationDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!",
				"Name is different!",
				"Canonical Name is different!",
				"Parameters length is different!",
				"Parameters differs on #2!"), findAnnotationDifferences(
				UneditableJavaAnnotation.builder()
						.name("Test")
						.canonicalName("com.test.Test")
						.parameter("something", "We doing something with it")
						.parameter("version", "It a version")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Derp")
						.canonicalName("com.derp.Test")
						.parameter("something", "We doing something with it")
						.build()));
	}
	
	@Test
	public void testAssertAnnotationEqualsNone(){
		assertAnnotationEquals(EditableJavaAnnotation.builder()
						.name("Test")
						.build(),
				EditableJavaAnnotation.builder()
						.name("Test")
						.build());
	}
	
	@Test
	public void testAssertAnnotationEqualsBothNull(){
		assertAnnotationEquals(null, null);
	}
	
	@Test
	public void testAssertAnnotationEqualsDoc1NullDoc2Not(){
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		try{
			assertAnnotationEquals(null, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the annotations is null, and the other isn't!",
					buildAssertError(null, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsDoc2NullDoc1Not(){
		JavaAnnotation annotation1 = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		try{
			assertAnnotationEquals(annotation1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the annotations is null, and the other isn't!",
					buildAssertError(annotation1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsEditable(){
		JavaAnnotation annotation1 = UneditableJavaAnnotation.builder()
				.name("Test")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsName(){
		JavaAnnotation annotation1 = EditableJavaAnnotation.builder()
				.name("Test")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Derp")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Name is different!",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsCanonicalName(){
		JavaAnnotation annotation1 = EditableJavaAnnotation.builder()
				.name("Test")
				.canonicalName("com.test.Test")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Test")
				.canonicalName("com.derp.Test")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Canonical Name is different!",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsParamsLength(){
		JavaAnnotation annotation1 = EditableJavaAnnotation.builder()
				.name("Test")
				.parameter("something", "We doing something with it")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Test")
				.parameter("something", "We doing something with it")
				.parameter("version", "It a version")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Parameters length is different!",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsParamsLengthDoc1Longer(){
		JavaAnnotation annotation1 = EditableJavaAnnotation.builder()
				.name("Test")
				.parameter("something", "We doing something with it")
				.parameter("version", "It a version")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Test")
				.parameter("something", "We doing something with it")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Parameters length is different!
					Parameters differs on #2!""",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertAnnotationEqualsAll(){
		JavaAnnotation annotation1 = UneditableJavaAnnotation.builder()
				.name("Test")
				.canonicalName("com.test.Test")
				.parameter("something", "We doing something with it")
				.parameter("version", "It a version")
				.build();
		JavaAnnotation annotation2 = EditableJavaAnnotation.builder()
				.name("Derp")
				.canonicalName("com.derp.Test")
				.parameter("something", "We doing something with it")
				.build();
		try{
			assertAnnotationEquals(annotation1, annotation2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Editable is different!
					Name is different!
					Canonical Name is different!
					Parameters length is different!
					Parameters differs on #2!""",
					buildAssertError(annotation1, annotation2)), e.getMessage());
		}
	}
}
