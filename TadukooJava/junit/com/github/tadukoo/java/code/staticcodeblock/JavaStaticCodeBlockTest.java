package com.github.tadukoo.java.code.staticcodeblock;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaStaticCodeBlockTest extends BaseJavaCodeTypeTest<JavaStaticCodeBlock>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavaStaticCodeBlock.builder()
								.build(),
						false,
						(Function<UneditableJavaStaticCodeBlock, Boolean>) UneditableJavaStaticCodeBlock::isEditable
				),
				Arguments.of(
						EditableJavaStaticCodeBlock.builder()
								.build(),
						true,
						(Function<EditableJavaStaticCodeBlock, Boolean>) EditableJavaStaticCodeBlock::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>,
				Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builders -> JavaCodeTypes.STATIC_CODE_BLOCK,
						builders -> builders.staticCodeBlockBuilder().get()
								.build()
								.getJavaCodeType()
				),
				// Default Lines
				Pair.of(
						builders -> new ArrayList<>(),
						builders -> builders.staticCodeBlockBuilder().get()
								.build()
								.getLines()
				),
				// Copy
				Pair.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						builders -> builders.staticCodeBlockBuilder().get()
								.copy(builders.staticCodeBlockBuilder().get()
										.line("doSomething();")
										.line("doSomethingElse();")
										.build())
								.build()
				),
				// Set Lines
				Pair.of(
						builders -> ListUtil.createList(
								"doSomething();", "doSomethingElse();"
						),
						builders -> builders.staticCodeBlockBuilder().get()
								.lines(ListUtil.createList(
										"doSomething();", "doSomethingElse();"
								))
								.build()
								.getLines()
				),
				// Set Line
				Pair.of(
						builders -> ListUtil.createList(
								"doSomething();"
						),
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.build()
								.getLines()
				),
				// Equals
				Pair.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomethingElse();")
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
				Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomething2();")
								.build()
				),
				// Different type
				Pair.of(
						builders -> builders.staticCodeBlockBuilder().get()
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
		List<Triple<Function<Builders, JavaStaticCodeBlock>,
				String,
				Function<SimpleClassNames, String>>> codeBlockMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.build(),
						"""
								static{ }""",
						classNames -> classNames.staticCodeBlockSimpleClassName() + """
								.builder()
										.build()"""
				),
				// With Line
				Triple.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.build(),
						"""
								static{
									doSomething();
								}""",
						classNames -> classNames.staticCodeBlockSimpleClassName() + """
								.builder()
										.line("doSomething();")
										.build()"""
				),
				// With Lines
				Triple.of(
						builders -> builders.staticCodeBlockBuilder().get()
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								static{
									doSomething();
									doSomethingElse();
								}""",
						classNames -> classNames.staticCodeBlockSimpleClassName() + """
								.builder()
										.line("doSomething();")
										.line("doSomethingElse();")
										.build()"""
				)
		);
		
		return codeBlockMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	@Test
	public void testAddLine(){
		EditableJavaStaticCodeBlock codeBlock = EditableJavaStaticCodeBlock.builder()
				.build();
		assertEquals(new ArrayList<>(), codeBlock.getLines());
		codeBlock.addLine("doSomething();");
		assertEquals(ListUtil.createList(
				"doSomething();"
		), codeBlock.getLines());
		codeBlock.addLine("doSomethingElse();");
		assertEquals(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();"
		), codeBlock.getLines());
	}
	
	@Test
	public void testAddLines(){
		EditableJavaStaticCodeBlock codeBlock = EditableJavaStaticCodeBlock.builder()
				.build();
		assertEquals(new ArrayList<>(), codeBlock.getLines());
		codeBlock.addLines(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();"
		));
		assertEquals(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();"
		), codeBlock.getLines());
		codeBlock.addLines(ListUtil.createList(
				"doSomething2();",
				"doSomethingElse2();"
		));
		assertEquals(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();",
				"doSomething2();",
				"doSomethingElse2();"
		), codeBlock.getLines());
	}
	
	@Test
	public void testSetLines(){
		EditableJavaStaticCodeBlock codeBlock = EditableJavaStaticCodeBlock.builder()
				.build();
		assertEquals(new ArrayList<>(), codeBlock.getLines());
		codeBlock.setLines(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();"
		));
		assertEquals(ListUtil.createList(
				"doSomething();",
				"doSomethingElse();"
		), codeBlock.getLines());
		codeBlock.setLines(ListUtil.createList(
				"doSomething2();",
				"doSomethingElse2();"
		));
		assertEquals(ListUtil.createList(
				"doSomething2();",
				"doSomethingElse2();"
		), codeBlock.getLines());
	}
}
