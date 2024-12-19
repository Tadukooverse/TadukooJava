package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotationBuilder;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineCommentBuilder;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineCommentBuilder;
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
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaClassTest<ClassType extends JavaClass>{
	
	private final Class<ClassType> theClazz;
	private final ThrowingSupplier<JavaClassBuilder<ClassType>, NoException> builder;
	private final ThrowingSupplier<JavaPackageDeclarationBuilder<?>, NoException> javaPackageDeclarationBuilder;
	private final ThrowingSupplier<JavaImportStatementBuilder<?>, NoException> javaImportStatementBuilder;
	private final ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder;
	private final ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder;
	private final ThrowingSupplier<JavaSingleLineCommentBuilder<?>, NoException> singleLineCommentBuilder;
	private final ThrowingSupplier<JavaMultiLineCommentBuilder<?>, NoException> multiLineCommentBuilder;
	private final ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder;
	private final ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder;
	
	protected ClassType clazz;
	protected String className;
	
	protected DefaultJavaClassTest(
			Class<ClassType> theClazz,
			ThrowingSupplier<JavaClassBuilder<ClassType>, NoException> builder,
			ThrowingSupplier<JavaPackageDeclarationBuilder<?>, NoException> javaPackageDeclarationBuilder,
			ThrowingSupplier<JavaImportStatementBuilder<?>, NoException> javaImportStatementBuilder,
			ThrowingSupplier<JavaAnnotationBuilder<?>, NoException> javaAnnotationBuilder,
			ThrowingSupplier<JavadocBuilder<?>, NoException> javadocBuilder,
			ThrowingSupplier<JavaSingleLineCommentBuilder<?>, NoException> singleLineCommentBuilder,
			ThrowingSupplier<JavaMultiLineCommentBuilder<?>, NoException> multiLineCommentBuilder,
			ThrowingSupplier<JavaFieldBuilder<?>, NoException> javaFieldBuilder,
			ThrowingSupplier<JavaMethodBuilder<?>, NoException> javaMethodBuilder){
		this.theClazz = theClazz;
		this.builder = builder;
		this.javaPackageDeclarationBuilder = javaPackageDeclarationBuilder;
		this.javaImportStatementBuilder = javaImportStatementBuilder;
		this.javaAnnotationBuilder = javaAnnotationBuilder;
		this.javadocBuilder = javadocBuilder;
		this.singleLineCommentBuilder = singleLineCommentBuilder;
		this.multiLineCommentBuilder = multiLineCommentBuilder;
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
		Assertions.assertEquals(JavaCodeTypes.CLASS, clazz.getJavaCodeType());
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
	public void testDefaultIsAbstract(){
		assertFalse(clazz.isAbstract());
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
	public void testGetInnerClassesMapNoInnerClasses(){
		assertTrue(clazz.getInnerClassesMap().isEmpty());
	}
	
	@Test
	public void testGetInnerClassesMapSingleInnerClass(){
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("BClassName")
				.build();
		clazz = builder.get()
				.innerClass(innerClass)
				.className(className)
				.build();
		Map<String, JavaClass> innerClassMap = clazz.getInnerClassesMap();
		assertEquals(1, innerClassMap.size());
		assertEquals(innerClass, innerClassMap.get("BClassName"));
	}
	
	@Test
	public void testGetInnerClassesMapTwoInnerClasses(){
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("BClassName")
				.build();
		JavaClass innerClass2 = builder.get()
				.innerClass()
				.className("CClassName")
				.build();
		clazz = builder.get()
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
		JavaField field = javaFieldBuilder.get()
				.type("String").name("test")
				.build();
		clazz = builder.get()
				.className(className)
				.field(field)
				.build();
		Map<String, JavaField> fieldMap = clazz.getFieldsMap();
		assertEquals(1, fieldMap.size());
		assertEquals(field, fieldMap.get("test"));
	}
	
	@Test
	public void testGetFieldsMapTwoFields(){
		JavaField field = javaFieldBuilder.get()
				.type("String").name("test")
				.build();
		JavaField field2 = javaFieldBuilder.get()
				.type("int").name("version")
				.build();
		clazz = builder.get()
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
		JavaMethod method = javaMethodBuilder.get()
				.returnType("String").name("getType")
				.build();
		clazz = builder.get()
				.className(className)
				.method(method)
				.build();
		Map<String, JavaMethod> methodMap = clazz.getMethodsMap();
		assertEquals(1, methodMap.size());
		assertEquals(method, methodMap.get("getType()"));
	}
	
	@Test
	public void testGetMethodsMapTwoMethods(){
		JavaMethod method = javaMethodBuilder.get()
				.returnType("String").name("getType")
				.build();
		JavaMethod method2 = javaMethodBuilder.get()
				.returnType("int").name("getVersion")
				.parameter("String type")
				.build();
		clazz = builder.get()
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
				.implementsInterfaceName("SomeInterface")
				.singleLineComment(singleLineCommentBuilder.get()
						.content("some comment")
						.build())
				.multiLineComment(multiLineCommentBuilder.get()
						.content("some content")
						.content("more comment")
						.build())
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
	public void testBuilderSetPackageName(){
		clazz = builder.get()
				.packageName("some.package")
				.className(className)
				.build();
		assertEquals(javaPackageDeclarationBuilder.get()
				.packageName("some.package")
				.build(), clazz.getPackageDeclaration());
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
	public void testBuilderImportName(){
		clazz = builder.get()
				.importName("com.example", false)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(javaImportStatementBuilder.get()
				.importName("com.example")
				.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderImportNameStatic(){
		clazz = builder.get()
				.importName("com.example", true)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(javaImportStatementBuilder.get()
				.isStatic().importName("com.example")
				.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportNames(){
		clazz = builder.get()
				.importNames(ListUtil.createList("com.example", "com.other"), false)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(
				javaImportStatementBuilder.get()
						.importName("com.example")
						.build(),
				javaImportStatementBuilder.get()
						.importName("com.other")
						.build()), clazz.getImportStatements());
	}
	
	@Test
	public void testBuilderSetImportNamesStatic(){
		clazz = builder.get()
				.importNames(ListUtil.createList("com.example", "com.other"), true)
				.className(className)
				.build();
		assertEquals(ListUtil.createList(
				javaImportStatementBuilder.get()
						.isStatic().importName("com.example")
						.build(),
				javaImportStatementBuilder.get()
						.isStatic().importName("com.other")
						.build()), clazz.getImportStatements());
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
	public void testBuilderSetIsAbstractNoParam(){
		clazz = builder.get()
				.isAbstract()
				.className(className)
				.build();
		assertTrue(clazz.isAbstract());
	}
	
	@Test
	public void testBuilderSetIsAbstract(){
		clazz = builder.get()
				.isAbstract(true)
				.className(className)
				.build();
		assertTrue(clazz.isAbstract());
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
	public void testBuilderSetImplementsInterfaceName(){
		clazz = builder.get()
				.className(className)
				.implementsInterfaceName("SomeInterface")
				.build();
		assertEquals(ListUtil.createList("SomeInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testBuilderSetMultipleImplementsInterfaceNames(){
		clazz = builder.get()
				.className(className)
				.implementsInterfaceNames(ListUtil.createList("SomeInterface", "SomeOtherInterface"))
				.build();
		assertEquals(ListUtil.createList("SomeInterface", "SomeOtherInterface"), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetSingleLineComments(){
		List<JavaSingleLineComment> comments = ListUtil.createList(
				singleLineCommentBuilder.get()
						.content("some comment")
						.build(),
				singleLineCommentBuilder.get()
						.content("some more comment")
						.build());
		clazz = builder.get()
				.className(className)
				.singleLineComments(comments)
				.build();
		assertEquals(comments, clazz.getSingleLineComments());
	}
	
	@Test
	public void testSet1SingleLineComment(){
		JavaSingleLineComment comment = singleLineCommentBuilder.get()
				.content("some comment")
				.build();
		clazz = builder.get()
				.className(className)
				.singleLineComment(comment)
				.build();
		List<JavaSingleLineComment> comments = clazz.getSingleLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
	}
	
	@Test
	public void testSet1SingleLineCommentByString(){
		JavaSingleLineComment comment = singleLineCommentBuilder.get()
				.content("some comment")
				.build();
		clazz = builder.get()
				.className(className)
				.singleLineComment("some comment")
				.build();
		List<JavaSingleLineComment> comments = clazz.getSingleLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
	}
	
	@Test
	public void testSetMultiLineComments(){
		List<JavaMultiLineComment> comments = ListUtil.createList(
				multiLineCommentBuilder.get()
						.content("some comment")
						.content("more content")
						.build(),
				multiLineCommentBuilder.get()
						.content("some more comment")
						.build());
		clazz = builder.get()
				.className(className)
				.multiLineComments(comments)
				.build();
		assertEquals(comments, clazz.getMultiLineComments());
	}
	
	@Test
	public void testSet1MultiLineComment(){
		JavaMultiLineComment comment = multiLineCommentBuilder.get()
				.content("some comment")
				.content("more content")
				.build();
		clazz = builder.get()
				.className(className)
				.multiLineComment(comment)
				.build();
		List<JavaMultiLineComment> comments = clazz.getMultiLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
	}
	
	@Test
	public void testSet1MultiLineCommentByStrings(){
		JavaMultiLineComment comment = multiLineCommentBuilder.get()
				.content("some comment")
				.content("more content")
				.build();
		clazz = builder.get()
				.className(className)
				.multiLineComment("some comment",
						"more content")
				.build();
		List<JavaMultiLineComment> comments = clazz.getMultiLineComments();
		assertEquals(1, comments.size());
		assertEquals(comment, comments.get(0));
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
		List<JavaMethod> methods = ListUtil.createList(javaMethodBuilder.get().returnType("int").name("test").build(),
				javaMethodBuilder.get().returnType("String").name("derp").build());
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
	public void testBuilderAbstractStatic(){
		try{
			clazz = builder.get()
					.className(className)
					.innerClass()
					.isAbstract().isStatic()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Can't be abstract and static!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAbstractFinal(){
		try{
			clazz = builder.get()
					.className(className)
					.isAbstract().isFinal()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Can't be abstract and final!", e.getMessage());
		}
	}
	
	@Test
	public void testBuilderAllAbstractErrors(){
		try{
			clazz = builder.get()
					.className(className)
					.innerClass()
					.isAbstract().isStatic().isFinal()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Can't be abstract and static!\n" +
					"Can't be abstract and final!", e.getMessage());
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
	public void testMissingInnerElementsOrderWhenSingleLineCommentsPresent(){
		try{
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.multiLineComment(multiLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.multiLineComment(multiLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.multiLineComment(multiLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.innerClass(builder.get()
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
			clazz = builder.get()
					.className(className)
					.innerClass(builder.get()
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
			clazz = builder.get()
					.className(className)
					.field(javaFieldBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.field(javaFieldBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.method(javaMethodBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.method(javaMethodBuilder.get()
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
			clazz = builder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some other comment")
							.build())
					.singleLineComment(singleLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some other comment")
							.build())
					.singleLineComment(singleLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.multiLineComment(multiLineCommentBuilder.get()
							.content("some comment")
							.build())
					.multiLineComment(multiLineCommentBuilder.get()
							.content("some other comment")
							.build())
					.multiLineComment(multiLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.multiLineComment(multiLineCommentBuilder.get()
							.content("some comment")
							.build())
					.multiLineComment(multiLineCommentBuilder.get()
							.content("some other comment")
							.build())
					.multiLineComment(multiLineCommentBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.innerClass(builder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.innerClass(builder.get()
							.innerClass()
							.className("BClassName")
							.build())
					.innerClass(builder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.field(javaFieldBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.field(javaFieldBuilder.get()
							.type("int").name("version")
							.build())
					.field(javaFieldBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.method(javaMethodBuilder.get()
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
			clazz = builder.get()
					.className(className)
					.singleLineComment(singleLineCommentBuilder.get()
							.content("some comment")
							.build())
					.method(javaMethodBuilder.get()
							.returnType("int").name("getVersion")
							.build())
					.method(javaMethodBuilder.get()
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
	public void testToStringWithImplementsInterface(){
		clazz = builder.get()
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
		clazz = builder.get()
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
	public void testToStringWithSingleLineComment(){
		clazz = builder.get()
				.className(className)
				.singleLineComment(singleLineCommentBuilder.get()
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
		clazz = builder.get()
				.className(className)
				.multiLineComment(multiLineCommentBuilder.get()
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
	public void testToStringWithInnerClassesSwapOrder(){
		clazz = builder.get()
				.className(className)
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
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
	public void testToStringWithFieldsSwapOrder(){
		clazz = builder.get()
				.className(className)
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
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
						.parameter("int test").line("return doSomething();").build())
				.build();
		String javaString = """
				class AClassName{
				\t
					AClassName(){ }
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
		clazz = builder.get()
				.className(className)
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int test").line("return doSomething();").build())
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
					AClassName(){ }
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMethodsOrderNotSpecified(){
		clazz = builder.get()
				.className(className)
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int test").line("return doSomething();").build())
				.innerElementsOrder(new ArrayList<>())
				.build();
		String javaString = """
				class AClassName{
				\t
					AClassName(){ }
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
	public void testToStringWithAbstract(){
		clazz = builder.get()
				.isAbstract()
				.className(className)
				.build();
		String javaString = """
				abstract class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithEverythingOrderNotSpecified(){
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
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int test").line("return doSomething();").build())
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
					AClassName(){ }
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
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.singleLineComment(singleLineCommentBuilder.get()
						.content("some comment")
						.build())
				.multiLineComment(multiLineCommentBuilder.get()
						.content("some comment")
						.content("more content")
						.build())
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
						.parameter("int test").line("return doSomething();").build())
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
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithEverythingReordered(){
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
				.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
				.singleLineComment(singleLineCommentBuilder.get()
						.content("some comment")
						.build())
				.multiLineComment(multiLineCommentBuilder.get()
						.content("some comment")
						.content("more content")
						.build())
				.innerClass(builder.get().innerClass().className("BClassName").build())
				.innerClass(builder.get().innerClass().className("CClassName").build())
				.field(javaFieldBuilder.get().type("int").name("test").build())
				.field(javaFieldBuilder.get().type("String").name("derp").build())
				.method(javaMethodBuilder.get().returnType(className).build())
				.method(javaMethodBuilder.get().returnType("String").name("getSomething")
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
						.parameter("int test").line("return doSomething();").build())
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
					AClassName(){ }
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
						.parameter("int test").line("return doSomething();").build())
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
						.parameter("int test").line("return doSomething();").build())
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
	
	@Test
	public void testToBuilderCode(){
		clazz = builder.get()
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeInnerClass(){
		clazz = builder.get()
				.innerClass()
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.innerClass()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithPackageName(){
		clazz = builder.get()
				.packageName("com.example")
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.packageName(\"com.example\")\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithRegularImport(){
		clazz = builder.get()
				.importName("com.something.*", false)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.importName(\"com.something.*\", false)\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithStaticImport(){
		clazz = builder.get()
				.importName("com.something.else.*", true)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.importName(\"com.something.else.*\", true)\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithRegularAndStaticImports(){
		clazz = builder.get()
				.importName("com.something.*", false)
				.importName("com.something.else.*", true)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.importName(\"com.something.*\", false)\n" +
				"\t\t.importName(\"com.something.else.*\", true)\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithJavadoc(){
		Javadoc javadoc = javadocBuilder.get()
				.build();
		clazz = builder.get()
				.javadoc(javadoc)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.javadoc(" +
				javadoc.toBuilderCode().replace(JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) +
				")\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithOneAnnotation(){
		JavaAnnotation annotation = javaAnnotationBuilder.get()
				.name("Test")
				.build();
		clazz = builder.get()
				.annotation(annotation)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.annotation(" +
				annotation.toBuilderCode().replace(JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) +
				")\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoAnnotations(){
		JavaAnnotation annotation = javaAnnotationBuilder.get()
				.name("Test")
				.build();
		JavaAnnotation annotation2 = javaAnnotationBuilder.get()
				.name("Derp")
				.build();
		clazz = builder.get()
				.annotation(annotation)
				.annotation(annotation2)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.annotation(" +
				annotation.toBuilderCode().replace(JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) +
				")\n" +
				"\t\t.annotation(" +
				annotation2.toBuilderCode().replace(JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) +
				")\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithVisibility(){
		clazz = builder.get()
				.visibility(Visibility.PUBLIC)
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.visibility(Visibility.PUBLIC)\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithAbstract(){
		clazz = builder.get()
				.isAbstract()
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.isAbstract()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithStatic(){
		clazz = builder.get()
				.innerClass()
				.isStatic()
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.innerClass()\n" +
				"\t\t.isStatic()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithFinal(){
		clazz = builder.get()
				.isFinal()
				.className(className)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.isFinal()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithExtends(){
		clazz = builder.get()
				.className(className)
				.superClassName("Something")
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.superClassName(\"Something\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithOneImplements(){
		clazz = builder.get()
				.className(className)
				.implementsInterfaceName("Test")
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.implementsInterfaceName(\"Test\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoImplements(){
		clazz = builder.get()
				.className(className)
				.implementsInterfaceName("Test")
				.implementsInterfaceName("Blah")
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.implementsInterfaceName(\"Test\")\n" +
				"\t\t.implementsInterfaceName(\"Blah\")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithSingleLineComment(){
		JavaSingleLineComment singleLineComment = singleLineCommentBuilder.get()
				.content("something useful")
				.build();
		clazz = builder.get()
				.className(className)
				.singleLineComment(singleLineComment)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.singleLineComment(" +
				singleLineComment.getContent() + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoSingleLineComments(){
		JavaSingleLineComment singleLineComment = singleLineCommentBuilder.get()
				.content("something useful")
				.build();
		JavaSingleLineComment singleLineComment2 = singleLineCommentBuilder.get()
				.content("something else useful")
				.build();
		clazz = builder.get()
				.className(className)
				.singleLineComment(singleLineComment)
				.singleLineComment(singleLineComment2)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.singleLineComment(" +
				singleLineComment.getContent() + ")\n" +
				"\t\t.singleLineComment(" +
				singleLineComment2.getContent() + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithMultiLineComment(){
		JavaMultiLineComment multiLineComment = multiLineCommentBuilder.get()
				.content("something useful")
				.build();
		clazz = builder.get()
				.className(className)
				.multiLineComment(multiLineComment)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.multiLineComment(" +
				multiLineComment.getContent().get(0) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoMultiLineComments(){
		JavaMultiLineComment multiLineComment = multiLineCommentBuilder.get()
				.content("something useful")
				.build();
		JavaMultiLineComment multiLineComment2 = multiLineCommentBuilder.get()
				.content("something else useful")
				.build();
		clazz = builder.get()
				.className(className)
				.multiLineComment(multiLineComment)
				.multiLineComment(multiLineComment2)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.multiLineComment(" +
				multiLineComment.getContent().get(0) + ")\n" +
				"\t\t.multiLineComment(" +
				multiLineComment2.getContent().get(0) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithInnerClass(){
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("Test")
				.build();
		clazz = builder.get()
				.className(className)
				.innerClass(innerClass)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.innerClass(" +
				innerClass.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoInnerClasses(){
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("Test")
				.build();
		JavaClass innerClass2 = builder.get()
				.innerClass()
				.className("Test2")
				.build();
		clazz = builder.get()
				.className(className)
				.innerClass(innerClass)
				.innerClass(innerClass2)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.innerClass(" +
				innerClass.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.innerClass(" +
				innerClass2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithField(){
		JavaField field = javaFieldBuilder.get()
				.type("String").name("version")
				.build();
		clazz = builder.get()
				.className(className)
				.field(field)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.field(" +
				field.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoFields(){
		JavaField field = javaFieldBuilder.get()
				.type("String").name("version")
				.build();
		JavaField field2 = javaFieldBuilder.get()
				.type("int").name("something")
				.build();
		clazz = builder.get()
				.className(className)
				.field(field)
				.field(field2)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.field(" +
				field.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.field(" +
				field2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithMethod(){
		JavaMethod method = javaMethodBuilder.get()
				.returnType(className)
				.build();
		clazz = builder.get()
				.className(className)
				.method(method)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.method(" +
				method.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithTwoMethods(){
		JavaMethod method = javaMethodBuilder.get()
				.returnType(className)
				.build();
		JavaMethod method2 = javaMethodBuilder.get()
				.returnType("int").name("getVersion")
				.build();
		clazz = builder.get()
				.className(className)
				.method(method)
				.method(method2)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.method(" +
				method.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.method(" +
				method2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithInnerStuffOrdered(){
		JavaSingleLineComment singleLineComment = singleLineCommentBuilder.get()
				.content("something useful")
				.build();
		JavaSingleLineComment singleLineComment2 = singleLineCommentBuilder.get()
				.content("something else useful")
				.build();
		JavaMultiLineComment multiLineComment = multiLineCommentBuilder.get()
				.content("something useful")
				.build();
		JavaMultiLineComment multiLineComment2 = multiLineCommentBuilder.get()
				.content("something useful")
				.content("something else useful")
				.build();
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("Test")
				.build();
		JavaClass innerClass2 = builder.get()
				.innerClass()
				.className("Test2")
				.build();
		JavaField field = javaFieldBuilder.get()
				.type("String").name("version")
				.build();
		JavaField field2 = javaFieldBuilder.get()
				.type("int").name("something")
				.build();
		JavaMethod method = javaMethodBuilder.get()
				.returnType(className)
				.build();
		JavaMethod method2 = javaMethodBuilder.get()
				.returnType("int").name("getVersion")
				.build();
		clazz = builder.get()
				.className(className)
				.multiLineComment(multiLineComment2)
				.method(method2)
				.field(field)
				.singleLineComment(singleLineComment2)
				.method(method)
				.innerClass(innerClass2)
				.multiLineComment(multiLineComment)
				.field(field2)
				.singleLineComment(singleLineComment)
				.innerClass(innerClass)
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.multiLineComment(" +
				multiLineComment2.getContent().get(0) + ", \n\t\t\t\t" + multiLineComment2.getContent().get(1) + ")\n" +
				"\t\t.method(" +
				method2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.field(" +
				field.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.singleLineComment(" +
				singleLineComment2.getContent() + ")\n" +
				"\t\t.method(" +
				method.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.innerClass(" +
				innerClass2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.multiLineComment(" +
				multiLineComment.getContent().get(0) + ")\n" +
				"\t\t.field(" +
				field2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.singleLineComment(" +
				singleLineComment.getContent() + ")\n" +
				"\t\t.innerClass(" +
				innerClass.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeWithInnerStuffNotOrdered(){
		JavaClass innerClass = builder.get()
				.innerClass()
				.className("Test")
				.build();
		JavaClass innerClass2 = builder.get()
				.innerClass()
				.className("Test2")
				.build();
		JavaField field = javaFieldBuilder.get()
				.type("String").name("version")
				.build();
		JavaField field2 = javaFieldBuilder.get()
				.type("int").name("something")
				.build();
		JavaMethod method = javaMethodBuilder.get()
				.returnType(className)
				.build();
		JavaMethod method2 = javaMethodBuilder.get()
				.returnType("int").name("getVersion")
				.build();
		clazz = builder.get()
				.className(className)
				.method(method)
				.field(field)
				.method(method2)
				.innerClass(innerClass)
				.field(field2)
				.innerClass(innerClass2)
				.innerElementsOrder(new ArrayList<>())
				.build();
		assertEquals(theClazz.getSimpleName() + ".builder()\n" +
				"\t\t.className(\"" + className + "\")\n" +
				"\t\t.innerClass(" +
				innerClass.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.innerClass(" +
				innerClass2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.field(" +
				field.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.field(" +
				field2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.method(" +
				method.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.method(" +
				method2.toBuilderCode().replace(
						JavaCodeType.NEWLINE_WITH_2_TABS, JavaCodeType.NEWLINE_WITH_4_TABS) + ")\n" +
				"\t\t.build()", clazz.toBuilderCode());
	}
}
