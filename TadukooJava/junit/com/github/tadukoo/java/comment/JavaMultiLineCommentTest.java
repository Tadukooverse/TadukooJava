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

public class JavaMultiLineCommentTest extends BaseJavaCodeTypeTest<JavaMultiLineComment>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(UneditableJavaMultiLineComment.builder()
						.build(), false,
						(Function<UneditableJavaMultiLineComment, Boolean>) UneditableJavaMultiLineComment::isEditable),
				Arguments.of(EditableJavaMultiLineComment.builder()
						.build(), true,
						(Function<EditableJavaMultiLineComment, Boolean>) EditableJavaMultiLineComment::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.MULTI_LINE_COMMENT,
						builders -> builders.multiLineCommentBuilder().get()
								.build()
								.getJavaCodeType()
				),
				// Default Content
				Pair.of(
						builder -> ListUtil.createList(),
						builders -> builders.multiLineCommentBuilder().get()
								.build()
								.getContent()
				),
				// Copy
				Pair.of(
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.build(),
						builders -> builders.multiLineCommentBuilder().get()
								.copy(builders.multiLineCommentBuilder().get()
										.content("something useful")
										.build())
								.build()
				),
				// Set Content
				Pair.of(
						builder -> ListUtil.createList("something useful"),
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.build()
								.getContent()
				),
				// Set Multi Line Content
				Pair.of(
						builder -> ListUtil.createList("something useful", "something else"),
						builders -> builders.multiLineCommentBuilder().get()
								.content(ListUtil.createList("something useful", "something else"))
								.build()
								.getContent()
				),
				// Set Multi Line Content Separate Entries
				Pair.of(
						builder -> ListUtil.createList("something useful", "something else"),
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.content("something else")
								.build()
								.getContent()
				),
				// Equals
				Pair.of(
						builders -> builders.multiLineCommentBuilder().get()
								.build(),
						builders -> builders.multiLineCommentBuilder().get()
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
						builders -> builders.multiLineCommentBuilder().get()
								.build(),
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Type
				Pair.of(
						builders -> builders.multiLineCommentBuilder().get()
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
		List<Triple<Function<Builders, JavaMultiLineComment>, String, Function<SimpleClassNames, String>>>
				commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.multiLineCommentBuilder().get()
								.build(),
						"/*\n */",
						classNames -> classNames.multiLineCommentSimpleClassName() + """
								.builder()
										.build()"""
				),
				// With 1 line of content
				Triple.of(
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */""",
						classNames -> classNames.multiLineCommentSimpleClassName() + """
								.builder()
										.content("something useful")
										.build()"""
				),
				// With 2 lines of content
				Triple.of(
						builders -> builders.multiLineCommentBuilder().get()
								.content("something useful")
								.content("something else")
								.build(),
						"""
								/*
								 * something useful
								 * something else
								 */""",
						classNames -> classNames.multiLineCommentSimpleClassName() + """
								.builder()
										.content("something useful")
										.content("something else")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(triple.getLeft().apply(allBuilders.get(index)), triple.getMiddle(),
								triple.getRight().apply(simpleClassNames.get(index)))));
	}
	
	/*
	 * Editable Tests
	 */
	
	@Test
	public void testAddContent(){
		EditableJavaMultiLineComment comment = EditableJavaMultiLineComment.builder()
				.build();
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.addContent("something");
		assertEquals(ListUtil.createList("something"), comment.getContent());
		comment.addContent("something else");
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
	}
	
	@Test
	public void testAddMultiComments(){
		EditableJavaMultiLineComment comment = EditableJavaMultiLineComment.builder()
				.build();
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.addContent(ListUtil.createList("something", "something else"));
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
		comment.addContent(ListUtil.createList("yep", "nope"));
		assertEquals(ListUtil.createList("something", "something else", "yep", "nope"), comment.getContent());
	}
	
	@Test
	public void testSetContent(){
		EditableJavaMultiLineComment comment = EditableJavaMultiLineComment.builder()
				.build();
		assertEquals(ListUtil.createList(), comment.getContent());
		comment.setContent(ListUtil.createList("something", "something else"));
		assertEquals(ListUtil.createList("something", "something else"), comment.getContent());
		comment.setContent(ListUtil.createList("yep", "nope"));
		assertEquals(ListUtil.createList("yep", "nope"), comment.getContent());
	}
}
