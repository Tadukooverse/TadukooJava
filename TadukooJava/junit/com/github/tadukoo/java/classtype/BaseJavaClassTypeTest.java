package com.github.tadukoo.java.classtype;

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
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
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

public abstract class BaseJavaClassTypeTest extends BaseJavaCodeTypeTest<JavaClassType>{
	
	private final Supplier<JavaClassTypeBuilder<?>> uneditableBuilder;
	private final Supplier<JavaClassTypeBuilder<?>> editableBuilder;
	private final Function<Builders, JavaClassTypeBuilder<?>> builderGet;
	private final Function<SimpleClassNames, String> classNameGet;
	private final JavaCodeTypes type;
	private final boolean canBeAbstract, canBeFinal;
	private final boolean hasSingleExtendsType, hasMultipleExtendsTypes, hasImplementsTypes;
	private final boolean hasStaticCodeBlocks, hasFields, hasMethods;
	private final boolean hasEnumConstants, hasAnnotationElements;
	
	private final Function<JavaClassType, Boolean> isEditable = JavaClassType::isEditable;
	
	protected BaseJavaClassTypeTest(
			Supplier<JavaClassTypeBuilder<?>> uneditableBuilder, Supplier<JavaClassTypeBuilder<?>> editableBuilder,
			Function<Builders, JavaClassTypeBuilder<?>> builderGet, Function<SimpleClassNames, String> classNameGet,
			JavaCodeTypes type, boolean canBeAbstract, boolean canBeFinal,
			boolean hasSingleExtendsType, boolean hasMultipleExtendsTypes, boolean hasImplementsTypes,
			boolean hasStaticCodeBlocks, boolean hasFields, boolean hasMethods,
			boolean hasEnumConstants, boolean hasAnnotationElements){
		this.uneditableBuilder = uneditableBuilder;
		this.editableBuilder = editableBuilder;
		this.builderGet = builderGet;
		this.classNameGet = classNameGet;
		this.type = type;
		this.canBeAbstract = canBeAbstract;
		this.canBeFinal = canBeFinal;
		this.hasSingleExtendsType = hasSingleExtendsType;
		this.hasMultipleExtendsTypes = hasMultipleExtendsTypes;
		this.hasImplementsTypes = hasImplementsTypes;
		this.hasStaticCodeBlocks = hasStaticCodeBlocks;
		this.hasFields = hasFields;
		this.hasMethods = hasMethods;
		this.hasEnumConstants = hasEnumConstants;
		this.hasAnnotationElements = hasAnnotationElements;
	}
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						uneditableBuilder.get()
								.name("Test")
								.build(),
						false,
						isEditable
				),
				Arguments.of(
						editableBuilder.get()
								.name("Test")
								.build(),
						true,
						isEditable
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
						builders -> type,
						builders ->
								builderGet.apply(builders)
										.name("AClassName")
										.build()
										.getJavaCodeType()
				),
				// Default Is Inner Type
				Pair.of(
						builders -> false,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.isInnerType()
				),
				// Default Package Declaration
				Pair.of(
						builders -> null,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Default Import Statements
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getImportStatements()
				),
				// Default Javadoc
				Pair.of(
						builders -> null,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getJavadoc()
				),
				// Default Annotations
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getAnnotations()
				),
				// Default Visibility
				Pair.of(
						builders -> Visibility.NONE,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getVisibility()
				),
				// Default Static
				Pair.of(
						builders -> false,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.isStatic()
				),
				// Default Single Line Comments
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getSingleLineComments()
				),
				// Default Multi Line Comments
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getMultiLineComments()
				),
				// Default Inner Elements Order
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getInnerElementsOrder()
				),
				// Empty Inner Classes Map
				Pair.of(
						builders -> MapUtil.createMap(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getInnerClassesMap()
				),
				// Inner Classes Map 1 Inner Class
				Pair.of(
						builders -> MapUtil.createMap(
								Pair.of("BClassName", builderGet.apply(builders)
										.innerType()
										.name("BClassName")
										.build())
						),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.build()
								.getInnerClassesMap()
				),
				// Inner Classes Map 2 Inner Classes
				Pair.of(
						builders -> MapUtil.createMap(
								Pair.of("BClassName", builderGet.apply(builders)
										.innerType()
										.name("BClassName")
										.build()),
								Pair.of("CClassName", builderGet.apply(builders)
										.innerType()
										.name("CClassName")
										.build())
						),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("CClassName")
										.build())
								.build()
								.getInnerClassesMap()
				),
				// Copy
				Pair.of(
						builders -> builderGet.apply(builders)
								.packageName("some.package")
								.importName("some.classname", false)
								.javadoc(builders.javadocBuilder().get()
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.visibility(Visibility.PUBLIC)
								.name("AClassName")
								.singleLineComment("some comment")
								.multiLineComment("some content", "more comment")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("CClassName")
										.build())
								.build(),
						builders -> builderGet.apply(builders)
								.copy(builderGet.apply(builders)
										.packageName("some.package")
										.importName("some.classname", false)
										.javadoc(builders.javadocBuilder().get()
												.build())
										.annotation(builders.annotationBuilder().get()
												.name("Test")
												.build())
										.visibility(Visibility.PUBLIC)
										.name("AClassName")
										.singleLineComment("some comment")
										.multiLineComment("some content", "more comment")
										.innerClass(builders.classBuilder().get()
												.innerType()
												.name("CClassName")
												.build())
										.build())
								.build()
				),
				// Set Is Inner Class
				Pair.of(
						builders -> true,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.isInnerType(true)
								.build()
								.isInnerType()
				),
				// Set Inner Class
				Pair.of(
						builders -> true,
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerType()
								.build()
								.isInnerType()
				),
				// Set Package Declaration
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("some.package")
								.build(),
						builders -> builderGet.apply(builders)
								.packageDeclaration(builders.packageDeclarationBuilder().get()
										.packageName("some.package")
										.build())
								.name("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Set Package Declaration
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("some.package")
								.build(),
						builders -> builderGet.apply(builders)
								.packageName("some.package")
								.name("AClassName")
								.build()
								.getPackageDeclaration()
				),
				// Set Class Name by Text
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AClassName")
								.build(),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getName()
				),
				// Set Class Name
				Pair.of(
						builders -> JavaType.builder()
								.baseType("AClassName")
								.build(),
						builders -> builderGet.apply(builders)
								.name(JavaType.builder()
										.baseType("AClassName")
										.build())
								.build()
								.getName()
				),
				// Get Simple Class Name
				Pair.of(
						builders -> "AClassName",
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build()
								.getSimpleName()
				),
				// Get Simple Class Name Complex Class Name
				Pair.of(
						builders -> "AClassName",
						builders -> builderGet.apply(builders)
								.name("AClassName<Map<String,Object>, T>")
								.build()
								.getSimpleName()
				),
				// Set Import Statement
				Pair.of(
						builders -> ListUtil.createList(
								builders.importStatementBuilder().get()
										.importName("com.example")
										.build()
						),
						builders -> builderGet.apply(builders)
								.importStatement(builders.importStatementBuilder().get()
										.importName("com.example")
										.build())
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.importStatements(ListUtil.createList(
										builders.importStatementBuilder().get()
												.importName("com.example")
												.build(),
										builders.importStatementBuilder().get()
												.isStatic()
												.importName("com.other")
												.build()))
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.importName("com.example", false)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.importName("com.example", true)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.importNames(ListUtil.createList("com.example", "com.other"), false)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.importNames(ListUtil.createList("com.example", "com.other"), true)
								.name("AClassName")
								.build()
								.getImportStatements()
				),
				// Set Javadoc
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.build(),
						builders -> builderGet.apply(builders)
								.javadoc(builders.javadocBuilder().get()
										.build())
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.annotations(ListUtil.createList(
										builders.annotationBuilder().get()
												.name("Test")
												.build(),
										builders.annotationBuilder().get()
												.name("Derp")
												.build()
								))
								.name("AClassName")
								.build()
								.getAnnotations()
				),
				// Set Visibility
				Pair.of(
						builders -> Visibility.PRIVATE,
						builders -> builderGet.apply(builders)
								.visibility(Visibility.PRIVATE)
								.name("AClassName")
								.build()
								.getVisibility()
				),
				// Set Is Static Param
				Pair.of(
						builders -> true,
						builders -> builderGet.apply(builders)
								.innerType()
								.isStatic(true)
								.name("AClassName")
								.build()
								.isStatic()
				),
				// Set Is Static No Param
				Pair.of(
						builders -> true,
						builders -> builderGet.apply(builders)
								.innerType()
								.isStatic()
								.name("AClassName")
								.build()
								.isStatic()
				),
				// Set Single Line Comment
				Pair.of(
						builders -> ListUtil.createList(
								builders.singleLineCommentBuilder().get()
										.content("some comment")
										.build()
						),
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.multiLineComment("some comment", "more content")
								.build()
								.getMultiLineComments()
				),
				// Set Inner Class
				Pair.of(
						builders -> ListUtil.createList(
								builderGet.apply(builders)
										.innerType()
										.name("BClassName")
										.build()
						),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.build()
								.getInnerClasses()
				),
				// Set Multiple Inner Classes
				Pair.of(
						builders -> ListUtil.createList(
								builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build(),
								builders.classBuilder().get()
										.innerType()
										.name("CClassName")
										.build()
						),
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClasses(ListUtil.createList(
										builders.classBuilder().get()
												.innerType()
												.name("BClassName")
												.build(),
										builders.classBuilder().get()
												.innerType()
												.name("CClassName")
												.build()))
								.build()
								.getInnerClasses()
				),
				// Equals
				Pair.of(
						builders -> builderGet.apply(builders)
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
								.name("AClassName")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.build(),
						builders -> builderGet.apply(builders)
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
								.name("AClassName")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.build()
				)
		);
		
		// Add Abstract methods if applicable
		if(canBeAbstract){
			// Default Abstract
			comparisons.add(
					Pair.of(
							builders -> false,
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.isAbstract()
					));
			// Set Is Abstract Param
			comparisons.add(
					Pair.of(
							builders -> true,
							builders -> builderGet.apply(builders)
									.isAbstract(true)
									.name("AClassName")
									.build()
									.isAbstract()
					));
			// Set Is Abstract No Param
			comparisons.add(
					Pair.of(
							builders -> true,
							builders -> builderGet.apply(builders)
									.isAbstract()
									.name("AClassName")
									.build()
									.isAbstract()
					));
		}
		
		// Add Final methods if applicable
		if(canBeFinal){
			// Default Final
			comparisons.add(
					Pair.of(
					builders -> false,
					builders -> builderGet.apply(builders)
							.name("AClassName")
							.build()
							.isFinal()
					));
			// Set Is Final Param
			comparisons.add(
					Pair.of(
							builders -> true,
							builders -> builderGet.apply(builders)
									.isFinal(true)
									.name("AClassName")
									.build()
									.isFinal()
					));
			// Set Is Final No Param
			comparisons.add(
					Pair.of(
							builders -> true,
							builders -> builderGet.apply(builders)
									.isFinal()
									.name("AClassName")
									.build()
									.isFinal()
					));
		}
		
		// Add Extends Types if applicable
		if(hasSingleExtendsType || hasMultipleExtendsTypes){
			// Default Extends Types
			comparisons.add(
					Pair.of(
							builders -> new ArrayList<>(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getExtendsTypes()
					));
			// Set Super Class Name by Text
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(JavaType.builder()
									.baseType("AnotherClassName")
									.build()),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.extendsType("AnotherClassName")
									.build()
									.getExtendsTypes()
					));
			// Set Super Class Name
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(JavaType.builder()
									.baseType("AnotherClassName")
									.build()),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.extendsType(JavaType.builder()
											.baseType("AnotherClassName")
											.build())
									.build()
									.getExtendsTypes()
					));
		}
		
		// Add Implements Types if applicable
		if(hasImplementsTypes){
			// Default Implements Types
			comparisons.add(
					Pair.of(
							builders -> new ArrayList<>(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getImplementsTypes()
					));
			// Set Implements Type by Text
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									JavaType.builder()
											.baseType("SomeInterface")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsType("SomeInterface")
									.build()
									.getImplementsTypes()
					));
			// Set Implements Type
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									JavaType.builder()
											.baseType("SomeInterface")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsType(JavaType.builder()
											.baseType("SomeInterface")
											.build())
									.build()
									.getImplementsTypes()
					));
			// Set Implements Types by Text
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									JavaType.builder()
											.baseType("SomeInterface")
											.build(),
									JavaType.builder()
											.baseType("SomeOtherInterface")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsTypeTexts(ListUtil.createList(
											"SomeInterface", "SomeOtherInterface"))
									.build()
									.getImplementsTypes()
					));
			// Set Implements Types
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									JavaType.builder()
											.baseType("SomeInterface")
											.build(),
									JavaType.builder()
											.baseType("SomeOtherInterface")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsTypes(ListUtil.createList(
											JavaType.builder()
													.baseType("SomeInterface")
													.build(),
											JavaType.builder()
													.baseType("SomeOtherInterface")
													.build()))
									.build()
									.getImplementsTypes()
					));
		}
		
		// Add Static Code Blocks if applicable
		if(hasStaticCodeBlocks){
			// Default Static Code Blocks
			comparisons.add(
					Pair.of(
							builders -> new ArrayList<>(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getStaticCodeBlocks()
					));
			// Set Static Code Block
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.staticCodeBlockBuilder().get()
											.line("doSomething();")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(builders.staticCodeBlockBuilder().get()
											.line("doSomething();")
											.build())
									.build()
									.getStaticCodeBlocks()
					));
			// Set Multiple Static Code Blocks
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.staticCodeBlockBuilder().get()
											.line("doSomething();")
											.build(),
									builders.staticCodeBlockBuilder().get()
											.line("doSomethingElse();")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlocks(ListUtil.createList(builders.staticCodeBlockBuilder().get()
													.line("doSomething();")
													.build(),
											builders.staticCodeBlockBuilder().get()
													.line("doSomethingElse();")
													.build()))
									.build()
									.getStaticCodeBlocks()
					));
			// Set Static Code Block using lines
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.staticCodeBlockBuilder().get()
											.line("doSomething();")
											.line("doSomethingElse();")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(ListUtil.createList(
											"doSomething();",
											"doSomethingElse();"
									))
									.build()
									.getStaticCodeBlocks()
					));
		}
		
		// Add Fields if applicable
		if(hasFields){
			// Default Fields
			comparisons.add(
					Pair.of(
							builders -> new ArrayList<>(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getFields()
					));
			// Fields Map No Fields
			comparisons.add(
					Pair.of(
							builders -> MapUtil.createMap(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getFieldsMap()
					));
			// Fields Map 1 Field
			comparisons.add(
					Pair.of(
							builders -> MapUtil.createMap(
									Pair.of("test", builders.fieldBuilder().get()
											.type("String").name("test")
											.build())
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.field(builders.fieldBuilder().get()
											.type("String").name("test")
											.build())
									.build()
									.getFieldsMap()
					));
			// Fields Map 2 Fields
			comparisons.add(
					Pair.of(
							builders -> MapUtil.createMap(
									Pair.of("test", builders.fieldBuilder().get()
											.type("String").name("test")
											.build()),
									Pair.of("version", builders.fieldBuilder().get()
											.type("int").name("version")
											.build())
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.field(builders.fieldBuilder().get()
											.type("String").name("test")
											.build())
									.field(builders.fieldBuilder().get()
											.type("int").name("version")
											.build())
									.build()
									.getFieldsMap()
					));
			// Set Field
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.fieldBuilder().get()
											.type("int").name("test")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.field(builders.fieldBuilder().get()
											.type("int").name("test")
											.build())
									.build()
									.getFields()
					));
			// Set Multiple Fields
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.fieldBuilder().get()
											.type("int").name("test")
											.build(),
									builders.fieldBuilder().get()
											.type("String").name("derp")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.fields(ListUtil.createList(
											builders.fieldBuilder().get()
													.type("int").name("test")
													.build(),
											builders.fieldBuilder().get()
													.type("String").name("derp")
													.build()))
									.build()
									.getFields()
					));
		}
		
		// Add Methods if applicable
		if(hasMethods){
			// Default Methods
			comparisons.add(
					Pair.of(
							builders -> new ArrayList<>(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getMethods()
					));
			// Methods Map No Methods
			comparisons.add(
					Pair.of(
							builders -> MapUtil.createMap(),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.build()
									.getMethodsMap()
					));
			// Methods Map 1 Method
			comparisons.add(
					Pair.of(
							builders -> MapUtil.createMap(
									Pair.of("getType()", builders.methodBuilder().get()
											.returnType("String").name("getType")
											.build())
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.method(builders.methodBuilder().get()
											.returnType("String").name("getType")
											.build())
									.build()
									.getMethodsMap()
					));
			// Methods Map 2 Methods
			comparisons.add(
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
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.method(builders.methodBuilder().get()
											.returnType("String").name("getType")
											.build())
									.method(builders.methodBuilder().get()
											.returnType("int").name("getVersion")
											.parameter("String type")
											.build())
									.build()
									.getMethodsMap()
					));
			// Set Method
			comparisons.add(
					Pair.of(
							builders -> ListUtil.createList(
									builders.methodBuilder().get()
											.returnType("int").name("test")
											.build()
							),
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.method(builders.methodBuilder().get()
											.returnType("int").name("test")
											.build())
									.build()
									.getMethods()
					));
			// Set Multiple Methods
			comparisons.add(
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
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
					));
		}
		
		// Add Enum Constants if applicable
		if(hasEnumConstants){
			// TODO: Add Enum Constants tests
		}
		
		// Add Annotation Elements if applicable
		if(hasAnnotationElements){
			// TODO: Add Annotation Elements tests
		}
		
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
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build(),
						builders -> builderGet.apply(builders)
								.packageName("some.package.different")
								.name("AClassName")
								.build()
				),
				// Different Types
				Pair.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
				Function<Builders, JavaClassType>,
				String,
				Function<SimpleClassNames, String>
				>> comparisons = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.build(),
						"""
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.build()"""
				),
				// With Package Name
				Triple.of(
						builders -> builderGet.apply(builders)
								.packageName("some.package")
								.name("AClassName")
								.build(),
						"""
								package some.package;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.packageName("some.package")
										.name("AClassName")
										.build()"""
				),
				// With Import
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.example.*", false)
								.name("AClassName")
								.build(),
						"""
								import com.example.*;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", false)
										.name("AClassName")
										.build()"""
				),
				// With Imports Same Base in Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.example.*", false)
								.importName("com.whatever", false)
								.name("AClassName")
								.build(),
						"""
								import com.example.*;
								import com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.name("AClassName")
										.build()"""
				),
				// With Imports Same Base Reverse Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.whatever", false)
								.importName("com.example.*", false)
								.name("AClassName")
								.build(),
						"""
								import com.example.*;
								import com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.name("AClassName")
										.build()"""
				),
				// With Imports Different Base Strange Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.whatever", false)
								.importName("org.yep", false)
								.importName("com.example.*", false)
								.importName("org.test", false)
								.name("AClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.name("AClassName")
										.build()"""
				),
				// With Static Import
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.example.*", true)
								.name("AClassName")
								.build(),
						"""
								import static com.example.*;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", true)
										.name("AClassName")
										.build()"""
				),
				// With Static Imports Same Base in Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.example.*", true)
								.importName("com.whatever", true)
								.name("AClassName")
								.build(),
						"""
								import static com.example.*;
								import static com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.name("AClassName")
										.build()"""
				),
				// With Static Imports Same Base Reverse Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.whatever", true)
								.importName("com.example.*", true)
								.name("AClassName")
								.build(),
						"""
								import static com.example.*;
								import static com.whatever;
								
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.name("AClassName")
										.build()"""
				),
				// With Static Imports Different Base Strange Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.whatever", true)
								.importName("org.yep", true)
								.importName("com.example.*", true)
								.importName("org.test", true)
								.name("AClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", true)
										.importName("com.whatever", true)
										.importName("org.test", true)
										.importName("org.yep", true)
										.name("AClassName")
										.build()"""
				),
				// With Regular and Static Imports Different Base Strange Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.importName("com.whatever", false)
								.importName("com.whatever.electric_boogaloo", true)
								.importName("org.yep", false)
								.importName("org.yep.dope", true)
								.importName("com.example.*", false)
								.importName("com.example.test.*", true)
								.importName("org.test", false)
								.importName("org.test.yep", true)
								.name("AClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.importName("com.example.*", false)
										.importName("com.whatever", false)
										.importName("org.test", false)
										.importName("org.yep", false)
										.importName("com.example.test.*", true)
										.importName("com.whatever.electric_boogaloo", true)
										.importName("org.test.yep", true)
										.importName("org.yep.dope", true)
										.name("AClassName")
										.build()"""
				),
				// With Visibility
				Triple.of(
						builders -> builderGet.apply(builders)
								.visibility(Visibility.PROTECTED)
								.name("AClassName")
								.build(),
						"""
								protected class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.visibility(Visibility.PROTECTED)
										.name("AClassName")
										.build()"""
				),
				// With Javadoc
				Triple.of(
						builders -> builderGet.apply(builders)
								.javadoc(builders.javadocBuilder().get()
										.build())
								.name("AClassName")
								.build(),
						"""
								/**
								 */
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.javadoc(""" + classNames.javadocSimpleClassName() + """
								.builder()
												.build())
										.name("AClassName")
										.build()"""
				),
				// With Annotation
				Triple.of(
						builders -> builderGet.apply(builders)
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.name("AClassName")
								.build(),
						"""
								@Test
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.name("AClassName")
										.build()"""
				),
				// With Multiple Annotations
				Triple.of(
						builders -> builderGet.apply(builders)
								.annotation(builders.annotationBuilder().get()
										.name("Test")
										.build())
								.annotation(builders.annotationBuilder().get()
										.name("Derp")
										.build())
								.name("AClassName")
								.build(),
						"""
								@Test
								@Derp
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Test")
												.build())
										.annotation(""" + classNames.annotationSimpleClassName() + """
								.builder()
												.name("Derp")
												.build())
										.name("AClassName")
										.build()"""
				),
				// With Single-Line Comment
				Triple.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.singleLineComment("some comment")
								.build(),
						"""
								class AClassName{
								\t
									// some comment
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.singleLineComment("some comment")
										.build()"""
				),
				// With Multi-Line Comment
				Triple.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.multiLineComment("some comment",\s
												"more content")
										.build()"""
				),
				// With Inner Classes
				Triple.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("CClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.build()"""
				),
				// With Inner Classes Reverse Order
				Triple.of(
						builders -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("CClassName")
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builderGet.apply(builders)
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
								.name("AClassName")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
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
								public class AClassName{
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
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
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
										.name("AClassName")
										.singleLineComment("some comment")
										.multiLineComment("some comment",\s
												"more content")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.build()"""
				),
				// Inner Class
				Triple.of(
						builders -> builderGet.apply(builders)
								.innerType()
								.name("AClassName")
								.build(),
						"""
								class AClassName{
								\t
								}
								""",
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.innerType()
										.name("AClassName")
										.build()"""
				),
				// Inner Class Static
				Triple.of(
						builders -> builders.classBuilder().get()
								.innerType()
								.isStatic()
								.name("AClassName")
								.build(),
						"""
								static class AClassName{
								\t
								}
								""",
						classNames -> classNames.classSimpleClassName() + """
								.builder()
										.innerType()
										.isStatic()
										.name("AClassName")
										.build()"""
				),
				// Inner Class With Everything
				Triple.of(
						builders -> builderGet.apply(builders)
								.innerType()
								.javadoc(builders.javadocBuilder().get().build())
								.annotation(builders.annotationBuilder().get().name("Test").build())
								.annotation(builders.annotationBuilder().get().name("Derp").build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.name("AClassName")
								.innerClass(builders.classBuilder().get().innerType().name("BClassName").build())
								.innerClass(builders.classBuilder().get().innerType().name("CClassName").build())
								.build(),
						"""
								/**
								 */
								@Test
								@Derp
								public static class AClassName{
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
						classNames -> classNameGet.apply(classNames) + """
								.builder()
										.innerType()
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
										.name("AClassName")
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("BClassName")
												.build())
										.innerClass(""" + classNames.classSimpleClassName() + """
								.builder()
												.innerType()
												.name("CClassName")
												.build())
										.build()"""
				));
		
		// Add Abstract if applicable
		if(canBeAbstract){
			// With Abstract
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.isAbstract()
									.name("AClassName")
									.build(),
							"""
									abstract class AClassName{
									\t
									}
									""",
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.isAbstract()
										.name("AClassName")
										.build()"""
					)
			);
		}
		
		// Add Final if applicable
		if(canBeFinal){
			// With Final
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.isFinal()
									.name("AClassName")
									.build(),
							"""
									final class AClassName{
									\t
									}
									""",
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.isFinal()
										.name("AClassName")
										.build()"""
					)
			);
		}
		
		// Add Extends Types if applicable
		if(hasSingleExtendsType || hasMultipleExtendsTypes){
			// With one extends type
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.extendsType("AnotherClassName")
									.build(),
							"""
									class AClassName extends AnotherClassName{
									\t
									}
									""",
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.extendsType("AnotherClassName")
										.build()"""
					)
			);
		}
		
		// Add Implements Types if applicable
		if(hasImplementsTypes){
			// With Implements Interface
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsType("SomeInterface")
									.build(),
							"""
									class AClassName implements SomeInterface{
									\t
									}
									""",
							classNames -> classNameGet.apply(classNames) + """
										.builder()
												.name("AClassName")
												.implementsType("SomeInterface")
												.build()"""
					));
			// With Multiple Implements Interfaces
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
									.implementsType("SomeInterface")
									.implementsType("SomeOtherInterface")
									.build(),
							"""
									class AClassName implements SomeInterface, SomeOtherInterface{
									\t
									}
									""",
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.implementsType("SomeInterface")
										.implementsType("SomeOtherInterface")
										.build()"""
					));
		}
		
		// Add Static Code Blocks if applicable
		if(hasStaticCodeBlocks){
			// With Static Code Block
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
										.builder()
												.name("AClassName")
												.staticCodeBlock(ListUtil.createList(
														"doSomething();"
												))
												.build()"""
					));
			// With Multiline Static Code Block
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.staticCodeBlock(ListUtil.createList(
												"doSomething();",
												"doSomethingElse();"
										))
										.build()"""
					));
		}
		
		// Add Fields if applicable
		if(hasFields){
			// With Fields
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
										.builder()
												.name("AClassName")
												.field(""" + classNames.fieldSimpleClassName() + """
										.builder()
														.type("int").name("test")
														.build())
												.field(""" + classNames.fieldSimpleClassName() + """
										.builder()
														.type("String").name("derp")
														.build())
												.build()"""
					));
			// With Fields Swap Order
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("String").name("derp")
												.build())
										.field(""" + classNames.fieldSimpleClassName() + """
								.builder()
												.type("int").name("test")
												.build())
										.build()"""
					));
			// With Fields with Javadoc on Field
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
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
					));
		}
		
		// Add Methods if applicable
		if(hasMethods){
			// With Methods
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
										.builder()
												.name("AClassName")
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
					));
			// With Methods Swap Order
			comparisons.add(
					Triple.of(
							builders -> builderGet.apply(builders)
									.name("AClassName")
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
							classNames -> classNameGet.apply(classNames) + """
								.builder()
										.name("AClassName")
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
					));
		}
		
		// Add specific comparisons if we have them
		List<Triple<Function<Builders, JavaClassType>, String, Function<SimpleClassNames, String>>> specific =
				getClassTypeSpecificStringData();
		if(ListUtil.isNotBlank(specific)){
			comparisons.addAll(specific);
		}
		
		return comparisons.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	protected abstract List<Triple<Function<Builders, JavaClassType>, String, Function<SimpleClassNames, String>>>
	getClassTypeSpecificStringData();
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaClassType>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Null Visibility
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.visibility(null)
								.name("AClassName")
								.build(),
						"Visibility is required!"
				),
				// Null Class Name
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.build(),
						"Must specify name!"
				),
				// Inner Class Not Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("BClassName")
								.innerClass(builders.classBuilder().get()
										.name("AClassName")
										.build())
								.build(),
						"Inner class type 'AClassName' is not an inner type!"
				),
				// Outer Class Can't be Static
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.isStatic()
								.name("AClassName")
								.build(),
						"Only inner types can be static!"
				),
				// All Outer Class Errors
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.isStatic()
								.innerClass(builders.classBuilder().get()
										.name("AClassName")
										.build())
								.visibility(null)
								.build(),
						"""
								Visibility is required!
								Must specify name!
								Inner class type 'AClassName' is not an inner type!
								Only inner types can be static!"""
				),
				// Null Visibility Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.visibility(null)
								.name("AClassName")
								.build(),
						"Visibility is required!"
				),
				// Null Class Name Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.build(),
						"Must specify name!"
				),
				// Inner Class in Inner Class Not Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.name("BClassName")
								.innerClass(builders.classBuilder().get()
										.name("AClassName")
										.build())
								.build(),
						"Inner class type 'AClassName' is not an inner type!"
				),
				// Package Declaration Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.packageName("some.package")
								.name("AClassName")
								.build(),
						"Not allowed to have package declaration for an inner type!"
				),
				// Import Statement Inner Class
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.importName("com.example", false)
								.name("AClassName")
								.build(),
						"Not allowed to have import statements for an inner type!"
				),
				// All Inner Class Builder Errors
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.innerType()
								.packageName("some.package")
								.innerClass(builders.classBuilder().get()
										.packageName("some.package")
										.name("AClassName")
										.build())
								.importName("com.example", false)
								.visibility(null)
								.build(),
						"""
							Visibility is required!
							Must specify name!
							Inner class type 'AClassName' is not an inner type!
							Not allowed to have package declaration for an inner type!
							Not allowed to have import statements for an inner type!"""
				),
				// Missing Inner Elements Order when Single Line Comment Present
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.singleLineComment("some comment")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Missing Inner Elements Order when Multi Line Comment Present
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.multiLineComment("some comment", "more content")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Missing Inner Elements Order when Both Types of Comment Present
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.singleLineComment("some comment")
								.multiLineComment("some comment", "more content")
								.innerElementsOrder(new ArrayList<>())
								.build(),
						"innerElementsOrder is required when static code blocks or comments are present!"
				),
				// Too Many Single Line Comments in Elements Order
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.CLASS, "BClassName"),
										Pair.of(JavaCodeTypes.CLASS, "CClassName")
								))
								.build(),
						"Unknown inner class name: CClassName"
				),
				// Unknown Inner Element Type
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.innerElementsOrder(ListUtil.createList(Pair.of(JavaCodeTypes.ANNOTATION, "something")))
								.build(),
						"Unknown inner element type: " + JavaCodeTypes.ANNOTATION.getStandardName()
				),
				// Missed 1 Single Line Comment in Elements Order
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
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
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.singleLineComment("some comment")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following inner classes were not specified in innerElementsOrder: BClassName"
				),
				// Missed 2 Inner Classes in Elements Order
				Pair.of(
						builders -> () -> builderGet.apply(builders)
								.name("AClassName")
								.singleLineComment("some comment")
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("BClassName")
										.build())
								.innerClass(builders.classBuilder().get()
										.innerType()
										.name("CClassName")
										.build())
								.innerElementsOrder(ListUtil.createList(
										Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
								))
								.build(),
						"The following inner classes were not specified in innerElementsOrder: BClassName,CClassName"
				)
		);
		
		List<Pair<Supplier<JavaClassType>, String>> editableRelatedErrors = ListUtil.createList(
				// Editable Package Declaration in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.name("AClassName")
								.build(),
						"package declaration is not uneditable in this uneditable JavaClassType"
				),
				// Editable Import Statement in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.name("AClassName")
								.build(),
						"some import statements are not uneditable in this uneditable JavaClassType"
				),
				// Editable Javadoc in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.javadoc(EditableJavadoc.builder()
										.build())
								.name("AClassName")
								.build(),
						"javadoc is not uneditable in this uneditable JavaClassType"
				),
				// Editable Annotation in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.name("AClassName")
								.build(),
						"some annotations are not uneditable in this uneditable JavaClassType"
				),
				// Editable Single Line Comment in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.name("AClassName")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some single-line comments are not uneditable in this uneditable JavaClassType"
				),
				// Editable Multi Line Comment in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.name("AClassName")
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some multi-line comments are not uneditable in this uneditable JavaClassType"
				),
				// Editable Inner Class in Uneditable JavaClass
				Pair.of(
						() -> uneditableBuilder.get()
								.name("AClassName")
								.innerClass(EditableJavaClass.builder()
										.innerType()
										.name("BClassName")
										.build())
								.build(),
						"some inner classes are not uneditable in this uneditable JavaClassType"
				),
				// Uneditable Package Declaration in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.packageDeclaration(UneditableJavaPackageDeclaration.builder()
										.packageName("some.package")
										.build())
								.name("AClassName")
								.build(),
						"package declaration is not editable in this editable JavaClassType"
				),
				// Uneditable Import Statement in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.importStatement(UneditableJavaImportStatement.builder()
										.importName("com.example")
										.build())
								.name("AClassName")
								.build(),
						"some import statements are not editable in this editable JavaClassType"
				),
				// Uneditable Javadoc in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.javadoc(UneditableJavadoc.builder()
										.build())
								.name("AClassName")
								.build(),
						"javadoc is not editable in this editable JavaClassType"
				),
				// Uneditable Annotation in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.annotation(UneditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.name("AClassName")
								.build(),
						"some annotations are not editable in this editable JavaClassType"
				),
				// Uneditable Single Line Comment in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.name("AClassName")
								.singleLineComment(UneditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some single-line comments are not editable in this editable JavaClassType"
				),
				// Uneditable Multi Line Comment in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.name("AClassName")
								.multiLineComment(UneditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.build(),
						"some multi-line comments are not editable in this editable JavaClassType"
				),
				// Uneditable Inner Class in Editable JavaClass
				Pair.of(
						() -> editableBuilder.get()
								.name("AClassName")
								.innerClass(UneditableJavaClass.builder()
										.innerType()
										.name("BClassName")
										.build())
								.build(),
						"some inner classes are not editable in this editable JavaClassType"
				)
		);
		
		// Add Abstract if applicable
		if(canBeAbstract){
			// Abstract Static
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.innerType()
									.isAbstract().isStatic()
									.build(),
							"Can't be abstract and static!"
					));
			
			// Add Abstract + Final if applicable
			if(canBeFinal){
				// Abstract Final
				builderFuncsAndErrorMessages.add(
						Pair.of(
								builders -> () -> builderGet.apply(builders)
										.name("AClassName")
										.innerType()
										.isAbstract().isFinal()
										.build(),
								"Can't be abstract and final!"
						));
				// Abstract Static Final
				builderFuncsAndErrorMessages.add(
						Pair.of(
								builders -> () -> builderGet.apply(builders)
										.name("AClassName")
										.innerType()
										.isAbstract().isStatic().isFinal()
										.build(),
								"Can't be abstract and static!\n" +
										"Can't be abstract and final!"
						));
			}
		}
		
		// Add Static Code Blocks if applicable
		if(hasStaticCodeBlocks){
			// Missing Inner Elements Order when Static Code Block Present
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(ListUtil.createList("doSomething();"))
									.innerElementsOrder(new ArrayList<>())
									.build(),
							"innerElementsOrder is required when static code blocks or comments are present!"
					));
			// Too Many Static Code Blocks in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(ListUtil.createList("doSomething();"))
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null),
											Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
									))
									.build(),
							"Specified more static code blocks in innerElementsOrder than we have!"
					));
			// Missed 1 Static Code Block in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(ListUtil.createList("doSomething();"))
									.staticCodeBlock(ListUtil.createList("doSomethingElse();"))
									.staticCodeBlock(ListUtil.createList("doSomethingEvenMore();"))
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null),
											Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
									))
									.build(),
							"Missed 1 static code blocks in innerElementsOrder!"
					));
			// Missed 2 Static Code Blocks in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.staticCodeBlock(ListUtil.createList("doSomething();"))
									.staticCodeBlock(ListUtil.createList("doSomethingElse();"))
									.staticCodeBlock(ListUtil.createList("doSomethingEvenMore();"))
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null)
									))
									.build(),
							"Missed 2 static code blocks in innerElementsOrder!"
					));
			// Editable Static Code Block in Uneditable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> uneditableBuilder.get()
									.name("AClassName")
									.staticCodeBlock(EditableJavaStaticCodeBlock.builder()
											.line("some comment")
											.build())
									.build(),
							"some static code blocks are not uneditable in this uneditable JavaClassType"
					));
			// Uneditable Static Code Block in Editable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> editableBuilder.get()
									.name("AClassName")
									.staticCodeBlock(UneditableJavaStaticCodeBlock.builder()
											.line("doSomething();")
											.build())
									.build(),
							"some static code blocks are not editable in this editable JavaClassType"
					));
		}
		
		// Add Fields if applicable
		if(hasFields){
			// Reused Field Name in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.field(builders.fieldBuilder().get()
											.type("int").name("test")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.FIELD, "test"),
											Pair.of(JavaCodeTypes.FIELD, "test")
									))
									.build(),
							"Already used field named: test"
					));
			// Unknown Field Name in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.field(builders.fieldBuilder().get()
											.type("int").name("test")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.FIELD, "test"),
											Pair.of(JavaCodeTypes.FIELD, "derp")
									))
									.build(),
							"Unknown field name: derp"
					));
			// Missed 1 Field in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.singleLineComment("some comment")
									.field(builders.fieldBuilder().get()
											.type("int").name("version")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
									))
									.build(),
							"The following fields were not specified in innerElementsOrder: version"
					));
			// Missed 2 Fields in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
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
					));
			// Editable Field in Uneditable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> uneditableBuilder.get()
									.name("AClassName")
									.field(EditableJavaField.builder()
											.type("int").name("version")
											.build())
									.build(),
							"some fields are not uneditable in this uneditable JavaClassType"
					));
			// Uneditable Field in Editable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> editableBuilder.get()
									.name("AClassName")
									.field(UneditableJavaField.builder()
											.type("int").name("version")
											.build())
									.build(),
							"some fields are not editable in this editable JavaClassType"
					));
		}
		
		// Add Methods if applicable
		if(hasMethods){
			// Reused Method Name in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.method(builders.methodBuilder().get()
											.returnType("int").name("getVersion")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
											Pair.of(JavaCodeTypes.METHOD, "getVersion()")
									))
									.build(),
							"Already used method named: getVersion()"
					));
			// Unknown Method Name in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.method(builders.methodBuilder().get()
											.returnType("int").name("getVersion")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.METHOD, "getVersion()"),
											Pair.of(JavaCodeTypes.METHOD, "getSomething()")
									))
									.build(),
							"Unknown method name: getSomething()"
					));
			// Missed 1 Method in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
									.singleLineComment("some comment")
									.method(builders.methodBuilder().get()
											.returnType("int").name("getVersion")
											.build())
									.innerElementsOrder(ListUtil.createList(
											Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null)
									))
									.build(),
							"The following methods were not specified in innerElementsOrder: getVersion()"
					));
			// Missed 2 Methods in Elements Order
			builderFuncsAndErrorMessages.add(
					Pair.of(
							builders -> () -> builderGet.apply(builders)
									.name("AClassName")
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
					));
			// Editable Method in Uneditable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> uneditableBuilder.get()
									.name("AClassName")
									.method(EditableJavaMethod.builder()
											.returnType("AClassName")
											.build())
									.build(),
							"some methods are not uneditable in this uneditable JavaClassType"
					));
			// Uneditable Method in Editable JavaClass
			editableRelatedErrors.add(
					Pair.of(
							() -> editableBuilder.get()
									.name("AClassName")
									.method(UneditableJavaMethod.builder()
											.returnType("AClassName")
											.build())
									.build(),
							"some methods are not editable in this editable JavaClassType"
					));
		}
		
		// Check for class type specific data
		List<Pair<Function<Builders, Supplier<? extends JavaClassType>>, String>> classTypeSpecificData =
				getClassTypeSpecificBuilderErrorData();
		if(ListUtil.isNotBlank(classTypeSpecificData)){
			builderFuncsAndErrorMessages.addAll(classTypeSpecificData);
		}
		
		return Stream.concat(builderFuncsAndErrorMessages.stream()
						.flatMap(pair -> Stream.of(0, 1, 2)
								.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
										pair.getRight()))),
				editableRelatedErrors.stream()
						.map(pair -> Arguments.of(pair.getLeft(), pair.getRight())));
	}
	
	protected abstract List<Pair<Function<Builders, Supplier<? extends JavaClassType>>, String>>
	getClassTypeSpecificBuilderErrorData();
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetInnerType(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertFalse(clazz.isInnerType());
		clazz.setInnerType(false);
		assertFalse(clazz.isInnerType());
		clazz.setInnerType(true);
		assertTrue(clazz.isInnerType());
	}
	
	@Test
	public void testSetPackageDeclaration(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setPackageDeclaration(UneditableJavaPackageDeclaration.builder()
					.packageName("some.package")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable package declaration", e.getMessage());
		}
	}
	
	@Test
	public void testSetPackageName(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertNull(clazz.getPackageDeclaration());
		clazz.setPackageName("some.random.package.name");
		assertEquals(EditableJavaPackageDeclaration.builder()
				.packageName("some.random.package.name")
				.build(), clazz.getPackageDeclaration());
	}
	
	@Test
	public void testAddImportStatement(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addImportStatement(UneditableJavaImportStatement.builder()
					.importName("com.example")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testAddImportStatements(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
			assertEquals("editable Java Class Type requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testSetImportStatements(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
			assertEquals("editable Java Class Type requires editable import statements", e.getMessage());
		}
	}
	
	@Test
	public void testAddImportName(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertNull(clazz.getJavadoc());
		clazz.setJavadoc(EditableJavadoc.builder().build());
		assertEquals(EditableJavadoc.builder().build(), clazz.getJavadoc());
	}
	
	@Test
	public void testSetJavadocUneditable(){
		try{
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setJavadoc(UneditableJavadoc.builder().build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable Javadoc", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotation(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addAnnotation(UneditableJavaAnnotation.builder().name("Test").build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testAddAnnotations(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addAnnotations(ListUtil.createList(
					UneditableJavaAnnotation.builder().name("Test").build(),
					UneditableJavaAnnotation.builder().name("Derp").build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetAnnotations(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setAnnotations(ListUtil.createList(
					UneditableJavaAnnotation.builder().name("Test").build(),
					UneditableJavaAnnotation.builder().name("Derp").build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable Java Annotations", e.getMessage());
		}
	}
	
	@Test
	public void testSetVisibility(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertEquals(Visibility.NONE, clazz.getVisibility());
		clazz.setVisibility(Visibility.PRIVATE);
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetAbstract(){
		if(canBeAbstract){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertFalse(clazz.isAbstract());
			clazz.setAbstract(false);
			assertFalse(clazz.isAbstract());
			clazz.setAbstract(true);
			assertTrue(clazz.isAbstract());
		}
	}
	
	@Test
	public void testSetStatic(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertFalse(clazz.isStatic());
		clazz.setStatic(false);
		assertFalse(clazz.isStatic());
		clazz.setStatic(true);
		assertTrue(clazz.isStatic());
	}
	
	@Test
	public void testSetFinal(){
		if(canBeFinal){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertFalse(clazz.isFinal());
			clazz.setFinal(false);
			assertFalse(clazz.isFinal());
			clazz.setFinal(true);
			assertTrue(clazz.isFinal());
		}
	}
	
	@Test
	public void testSetClassName(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertEquals(JavaType.builder()
				.baseType("AClassName")
				.build(), clazz.getName());
		clazz.setName(JavaType.builder()
				.baseType("SomethingElse")
				.build());
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getName());
	}
	
	@Test
	public void testSetClassNameByText(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		assertEquals(JavaType.builder()
				.baseType("AClassName")
				.build(), clazz.getName());
		clazz.setName("SomethingElse");
		assertEquals(JavaType.builder()
				.baseType("SomethingElse")
				.build(), clazz.getName());
	}
	
	@Test
	public void testAddImplementsType(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.addImplementsType(JavaType.builder()
					.baseType("SomeInterface")
					.build());
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build()), clazz.getImplementsTypes());
			clazz.addImplementsType(JavaType.builder()
					.baseType("SomeOtherInterface")
					.build());
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testAddImplementsTypeByText(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.addImplementsType("SomeInterface");
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build()), clazz.getImplementsTypes());
			clazz.addImplementsType("SomeOtherInterface");
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testAddImplementsTypes(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.addImplementsTypes(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
			clazz.addImplementsTypes(ListUtil.createList(JavaType.builder()
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
					clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testAddImplementsTypesTexts(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.addImplementsTypeTexts(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
			clazz.addImplementsTypeTexts(ListUtil.createList("AInterface", "BInterface"));
			assertEquals(ListUtil.createList(JavaType.builder()
							.baseType("SomeInterface")
							.build(), JavaType.builder()
							.baseType("SomeOtherInterface")
							.build(), JavaType.builder()
							.baseType("AInterface")
							.build(), JavaType.builder()
							.baseType("BInterface")
							.build()),
					clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testSetImplementsTypes(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.setImplementsTypes(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
			clazz.setImplementsTypes(ListUtil.createList(JavaType.builder()
					.baseType("AInterface")
					.build(), JavaType.builder()
					.baseType("BInterface")
					.build()));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("AInterface")
					.build(), JavaType.builder()
					.baseType("BInterface")
					.build()), clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testSetImplementsTypesTexts(){
		if(hasImplementsTypes){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			assertEquals(ListUtil.createList(), clazz.getImplementsTypes());
			clazz.setImplementsTypeTexts(ListUtil.createList("SomeInterface", "SomeOtherInterface"));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("SomeInterface")
					.build(), JavaType.builder()
					.baseType("SomeOtherInterface")
					.build()), clazz.getImplementsTypes());
			clazz.setImplementsTypeTexts(ListUtil.createList("AInterface", "BInterface"));
			assertEquals(ListUtil.createList(JavaType.builder()
					.baseType("AInterface")
					.build(), JavaType.builder()
					.baseType("BInterface")
					.build()), clazz.getImplementsTypes());
		}
	}
	
	@Test
	public void testAddStaticCodeBlock(){
		if(hasStaticCodeBlocks){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddStaticCodeBlockUneditable(){
		if(hasStaticCodeBlocks){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
						.build();
				clazz.addStaticCodeBlock(UneditableJavaStaticCodeBlock.builder()
						.line("doSomething();")
						.build());
				fail();
			}catch(IllegalArgumentException e){
				assertEquals("editable Java Class Type requires editable static code blocks", e.getMessage());
			}
		}
	}
	
	@Test
	public void testAddStaticCodeBlocks(){
		if(hasStaticCodeBlocks){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddStaticCodeBlocksUneditable(){
		if(hasStaticCodeBlocks){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
						.build();
				clazz.addStaticCodeBlocks(ListUtil.createList(UneditableJavaStaticCodeBlock.builder()
						.line("doSomething();")
						.build()));
				fail();
			}catch(IllegalArgumentException e){
				assertEquals("editable Java Class Type requires editable static code blocks", e.getMessage());
			}
		}
	}
	
	@Test
	public void testSetStaticCodeBlocks(){
		if(hasStaticCodeBlocks){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testSetStaticCodeBlocksUneditable(){
		if(hasStaticCodeBlocks){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
						.build();
				clazz.setStaticCodeBlocks(ListUtil.createList(UneditableJavaStaticCodeBlock.builder()
						.line("doSomething();")
						.build()));
				fail();
			}catch(IllegalArgumentException e){
				assertEquals("editable Java Class Type requires editable static code blocks", e.getMessage());
			}
		}
	}
	
	@Test
	public void testAddSingleLineComment(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addSingleLineComment(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddSingleLineComments(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addSingleLineComments(ListUtil.createList(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testSetSingleLineComments(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setSingleLineComments(ListUtil.createList(UneditableJavaSingleLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable single-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddMultiLineComment(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addMultiLineComment(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddMultiLineComments(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addMultiLineComments(ListUtil.createList(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testSetMultiLineComments(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setMultiLineComments(ListUtil.createList(UneditableJavaMultiLineComment.builder()
					.content("some comment")
					.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable multi-line comments", e.getMessage());
		}
	}
	
	@Test
	public void testAddInnerClass(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		JavaClass clazz1 = EditableJavaClass.builder()
				.name("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.name("Derp")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addInnerClass(UneditableJavaClass.builder()
					.name("Test")
					.build());
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testAddInnerClasses(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		JavaClass clazz1 = EditableJavaClass.builder()
				.name("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.name("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.name("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.name("Yep")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.addInnerClasses(ListUtil.createList(
					UneditableJavaClass.builder()
							.name("Test")
							.build(),
					UneditableJavaClass.builder()
							.name("Derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testSetInnerClasses(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
				.build();
		JavaClass clazz1 = EditableJavaClass.builder()
				.name("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.name("Derp")
				.build();
		JavaClass clazz3 = EditableJavaClass.builder()
				.name("Blah")
				.build();
		JavaClass clazz4 = EditableJavaClass.builder()
				.name("Yep")
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
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
					.build();
			clazz.setInnerClasses(ListUtil.createList(
					UneditableJavaClass.builder()
							.name("Test")
							.build(),
					UneditableJavaClass.builder()
							.name("Derp")
							.build()));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("editable Java Class Type requires editable inner classes", e.getMessage());
		}
	}
	
	@Test
	public void testAddField(){
		if(hasFields){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddFieldUneditable(){
		if(hasFields){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
						.build();
				clazz.addField(UneditableJavaField.builder()
						.type("String").name("test")
						.build());
				fail();
			}catch(IllegalArgumentException e){
				assertEquals("editable Java Class Type requires editable Java Fields", e.getMessage());
			}
		}
	}
	
	@Test
	public void testAddFields(){
		if(hasFields){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddFieldsUneditable(){
		if(hasFields){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
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
				assertEquals("editable Java Class Type requires editable Java Fields", e.getMessage());
			}
		}
	}
	
	@Test
	public void testSetFields(){
		if(hasFields){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testSetFieldsUneditable(){
		if(hasFields){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
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
				assertEquals("editable Java Class Type requires editable Java Fields", e.getMessage());
			}
		}
	}
	
	@Test
	public void testAddMethod(){
		if(hasMethods){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddMethodUneditable(){
		if(hasMethods){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
						.build();
				clazz.addMethod(UneditableJavaMethod.builder()
						.returnType("String").name("test")
						.build());
				fail();
			}catch(IllegalArgumentException e){
				assertEquals("editable Java Class Type requires editable Java Methods", e.getMessage());
			}
		}
	}
	
	@Test
	public void testAddMethods(){
		if(hasMethods){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testAddMethodsUneditable(){
		if(hasMethods){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
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
				assertEquals("editable Java Class Type requires editable Java Methods", e.getMessage());
			}
		}
	}
	
	@Test
	public void testSetMethods(){
		if(hasMethods){
			JavaClassType clazz = editableBuilder.get()
					.name("AClassName")
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
	}
	
	@Test
	public void testSetMethodsUneditable(){
		if(hasMethods){
			try{
				JavaClassType clazz = editableBuilder.get()
						.name("AClassName")
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
				assertEquals("editable Java Class Type requires editable Java Methods", e.getMessage());
			}
		}
	}
	
	@Test
	public void testSetInnerElementsOrder(){
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
		JavaClassType clazz = editableBuilder.get()
				.name("AClassName")
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
