package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaClassTest{
	
	private static class TestJavaClass extends JavaClass{
		
		private TestJavaClass(
				boolean editable, boolean isInnerClass,
				JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
				Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal, String className,
				String superClassName, List<String> implementsInterfaceNames,
				List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
				List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
				List<Pair<JavaCodeTypes, String>> innerElementsOrder){
			super(editable, isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
					singleLineComments, multiLineComments,
					innerClasses, fields, methods,
					innerElementsOrder);
		}
	}
	
	private static class TestJavaClassBuilder extends JavaClassBuilder<TestJavaClass>{
		
		private final boolean editable;
		
		private TestJavaClassBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder(){
			return UneditableJavaPackageDeclaration.builder();
		}
		
		@Override
		protected JavaImportStatementBuilder<?> getImportStatementBuilder(){
			return UneditableJavaImportStatement.builder();
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaClass constructClass(){
			return new TestJavaClass(editable, isInnerClass, packageDeclaration, importStatements,
					javadoc, annotations,
					visibility, isStatic, isFinal, className,
					superClassName, implementsInterfaceNames,
					singleLineComments, multiLineComments,
					innerClasses, fields, methods,
					innerElementsOrder);
		}
	}
	
	private String className;
	private JavaClass clazz;
	
	@BeforeEach
	public void setup(){
		className = "AClassName";
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.build();
	}
	
	@Test
	public void testGetType(){
		assertEquals(JavaCodeTypes.CLASS, clazz.getJavaCodeType());
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(clazz.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		clazz = new TestJavaClassBuilder(true)
				.className(className)
				.build();
		assertTrue(clazz.isEditable());
	}
	
	@Test
	public void testGetInnerClassesMapNoInnerClasses(){
		assertTrue(clazz.getInnerClassesMap().isEmpty());
	}
	
	@Test
	public void testGetInnerClassesMapSingleInnerClass(){
		JavaClass innerClass = new TestJavaClassBuilder(false)
				.innerClass()
				.className("BClassName")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.innerClass(innerClass)
				.className(className)
				.build();
		Map<String, JavaClass> innerClassMap = clazz.getInnerClassesMap();
		assertEquals(1, innerClassMap.size());
		assertEquals(innerClass, innerClassMap.get("BClassName"));
	}
	
	@Test
	public void testGetInnerClassesMapTwoInnerClasses(){
		JavaClass innerClass = new TestJavaClassBuilder(false)
				.innerClass()
				.className("BClassName")
				.build();
		JavaClass innerClass2 = new TestJavaClassBuilder(false)
				.innerClass()
				.className("CClassName")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.innerClass(innerClass)
				.innerClass(innerClass2)
				.className(className)
				.build();
		Map<String, JavaClass> innerClassMap = clazz.getInnerClassesMap();
		assertEquals(2, innerClassMap.size());
		assertEquals(innerClass, innerClassMap.get("BClassName"));
		assertEquals(innerClass2, innerClassMap.get("CClassName"));
	}
	
	@Test
	public void testGetFieldsMapNoFields(){
		assertTrue(clazz.getFieldsMap().isEmpty());
	}
	
	@Test
	public void testGetFieldsMapSingleField(){
		JavaField field = UneditableJavaField.builder()
				.type("String").name("test")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.field(field)
				.build();
		Map<String, JavaField> fieldMap = clazz.getFieldsMap();
		assertEquals(1, fieldMap.size());
		assertEquals(field, fieldMap.get("test"));
	}
	
	@Test
	public void testGetFieldsMapTwoFields(){
		JavaField field = UneditableJavaField.builder()
				.type("String").name("test")
				.build();
		JavaField field2 = UneditableJavaField.builder()
				.type("int").name("version")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.field(field)
				.field(field2)
				.build();
		Map<String, JavaField> fieldMap = clazz.getFieldsMap();
		assertEquals(2, fieldMap.size());
		assertEquals(field, fieldMap.get("test"));
		assertEquals(field2, fieldMap.get("version"));
	}
	
	@Test
	public void testGetMethodsMapNoMethods(){
		assertTrue(clazz.getMethodsMap().isEmpty());
	}
	
	@Test
	public void testGetMethodsMapSingleMethod(){
		JavaMethod method = UneditableJavaMethod.builder()
				.returnType("String").name("getType")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.method(method)
				.build();
		Map<String, JavaMethod> methodMap = clazz.getMethodsMap();
		assertEquals(1, methodMap.size());
		assertEquals(method, methodMap.get("getType()"));
	}
	
	@Test
	public void testGetMethodsMapTwoMethods(){
		JavaMethod method = UneditableJavaMethod.builder()
				.returnType("String").name("getType")
				.build();
		JavaMethod method2 = UneditableJavaMethod.builder()
				.returnType("int").name("getVersion")
				.parameter("String", "type")
				.build();
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.method(method)
				.method(method2)
				.build();
		Map<String, JavaMethod> methodMap = clazz.getMethodsMap();
		assertEquals(2, methodMap.size());
		assertEquals(method, methodMap.get("getType()"));
		assertEquals(method2, methodMap.get("getVersion(String type)"));
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
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
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
		clazz = new TestJavaClassBuilder(false)
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
	public void testToStringWithImplementsInterface(){
		clazz = new TestJavaClassBuilder(false)
				.className(className).implementsInterfaceName("SomeInterface")
				.build();
		String javaString = """
				class AClassName implements SomeInterface{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMultipleImplementsInterfaces(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.implementsInterfaceName("SomeInterface")
				.implementsInterfaceName("SomeOtherInterface")
				.build();
		String javaString = """
				class AClassName implements SomeInterface, SomeOtherInterface{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.javadoc(UneditableJavadoc.builder().build())
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
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = new TestJavaClassBuilder(false)
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatement(UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatement(UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
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
		clazz = new TestJavaClassBuilder(false)
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
	public void testToStringWithFinal(){
		clazz = new TestJavaClassBuilder(false)
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
	public void testToStringWithSingleLineComment(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.singleLineComment(UneditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.build();
		String javaString = """
				class AClassName{
				\t
					// some comment
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMultiLineComment(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.multiLineComment(UneditableJavaMultiLineComment.builder()
						.content("some comment")
						.content("more content")
						.build())
				.build();
		String javaString = """
				class AClassName{
				\t
					/*
					 * some comment
					 * more content
					 */
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithInnerClasses(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
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
	public void testToStringWithInnerClassesSwapOrder(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.innerElementsOrder(ListUtil.createList(
						Pair.of(JavaCodeTypes.CLASS, "CClassName"),
						Pair.of(JavaCodeTypes.CLASS, "BClassName")))
				.build();
		String javaString = """
				class AClassName{
				\t
					class CClassName{
					\t
					}
				\t
					class BClassName{
					\t
					}
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFields(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
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
	public void testToStringWithFieldsSwapOrder(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.innerElementsOrder(ListUtil.createList(
						Pair.of(JavaCodeTypes.FIELD, "derp"),
						Pair.of(JavaCodeTypes.FIELD, "test")
				))
				.build();
		String javaString = """
				class AClassName{
				\t
					String derp;
					int test;
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFieldsWithJavadocsOnFields(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.field(UneditableJavaField.builder()
						.javadoc(UneditableJavadoc.builder()
								.condensed()
								.content("something")
								.build())
						.type("int").name("test")
						.build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
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
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
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
	public void testToStringWithMethodsSwapOrder(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.innerElementsOrder(ListUtil.createList(
						Pair.of(JavaCodeTypes.METHOD, "getSomething(int test)"),
						Pair.of(JavaCodeTypes.METHOD, "init()")
				))
				.build();
		String javaString = """
				class AClassName{
				\t
					String getSomething(int test){
						return doSomething();
					}
				\t
					AClassName(){
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMethodsOrderNotSpecified(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.innerElementsOrder(new ArrayList<>())
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
	public void testToStringWithEverythingOrderNotSpecified(){
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className).superClassName("AnotherClassName")
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.innerElementsOrder(new ArrayList<>())
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
	public void testToStringWithEverything(){
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className).superClassName("AnotherClassName")
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.singleLineComment(UneditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.multiLineComment(UneditableJavaMultiLineComment.builder()
						.content("some comment")
						.content("more content")
						.build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
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
				public final class AClassName extends AnotherClassName implements SomeInterface, SomeOtherInterface{
				\t
					// some comment
					/*
					 * some comment
					 * more content
					 */
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
	public void testToStringWithEverythingReordered(){
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className).superClassName("AnotherClassName")
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.singleLineComment(UneditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.multiLineComment(UneditableJavaMultiLineComment.builder()
						.content("some comment")
						.content("more content")
						.build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
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
				public final class AClassName extends AnotherClassName implements SomeInterface, SomeOtherInterface{
				\t
					/*
					 * some comment
					 * more content
					 */
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
					AClassName(){
					}
				\t
					int test;
					class BClassName{
					\t
					}
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringInnerClass(){
		clazz = new TestJavaClassBuilder(false)
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
		clazz = new TestJavaClassBuilder(false)
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
		clazz = new TestJavaClassBuilder(false)
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
		clazz = new TestJavaClassBuilder(false)
				.innerClass()
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isStatic().isFinal()
				.className(className).superClassName("AnotherClassName")
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
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
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.className(className).superClassName("AnotherClassName")
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		JavaClass otherClass = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatements(ListUtil.createList(
						UneditableJavaImportStatement.builder()
								.importName("com.whatever")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.yep")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("com.example.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.importName("org.test")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.whatever.electric_boogaloo")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.yep.dope")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example.test.*")
								.build(),
						UneditableJavaImportStatement.builder()
								.isStatic()
								.importName("org.test.yep")
								.build()))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.className(className).superClassName("AnotherClassName")
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		assertEquals(clazz, otherClass);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaClass otherClass = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package.different")
						.build())
				.className(className)
				.build();
		assertNotEquals(clazz, otherClass);
	}
	
	@Test
	public void testEqualsNotSameType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(clazz, "testing");
	}
}
