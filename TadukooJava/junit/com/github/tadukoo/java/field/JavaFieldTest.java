package com.github.tadukoo.java.field;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaFieldTest extends BaseJavaCodeTypeTest<JavaField>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(UneditableJavaField.builder()
						.type("int").name("test")
						.build(), false,
						(Function<UneditableJavaField, Boolean>) UneditableJavaField::isEditable),
				Arguments.of(EditableJavaField.builder()
						.type("int").name("test")
						.build(), true,
						(Function<EditableJavaField, Boolean>) EditableJavaField::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>>
				comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builders -> JavaCodeTypes.FIELD,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getJavaCodeType()
				),
				// Default Javadoc
				Pair.of(
						builders -> null,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						builders -> Visibility.NONE,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getVisibility()
				),
				// Default Is Static
				Pair.of(
						builders -> false,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.isStatic()
				),
				// Default Is Final
				Pair.of(
						builders -> false,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.isFinal()
				),
				// Default Value
				Pair.of(
						builders -> null,
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getValue()
				),
				// Copy
				Pair.of(
						builders -> builders.fieldBuilder().get()
								.javadoc(builders.javadocBuilder().get()
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.type("int").name("test")
								.value("42")
								.build(),
						builders -> builders.fieldBuilder().get()
								.copy(builders.fieldBuilder().get()
										.javadoc(builders.javadocBuilder().get()
												.build())
										.annotation(builders.annotationBuilder().get()
												.name("Test")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic().isFinal()
										.type("int").name("test")
										.value("42")
										.build())
								.build()
				),
				// Set Type
				Pair.of(
						builders -> JavaType.builder()
								.baseType("int")
								.build(),
						builders -> builders.fieldBuilder().get()
								.type(JavaType.builder()
										.baseType("int")
										.build()).name("test")
								.build()
								.getType()
				),
				// Set Type by Text
				Pair.of(
						builders -> JavaType.builder()
								.baseType("int")
								.build(),
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getType()
				),
				// Set Name
				Pair.of(
						builders -> "test",
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build()
								.getName()
				),
				// Set Javadoc
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.build(),
						builders -> builders.fieldBuilder().get()
								.javadoc(builders.javadocBuilder().get().build())
								.type("int").name("test")
								.build()
								.getJavadoc()
				),
				// Set Annotations
				Pair.of(
						builders -> ListUtil.createList(
								builders.annotationBuilder().get()
										.name("Test").build(),
								builders.annotationBuilder().get()
										.name("Derp").build()
						),
						builders -> builders.fieldBuilder().get()
								.annotations(ListUtil.createList(
										builders.annotationBuilder().get()
												.name("Test").build(),
										builders.annotationBuilder().get()
												.name("Derp").build()
								))
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Set Annotation
				Pair.of(
						builders -> ListUtil.createList(
								builders.annotationBuilder().get()
										.name("Test")
										.build()
						),
						builders -> builders.fieldBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						builders -> Visibility.PUBLIC,
						builders -> builders.fieldBuilder().get()
								.visibility(Visibility.PUBLIC)
								.type("int").name("test")
								.build()
								.getVisibility()
				),
				// Set Is Static False
				Pair.of(
						builders -> false,
						builders -> builders.fieldBuilder().get()
								.isStatic(false).type("int").name("test")
								.build()
								.isStatic()
				),
				// Set Is Static True
				Pair.of(
						builders -> true,
						builders -> builders.fieldBuilder().get()
								.isStatic(true).type("int").name("test")
								.build()
								.isStatic()
				),
				// Is Static
				Pair.of(
						builders -> true,
						builders -> builders.fieldBuilder().get()
								.isStatic().type("int").name("test")
								.build()
								.isStatic()
				),
				// Set Is Final False
				Pair.of(
						builders -> false,
						builders -> builders.fieldBuilder().get()
								.isFinal(false).type("int").name("test")
								.build()
								.isFinal()
				),
				// Set Is Final True
				Pair.of(
						builders -> true,
						builders -> builders.fieldBuilder().get()
								.isFinal(true).type("int").name("test")
								.build()
								.isFinal()
				),
				// Is Final
				Pair.of(
						builders -> true,
						builders -> builders.fieldBuilder().get()
								.isFinal().type("int").name("test")
								.build()
								.isFinal()
				),
				// Set Value
				Pair.of(
						builders -> "42",
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.value("42")
								.build()
								.getValue()
				),
				// Equals
				Pair.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build(),
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>>
				comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build(),
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("41")
								.build()
				),
				// Different Types
				Pair.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build(),
						builders -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function<Builders, JavaField>, String, Function<SimpleClassNames, String>>> fieldMakersAndStrings =
				ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.build(),
						"int test;",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.type("int").name("test")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.build(),
						"""
								/**
								 */
								int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() +
								"""
								.builder()
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Single Annotation
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.build(),
						"""
								@Test
								int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() +
								"""
								.builder()
												.name("Test")
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Annotations
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.build(),
						"""
								@Test
								@Derp
								int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() +
								"""
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() +
								"""
								.builder()
												.name("Derp")
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Visibility
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.visibility(Visibility.PRIVATE)
								.type("int").name("test")
								.build(),
						"""
								private int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.visibility(Visibility.PRIVATE)
										.type("int").name("test")
										.build()"""
				),
				// With Static
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.isStatic()
								.type("int").name("test")
								.build(),
						"""
								static int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.isStatic()
										.type("int").name("test")
										.build()"""
				),
				// With Final
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.isFinal()
								.type("int").name("test")
								.build(),
						"""
								final int test;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.isFinal()
										.type("int").name("test")
										.build()"""
				),
				// With Value
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.value("42")
								.build(),
						"""
								int test = 42;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.type("int").name("test")
										.value("42")
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builders.fieldBuilder().get()
								.type("int").name("test")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build(),
						"""
								/**
								 */
								@Test
								@Derp
								private static final int test = 42;""",
						classNames -> classNames.fieldSimpleClassName() + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() +
								"""
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() +
								"""
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() +
								"""
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic()
										.isFinal()
										.type("int").name("test")
										.value("42")
										.build()"""
				)
		);
		
		return fieldMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaField>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builders -> () -> builders.fieldBuilder().get()
								.type("int").name("test")
								.visibility(null)
								.build(),
						"Visibility is required!"
				),
				// Null Type
				Pair.of(
						builders -> () -> builders.fieldBuilder().get()
								.name("test")
								.build(),
						"Must specify type!"
				),
				// Null Name
				Pair.of(
						builders -> () -> builders.fieldBuilder().get()
								.type("int")
								.build(),
						"Must specify name!"
				),
				// All
				Pair.of(
						builders -> () -> builders.fieldBuilder().get()
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify type!
								Must specify name!"""
				)
		);
		
		List<Pair<Supplier<JavaField>, String>> editableRelatedErrors = ListUtil.createList(
				// Editable Javadoc in Uneditable JavaField
				Pair.of(
						() -> UneditableJavaField.builder()
								.type("int").name("test")
								.javadoc(EditableJavadoc.builder().build())
								.build(),
						"javadoc is not uneditable in this uneditable JavaField"
				),
				// Editable Annotation in Uneditable JavaField
				Pair.of(
						() -> UneditableJavaField.builder()
								.type("int").name("test")
								.annotation(EditableJavaAnnotation.builder().name("Test").build())
								.build(),
						"some annotations are not uneditable in this uneditable JavaField"
				),
				// Editable Javadoc and Editable Annotation in Uneditable JavaField
				Pair.of(
						() -> UneditableJavaField.builder()
								.type("int").name("test")
								.javadoc(EditableJavadoc.builder().build())
								.annotation(EditableJavaAnnotation.builder().name("Test").build())
								.build(),
						"""
								javadoc is not uneditable in this uneditable JavaField
								some annotations are not uneditable in this uneditable JavaField"""
				),
				// Uneditable Javadoc in Editable JavaField
				Pair.of(
						() -> EditableJavaField.builder()
								.type("int").name("test")
								.javadoc(UneditableJavadoc.builder().build())
								.build(),
						"javadoc is not editable in this editable JavaField"
				),
				// Uneditable Annotation in Editable JavaField
				Pair.of(
						() -> EditableJavaField.builder()
								.type("int").name("test")
								.annotation(UneditableJavaAnnotation.builder().name("Test").build())
								.build(),
						"some annotations are not editable in this editable JavaField"
				),
				// Uneditable Javadoc and Uneditable Annotation in Editable JavaField
				Pair.of(
						() -> EditableJavaField.builder()
								.type("int").name("test")
								.javadoc(UneditableJavadoc.builder().build())
								.annotation(UneditableJavaAnnotation.builder().name("Test").build())
								.build(),
						"""
								javadoc is not editable in this editable JavaField
								some annotations are not editable in this editable JavaField"""
				)
		);
		
		return Stream.concat(builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight()))), editableRelatedErrors.stream()
				.map(pair -> Arguments.of(pair.getLeft(), pair.getRight())));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetJavadoc(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		Javadoc doc1 = EditableJavadoc.builder().build();
		Javadoc doc2 = EditableJavadoc.builder().author("Me").build();
		assertNull(field.getJavadoc());
		field.setJavadoc(doc1);
		assertEquals(doc1, field.getJavadoc());
		field.setJavadoc(doc2);
		assertEquals(doc2, field.getJavadoc());
	}
	
	@Test
	public void testSetJavadocNotEditable(){
		try{
			EditableJavaField field = EditableJavaField.builder()
					.type("int").name("test")
					.build();
			Javadoc doc = UneditableJavadoc.builder().build();
			field.setJavadoc(doc);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires an editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.addAnnotation(test);
		assertEquals(ListUtil.createList(test), field.getAnnotations());
		field.addAnnotation(derp);
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationNotEditable(){
		try{
			EditableJavaField field = EditableJavaField.builder()
					.type("int").name("test")
					.build();
			JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
			field.addAnnotation(test);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.addAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
		field.addAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(test, derp, blah, yep), field.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationsNotEditable(){
		try{
			EditableJavaField field = EditableJavaField.builder()
					.type("int").name("test")
					.build();
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			field.addAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), field.getAnnotations());
		field.setAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), field.getAnnotations());
		field.setAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(blah, yep), field.getAnnotations());
	}
	
	@Test
	public void testSetAnnotationsNotEditable(){
		try{
			EditableJavaField field = EditableJavaField.builder()
					.type("int").name("test")
					.build();
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			field.setAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable JavaField requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertEquals(Visibility.NONE, field.getVisibility());
		field.setVisibility(Visibility.PUBLIC);
		assertEquals(Visibility.PUBLIC, field.getVisibility());
	}
	
	@Test
	public void testSetStatic(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertFalse(field.isStatic());
		field.setStatic(true);
		assertTrue(field.isStatic());
		field.setStatic(true);
		assertTrue(field.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertFalse(field.isFinal());
		field.setFinal(true);
		assertTrue(field.isFinal());
		field.setFinal(true);
		assertTrue(field.isFinal());
	}
	
	@Test
	public void testSetType(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertEquals(JavaType.builder()
				.baseType("int")
				.build(), field.getType());
		field.setType(JavaType.builder()
				.baseType("String")
				.build());
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), field.getType());
		field.setType(JavaType.builder()
				.baseType("double")
				.build());
		assertEquals(JavaType.builder()
				.baseType("double")
				.build(), field.getType());
	}
	
	@Test
	public void testSetTypeByText(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertEquals(JavaType.builder()
				.baseType("int")
				.build(), field.getType());
		field.setType("String");
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), field.getType());
		field.setType("double");
		assertEquals(JavaType.builder()
				.baseType("double")
				.build(), field.getType());
	}
	
	@Test
	public void testSetName(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertEquals("test", field.getName());
		field.setName("derp");
		assertEquals("derp", field.getName());
		field.setName("blah");
		assertEquals("blah", field.getName());
	}
	
	@Test
	public void testSetValue(){
		EditableJavaField field = EditableJavaField.builder()
				.type("int").name("test")
				.build();
		assertNull(field.getValue());
		field.setValue("42");
		assertEquals("42", field.getValue());
		field.setValue("27");
		assertEquals("27", field.getValue());
	}
}
