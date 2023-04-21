package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
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
				boolean editable, boolean isInnerClass, String packageName, List<String> imports, List<String> staticImports,
				Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, String className, String superClassName,
				List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods){
			super(editable, isInnerClass, packageName, imports, staticImports,
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
			return new TestJavaClass(editable, isInnerClass, packageName, imports, staticImports,
					javadoc, annotations,
					visibility, isStatic, className, superClassName,
					innerClasses, fields, methods);
		}
	}
	
	private String packageName, className;
	private JavaClass clazz;
	
	@BeforeEach
	public void setup(){
		packageName = "some.package";
		className = "AClassName";
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className)
				.build();
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(clazz.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		clazz = new TestJavaClassBuilder(true)
				.packageName(packageName).className(className)
				.build();
		assertTrue(clazz.isEditable());
	}
	
	@Test
	public void testToString(){
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithSuperClassName(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className).superClassName("AnotherClassName")
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName extends AnotherClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithJavadoc(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className)
				.javadoc(UneditableJavadoc.builder().build())
				.build();
		String javaString = """
				package some.package;
				
				/**
				 */
				public class AClassName{
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
				.packageName(packageName).className(className)
				.annotations(annotations)
				.build();
		String javaString = """
				package some.package;
				
				@Test
				@Derp
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImports(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className)
				.imports(ListUtil.createList("com.example.*", null, "com.github.tadukoo.*"))
				.build();
		String javaString = """
				package some.package;
				
				import com.example.*;
				
				import com.github.tadukoo.*;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImports(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className)
				.staticImports(ListUtil.createList("com.example.Test", null, "com.github.tadukoo.test.*"))
				.build();
		String javaString = """
				package some.package;
				
				import static com.example.Test;
				
				import static com.github.tadukoo.test.*;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithInnerClasses(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName).className(className)
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("BClassName").build())
				.innerClass(new TestJavaClassBuilder(false).innerClass().className("CClassName").build())
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
					public class BClassName{
					\t
					}
				\t
					public class CClassName{
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
				.packageName(packageName).className(className)
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.field(UneditableJavaField.builder().type("String").name("derp").build())
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName{
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
				.packageName(packageName).className(className)
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
				package some.package;
				
				public class AClassName{
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
				.packageName(packageName).className(className)
				.method(UneditableJavaMethod.builder().returnType(className).build())
				.method(UneditableJavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
					public AClassName(){
					}
				\t
					public String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName)
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
					public class BClassName{
					\t
					}
				\t
					public class CClassName{
					\t
					}
				\t
					int test;
					String derp;
				\t
					public AClassName(){
					}
				\t
					public String getSomething(int test){
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
				public class AClassName{
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
				public static class AClassName{
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
					public class BClassName{
					\t
					}
				\t
					public class CClassName{
					\t
					}
				\t
					int test;
					String derp;
				\t
					public AClassName(){
					}
				\t
					public String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testEquals(){
		clazz = new TestJavaClassBuilder(false)
				.packageName(packageName)
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
				.packageName(packageName)
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
				.packageName("some.package.different").className(className)
				.build();
		assertNotEquals(clazz, otherClass);
	}
	
	@Test
	public void testEqualsNotSameType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(clazz, "testing");
	}
}
