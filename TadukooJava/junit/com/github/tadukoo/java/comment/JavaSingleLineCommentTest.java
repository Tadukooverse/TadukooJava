package com.github.tadukoo.java.comment;

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

public class JavaSingleLineCommentTest extends BaseJavaCodeTypeTest<JavaSingleLineComment>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(UneditableJavaSingleLineComment.builder()
						.build(), false,
						(Function<UneditableJavaSingleLineComment, Boolean>)
								UneditableJavaSingleLineComment::isEditable),
				Arguments.of(EditableJavaSingleLineComment.builder()
						.build(), true,
						(Function<EditableJavaSingleLineComment, Boolean>)
								EditableJavaSingleLineComment::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<
				Function<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>, Object>,
				Function<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>, Object>>>
				comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.SINGLE_LINE_COMMENT,
						builder -> builder.get()
								.build().getJavaCodeType()
				),
				// Default Content
				Pair.of(
						builder -> "",
						builder -> builder.get()
								.build().getContent()
				),
				// Copy
				Pair.of(
						builder -> builder.get()
								.content("something useful")
								.build(),
						builder -> builder.get()
								.copy(builder.get()
										.content("something useful")
										.build())
								.build()
				),
				// Set Content
				Pair.of(
						builder -> "something useful",
						builder -> builder.get()
								.content("something useful")
								.build()
								.getContent()
				),
				// Equals
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> builder.get()
								.build()
				),
				// Equals With Content
				Pair.of(
						builder -> builder.get()
								.content("something useful")
								.build(),
						 builder -> builder.get()
								 .content("something useful")
								 .build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(singleLineCommentBuilders.get(index)),
								pair.getRight().apply(singleLineCommentBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<
				Function<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>, Object>,
				Function<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>, Object>>>
				comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> builder.get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Content
				Pair.of(
						builder -> builder.get()
								.content("something else")
								.build(),
						builder -> builder.get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Types
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(singleLineCommentBuilders.get(index)),
								pair.getRight().apply(singleLineCommentBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<
				Function<Supplier<JavaSingleLineCommentBuilder<? extends JavaSingleLineComment>>, JavaSingleLineComment>,
				String,
				Function<String, String>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builder -> builder.get()
								.build(),
						"// ",
						simpleClassName -> simpleClassName + """
								.builder()
										.content("")
										.build()"""
				),
				// With Content
				Triple.of(
						builder -> builder.get()
								.content("something useful")
								.build(),
						"// something useful",
						simpleClassName -> simpleClassName + """
								.builder()
										.content("something useful")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(singleLineCommentBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(singleLineCommentSimpleClassNames.get(index)))));
	}
	
	/*
	 * Editable Tests
	 */
	@Test
	public void testSetContent(){
		EditableJavaSingleLineComment comment = EditableJavaSingleLineComment.builder()
				.build();
		assertEquals("", comment.getContent());
		comment.setContent("something dumb");
		assertEquals("something dumb", comment.getContent());
	}
}
