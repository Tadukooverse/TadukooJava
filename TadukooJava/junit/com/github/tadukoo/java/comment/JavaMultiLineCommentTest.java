package com.github.tadukoo.java.comment;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaMultiLineCommentTest extends BaseJavaCodeTypeTest<JavaMultiLineComment>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(UneditableJavaMultiLineComment.builder()
						.build(), false,
						(ThrowingFunction<UneditableJavaMultiLineComment, Boolean, NoException>)
								UneditableJavaMultiLineComment::isEditable),
				Arguments.of(EditableJavaMultiLineComment.builder()
						.build(), true,
						(ThrowingFunction<EditableJavaMultiLineComment, Boolean, NoException>)
						EditableJavaMultiLineComment::isEditable)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<
				ThrowingFunction<ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException>,
						Object, NoException>,
				ThrowingFunction<ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.MULTI_LINE_COMMENT,
						builder -> builder.get()
								.build()
								.getJavaCodeType()
				),
				// Default Content
				Pair.of(
						builder -> ListUtil.createList(),
						builder -> builder.get()
								.build()
								.getContent()
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
						builder -> ListUtil.createList("something useful"),
						builder -> builder.get()
								.content("something useful")
								.build()
								.getContent()
				),
				// Set Multi Line Content
				Pair.of(
						builder -> ListUtil.createList("something useful", "something else"),
						builder -> builder.get()
								.content(ListUtil.createList("something useful", "something else"))
								.build()
								.getContent()
				),
				// Set Multi Line Content Separate Entries
				Pair.of(
						builder -> ListUtil.createList("something useful", "something else"),
						builder -> builder.get()
								.content("something useful")
								.content("something else")
								.build()
								.getContent()
				),
				// Equals
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> builder.get()
								.build()
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(multiLineCommentBuilders.get(index)),
								pair.getRight().apply(multiLineCommentBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getNotEqualsData(){
		List<Pair<
				ThrowingFunction<ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException>,
						Object, NoException>,
				ThrowingFunction<ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException>,
						Object, NoException>>> comparisons = ListUtil.createList(
				// Not Equals
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> builder.get()
								.content("something useful")
								.build()
				),
				// Not Equals Different Type
				Pair.of(
						builder -> builder.get()
								.build(),
						builder -> "testing"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(multiLineCommentBuilders.get(index)),
								pair.getRight().apply(multiLineCommentBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<
				ThrowingFunction<
						ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException>,
						JavaMultiLineComment, NoException>,
				String,
				ThrowingFunction<String, String, NoException>>> commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builder -> builder.get()
								.build(),
						"/*\n */",
						simpleClassName -> simpleClassName + """
								.builder()
										.build()"""
				),
				// With 1 line of content
				Triple.of(
						builder -> builder.get()
								.content("something useful")
								.build(),
						"""
								/*
								 * something useful
								 */""",
						simpleClassName -> simpleClassName + """
								.builder()
										.content("something useful")
										.build()"""
				),
				// With 2 lines of content
				Triple.of(
						builder -> builder.get()
								.content("something useful")
								.content("something else")
								.build(),
						"""
								/*
								 * something useful
								 * something else
								 */""",
						simpleClassName -> simpleClassName + """
								.builder()
										.content("something useful")
										.content("something else")
										.build()"""
				)
		);
		
		return commentMakersAndStrings.stream()
				.flatMap(triple -> Stream.of(0, 1, 2)
						.map(index -> {
							ThrowingSupplier<JavaMultiLineCommentBuilder<? extends JavaMultiLineComment>, NoException> builder =
									multiLineCommentBuilders.get(index);
							return Arguments.of(triple.getLeft().apply(builder), triple.getMiddle(),
									triple.getRight().apply(multiLineCommentSimpleClassNames.get(index)));
						}));
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
