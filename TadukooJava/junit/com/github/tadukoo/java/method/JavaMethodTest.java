package com.github.tadukoo.java.method;

import com.github.tadukoo.java.*;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.function.Function3;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JavaMethodTest extends BaseJavaCodeTypeTest<JavaMethod>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavaMethod.builder()
								.returnType("int")
								.build(),
						false,
						(Function<UneditableJavaMethod, Boolean>) UneditableJavaMethod::isEditable
				),
				Arguments.of(
						EditableJavaMethod.builder()
								.returnType("int")
								.build(),
						true,
						(Function<EditableJavaMethod, Boolean>) EditableJavaMethod::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function3<
						Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<JavadocBuilder<? extends Javadoc>>,
						Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>,
						Object>,
				Function3<
						Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<JavadocBuilder<? extends Javadoc>>,
						Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>,
						Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> JavaCodeTypes.METHOD,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build().getJavaCodeType()
				),
				// Default Javadoc
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> null,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> new ArrayList<>(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> Visibility.NONE,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getVisibility()
				),
				// Default Abstract
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Default Static
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.isStatic()
				),
				// Default Final
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> false,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.isFinal()
				),
				// Default Name
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> null,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getName()
				),
				// Default Parameters
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> new ArrayList<>(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getParameters()
				),
				// Default Throw Types
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> new ArrayList<>(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getThrowTypes()
				),
				// Default Lines
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> new ArrayList<>(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getLines()
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
								.returnType("int").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Throwable")
								.throwType("Exception")
								.line("doSomething();")
								.line("doSomethingElse();")
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
										.returnType("int").name("test")
										.parameter("String type")
										.parameter("int derp")
										.throwType("Throwable")
										.throwType("Exception")
										.line("doSomething();")
										.line("doSomethingElse();")
										.build())
								.build()
				),
				// Set Javadoc
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> javadocBuilder.get()
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.javadoc(javadocBuilder.get()
										.build())
								.build()
								.getJavadoc()
				),
				// Set Annotations
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								annotationBuilder.get()
										.name("Test")
										.build(),
								annotationBuilder.get()
										.name("Derp")
										.build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.annotations(ListUtil.createList(
										annotationBuilder.get()
												.name("Test")
												.build(),
										annotationBuilder.get()
												.name("Derp")
												.build()
								))
								.returnType("int")
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
								.returnType("int")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> Visibility.PRIVATE,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.visibility(Visibility.PRIVATE)
								.returnType("int")
								.build()
								.getVisibility()
				),
				// Is Abstract
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isAbstract()
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Set Is Abstract
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isAbstract(true)
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Is Static
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic()
								.returnType("int")
								.build()
								.isStatic()
				),
				// Set Is Static
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic(true)
								.returnType("int")
								.build()
								.isStatic()
				),
				// Is Final
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal()
								.returnType("int")
								.build()
								.isFinal()
				),
				// Set Is Final
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> true,
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal(true)
								.returnType("int")
								.build()
								.isFinal()
				),
				// Set Return Type
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> JavaType.builder()
								.baseType("int")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType(JavaType.builder()
										.baseType("int")
										.build())
								.build()
								.getReturnType()
				),
				// Set Return Type by Text
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> JavaType.builder()
								.baseType("int")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build()
								.getReturnType()
				),
				// Set Name
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> "someName",
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int").name("someName")
								.build()
								.getName()
				),
				// Set Parameters
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("int")
												.build())
										.name("someInt")
										.build(),
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("String")
												.build())
										.name("someText")
										.build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameters(ListUtil.createList(
										JavaParameter.builder()
												.type(JavaType.builder()
														.baseType("int")
														.build())
												.name("someInt")
												.build(),
										JavaParameter.builder()
												.type(JavaType.builder()
														.baseType("String")
														.build())
												.name("someText")
												.build()
								))
								.build()
								.getParameters()
				),
				// Set Parameter
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("int")
												.build())
										.name("someInt")
										.build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter(JavaParameter.builder()
												.type(JavaType.builder()
														.baseType("int")
														.build())
												.name("someInt")
												.build()
								)
								.build()
								.getParameters()
				),
				// Set Parameter Text
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("int")
												.build())
										.name("someInt")
										.build()
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("int someInt")
								.build()
								.getParameters()
				),
				// Set Throw Types
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								"Throwable", "Exception"
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.throwTypes(ListUtil.createList(
										"Throwable",
										"Exception"
										)
								)
								.build()
								.getThrowTypes()
				),
				// Set Throw Type
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								"Throwable"
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.throwType("Throwable")
								.build()
								.getThrowTypes()
				),
				// Set Lines
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								"doSomething();", "return 42;"
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.lines(ListUtil.createList(
										"doSomething();", "return 42;"
										)
								)
								.build()
								.getLines()
				),
				// Set Line
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> ListUtil.createList(
								"return 42;"
						),
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.line("return 42;")
								.build()
								.getLines()
				),
				// Equals
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get().returnType("int")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get().returnType("int")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(methodBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								pair.getRight().apply(methodBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function3<
						Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<JavadocBuilder<? extends Javadoc>>,
						Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>,
						Object>,
				Function3<
						Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<JavadocBuilder<? extends Javadoc>>,
						Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>,
						Object>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get().returnType("int")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build(),
						(builder, javadocBuilder, annotationBuilder) -> builder.get().returnType("int")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 41;").build()
				),
				// Different type
				Pair.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build(),
						(builder, javadocBuilder, annotationBuilder) -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(methodBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								pair.getRight().apply(methodBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function3<
						Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<JavadocBuilder<? extends Javadoc>>,
						Supplier<JavaAnnotationBuilder<? extends JavaAnnotation>>,
						JavaMethod>,
				String,
				Function3<String, String, String, String>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.build(),
						"""
								int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.javadoc(javadocBuilder.get()
										.build())
								.returnType("int")
								.build(),
						"""
								/**
								 */
								int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.javadoc(""" + javadocClassName + """
								.builder()
												.build())
										.returnType("int")
										.build()"""
				),
				// With Annotation
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.returnType("int")
								.build(),
						"""
								@Test
								int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.annotation(""" + annotationClassName + """
								.builder()
												.name("Test")
												.build())
										.returnType("int")
										.build()"""
				),
				// With Multiple Annotations
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build())
								.returnType("int")
								.build(),
						"""
								@Test
								@Derp
								int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.annotation(""" + annotationClassName + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + annotationClassName + """
								.builder()
												.name("Derp")
												.build())
										.returnType("int")
										.build()"""
				),
				// With Visibility
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.visibility(Visibility.PRIVATE)
								.returnType("int")
								.build(),
						"""
								private int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.visibility(Visibility.PRIVATE)
										.returnType("int")
										.build()"""
				),
				// With Abstract
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isAbstract()
								.returnType("int")
								.build(),
						"""
								abstract int();""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.isAbstract()
										.returnType("int")
										.build()"""
				),
				// With Static
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isStatic()
								.returnType("int")
								.build(),
						"""
								static int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.isStatic()
										.returnType("int")
										.build()"""
				),
				// With Final
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.isFinal()
								.returnType("int")
								.build(),
						"""
								final int(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.isFinal()
										.returnType("int")
										.build()"""
				),
				// With Name
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int").name("someMethod")
								.build(),
						"""
								int someMethod(){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.name("someMethod")
										.build()"""
				),
				// With Single Parameter
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String text")
								.build(),
						"""
								int(String text){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.build()"""
				),
				// With Multiple Parameters
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String text")
								.parameter("int something")
								.build(),
						"""
								int(String text, int something){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.parameter("int something")
										.build()"""
				),
				// With Long Parameters
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String text")
								.parameter("Testy testy")
								.parameter("Test test")
								.parameter("Test test2")
								.parameter("Test test3")
								.parameter("Test test4")
								.parameter("Test test5")
								.parameter("Test test6")
								.parameter("Test test7")
								.build(),
						"""
								int(
										String text, Testy testy, Test test, Test test2, Test test3, Test test4, Test test5, Test test6, Test test7){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.parameter("Testy testy")
										.parameter("Test test")
										.parameter("Test test2")
										.parameter("Test test3")
										.parameter("Test test4")
										.parameter("Test test5")
										.parameter("Test test6")
										.parameter("Test test7")
										.build()"""
				),
				// With Long Parameters 2 Lines
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String text")
								.parameter("Testy testy")
								.parameter("Test test")
								.parameter("Test test2")
								.parameter("Test test3")
								.parameter("Test test4")
								.parameter("Test test5")
								.parameter("Test test6")
								.parameter("Test test7")
								.parameter("Test test8")
								.build(),
						"""
								int(
										String text, Testy testy, Test test, Test test2, Test test3, Test test4, Test test5, Test test6, Test test7,\s
										Test test8){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.parameter("Testy testy")
										.parameter("Test test")
										.parameter("Test test2")
										.parameter("Test test3")
										.parameter("Test test4")
										.parameter("Test test5")
										.parameter("Test test6")
										.parameter("Test test7")
										.parameter("Test test8")
										.build()"""
				),
				// With Long Single Parameter
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
								.build(),
						"""
								int(
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
										.build()"""
				),
				// With 2 Long Single Parameters
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2")
								.build(),
						"""
								int(
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah,\s
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2){ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2")
										.build()"""
				),
				// With Single Throw Type
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.throwType("Throwable")
								.build(),
						"""
								int() throws Throwable{ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.throwType("Throwable")
										.build()"""
				),
				// With Multiple Throw Types
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.throwType("Throwable")
								.throwType("Exception")
								.build(),
						"""
								int() throws Throwable, Exception{ }""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.throwType("Throwable")
										.throwType("Exception")
										.build()"""
				),
				// With Line
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.line("return 42;")
								.build(),
						"""
								int(){
									return 42;
								}""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.line("return 42;")
										.build()"""
				),
				// With Lines
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.line("doSomething();")
								.line("return 42;")
								.build(),
						"""
								int(){
									doSomething();
									return 42;
								}""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.returnType("int")
										.line("doSomething();")
										.line("return 42;")
										.build()"""
				),
				// With Everything
				Triple.of(
						(builder, javadocBuilder, annotationBuilder) -> builder.get()
								.returnType("int")
								.javadoc(javadocBuilder.get().build())
								.annotation(annotationBuilder.get()
										.name("Test")
										.build())
								.annotation(annotationBuilder.get()
										.name("Derp")
										.build()).name("someMethod")
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build(),
						"""
								/**
								 */
								@Test
								@Derp
								public static final int someMethod(String text, int something) throws Throwable, Exception{
									doSomething();
									return 42;
								}""",
						(simpleClassName, javadocClassName, annotationClassName) -> simpleClassName + """
								.builder()
										.javadoc(""" + javadocClassName + """
								.builder()
												.build())
										.annotation(""" + annotationClassName + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + annotationClassName + """
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isStatic()
										.isFinal()
										.returnType("int")
										.name("someMethod")
										.parameter("String text")
										.parameter("int something")
										.throwType("Throwable")
										.throwType("Exception")
										.line("doSomething();")
										.line("return 42;")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(methodBuilders.get(index),
										javadocBuilders.get(index), annotationBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(methodSimpleClassNames.get(index),
										javadocSimpleClassNames.get(index), annotationSimpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Supplier<JavaMethodBuilder<? extends JavaMethod>>,
						Supplier<? extends JavaMethod>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builder -> () -> builder.get()
								.returnType("int")
								.visibility(null)
								.build(),
						"Visibility is required!"
				),
				// Null Return Type
				Pair.of(
						builder -> () -> builder.get()
								.build(),
						"Must specify returnType!"
				),
				// All Errors
				Pair.of(
						builder -> () -> builder.get()
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify returnType!"""
				),
				// Abstract and Private
				Pair.of(
						builder -> () -> builder.get()
								.visibility(Visibility.PRIVATE)
								.isAbstract()
								.returnType("String")
								.build(),
						"Can't be abstract and private!"
				),
				// Abstract and Static
				Pair.of(
						builder -> () -> builder.get()
								.isAbstract()
								.isStatic()
								.returnType("String")
								.build(),
						"Can't be abstract and static!"
				),
				// Abstract and Final
				Pair.of(
						builder -> () -> builder.get()
								.isAbstract()
								.isFinal()
								.returnType("String")
								.build(),
						"Can't be abstract and final!"
				),
				// Abstract and Lines
				Pair.of(
						builder -> () -> builder.get()
								.isAbstract()
								.returnType("String")
								.line("return this")
								.build(),
						"Can't be abstract and have lines!"
				),
				// All Abstract Errors
				Pair.of(
						builder -> () -> builder.get()
								.visibility(Visibility.PRIVATE)
								.isAbstract()
								.isStatic()
								.isFinal()
								.returnType("String")
								.line("return this")
								.build(),
						"""
						Can't be abstract and private!
						Can't be abstract and static!
						Can't be abstract and final!
						Can't be abstract and have lines!"""
				)
		);
		
		List<Pair<Supplier<JavaMethod>, String>> editableRelatedErrors = ListUtil.createList(
				// Editable Javadoc in Uneditable JavaMethod
				Pair.of(
						() -> UneditableJavaMethod.builder()
								.returnType("int")
								.javadoc(EditableJavadoc.builder()
										.build())
								.build(),
						"javadoc is not uneditable in this uneditable JavaMethod"
				),
				// Editable Annotation in Uneditable JavaMethod
				Pair.of(
						() -> UneditableJavaMethod.builder()
								.returnType("int")
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.build(),
						"some annotations are not uneditable in this uneditable JavaMethod"
				),
				// Editable Javadoc and Editable Annotation in Uneditable JavaMethod
				Pair.of(
						() -> UneditableJavaMethod.builder()
								.returnType("int")
								.javadoc(EditableJavadoc.builder()
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.build(),
						"""
						javadoc is not uneditable in this uneditable JavaMethod
						some annotations are not uneditable in this uneditable JavaMethod"""
				),
				// Uneditable Javadoc in Editable JavaMethod
				Pair.of(
						() -> EditableJavaMethod.builder()
								.returnType("int")
								.javadoc(UneditableJavadoc.builder()
										.build())
								.build(),
						"javadoc is not editable in this editable JavaMethod"
				),
				// Uneditable Annotation in Editable JavaMethod
				Pair.of(
						() -> EditableJavaMethod.builder()
								.returnType("int")
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.build(),
						"some annotations are not editable in this editable JavaMethod"
				),
				// Uneditable Javadoc and Uneditable Annotation in Editable JavaMethod
				Pair.of(
						() -> EditableJavaMethod.builder()
								.returnType("int")
								.javadoc(UneditableJavadoc.builder()
										.build())
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.build(),
						"""
						javadoc is not editable in this editable JavaMethod
						some annotations are not editable in this editable JavaMethod"""
				)
		);
		
		return Stream.concat(builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(methodBuilders.get(index)),
								pair.getRight()))),
				editableRelatedErrors.stream()
						.map(pair -> Arguments.of(pair.getLeft(), pair.getRight())));
	}
	
	/*
	 * Unique Name
	 */
	
	private Stream<Arguments> getUniqueNameData(){
		List<Pair<Function<Supplier<JavaMethodBuilder<? extends JavaMethod>>, JavaMethod>, String>>
				uniqueNameAndBuilders = ListUtil.createList(
				// Constructor
				Pair.of(
						builder -> builder.get()
								.returnType("int")
								.build(),
						"init()"
				),
				// Constructor With Parameter
				Pair.of(
						builder -> builder.get()
								.returnType("int")
								.parameter("String name")
								.build(),
						"init(String name)"
				),
				// Constructor With Multiple Parameters
				Pair.of(
						builder -> builder.get()
								.returnType("int")
								.parameter("String name")
								.parameter("int version")
								.build(),
						"init(String name, int version)"
				),
				// Simple
				Pair.of(
						builder -> builder.get()
								.returnType("int")
								.name("test")
								.build(),
						"test()"
				),
				// With Parameter
				Pair.of(
						builder -> builder.get()
								.returnType("int").name("test")
								.parameter("String name")
								.build(),
						"test(String name)"
				),
				// With Multiple Parameters
				Pair.of(
						builder -> builder.get()
								.returnType("int").name("test")
								.parameter("String name")
								.parameter("int version")
								.build(),
						"test(String name, int version)"
				)
		);
		
		return uniqueNameAndBuilders.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(methodBuilders.get(index)),
								pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getUniqueNameData")
	public void testGetUniqueName(JavaMethod method, String uniqueName){
		assertEquals(uniqueName, method.getUniqueName());
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetJavadoc(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertNull(method.getJavadoc());
		method.setJavadoc(EditableJavadoc.builder().build());
		assertEquals(EditableJavadoc.builder().build(), method.getJavadoc());
		method.setJavadoc(EditableJavadoc.builder().author("Me").build());
		assertEquals(EditableJavadoc.builder().author("Me").build(), method.getJavadoc());
	}
	
	@Test
	public void testSetJavadocUneditable(){
		try{
			EditableJavaMethod method = EditableJavaMethod.builder()
					.returnType("int")
					.build();
			method.setJavadoc(UneditableJavadoc.builder().build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.addAnnotation(EditableJavaAnnotation.builder().name("Test").build());
		assertEquals(ListUtil.createList(EditableJavaAnnotation.builder().name("Test").build()), method.getAnnotations());
		method.addAnnotation(EditableJavaAnnotation.builder().name("Derp").build());
		assertEquals(ListUtil.createList(EditableJavaAnnotation.builder().name("Test").build(),
				EditableJavaAnnotation.builder().name("Derp").build()), method.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationUneditable(){
		try{
			EditableJavaMethod method = EditableJavaMethod.builder()
					.returnType("int")
					.build();
			method.addAnnotation(UneditableJavaAnnotation.builder().name("Test").build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("Yep").build();
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.addAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), method.getAnnotations());
		method.addAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(test, derp, blah, yep), method.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationsUneditable(){
		try{
			EditableJavaMethod method = EditableJavaMethod.builder()
					.returnType("int")
					.build();
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			method.addAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("Yep").build();
		assertEquals(new ArrayList<>(), method.getAnnotations());
		method.setAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), method.getAnnotations());
		method.setAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(blah, yep), method.getAnnotations());
	}
	
	@Test
	public void testSetAnnotationsUneditable(){
		try{
			EditableJavaMethod method = EditableJavaMethod.builder()
					.returnType("int")
					.build();
			JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
			JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
			method.setAnnotations(ListUtil.createList(test, derp));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Method requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(Visibility.NONE, method.getVisibility());
		method.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, method.getVisibility());
	}
	
	@Test
	public void testSetAbstract(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertFalse(method.isAbstract());
		method.setAbstract(true);
		assertTrue(method.isAbstract());
		method.setAbstract(true);
		assertTrue(method.isAbstract());
	}
	
	@Test
	public void testSetStatic(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertFalse(method.isStatic());
		method.setStatic(true);
		assertTrue(method.isStatic());
		method.setStatic(true);
		assertTrue(method.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertFalse(method.isFinal());
		method.setFinal(true);
		assertTrue(method.isFinal());
		method.setFinal(true);
		assertTrue(method.isFinal());
	}
	
	@Test
	public void testSetReturnType(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(JavaType.builder()
				.baseType("int")
				.build(), method.getReturnType());
		method.setReturnType(JavaType.builder()
				.baseType("String")
				.build());
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), method.getReturnType());
		method.setReturnType(JavaType.builder()
				.baseType("Derp")
				.build());
		assertEquals(JavaType.builder()
				.baseType("Derp")
				.build(), method.getReturnType());
	}
	
	@Test
	public void testSetReturnTypeByText(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(JavaType.builder()
				.baseType("int")
				.build(), method.getReturnType());
		method.setReturnType("String");
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), method.getReturnType());
		method.setReturnType("Derp");
		assertEquals(JavaType.builder()
				.baseType("Derp")
				.build(), method.getReturnType());
	}
	
	@Test
	public void testSetName(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertNull(method.getName());
		method.setName("something");
		assertEquals("something", method.getName());
		method.setName("somethingElse");
		assertEquals("somethingElse", method.getName());
	}
	
	@Test
	public void testAddParameterText(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaParameter parameter1 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("test")
				.build();
		JavaParameter parameter2 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("int")
						.build())
				.name("something")
				.build();
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameter("String test");
		assertEquals(ListUtil.createList(parameter1), method.getParameters());
		method.addParameter("int something");
		assertEquals(ListUtil.createList(parameter1, parameter2),
				method.getParameters());
	}
	
	@Test
	public void testAddParameter(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaParameter parameter1 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("test")
				.build();
		JavaParameter parameter2 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("int")
						.build())
				.name("something")
				.build();
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameter(parameter1);
		assertEquals(ListUtil.createList(parameter1), method.getParameters());
		method.addParameter(parameter2);
		assertEquals(ListUtil.createList(parameter1, parameter2), method.getParameters());
	}
	
	@Test
	public void testAddParameters(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaParameter parameter1 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("test")
				.build();
		JavaParameter parameter2 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("int")
						.build())
				.name("something")
				.build();
		JavaParameter parameter3 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("derp")
				.build();
		JavaParameter parameter4 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("double")
						.build())
				.name("blah")
				.build();
		assertEquals(new ArrayList<>(), method.getParameters());
		method.addParameters(ListUtil.createList(parameter1, parameter2));
		assertEquals(ListUtil.createList(parameter1, parameter2), method.getParameters());
		method.addParameters(ListUtil.createList(parameter3, parameter4));
		assertEquals(ListUtil.createList(parameter1, parameter2, parameter3, parameter4), method.getParameters());
	}
	
	@Test
	public void testSetParameters(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		JavaParameter parameter1 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("test")
				.build();
		JavaParameter parameter2 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("int")
						.build())
				.name("something")
				.build();
		JavaParameter parameter3 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("String")
						.build())
				.name("derp")
				.build();
		JavaParameter parameter4 = JavaParameter.builder()
				.type(JavaType.builder()
						.baseType("double")
						.build())
				.name("blah")
				.build();
		assertEquals(new ArrayList<>(), method.getParameters());
		method.setParameters(ListUtil.createList(parameter1, parameter2));
		assertEquals(ListUtil.createList(parameter1, parameter2), method.getParameters());
		method.setParameters(ListUtil.createList(parameter3, parameter4));
		assertEquals(ListUtil.createList(parameter3, parameter4), method.getParameters());
	}
	
	@Test
	public void testAddThrowType(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.addThrowType("NoException");
		assertEquals(ListUtil.createList("NoException"), method.getThrowTypes());
		method.addThrowType("Throwable");
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
	}
	
	@Test
	public void testAddThrowTypes(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.addThrowTypes(ListUtil.createList("NoException", "Throwable"));
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
		method.addThrowTypes(ListUtil.createList("String", "Exception"));
		assertEquals(ListUtil.createList("NoException", "Throwable", "String", "Exception"), method.getThrowTypes());
	}
	
	@Test
	public void testSetThrowTypes(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getThrowTypes());
		method.setThrowTypes(ListUtil.createList("NoException", "Throwable"));
		assertEquals(ListUtil.createList("NoException", "Throwable"), method.getThrowTypes());
		method.setThrowTypes(ListUtil.createList("String", "Exception"));
		assertEquals(ListUtil.createList("String", "Exception"), method.getThrowTypes());
	}
	
	@Test
	public void testAddLine(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getLines());
		method.addLine("String test = \"42\";");
		assertEquals(ListUtil.createList("String test = \"42\";"), method.getLines());
		method.addLine("test.toString();");
		assertEquals(ListUtil.createList("String test = \"42\";", "test.toString();"), method.getLines());
	}
	
	@Test
	public void testAddLines(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		String line1 = "String test = \"42\";";
		String line2 = "test.toString();";
		String line3 = "String line1 = \"String test = \"42\";\";";
		String line4 = "// yep";
		assertEquals(new ArrayList<>(), method.getLines());
		method.addLines(ListUtil.createList(line1, line2));
		assertEquals(ListUtil.createList(line1, line2), method.getLines());
		method.addLines(ListUtil.createList(line3, line4));
		assertEquals(ListUtil.createList(line1, line2, line3, line4), method.getLines());
	}
	
	@Test
	public void testSetLines(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		String line1 = "String test = \"42\";";
		String line2 = "test.toString();";
		String line3 = "String line1 = \"String test = \"42\";\";";
		String line4 = "// yep";
		assertEquals(new ArrayList<>(), method.getLines());
		method.setLines(ListUtil.createList(line1, line2));
		assertEquals(ListUtil.createList(line1, line2), method.getLines());
		method.setLines(ListUtil.createList(line3, line4));
		assertEquals(ListUtil.createList(line3, line4), method.getLines());
	}
}
