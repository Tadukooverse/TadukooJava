package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavadocBuilderTest{
	
	private static class TestJavadoc extends Javadoc{
		
		protected TestJavadoc(
				boolean condensed, List<String> content, String author, String version, String since,
				List<Pair<String, String>> params, String returnVal){
			super(false, condensed, content, author, version, since, params, returnVal);
		}
	}
	
	private static class TestJavadocBuilder extends JavadocBuilder<TestJavadoc>{
		
		@Override
		protected TestJavadoc constructJavadoc(){
			return new TestJavadoc(condensed, content, author, version, since, params, returnVal);
		}
	}
	
	private Javadoc doc;
	
	@BeforeEach
	public void setup(){
		doc = new TestJavadocBuilder().build();
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
	public void testBuilderSetCondensedValue(){
		doc = new TestJavadocBuilder()
				.condensed(true)
				.build();
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testBuilderSetCondensed(){
		doc = new TestJavadocBuilder()
				.condensed()
				.build();
		assertTrue(doc.isCondensed());
	}
	
	@Test
	public void testBuilderSetContent(){
		List<String> content = ListUtil.createList("test", "derp");
		doc = new TestJavadocBuilder()
				.content(content)
				.build();
		assertEquals(content, doc.getContent());
	}
	
	@Test
	public void testBuilderSetContentLine(){
		String content = "test";
		doc = new TestJavadocBuilder()
				.content(content)
				.build();
		List<String> actContent = doc.getContent();
		assertEquals(1, actContent.size());
		assertEquals(content, actContent.get(0));
	}
	
	@Test
	public void testBuilderSetAuthor(){
		doc = new TestJavadocBuilder()
				.author("Logan Ferree (Tadukoo)")
				.build();
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
	}
	
	@Test
	public void testBuilderSetVersion(){
		doc = new TestJavadocBuilder()
				.version("Alpha v.0.1")
				.build();
		assertEquals("Alpha v.0.1", doc.getVersion());
	}
	
	@Test
	public void testBuilderSetSince(){
		doc = new TestJavadocBuilder()
				.since("Alpha v.0.0.1")
				.build();
		assertEquals("Alpha v.0.0.1", doc.getSince());
	}
	
	@Test
	public void testBuilderSetParams(){
		List<Pair<String, String>> params = ListUtil.createList(Pair.of("test", "yes"),
				Pair.of("derp", "no"));
		doc = new TestJavadocBuilder()
				.params(params)
				.build();
		assertEquals(params, doc.getParams());
	}
	
	@Test
	public void testBuilderSetParamPair(){
		Pair<String, String> param = Pair.of("test", "yes");
		doc = new TestJavadocBuilder()
				.param(param)
				.build();
		List<Pair<String, String>> params = doc.getParams();
		assertEquals(1, params.size());
		assertEquals(param, params.get(0));
	}
	
	@Test
	public void testBuilderSetParamPieces(){
		doc = new TestJavadocBuilder()
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
		doc = new TestJavadocBuilder()
				.returnVal("this, to continue building")
				.build();
		assertEquals("this, to continue building", doc.getReturnVal());
	}
	
	@Test
	public void testBuilderSetAll(){
		List<String> content = ListUtil.createList("test", "derp");
		List<Pair<String, String>> params = ListUtil.createList(Pair.of("test", "yes"),
				Pair.of("derp", "no"));
		doc = new TestJavadocBuilder()
				.condensed()
				.content(content)
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Alpha v.0.0.1")
				.params(params)
				.returnVal("this, to continue building")
				.build();
		assertTrue(doc.isCondensed());
		assertEquals(content, doc.getContent());
		assertEquals("Logan Ferree (Tadukoo)", doc.getAuthor());
		assertEquals("Alpha v.0.1", doc.getVersion());
		assertEquals("Alpha v.0.0.1", doc.getSince());
		assertEquals(params, doc.getParams());
		assertEquals("this, to continue building", doc.getReturnVal());
	}
}
