package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.JavaFieldBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.JavadocBuilder;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.JavaMethodBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	private final ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder;
	private final ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder;
	private final ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder;
	private final ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder;
	
	protected ClassType clazz;
	protected String packageName, className;
	
	protected DefaultJavaClassTest(
			ThrowingSupplier<JavaClassBuilder<ClassType>, NoException> builder,
			ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder,
			ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder,
			ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder,
			ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder){
		this.builder = builder;
		this.javaAnnotationBuilder = javaAnnotationBuilder;
		this.javadocBuilder = javadocBuilder;
		this.javaFieldBuilder = javaFieldBuilder;
		this.javaMethodBuilder = javaMethodBuilder;
	}
	
	@BeforeEach
	public void setup(){
		packageName = "some.package";
		className = "AClassName";
		clazz = builder.get()
				.packageName(packageName).className(className)
				.build();
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaTypes.CLASS, clazz.getType());
	}
	
	@Test
	public void testDefaultIsInnerClass(){
		assertFalse(clazz.isInnerClass());
	}
	
	@Test
	public void testDefaultImports(){
		assertNotNull(clazz.getImports());
		assertTrue(clazz.getImports().isEmpty());
	}
	
	@Test
	public void testDefaultStaticImports(){
		assertNotNull(clazz.getStaticImports());
		assertTrue(clazz.getStaticImports().isEmpty());
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
		Assertions.assertEquals(Visibility.PUBLIC, clazz.getVisibility());
	}
	
	@Test
	public void testDefaultIsStatic(){
		assertFalse(clazz.isStatic());
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
	public void testBuilderSetPackageName(){
		assertEquals(packageName, clazz.getPackageName());
	}
	
	@Test
	public void testBuilderSetClassName(){
		assertEquals(className, clazz.getClassName());
	}
	
	@Test
	public void testBuilderSetImports(){
		List<String> imports = ListUtil.createList("com.example.*", "com.github.tadukoo.*");
		clazz = builder.get()
				.packageName(packageName).className(className)
				.imports(imports)
				.build();
		assertEquals(imports, clazz.getImports());
	}
	
	@Test
	public void testBuilderSetSingleImport(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.singleImport("com.example.*")
				.build();
		List<String> imports = clazz.getImports();
		assertEquals(1, imports.size());
		assertEquals("com.example.*", imports.get(0));
	}
	
	@Test
	public void testBuilderSetStaticImports(){
		List<String> staticImports = ListUtil.createList("com.example.Test", "com.github.tadukoo.*");
		clazz = builder.get()
				.packageName(packageName).className(className)
				.staticImports(staticImports)
				.build();
		assertEquals(staticImports, clazz.getStaticImports());
	}
	
	@Test
	public void testBuilderSetSingleStaticImport(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.staticImport("com.github.tadukoo.*")
				.build();
		List<String> staticImports = clazz.getStaticImports();
		assertEquals(1, staticImports.size());
		assertEquals("com.github.tadukoo.*", staticImports.get(0));
	}
	
	@Test
	public void testBuilderSetJavadoc(){
		Javadoc doc = javadocBuilder.get().build();
		clazz = builder.get()
				.packageName(packageName).className(className)
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
				.packageName(packageName).className(className)
				.annotations(annotations)
				.build();
		assertEquals(annotations, clazz.getAnnotations());
	}
	
	@Test
	public void testBuilderSetSingleAnnotation(){
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		clazz = builder.get()
				.packageName(packageName).className(className)
				.annotation(test)
				.build();
		List<JavaAnnotation> annotations = clazz.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testBuilderSetVisibility(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.visibility(Visibility.PRIVATE)
				.build();
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
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
	public void testBuilderSetIsStaticNoParam(){
		clazz = builder.get()
				.innerClass()
				.className(className)
				.isStatic()
				.build();
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testBuilderSetSuperClassName(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.superClassName("AnotherClassName")
				.build();
		assertEquals("AnotherClassName", clazz.getSuperClassName());
	}
	
	@Test
	public void testBuilderSetInnerClasses(){
		List<JavaClass> classes = ListUtil.createList(builder.get().innerClass().className("AClass").build(),
				builder.get().innerClass().className("BClass").build());
		clazz = builder.get()
				.packageName(packageName).className("CClassName")
				.innerClasses(classes)
				.build();
		assertEquals(classes, clazz.getInnerClasses());
	}
	
	@Test
	public void testBuilderSet1InnerClass(){
		JavaClass class2 = builder.get().innerClass().className("AClass").build();
		clazz = builder.get()
				.packageName(packageName).className("BClassName")
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
				.packageName(packageName).className(className)
				.fields(fields)
				.build();
		assertEquals(fields, clazz.getFields());
	}
	
	@Test
	public void testBuilderSetField(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.build();
		List<JavaField> fields = clazz.getFields();
		assertEquals(1, fields.size());
		JavaField field = fields.get(0);
		assertNull(field.getVisibility());
		assertEquals("int", field.getType());
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testBuilderSetMethods(){
		List<JavaMethod> methods = ListUtil.createList(javaMethodBuilder.get().returnType("int").build(),
				javaMethodBuilder.get().returnType("String").build());
		clazz = builder.get()
				.packageName(packageName).className(className)
				.methods(methods)
				.build();
		assertEquals(methods, clazz.getMethods());
	}
	
	@Test
	public void testBuilderSetMethod(){
		clazz = builder.get()
				.packageName(packageName).className(className)
				.method(javaMethodBuilder.get().returnType("int").name("someMethod").line("return 42;").build())
				.build();
		List<JavaMethod> methods = clazz.getMethods();
		assertEquals(1, methods.size());
		JavaMethod method = methods.get(0);
		assertEquals(Visibility.PUBLIC, method.getVisibility());
		assertEquals("int", method.getReturnType());
		assertEquals("someMethod", method.getName());
		assertTrue(method.getParameters().isEmpty());
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testBuilderNullPackageName(){
		try{
			clazz = builder.get()
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify packageName when not making an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderNullClassName(){
		try{
			clazz = builder.get()
					.packageName(packageName)
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
					.packageName(packageName).className(className)
					.build();
			clazz = builder.get()
					.packageName(packageName).className("BClassName")
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
					.packageName(packageName).className(className)
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
					.packageName(packageName).className(className)
					.build();
			clazz = builder.get()
					.isStatic()
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Must specify packageName when not making an inner class!
					Only inner classes can be static!""", e.getMessage());
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
					.packageName(packageName).className(className)
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
	public void testBuilderSetPackageNameInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.packageName(packageName)
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have packageName for an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetImportInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.className(className)
					.singleImport("an.import")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have imports for an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderSetStaticImportInnerClass(){
		try{
			clazz = builder.get()
					.innerClass()
					.className(className)
					.staticImport("an.other.import")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have static imports for an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllInnerClassBuilderErrors(){
		try{
			JavaClass inner = builder.get()
					.packageName(packageName).className(className)
					.build();
			clazz = builder.get()
					.innerClass()
					.packageName(packageName)
					.innerClass(inner)
					.singleImport("an.import")
					.staticImport("an.other.import")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Not allowed to have packageName for an inner class!
					Not allowed to have imports for an inner class!
					Not allowed to have static imports for an inner class!""", e.getMessage());
		}
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
		clazz = builder.get()
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
		clazz = builder.get()
				.packageName(packageName).className(className)
				.javadoc(javadocBuilder.get().build())
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
		JavaAnnotation test = javaAnnotationBuilder.get().name("Test").build();
		JavaAnnotation derp = javaAnnotationBuilder.get().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = builder.get()
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
		clazz = builder.get()
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
		clazz = builder.get()
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
		clazz = builder.get()
				.packageName(packageName).className(className)
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
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
		clazz = builder.get()
				.packageName(packageName).className(className)
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
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
		clazz = builder.get()
				.packageName(packageName).className(className)
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
		clazz = builder.get()
				.packageName(packageName).className(className)
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
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
		clazz = builder.get()
				.packageName(packageName)
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
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
		clazz = builder.get()
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
		clazz = builder.get()
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
		clazz = builder.get()
				.innerClass()
				.javadoc(javadocBuilder.get().build())
				.annotation(javaAnnotationBuilder.get().name("Test").build())
				.annotation(javaAnnotationBuilder.get().name("Derp").build())
				.isStatic()
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
		clazz = builder.get()
				.packageName(packageName)
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
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
				.packageName(packageName)
				.imports(ListUtil.createList("com.example.*", "", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "", "com.github.tadukoo.test.*"))
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
