package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavadocTest extends DefaultJavadocTest<EditableJavadoc>{
	
	public EditableJavadocTest(){
		super(EditableJavadoc.class, EditableJavadoc::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(doc.isEditable());
	}
	
	@Test
	public void testSetCondensed(){
		assertFalse(doc.isCondensed());
		doc.setCondensed(true);
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testAddContentSingleLine(){
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.addContent("Test");
		assertEquals(ListUtil.createList("Test"), doc.getContent());
		doc.addContent("Derp");
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
	}
	
	@Test
	public void testAddContentMultiline(){
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.addContent(ListUtil.createList("Test", "Derp"));
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
		doc.addContent(ListUtil.createList("blah", "yep"));
		assertEquals(ListUtil.createList("Test", "Derp", "blah", "yep"), doc.getContent());
	}
	
	@Test
	public void testSetContent(){
		assertEquals(new ArrayList<>(), doc.getContent());
		doc.setContent(ListUtil.createList("Test", "Derp"));
		assertEquals(ListUtil.createList("Test", "Derp"), doc.getContent());
		doc.setContent(ListUtil.createList("blah", "yep"));
		assertEquals(ListUtil.createList("blah", "yep"), doc.getContent());
	}
	
	@Test
	public void testSetAuthor(){
		assertNull(doc.getAuthor());
		doc.setAuthor("Me");
		assertEquals("Me", doc.getAuthor());
		doc.setAuthor("Logan Ferree (Tadukoo)");
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
	}
	
	@Test
	public void testSetVersion(){
		assertNull(doc.getVersion());
		doc.setVersion("Test");
		assertEquals("Test", doc.getVersion());
		doc.setVersion("Alpha v.0.1");
		assertEquals("Alpha v.0.1", doc.getVersion());
	}
	
	@Test
	public void testSetSince(){
		assertNull(doc.getSince());
		doc.setSince("Test");
		assertEquals("Test", doc.getSince());
		doc.setSince("Alpha v.0.1");
		assertEquals("Alpha v.0.1", doc.getSince());
	}
	
	@Test
	public void testAddParamPieces(){
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.addParam("Test", "something");
		assertEquals(ListUtil.createList(Pair.of("Test", "something")), doc.getParams());
		doc.addParam("Derp", "something else");
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
	}
	
	@Test
	public void testAddParamPair(){
		assertEquals(new ArrayList<>(), doc.getParams());
		doc.addParam(Pair.of("Test", "something"));
		assertEquals(ListUtil.createList(Pair.of("Test", "something")), doc.getParams());
		doc.addParam(Pair.of("Derp", "something else"));
		assertEquals(ListUtil.createList(Pair.of("Test", "something"), Pair.of("Derp", "something else")),
				doc.getParams());
	}
	
	@Test
	public void testAddParams(){
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
		assertNull(doc.getReturnVal());
		doc.setReturnVal("a string");
		assertEquals("a string", doc.getReturnVal());
		doc.setReturnVal("this");
		assertEquals("this", doc.getReturnVal());
	}
}
