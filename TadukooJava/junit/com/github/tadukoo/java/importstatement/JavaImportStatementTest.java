package com.github.tadukoo.java.importstatement;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaImportStatementTest extends BaseJavaCodeTypeTest<JavaImportStatement>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						false,
						(Function<UneditableJavaImportStatement, Boolean>)
								UneditableJavaImportStatement::isEditable
				),
				Arguments.of(
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						true,
						(Function<EditableJavaImportStatement, Boolean>)
								EditableJavaImportStatement::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>, Object>,
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>, Object>>>
				comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.IMPORT_STATEMENT,
						builder -> builder.get()
								.importName("com.example")
								.build().getJavaCodeType()
				),
				// Copy
				Pair.of(
						builder -> builder.get()
								.isStatic()
								.importName("com.example")
								.build(),
						builder -> builder.get()
								.copy(builder.get()
										.isStatic()
										.importName("com.example")
										.build())
								.build()
				),
				// Set Import Name
				Pair.of(
						builder -> "com.example",
						builder -> builder.get()
								.importName("com.example")
								.build()
								.getImportName()
				),
				// Default Is Static
				Pair.of(
						builder -> false,
						builder -> builder.get()
								.importName("com.example")
								.build()
								.isStatic()
				),
				// Is Static
				Pair.of(
						builder -> true,
						builder -> builder.get()
								.importName("com.example")
								.isStatic()
								.build()
								.isStatic()
				),
				// Set Is Static
				Pair.of(
						builder -> true,
						builder -> builder.get()
								.importName("com.example")
								.isStatic(true)
								.build()
								.isStatic()
				),
				// Equals
				Pair.of(
						builder -> builder.get()
								.importName("com.example")
								.isStatic()
								.build(),
						builder -> builder.get()
								.importName("com.example")
								.isStatic()
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(importStatementBuilders.get(index)),
								pair.getRight().apply(importStatementBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>, Object>,
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>, Object>>>
				comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builder -> builder.get()
								.importName("com.example")
								.build(),
						builder -> builder.get()
								.importName("com.example")
								.isStatic()
								.build()
				),
				// Different types
				Pair.of(
						builder -> builder.get()
								.importName("com.example")
								.build(),
						builder -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(importStatementBuilders.get(index)),
								pair.getRight().apply(importStatementBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>, JavaImportStatement>,
				String,
				Function<String, String>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builder -> builder.get()
								.importName("com.example")
								.build(),
						"import com.example;",
						simpleClassName -> simpleClassName + """
								.builder()
										.importName("com.example")
										.build()"""
				),
				// With Static
				Triple.of(
						builder -> builder.get()
								.importName("com.example")
								.isStatic()
								.build(),
						"import static com.example;",
						simpleClassName -> simpleClassName + """
								.builder()
										.isStatic()
										.importName("com.example")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(importStatementBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(importStatementSimpleClassNames.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getBuilderErrorData(){
		List<Pair<
				Function<Supplier<JavaImportStatementBuilder<? extends JavaImportStatement>>,
						Supplier<? extends JavaImportStatement>>,
				String>> builderFuncsAndErrorMessages = ListUtil.createList(
				// Missing Import Name
				Pair.of(
						builder -> () -> builder.get()
								.build(),
						"importName is required!"
				)
		);
		
		return builderFuncsAndErrorMessages.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(importStatementBuilders.get(index)),
								pair.getRight())));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testSetStatic(){
		EditableJavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		assertFalse(importStatement.isStatic());
		importStatement.setStatic(false);
		assertFalse(importStatement.isStatic());
		importStatement.setStatic(true);
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testSetImportName(){
		EditableJavaImportStatement importStatement = EditableJavaImportStatement.builder()
				.importName("com.example")
				.build();
		assertEquals("com.example", importStatement.getImportName());
		importStatement.setImportName("org.help.me");
		assertEquals("org.help.me", importStatement.getImportName());
	}
}
