package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertClassEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findClassDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestClassTest{
	
	@Test
	public void testFindClassDifferencesNone(){
		assertEquals(new ArrayList<>(), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findClassDifferences(
				null,
				null
		));
	}
	
	@Test
	public void testFindClassDifferencesClass1NullClass2Not(){
		assertEquals(ListUtil.createList("One of the classes is null, and the other isn't!"), findClassDifferences(
				null,
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesClass2NullClass1Not(){
		assertEquals(ListUtil.createList("One of the classes is null, and the other isn't!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				null
		));
	}
	
	@Test
	public void testFindClassDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findClassDifferences(
				UneditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesInnerClass(){
		assertEquals(ListUtil.createList("Inner Class is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.innerClass()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesPackageDeclaration(){
		assertEquals(ListUtil.createList("Package Declaration differs:\n" +
				"\tPackage Name is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.packageName("com.test")
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.packageName("com.example")
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImportStatementsLength(){
		assertEquals(ListUtil.createList("Import Statements length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.importName("com.example.Thing", false)
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImportStatementsLength1(){
		assertEquals(ListUtil.createList("Import Statements length is different!",
				"Import Statements differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.importName("com.example.Thing", false)
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImportStatementsContent(){
		assertEquals(ListUtil.createList("Import Statements differs on #1:\n" +
				"\tStatic is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.importName("com.example.Thing", false)
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.importName("com.example.Thing", true)
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesJavadoc(){
		assertEquals(ListUtil.createList("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!"""), findClassDifferences(
				EditableJavaClass.builder()
						.javadoc(EditableJavadoc.builder()
								.build())
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesAnnotationsLength(){
		assertEquals(ListUtil.createList("Annotations length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getTest")
								.build())
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getTest")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("getDerp")
								.build())
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesAnnotationsLength1(){
		assertEquals(ListUtil.createList("Annotations length is different!",
				"Annotations differs on #2!"), findClassDifferences(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getTest")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("getDerp")
								.build())
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getTest")
								.build())
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesAnnotationsContent(){
		assertEquals(ListUtil.createList("""
				Annotations differs on #1:
					Name is different!"""), findClassDifferences(
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getTest")
								.build())
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("getDerp")
								.build())
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesVisibility(){
		assertEquals(ListUtil.createList("Visibility is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.visibility(Visibility.PUBLIC)
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.visibility(Visibility.PRIVATE)
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesAbstract(){
		assertEquals(ListUtil.createList("Abstract is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.isAbstract()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesStatic(){
		assertEquals(ListUtil.createList("Static is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.innerClass()
						.isStatic()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.innerClass()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesFinal(){
		assertEquals(ListUtil.createList("Final is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.isFinal()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesClassName(){
		assertEquals(ListUtil.createList("Class Name is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Derp")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesSuperClassName(){
		assertEquals(ListUtil.createList("Super Class Name is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test").superClassName("Something")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImplementsInterfaceNames(){
		assertEquals(ListUtil.createList("Implements Interface Names differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("Something")
						.build(),
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("SomethingElse")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImplementsInterfaceNamesLength(){
		assertEquals(ListUtil.createList("Implements Interface Names length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("Something")
						.build(),
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesImplementsInterfaceNamesLength1Longer(){
		assertEquals(ListUtil.createList("Implements Interface Names length is different!",
				"Implements Interface Names differs on #2!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
						.build(),
				EditableJavaClass.builder()
						.className("Test").implementsInterfaceName("Something")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesSingleLineComments(){
		assertEquals(ListUtil.createList("Single Line Comments differs on #1:\n" +
				"\tContent is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("Something useful")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("Something different")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesSingleLineCommentsLength(){
		assertEquals(ListUtil.createList("Single Line Comments length is different!",
				"Inner Elements Order length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("Something different")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesSingleLineCommentsLength1Longer(){
		assertEquals(ListUtil.createList("Single Line Comments length is different!",
				"Single Line Comments differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.singleLineComment(EditableJavaSingleLineComment.builder()
								.content("Something different")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMultiLineComments(){
		assertEquals(ListUtil.createList("Multi Line Comments differs on #1:\n" +
				"\tContent differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("Something useful")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("Something different")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMultiLineCommentsLength(){
		assertEquals(ListUtil.createList("Multi Line Comments length is different!",
				"Inner Elements Order length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("Something different")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMultiLineCommentsLength1Longer(){
		assertEquals(ListUtil.createList("Multi Line Comments length is different!",
				"Multi Line Comments differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.multiLineComment(EditableJavaMultiLineComment.builder()
								.content("Something different")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesInnerClasses(){
		assertEquals(ListUtil.createList("Inner Classes differs on #1:\n" +
				"\tClass Name is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Something")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("SomethingElse")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesInnerClassesLength(){
		assertEquals(ListUtil.createList("Inner Classes length is different!",
				"Inner Elements Order length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Something")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesInnerClassesLength1Longer(){
		assertEquals(ListUtil.createList("Inner Classes length is different!",
				"Inner Classes differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.innerClass(EditableJavaClass.builder()
								.innerClass()
								.className("Something")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesFields(){
		assertEquals(ListUtil.createList("Fields differs on #1:\n" +
						"\tName is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("version")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("test")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesFieldsLength(){
		assertEquals(ListUtil.createList("Fields length is different!",
				"Inner Elements Order length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("test")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesFieldsLength1Longer(){
		assertEquals(ListUtil.createList("Fields length is different!",
				"Fields differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.field(EditableJavaField.builder()
								.type("String").name("test")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMethods(){
		assertEquals(ListUtil.createList("Methods differs on #1:\n" +
						"\tName is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("getVersion")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMethodsLength(){
		assertEquals(ListUtil.createList("Methods length is different!",
				"Inner Elements Order length is different!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build())
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesMethodsLength1Longer(){
		assertEquals(ListUtil.createList("Methods length is different!",
				"Methods differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!"), findClassDifferences(
				EditableJavaClass.builder()
						.className("Test")
						.method(EditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build())
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		));
	}
	
	@Test
	public void testFindClassDifferencesAll(){
		assertEquals(ListUtil.createList("Editable is different!",
				"Inner Class is different!",
				"Package Declaration differs:\n" +
						"\tOne of the package declarations is null, and the other isn't!",
				"Import Statements length is different!",
				"Import Statements differs on #1!",
				"Javadoc differs:\n" +
						"\tOne of the Javadocs is null, and the other isn't!",
				"Annotations length is different!",
				"""
					Annotations differs on #1:
						Editable is different!
						Name is different!""",
				"Annotations differs on #2!",
				"Visibility is different!",
				"Abstract is different!",
				"Static is different!",
				"Final is different!",
				"Class Name is different!",
				"Super Class Name is different!",
				"Implements Interface Names length is different!",
				"Implements Interface Names differs on #2!",
				"Single Line Comments length is different!",
				"Single Line Comments differs on #1!",
				"Multi Line Comments length is different!",
				"Multi Line Comments differs on #1!",
				"Inner Classes length is different!",
				"Inner Classes differs on #1!",
				"Fields length is different!",
				"Fields differs on #1!",
				"Methods length is different!",
				"Methods differs on #1!",
				"Inner Elements Order length is different!",
				"Inner Elements Order differs on #1!",
				"Inner Elements Order differs on #2!",
				"Inner Elements Order differs on #3!",
				"Inner Elements Order differs on #4!",
				"Inner Elements Order differs on #5!"), findClassDifferences(
				UneditableJavaClass.builder()
						.packageName("com.test")
						.importName("com.example.Thing", false)
						.javadoc(UneditableJavadoc.builder()
								.build())
						.annotation(UneditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.annotation(UneditableJavaAnnotation.builder()
								.name("Derp")
								.build())
						.visibility(Visibility.PUBLIC)
						.isAbstract()
						.className("Test").superClassName("Something")
						.implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
						.singleLineComment(UneditableJavaSingleLineComment.builder()
								.content("Something different")
								.build())
						.multiLineComment(UneditableJavaMultiLineComment.builder()
								.content("Something different")
								.build())
						.innerClass(UneditableJavaClass.builder()
								.innerClass()
								.className("Something")
								.build())
						.field(UneditableJavaField.builder()
								.type("String").name("test")
								.build())
						.method(UneditableJavaMethod.builder()
								.returnType("String").name("getTest")
								.build())
						.build(),
				EditableJavaClass.builder()
						.innerClass()
						.annotation(EditableJavaAnnotation.builder()
								.name("Derp")
								.build())
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.className("Derp")
						.implementsInterfaceName("Something")
						.build()
		));
	}
	
	@Test
	public void testAssertClassEqualsNone(){
		assertClassEquals(
				EditableJavaClass.builder()
						.className("Test")
						.build(),
				EditableJavaClass.builder()
						.className("Test")
						.build()
		);
	}
	
	@Test
	public void testAssertClassEqualsBothNull(){
		assertClassEquals(
				null,
				null
		);
	}
	
	@Test
	public void testAssertClassEqualsClass1NullClass2Not(){
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(null, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the classes is null, and the other isn't!",
					buildAssertError(null, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsClass2NullClass1Not(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the classes is null, and the other isn't!",
					buildAssertError(class1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsEditable(){
		JavaClass class1 = UneditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsInnerClass(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.innerClass()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Inner Class is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsPackageDeclaration(){
		JavaClass class1 = EditableJavaClass.builder()
				.packageName("com.test")
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.packageName("com.example")
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Package Declaration differs:\n" +
							"\tPackage Name is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImportStatementsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.importName("com.example.Thing", false)
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Import Statements length is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImportStatementsLength1(){
		JavaClass class1 = EditableJavaClass.builder()
				.importName("com.example.Thing", false)
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Import Statements length is different!
					Import Statements differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImportStatementsContent(){
		JavaClass class1 = EditableJavaClass.builder()
				.importName("com.example.Thing", false)
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.importName("com.example.Thing", true)
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Import Statements differs on #1:\n" +
							"\tStatic is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsJavadoc(){
		JavaClass class1 = EditableJavaClass.builder()
				.javadoc(EditableJavadoc.builder()
						.build())
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Javadoc differs:
					One of the Javadocs is null, and the other isn't!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsAnnotationsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Annotations length is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsAnnotationsLength1(){
		JavaClass class1 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Annotations length is different!
					Annotations differs on #2!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsAnnotationsContent(){
		JavaClass class1 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getTest")
						.build())
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.annotation(EditableJavaAnnotation.builder()
						.name("getDerp")
						.build())
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
				Annotations differs on #1:
					Name is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsVisibility(){
		JavaClass class1 = EditableJavaClass.builder()
				.visibility(Visibility.PUBLIC)
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.visibility(Visibility.PRIVATE)
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Visibility is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsAbstract(){
		JavaClass class1 = EditableJavaClass.builder()
				.isAbstract()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Abstract is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsStatic(){
		JavaClass class1 = EditableJavaClass.builder()
				.innerClass()
				.isStatic()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Inner Class is different!
					Static is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsFinal(){
		JavaClass class1 = EditableJavaClass.builder()
				.isFinal()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Final is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsClassName(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Derp")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Class Name is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsSuperClassName(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test").superClassName("Something")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Super Class Name is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImplementsInterfaceNames(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("Something")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("SomethingElse")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Implements Interface Names differs on #1!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImplementsInterfaceNamesLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("Something")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Implements Interface Names length is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsImplementsInterfaceNamesLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test").implementsInterfaceName("Something")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Implements Interface Names length is different!
					Implements Interface Names differs on #2!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsSingleLineComments(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("Something useful")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("Something different")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Single Line Comments differs on #1:\n" +
							"\tContent is different!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsSingleLineCommentsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("Something different")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Single Line Comments length is different!
					Inner Elements Order length is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsSingleLineCommentsLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.singleLineComment(EditableJavaSingleLineComment.builder()
						.content("Something different")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Single Line Comments length is different!
					Single Line Comments differs on #1!
					Inner Elements Order length is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMultiLineComments(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("Something useful")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("Something different")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Multi Line Comments differs on #1:\n" +
							"\tContent differs on #1!",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMultiLineCommentsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("Something different")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Multi Line Comments length is different!
					Inner Elements Order length is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMultiLineCommentsLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.multiLineComment(EditableJavaMultiLineComment.builder()
						.content("Something different")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Multi Line Comments length is different!
					Multi Line Comments differs on #1!
					Inner Elements Order length is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsInnerClasses(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("Something")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("SomethingElse")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Inner Classes differs on #1:
						Class Name is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsInnerClassesLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("Something")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Inner Classes length is different!
					Inner Elements Order length is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsInnerClassesLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.innerClass(EditableJavaClass.builder()
						.innerClass()
						.className("Something")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Inner Classes length is different!
					Inner Classes differs on #1!
					Inner Elements Order length is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsFields(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.field(EditableJavaField.builder()
						.type("String").name("version")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.field(EditableJavaField.builder()
						.type("String").name("test")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Fields differs on #1:
						Name is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsFieldsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.field(EditableJavaField.builder()
						.type("String").name("test")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Fields length is different!
					Inner Elements Order length is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsFieldsLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.field(EditableJavaField.builder()
						.type("String").name("test")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Fields length is different!
					Fields differs on #1!
					Inner Elements Order length is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMethods(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.method(EditableJavaMethod.builder()
						.returnType("String").name("getVersion")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.method(EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Methods differs on #1:
						Name is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMethodsLength(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.method(EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build())
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Methods length is different!
					Inner Elements Order length is different!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsMethodsLength1Longer(){
		JavaClass class1 = EditableJavaClass.builder()
				.className("Test")
				.method(EditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Methods length is different!
					Methods differs on #1!
					Inner Elements Order length is different!
					Inner Elements Order differs on #1!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertClassEqualsAll(){
		JavaClass class1 = UneditableJavaClass.builder()
				.packageName("com.test")
				.importName("com.example.Thing", false)
				.javadoc(UneditableJavadoc.builder()
						.build())
				.annotation(UneditableJavaAnnotation.builder()
						.name("Test")
						.build())
				.annotation(UneditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.visibility(Visibility.PUBLIC)
				.isAbstract()
				.className("Test").superClassName("Something")
				.implementsInterfaceName("Something").implementsInterfaceName("SomethingElse")
				.singleLineComment(UneditableJavaSingleLineComment.builder()
						.content("Something different")
						.build())
				.multiLineComment(UneditableJavaMultiLineComment.builder()
						.content("Something different")
						.build())
				.innerClass(UneditableJavaClass.builder()
						.innerClass()
						.className("Something")
						.build())
				.field(UneditableJavaField.builder()
						.type("String").name("test")
						.build())
				.method(UneditableJavaMethod.builder()
						.returnType("String").name("getTest")
						.build())
				.build();
		JavaClass class2 = EditableJavaClass.builder()
				.innerClass()
				.annotation(EditableJavaAnnotation.builder()
						.name("Derp")
						.build())
				.visibility(Visibility.PRIVATE)
				.isStatic().isFinal()
				.className("Derp")
				.implementsInterfaceName("Something")
				.build();
		try{
			assertClassEquals(class1, class2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
							Editable is different!
							Inner Class is different!
							Package Declaration differs:
								One of the package declarations is null, and the other isn't!
							Import Statements length is different!
							Import Statements differs on #1!
							Javadoc differs:
								One of the Javadocs is null, and the other isn't!
							Annotations length is different!
							Annotations differs on #1:
								Editable is different!
								Name is different!
							Annotations differs on #2!
							Visibility is different!
							Abstract is different!
							Static is different!
							Final is different!
							Class Name is different!
							Super Class Name is different!
							Implements Interface Names length is different!
							Implements Interface Names differs on #2!
							Single Line Comments length is different!
							Single Line Comments differs on #1!
							Multi Line Comments length is different!
							Multi Line Comments differs on #1!
							Inner Classes length is different!
							Inner Classes differs on #1!
							Fields length is different!
							Fields differs on #1!
							Methods length is different!
							Methods differs on #1!
							Inner Elements Order length is different!
							Inner Elements Order differs on #1!
							Inner Elements Order differs on #2!
							Inner Elements Order differs on #3!
							Inner Elements Order differs on #4!
							Inner Elements Order differs on #5!""",
					buildAssertError(class1, class2)), e.getMessage());
		}
	}
}
