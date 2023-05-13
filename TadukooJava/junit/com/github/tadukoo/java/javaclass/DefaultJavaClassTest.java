package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.JavaFieldBuilder;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.JavaMethodBuilder;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaClassTest<ClassType extends JavaClass>{
	
	private final ThrowingSupplier<JavaClassBuilder<ClassType>, NoException> builder;
	private final ThrowingSupplier<JavaPackageDeclarationBuilder<?>, NoException> javaPackageDeclarationBuilder;
	private final ThrowingSupplier<JavaImportStatementBuilder<?>, NoException> javaImportStatementBuilder;
	private final ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder;
	private final ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder;
	private final ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder;
	private final ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder;
	
	protected ClassType clazz;
	protected String className;
	
	protected DefaultJavaClassTest(
			ThrowingSupplier<JavaClassBuilder<ClassType>, NoException> builder,
			ThrowingSupplier<JavaPackageDeclarationBuilder<?>, NoException> javaPackageDeclarationBuilder,
			ThrowingSupplier<JavaImportStatementBuilder<?>, NoException> javaImportStatementBuilder,
			ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder,
			ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder,
			ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder,
			ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder){
		this.builder = builder;
		this.javaPackageDeclarationBuilder = javaPackageDeclarationBuilder;
		this.javaImportStatementBuilder = javaImportStatementBuilder;
		this.javaAnnotationBuilder = javaAnnotationBuilder;
		this.javadocBuilder = javadocBuilder;
		this.javaFieldBuilder = javaFieldBuilder;
		this.javaMethodBuilder = javaMethodBuilder;
	}
	
	@BeforeEach
	public void setup(){
		className = "AClassName";
		clazz = builder.get()
				.className(className)
				.build();
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaTypes.CLASS, clazz.getJavaType());
	}
	
	@Test
	public void testDefaultIsInnerClass(){
		assertFalse(clazz.isInnerClass());
	}
	
	@Test
	public void testDefaultPackageDeclaration(){
		assertNull(clazz.getPackageDeclaration());
	}
	
	@Test
	public void testDefaultImportStatements(){
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
	}
	
	@Test
	public void testDefaultJavadoc(){
		assertNull(clazz.getJavadoc());
	}
	
	@Test
	public void testDefaultAnnotations(){
		assertNotNull(clazz.getAnnotations());
		assertTrue(clazz.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		Assertions.assertEquals(Visibility.NONE, clazz.getVisibility());
	}
	
	@Test
	public void testDefaultIsStatic(){
		assertFalse(clazz.isStatic());
	}
	
	@Test
	public void testDefaultIsFinal(){
		assertFalse(clazz.isFinal());
	}
	
	@Test
	public void testDefaultSuperClassName(){
		assertNull(clazz.getSuperClassName());
	}
	
	@Test
	public void testDefaultFields(){
		assertNotNull(clazz.getFields());
		assertTrue(clazz.getFields().isEmpty());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaClass otherClass = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package")
						.build())
				.importStatement(javaImportStatementBuilder.get()
						.importName("some.classname")
						.build())
				.javadoc(javadocBuilder.get()
						.build())
				.annotation(javaAnnotationBuilder.get()
						.name("Test")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className)
				.superClassName("BClassName")
				.innerClass(builder.get()
						.innerClass()
						.className("CClassName")
						.build())
				.field(javaFieldBuilder.get()
						.type("String").name("test")
						.build())
				.method(javaMethodBuilder.get()
						.returnType("String").name("type")
						.build())
				.build();
		clazz = builder.get()
				.copy(otherClass)
				.build();
		assertEquals(otherClass, clazz);
	}
	
	@Test
	public void testBuilderSetIsInnerClass(){
		clazz = builder.get()
				.className(className)
				.isInnerClass(true)
				.build();
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testBuilderSetInnerClass(){
		clazz = builder.get()
				.className(className)
				.innerClass()
				.build();
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testBuilderSetPackageDeclaration(){
		JavaPackageDeclaration packageDeclaration = javaPackageDeclarationBuilder.get()
				.packageName("some.package")
				.build();
		clazz = builder.get()
				.packageDeclaration(packageDeclaration)
				.className(className)
				.build();
		assertEquals(packageDeclaration, clazz.getPackageDeclaration());
	}
	
	@Test
	public void testBuilderSetClassName(){
		assertEquals(className, clazz.getClassName());
	}
	
	@Test
	public void testBuilderSingleImportStatement(){
		JavaImportStatement importStatement = javaImportStatementBuilder.get()
				.importName("com.example")
				.build();
		clazz = builder.get()
				.importStatement(importStatement)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(importStatement), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportStatements(){
		List<JavaImportStatement> importStatements = ListUtil.createList(
				javaImportStatementBuilder.get()
						.importName("com.example")
						.build(),
				javaImportStatementBuilder.get()
						.isStatic()
						.importName("com.other")
						.build());
		clazz = builder.get()
				.importStatements(importStatements)
				.className(className)
				.build();
		assertEquals(importStatements, clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetJavadoc(){
		Javadoc doc = javadocBuilder.get().build();
		clazz = builder.get()
				.className(className)
				.javadoc(doc)
				.build();
		assertEquals(doc, clazz.getJavadoc());
	}
	
	@Test
	public void testBuilderSetAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = builder.get()
				.className(className)
				.annotations(annotations)
				.build();
		assertEquals(annotations, clazz.getAnnotations());
	}
	
	@Test
	public void testBuilderSetSingleAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		clazz = builder.get()
				.className(className)
				.annotation(test)
				.build();
		List<JavaAnnotation> annotations = clazz.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testBuilderSetVisibility(){
		clazz = builder.get()
				.className(className)
				.visibility(Visibility.PRIVATE)
				.build();
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testBuilderSetIsStaticNoParam(){
		clazz = builder.get()
				.innerClass()
				.className(className)
				.isStatic()
				.build();
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		clazz = builder.get()
				.innerClass()
				.className(className)
				.isStatic(true)
				.build();
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testBuilderSetFinalNoParam(){
		clazz = builder.get()
				.className(className)
				.isFinal()
				.build();
		assertTrue(clazz.isFinal());
	}
	
	@Test
	public void testBuilderSetIsFinal(){
		clazz = builder.get()
				.className(className)
				.isFinal(true)
				.build();
		assertTrue(clazz.isFinal());
	}
	
	@Test
	public void testBuilderSetSuperClassName(){
		clazz = builder.get()
				.className(className)
				.superClassName("AnotherClassName")
				.build();
		assertEquals("AnotherClassName", clazz.getSuperClassName());
	}
	
	@Test
	public void testBuilderSetInnerClasses(){
		List<JavaClass> classes = ListUtil.createList(builder.get().innerClass().className("AClass").build(),
				builder.get().innerClass().className("BClass").build());
		clazz = builder.get()
				.className("CClassName")
				.innerClasses(classes)
				.build();
		assertEquals(classes, clazz.getInnerClasses());
	}
	
	@Test
	public void testBuilderSet1InnerClass(){
		JavaClass class2 = builder.get().innerClass().className("AClass").build();
		clazz = builder.get()
				.className("BClassName")
				.innerClass(class2)
				.build();
		List<JavaClass> innerClasses = clazz.getInnerClasses();
		assertEquals(1, innerClasses.size());
		assertEquals(class2, innerClasses.get(0));
	}
	
	@Test
	public void testBuilderSetFields(){
		List<JavaField> fields = ListUtil.createList(javaFieldBuilder.get().type("int").name("test").build(),
				javaFieldBuilder.get().type("String").name("derp").build());
		clazz = builder.get()
				.className(className)
				.fields(fields)
				.build();
		assertEquals(fields, clazz.getFields());
	}
	
	@Test
	public void testBuilderSetField(){
		clazz = builder.get()
				.className(className)
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.build();
		List<JavaField> fields = clazz.getFields();
		assertEquals(1, fields.size());
		JavaField field = fields.get(0);
		assertEquals(Visibility.NONE, field.getVisibility());
		assertEquals("int", field.getType());
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testBuilderSetMethods(){
		List<JavaMethod> methods = ListUtil.createList(javaMethodBuilder.get().returnType("int").build(),
				javaMethodBuilder.get().returnType("String").build());
		clazz = builder.get()
				.className(className)
				.methods(methods)
				.build();
		assertEquals(methods, clazz.getMethods());
	}
	
	@Test
	public void testBuilderSetMethod(){
		clazz = builder.get()
				.className(className)
				.method(javaMethodBuilder.get().returnType("int").name("someMethod").line("return 42;").build())
				.build();
		List<JavaMethod> methods = clazz.getMethods();
		assertEquals(1, methods.size());
		JavaMethod method = methods.get(0);
		assertEquals(Visibility.NONE, method.getVisibility());
		assertEquals("int", method.getReturnType());
		assertEquals("someMethod", method.getName());
		assertTrue(method.getParameters().isEmpty());
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testBuilderNullVisibility(){
		try{
			clazz = builder.get()
					.visibility(null)
					.className("AClassName")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderNullClassName(){
		try{
			clazz = builder.get()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderInnerClassNotInnerClass(){
		try{
			JavaClass inner = builder.get()
					.className(className)
					.build();
			clazz = builder.get()
					.className("BClassName")
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Inner class 'AClassName' is not an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderOuterClassCantBeStatic(){
		try{
			clazz = builder.get()
					.isStatic()
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Only inner classes can be static!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllOuterClassErrors(){
		try{
			JavaClass inner = builder.get()
					.className(className)
					.build();
			clazz = builder.get()
					.isStatic()
					.innerClass(inner)
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Visibility is required!
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Only inner classes can be static!""", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderNullVisibilityInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.visibility(null)
					.className("AClassName")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Visibility is required!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderNullClassNameInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderInnerClassNotInnerClassInInnerClass(){
		try{
			JavaClass inner = builder.get()
					.className(className)
					.build();
			clazz = builder.get()
					.innerClass()
					.className("BClassName")
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Inner class 'AClassName' is not an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetPackageDeclarationInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.packageDeclaration(javaPackageDeclarationBuilder.get()
							.packageName("some.package")
							.build())
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have package declaration for an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetImportStatementInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.className(className)
					.importStatement(javaImportStatementBuilder.get()
							.importName("com.example")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have import statements for an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllInnerClassBuilderErrors(){
		try{
			JavaClass inner = builder.get()
					.packageDeclaration(javaPackageDeclarationBuilder.get()
							.packageName("some.package")
							.build())
					.className(className)
					.build();
			clazz = builder.get()
					.innerClass()
					.packageDeclaration(javaPackageDeclarationBuilder.get()
							.packageName("some.package")
							.build())
					.innerClass(inner)
					.importStatement(javaImportStatementBuilder.get()
							.importName("com.example")
							.build())
					.visibility(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Visibility is required!
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Not allowed to have package declaration for an inner class!
					Not allowed to have import statements for an inner class!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		String javaString = """
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithPackageDeclaration(){
		clazz = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package")
						.build())
				.className(className)
				.build();
		String javaString = """
				package some.package;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithSuperClassName(){
		clazz = builder.get()
				.className(className).superClassName("AnotherClassName")
				.build();
		String javaString = """
				class AClassName extends AnotherClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		clazz = builder.get()
				.className(className)
				.javadoc(javadocBuilder.get().build())
				.build();
		String javaString = """
				/**
				 */
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = builder.get()
				.className(className)
				.annotations(annotations)
				.build();
		String javaString = """
				@Test
				@Derp
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImport(){
		clazz = builder.get()
				.className(className)
				.importStatement(javaImportStatementBuilder.get()
						.importName("com.example.*")
						.build())
				.build();
		String javaString = """
				import com.example.*;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImportsSameBaseInOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build()))
				.build();
		String javaString = """
				import com.example.*;
				import com.whatever;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImportsSameBaseReverseOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build()))
				.build();
		String javaString = """
				import com.example.*;
				import com.whatever;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImportsDifferentBaseStrangeOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.test")
								.build()))
				.build();
		String javaString = """
				import com.example.*;
				import com.whatever;
				
				import org.test;
				import org.yep;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImport(){
		clazz = builder.get()
				.className(className)
				.importStatement(javaImportStatementBuilder.get()
						.isStatic()
						.importName("com.example.*")
						.build())
				.build();
		String javaString = """
				import static com.example.*;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImportsSameBaseInOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever")
								.build()))
				.build();
		String javaString = """
				import static com.example.*;
				import static com.whatever;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImportsSameBaseReverseOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.*")
								.build()))
				.build();
		String javaString = """
				import static com.example.*;
				import static com.whatever;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImportsDifferentBaseStrangeOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.test")
								.build()))
				.build();
		String javaString = """
				import static com.example.*;
				import static com.whatever;
				
				import static org.test;
				import static org.yep;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithRegularAndStaticImportsDifferentBaseStrangeOrder(){
		clazz = builder.get()
				.className(className)
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.test")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.build();
		String javaString = """
				import com.example.*;
				import com.whatever;
				
				import org.test;
				import org.yep;
				
				import static com.example.test.*;
				import static com.whatever.electric_boogaloo;
				
				import static org.test.yep;
				import static org.yep.dope;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithVisibility(){
		clazz = builder.get()
				.className(className)
				.visibility(Visibility.PROTECTED)
				.build();
		String javaString = """
				protected class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithInnerClasses(){
		clazz = builder.get()
				.className(className)
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.build();
		String javaString = """
				class AClassName{
				\t
					class BClassName{
					\t
					}
				\t
					class CClassName{
					\t
					}
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFields(){
		clazz = builder.get()
				.className(className)
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.build();
		String javaString = """
				class AClassName{
				\t
					int test;
					String derp;
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFieldsWithJavadocsOnFields(){
		clazz = builder.get()
				.className(className)
				.field(javaFieldBuilder.get()
						.javadoc(javadocBuilder.get()
								.condensed()
								.content("something")
								.build())
						.type("int").name("test")
						.build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.build();
		String javaString = """
				class AClassName{
				\t
					/** something */
					int test;
					String derp;
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMethods(){
		clazz = builder.get()
				.className(className)
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
				class AClassName{
				\t
					AClassName(){
					}
				\t
					String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFinal(){
		clazz = builder.get()
				.isFinal()
				.className(className)
				.build();
		String javaString = """
				final class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		clazz = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.test")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className).superClassName("AnotherClassName")
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
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
				public final class AClassName extends AnotherClassName{
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
					AClassName(){
					}
				\t
					String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringInnerClass(){
		clazz = builder.get()
				.innerClass()
				.className(className)
				.build();
		assertEquals("""
				class AClassName{
				\t
				}
				""", clazz.toString());
	}
	
	@Test
	public void testToStringStaticInnerClass(){
		clazz = builder.get()
				.innerClass()
				.isStatic()
				.className(className)
				.build();
		assertEquals("""
				static class AClassName{
				\t
				}
				""", clazz.toString());
	}
	
	@Test
	public void testToStringFinalInnerClass(){
		clazz = builder.get()
				.innerClass()
				.isFinal()
				.className(className)
				.build();
		assertEquals("""
				final class AClassName{
				\t
				}
				""", clazz.toString());
	}
	
	@Test
	public void testToStringInnerClassWithEverything(){
		clazz = builder.get()
				.innerClass()
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isStatic().isFinal()
				.className(className).superClassName("AnotherClassName")
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
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
					AClassName(){
					}
				\t
					String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testEquals(){
		clazz = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.test")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.className(className).superClassName("AnotherClassName")
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		JavaClass otherClass = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						javaImportStatementBuilder.get()
								.importName("com.whatever")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.yep")
								.build(),
						javaImportStatementBuilder.get()
								.importName("com.example.*")
								.build(),
						javaImportStatementBuilder.get()
								.importName("org.test")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						javaImportStatementBuilder.get()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.className(className).superClassName("AnotherClassName")
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		assertEquals(clazz, otherClass);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaClass otherClass = builder.get()
				.packageDeclaration(javaPackageDeclarationBuilder.get()
						.packageName("some.package.different")
						.build()).className(className)
				.build();
		assertNotEquals(clazz, otherClass);
	}
	
	@Test
	public void testEqualsNotSameType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(clazz, "testing");
	}
}
