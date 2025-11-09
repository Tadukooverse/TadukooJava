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
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.IMPORT_STATEMENT,
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.build().getJavaCodeType()
				),
				// Copy
				Pair.of(
						builders -> builders.importStatementBuilder().get()
								.isStatic()
								.importName("com.example")
								.build(),
						builders -> builders.importStatementBuilder().get()
								.copy(builders.importStatementBuilder().get()
										.isStatic()
										.importName("com.example")
										.build())
								.build()
				),
				// Set Import Name
				Pair.of(
						builder -> "com.example",
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.build()
								.getImportName()
				),
				// Default Is Static
				Pair.of(
						builder -> false,
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.build()
								.isStatic()
				),
				// Is Static
				Pair.of(
						builder -> true,
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic()
								.build()
								.isStatic()
				),
				// Set Is Static
				Pair.of(
						builder -> true,
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic(true)
								.build()
								.isStatic()
				),
				// Equals
				Pair.of(
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic()
								.build(),
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic()
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
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.build(),
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic()
								.build()
				),
				// Different types
				Pair.of(
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
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
		List<Triple<Function<Builders, JavaImportStatement>, String, Function<SimpleClassNames, String>>>
				commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.build(),
						"import com.example;",
						classNames -> classNames.importStatementSimpleClassName() + """
								.builder()
										.importName("com.example")
										.build()"""
				),
				// With Static
				Triple.of(
						builders -> builders.importStatementBuilder().get()
								.importName("com.example")
								.isStatic()
								.build(),
						"import static com.example;",
						classNames -> classNames.importStatementSimpleClassName() + """
								.builder()
										.isStatic()
										.importName("com.example")
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
		List<Pair<Function<Builders, Supplier<? extends JavaImportStatement>>, String>>
				builderFuncsAndErrorMessages = ListUtil.createList(
				// Missing Import Name
				Pair.of(
						builders -> () -> builders.importStatementBuilder().get()
								.build(),
						"importName is required!"
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
