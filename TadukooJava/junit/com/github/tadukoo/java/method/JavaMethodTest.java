package com.github.tadukoo.java.method;

import com.github.tadukoo.java.*;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
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
		List<Pair<Function<Builders, Object>,
				Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builders -> JavaCodeTypes.METHOD,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build().getJavaCodeType()
				),
				// Default Javadoc
				Pair.of(
						builders -> null,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						builders -> Visibility.NONE,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getVisibility()
				),
				// Default Abstract
				Pair.of(
						builders -> false,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Default Static
				Pair.of(
						builders -> false,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.isStatic()
				),
				// Default Final
				Pair.of(
						builders -> false,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.isFinal()
				),
				// Default Type Parameters
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getTypeParameters()
				),
				// Default Name
				Pair.of(
						builders -> null,
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getName()
				),
				// Default Parameters
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getParameters()
				),
				// Default Throw Types
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getThrowTypes()
				),
				// Default Lines
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getLines()
				),
				// Copy
				Pair.of(
						builders -> builders.methodBuilder().get()
								.javadoc(builders.javadocBuilder().get()
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("T")
												.build())
										.build())
								.returnType("int").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Throwable")
								.throwType("Exception")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						builders -> builders.methodBuilder().get()
								.copy(builders.methodBuilder().get()
										.javadoc(builders.javadocBuilder().get()
												.build())
										.annotation(builders.annotationBuilder().get()
												.name("Test")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic().isFinal()
										.typeParameter(JavaTypeParameter.builder()
												.baseType(JavaType.builder()
														.baseType("T")
														.build())
												.build())
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
						builders -> builders.javadocBuilder().get()
								.build(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.javadoc(builders.javadocBuilder().get()
										.build())
								.build()
								.getJavadoc()
				),
				// Set Annotations
				Pair.of(
						builders -> ListUtil.createList(
								builders.annotationBuilder().get()
										.name("Test")
										.build(),
								builders.annotationBuilder().get()
										.name("Derp")
										.build()
						),
						builders -> builders.methodBuilder().get()
								.annotations(ListUtil.createList(
										builders.annotationBuilder().get()
												.name("Test")
												.build(),
										builders.annotationBuilder().get()
												.name("Derp")
												.build()
								))
								.returnType("int")
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
						builders -> builders.methodBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.returnType("int")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						builders -> Visibility.PRIVATE,
						builders -> builders.methodBuilder().get()
								.visibility(Visibility.PRIVATE)
								.returnType("int")
								.build()
								.getVisibility()
				),
				// Is Abstract
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isAbstract()
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Set Is Abstract
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isAbstract(true)
								.returnType("int")
								.build()
								.isAbstract()
				),
				// Is Static
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isStatic()
								.returnType("int")
								.build()
								.isStatic()
				),
				// Set Is Static
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isStatic(true)
								.returnType("int")
								.build()
								.isStatic()
				),
				// Is Final
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isFinal()
								.returnType("int")
								.build()
								.isFinal()
				),
				// Set Is Final
				Pair.of(
						builders -> true,
						builders -> builders.methodBuilder().get()
								.isFinal(true)
								.returnType("int")
								.build()
								.isFinal()
				),
				// Set a Type Parameter
				Pair.of(
						builders -> ListUtil.createList(JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("T")
										.build())
								.build()),
						builders -> builders.methodBuilder().get()
								.typeParameter(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("T")
												.build())
										.build())
								.returnType("int")
								.build()
								.getTypeParameters()
				),
				// Set Multiple Type Parameters
				Pair.of(
						builders -> ListUtil.createList(JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("T")
										.build())
								.build(),
								JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("U")
												.build())
										.build()),
						builders -> builders.methodBuilder().get()
								.typeParameters(ListUtil.createList(JavaTypeParameter.builder()
										.baseType(JavaType.builder()
												.baseType("T")
												.build())
										.build(),
										JavaTypeParameter.builder()
												.baseType(JavaType.builder()
														.baseType("U")
														.build())
												.build()))
								.returnType("int")
								.build()
								.getTypeParameters()
				),
				// Set a Type Parameter via String
				Pair.of(
						builders -> ListUtil.createList(JavaTypeParameter.builder()
								.baseType(JavaType.builder()
										.baseType("T")
										.build())
								.build()),
						builders -> builders.methodBuilder().get()
								.addTypeParameters("T")
								.returnType("int")
								.build()
								.getTypeParameters()
				),
				// Set Return Type
				Pair.of(
						builders -> JavaType.builder()
								.baseType("int")
								.build(),
						builders -> builders.methodBuilder().get()
								.returnType(JavaType.builder()
										.baseType("int")
										.build())
								.build()
								.getReturnType()
				),
				// Set Return Type by Text
				Pair.of(
						builders -> JavaType.builder()
								.baseType("int")
								.build(),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build()
								.getReturnType()
				),
				// Set Name
				Pair.of(
						builders -> "someName",
						builders -> builders.methodBuilder().get()
								.returnType("int").name("someName")
								.build()
								.getName()
				),
				// Set Parameters
				Pair.of(
						builders -> ListUtil.createList(
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
						builders -> builders.methodBuilder().get()
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
						builders -> ListUtil.createList(
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("int")
												.build())
										.name("someInt")
										.build()
						),
						builders -> builders.methodBuilder().get()
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
						builders -> ListUtil.createList(
								JavaParameter.builder()
										.type(JavaType.builder()
												.baseType("int")
												.build())
										.name("someInt")
										.build()
						),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("int someInt")
								.build()
								.getParameters()
				),
				// Set Throw Types
				Pair.of(
						builders -> ListUtil.createList(
								"Throwable", "Exception"
						),
						builders -> builders.methodBuilder().get()
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
						builders -> ListUtil.createList(
								"Throwable"
						),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.throwType("Throwable")
								.build()
								.getThrowTypes()
				),
				// Set Lines
				Pair.of(
						builders -> ListUtil.createList(
								"doSomething();", "return 42;"
						),
						builders -> builders.methodBuilder().get()
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
						builders -> ListUtil.createList(
								"return 42;"
						),
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.line("return 42;")
								.build()
								.getLines()
				),
				// Equals
				Pair.of(
						builders -> builders.methodBuilder().get().returnType("int")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build(),
						builders -> builders.methodBuilder().get().returnType("int")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
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
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function<Builders, Object>,
				Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builders -> builders.methodBuilder().get().returnType("int")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.name("someMethod")
								.isStatic().isFinal()
								.parameter("String text").parameter("int something")
								.throwType("Throwable").throwType("Exception")
								.line("doSomething();").line("return 42;").build(),
						builders -> builders.methodBuilder().get().returnType("int")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
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
						builders -> builders.methodBuilder().get()
								.returnType("int")
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
		List<Triple<Function<Builders, JavaMethod>,
				String,
				Function<SimpleClassNames, String>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build(),
						"""
								int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						builders -> builders.methodBuilder().get()
								.javadoc(builders.javadocBuilder().get()
										.build())
								.returnType("int")
								.build(),
						"""
								/**
								 */
								int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.returnType("int")
										.build()"""
				),
				// With Annotation
				Triple.of(
						builders -> builders.methodBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.returnType("int")
								.build(),
						"""
								@Test
								int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.returnType("int")
										.build()"""
				),
				// With Multiple Annotations
				Triple.of(
						builders -> builders.methodBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.returnType("int")
								.build(),
						"""
								@Test
								@Derp
								int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.returnType("int")
										.build()"""
				),
				// With Visibility
				Triple.of(
						builders -> builders.methodBuilder().get()
								.visibility(Visibility.PRIVATE)
								.returnType("int")
								.build(),
						"""
								private int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.visibility(Visibility.PRIVATE)
										.returnType("int")
										.build()"""
				),
				// With Abstract
				Triple.of(
						builders -> builders.methodBuilder().get()
								.isAbstract()
								.returnType("int")
								.build(),
						"""
								abstract int();""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.isAbstract()
										.returnType("int")
										.build()"""
				),
				// With Static
				Triple.of(
						builders -> builders.methodBuilder().get()
								.isStatic()
								.returnType("int")
								.build(),
						"""
								static int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.isStatic()
										.returnType("int")
										.build()"""
				),
				// With Final
				Triple.of(
						builders -> builders.methodBuilder().get()
								.isFinal()
								.returnType("int")
								.build(),
						"""
								final int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.isFinal()
										.returnType("int")
										.build()"""
				),
				// With Type Parameter
				Triple.of(
						builders -> builders.methodBuilder().get()
								.addTypeParameters("T")
								.returnType("int")
								.build(),
						"""
								<T> int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.addTypeParameters("T")
										.returnType("int")
										.build()"""
				),
				// With Multiple Type Parameters
				Triple.of(
						builders -> builders.methodBuilder().get()
								.addTypeParameters("T")
								.addTypeParameters("U")
								.returnType("int")
								.build(),
						"""
								<T, U> int(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.addTypeParameters("T")
										.addTypeParameters("U")
										.returnType("int")
										.build()"""
				),
				// With Name
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int").name("someMethod")
								.build(),
						"""
								int someMethod(){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.name("someMethod")
										.build()"""
				),
				// With Single Parameter
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String text")
								.build(),
						"""
								int(String text){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.build()"""
				),
				// With Multiple Parameters
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String text")
								.parameter("int something")
								.build(),
						"""
								int(String text, int something){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.parameter("String text")
										.parameter("int something")
										.build()"""
				),
				// With Long Parameters
				Triple.of(
						builders -> builders.methodBuilder().get()
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
						classNames -> classNames.methodSimpleClassName() + """
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
						builders -> builders.methodBuilder().get()
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
						classNames -> classNames.methodSimpleClassName() + """
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
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
								.build(),
						"""
								int(
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
										.build()"""
				),
				// With 2 Long Single Parameters
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
								.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2")
								.build(),
						"""
								int(
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah,\s
										String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2){ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah")
										.parameter("String aReallyLongNameForAStringThatReallyShouldNotBeThisWayButItIsForSomeReasonAndNoOneKnowsWhyItWasDoneThisWayAndYeah2")
										.build()"""
				),
				// With Single Throw Type
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.throwType("Throwable")
								.build(),
						"""
								int() throws Throwable{ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.throwType("Throwable")
										.build()"""
				),
				// With Multiple Throw Types
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.throwType("Throwable")
								.throwType("Exception")
								.build(),
						"""
								int() throws Throwable, Exception{ }""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.throwType("Throwable")
										.throwType("Exception")
										.build()"""
				),
				// With Line
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.line("return 42;")
								.build(),
						"""
								int(){
									return 42;
								}""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.line("return 42;")
										.build()"""
				),
				// With Lines
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.line("doSomething();")
								.line("return 42;")
								.build(),
						"""
								int(){
									doSomething();
									return 42;
								}""",
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.returnType("int")
										.line("doSomething();")
										.line("return 42;")
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
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
						classNames -> classNames.methodSimpleClassName() + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
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
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaMethod>>, String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.returnType("int")
								.visibility(null)
								.build(),
						"Visibility is required!"
				),
				// Null Return Type
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.build(),
						"Must specify returnType!"
				),
				// All Errors
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify returnType!"""
				),
				// Abstract and Private
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.visibility(Visibility.PRIVATE)
								.isAbstract()
								.returnType("String")
								.build(),
						"Can't be abstract and private!"
				),
				// Abstract and Static
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.isAbstract()
								.isStatic()
								.returnType("String")
								.build(),
						"Can't be abstract and static!"
				),
				// Abstract and Final
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.isAbstract()
								.isFinal()
								.returnType("String")
								.build(),
						"Can't be abstract and final!"
				),
				// Abstract and Lines
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
								.isAbstract()
								.returnType("String")
								.line("return this")
								.build(),
						"Can't be abstract and have lines!"
				),
				// All Abstract Errors
				Pair.of(
						builders -> () -> builders.methodBuilder().get()
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
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight()))),
				editableRelatedErrors.stream()
						.map(pair -> Arguments.of(pair.getLeft(), pair.getRight())));
	}
	
	/*
	 * Unique Name
	 */
	
	private Stream<Arguments> getUniqueNameData(){
		List<Pair<Function<Builders, JavaMethod>, String>>
				uniqueNameAndBuilders = ListUtil.createList(
				// Constructor
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.build(),
						"init()"
				),
				// Constructor With Parameter
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String name")
								.build(),
						"init(String name)"
				),
				// Constructor With Multiple Parameters
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.parameter("String name")
								.parameter("int version")
								.build(),
						"init(String name, int version)"
				),
				// Simple
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int")
								.name("test")
								.build(),
						"test()"
				),
				// With Parameter
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int").name("test")
								.parameter("String name")
								.build(),
						"test(String name)"
				),
				// With Multiple Parameters
				Pair.of(
						builders -> builders.methodBuilder().get()
								.returnType("int").name("test")
								.parameter("String name")
								.parameter("int version")
								.build(),
						"test(String name, int version)"
				)
		);
		
		return uniqueNameAndBuilders.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
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
	public void testSetTypeParameters(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getTypeParameters());
		method.setTypeParameters(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		));
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		), method.getTypeParameters());
		method.setTypeParameters(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		));
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		), method.getTypeParameters());
	}
	
	@Test
	public void testAddTypeParameters(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getTypeParameters());
		method.addTypeParameters(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		));
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		), method.getTypeParameters());
		method.addTypeParameters(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		));
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		), method.getTypeParameters());
	}
	
	@Test
	public void testSetTypeParametersByString(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getTypeParameters());
		method.setTypeParameters("T, U");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		), method.getTypeParameters());
		method.setTypeParameters("R, S");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		), method.getTypeParameters());
	}
	
	@Test
	public void testAddTypeParametersByString(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getTypeParameters());
		method.addTypeParameters("T, U");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build()
		), method.getTypeParameters());
		method.addTypeParameters("R, S");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("U")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("S")
								.build())
						.build()
		), method.getTypeParameters());
	}
	
	@Test
	public void testAddTypeParameter(){
		EditableJavaMethod method = EditableJavaMethod.builder()
				.returnType("int")
				.build();
		assertEquals(new ArrayList<>(), method.getTypeParameters());
		method.addTypeParameter(JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build());
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build()
		), method.getTypeParameters());
		method.addTypeParameter(JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build());
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("T")
								.build())
						.build(),
				JavaTypeParameter.builder()
						.baseType(JavaType.builder()
								.baseType("R")
								.build())
						.build()
		), method.getTypeParameters());
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
