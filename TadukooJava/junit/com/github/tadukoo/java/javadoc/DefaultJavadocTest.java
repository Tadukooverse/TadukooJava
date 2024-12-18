package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DefaultJavadocTest<JavadocType extends Javadoc>{
	
	protected final Class<JavadocType> clazz;
	protected final ThrowingSupplier<JavadocBuilder<JavadocType>, NoException> builder;
	protected JavadocType doc;
	
	protected DefaultJavadocTest(
			Class<JavadocType> clazz, ThrowingSupplier<JavadocBuilder<JavadocType>, NoException> builder){
		this.clazz = clazz;
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		doc = builder.get().build();
	}
	
	@Test
	public void testGetType(){
		assertEquals(JavaCodeTypes.JAVADOC, doc.getJavaCodeType());
	}
	
	@Test
	public void testDefaultCondensed(){
		assertFalse(doc.isCondensed());
	}
	
	@Test
	public void testDefaultContent(){
		List<String> content = doc.getContent();
		assertNotNull(content);
		assertEquals(0, content.size());
	}
	
	@Test
	public void testDefaultAuthor(){
		assertNull(doc.getAuthor());
	}
	
	@Test
	public void testDefaultVersion(){
		assertNull(doc.getVersion());
	}
	
	@Test
	public void testDefaultSince(){
		assertNull(doc.getSince());
	}
	
	@Test
	public void testDefaultParams(){
		List<Pair<String, String>> params = doc.getParams();
		assertNotNull(params);
		assertEquals(0, params.size());
	}
	
	@Test
	public void testDefaultReturnVal(){
		assertNull(doc.getReturnVal());
	}
	
	@Test
	public void testDefaultThrowsInfos(){
		List<Pair<String, String>> throwsInfos = doc.getThrowsInfos();
		assertNotNull(throwsInfos);
		assertEquals(0, throwsInfos.size());
	}
	
	@Test
	public void testBuilderCopy(){
		Javadoc otherDoc = builder.get()
				.condensed()
				.content("Some information")
				.content("Some more information")
				.author("Logan Ferree (Tadukoo)")
				.version("Beta v.0.5")
				.since("Alpha v.0.1")
				.param("something", "Does something")
				.returnVal("yep")
				.throwsInfo("Exception", "because I can")
				.build();
		doc = builder.get()
				.copy(otherDoc)
				.build();
		assertEquals(otherDoc, doc);
	}
	
	@Test
	public void testBuilderSetCondensedValue(){
		doc = builder.get()
				.condensed(true)
				.build();
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testBuilderSetCondensed(){
		doc = builder.get()
				.condensed()
				.build();
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testBuilderSetContent(){
		List<String> content = ListUtil.createList("test", "derp");
		doc = builder.get()
				.content(content)
				.build();
		assertEquals(content, doc.getContent());
	}
	
	@Test
	public void testBuilderSetContentLine(){
		String content = "test";
		doc = builder.get()
				.content(content)
				.build();
		List<String> actContent = doc.getContent();
		assertEquals(1, actContent.size());
		assertEquals(content, actContent.get(0));
	}
	
	@Test
	public void testBuilderSetAuthor(){
		doc = builder.get()
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
	}
	
	@Test
	public void testBuilderSetVersion(){
		doc = builder.get()
				.version("Alpha v.0.1")
				.build();
		assertEquals("Alpha v.0.1", doc.getVersion());
	}
	
	@Test
	public void testBuilderSetSince(){
		doc = builder.get()
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("Alpha v.0.0.1", doc.getSince());
	}
	
	@Test
	public void testBuilderSetParams(){
		List<Pair<String, String>> params = ListUtil.createList(Pair.of("test", "yes"),
				Pair.of("derp", "no"));
		doc = builder.get()
				.params(params)
				.build();
		assertEquals(params, doc.getParams());
	}
	
	@Test
	public void testBuilderSetParamPair(){
		Pair<String, String> param = Pair.of("test", "yes");
		doc = builder.get()
				.param(param)
				.build();
		List<Pair<String, String>> params = doc.getParams();
		assertEquals(1, params.size());
		assertEquals(param, params.get(0));
	}
	
	@Test
	public void testBuilderSetParamPieces(){
		doc = builder.get()
				.param("test", "yes")
				.build();
		List<Pair<String, String>> params = doc.getParams();
		assertEquals(1, params.size());
		Pair<String, String> param = params.get(0);
		assertEquals("test", param.getLeft());
		assertEquals("yes", param.getRight());
	}
	
	@Test
	public void testBuilderSetReturnVal(){
		doc = builder.get()
				.returnVal("this, to continue building")
				.build();
		assertEquals("this, to continue building", doc.getReturnVal());
	}
	
	@Test
	public void testSetThrowsInfos(){
		List<Pair<String, String>> throwsInfos = ListUtil.createList(Pair.of("Exception", "Because I can"),
				Pair.of("Throwable", "Because I want to"));
		doc = builder.get()
				.throwsInfos(throwsInfos)
				.build();
		assertEquals(throwsInfos, doc.getThrowsInfos());
	}
	
	@Test
	public void testSetThrowsInfoPair(){
		doc = builder.get()
				.throwsInfo(Pair.of("Exception", "Because I can"))
				.build();
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can")), doc.getThrowsInfos());
	}
	
	@Test
	public void testSetThrowsInfoPieces(){
		doc = builder.get()
				.throwsInfo("Exception", "Because I can")
				.build();
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can")), doc.getThrowsInfos());
	}
	
	@Test
	public void testBuilderSetAll(){
		List<String> content = ListUtil.createList("test", "derp");
		List<Pair<String, String>> params = ListUtil.createList(Pair.of("test", "yes"),
				Pair.of("derp", "no"));
		List<Pair<String, String>> throwsInfos = ListUtil.createList(Pair.of("Exception", "Because I can"),
				Pair.of("Throwable", "Because I want to"));
		doc = builder.get()
				.condensed()
				.content(content)
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.params(params)
				.returnVal("this, to continue building")
				.throwsInfos(throwsInfos)
				.build();
		assertTrue(doc.isCondensed());
		assertEquals(content, doc.getContent());
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
		assertEquals("Alpha v.0.1", doc.getVersion());
		assertEquals("Alpha v.0.0.1", doc.getSince());
		assertEquals(params, doc.getParams());
		assertEquals("this, to continue building", doc.getReturnVal());
		assertEquals(throwsInfos, doc.getThrowsInfos());
	}
	
	@Test
	public void testToString(){
		assertEquals("/**\n */", doc.toString());
	}
	
	@Test
	public void testToStringWithContent(){
		doc = builder.get()
				.content("test")
				.content("derp")
				.build();
		assertEquals("""
				/**
				 * test
				 * derp
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithAuthor(){
		doc = builder.get()
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("""
				/**
				 * @author Logan Ferree (Tadukoo)
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithVersion(){
		doc = builder.get()
				.version("Alpha v.0.1")
				.build();
		assertEquals("""
				/**
				 * @version Alpha v.0.1
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithSince(){
		doc = builder.get()
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("""
				/**
				 * @since Alpha v.0.0.1
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithSingleParam(){
		doc = builder.get()
				.param("test", "yes")
				.build();
		assertEquals("""
				/**
				 * @param test yes
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleParams(){
		doc = builder.get()
				.param("test", "yes")
				.param("derp", "no")
				.build();
		assertEquals("""
				/**
				 * @param test yes
				 * @param derp no
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithReturnVal(){
		doc = builder.get()
				.returnVal("this, to continue building")
				.build();
		assertEquals("""
				/**
				 * @return this, to continue building
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithThrowsInfo(){
		doc = builder.get()
				.throwsInfo("Exception", "Because I can")
				.build();
		assertEquals("""
				/**
				 * @throws Exception Because I can
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleThrowsInfos(){
		doc = builder.get()
				.throwsInfo("Exception", "Because I can")
				.throwsInfo("Throwable", "Because I want to")
				.build();
		assertEquals("""
				/**
				 * @throws Exception Because I can
				 * @throws Throwable Because I want to
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		doc = builder.get()
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
				.build();
		assertEquals("""
				/**
				 * test
				 * derp
				 *\040
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.1
				 * @since Alpha v.0.0.1
				 *\040
				 * @param test yes
				 * @param derp no
				 * @return this, to continue building
				 * @throws Exception Because I can
				 * @throws Throwable Because I want to
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringCondensed(){
		doc = builder.get()
				.condensed()
				.build();
		assertEquals("/** */", doc.toString());
	}
	
	@Test
	public void testToStringWithContentCondensed(){
		List<String> content = ListUtil.createList("test", "derp");
		doc = builder.get()
				.condensed()
				.content(content)
				.build();
		assertEquals("""
				/** test
				 * derp */""", doc.toString());
	}
	
	@Test
	public void testToStringWithAuthorCondensed(){
		doc = builder.get()
				.condensed()
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("/** @author Logan Ferree (Tadukoo) */", doc.toString());
	}
	
	@Test
	public void testToStringWithVersionCondensed(){
		doc = builder.get()
				.condensed()
				.version("Alpha v.0.1")
				.build();
		assertEquals("/** @version Alpha v.0.1 */", doc.toString());
	}
	
	@Test
	public void testToStringWithSinceCondensed(){
		doc = builder.get()
				.condensed()
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("/** @since Alpha v.0.0.1 */", doc.toString());
	}
	
	@Test
	public void testToStringWithSingleParamCondensed(){
		doc = builder.get()
				.condensed()
				.param("test", "yes")
				.build();
		assertEquals("""
				/** @param test yes */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleParamsCondensed(){
		doc = builder.get()
				.condensed()
				.param("test", "yes")
				.param("derp", "no")
				.build();
		assertEquals("""
				/** @param test yes
				 * @param derp no */""", doc.toString());
	}
	
	@Test
	public void testToStringWithReturnValCondensed(){
		doc = builder.get()
				.condensed()
				.returnVal("this, to continue building")
				.build();
		assertEquals("""
				/** @return this, to continue building */""", doc.toString());
	}
	
	@Test
	public void testToStringWithThrowsInfoCondensed(){
		doc = builder.get()
				.condensed()
				.throwsInfo("Exception", "Because I can")
				.build();
		assertEquals("""
				/** @throws Exception Because I can */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleThrowsInfosCondensed(){
		doc = builder.get()
				.condensed()
				.throwsInfo("Exception", "Because I can")
				.throwsInfo("Throwable", "Because I want to")
				.build();
		assertEquals("""
				/** @throws Exception Because I can
				 * @throws Throwable Because I want to */""", doc.toString());
	}
	
	@Test
	public void testToStringWithEverythingCondensed(){
		doc = builder.get()
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
				.build();
		assertEquals("""
				/** test
				 * derp
				 *\040
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.1
				 * @since Alpha v.0.0.1
				 *\040
				 * @param test yes
				 * @param derp no
				 * @return this, to continue building
				 * @throws Exception Because I can
				 * @throws Throwable Because I want to */""", doc.toString());
	}
	
	/*
	 * Special toString Cases
	 */
	
	@Test
	public void testToStringContentAndInfoAnnotations(){
		doc = builder.get()
				.content("test")
				.author("Me")
				.build();
		assertEquals("""
				/**
				 * test
				 *\040
				 * @author Me
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringContentAndCodeAnnotations(){
		doc = builder.get()
				.content("test")
				.returnVal("this")
				.build();
		assertEquals("""
				/**
				 * test
				 *\040
				 * @return this
				 */""", doc.toString());
	}
	
	/*
	 * Test Equals
	 */
	
	@Test
	public void testEquals(){
		doc = builder.get()
				.content("test")
				.content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes")
				.param("derp", "no")
				.returnVal("this, to continue building")
				.throwsInfo("Exception", "Because I can")
				.build();
		JavadocType otherDoc = builder.get()
				.content("test")
				.content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes")
				.param("derp", "no")
				.returnVal("this, to continue building")
				.throwsInfo("Exception", "Because I can")
				.build();
		assertEquals(otherDoc, doc);
	}
	
	@Test
	public void testEqualsNotEqual(){
		doc = builder.get()
				.content("test")
				.content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes")
				.param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		JavadocType otherDoc = builder.get()
				.content("tes")
				.content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes")
				.param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		assertNotEquals(otherDoc, doc);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(doc, "test");
	}
	
	@Test
	public void testToBuilderCode(){
		doc = builder.get()
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeCondensed(){
		doc = builder.get()
				.condensed()
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.condensed()\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeOneContent(){
		doc = builder.get()
				.content("something useful")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.content(\"something useful\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeTwoContent(){
		doc = builder.get()
				.content("something useful")
				.content("something else")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.content(\"something useful\")\n" +
				"\t\t.content(\"something else\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeAuthor(){
		doc = builder.get()
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.author(\"Logan Ferree (Tadukoo)\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeVersion(){
		doc = builder.get()
				.version("Alpha v.0.1")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.version(\"Alpha v.0.1\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeSince(){
		doc = builder.get()
				.since("Pre-Alpha")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.since(\"Pre-Alpha\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeOneParam(){
		doc = builder.get()
				.param("something", "does something")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.param(\"something\", \"does something\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeTwoParam(){
		doc = builder.get()
				.param("something", "does something")
				.param("somethingElse", "does something else")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.param(\"something\", \"does something\")\n" +
				"\t\t.param(\"somethingElse\", \"does something else\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeReturns(){
		doc = builder.get()
				.returnVal("this, to continue building")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.returnVal(\"this, to continue building\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeOneThrowsInfo(){
		doc = builder.get()
				.throwsInfo("Exception", "Because I can")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.throwsInfo(\"Exception\", \"Because I can\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeTwoThrowsInfos(){
		doc = builder.get()
				.throwsInfo("Exception", "Because I can")
				.throwsInfo("Throwable", "Because I want to")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.throwsInfo(\"Exception\", \"Because I can\")\n" +
				"\t\t.throwsInfo(\"Throwable\", \"Because I want to\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeEverything(){
		doc = builder.get()
				.condensed()
				.content("something useful")
				.content("something else")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Pre-Alpha")
				.param("something", "does something")
				.param("somethingElse", "does something else")
				.returnVal("this, to continue building")
				.throwsInfo("Exception", "Because I can")
				.throwsInfo("Throwable", "Because I want to")
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.condensed()\n" +
				"\t\t.content(\"something useful\")\n" +
				"\t\t.content(\"something else\")\n" +
				"\t\t.author(\"Logan Ferree (Tadukoo)\")\n" +
				"\t\t.version(\"Alpha v.0.1\")\n" +
				"\t\t.since(\"Pre-Alpha\")\n" +
				"\t\t.param(\"something\", \"does something\")\n" +
				"\t\t.param(\"somethingElse\", \"does something else\")\n" +
				"\t\t.returnVal(\"this, to continue building\")\n" +
				"\t\t.throwsInfo(\"Exception\", \"Because I can\")\n" +
				"\t\t.throwsInfo(\"Throwable\", \"Because I want to\")\n" +
				"\t\t.build()", doc.toBuilderCode());
	}
}
