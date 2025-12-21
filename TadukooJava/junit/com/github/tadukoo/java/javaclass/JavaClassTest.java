package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.classtype.BaseJavaClassTypeTest;
import com.github.tadukoo.java.classtype.JavaClassType;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaClassTest extends BaseJavaClassTypeTest{
	
	public JavaClassTest(){
		super(UneditableJavaClass::builder, EditableJavaClass::builder,
				builders -> builders.classBuilder().get(), SimpleClassNames::classSimpleClassName,
				JavaCodeTypes.CLASS, true, true,
				true, false, true,
				true, true, true,
				false, false);
	}
	
	protected List<Triple<Function<Builders, JavaClassType>, String, Function<SimpleClassNames, String>>>
	getClassTypeSpecificStringData(){
		return ListUtil.createList(
				// With Everything No Order Specified
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.name("AClassName").extendsType("AnotherClassName")
								.implementsType("SomeInterface").implementsType("SomeOtherInterface")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"""
								package some.package;
								
								import com.example.*;
								import com.whatever;
								
								import org.test;
								import org.yep;
								
								import static com.example.test.*;
								import static com.whatever.electric_boogaloo;
								
								import static org.test.yep;
								import static org.yep.dope;
								
								/**
								 */
								@Test
								@Derp
								public final class AClassName extends AnotherClassName implements SomeInterface, SomeOtherInterface{
								\t
									class BClassName{
									\t
									}
								\t
									class CClassName{
									\t
									}
								\t
									int test;
									String derp;
								\t
									AClassName(){ }
								\t
									String getSomething(int test){
										return doSomething();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
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
										.isFinal()
										.name("AClassName")
										.extendsType("AnotherClassName")
										.implementsType("SomeInterface")
										.implementsType("SomeOtherInterface")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.name("AClassName").extendsType("AnotherClassName")
								.implementsType("SomeInterface").implementsType("SomeOtherInterface")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build(),
						"""
								package some.package;
								
								import com.example.*;
								import com.whatever;
								
								import org.test;
								import org.yep;
								
								import static com.example.test.*;
								import static com.whatever.electric_boogaloo;
								
								import static org.test.yep;
								import static org.yep.dope;
								
								/**
								 */
								@Test
								@Derp
								public final class AClassName extends AnotherClassName implements SomeInterface, SomeOtherInterface{
								\t
									// some comment
									/*
									 * some comment
									 * more content
									 */
								\t
									class BClassName{
									\t
									}
								\t
									class CClassName{
									\t
									}
								\t
									int test;
									String derp;
								\t
									AClassName(){ }
								\t
									String getSomething(int test){
										return doSomething();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
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
										.isFinal()
										.name("AClassName")
										.extendsType("AnotherClassName")
										.implementsType("SomeInterface")
										.implementsType("SomeOtherInterface")
										.singleLineComment("some comment")
										.multiLineComment("some comment",\s
												"more content")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				),
				// With Everything Order Swapped
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.name("AClassName").extendsType("AnotherClassName")
								.implementsType("SomeInterface").implementsType("SomeOtherInterface")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.CLASS, "CClassName"),
										Pair.of(JavaCodeTypes.FIELD, "derp"),
										Pair.of(JavaCodeTypes.METHOD, "getSomething(int test)"),
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.METHOD, "init()"),
										Pair.of(JavaCodeTypes.FIELD, "test"),
										Pair.of(JavaCodeTypes.CLASS, "BClassName")
								))
								.build(),
						"""
								package some.package;
								
								import com.example.*;
								import com.whatever;
								
								import org.test;
								import org.yep;
								
								import static com.example.test.*;
								import static com.whatever.electric_boogaloo;
								
								import static org.test.yep;
								import static org.yep.dope;
								
								/**
								 */
								@Test
								@Derp
								public final class AClassName extends AnotherClassName implements SomeInterface, SomeOtherInterface{
								\t
									/*
									 * some comment
									 * more content
									 */
								\t
									class CClassName{
									\t
									}
								\t
									String derp;
								\t
									String getSomething(int test){
										return doSomething();
									}
								\t
									// some comment
									AClassName(){ }
								\t
									int test;
									class BClassName{
									\t
									}
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
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
										.isFinal()
										.name("AClassName")
										.extendsType("AnotherClassName")
										.implementsType("SomeInterface")
										.implementsType("SomeOtherInterface")
										.multiLineComment("some comment",\s
												"more content")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.singleLineComment("some comment")
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.build()"""
				),
				// Inner Class Final
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerType()
								.isFinal()
								.name("AClassName")
								.build(),
						"""
								final class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerType()
										.isFinal()
										.name("AClassName")
										.build()"""
				),
				// Inner Class With Everything
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerType()
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.name("AClassName").extendsType("AnotherClassName")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build(),
						"""
								/**
								 */
								@Test
								@Derp
								public static final class AClassName extends AnotherClassName{
								\t
									class BClassName{
									\t
									}
								\t
									class CClassName{
									\t
									}
								\t
									int test;
									String derp;
								\t
									AClassName(){ }
								\t
									String getSomething(int test){
										return doSomething();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerType()
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
										.name("AClassName")
										.extendsType("AnotherClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				)
		);
	}
	
	protected List<Pair<Function<Builders, Supplier<? extends JavaClassType>>, String>> getClassTypeSpecificBuilderErrorData(){
		return ListUtil.createList();
	}
	
	@Test
	public void testSetExtendsType(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.name("AClassName")
				.build();
		assertNull(clazz.getExtendsType());
		clazz.setExtendsType(JavaType.builder()
				.baseType("SomethingElse")
				.build());
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getExtendsType());
	}
	
	@Test
	public void testSetExtendsTypeByText(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.name("AClassName")
				.build();
		assertNull(clazz.getExtendsType());
		clazz.setExtendsType("SomethingElse");
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getExtendsType());
	}
	
	@Test
	public void testSetExtendsTypeNull(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.name("AClassName")
				.build();
		clazz.setExtendsType(JavaType.builder()
				.baseType("SomethingElse")
				.build());
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getExtendsType());
		clazz.setExtendsType((JavaType) null);
		assertNull(clazz.getExtendsType());
	}
	
	@Test
	public void testSetExtendsTypeByTextEmpty(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.name("AClassName")
				.build();
		clazz.setExtendsType("SomethingElse");
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getExtendsType());
		clazz.setExtendsType("");
		assertNull(clazz.getExtendsType());
	}
}
