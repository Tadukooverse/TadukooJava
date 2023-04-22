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
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaClassBuilderTest{
	
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
		
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaClass constructClass(){
			return new TestJavaClass(false, isInnerClass, packageDeclaration, imports, staticImports,
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
		clazz = new TestJavaClassBuilder()
				.className(className)
				.build();
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
		assertEquals(Visibility.NONE, clazz.getVisibility());
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
	public void testSetIsInnerClass(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.isInnerClass(true)
				.build();
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testSetInnerClass(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.innerClass()
				.build();
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testSetPackageDeclartion(){
		JavaPackageDeclaration packageDeclaration = UneditableJavaPackageDeclaration.builder()
				.packageName("some.package")
				.build();
		clazz = new TestJavaClassBuilder()
				.packageDeclaration(packageDeclaration)
				.className(className)
				.build();
		assertEquals(packageDeclaration, clazz.getPackageDeclaration());
	}
	
	@Test
	public void testSetClassName(){
		assertEquals(className, clazz.getClassName());
	}
	
	@Test
	public void testSetImports(){
		List<String> imports = ListUtil.createList("com.example.*", "com.github.tadukoo.*");
		clazz = new TestJavaClassBuilder()
				.className(className)
				.imports(imports)
				.build();
		assertEquals(imports, clazz.getImports());
	}
	
	@Test
	public void testSetSingleImport(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.singleImport("com.example.*")
				.build();
		List<String> imports = clazz.getImports();
		assertEquals(1, imports.size());
		assertEquals("com.example.*", imports.get(0));
	}
	
	@Test
	public void testSetStaticImports(){
		List<String> staticImports = ListUtil.createList("com.example.Test", "com.github.tadukoo.*");
		clazz = new TestJavaClassBuilder()
				.className(className)
				.staticImports(staticImports)
				.build();
		assertEquals(staticImports, clazz.getStaticImports());
	}
	
	@Test
	public void testSetSingleStaticImport(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.staticImport("com.github.tadukoo.*")
				.build();
		List<String> staticImports = clazz.getStaticImports();
		assertEquals(1, staticImports.size());
		assertEquals("com.github.tadukoo.*", staticImports.get(0));
	}
	
	@Test
	public void testSetJavadoc(){
		Javadoc doc = UneditableJavadoc.builder().build();
		clazz = new TestJavaClassBuilder()
				.className(className)
				.javadoc(doc)
				.build();
		assertEquals(doc, clazz.getJavadoc());
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = UneditableJavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = new TestJavaClassBuilder()
				.className(className)
				.annotations(annotations)
				.build();
		assertEquals(annotations, clazz.getAnnotations());
	}
	
	@Test
	public void testSetSingleAnnotation(){
		JavaAnnotation test = UneditableJavaAnnotation.builder().name("Test").build();
		clazz = new TestJavaClassBuilder()
				.className(className)
				.annotation(test)
				.build();
		List<JavaAnnotation> annotations = clazz.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testSetVisibility(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.visibility(Visibility.PRIVATE)
				.build();
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetIsStatic(){
		clazz = new TestJavaClassBuilder()
				.innerClass()
				.className(className)
				.isStatic(true)
				.build();
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testSetIsStaticNoParam(){
		clazz = new TestJavaClassBuilder()
				.innerClass()
				.className(className)
				.isStatic()
				.build();
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testSetSuperClassName(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.superClassName("AnotherClassName")
				.build();
		assertEquals("AnotherClassName", clazz.getSuperClassName());
	}
	
	@Test
	public void testSetInnerClasses(){
		List<JavaClass> classes = ListUtil.createList(new TestJavaClassBuilder().innerClass().className("AClass").build(),
				new TestJavaClassBuilder().innerClass().className("BClass").build());
		clazz = new TestJavaClassBuilder()
				.className("CClassName")
				.innerClasses(classes)
				.build();
		assertEquals(classes, clazz.getInnerClasses());
	}
	
	@Test
	public void testSet1InnerClass(){
		JavaClass class2 = new TestJavaClassBuilder().innerClass().className("AClass").build();
		clazz = new TestJavaClassBuilder()
				.className("BClassName")
				.innerClass(class2)
				.build();
		List<JavaClass> innerClasses = clazz.getInnerClasses();
		assertEquals(1, innerClasses.size());
		assertEquals(class2, innerClasses.get(0));
	}
	
	@Test
	public void testSetFields(){
		List<JavaField> fields = ListUtil.createList(UneditableJavaField.builder().type("int").name("test").build(),
				UneditableJavaField.builder().type("String").name("derp").build());
		clazz = new TestJavaClassBuilder()
				.className(className)
				.fields(fields)
				.build();
		assertEquals(fields, clazz.getFields());
	}
	
	@Test
	public void testSetField(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.field(UneditableJavaField.builder().type("int").name("test").build())
				.build();
		List<JavaField> fields = clazz.getFields();
		assertEquals(1, fields.size());
		JavaField field = fields.get(0);
		assertEquals(Visibility.NONE, field.getVisibility());
		assertEquals("int", field.getType());
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testSetMethods(){
		List<JavaMethod> methods = ListUtil.createList(UneditableJavaMethod.builder().returnType("int").build(),
				UneditableJavaMethod.builder().returnType("String").build());
		clazz = new TestJavaClassBuilder()
				.className(className)
				.methods(methods)
				.build();
		assertEquals(methods, clazz.getMethods());
	}
	
	@Test
	public void testSetMethod(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.method(UneditableJavaMethod.builder().returnType("int").name("someMethod").line("return 42;").build())
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
	public void testNullClassName(){
		try{
			clazz = new TestJavaClassBuilder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testInnerClassNotInnerClass(){
		try{
			JavaClass inner = new TestJavaClassBuilder()
					.className(className)
					.build();
			clazz = new TestJavaClassBuilder()
					.className("BClassName")
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Inner class 'AClassName' is not an inner class!", e.getMessage());
		}
	}
	
	@Test
	public void testOuterClassCantBeStatic(){
		try{
			clazz = new TestJavaClassBuilder()
					.isStatic()
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Only inner classes can be static!", e.getMessage());
		}
	}
	
	@Test
	public void testAllOuterClassErrors(){
		try{
			JavaClass inner = new TestJavaClassBuilder()
					.className(className)
					.build();
			clazz = new TestJavaClassBuilder()
					.isStatic()
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Only inner classes can be static!""", e.getMessage());
		}
	}
	
	@Test
	public void testNullClassNameInnerClass(){
		try{
			clazz = new TestJavaClassBuilder()
					.innerClass()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testInnerClassNotInnerClassInInnerClass(){
		try{
			JavaClass inner = new TestJavaClassBuilder()
					.className(className)
					.build();
			clazz = new TestJavaClassBuilder()
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
	public void testSetPackageNameInnerClass(){
		try{
			clazz = new TestJavaClassBuilder()
					.innerClass()
					.packageDeclaration(EditableJavaPackageDeclaration.builder()
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
	public void testSetImportInnerClass(){
		try{
			clazz = new TestJavaClassBuilder()
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
	public void testSetStaticImportInnerClass(){
		try{
			clazz = new TestJavaClassBuilder()
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
	public void testAllInnerClassBuilderErrors(){
		try{
			JavaClass inner = new TestJavaClassBuilder()
					.className(className)
					.build();
			clazz = new TestJavaClassBuilder()
					.innerClass()
					.packageDeclaration(EditableJavaPackageDeclaration.builder()
							.packageName("some.package")
							.build())
					.innerClass(inner)
					.singleImport("an.import")
					.staticImport("an.other.import")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Not allowed to have package declaration for an inner class!
					Not allowed to have imports for an inner class!
					Not allowed to have static imports for an inner class!""", e.getMessage());
		}
	}
}
