package com.github.tadukoo.java.field;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.functional.function.ThrowingFunction3;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
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
						(ThrowingFunction<UneditableJavaField, Boolean, NoException>) UneditableJavaField::isEditable),
				Arguments.of(EditableJavaField.builder()
						.type("int").name("test")
						.build(), true,
						(ThrowingFunction<EditableJavaField, Boolean, NoException>) EditableJavaField::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<
				ThrowingFunction3<
						ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<JavadocBuilder<? extends Javadoc>, NoException>,
						ThrowingSupplier<JavaAnnotationBuilder<? extends JavaAnnotation>, NoException>,
						Object, NoException>,
				ThrowingFunction3<
						ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<JavadocBuilder<? extends Javadoc>, NoException>,
						ThrowingSupplier<JavaAnnotationBuilder<? extends JavaAnnotation>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> JavaCodeTypes.FIELD,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getJavaCodeType()
				),
				// Default Javadoc
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> null,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> new ArrayList<>(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> Visibility.NONE,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getVisibility()
				),
				// Default Is Static
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.isStatic()
				),
				// Default Is Final
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.isFinal()
				),
				// Default Value
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> null,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getValue()
				),
				// Copy
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.javadoc(javadocBuilder.get()
										.build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.type("int").name("test")
								.value("42")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.copy(builder.get()
										.javadoc(javadocBuilder.get()
												.build())
										.annotation(annotationBuilder.get()
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
						(builder, javadocBuilder, annotationBuilder) -> JavaType.builder()
								.baseType("int")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type(JavaType.builder()
										.baseType("int")
										.build()).name("test")
								.build()
								.getType()
				),
				// Set Type by Text
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> JavaType.builder()
								.baseType("int")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getType()
				),
				// Set Name
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> "test",
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build()
								.getName()
				),
				// Set Javadoc
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> javadocBuilder.get()
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.javadoc(javadocBuilder.get().build())
								.type("int").name("test")
								.build()
								.getJavadoc()
				),
				// Set Annotations
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								annotationBuilder.get()
										.name("Test").build(),
								annotationBuilder.get()
										.name("Derp").build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.annotations(ListUtil.createList(
										annotationBuilder.get()
												.name("Test").build(),
										annotationBuilder.get()
												.name("Derp").build()
								))
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Set Annotation
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								annotationBuilder.get()
										.name("Test")
										.build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.type("int").name("test")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> Visibility.PUBLIC,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.visibility(Visibility.PUBLIC)
								.type("int").name("test")
								.build()
								.getVisibility()
				),
				// Set Is Static False
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic(false).type("int").name("test")
								.build()
								.isStatic()
				),
				// Set Is Static True
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic(true).type("int").name("test")
								.build()
								.isStatic()
				),
				// Is Static
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic().type("int").name("test")
								.build()
								.isStatic()
				),
				// Set Is Final False
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal(false).type("int").name("test")
								.build()
								.isFinal()
				),
				// Set Is Final True
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal(true).type("int").name("test")
								.build()
								.isFinal()
				),
				// Is Final
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal().type("int").name("test")
								.build()
								.isFinal()
				),
				// Set Value
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> "42",
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.value("42")
								.build()
								.getValue()
				),
				// Equals
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get().name("Test").build())
								.annotation(annotationBuilder.get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get().name("Test").build())
								.annotation(annotationBuilder.get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(fieldBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								pair.getRight().apply(fieldBuilders.get(index), javadocBuilders.get(index),
										annotationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<
				ThrowingFunction3<
						ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<JavadocBuilder<? extends Javadoc>, NoException>,
						ThrowingSupplier<JavaAnnotationBuilder<? extends JavaAnnotation>, NoException>,
						Object, NoException>,
				ThrowingFunction3<
						ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<JavadocBuilder<? extends Javadoc>, NoException>,
						ThrowingSupplier<JavaAnnotationBuilder<? extends JavaAnnotation>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get().name("Test").build())
								.annotation(annotationBuilder.get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("42")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get().name("Test").build())
								.annotation(annotationBuilder.get().name("Derp").build())
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.isFinal()
								.value("41")
								.build()
				),
				// Different Types
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(fieldBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								pair.getRight().apply(fieldBuilders.get(index), javadocBuilders.get(index),
										annotationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<
				ThrowingFunction3<
						ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<JavadocBuilder<? extends Javadoc>, NoException>,
						ThrowingSupplier<JavaAnnotationBuilder<? extends JavaAnnotation>, NoException>,
						JavaField, NoException>,
				String,
				ThrowingFunction3<String, String, String, String, NoException>>> fieldMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.build(),
						"int test;",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.type("int").name("test")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.build(),
						"""
								/**
								 */
								int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.javadoc(""" + simpleJavadocClassName +
								"""
								.builder()
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Single Annotation
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.build(),
						"""
								@Test
								int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.annotation(""" + simpleAnnotationClassName +
								"""
								.builder()
												.name("Test")
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Annotations
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.build(),
						"""
								@Test
								@Derp
								int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.annotation(""" + simpleAnnotationClassName +
								"""
								.builder()
												.name("Test")
												.build())
										.annotation(""" + simpleAnnotationClassName +
								"""
								.builder()
												.name("Derp")
												.build())
										.type("int").name("test")
										.build()"""
				),
				// With Visibility
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.visibility(Visibility.PRIVATE)
								.type("int").name("test")
								.build(),
						"""
								private int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.visibility(Visibility.PRIVATE)
										.type("int").name("test")
										.build()"""
				),
				// With Static
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic()
								.type("int").name("test")
								.build(),
						"""
								static int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.isStatic()
										.type("int").name("test")
										.build()"""
				),
				// With Final
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal()
								.type("int").name("test")
								.build(),
						"""
								final int test;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.isFinal()
										.type("int").name("test")
										.build()"""
				),
				// With Value
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.value("42")
								.build(),
						"""
								int test = 42;""",
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.type("int").name("test")
										.value("42")
										.build()"""
				),
				// With Everything
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.type("int").name("test")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get().name("Test").build())
								.annotation(annotationBuilder.get().name("Derp").build())
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
						(simpleClassName, simpleJavadocClassName, simpleAnnotationClassName) -> simpleClassName + """
								.builder()
										.javadoc(""" + simpleJavadocClassName +
								"""
								.builder()
												.build())
										.annotation(""" + simpleAnnotationClassName +
								"""
								.builder()
												.name("Test")
												.build())
										.annotation(""" + simpleAnnotationClassName +
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
						.map(index -> Arguments.of(triple.getLeft().apply(fieldBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(fieldSimpleClassNames.get(index),
										javadocSimpleClassNames.get(index),
										annotationSimpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<
				ThrowingFunction<ThrowingSupplier<JavaFieldBuilder<? extends JavaField>, NoException>,
						ThrowingSupplier<? extends JavaField, NoException>, NoException>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builder -> () -> builder.get()
								.type("int").name("test")
								.visibility(null)
								.build(),
						"Visibility is required!"
				),
				// Null Type
				Pair.of(
						builder -> () -> builder.get()
								.name("test")
								.build(),
						"Must specify type!"
				),
				// Null Name
				Pair.of(
						builder -> () -> builder.get()
								.type("int")
								.build(),
						"Must specify name!"
				),
				// All
				Pair.of(
						builder -> () -> builder.get()
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify type!
								Must specify name!"""
				)
		);
		
		List<Pair<ThrowingSupplier<JavaField, NoException>, String>> editableRelatedErrors = ListUtil.createList(
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
						.map(index -> Arguments.of(pair.getLeft().apply(fieldBuilders.get(index)),
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
