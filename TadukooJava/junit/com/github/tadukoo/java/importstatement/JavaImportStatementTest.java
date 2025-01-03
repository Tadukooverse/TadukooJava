package com.github.tadukoo.java.importstatement;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
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
						(ThrowingFunction<UneditableJavaImportStatement, Boolean, NoException>)
								UneditableJavaImportStatement::isEditable
				),
				Arguments.of(
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						true,
						(ThrowingFunction<EditableJavaImportStatement, Boolean, NoException>)
								EditableJavaImportStatement::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<
				ThrowingFunction<ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						Object, NoException>,
				ThrowingFunction<ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
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
				ThrowingFunction<ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						Object, NoException>,
				ThrowingFunction<ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
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
				ThrowingFunction<
						ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						JavaImportStatement, NoException>,
				String,
				ThrowingFunction<String, String, NoException>>> commentMakersAndStrings = ListUtil.createList(
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
				ThrowingFunction<ThrowingSupplier<JavaImportStatementBuilder<? extends JavaImportStatement>, NoException>,
						ThrowingSupplier<? extends JavaImportStatement, NoException>, NoException>,
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
