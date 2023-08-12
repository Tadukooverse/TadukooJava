package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
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
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
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
			return new TestJavaClass(false, isInnerClass, packageDeclaration, importStatements,
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
		assertEquals(Visibility.NONE, clazz.getVisibility());
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
	public void testDefaultImplementsInterfaces(){
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testDefaultSingleLineComments(){
		assertNotNull(clazz.getSingleLineComments());
		assertTrue(clazz.getSingleLineComments().isEmpty());
	}
	
	@Test
	public void testDefaultMultiLineComments(){
		assertNotNull(clazz.getMultiLineComments());
		assertTrue(clazz.getMultiLineComments().isEmpty());
	}
	
	@Test
	public void testDefaultFields(){
		assertNotNull(clazz.getFields());
		assertTrue(clazz.getFields().isEmpty());
	}
	
	@Test
	public void testDefaultMethods(){
		assertNotNull(clazz.getMethods());
		assertTrue(clazz.getMethods().isEmpty());
	}
	
	@Test
	public void testDefaultInnerElementsOrder(){
		assertNotNull(clazz.getInnerElementsOrder());
		assertTrue(clazz.getInnerElementsOrder().isEmpty());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaClass otherClass = new TestJavaClassBuilder()
				.packageDeclaration(UneditableJavaPackageDeclaration.builder()
						.packageName("some.package")
						.build())
				.importStatement(UneditableJavaImportStatement.builder()
						.importName("some.classname")
						.build())
				.javadoc(UneditableJavadoc.builder()
						.build())
				.annotation(UneditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className(className)
				.superClassName("BClassName")
				.implementsInterfaceName("SomeInterface")
				.singleLineComment(UneditableJavaSingleLineComment.builder()
						.content("some comment")
						.build())
				.multiLineComment(UneditableJavaMultiLineComment.builder()
						.content("some content")
						.content("more comment")
						.build())
				.innerClass(new TestJavaClassBuilder()
						.innerClass()
						.className("CClassName")
						.build())
				.field(UneditableJavaField.builder()
						.type("String").name("test")
						.build())
				.method(UneditableJavaMethod.builder()
						.returnType("String").name("type")
						.build())
				.build();
		clazz = new TestJavaClassBuilder()
				.copy(otherClass)
				.build();
		assertEquals(otherClass, clazz);
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
	public void testSetPackageDeclaration(){
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
	public void testBuilderSetPackageName(){
		clazz = new TestJavaClassBuilder()
				.packageName("some.package")
				.className(className)
				.build();
		assertEquals(UneditableJavaPackageDeclaration.builder()
				.packageName("some.package")
				.build(), clazz.getPackageDeclaration());
	}
	
	@Test
	public void testSetClassName(){
		assertEquals(className, clazz.getClassName());
	}
	
	@Test
	public void testBuilderSingleImportStatement(){
		JavaImportStatement importStatement = UneditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		clazz = new TestJavaClassBuilder()
				.importStatement(importStatement)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(importStatement), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportStatements(){
		List<JavaImportStatement> importStatements = ListUtil.createList(
				UneditableJavaImportStatement.builder()
						.importName("com.example")
						.build(),
				UneditableJavaImportStatement.builder()
						.isStatic()
						.importName("com.other")
						.build());
		clazz = new TestJavaClassBuilder()
				.importStatements(importStatements)
				.className(className)
				.build();
		assertEquals(importStatements, clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderImportName(){
		clazz = new TestJavaClassBuilder()
				.importName("com.example", false)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(UneditableJavaImportStatement.builder()
				.importName("com.example")
				.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderImportNameStatic(){
		clazz = new TestJavaClassBuilder()
				.importName("com.example", true)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(UneditableJavaImportStatement.builder()
				.isStatic().importName("com.example")
				.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportNames(){
		clazz = new TestJavaClassBuilder()
				.importNames(ListUtil.createList("com.example", "com.other"), false)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(
				UneditableJavaImportStatement.builder()
						.importName("com.example")
						.build(),
				UneditableJavaImportStatement.builder()
						.importName("com.other")
						.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportNamesStatic(){
		clazz = new TestJavaClassBuilder()
				.importNames(ListUtil.createList("com.example", "com.other"), true)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(
				UneditableJavaImportStatement.builder()
						.isStatic().importName("com.example")
						.build(),
				UneditableJavaImportStatement.builder()
						.isStatic().importName("com.other")
						.build()), clazz.getImportStatements());
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
	public void testSetIsStaticNoParam(){
		clazz = new TestJavaClassBuilder()
				.innerClass()
				.className(className)
				.isStatic()
				.build();
		assertTrue(clazz.isStatic());
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
	public void testSetIsFinalNoParam(){
		clazz = new TestJavaClassBuilder()
				.isFinal()
				.className(className)
				.build();
		assertTrue(clazz.isFinal());
	}
	
	@Test
	public void testSetIsFinal(){
		clazz = new TestJavaClassBuilder()
				.isFinal(true)
				.className(className)
				.build();
		assertTrue(clazz.isFinal());
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
	public void testSetImplementsInterfaceName(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.implementsInterfaceName("SomeInterface")
				.build();
		assertEquals(ListUtil.createList("SomeInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetMultipleImplementsInterfaceNames(){
		clazz = new TestJavaClassBuilder()
				.className(className)
				.implementsInterfaceNames(ListUtil.createList("SomeInterface", "SomeOtherInterface"))
				.build();
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetSingleLineComments(){
		List<JavaSingleLineComment> comments = ListUtil.createList(
				UneditableJavaSingleLineComment.builder()
						.content("some comment")
						.build(),
				UneditableJavaSingleLineComment.builder()
						.content("some more comment")
						.build());
		clazz = new TestJavaClassBuilder()
				.className(className)
				.singleLineComments(comments)
				.build();
		assertEquals(comments, clazz.getSingleLineComments());
	}
	
	@Test
	public void testSet1SingleLineComment(){
		JavaSingleLineComment comment = UneditableJavaSingleLineComment.builder()
				.content("some comment")
				.build();
		clazz = new TestJavaClassBuilder()
				.className(className)
				.singleLineComment(comment)
				.build();
		List<JavaSingleLineComment> comments = clazz.getSingleLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
	}
	
	@Test
	public void testSetMultiLineComments(){
		List<JavaMultiLineComment> comments = ListUtil.createList(
				UneditableJavaMultiLineComment.builder()
						.content("some comment")
						.content("more content")
						.build(),
				UneditableJavaMultiLineComment.builder()
						.content("some more comment")
						.build());
		clazz = new TestJavaClassBuilder()
				.className(className)
				.multiLineComments(comments)
				.build();
		assertEquals(comments, clazz.getMultiLineComments());
	}
	
	@Test
	public void testSet1MultiLineComment(){
		JavaMultiLineComment comment = UneditableJavaMultiLineComment.builder()
				.content("some comment")
				.content("more content")
				.build();
		clazz = new TestJavaClassBuilder()
				.className(className)
				.multiLineComment(comment)
				.build();
		List<JavaMultiLineComment> comments = clazz.getMultiLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
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
		List<JavaMethod> methods = ListUtil.createList(UneditableJavaMethod.builder()
						.returnType("int").name("test").build(),
				UneditableJavaMethod.builder().returnType("String").name("derp").build());
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
					.importStatement(EditableJavaImportStatement.builder()
							.importName("some.name")
							.build())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Not allowed to have import statements for an inner class!", e.getMessage());
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
					.importStatement(EditableJavaImportStatement.builder()
							.importName("some.name")
							.build())
					.innerClass(inner)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Must specify className!
					Inner class 'AClassName' is not an inner class!
					Not allowed to have package declaration for an inner class!
					Not allowed to have import statements for an inner class!""", e.getMessage());
		}
	}
	
	@Test
	public void testMissingInnerElementsOrderWhenSingleLineCommentsPresent(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(UneditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.innerElementsOrder(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("innerElementsOrder is required when comments are present!", e.getMessage());
		}
	}
	
	@Test
	public void testMissingInnerElementsOrderWhenMultiLineCommentsPresent(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.multiLineComment(UneditableJavaMultiLineComment.builder()
							.content("some comment")
							.content("more content")
							.build())
					.innerElementsOrder(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("innerElementsOrder is required when comments are present!", e.getMessage());
		}
	}
	
	@Test
	public void testMissingInnerElementsOrderWhenBothTypesOfCommentsPresent(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(UneditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.multiLineComment(UneditableJavaMultiLineComment.builder()
							.content("some comment")
							.content("more content")
							.build())
					.innerElementsOrder(new ArrayList<>())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("innerElementsOrder is required when comments are present!", e.getMessage());
		}
	}
	
	@Test
	public void testSpecifiedTooManySingleLineCommentsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null),
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Specified more single-line comments in innerElementsOrder than we have!", e.getMessage());
		}
	}
	
	@Test
	public void testSpecifiedTooManyMultiLineCommentsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some comment")
							.content("more content")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null),
							Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Specified more multi-line comments in innerElementsOrder than we have!", e.getMessage());
		}
	}
	
	@Test
	public void testReusedInnerClassNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.innerClass(new TestJavaClassBuilder()
							.innerClass()
							.className("BClassName")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.CLASS, "BClassName"),
							Pair.of(JavaCodeTypes.CLASS, "BClassName")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Already used inner class named: BClassName", e.getMessage());
		}
	}
	
	@Test
	public void testUnknownInnerClassNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.innerClass(new TestJavaClassBuilder()
							.innerClass()
							.className("BClassName")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.CLASS, "BClassName"),
							Pair.of(JavaCodeTypes.CLASS, "CClassName")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown inner class name: CClassName", e.getMessage());
		}
	}
	
	@Test
	public void testReusedFieldNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.field(EditableJavaField.builder()
							.type("int").name("test")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.FIELD, "test"),
							Pair.of(JavaCodeTypes.FIELD, "test")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Already used field named: test", e.getMessage());
		}
	}
	
	@Test
	public void testUnknownFieldNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.field(EditableJavaField.builder()
							.type("int").name("test")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.FIELD, "test"),
							Pair.of(JavaCodeTypes.FIELD, "derp")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown field name: derp", e.getMessage());
		}
	}
	
	@Test
	public void testReusedMethodNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.method(EditableJavaMethod.builder()
							.returnType("int").name("getVersion")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
							Pair.of(JavaCodeTypes.METHOD, "getVersion()")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Already used method named: getVersion()", e.getMessage());
		}
	}
	
	@Test
	public void testUnknownMethodNameInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.method(EditableJavaMethod.builder()
							.returnType("int").name("getVersion")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
							Pair.of(JavaCodeTypes.METHOD, "getSomething()")
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown method name: getSomething()", e.getMessage());
		}
	}
	
	@Test
	public void testUnknownInnerElementType(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.innerElementsOrder(ListUtil.createList(Pair.of(JavaCodeTypes.ANNOTATION, "something")))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown inner element type: " + JavaCodeTypes.ANNOTATION.getStandardName(),
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedOneSingleLineCommentInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some other comment")
							.build())
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some third comment")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null),
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Missed 1 single-line comments in innerElementsOrder!", e.getMessage());
		}
	}
	
	@Test
	public void testMissedTwoSingleLineCommentsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some other comment")
							.build())
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some third comment")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Missed 2 single-line comments in innerElementsOrder!", e.getMessage());
		}
	}
	
	@Test
	public void testMissedOneMultiLineCommentInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some comment")
							.build())
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some other comment")
							.build())
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some third comment")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null),
							Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Missed 1 multi-line comments in innerElementsOrder!", e.getMessage());
		}
	}
	
	@Test
	public void testMissedTwoMultiLineCommentsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some comment")
							.build())
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some other comment")
							.build())
					.multiLineComment(EditableJavaMultiLineComment.builder()
							.content("some third comment")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Missed 2 multi-line comments in innerElementsOrder!", e.getMessage());
		}
	}
	
	@Test
	public void testMissedOneInnerClassInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.innerClass(new TestJavaClassBuilder()
							.innerClass()
							.className("BClassName")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following inner classes were not specified in innerElementsOrder: BClassName",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedTwoInnerClassesInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.innerClass(new TestJavaClassBuilder()
							.innerClass()
							.className("BClassName")
							.build())
					.innerClass(new TestJavaClassBuilder()
							.innerClass()
							.className("CClassName")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following inner classes were not specified in innerElementsOrder: BClassName,CClassName",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedOneFieldInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.field(EditableJavaField.builder()
							.type("int").name("version")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following fields were not specified in innerElementsOrder: version",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedTwoFieldsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.field(EditableJavaField.builder()
							.type("int").name("version")
							.build())
					.field(EditableJavaField.builder()
							.type("String").name("test")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following fields were not specified in innerElementsOrder: version,test",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedOneMethodInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.method(EditableJavaMethod.builder()
							.returnType("int").name("getVersion")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following methods were not specified in innerElementsOrder: getVersion()",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissedTwoMethodsInnerElementsOrder(){
		try{
			clazz = new TestJavaClassBuilder()
					.className(className)
					.singleLineComment(EditableJavaSingleLineComment.builder()
							.content("some comment")
							.build())
					.method(EditableJavaMethod.builder()
							.returnType("int").name("getVersion")
							.build())
					.method(EditableJavaMethod.builder()
							.returnType("String").name("getTest")
							.build())
					.innerElementsOrder(ListUtil.createList(
							Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
					))
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The following methods were not specified in innerElementsOrder: getVersion(),getTest()",
					e.getMessage());
		}
	}
}
