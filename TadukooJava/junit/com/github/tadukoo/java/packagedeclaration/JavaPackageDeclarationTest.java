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
		List<Pair<Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>, Object>,
				Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>, Object>>>
				comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.PACKAGE_DECLARATION,
						builder -> builder.get()
								.packageName("com.example")
								.build().getJavaCodeType()
				),
				// Copy
				Pair.of(
						builder -> builder.get()
								.packageName("com.example")
								.build(),
						builder -> builder.get()
								.copy(builder.get()
										.packageName("com.example")
										.build())
								.build()
				),
				// Package Name
				Pair.of(
						builder -> "com.example",
						builder -> builder.get()
								.packageName("com.example")
								.build()
								.getPackageName()
				),
				// Equals
				Pair.of(
						builder -> builder.get()
								.packageName("com.example")
								.build(),
						builder -> builder.get()
								.packageName("com.example")
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(packageDeclarationBuilders.get(index)),
								pair.getRight().apply(packageDeclarationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>, Object>,
				Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>, Object>>>
				comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builder -> builder.get()
								.packageName("com.example")
								.build(),
						builder -> builder.get()
								.packageName("com.github.tadukoo")
								.build()
				),
				// Different Types
				Pair.of(
						builder -> builder.get()
								.packageName("com.example")
								.build(),
						builder -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(packageDeclarationBuilders.get(index)),
								pair.getRight().apply(packageDeclarationBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>,
					JavaPackageDeclaration>,
				String,
				Function<String, String>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builder -> builder.get()
								.packageName("com.example")
								.build(),
						"package com.example;",
						simpleClassName -> simpleClassName + """
								.builder()
										.packageName("com.example")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(packageDeclarationBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(packageDeclarationSimpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<Function<Supplier<JavaPackageDeclarationBuilder<? extends JavaPackageDeclaration>>,
						Supplier<? extends JavaPackageDeclaration>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Missing Package Name
				Pair.of(
						builder -> () -> builder.get()
								.build(),
						"packageName is required!"
				)
		);
		
		return builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(packageDeclarationBuilders.get(index)),
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
