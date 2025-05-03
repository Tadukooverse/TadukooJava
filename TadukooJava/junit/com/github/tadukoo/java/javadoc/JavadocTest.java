package com.github.tadukoo.java.javadoc;

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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavadocTest extends BaseJavaCodeTypeTest<Javadoc>{
	
	@Override
	protected Stream<Arguments> getEditableData(){
		return Stream.of(
				Arguments.of(
						UneditableJavadoc.builder()
								.build(),
						false,
						(Function<UneditableJavadoc, Boolean>) UneditableJavadoc::isEditable
				),
				Arguments.of(
						EditableJavadoc.builder()
								.build(),
						true,
						(Function<EditableJavadoc, Boolean>) EditableJavadoc::isEditable
				)
		);
	}
	
	@Override
	protected Stream<Arguments> getEqualsData(){
		List<Pair<Function<Builders, Object>, Function<Builders, Object>>> comparisons = ListUtil.createList(
				// Java Code Type
				Pair.of(
						builder -> JavaCodeTypes.JAVADOC,
						builders -> builders.javadocBuilder().get()
								.build().getJavaCodeType()
				),
				// Default Condensed
				Pair.of(
						builder -> false,
						builders -> builders.javadocBuilder().get()
								.build()
								.isCondensed()
				),
				// Default Content
				Pair.of(
						builder -> new ArrayList<>(),
						builders -> builders.javadocBuilder().get()
								.build()
								.getContent()
				),
				// Default Author
				Pair.of(
						builder -> null,
						builders -> builders.javadocBuilder().get()
								.build()
								.getAuthor()
				),
				// Default Version
				Pair.of(
						builder -> null,
						builders -> builders.javadocBuilder().get()
								.build()
								.getVersion()
				),
				// Default Since
				Pair.of(
						builder -> null,
						builders -> builders.javadocBuilder().get()
								.build()
								.getSince()
				),
				// Default Params
				Pair.of(
						builder -> new ArrayList<>(),
						builders -> builders.javadocBuilder().get()
								.build()
								.getParams()
				),
				// Default Return Value
				Pair.of(
						builder -> null,
						builders -> builders.javadocBuilder().get()
								.build()
								.getReturnVal()
				),
				// Default Throws Infos
				Pair.of(
						builder -> new ArrayList<>(),
						builders -> builders.javadocBuilder().get()
								.build()
								.getThrowsInfos()
				),
				// Copy
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.content("Some information")
								.content("Some more information")
								.author("Logan Ferree (Tadukoo)")
								.version("Beta v.0.5")
								.since("Alpha v.0.1")
								.param("something", "Does something")
								.returnVal("yep")
								.throwsInfo("Exception", "because I can")
								.build(),
						builders -> builders.javadocBuilder().get()
								.copy(builders.javadocBuilder().get()
										.condensed()
										.content("Some information")
										.content("Some more information")
										.author("Logan Ferree (Tadukoo)")
										.version("Beta v.0.5")
										.since("Alpha v.0.1")
										.param("something", "Does something")
										.returnVal("yep")
										.throwsInfo("Exception", "because I can")
										.build())
								.build()
				),
				// Set Condensed Value
				Pair.of(
						builder -> true,
						builders -> builders.javadocBuilder().get()
								.condensed(true)
								.build()
								.isCondensed()
				),
				// Set Condensed
				Pair.of(
						builder -> true,
						builders -> builders.javadocBuilder().get()
								.condensed()
								.build()
								.isCondensed()
				),
				// Set Content
				Pair.of(
						builder -> ListUtil.createList("test", "derp"),
						builders -> builders.javadocBuilder().get()
								.content(ListUtil.createList("test", "derp"))
								.build()
								.getContent()
				),
				// Set Content Line
				Pair.of(
						builder -> ListUtil.createList("test"),
						builders -> builders.javadocBuilder().get()
								.content("test")
								.build()
								.getContent()
				),
				// Set Author
				Pair.of(
						builder -> "Logan Ferree (Tadukoo)",
						builders -> builders.javadocBuilder().get()
								.author("Logan Ferree (Tadukoo)")
								.build()
								.getAuthor()
				),
				// Set Version
				Pair.of(
						builder -> "Alpha v.0.1",
						builders -> builders.javadocBuilder().get()
								.version("Alpha v.0.1")
								.build()
								.getVersion()
				),
				// Set Since
				Pair.of(
						builder -> "Alpha v.0.0.1",
						builders -> builders.javadocBuilder().get()
								.since("Alpha v.0.0.1")
								.build()
								.getSince()
				),
				// Set Params
				Pair.of(
						builder -> ListUtil.createList(Pair.of("test", "yes"), Pair.of("derp", "no")),
						builders -> builders.javadocBuilder().get()
								.params(ListUtil.createList(Pair.of("test", "yes"),
										Pair.of("derp", "no")))
								.build()
								.getParams()
				),
				// Set Param Pair
				Pair.of(
						builder -> ListUtil.createList(Pair.of("test", "yes")),
						builders -> builders.javadocBuilder().get()
								.param(Pair.of("test", "yes"))
								.build()
								.getParams()
				),
				// Set Param Pieces
				Pair.of(
						builder -> ListUtil.createList(Pair.of("test", "yes")),
						builders -> builders.javadocBuilder().get()
								.param("test", "yes")
								.build()
								.getParams()
				),
				// Set Return Value
				Pair.of(
						builder -> "this, to continue building",
						builders -> builders.javadocBuilder().get()
								.returnVal("this, to continue building")
								.build()
								.getReturnVal()
				),
				// Set Throws Infos
				Pair.of(
						builder -> ListUtil.createList(Pair.of("Exception", "Because I can"),
								Pair.of("Throwable", "Because I want to")),
						builders -> builders.javadocBuilder().get()
								.throwsInfos(ListUtil.createList(Pair.of("Exception", "Because I can"),
										Pair.of("Throwable", "Because I want to")))
								.build()
								.getThrowsInfos()
				),
				// Set Throws Info Pair
				Pair.of(
						builder -> ListUtil.createList(Pair.of("Exception", "Because I can")),
						builders -> builders.javadocBuilder().get()
								.throwsInfo(Pair.of("Exception", "Because I can"))
								.build()
								.getThrowsInfos()
				),
				// Set Throws Info Pieces
				Pair.of(
						builder -> ListUtil.createList(Pair.of("Exception", "Because I can")),
						builders -> builders.javadocBuilder().get()
								.throwsInfo("Exception", "Because I can")
								.build()
								.getThrowsInfos()
				),
				// Equals
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.content("test")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
								.build(),
						builders -> builders.javadocBuilder().get()
								.content("test")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
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
				// Not Equal
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.content("tes")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.build(),
						builders -> builders.javadocBuilder().get()
								.content("test")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.build()
				),
				// Different type
				Pair.of(
						builders -> builders.javadocBuilder().get()
								.build(),
						builder -> "test"
				)
		);
		
		return comparisons.stream()
				.flatMap(pair -> Stream.of(0, 1, 2)
						.map(index -> Arguments.of(pair.getLeft().apply(allBuilders.get(index)),
								pair.getRight().apply(allBuilders.get(index)))));
	}
	
	@Override
	protected Stream<Arguments> getStringData(){
		List<Triple<Function<Builders, Javadoc>, String, Function<SimpleClassNames, String>>>
				commentMakersAndStrings = ListUtil.createList(
				// Simple
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.build(),
						"""
								/**
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.build()"""
				),
				// With Content
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.content("test")
								.content("derp")
								.build(),
						"""
								/**
								 * test
								 * derp
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.content("test")
										.content("derp")
										.build()"""
				),
				// With Author
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						"""
								/**
								 * @author Logan Ferree (Tadukoo)
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.author("Logan Ferree (Tadukoo)")
										.build()"""
				),
				// With Version
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.version("Alpha v.0.1")
								.build(),
						"""
								/**
								 * @version Alpha v.0.1
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.version("Alpha v.0.1")
										.build()"""
				),
				// With Since
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.since("Alpha v.0.0.1")
								.build(),
						"""
								/**
								 * @since Alpha v.0.0.1
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.since("Alpha v.0.0.1")
										.build()"""
				),
				// With Single Param
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.param("test", "yes")
								.build(),
						"""
								/**
								 * @param test yes
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.param("test", "yes")
										.build()"""
				),
				// With Multiple Params
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.param("test", "yes")
								.param("derp", "no")
								.build(),
						"""
								/**
								 * @param test yes
								 * @param derp no
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.param("test", "yes")
										.param("derp", "no")
										.build()"""
				),
				// With Return Value
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.returnVal("this, to continue building")
								.build(),
						"""
								/**
								 * @return this, to continue building
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.returnVal("this, to continue building")
										.build()"""
				),
				// With Throws Info
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/**
								 * @throws Exception Because I can
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.throwsInfo("Exception", "Because I can")
										.build()"""
				),
				// With Multiple Throws Infos
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.throwsInfo("Exception", "Because I can")
								.throwsInfo("Throwable", "Because I want to")
								.build(),
						"""
								/**
								 * @throws Exception Because I can
								 * @throws Throwable Because I want to
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.throwsInfo("Exception", "Because I can")
										.throwsInfo("Throwable", "Because I want to")
										.build()"""
				),
				// With Everything
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.content("test")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
								.throwsInfo("Throwable", "Because I want to")
								.build(),
						"""
								/**
								 * test
								 * derp
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 * @since Alpha v.0.0.1
								 *\s
								 * @param test yes
								 * @param derp no
								 * @return this, to continue building
								 * @throws Exception Because I can
								 * @throws Throwable Because I want to
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.content("test")
										.content("derp")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.1")
										.since("Alpha v.0.0.1")
										.param("test", "yes")
										.param("derp", "no")
										.returnVal("this, to continue building")
										.throwsInfo("Exception", "Because I can")
										.throwsInfo("Throwable", "Because I want to")
										.build()"""
				),
				// Condensed Simple
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.build(),
						"""
								/** */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.build()"""
				),
				// Condensed With Content
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.content("test")
								.content("derp")
								.build(),
						"""
								/** test
								 * derp */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.content("test")
										.content("derp")
										.build()"""
				),
				// Condensed With Author
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.author("Logan Ferree (Tadukoo)")
								.build(),
						"""
								/** @author Logan Ferree (Tadukoo) */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.author("Logan Ferree (Tadukoo)")
										.build()"""
				),
				// Condensed With Version
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.version("Alpha v.0.1")
								.build(),
						"""
								/** @version Alpha v.0.1 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.version("Alpha v.0.1")
										.build()"""
				),
				// Condensed With Since
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.since("Alpha v.0.0.1")
								.build(),
						"""
								/** @since Alpha v.0.0.1 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.since("Alpha v.0.0.1")
										.build()"""
				),
				// Condensed With Single Param
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.param("test", "yes")
								.build(),
						"""
								/** @param test yes */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.param("test", "yes")
										.build()"""
				),
				// Condensed With Multiple Params
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.param("test", "yes")
								.param("derp", "no")
								.build(),
						"""
								/** @param test yes
								 * @param derp no */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.param("test", "yes")
										.param("derp", "no")
										.build()"""
				),
				// Condensed With Return Value
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.returnVal("this, to continue building")
								.build(),
						"""
								/** @return this, to continue building */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.returnVal("this, to continue building")
										.build()"""
				),
				// Condensed With Throws Info
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.throwsInfo("Exception", "Because I can")
								.build(),
						"""
								/** @throws Exception Because I can */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.throwsInfo("Exception", "Because I can")
										.build()"""
				),
				// Condensed With Multiple Throws Infos
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.throwsInfo("Exception", "Because I can")
								.throwsInfo("Throwable", "Because I want to")
								.build(),
						"""
								/** @throws Exception Because I can
								 * @throws Throwable Because I want to */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.throwsInfo("Exception", "Because I can")
										.throwsInfo("Throwable", "Because I want to")
										.build()"""
				),
				// Condensed With Everything
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.condensed()
								.content("test")
								.content("derp")
								.author("Logan Ferree (Tadukoo)")
								.version("Alpha v.0.1")
								.since("Alpha v.0.0.1")
								.param("test", "yes")
								.param("derp", "no")
								.returnVal("this, to continue building")
								.throwsInfo("Exception", "Because I can")
								.throwsInfo("Throwable", "Because I want to")
								.build(),
						"""
								/** test
								 * derp
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 * @since Alpha v.0.0.1
								 *\s
								 * @param test yes
								 * @param derp no
								 * @return this, to continue building
								 * @throws Exception Because I can
								 * @throws Throwable Because I want to */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.condensed()
										.content("test")
										.content("derp")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.1")
										.since("Alpha v.0.0.1")
										.param("test", "yes")
										.param("derp", "no")
										.returnVal("this, to continue building")
										.throwsInfo("Exception", "Because I can")
										.throwsInfo("Throwable", "Because I want to")
										.build()"""
				),
				// Content and Info Annotations
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.content("test")
								.author("Me")
								.build(),
						"""
								/**
								 * test
								 *\s
								 * @author Me
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.content("test")
										.author("Me")
										.build()"""
				),
				// Content and Code Annotations
				Triple.of(
						builders -> builders.javadocBuilder().get()
								.content("test")
								.returnVal("this")
								.build(),
						"""
								/**
								 * test
								 *\s
								 * @return this
								 */""",
						classNames -> classNames.javadocSimpleClassName() + """
								.builder()
										.content("test")
										.returnVal("this")
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
	public void testSetCondensed(){
		EditableJavadoc doc = EditableJavadoc.builder()
						.build();
		assertFalse(doc.isCondensed());
		doc.setCondensed(true);
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testAddContentSingleLine(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.addContent("Test");
		assertEquals(ListUtil.createList("Test"), doc.getContent());
		doc.addContent("Derp");
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
	}
	
	@Test
	public void testAddContentMultiline(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.addContent(ListUtil.createList("Test", "Derp"));
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
		doc.addContent(ListUtil.createList("blah", "yep"));
		assertEquals(ListUtil.createList("Test", "Derp", "blah", "yep"), doc.getContent());
	}
	
	@Test
	public void testSetContent(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.setContent(ListUtil.createList("Test", "Derp"));
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
		doc.setContent(ListUtil.createList("blah", "yep"));
		assertEquals(ListUtil.createList("blah", "yep"), doc.getContent());
	}
	
	@Test
	public void testSetAuthor(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertNull(doc.getAuthor());
		doc.setAuthor("Me");
		assertEquals("Me", doc.getAuthor());
		doc.setAuthor("Logan Ferree (Tadukoo)");
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
	}
	
	@Test
	public void testSetVersion(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertNull(doc.getVersion());
		doc.setVersion("Test");
		assertEquals("Test", doc.getVersion());
		doc.setVersion("Alpha v.0.1");
		assertEquals("Alpha v.0.1", doc.getVersion());
	}
	
	@Test
	public void testSetSince(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertNull(doc.getSince());
		doc.setSince("Test");
		assertEquals("Test", doc.getSince());
		doc.setSince("Alpha v.0.1");
		assertEquals("Alpha v.0.1", doc.getSince());
	}
	
	@Test
	public void testAddParamPieces(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.addParam("Test", "something");
		assertEquals(ListUtil.createList(Pair.of("Test", "something")), doc.getParams());
		doc.addParam("Derp", "something else");
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
	}
	
	@Test
	public void testAddParamPair(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.addParam(Pair.of("Test", "something"));
		assertEquals(ListUtil.createList(Pair.of("Test", "something")), doc.getParams());
		doc.addParam(Pair.of("Derp", "something else"));
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
	}
	
	@Test
	public void testAddParams(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.addParams(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")));
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
		doc.addParams(ListUtil.createList(Pair.of("Blah", "yep"), Pair.of("Bloop", "nope")));
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else"),
						Pair.of("Blah", "yep"), Pair.of("Bloop", "nope")),
				doc.getParams());
	}
	
	@Test
	public void testSetParams(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.setParams(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")));
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
		doc.setParams(ListUtil.createList(Pair.of("Blah", "yep"), Pair.of("Bloop", "nope")));
		assertEquals(ListUtil.createList(Pair.of("Blah", "yep"), Pair.of("Bloop", "nope")),
				doc.getParams());
	}
	
	@Test
	public void testSetReturnVal(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertNull(doc.getReturnVal());
		doc.setReturnVal("a string");
		assertEquals("a string", doc.getReturnVal());
		doc.setReturnVal("this");
		assertEquals("this", doc.getReturnVal());
	}
	
	@Test
	public void testAddThrowsInfoPieces(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		assertEquals(new ArrayList<>(), doc.getThrowsInfos());
		doc.addThrowsInfo("Exception", "Because I can");
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can")), doc.getThrowsInfos());
		doc.addThrowsInfo("Throwable", "Because I want to");
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can"),
				Pair.of("Throwable", "Because I want to")), doc.getThrowsInfos());
	}
	
	@Test
	public void testAddThrowsInfoPair(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		Pair<String, String> throwsInfo1 = Pair.of("Exception", "Because I can");
		Pair<String, String> throwsInfo2 = Pair.of("Throwable", "Because I want to");
		assertEquals(new ArrayList<>(), doc.getThrowsInfos());
		doc.addThrowsInfo(throwsInfo1);
		assertEquals(ListUtil.createList(throwsInfo1), doc.getThrowsInfos());
		doc.addThrowsInfo(throwsInfo2);
		assertEquals(ListUtil.createList(throwsInfo1, throwsInfo2), doc.getThrowsInfos());
	}
	
	@Test
	public void testAddThrowsInfos(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		Pair<String, String> throwsInfo1 = Pair.of("Exception", "Because I can");
		Pair<String, String> throwsInfo2 = Pair.of("Throwable", "Because I want to");
		Pair<String, String> throwsInfo3 = Pair.of("CustomException", "Because why not?");
		Pair<String, String> throwsInfo4 = Pair.of("UnknownException", "Because reasons");
		assertEquals(new ArrayList<>(), doc.getThrowsInfos());
		doc.addThrowsInfos(ListUtil.createList(throwsInfo1, throwsInfo2));
		assertEquals(ListUtil.createList(throwsInfo1, throwsInfo2), doc.getThrowsInfos());
		doc.addThrowsInfos(ListUtil.createList(throwsInfo3, throwsInfo4));
		assertEquals(ListUtil.createList(throwsInfo1, throwsInfo2, throwsInfo3, throwsInfo4), doc.getThrowsInfos());
	}
	
	@Test
	public void testSetThrowsInfos(){
		EditableJavadoc doc = EditableJavadoc.builder()
				.build();
		Pair<String, String> throwsInfo1 = Pair.of("Exception", "Because I can");
		Pair<String, String> throwsInfo2 = Pair.of("Throwable", "Because I want to");
		Pair<String, String> throwsInfo3 = Pair.of("CustomException", "Because why not?");
		Pair<String, String> throwsInfo4 = Pair.of("UnknownException", "Because reasons");
		assertEquals(new ArrayList<>(), doc.getThrowsInfos());
		doc.setThrowsInfos(ListUtil.createList(throwsInfo1, throwsInfo2));
		assertEquals(ListUtil.createList(throwsInfo1, throwsInfo2), doc.getThrowsInfos());
		doc.setThrowsInfos(ListUtil.createList(throwsInfo3, throwsInfo4));
		assertEquals(ListUtil.createList(throwsInfo3, throwsInfo4), doc.getThrowsInfos());
	}
}
