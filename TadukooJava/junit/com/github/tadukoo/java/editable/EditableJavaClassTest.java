package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.DefaultJavaClassTest;
import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.JavaField;
import com.github.tadukoo.java.JavaMethod;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.uneditable.UneditableJavaAnnotation;
import com.github.tadukoo.java.uneditable.UneditableJavaClass;
import com.github.tadukoo.java.uneditable.UneditableJavaField;
import com.github.tadukoo.java.uneditable.UneditableJavaMethod;
import com.github.tadukoo.java.uneditable.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EditableJavaClassTest extends DefaultJavaClassTest<EditableJavaClass>{
	
	public EditableJavaClassTest(){
		super(EditableJavaClass::builder, EditableJavaAnnotation::builder, EditableJavadoc::builder,
				EditableJavaField::builder, EditableJavaMethod::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(clazz.isEditable());
	}
	
	@Test
	public void testBuilderUneditableJavadocError(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.javadoc(UneditableJavadoc.builder().build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("javadoc is not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableAnnotationError(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableInnerClassError(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.innerClass(UneditableJavaClass.builder()
							.innerClass()
							.className(className)
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some inner classes are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableFieldError(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.field(UneditableJavaField.builder()
							.type("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some fields are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableMethodError(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.method(UneditableJavaMethod.builder()
							.returnType("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some methods are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderSpecificErrors(){
		try{
			EditableJavaClass.builder()
					.packageName(packageName).className(className)
					.javadoc(UneditableJavadoc.builder().build())
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.innerClass(UneditableJavaClass.builder()
							.innerClass()
							.className(className)
							.build())
					.field(UneditableJavaField.builder()
							.type("String").name("test")
							.build())
					.method(UneditableJavaMethod.builder()
							.returnType("String").name("test")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					javadoc is not editable in this editable JavaClass
					some annotations are not editable in this editable JavaClass
					some inner classes are not editable in this editable JavaClass
					some fields are not editable in this editable JavaClass
					some methods are not editable in this editable JavaClass""", e.getMessage());
		}
	}
	
	@Test
	public void testSetInnerClass(){
		assertFalse(clazz.isInnerClass());
		clazz.setInnerClass(false);
		assertFalse(clazz.isInnerClass());
		clazz.setInnerClass(true);
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testSetPackageName(){
		assertEquals(packageName, clazz.getPackageName());
		clazz.setPackageName("some.random.package.name");
		assertEquals("some.random.package.name", clazz.getPackageName());
	}
	
	@Test
	public void testAddImport(){
		assertEquals(new ArrayList<>(), clazz.getImports());
		clazz.addImport("some.import");
		assertEquals(ListUtil.createList("some.import"), clazz.getImports());
		clazz.addImport("some.other.import");
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getImports());
	}
	
	@Test
	public void testAddImports(){
		assertEquals(new ArrayList<>(), clazz.getImports());
		clazz.addImports(ListUtil.createList("some.import", "some.other.import"));
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getImports());
		clazz.addImports(ListUtil.createList("com.github.tadukoo", "derp.yep"));
		assertEquals(ListUtil.createList("some.import", "some.other.import", "com.github.tadukoo", "derp.yep"),
				clazz.getImports());
	}
	
	@Test
	public void testSetImports(){
		assertEquals(new ArrayList<>(), clazz.getImports());
		clazz.setImports(ListUtil.createList("some.import", "some.other.import"));
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getImports());
		clazz.setImports(ListUtil.createList("com.github.tadukoo", "derp.yep"));
		assertEquals(ListUtil.createList("com.github.tadukoo", "derp.yep"), clazz.getImports());
	}
	
	@Test
	public void testAddStaticImport(){
		assertEquals(new ArrayList<>(), clazz.getStaticImports());
		clazz.addStaticImport("some.import");
		assertEquals(ListUtil.createList("some.import"), clazz.getStaticImports());
		clazz.addStaticImport("some.other.import");
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getStaticImports());
	}
	
	@Test
	public void testAddStaticImports(){
		assertEquals(new ArrayList<>(), clazz.getStaticImports());
		clazz.addStaticImports(ListUtil.createList("some.import", "some.other.import"));
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getStaticImports());
		clazz.addStaticImports(ListUtil.createList("com.github.tadukoo", "derp.yep"));
		assertEquals(ListUtil.createList("some.import", "some.other.import", "com.github.tadukoo", "derp.yep"),
				clazz.getStaticImports());
	}
	
	@Test
	public void testSetStaticImports(){
		assertEquals(new ArrayList<>(), clazz.getStaticImports());
		clazz.setStaticImports(ListUtil.createList("some.import", "some.other.import"));
		assertEquals(ListUtil.createList("some.import", "some.other.import"), clazz.getStaticImports());
		clazz.setStaticImports(ListUtil.createList("com.github.tadukoo", "derp.yep"));
		assertEquals(ListUtil.createList("com.github.tadukoo", "derp.yep"), clazz.getStaticImports());
	}
	
	@Test
	public void testSetJavadoc(){
		assertNull(clazz.getJavadoc());
		clazz.setJavadoc(EditableJavadoc.builder().build());
		assertEquals(EditableJavadoc.builder().build(), clazz.getJavadoc());
	}
	
	@Test
	public void testSetJavadocUneditable(){
		try{
			clazz.setJavadoc(UneditableJavadoc.builder().build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		assertEquals(new ArrayList<>(), clazz.getAnnotations());
		clazz.addAnnotation(test);
		assertEquals(ListUtil.createList(test), clazz.getAnnotations());
		clazz.addAnnotation(derp);
		assertEquals(ListUtil.createList(test, derp), clazz.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationUneditable(){
		try{
			clazz.addAnnotation(UneditableJavaAnnotation.builder().name("Test").build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), clazz.getAnnotations());
		clazz.addAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), clazz.getAnnotations());
		clazz.addAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(test, derp, blah, yep), clazz.getAnnotations());
	}
	
	@Test
	public void testAddAnnotationsUneditable(){
		try{
			clazz.addAnnotations(ListUtil.createList(
					UneditableJavaAnnotation.builder().name("Test").build(),
					UneditableJavaAnnotation.builder().name("Derp").build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = EditableJavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = EditableJavaAnnotation.builder().name("Derp").build();
		JavaAnnotation blah = EditableJavaAnnotation.builder().name("Blah").build();
		JavaAnnotation yep = EditableJavaAnnotation.builder().name("yep").build();
		assertEquals(new ArrayList<>(), clazz.getAnnotations());
		clazz.setAnnotations(ListUtil.createList(test, derp));
		assertEquals(ListUtil.createList(test, derp), clazz.getAnnotations());
		clazz.setAnnotations(ListUtil.createList(blah, yep));
		assertEquals(ListUtil.createList(blah, yep), clazz.getAnnotations());
	}
	
	@Test
	public void testSetAnnotationsUneditable(){
		try{
			clazz.setAnnotations(ListUtil.createList(
					UneditableJavaAnnotation.builder().name("Test").build(),
					UneditableJavaAnnotation.builder().name("Derp").build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
		clazz.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetStatic(){
		assertFalse(clazz.isStatic());
		clazz.setStatic(false);
		assertFalse(clazz.isStatic());
		clazz.setStatic(true);
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testSetClassName(){
		assertEquals(className, clazz.getClassName());
		clazz.setClassName("SomethingElse");
		assertEquals("SomethingElse", clazz.getClassName());
	}
	
	@Test
	public void testSetSuperClassName(){
		assertNull(clazz.getSuperClassName());
		clazz.setSuperClassName("SomethingElse");
		assertEquals("SomethingElse", clazz.getSuperClassName());
	}
	
	@Test
	public void testAddInnerClass(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.packageName("a.package").className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.packageName("package.b").className("Derp")
				.build();
		assertEquals(new ArrayList<>(), clazz.getInnerClasses());
		clazz.addInnerClass(clazz1);
		assertEquals(ListUtil.createList(clazz1), clazz.getInnerClasses());
		clazz.addInnerClass(clazz2);
		assertEquals(ListUtil.createList(clazz1, clazz2), clazz.getInnerClasses());
	}
	
	@Test
	public void testAddInnerClassUneditable(){
		try{
			clazz.addInnerClass(UneditableJavaClass.builder()
					.packageName("a.package").className("Test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testAddInnerClasses(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.packageName("a.package").className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.packageName("package.b").className("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.packageName("package.c").className("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.packageName("package.d").className("Yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getInnerClasses());
		clazz.addInnerClasses(ListUtil.createList(clazz1, clazz2));
		assertEquals(ListUtil.createList(clazz1, clazz2), clazz.getInnerClasses());
		clazz.addInnerClasses(ListUtil.createList(clazz3, clazz4));
		assertEquals(ListUtil.createList(clazz1, clazz2, clazz3, clazz4), clazz.getInnerClasses());
	}
	
	@Test
	public void testAddInnerClassesUneditable(){
		try{
			clazz.addInnerClasses(ListUtil.createList(
					UneditableJavaClass.builder()
							.packageName("a.package").className("Test")
							.build(),
					UneditableJavaClass.builder()
							.packageName("b.package").className("Derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testSetInnerClasses(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.packageName("a.package").className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.packageName("package.b").className("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.packageName("package.c").className("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.packageName("package.d").className("Yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getInnerClasses());
		clazz.setInnerClasses(ListUtil.createList(clazz1, clazz2));
		assertEquals(ListUtil.createList(clazz1, clazz2), clazz.getInnerClasses());
		clazz.setInnerClasses(ListUtil.createList(clazz3, clazz4));
		assertEquals(ListUtil.createList(clazz3, clazz4), clazz.getInnerClasses());
	}
	
	@Test
	public void testSetInnerClassesUneditable(){
		try{
			clazz.setInnerClasses(ListUtil.createList(
					UneditableJavaClass.builder()
							.packageName("a.package").className("Test")
							.build(),
					UneditableJavaClass.builder()
							.packageName("b.package").className("Derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testAddField(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("int").name("derp")
				.build();
		assertEquals(new ArrayList<>(), clazz.getFields());
		clazz.addField(field1);
		assertEquals(ListUtil.createList(field1), clazz.getFields());
		clazz.addField(field2);
		assertEquals(ListUtil.createList(field1, field2), clazz.getFields());
	}
	
	@Test
	public void testAddFieldUneditable(){
		try{
			clazz.addField(UneditableJavaField.builder()
					.type("String").name("test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Fields", e.getMessage());
		}
	}
	
	@Test
	public void testAddFields(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("int").name("derp")
				.build();
		JavaField field3 = EditableJavaField.builder()
				.type("String").name("blah")
				.build();
		JavaField field4 = EditableJavaField.builder()
				.type("double").name("yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getFields());
		clazz.addFields(ListUtil.createList(field1, field2));
		assertEquals(ListUtil.createList(field1, field2), clazz.getFields());
		clazz.addFields(ListUtil.createList(field3, field4));
		assertEquals(ListUtil.createList(field1, field2, field3, field4), clazz.getFields());
	}
	
	@Test
	public void testAddFieldsUneditable(){
		try{
			clazz.addFields(ListUtil.createList(
					UneditableJavaField.builder()
							.type("String").name("test")
							.build(),
					UneditableJavaField.builder()
							.type("int").name("derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Fields", e.getMessage());
		}
	}
	
	@Test
	public void testSetFields(){
		JavaField field1 = EditableJavaField.builder()
				.type("String").name("test")
				.build();
		JavaField field2 = EditableJavaField.builder()
				.type("int").name("derp")
				.build();
		JavaField field3 = EditableJavaField.builder()
				.type("String").name("blah")
				.build();
		JavaField field4 = EditableJavaField.builder()
				.type("double").name("yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getFields());
		clazz.setFields(ListUtil.createList(field1, field2));
		assertEquals(ListUtil.createList(field1, field2), clazz.getFields());
		clazz.setFields(ListUtil.createList(field3, field4));
		assertEquals(ListUtil.createList(field3, field4), clazz.getFields());
	}
	
	@Test
	public void testSetFieldsUneditable(){
		try{
			clazz.setFields(ListUtil.createList(
					UneditableJavaField.builder()
							.type("String").name("test")
							.build(),
					UneditableJavaField.builder()
							.type("int").name("derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Fields", e.getMessage());
		}
	}
	
	@Test
	public void testAddMethod(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("test")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("int").name("derp")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMethods());
		clazz.addMethod(method1);
		assertEquals(ListUtil.createList(method1), clazz.getMethods());
		clazz.addMethod(method2);
		assertEquals(ListUtil.createList(method1, method2), clazz.getMethods());
	}
	
	@Test
	public void testAddMethodUneditable(){
		try{
			clazz.addMethod(UneditableJavaMethod.builder()
					.returnType("String").name("test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Methods", e.getMessage());
		}
	}
	
	@Test
	public void testAddMethods(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("test")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("int").name("derp")
				.build();
		JavaMethod method3 = EditableJavaMethod.builder()
				.returnType("String").name("blah")
				.build();
		JavaMethod method4 = EditableJavaMethod.builder()
				.returnType("double").name("yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMethods());
		clazz.addMethods(ListUtil.createList(method1, method2));
		assertEquals(ListUtil.createList(method1, method2), clazz.getMethods());
		clazz.addMethods(ListUtil.createList(method3, method4));
		assertEquals(ListUtil.createList(method1, method2, method3, method4), clazz.getMethods());
	}
	
	@Test
	public void testAddMethodsUneditable(){
		try{
			clazz.addMethods(ListUtil.createList(
					UneditableJavaMethod.builder()
							.returnType("String").name("test")
							.build(),
					UneditableJavaMethod.builder()
							.returnType("int").name("derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Methods", e.getMessage());
		}
	}
	
	@Test
	public void testSetMethods(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("test")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("int").name("derp")
				.build();
		JavaMethod method3 = EditableJavaMethod.builder()
				.returnType("String").name("blah")
				.build();
		JavaMethod method4 = EditableJavaMethod.builder()
				.returnType("double").name("yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMethods());
		clazz.setMethods(ListUtil.createList(method1, method2));
		assertEquals(ListUtil.createList(method1, method2), clazz.getMethods());
		clazz.setMethods(ListUtil.createList(method3, method4));
		assertEquals(ListUtil.createList(method3, method4), clazz.getMethods());
	}
	
	@Test
	public void testSetMethodsUneditable(){
		try{
			clazz.setMethods(ListUtil.createList(
					UneditableJavaMethod.builder()
							.returnType("String").name("test")
							.build(),
					UneditableJavaMethod.builder()
							.returnType("int").name("derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Methods", e.getMessage());
		}
	}
}
