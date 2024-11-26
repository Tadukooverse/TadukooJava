package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertFieldEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findFieldDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestFieldTest{
	
	@Test
	public void testFindFieldDifferencesNone(){
		assertEquals(new ArrayList<>(), findFieldDifferences(
				EditableJavaField.builder()
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findFieldDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindFieldDifferencesField1NullField2Not(){
		assertEquals(ListUtil.createList("One of the fields is null, and the other isn't!"), findFieldDifferences(
				null,
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesField2NullField1Not(){
		assertEquals(ListUtil.createList("One of the fields is null, and the other isn't!"), findFieldDifferences(
				EditableJavaField.builder()
						.type("String").name("Test")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindFieldDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findFieldDifferences(
				UneditableJavaField.builder()
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesJavadoc(){
		assertEquals(ListUtil.createList("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!"""), findFieldDifferences(
				EditableJavaField.builder()
						.javadoc(EditableJavadoc.builder()
								.build())
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesAnnotationsLength(){
		assertEquals(ListUtil.createList("Annotations length is different!"), findFieldDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesAnnotationsLength1(){
		assertEquals(ListUtil.createList("Annotations length is different!",
				"Annotations differs on #2!"), findFieldDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesAnnotationsContent(){
		assertEquals(ListUtil.createList("""
				Annotations differs on #1:
					Name is different!"""), findFieldDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesVisibility(){
		assertEquals(ListUtil.createList("Visibility is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.visibility(Visibility.PUBLIC)
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.visibility(Visibility.PRIVATE)
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesStatic(){
		assertEquals(ListUtil.createList("Static is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.isStatic()
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesFinal(){
		assertEquals(ListUtil.createList("Final is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.isFinal()
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesType(){
		assertEquals(ListUtil.createList("Type is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.type("int").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesName(){
		assertEquals(ListUtil.createList("Name is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.type("String").name("version")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesValue(){
		assertEquals(ListUtil.createList("Value is different!"), findFieldDifferences(
				EditableJavaField.builder()
						.type("String").name("Test")
						.value("\"Something\"")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		));
	}
	
	@Test
	public void testFindFieldDifferencesAll(){
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
				"Static is different!",
				"Final is different!",
				"Type is different!",
				"Name is different!",
				"Value is different!"), findFieldDifferences(
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
						.build()
		));
	}
	
	@Test
	public void testAssertFieldEqualsNone(){
		assertFieldEquals(
				EditableJavaField.builder()
						.type("String").name("Test")
						.build(),
				EditableJavaField.builder()
						.type("String").name("Test")
						.build()
		);
	}
	
	@Test
	public void testAssertFieldEqualsBothNull(){
		assertFieldEquals(
				null, null
		);
	}
	
	@Test
	public void testAssertFieldEqualsField1NullField2Not(){
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(null, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the fields is null, and the other isn't!",
					buildAssertError(null, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsField2NullField1Not(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the fields is null, and the other isn't!",
					buildAssertError(field1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsEditable(){
		JavaField field1 = UneditableJavaField.builder()
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsJavadoc(){
		JavaField field1 = EditableJavaField.builder()
				.javadoc(EditableJavadoc.builder()
						.build())
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsAnnotationsLength(){
		JavaField field1 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Annotations length is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsAnnotationsLength1(){
		JavaField field1 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Annotations length is different!
					Annotations differs on #2!""",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsAnnotationsContent(){
		JavaField field1 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Annotations differs on #1:
					Name is different!""",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsVisibility(){
		JavaField field1 = EditableJavaField.builder()
				.visibility(Visibility.PUBLIC)
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.visibility(Visibility.PRIVATE)
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Visibility is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsStatic(){
		JavaField field1 = EditableJavaField.builder()
				.isStatic()
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Static is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsFinal(){
		JavaField field1 = EditableJavaField.builder()
				.isFinal()
				.type("String").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Final is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsType(){
		JavaField field1 = EditableJavaField.builder()
				.type("int").name("Test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Type is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsName(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("version")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Name is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsValue(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("Test")
				.value("\"Something\"")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Value is different!",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertFieldEqualsAll(){
		JavaField field1 = EditableJavaField.builder()
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
				.build();
		JavaField field2 = UneditableJavaField.builder()
				.annotation(UneditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.visibility(Visibility.PRIVATE)
				.type("String").name("Test")
				.build();
		try{
			assertFieldEquals(field1, field2);
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
				Static is different!
				Final is different!
				Type is different!
				Name is different!
				Value is different!""",
					buildAssertError(field1, field2)), e.getMessage());
		}
	}
}
