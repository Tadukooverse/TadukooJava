package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.code.staticcodeblock.EditableJavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.UneditableJavaStaticCodeBlock;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.UneditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.UneditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.field.UneditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.method.UneditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.map.MapUtil;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaClassTest extends BaseJavaCodeTypeTest<JavaClass>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavaClass.builder()
								.className("Test")
								.build(),
						false,
						(Function<UneditableJavaClass, Boolean>) UneditableJavaClass::isEditable
				),
				Arguments.of(
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						true,
						(Function<EditableJavaClass, Boolean>) EditableJavaClass::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>,
				Function<Builders, Object>
				>> comparisons = ListUtil.<Pair<Function<Builders, Object>, Function<Builders, Object>>>createList(
				// Java Code Type
				Pair.of(
						builders ->
								JavaCodeTypes.CLASS,
						builders ->
								builders.classBuilder().get()
										.className("AClassName")
										.build()
										.getJavaCodeType()
				),
				// Default Is Inner Class
				Pair.of(
						builders -> false,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.isInnerClass()
				),
				// Default Package Declaration
				Pair.of(
						builders -> null,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Default Import Statements
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Default Javadoc
				Pair.of(
						builders -> null,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						builders -> Visibility.NONE,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getVisibility()
				),
				// Default Abstract
				Pair.of(
						builders -> false,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.isAbstract()
				),
				// Default Static
				Pair.of(
						builders -> false,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.isStatic()
				),
				// Default Final
				Pair.of(
						builders -> false,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.isFinal()
				),
				// Default Super Class Name
				Pair.of(
						builders -> null,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getSuperClassName()
				),
				// Default Implements Interfaces
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getImplementsInterfaceNames()
				),
				// Default Static Code Blocks
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getStaticCodeBlocks()
				),
				// Default Single Line Comments
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getSingleLineComments()
				),
				// Default Multi Line Comments
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getMultiLineComments()
				),
				// Default Fields
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getFields()
				),
				// Default Methods
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getMethods()
				),
				// Default Inner Elements Order
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getInnerElementsOrder()
				),
				// Empty Inner Classes Map
				Pair.of(
						builders -> MapUtil.createMap(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getInnerClassesMap()
				),
				// Inner Classes Map 1 Inner Class
				Pair.of(
						builders -> MapUtil.createMap(
								 Pair.of("BClassName", builders.classBuilder().get()
										 .innerClass()
										 .className("BClassName")
										 .build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.build()
								.getInnerClassesMap()
				),
				// Inner Classes Map 2 Inner Classes
				Pair.of(
						builders -> MapUtil.createMap(
								Pair.of("BClassName", builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build()),
								Pair.of("CClassName", builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
								.build()
								.getInnerClassesMap()
				),
				// Fields Map No Fields
				Pair.of(
						builders -> MapUtil.createMap(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getFieldsMap()
				),
				// Fields Map 1 Field
				Pair.of(
						builders -> MapUtil.createMap(
								 Pair.of("test", builders.fieldBuilder().get()
										 .type("String").name("test")
										 .build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("String").name("test")
										.build())
								.build()
								.getFieldsMap()
				),
				// Fields Map 2 Fields
				Pair.of(
						builders -> MapUtil.createMap(
								Pair.of("test", builders.fieldBuilder().get()
										.type("String").name("test")
										.build()),
								Pair.of("version", builders.fieldBuilder().get()
										.type("int").name("version")
										.build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("String").name("test")
										.build())
								.field(builders.fieldBuilder().get()
										.type("int").name("version")
										.build())
								.build()
								.getFieldsMap()
				),
				// Methods Map No Methods
				Pair.of(
						builders -> MapUtil.createMap(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getMethodsMap()
				),
				// Methods Map 1 Method
				Pair.of(
						builders -> MapUtil.createMap(
								 Pair.of("getType()", builders.methodBuilder().get()
										 .returnType("String").name("getType")
										 .build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("String").name("getType")
										.build())
								.build()
								.getMethodsMap()
				),
				// Methods Map 2 Methods
				Pair.of(
						builders -> MapUtil.createMap(
								Pair.of("getType()", builders.methodBuilder().get()
										.returnType("String").name("getType")
										.build()),
								Pair.of("getVersion(String type)", builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.parameter("String type")
										.build())
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("String").name("getType")
										.build())
								.method(builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.parameter("String type")
										.build())
								.build()
								.getMethodsMap()
				),
				// Copy
				Pair.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("some.classname", false)
								.javadoc(builders.javadocBuilder().get()
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("AClassName")
								.superClassName("BClassName")
								.implementsInterfaceName("SomeInterface")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.singleLineComment("some comment")
								.multiLineComment("some content", "more comment")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
								.field(builders.fieldBuilder().get()
										.type("String").name("test")
										.build())
								.method(builders.methodBuilder().get()
										.returnType("String").name("type")
										.build())
								.build(),
						builders -> builders.classBuilder().get()
								.copy(builders.classBuilder().get()
										.packageName("some.package")
										.importName("some.classname", false)
										.javadoc(builders.javadocBuilder().get()
												.build())
										.annotation(builders.annotationBuilder().get()
												.name("Test")
												.build())
										.visibility(Visibility.PUBLIC)
										.isFinal()
										.className("AClassName")
										.superClassName("BClassName")
										.implementsInterfaceName("SomeInterface")
										.staticCodeBlock(ListUtil.createList("doSomething();"))
										.singleLineComment("some comment")
										.multiLineComment("some content", "more comment")
										.innerClass(builders.classBuilder().get()
												.innerClass()
												.className("CClassName")
												.build())
										.field(builders.fieldBuilder().get()
												.type("String").name("test")
												.build())
										.method(builders.methodBuilder().get()
												.returnType("String").name("type")
												.build())
										.build())
								.build()
				),
				// Set Is Inner Class
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.isInnerClass(true)
								.build()
								.isInnerClass()
				),
				// Set Inner Class
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass()
								.build()
								.isInnerClass()
				),
				// Set Package Declaration
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("some.package")
								.build(),
						builders -> builders.classBuilder().get()
								.packageDeclaration(builders.packageDeclarationBuilder().get()
										.packageName("some.package")
										.build())
								.className("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Set Package Declaration
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("some.package")
								.build(),
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.className("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Set Class Name by Text
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AClassName")
								.build(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getClassName()
				),
				// Set Class Name
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AClassName")
								.build(),
						builders -> builders.classBuilder().get()
								.className(JavaType.builder()
										.baseType("AClassName")
										.build())
								.build()
								.getClassName()
				),
				// Get Simple Class Name
				Pair.of(
						builders -> "AClassName",
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build()
								.getSimpleClassName()
				),
				// Get Simple Class Name Complex Class Name
				Pair.of(
						builders -> "AClassName",
						builders -> builders.classBuilder().get()
								.className("AClassName<Map<String,Object>, T>")
								.build()
								.getSimpleClassName()
				),
				// Set Import Statement
				Pair.of(
						builders -> ListUtil.createList(
								 builders.importStatementBuilder().get()
										 .importName("com.example")
										 .build()
						),
						builders -> builders.classBuilder().get()
								.importStatement(builders.importStatementBuilder().get()
										.importName("com.example")
										.build())
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Multiple Import Statements
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.importName("com.example")
										.build(),
								builders.importStatementBuilder().get()
										.isStatic()
										.importName("com.other")
										.build()
						),
						builders -> builders.classBuilder().get()
								.importStatements(ListUtil.createList(
										builders.importStatementBuilder().get()
												.importName("com.example")
												.build(),
										builders.importStatementBuilder().get()
												.isStatic()
												.importName("com.other")
												.build()))
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Import Name
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.importName("com.example")
										.build()
						),
						builders -> builders.classBuilder().get()
								.importName("com.example", false)
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Static Import Name
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.isStatic()
										.importName("com.example")
										.build()
						),
						builders -> builders.classBuilder().get()
								.importName("com.example", true)
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Import Names
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.importName("com.example")
										.build(),
								builders.importStatementBuilder().get()
										.importName("com.other")
										.build()
						),
						builders -> builders.classBuilder().get()
								.importNames(ListUtil.createList("com.example", "com.other"), false)
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Static Import Names
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.isStatic()
										.importName("com.example")
										.build(),
								builders.importStatementBuilder().get()
										.isStatic()
										.importName("com.other")
										.build()
						),
						builders -> builders.classBuilder().get()
								.importNames(ListUtil.createList("com.example", "com.other"), true)
								.className("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Javadoc
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.build(),
						builders -> builders.classBuilder().get()
								.javadoc(builders.javadocBuilder().get()
										.build())
								.className("AClassName")
								.build()
								.getJavadoc()
				),
				// Set Single Annotation
				Pair.of(
						builders -> ListUtil.createList(
								 builders.annotationBuilder().get()
										 .name("Test")
										 .build()
						),
						builders -> builders.classBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.className("AClassName")
								.build()
								.getAnnotations()
				),
				// Set Multiple Annotations
				Pair.of(
						builders -> ListUtil.createList(
								builders.annotationBuilder().get()
										.name("Test")
										.build(),
								builders.annotationBuilder().get()
										.name("Derp")
										.build()
						),
						builders -> builders.classBuilder().get()
								.annotations(ListUtil.createList(
										builders.annotationBuilder().get()
												.name("Test")
												.build(),
										builders.annotationBuilder().get()
												.name("Derp")
												.build()
								))
								.className("AClassName")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						builders -> Visibility.PRIVATE,
						builders -> builders.classBuilder().get()
								.visibility(Visibility.PRIVATE)
								.className("AClassName")
								.build()
								.getVisibility()
				),
				// Set Is Abstract Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.isAbstract(true)
								.className("AClassName")
								.build()
								.isAbstract()
				),
				// Set Is Abstract No Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.isAbstract()
								.className("AClassName")
								.build()
								.isAbstract()
				),
				// Set Is Static Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.innerClass()
								.isStatic(true)
								.className("AClassName")
								.build()
								.isStatic()
				),
				// Set Is Static No Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.innerClass()
								.isStatic()
								.className("AClassName")
								.build()
								.isStatic()
				),
				// Set Is Final Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.isFinal(true)
								.className("AClassName")
								.build()
								.isFinal()
				),
				// Set Is Final No Param
				Pair.of(
						builders -> true,
						builders -> builders.classBuilder().get()
								.isFinal()
								.className("AClassName")
								.build()
								.isFinal()
				),
				// Set Super Class Name by Text
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AnotherClassName")
								.build(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.superClassName("AnotherClassName")
								.build()
								.getSuperClassName()
				),
				// Set Super Class Name
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AnotherClassName")
								.build(),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.superClassName(JavaType.builder()
										.baseType("AnotherClassName")
										.build())
								.build()
								.getSuperClassName()
				),
				// Set Implements Interface Name by Text
				Pair.of(
						builders -> ListUtil.createList(
								JavaType.builder()
										.baseType("SomeInterface")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceName("SomeInterface")
								.build()
								.getImplementsInterfaceNames()
				),
				// Set Implements Interface Name
				Pair.of(
						builders -> ListUtil.createList(
								JavaType.builder()
										.baseType("SomeInterface")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceName(JavaType.builder()
										.baseType("SomeInterface")
										.build())
								.build()
								.getImplementsInterfaceNames()
				),
				// Set Implements Interface Names by Text
				Pair.of(
						builders -> ListUtil.createList(
								JavaType.builder()
										.baseType("SomeInterface")
										.build(),
								JavaType.builder()
										.baseType("SomeOtherInterface")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceNameTexts(ListUtil.createList(
										"SomeInterface", "SomeOtherInterface"))
								.build()
								.getImplementsInterfaceNames()
				),
				// Set Implements Interface Names
				Pair.of(
						builders -> ListUtil.createList(
								JavaType.builder()
										.baseType("SomeInterface")
										.build(),
								JavaType.builder()
										.baseType("SomeOtherInterface")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceNames(ListUtil.createList(
										JavaType.builder()
												.baseType("SomeInterface")
												.build(),
										JavaType.builder()
												.baseType("SomeOtherInterface")
												.build()))
								.build()
								.getImplementsInterfaceNames()
				),
				// Set Static Code Block
				Pair.of(
						builders -> ListUtil.createList(
								builders.staticCodeBlockBuilder().get()
										.line("doSomething();")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(builders.staticCodeBlockBuilder().get()
										.line("doSomething();")
										.build())
								.build()
								.getStaticCodeBlocks()
				),
				// Set Multiple Static Code Blocks
				Pair.of(
						builders -> ListUtil.createList(
								builders.staticCodeBlockBuilder().get()
										.line("doSomething();")
										.build(),
								builders.staticCodeBlockBuilder().get()
										.line("doSomethingElse();")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlocks(ListUtil.createList(builders.staticCodeBlockBuilder().get()
												.line("doSomething();")
												.build(),
										builders.staticCodeBlockBuilder().get()
												.line("doSomethingElse();")
												.build()))
								.build()
								.getStaticCodeBlocks()
				),
				// Set Static Code Block using lines
				Pair.of(
						builders -> ListUtil.createList(
								builders.staticCodeBlockBuilder().get()
										.line("doSomething();")
										.line("doSomethingElse();")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList(
										"doSomething();",
										"doSomethingElse();"
								))
								.build()
								.getStaticCodeBlocks()
				),
				// Set Single Line Comment
				Pair.of(
						builders -> ListUtil.createList(
								builders.singleLineCommentBuilder().get()
										.content("some comment")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment(builders.singleLineCommentBuilder().get()
										.content("some comment")
										.build())
								.build()
								.getSingleLineComments()
				),
				// Set Multiple Single Line Comments
				Pair.of(
						builders -> ListUtil.createList(
								builders.singleLineCommentBuilder().get()
										.content("some comment")
										.build(),
								builders.singleLineCommentBuilder().get()
										.content("some more comment")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComments(ListUtil.createList(
										builders.singleLineCommentBuilder().get()
												.content("some comment")
												.build(),
										builders.singleLineCommentBuilder().get()
												.content("some more comment")
												.build()
								))
								.build()
								.getSingleLineComments()
				),
				// Set Single Line Comment by string
				Pair.of(
						builders -> ListUtil.createList(
								builders.singleLineCommentBuilder().get()
										.content("some comment")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.build()
								.getSingleLineComments()
				),
				// Set Multi Line Comment
				Pair.of(
						builders -> ListUtil.createList(
								builders.multiLineCommentBuilder().get()
										.content("some comment")
										.content("more content")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment(builders.multiLineCommentBuilder().get()
										.content("some comment")
										.content("more content")
										.build())
								.build()
								.getMultiLineComments()
				),
				// Set Multiple Multi Line Comments
				Pair.of(
						builders -> ListUtil.createList(
								builders.multiLineCommentBuilder().get()
										.content("some comment")
										.content("more content")
										.build(),
								builders.multiLineCommentBuilder().get()
										.content("some more comment")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComments(ListUtil.createList(
										builders.multiLineCommentBuilder().get()
												.content("some comment")
												.content("more content")
												.build(),
										builders.multiLineCommentBuilder().get()
												.content("some more comment")
												.build()
								))
								.build()
								.getMultiLineComments()
				),
				// Set Multi Line Comment by strings
				Pair.of(
						builders -> ListUtil.createList(
								builders.multiLineCommentBuilder().get()
										.content("some comment")
										.content("more content")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment", "more content")
								.build()
								.getMultiLineComments()
				),
				// Set Inner Class
				Pair.of(
						builders -> ListUtil.createList(
								builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.build()
								.getInnerClasses()
				),
				// Set Multiple Inner Classes
				Pair.of(
						builders -> ListUtil.createList(
								builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build(),
								builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClasses(ListUtil.createList(
										builders.classBuilder().get()
												.innerClass()
												.className("BClassName")
												.build(),
										builders.classBuilder().get()
												.innerClass()
												.className("CClassName")
												.build()))
								.build()
								.getInnerClasses()
				),
				// Set Field
				Pair.of(
						builders -> ListUtil.createList(
								builders.fieldBuilder().get()
										.type("int").name("test")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("int").name("test")
										.build())
								.build()
								.getFields()
				),
				// Set Multiple Fields
				Pair.of(
						builders -> ListUtil.createList(
								builders.fieldBuilder().get()
										.type("int").name("test")
										.build(),
								builders.fieldBuilder().get()
										.type("String").name("derp")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.fields(ListUtil.createList(
										builders.fieldBuilder().get()
												.type("int").name("test")
												.build(),
										builders.fieldBuilder().get()
												.type("String").name("derp")
												.build()))
								.build()
								.getFields()
				),
				// Set Method
				Pair.of(
						builders -> ListUtil.createList(
								builders.methodBuilder().get()
										.returnType("int").name("test")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("int").name("test")
										.build())
								.build()
								.getMethods()
				),
				// Set Multiple Methods
				Pair.of(
						builders -> ListUtil.createList(
								builders.methodBuilder().get()
										.returnType("int").name("test")
										.build(),
								builders.methodBuilder().get()
										.returnType("String").name("derp")
										.line("return 42;")
										.build()
						),
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.methods(ListUtil.createList(
										builders.methodBuilder().get()
												.returnType("int").name("test")
												.build(),
										builders.methodBuilder().get()
												.returnType("String").name("derp")
												.line("return 42;")
												.build()))
								.build()
								.getMethods()
				),
				// Equals
				Pair.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.className("AClassName").superClassName("AnotherClassName")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build(),
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.className("AClassName").superClassName("AnotherClassName")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function<Builders, Object>,
				Function<Builders, Object>
				>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build(),
						builders -> builders.classBuilder().get()
								.packageName("some.package.different")
								.className("AClassName")
								.build()
				),
				// Different Types
				Pair.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build(),
						builders -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<
				Function<Builders, JavaClass>,
				String,
				Function<SimpleClassNames, String>
				>> comparisons = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.build(),
						"""
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.build()"""
				),
				// With Package Name
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.className("AClassName")
								.build(),
						"""
								package some.package;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.className("AClassName")
										.build()"""
				),
				// With Import
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.example.*", false)
								.className("AClassName")
								.build(),
						"""
								import com.example.*;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", false)
										.className("AClassName")
										.build()"""
				),
				// With Imports Same Base in Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.example.*", false)
								.importName("com.whatever", false)
								.className("AClassName")
								.build(),
						"""
								import com.example.*;
								import com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.className("AClassName")
										.build()"""
				),
				// With Imports Same Base Reverse Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.whatever", false)
								.importName("com.example.*", false)
								.className("AClassName")
								.build(),
						"""
								import com.example.*;
								import com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.className("AClassName")
										.build()"""
				),
				// With Imports Different Base Strange Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.className("AClassName")
								.build(),
						"""
								import com.example.*;
								import com.whatever;
								
								import org.test;
								import org.yep;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.className("AClassName")
										.build()"""
				),
				// With Static Import
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.example.*", true)
								.className("AClassName")
								.build(),
						"""
								import static com.example.*;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", true)
										.className("AClassName")
										.build()"""
				),
				// With Static Imports Same Base in Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.example.*", true)
								.importName("com.whatever", true)
								.className("AClassName")
								.build(),
						"""
								import static com.example.*;
								import static com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.className("AClassName")
										.build()"""
				),
				// With Static Imports Same Base Reverse Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.whatever", true)
								.importName("com.example.*", true)
								.className("AClassName")
								.build(),
						"""
								import static com.example.*;
								import static com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.className("AClassName")
										.build()"""
				),
				// With Static Imports Different Base Strange Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.whatever", true)
								.importName("org.yep", true)
								.importName("com.example.*", true)
								.importName("org.test", true)
								.className("AClassName")
								.build(),
						"""
								import static com.example.*;
								import static com.whatever;
								
								import static org.test;
								import static org.yep;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.importName("org.test", true)
										.importName("org.yep", true)
										.className("AClassName")
										.build()"""
				),
				// With Regular and Static Imports Different Base Strange Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.importName("com.whatever", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep", false)
								.importName("org.yep.dope", true)
								.importName("com.example.*", false)
								.importName("com.example.test.*", true)
								.importName("org.test", false)
								.importName("org.test.yep", true)
								.className("AClassName")
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
										.className("AClassName")
										.build()"""
				),
				// With Visibility
				Triple.of(
						builders -> builders.classBuilder().get()
								.visibility(Visibility.PROTECTED)
								.className("AClassName")
								.build(),
						"""
								protected class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.visibility(Visibility.PROTECTED)
										.className("AClassName")
										.build()"""
				),
				// With Abstract
				Triple.of(
						builders -> builders.classBuilder().get()
								.isAbstract()
								.className("AClassName")
								.build(),
						"""
								abstract class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.isAbstract()
										.className("AClassName")
										.build()"""
				),
				// With Final
				Triple.of(
						builders -> builders.classBuilder().get()
								.isFinal()
								.className("AClassName")
								.build(),
						"""
								final class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.isFinal()
										.className("AClassName")
										.build()"""
				),
				// With Super Class Name
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.superClassName("AnotherClassName")
								.build(),
						"""
								class AClassName extends AnotherClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.superClassName("AnotherClassName")
										.build()"""
				),
				// With Implements Interface
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceName("SomeInterface")
								.build(),
						"""
								class AClassName implements SomeInterface{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.implementsInterfaceName("SomeInterface")
										.build()"""
				),
				// With Multiple Implements Interfaces
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.implementsInterfaceName("SomeInterface")
								.implementsInterfaceName("SomeOtherInterface")
								.build(),
						"""
								class AClassName implements SomeInterface, SomeOtherInterface{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.implementsInterfaceName("SomeInterface")
										.implementsInterfaceName("SomeOtherInterface")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						builders -> builders.classBuilder().get()
								.javadoc(builders.javadocBuilder().get()
										.build())
								.className("AClassName")
								.build(),
						"""
								/**
								 */
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.className("AClassName")
										.build()"""
				),
				// With Annotation
				Triple.of(
						builders -> builders.classBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.className("AClassName")
								.build(),
						"""
								@Test
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.className("AClassName")
										.build()"""
				),
				// With Multiple Annotations
				Triple.of(
						builders -> builders.classBuilder().get()
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.className("AClassName")
								.build(),
						"""
								@Test
								@Derp
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.className("AClassName")
										.build()"""
				),
				// With Static Code Block
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.build(),
						"""
								class AClassName{
								\t
									static{
										doSomething();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.staticCodeBlock(ListUtil.createList(
												"doSomething();"
										))
										.build()"""
				),
				// With Multiline Static Code Block
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList(
										"doSomething();",
										"doSomethingElse();"
								))
								.build(),
						"""
								class AClassName{
								\t
									static{
										doSomething();
										doSomethingElse();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.staticCodeBlock(ListUtil.createList(
												"doSomething();",
												"doSomethingElse();"
										))
										.build()"""
				),
				// With Single-Line Comment
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.build(),
						"""
								class AClassName{
								\t
									// some comment
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.singleLineComment("some comment")
										.build()"""
				),
				// With Multi-Line Comment
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment", "more content")
								.build(),
						"""
								class AClassName{
								\t
									/*
									 * some comment
									 * more content
									 */
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.multiLineComment("some comment",\s
												"more content")
										.build()"""
				),
				// With Inner Classes
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.build()"""
				),
				// With Inner Classes Reverse Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.CLASS, "CClassName"),
										Pair.of(JavaCodeTypes.CLASS, "BClassName")))
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.build()"""
				),
				// With Fields
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("int").name("test")
										.build())
								.field(builders.fieldBuilder().get()
										.type("String").name("derp")
										.build())
								.build(),
						"""
								class AClassName{
								\t
									int test;
									String derp;
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.build()"""
				),
				// With Fields Swap Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("int").name("test")
										.build())
								.field(builders.fieldBuilder().get()
										.type("String").name("derp")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.FIELD, "derp"),
										Pair.of(JavaCodeTypes.FIELD, "test")
								))
								.build(),
						"""
								class AClassName{
								\t
									String derp;
									int test;
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.build()"""
				),
				// With Fields with Javadoc on Field
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.javadoc(builders.javadocBuilder().get()
												.condensed()
												.content("something")
												.build())
										.type("int").name("test")
										.build())
								.field(builders.fieldBuilder().get()
										.type("String").name("derp")
										.build())
								.build(),
						"""
								class AClassName{
								\t
									/** something */
									int test;
									String derp;
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
														.condensed()
														.content("something")
														.build())
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.build()"""
				),
				// With Methods
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("AClassName")
										.build())
								.method(builders.methodBuilder().get()
										.returnType("String")
										.name("getSomething")
										.parameter("int test")
										.line("return doSomething();")
										.build())
								.build(),
						"""
								class AClassName{
								\t
									AClassName(){ }
								\t
									String getSomething(int test){
										return doSomething();
									}
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				),
				// With Methods Swap Order
				Triple.of(
						builders -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("AClassName")
										.build())
								.method(builders.methodBuilder().get()
										.returnType("String")
										.name("getSomething")
										.parameter("int test")
										.line("return doSomething();")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.METHOD, "getSomething(int test)"),
										Pair.of(JavaCodeTypes.METHOD, "init()")
								))
								.build(),
						"""
								class AClassName{
								\t
									String getSomething(int test){
										return doSomething();
									}
								\t
									AClassName(){ }
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.className("AClassName")
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.build()"""
				),
				// With Everything No Order Specified
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("AClassName").superClassName("AnotherClassName")
								.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isFinal()
										.className("AClassName")
										.superClassName("AnotherClassName")
										.implementsInterfaceName("SomeInterface")
										.implementsInterfaceName("SomeOtherInterface")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("AClassName").superClassName("AnotherClassName")
								.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isFinal()
										.className("AClassName")
										.superClassName("AnotherClassName")
										.implementsInterfaceName("SomeInterface")
										.implementsInterfaceName("SomeOtherInterface")
										.singleLineComment("some comment")
										.multiLineComment("some comment",\s
												"more content")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				),
				// With Everything Order Swapped
				Triple.of(
						builders -> builders.classBuilder().get()
								.packageName("some.package")
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep.dope", true)
								.importName("com.example.test.*", true)
								.importName("org.test.yep", true)
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("AClassName").superClassName("AnotherClassName")
								.implementsInterfaceName("SomeInterface").implementsInterfaceName("SomeOtherInterface")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
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
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.packageName("some.package")
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isFinal()
										.className("AClassName")
										.superClassName("AnotherClassName")
										.implementsInterfaceName("SomeInterface")
										.implementsInterfaceName("SomeOtherInterface")
										.multiLineComment("some comment",\s
												"more content")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.singleLineComment("some comment")
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.build()"""
				),
				// Inner Class
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerClass()
								.className("AClassName")
								.build(),
						"""
								class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerClass()
										.className("AClassName")
										.build()"""
				),
				// Inner Class Static
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerClass()
								.isStatic()
								.className("AClassName")
								.build(),
						"""
								static class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerClass()
										.isStatic()
										.className("AClassName")
										.build()"""
				),
				// Inner Class Final
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerClass()
								.isFinal()
								.className("AClassName")
								.build(),
						"""
								final class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerClass()
										.isFinal()
										.className("AClassName")
										.build()"""
				),
				// Inner Class With Everything
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerClass()
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isStatic().isFinal()
								.className("AClassName").superClassName("AnotherClassName")
								.innerClass(builders.classBuilder().get().innerClass().className("BClassName").build())
								.innerClass(builders.classBuilder().get().innerClass().className("CClassName").build())
								.field(builders.fieldBuilder().get().type("int").name("test").build())
								.field(builders.fieldBuilder().get().type("String").name("derp").build())
								.method(builders.methodBuilder().get().returnType("AClassName").build())
								.method(builders.methodBuilder().get().returnType("String").name("getSomething")
										.parameter("int test").line("return doSomething();").build())
								.build(),
						"""
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
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerClass()
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isStatic()
										.isFinal()
										.className("AClassName")
										.superClassName("AnotherClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerClass()
												.className("CClassName")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("AClassName")
												.build())
										.method(""" + classNames.methodSimpleClassName() + """
								.builder()
												.returnType("String")
												.name("getSomething")
												.parameter("int test")
												.line("return doSomething();")
												.build())
										.build()"""
				)
		);
		
		return comparisons.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaClass>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.visibility(null)
								.className("AClassName")
								.build(),
						"Visibility is required!"
				),
				// Null Class Name
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.build(),
						"Must specify className!"
				),
				// Abstract Static
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass()
								.isAbstract().isStatic()
								.build(),
						"Can't be abstract and static!"
				),
				// Abstract Final
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass()
								.isAbstract().isFinal()
								.build(),
						"Can't be abstract and final!"
				),
				// Abstract Static Final
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass()
								.isAbstract().isStatic().isFinal()
								.build(),
						"Can't be abstract and static!\n" +
						"Can't be abstract and final!"
				),
				// Inner Class Not Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("BClassName")
								.innerClass(builders.classBuilder().get()
										.className("AClassName")
										.build())
								.build(),
						"Inner class 'AClassName' is not an inner class!"
				),
				// Outer Class Can't be Static
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.isStatic()
								.className("AClassName")
								.build(),
						"Only inner classes can be static!"
				),
				// All Outer Class Errors
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.isStatic()
								.innerClass(builders.classBuilder().get()
										.className("AClassName")
										.build())
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify className!
								Inner class 'AClassName' is not an inner class!
								Only inner classes can be static!"""
				),
				// Null Visibility Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.visibility(null)
								.className("AClassName")
								.build(),
						"Visibility is required!"
				),
				// Null Class Name Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.build(),
						"Must specify className!"
				),
				// Inner Class in Inner Class Not Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.className("BClassName")
								.innerClass(builders.classBuilder().get()
										.className("AClassName")
										.build())
								.build(),
						"Inner class 'AClassName' is not an inner class!"
				),
				// Package Declaration Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.packageName("some.package")
								.className("AClassName")
								.build(),
						"Not allowed to have package declaration for an inner class!"
				),
				// Import Statement Inner Class
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.importName("com.example", false)
								.className("AClassName")
								.build(),
						"Not allowed to have import statements for an inner class!"
				),
				// All Inner Class Builder Errors
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.innerClass()
								.packageName("some.package")
								.innerClass(builders.classBuilder().get()
										.packageName("some.package")
										.className("AClassName")
										.build())
								.importName("com.example", false)
								.visibility(null)
								.build(),
						"""
							Visibility is required!
							Must specify className!
							Inner class 'AClassName' is not an inner class!
							Not allowed to have package declaration for an inner class!
							Not allowed to have import statements for an inner class!"""
				),
				// Missing Inner Elements Order when Static Code Block Present
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Missing Inner Elements Order when Single Line Comment Present
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Missing Inner Elements Order when Multi Line Comment Present
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment", "more content")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Missing Inner Elements Order when Both Types of Comment Present
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Too Many Static Code Blocks in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null),
										Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
								))
								.build(),
						"Specified more static code blocks in innerElementsOrder than we have!"
				),
				// Too Many Single Line Comments in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"Specified more single-line comments in innerElementsOrder than we have!"
				),
				// Too Many Multi Line Comments in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment", "more content")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
								))
								.build(),
						"Specified more multi-line comments in innerElementsOrder than we have!"
				),
				// Reused Inner Class Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.CLASS, "BClassName"),
										Pair.of(JavaCodeTypes.CLASS, "BClassName")
								))
								.build(),
						"Already used inner class named: BClassName"
				),
				// Unknown Inner Class Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.CLASS, "BClassName"),
										Pair.of(JavaCodeTypes.CLASS, "CClassName")
								))
								.build(),
						"Unknown inner class name: CClassName"
				),
				// Reused Field Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("int").name("test")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.FIELD, "test"),
										Pair.of(JavaCodeTypes.FIELD, "test")
								))
								.build(),
						"Already used field named: test"
				),
				// Unknown Field Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.field(builders.fieldBuilder().get()
										.type("int").name("test")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.FIELD, "test"),
										Pair.of(JavaCodeTypes.FIELD, "derp")
								))
								.build(),
						"Unknown field name: derp"
				),
				// Reused Method Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
										Pair.of(JavaCodeTypes.METHOD, "getVersion()")
								))
								.build(),
						"Already used method named: getVersion()"
				),
				// Unknown Method Name in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.method(builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
										Pair.of(JavaCodeTypes.METHOD, "getSomething()")
								))
								.build(),
						"Unknown method name: getSomething()"
				),
				// Unknown Inner Element Type
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.innerElementsOrder(ListUtil.createList(Pair.of(JavaCodeTypes.ANNOTATION, "something")))
								.build(),
						"Unknown inner element type: " + JavaCodeTypes.ANNOTATION.getStandardName()
				),
				// Missed 1 Static Code Block in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.staticCodeBlock(ListUtil.createList("doSomethingElse();"))
								.staticCodeBlock(ListUtil.createList("doSomethingEvenMore();"))
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null),
										Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
								))
								.build(),
						"Missed 1 static code blocks in innerElementsOrder!"
				),
				// Missed 2 Static Code Blocks in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.staticCodeBlock(ListUtil.createList("doSomethingElse();"))
								.staticCodeBlock(ListUtil.createList("doSomethingEvenMore();"))
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
								))
								.build(),
						"Missed 2 static code blocks in innerElementsOrder!"
				),
				// Missed 1 Single Line Comment in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.singleLineComment("some other comment")
								.singleLineComment("some third comment")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"Missed 1 single-line comments in innerElementsOrder!"
				),
				// Missed 2 Single Line Comments in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.singleLineComment("some other comment")
								.singleLineComment("some third comment")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"Missed 2 single-line comments in innerElementsOrder!"
				),
				// Missed 1 Multi Line Comment in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment")
								.multiLineComment("some other comment")
								.multiLineComment("some third comment")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null),
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
								))
								.build(),
						"Missed 1 multi-line comments in innerElementsOrder!"
				),
				// Missed 2 Multi Line Comments in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.multiLineComment("some comment")
								.multiLineComment("some other comment")
								.multiLineComment("some third comment")
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null)
								))
								.build(),
						"Missed 2 multi-line comments in innerElementsOrder!"
				),
				// Missed 1 Inner Class in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following inner classes were not specified in innerElementsOrder: BClassName"
				),
				// Missed 2 Inner Classes in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerClass()
										.className("CClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following inner classes were not specified in innerElementsOrder: BClassName,CClassName"
				),
				// Missed 1 Field in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.field(builders.fieldBuilder().get()
										.type("int").name("version")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following fields were not specified in innerElementsOrder: version"
				),
				// Missed 2 Fields in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.field(builders.fieldBuilder().get()
										.type("int").name("version")
										.build())
								.field(builders.fieldBuilder().get()
										.type("String").name("test")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following fields were not specified in innerElementsOrder: version,test"
				),
				// Missed 1 Method in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.method(builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following methods were not specified in innerElementsOrder: getVersion()"
				),
				// Missed 2 Methods in Elements Order
				Pair.of(
						builders -> () -> builders.classBuilder().get()
								.className("AClassName")
								.singleLineComment("some comment")
								.method(builders.methodBuilder().get()
										.returnType("int").name("getVersion")
										.build())
								.method(builders.methodBuilder().get()
										.returnType("String").name("getTest")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following methods were not specified in innerElementsOrder: getVersion(),getTest()"
				)
		);
		
		List<Pair<Supplier<JavaClass>, String>> editableRelatedErrors = ListUtil.createList(
				// Editable Package Declaration in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.className("AClassName")
								.build(),
						"package declaration is not uneditable in this uneditable JavaClass"
				),
				// Editable Import Statement in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.className("AClassName")
								.build(),
						"some import statements are not uneditable in this uneditable JavaClass"
				),
				// Editable Javadoc in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.javadoc(EditableJavadoc.builder()
										.build())
								.className("AClassName")
								.build(),
						"javadoc is not uneditable in this uneditable JavaClass"
				),
				// Editable Annotation in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.className("AClassName")
								.build(),
						"some annotations are not uneditable in this uneditable JavaClass"
				),
				// Editable Static Code Block in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.staticCodeBlock(EditableJavaStaticCodeBlock.builder()
										.line("some comment")
										.build())
								.build(),
						"some static code blocks are not uneditable in this uneditable JavaClass"
				),
				// Editable Single Line Comment in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some single-line comments are not uneditable in this uneditable JavaClass"
				),
				// Editable Multi Line Comment in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some multi-line comments are not uneditable in this uneditable JavaClass"
				),
				// Editable Inner Class in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("BClassName")
										.build())
								.build(),
						"some inner classes are not uneditable in this uneditable JavaClass"
				),
				// Editable Field in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.field(EditableJavaField.builder()
										.type("int").name("version")
										.build())
								.build(),
						"some fields are not uneditable in this uneditable JavaClass"
				),
				// Editable Method in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.className("AClassName")
								.method(EditableJavaMethod.builder()
										.returnType("AClassName")
										.build())
								.build(),
						"some methods are not uneditable in this uneditable JavaClass"
				),
				// All Editable Stuff in Uneditable JavaClass
				Pair.of(
						() -> UneditableJavaClass.builder()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.javadoc(EditableJavadoc.builder()
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.className("AClassName")
								.staticCodeBlock(EditableJavaStaticCodeBlock.builder()
										.line("doSomething();")
										.build())
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("BClassName")
										.build())
								.field(EditableJavaField.builder()
										.type("int").name("version")
										.build())
								.method(EditableJavaMethod.builder()
										.returnType("AClassName")
										.build())
								.build(),
						"""
								package declaration is not uneditable in this uneditable JavaClass
								some import statements are not uneditable in this uneditable JavaClass
								javadoc is not uneditable in this uneditable JavaClass
								some annotations are not uneditable in this uneditable JavaClass
								some static code blocks are not uneditable in this uneditable JavaClass
								some single-line comments are not uneditable in this uneditable JavaClass
								some multi-line comments are not uneditable in this uneditable JavaClass
								some inner classes are not uneditable in this uneditable JavaClass
								some fields are not uneditable in this uneditable JavaClass
								some methods are not uneditable in this uneditable JavaClass"""
				),
				// Uneditable Package Declaration in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.packageDeclaration(UneditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.className("AClassName")
								.build(),
						"package declaration is not editable in this editable JavaClass"
				),
				// Uneditable Import Statement in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.importStatement(UneditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.className("AClassName")
								.build(),
						"some import statements are not editable in this editable JavaClass"
				),
				// Uneditable Javadoc in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.javadoc(UneditableJavadoc.builder()
										.build())
								.className("AClassName")
								.build(),
						"javadoc is not editable in this editable JavaClass"
				),
				// Uneditable Annotation in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.className("AClassName")
								.build(),
						"some annotations are not editable in this editable JavaClass"
				),
				// Uneditable Static Code Block in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.staticCodeBlock(UneditableJavaStaticCodeBlock.builder()
										.line("doSomething();")
										.build())
								.build(),
						"some static code blocks are not editable in this editable JavaClass"
				),
				// Uneditable Single Line Comment in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.singleLineComment(UneditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some single-line comments are not editable in this editable JavaClass"
				),
				// Uneditable Multi Line Comment in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.multiLineComment(UneditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some multi-line comments are not editable in this editable JavaClass"
				),
				// Uneditable Inner Class in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.innerClass(UneditableJavaClass.builder()
										.innerClass()
										.className("BClassName")
										.build())
								.build(),
						"some inner classes are not editable in this editable JavaClass"
				),
				// Uneditable Field in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.field(UneditableJavaField.builder()
										.type("int").name("version")
										.build())
								.build(),
						"some fields are not editable in this editable JavaClass"
				),
				// Uneditable Method in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.className("AClassName")
								.method(UneditableJavaMethod.builder()
										.returnType("AClassName")
										.build())
								.build(),
						"some methods are not editable in this editable JavaClass"
				),
				// All Uneditable Stuff in Editable JavaClass
				Pair.of(
						() -> EditableJavaClass.builder()
								.packageDeclaration(UneditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.importStatement(UneditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.javadoc(UneditableJavadoc.builder()
										.build())
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.className("AClassName")
								.staticCodeBlock(UneditableJavaStaticCodeBlock.builder()
										.line("doSomething();")
										.build())
								.singleLineComment(UneditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.multiLineComment(UneditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.innerClass(UneditableJavaClass.builder()
										.innerClass()
										.className("BClassName")
										.build())
								.field(UneditableJavaField.builder()
										.type("int").name("version")
										.build())
								.method(UneditableJavaMethod.builder()
										.returnType("AClassName")
										.build())
								.build(),
						"""
								package declaration is not editable in this editable JavaClass
								some import statements are not editable in this editable JavaClass
								javadoc is not editable in this editable JavaClass
								some annotations are not editable in this editable JavaClass
								some static code blocks are not editable in this editable JavaClass
								some single-line comments are not editable in this editable JavaClass
								some multi-line comments are not editable in this editable JavaClass
								some inner classes are not editable in this editable JavaClass
								some fields are not editable in this editable JavaClass
								some methods are not editable in this editable JavaClass"""
				)
		);
		
		return Stream.concat(builderFuncsAndErrorMessages.stream()
						.flatMap(pair -> Stream.of(0, 1, 2)
								.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
										pair.getRight()))),
				editableRelatedErrors.stream()
						.map(pair -> Arguments.of(pair.getLeft(), pair.getRight())));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetInnerClass(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertFalse(clazz.isInnerClass());
		clazz.setInnerClass(false);
		assertFalse(clazz.isInnerClass());
		clazz.setInnerClass(true);
		assertTrue(clazz.isInnerClass());
	}
	
	@Test
	public void testSetPackageDeclaration(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertNull(clazz.getPackageDeclaration());
		clazz.setPackageName("some.random.package.name");
		assertEquals(EditableJavaPackageDeclaration.builder()
				.packageName("some.random.package.name")
				.build(), clazz.getPackageDeclaration());
	}
	
	@Test
	public void testAddImportStatement(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertNull(clazz.getJavadoc());
		clazz.setJavadoc(EditableJavadoc.builder().build());
		assertEquals(EditableJavadoc.builder().build(), clazz.getJavadoc());
	}
	
	@Test
	public void testSetJavadocUneditable(){
		try{
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
			clazz.setJavadoc(UneditableJavadoc.builder().build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
			clazz.addAnnotation(UneditableJavaAnnotation.builder().name("Test").build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(Visibility.NONE, clazz.getVisibility());
		clazz.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetAbstract(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertFalse(clazz.isAbstract());
		clazz.setAbstract(false);
		assertFalse(clazz.isAbstract());
		clazz.setAbstract(true);
		assertTrue(clazz.isAbstract());
	}
	
	@Test
	public void testSetStatic(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertFalse(clazz.isStatic());
		clazz.setStatic(false);
		assertFalse(clazz.isStatic());
		clazz.setStatic(true);
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertFalse(clazz.isFinal());
		clazz.setFinal(false);
		assertFalse(clazz.isFinal());
		clazz.setFinal(true);
		assertTrue(clazz.isFinal());
	}
	
	@Test
	public void testSetClassName(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(JavaType.builder()
				.baseType("AClassName")
				.build(), clazz.getClassName());
		clazz.setClassName(JavaType.builder()
				.baseType("SomethingElse")
				.build());
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getClassName());
	}
	
	@Test
	public void testSetClassNameByText(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(JavaType.builder()
				.baseType("AClassName")
				.build(), clazz.getClassName());
		clazz.setClassName("SomethingElse");
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getClassName());
	}
	
	@Test
	public void testSetSuperClassName(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertNull(clazz.getSuperClassName());
		clazz.setSuperClassName(JavaType.builder()
				.baseType("SomethingElse")
				.build());
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getSuperClassName());
	}
	
	@Test
	public void testSetSuperClassNameByText(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertNull(clazz.getSuperClassName());
		clazz.setSuperClassName("SomethingElse");
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getSuperClassName());
	}
	
	@Test
	public void testAddImplementsInterfaceName(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName(JavaType.builder()
				.baseType("SomeInterface")
				.build());
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName(JavaType.builder()
				.baseType("SomeOtherInterface")
				.build());
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddImplementsInterfaceNameByText(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName("SomeInterface");
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceName("SomeOtherInterface");
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddImplementsInterfaceNames(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNames(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNames(ListUtil.createList(JavaType.builder()
				.baseType("AInterface")
				.build(), JavaType.builder()
				.baseType("BInterface")
				.build()));
		assertEquals(ListUtil.createList(JavaType.builder()
						.baseType("SomeInterface")
						.build(), JavaType.builder()
						.baseType("SomeOtherInterface")
						.build(), JavaType.builder()
						.baseType("AInterface")
						.build(), JavaType.builder()
						.baseType("BInterface")
						.build()),
				clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddImplementsInterfaceNameTexts(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNameTexts(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.addImplementsInterfaceNameTexts(ListUtil.createList("AInterface", "BInterface"));
		assertEquals(ListUtil.createList(JavaType.builder()
						.baseType("SomeInterface")
						.build(), JavaType.builder()
						.baseType("SomeOtherInterface")
						.build(), JavaType.builder()
						.baseType("AInterface")
						.build(), JavaType.builder()
						.baseType("BInterface")
						.build()),
				clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetImplementsInterfaceNames(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNames(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNames(ListUtil.createList(JavaType.builder()
				.baseType("AInterface")
				.build(), JavaType.builder()
				.baseType("BInterface")
				.build()));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("AInterface")
				.build(), JavaType.builder()
				.baseType("BInterface")
				.build()), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testSetImplementsInterfaceNameTexts(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		assertEquals(ListUtil.createList(), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNameTexts(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("SomeInterface")
				.build(), JavaType.builder()
				.baseType("SomeOtherInterface")
				.build()), clazz.getImplementsInterfaceNames());
		clazz.setImplementsInterfaceNameTexts(ListUtil.createList("AInterface", "BInterface"));
		assertEquals(ListUtil.createList(JavaType.builder()
				.baseType("AInterface")
				.build(), JavaType.builder()
				.baseType("BInterface")
				.build()), clazz.getImplementsInterfaceNames());
	}
	
	@Test
	public void testAddStaticCodeBlockComment(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		JavaStaticCodeBlock codeBlock1 = EditableJavaStaticCodeBlock.builder()
				.line("doSomething();")
				.build();
		JavaStaticCodeBlock codeBlock2 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingElse();")
				.build();
		assertEquals(new ArrayList<>(), clazz.getStaticCodeBlocks());
		clazz.addStaticCodeBlock(codeBlock1);
		assertEquals(ListUtil.createList(codeBlock1), clazz.getStaticCodeBlocks());
		clazz.addStaticCodeBlock(codeBlock2);
		assertEquals(ListUtil.createList(codeBlock1, codeBlock2), clazz.getStaticCodeBlocks());
	}
	
	@Test
	public void testAddStaticCodeBlockUneditable(){
		try{
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
			clazz.addStaticCodeBlock(UneditableJavaStaticCodeBlock.builder()
					.line("doSomething();")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable static code blocks", e.getMessage());
		}
	}
	
	@Test
	public void testAddStaticCodeBlockComments(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		JavaStaticCodeBlock codeBlock1 = EditableJavaStaticCodeBlock.builder()
				.line("doSomething();")
				.build();
		JavaStaticCodeBlock codeBlock2 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingElse();")
				.build();
		JavaStaticCodeBlock codeBlock3 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingEvenMore();")
				.build();
		JavaStaticCodeBlock codeBlock4 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingElseEvenMore();")
				.build();
		assertEquals(new ArrayList<>(), clazz.getStaticCodeBlocks());
		clazz.addStaticCodeBlocks(ListUtil.createList(codeBlock1, codeBlock2));
		assertEquals(ListUtil.createList(codeBlock1, codeBlock2), clazz.getStaticCodeBlocks());
		clazz.addStaticCodeBlocks(ListUtil.createList(codeBlock3, codeBlock4));
		assertEquals(ListUtil.createList(codeBlock1, codeBlock2, codeBlock3, codeBlock4), clazz.getStaticCodeBlocks());
	}
	
	@Test
	public void testAddStaticCodeBlocksUneditable(){
		try{
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
			clazz.addStaticCodeBlocks(ListUtil.createList(UneditableJavaStaticCodeBlock.builder()
					.line("doSomething();")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable static code blocks", e.getMessage());
		}
	}
	
	@Test
	public void testSetStaticCodeBlocks(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
		JavaStaticCodeBlock codeBlock1 = EditableJavaStaticCodeBlock.builder()
				.line("doSomething();")
				.build();
		JavaStaticCodeBlock codeBlock2 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingElse();")
				.build();
		JavaStaticCodeBlock codeBlock3 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingEvenMore();")
				.build();
		JavaStaticCodeBlock codeBlock4 = EditableJavaStaticCodeBlock.builder()
				.line("doSomethingElseEvenMore();")
				.build();
		assertEquals(new ArrayList<>(), clazz.getStaticCodeBlocks());
		clazz.setStaticCodeBlocks(ListUtil.createList(codeBlock1, codeBlock2));
		assertEquals(ListUtil.createList(codeBlock1, codeBlock2), clazz.getStaticCodeBlocks());
		clazz.setStaticCodeBlocks(ListUtil.createList(codeBlock3, codeBlock4));
		assertEquals(ListUtil.createList(codeBlock3, codeBlock4), clazz.getStaticCodeBlocks());
	}
	
	@Test
	public void testSetStaticCodeBlocksUneditable(){
		try{
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
			clazz.setStaticCodeBlocks(ListUtil.createList(UneditableJavaStaticCodeBlock.builder()
					.line("doSomething();")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class requires editable static code blocks", e.getMessage());
		}
	}
	
	@Test
	public void testAddSingleLineComment(){
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
			EditableJavaClass clazz = EditableJavaClass.builder()
					.className("AClassName")
					.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
				.build();
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
		EditableJavaClass clazz = EditableJavaClass.builder()
				.className("AClassName")
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
