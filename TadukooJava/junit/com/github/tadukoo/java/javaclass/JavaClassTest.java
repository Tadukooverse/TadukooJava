package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaClassTest{
	
	private static class TestJavaClass extends JavaClass{
		
		private TestJavaClass(
				boolean editable, boolean isInnerClass, JavaPackageDeclaration packageDeclaration, List<String> imports, List<String> staticImports,
				Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, String className, String superClassName,
				List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods){
			super(editable, isInnerClass, packageDeclaration, imports, staticImports,
					javadoc, annotations,
					visibility, isStatic, className, superClassName,
					innerClasses, fields, methods);
		}
	}
	
	private static class TestJavaClassBuilder extends JavaClassBuilder<TestJavaClass>{
		
		private final boolean editable;
		
		private TestJavaClassBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaClass constructClass(){
			return new TestJavaClass(editable, isInnerClass, packageDeclaration, imports, staticImports,
					javadoc, annotations,
					visibility, isStatic, className, superClassName,
					innerClasses, fields, methods);
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
		assertEquals(JavaTypes.CLASS, clazz.getJavaType());
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
	public void testToStringWithImports(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.imports(ListUtil.createList("com.example.*", null, "com.github.tadukoo.*"))
				.build();
		String javaString = """
				import com.example.*;
				
				import com.github.tadukoo.*;
				
				class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImports(){
		clazz = new TestJavaClassBuilder(false)
				.className(className)
				.staticImports(ListUtil.createList("com.example.Test", null, "com.github.tadukoo.test.*"))
				.build();
		String javaString = """
				import static com.example.Test;
				
				import static com.github.tadukoo.test.*;
				
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
	public void testToStringWithEverything(){
		clazz = new TestJavaClassBuilder(false)
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
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
				package some.package;
				
				import com.example.*;
				
				import com.github.tadukoo.*;
				
				import static com.example.Test;
				
				import static com.github.tadukoo.test.*;
				
				/**
				 */
				@Test
				@Derp
				public class AClassName extends AnotherClassName{
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
	public void testToStringInnerClassWithEverything(){
		clazz = new TestJavaClassBuilder(false)
				.innerClass()
				.javadoc(UneditableJavadoc.builder().build())
				.annotation(UneditableJavaAnnotation.builder().name("Test").build())
				.annotation(UneditableJavaAnnotation.builder().name("Derp").build())
				.visibility(Visibility.PUBLIC)
				.isStatic()
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
				public static class AClassName extends AnotherClassName{
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
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
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
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
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
