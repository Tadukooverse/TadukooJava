package com.github.tadukoo.java.comment;

import com.github.tadukoo.java.BaseJavaCodeTypeTest;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.Function;
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
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.SINGLE_LINE_COMMENT,
						builders -> builders.singleLineCommentBuilder().get()
								.build().getJavaCodeType()
				),
				// Default Content
				Pair.of(
						builder -> "",
						builders -> builders.singleLineCommentBuilder().get()
								.build().getContent()
				),
				// Copy
				Pair.of(
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build(),
						builders -> builders.singleLineCommentBuilder().get()
								.copy(builders.singleLineCommentBuilder().get()
										.content("something useful")
										.build())
								.build()
				),
				// Set Content
				Pair.of(
						builder -> "something useful",
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build()
								.getContent()
				),
				// Equals
				Pair.of(
						builders -> builders.singleLineCommentBuilder().get()
								.build(),
						builders -> builders.singleLineCommentBuilder().get()
								.build()
				),
				// Equals With Content
				Pair.of(
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build(),
						 builders -> builders.singleLineCommentBuilder().get()
								 .content("something useful")
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
						builders -> builders.singleLineCommentBuilder().get()
								.build(),
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Content
				Pair.of(
						builders -> builders.singleLineCommentBuilder().get()
								.content("something else")
								.build(),
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Types
				Pair.of(
						builders -> builders.singleLineCommentBuilder().get()
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
		List<Triple<Function<Builders, JavaSingleLineComment>, String, Function<SimpleClassNames, String>>>
				commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.singleLineCommentBuilder().get()
								.build(),
						"// ",
						classNames -> classNames.singleLineCommentSimpleClassName() + """
								.builder()
										.content("")
										.build()"""
				),
				// With Content
				Triple.of(
						builders -> builders.singleLineCommentBuilder().get()
								.content("something useful")
								.build(),
						"// something useful",
						classNames -> classNames.singleLineCommentSimpleClassName() + """
								.builder()
										.content("something useful")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)),
								triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
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
