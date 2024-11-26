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
	
	@Test
	public void testAddThrowsInfoPieces(){
		assertEquals(new ArrayList<>(), doc.getThrowsInfos());
		doc.addThrowsInfo("Exception", "Because I can");
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can")), doc.getThrowsInfos());
		doc.addThrowsInfo("Throwable", "Because I want to");
		assertEquals(ListUtil.createList(Pair.of("Exception", "Because I can"),
				Pair.of("Throwable", "Because I want to")), doc.getThrowsInfos());
	}
	
	@Test
	public void testAddThrowsInfoPair(){
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
