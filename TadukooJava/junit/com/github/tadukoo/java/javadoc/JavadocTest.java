package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavadocTest{
	
	private static class TestJavadoc extends Javadoc{
		
		protected TestJavadoc(
				boolean editable, boolean condensed, List<String> content, String author, String version, String since,
				List<Pair<String, String>> params, String returnVal){
			super(editable, condensed, content, author, version, since, params, returnVal);
		}
	}
	
	private static class TestJavadocBuilder extends JavadocBuilder<TestJavadoc>{
		
		private final boolean editable;
		
		public TestJavadocBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavadoc constructJavadoc(){
			return new TestJavadoc(editable, condensed, content, author, version, since, params, returnVal);
		}
	}
	
	private Javadoc doc;
	
	@BeforeEach
	public void setup(){
		doc = new TestJavadocBuilder(false).build();
	}
	
	@Test
	public void testGetType(){
		assertEquals(JavaTypes.JAVADOC, doc.getJavaType());
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(doc.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		doc = new TestJavadocBuilder(true).build();
		assertTrue(doc.isEditable());
	}
	
	@Test
	public void testToString(){
		assertEquals("/**\n */", doc.toString());
	}
	
	@Test
	public void testToStringWithContent(){
		doc = new TestJavadocBuilder(false)
				.content(ListUtil.createList("test", "derp"))
				.build();
		assertEquals("""
				/**
				 * test
				 * derp
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithAuthor(){
		doc = new TestJavadocBuilder(false)
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("""
				/**
				 * @author Logan Ferree (Tadukoo)
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithVersion(){
		doc = new TestJavadocBuilder(false)
				.version("Alpha v.0.1")
				.build();
		assertEquals("""
				/**
				 * @version Alpha v.0.1
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithSince(){
		doc = new TestJavadocBuilder(false)
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("""
				/**
				 * @since Alpha v.0.0.1
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithSingleParam(){
		doc = new TestJavadocBuilder(false)
				.param("test", "yes")
				.build();
		assertEquals("""
				/**
				 * @param test yes
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleParams(){
		doc = new TestJavadocBuilder(false)
				.params(ListUtil.createList(Pair.of("test", "yes"), Pair.of("derp", "no")))
				.build();
		assertEquals("""
				/**
				 * @param test yes
				 * @param derp no
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithReturnVal(){
		doc = new TestJavadocBuilder(false)
				.returnVal("this, to continue building")
				.build();
		assertEquals("""
				/**
				 * @return this, to continue building
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		doc = new TestJavadocBuilder(false)
				.content("test").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
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
				 */""", doc.toString());
	}
	
	@Test
	public void testToStringCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.build();
		assertEquals("/** */", doc.toString());
	}
	
	@Test
	public void testToStringWithContentCondensed(){
		List<String> content = ListUtil.createList("test", "derp");
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.content(content)
				.build();
		assertEquals("""
				/** test
				 * derp */""", doc.toString());
	}
	
	@Test
	public void testToStringWithAuthorCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("/** @author Logan Ferree (Tadukoo) */", doc.toString());
	}
	
	@Test
	public void testToStringWithVersionCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.version("Alpha v.0.1")
				.build();
		assertEquals("/** @version Alpha v.0.1 */", doc.toString());
	}
	
	@Test
	public void testToStringWithSinceCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("/** @since Alpha v.0.0.1 */", doc.toString());
	}
	
	@Test
	public void testToStringWithSingleParamCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.param("test", "yes")
				.build();
		assertEquals("""
				/** @param test yes */""", doc.toString());
	}
	
	@Test
	public void testToStringWithMultipleParamsCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.params(ListUtil.createList(Pair.of("test", "yes"), Pair.of("derp", "no")))
				.build();
		assertEquals("""
				/** @param test yes
				 * @param derp no */""", doc.toString());
	}
	
	@Test
	public void testToStringWithReturnValCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.returnVal("this, to continue building")
				.build();
		assertEquals("""
				/** @return this, to continue building */""", doc.toString());
	}
	
	@Test
	public void testToStringWithEverythingCondensed(){
		doc = new TestJavadocBuilder(false)
				.condensed(true)
				.content("test").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
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
				 * @return this, to continue building */""", doc.toString());
	}
	
	/*
	 * Special toString Cases
	 */
	
	@Test
	public void testToStringContentAndInfoAnnotations(){
		doc = new TestJavadocBuilder(false)
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
		doc = new TestJavadocBuilder(false)
				.content("test")
				.returnVal("this")
				.build();
		doc = new Javadoc(false, false, ListUtil.createList("test"), null, null, null,
				new ArrayList<>(), "this"){ };
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
		doc = new TestJavadocBuilder(false)
				.content("test").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		Javadoc otherDoc = new TestJavadocBuilder(false)
				.content("test").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		assertEquals(otherDoc, doc);
	}
	
	@Test
	public void testEqualsNotEqual(){
		doc = new TestJavadocBuilder(false)
				.content("test").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		Javadoc otherDoc = new TestJavadocBuilder(false)
				.content("tes").content("derp")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.param("test", "yes").param("derp", "no")
				.returnVal("this, to continue building")
				.build();
		assertNotEquals(otherDoc, doc);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(doc, "test");
	}
}
