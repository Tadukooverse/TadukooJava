package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class EditableJavaClassTest extends DefaultJavaClassTest<EditableJavaClass>{
	
	public EditableJavaClassTest(){
		super(EditableJavaClass::builder, EditableJavaPackageDeclaration::builder, EditableJavaImportStatement::builder,
				EditableJavaAnnotation::builder, EditableJavadoc::builder,
				EditableJavaSingleLineComment::builder, EditableJavaMultiLineComment::builder,
				EditableJavaField::builder, EditableJavaMethod::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(clazz.isEditable());
	}
	
	@Test
	public void testBuilderUneditablePackageDeclarationError(){
		try{
			EditableJavaClass.builder()
					.packageDeclaration(UneditableJavaPackageDeclaration.builder()
							.packageName("some.package")
							.build())
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("package declaration is not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testAllBuilderUneditableImportStatementError(){
		try{
			EditableJavaClass.builder()
					.importStatement(UneditableJavaImportStatement.builder()
							.importName("some.import")
							.build())
					.className(className)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some import statements are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableJavadocError(){
		try{
			EditableJavaClass.builder()
					.className(className)
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
					.className(className)
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some annotations are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableSingleLineCommentError(){
		try{
			EditableJavaClass.builder()
					.className(className)
					.singleLineComment(UneditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some single-line comments are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableMultiLineCommentError(){
		try{
			EditableJavaClass.builder()
					.className(className)
					.multiLineComment(UneditableJavaMultiLineComment.builder()
							.content("some comment")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("some multi-line comments are not editable in this editable JavaClass", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderUneditableInnerClassError(){
		try{
			EditableJavaClass.builder()
					.className(className)
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
					.className(className)
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
					.className(className)
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
					.packageDeclaration(UneditableJavaPackageDeclaration.builder()
							.packageName("some.package")
							.build())
					.importStatement(UneditableJavaImportStatement.builder()
							.importName("some.import")
							.build())
					.className(className)
					.javadoc(UneditableJavadoc.builder().build())
					.annotation(UneditableJavaAnnotation.builder().name("Test").build())
					.singleLineComment(UneditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.multiLineComment(UneditableJavaMultiLineComment.builder()
							.content("some comment")
							.build())
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
					package declaration is not editable in this editable JavaClass
					some import statements are not editable in this editable JavaClass
					javadoc is not editable in this editable JavaClass
					some annotations are not editable in this editable JavaClass
					some single-line comments are not editable in this editable JavaClass
					some multi-line comments are not editable in this editable JavaClass
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
	public void testSetPackageDeclaration(){
		JavaPackageDeclaration packageDeclaration = EditableJavaPackageDeclaration.builder()
				.packageName("some.random.package.name")
				.build();
		assertNull(clazz.getPackageDeclaration());
		clazz.setPackageDeclaration(packageDeclaration);
		assertEquals(packageDeclaration, clazz.getPackageDeclaration());
	}
	
	@Test
	public void testSetPackageDeclarationUneditable(){
		try{
			clazz.setPackageDeclaration(UneditableJavaPackageDeclaration.builder()
					.packageName("some.package")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable package declaration", e.getMessage());
		}
	}
	
	@Test
	public void testSetPackageName(){
		assertNull(clazz.getPackageDeclaration());
		clazz.setPackageName("some.random.package.name");
		assertEquals(EditableJavaPackageDeclaration.builder()
				.packageName("some.random.package.name")
				.build(), clazz.getPackageDeclaration());
	}
	
	@Test
	public void testAddImportStatement(){
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.importName("org.test")
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.addImportStatement(importStatement);
		assertEquals(ListUtil.createList(importStatement), clazz.getImportStatements());
		clazz.addImportStatement(importStatement2);
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
	}
	
	@Test
	public void testAddImportStatementUneditable(){
		try{
			clazz.addImportStatement(UneditableJavaImportStatement.builder()
					.importName("com.example")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testAddImportStatements(){
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.importName("org.test")
				.build();
		JavaImportStatement importStatement3 = EditableJavaImportStatement.builder()
				.importName("java.whatever")
				.build();
		JavaImportStatement importStatement4 = EditableJavaImportStatement.builder()
				.importName("org.yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.addImportStatements(ListUtil.createList(importStatement, importStatement2));
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
		clazz.addImportStatements(ListUtil.createList(importStatement3, importStatement4));
		assertEquals(ListUtil.createList(importStatement, importStatement2, importStatement3, importStatement4),
				clazz.getImportStatements());
	}
	
	@Test
	public void testAddImportStatementsUneditable(){
		try{
			clazz.addImportStatements(ListUtil.createList(
					UneditableJavaImportStatement.builder()
							.importName("com.example")
							.build(),
					UneditableJavaImportStatement.builder()
							.importName("yep")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testSetImportStatements(){
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.importName("org.test")
				.build();
		JavaImportStatement importStatement3 = EditableJavaImportStatement.builder()
				.importName("java.whatever")
				.build();
		JavaImportStatement importStatement4 = EditableJavaImportStatement.builder()
				.importName("org.yep")
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.setImportStatements(ListUtil.createList(importStatement, importStatement2));
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
		clazz.setImportStatements(ListUtil.createList(importStatement3, importStatement4));
		assertEquals(ListUtil.createList(importStatement3, importStatement4), clazz.getImportStatements());
	}
	
	@Test
	public void testSetImportStatementsUneditable(){
		try{
			clazz.setImportStatements(ListUtil.createList(
					UneditableJavaImportStatement.builder()
							.importName("com.example")
							.build(),
					UneditableJavaImportStatement.builder()
							.importName("yep")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testAddImportName(){
		String importName1 = "com.example";
		String importName2 = "org.test";
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName(importName1)
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.isStatic().importName(importName2)
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.addImportName(importName1, false);
		assertEquals(ListUtil.createList(importStatement), clazz.getImportStatements());
		clazz.addImportName(importName2, true);
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
	}
	
	@Test
	public void testAddImportNames(){
		String importName1 = "com.example";
		String importName2 = "org.test";
		String importName3 = "java.whatever";
		String importName4 = "org.yep";
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName(importName1)
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.importName(importName2)
				.build();
		JavaImportStatement importStatement3 = EditableJavaImportStatement.builder()
				.isStatic().importName(importName3)
				.build();
		JavaImportStatement importStatement4 = EditableJavaImportStatement.builder()
				.isStatic().importName(importName4)
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.addImportNames(ListUtil.createList(importName1, importName2), false);
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
		clazz.addImportNames(ListUtil.createList(importName3, importName4), true);
		assertEquals(ListUtil.createList(importStatement, importStatement2, importStatement3, importStatement4),
				clazz.getImportStatements());
	}
	
	@Test
	public void testSetImportNames(){
		String importName1 = "com.example";
		String importName2 = "org.test";
		String importName3 = "java.whatever";
		String importName4 = "org.yep";
		JavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName(importName1)
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.importName(importName2)
				.build();
		JavaImportStatement importStatement3 = EditableJavaImportStatement.builder()
				.isStatic().importName(importName3)
				.build();
		JavaImportStatement importStatement4 = EditableJavaImportStatement.builder()
				.isStatic().importName(importName4)
				.build();
		assertEquals(new ArrayList<>(), clazz.getImportStatements());
		clazz.setImportNames(ListUtil.createList(importName1, importName2), false);
		assertEquals(ListUtil.createList(importStatement, importStatement2), clazz.getImportStatements());
		clazz.setImportNames(ListUtil.createList(importName3, importName4), true);
		assertEquals(ListUtil.createList(importStatement3, importStatement4), clazz.getImportStatements());
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
		assertEquals(Visibility.NONE, clazz.getVisibility());
		clazz.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetAbstract(){
		assertFalse(clazz.isAbstract());
		clazz.setAbstract(false);
		assertFalse(clazz.isAbstract());
		clazz.setAbstract(true);
		assertTrue(clazz.isAbstract());
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
	public void testSetFinal(){
		assertFalse(clazz.isFinal());
		clazz.setFinal(false);
		assertFalse(clazz.isFinal());
		clazz.setFinal(true);
		assertTrue(clazz.isFinal());
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
	public void testAddImplementsInterfaceName(){
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName("SomeInterface");
		assertEquals(ListUtil.createList("SomeInterface"), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName("SomeOtherInterface");
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddImplementsInterfaceNames(){
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNames(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface"), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNames(ListUtil.createList("AInterface", "BInterface"));
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface", "AInterface", "BInterface"),
				clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetImplementsInterfaceNames(){
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNames(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface"), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNames(ListUtil.createList("AInterface", "BInterface"));
		assertEquals(ListUtil.createList("AInterface", "BInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddSingleLineComment(){
		JavaSingleLineComment comment1 = EditableJavaSingleLineComment.builder()
				.content("some comment")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("some other comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getSingleLineComments());
		clazz.addSingleLineComment(comment1);
		assertEquals(ListUtil.createList(comment1), clazz.getSingleLineComments());
		clazz.addSingleLineComment(comment2);
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getSingleLineComments());
	}
	
	@Test
	public void testAddSingleLineCommentUneditable(){
		try{
			clazz.addSingleLineComment(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddSingleLineComments(){
		JavaSingleLineComment comment1 = EditableJavaSingleLineComment.builder()
				.content("some comment")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("some other comment")
				.build();
		JavaSingleLineComment comment3 = EditableJavaSingleLineComment.builder()
				.content("some 3rd comment")
				.build();
		JavaSingleLineComment comment4 = EditableJavaSingleLineComment.builder()
				.content("some other 4th comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getSingleLineComments());
		clazz.addSingleLineComments(ListUtil.createList(comment1, comment2));
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getSingleLineComments());
		clazz.addSingleLineComments(ListUtil.createList(comment3, comment4));
		assertEquals(ListUtil.createList(comment1, comment2, comment3, comment4), clazz.getSingleLineComments());
	}
	
	@Test
	public void testAddSingleLineCommentsUneditable(){
		try{
			clazz.addSingleLineComments(ListUtil.createList(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testSetSingleLineComments(){
		JavaSingleLineComment comment1 = EditableJavaSingleLineComment.builder()
				.content("some comment")
				.build();
		JavaSingleLineComment comment2 = EditableJavaSingleLineComment.builder()
				.content("some other comment")
				.build();
		JavaSingleLineComment comment3 = EditableJavaSingleLineComment.builder()
				.content("some 3rd comment")
				.build();
		JavaSingleLineComment comment4 = EditableJavaSingleLineComment.builder()
				.content("some other 4th comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getSingleLineComments());
		clazz.setSingleLineComments(ListUtil.createList(comment1, comment2));
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getSingleLineComments());
		clazz.setSingleLineComments(ListUtil.createList(comment3, comment4));
		assertEquals(ListUtil.createList(comment3, comment4), clazz.getSingleLineComments());
	}
	
	@Test
	public void testSetSingleLineCommentsUneditable(){
		try{
			clazz.setSingleLineComments(ListUtil.createList(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddMultiLineComment(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("some comment")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("some other comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMultiLineComments());
		clazz.addMultiLineComment(comment1);
		assertEquals(ListUtil.createList(comment1), clazz.getMultiLineComments());
		clazz.addMultiLineComment(comment2);
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getMultiLineComments());
	}
	
	@Test
	public void testAddMultiLineCommentUneditable(){
		try{
			clazz.addMultiLineComment(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddMultiLineComments(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("some comment")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("some other comment")
				.build();
		JavaMultiLineComment comment3 = EditableJavaMultiLineComment.builder()
				.content("some 3rd comment")
				.build();
		JavaMultiLineComment comment4 = EditableJavaMultiLineComment.builder()
				.content("some other 4th comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMultiLineComments());
		clazz.addMultiLineComments(ListUtil.createList(comment1, comment2));
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getMultiLineComments());
		clazz.addMultiLineComments(ListUtil.createList(comment3, comment4));
		assertEquals(ListUtil.createList(comment1, comment2, comment3, comment4), clazz.getMultiLineComments());
	}
	
	@Test
	public void testAddMultiLineCommentsUneditable(){
		try{
			clazz.addMultiLineComments(ListUtil.createList(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testSetMultiLineComments(){
		JavaMultiLineComment comment1 = EditableJavaMultiLineComment.builder()
				.content("some comment")
				.build();
		JavaMultiLineComment comment2 = EditableJavaMultiLineComment.builder()
				.content("some other comment")
				.build();
		JavaMultiLineComment comment3 = EditableJavaMultiLineComment.builder()
				.content("some 3rd comment")
				.build();
		JavaMultiLineComment comment4 = EditableJavaMultiLineComment.builder()
				.content("some other 4th comment")
				.build();
		assertEquals(new ArrayList<>(), clazz.getMultiLineComments());
		clazz.setMultiLineComments(ListUtil.createList(comment1, comment2));
		assertEquals(ListUtil.createList(comment1, comment2), clazz.getMultiLineComments());
		clazz.setMultiLineComments(ListUtil.createList(comment3, comment4));
		assertEquals(ListUtil.createList(comment3, comment4), clazz.getMultiLineComments());
	}
	
	@Test
	public void testSetMultiLineCommentsUneditable(){
		try{
			clazz.setMultiLineComments(ListUtil.createList(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddInnerClass(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Derp")
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
					.className("Test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testAddInnerClasses(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.className("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.className("Yep")
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
							.className("Test")
							.build(),
					UneditableJavaClass.builder()
							.className("Derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testSetInnerClasses(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.className("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.className("Yep")
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
							.className("Test")
							.build(),
					UneditableJavaClass.builder()
							.className("Derp")
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
	
	@Test
	public void testSetInnerElementsOrder(){
		List<Pair<JavaCodeTypes, String>> list1 = ListUtil.createList(
				Pair.of(JavaCodeTypes.METHOD, "getVersion()"));
		List<Pair<JavaCodeTypes, String>> list2 = ListUtil.createList(
				Pair.of(JavaCodeTypes.FIELD, "version"));
		assertEquals(new ArrayList<>(), clazz.getInnerElementsOrder());
		clazz.setInnerElementsOrder(list1);
		assertEquals(list1, clazz.getInnerElementsOrder());
		clazz.setInnerElementsOrder(list2);
		assertEquals(list2, clazz.getInnerElementsOrder());
	}
	
	@Test
	public void testToStringSkipsInvalidInnerElementType(){
		clazz = EditableJavaClass.builder()
				.className(className)
				.build();
		clazz.setInnerElementsOrder(ListUtil.createList(
				Pair.of(JavaCodeTypes.ANNOTATION, null)
		));
		assertEquals("""
				class AClassName{
				}
				""", clazz.toString());
	}
}
