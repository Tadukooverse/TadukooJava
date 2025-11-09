package com.github.tadukoo.java.packagedeclaration;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.functional.supplier.Supplier;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaPackageDeclarationTest extends BaseJavaCodeTypeTest<JavaPackageDeclaration>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavaPackageDeclaration.builder().packageName("com.example").build(),
						false,
						(Function<UneditableJavaPackageDeclaration, Boolean>) UneditableJavaPackageDeclaration::isEditable
				),
				Arguments.of(
						EditableJavaPackageDeclaration.builder().packageName("com.example").build(),
						true,
						(Function<EditableJavaPackageDeclaration, Boolean>) EditableJavaPackageDeclaration::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.PACKAGE_DECLARATION,
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build().getJavaCodeType()
				),
				// Copy
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build(),
						builders -> builders.packageDeclarationBuilder().get()
								.copy(builders.packageDeclarationBuilder().get()
										.packageName("com.example")
										.build())
								.build()
				),
				// Package Name
				Pair.of(
						builder -> "com.example",
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build()
								.getPackageName()
				),
				// Equals
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build(),
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
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
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build(),
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.github.tadukoo")
								.build()
				),
				// Different Types
				Pair.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build(),
						builder -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function<Builders, JavaPackageDeclaration>, String, Function<SimpleClassNames, String>>>
				commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.packageDeclarationBuilder().get()
								.packageName("com.example")
								.build(),
						"package com.example;",
						classNames -> classNames.packageDeclarationSimpleClassName() + """
								.builder()
										.packageName("com.example")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Builders, Supplier<? extends JavaPackageDeclaration>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Missing Package Name
				Pair.of(
						builders -> () -> builders.packageDeclarationBuilder().get()
								.build(),
						"packageName is required!"
				)
		);
		
		return builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight())));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetPackageName(){
		EditableJavaPackageDeclaration packageDeclaration = EditableJavaPackageDeclaration.builder()
				.packageName("com.example")
				.build();
		assertEquals("com.example", packageDeclaration.getPackageName());
		packageDeclaration.setPackageName("org.java.test");
		assertEquals("org.java.test", packageDeclaration.getPackageName());
	}
}
