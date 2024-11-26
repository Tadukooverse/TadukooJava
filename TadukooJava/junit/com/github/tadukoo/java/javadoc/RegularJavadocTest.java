package com.github.tadukoo.java.javadoc;

import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavadocTest extends DefaultJavadocTest<RegularJavadocTest.TestJavadoc>{
	
	public static class TestJavadoc extends Javadoc{
		
		protected TestJavadoc(
				boolean editable, boolean condensed, List<String> content, String author, String version, String since,
				List<Pair<String, String>> params, String returnVal, List<Pair<String, String>> throwsInfos){
			super(editable, condensed, content, author, version, since, params, returnVal, throwsInfos);
		}
		
		public static TestJavadocBuilder builder(){
			return new TestJavadocBuilder(false);
		}
	}
	
	public static class TestJavadocBuilder extends JavadocBuilder<TestJavadoc>{
		
		private final boolean editable;
		
		public TestJavadocBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavadoc constructJavadoc(){
			return new TestJavadoc(editable, condensed, content, author, version, since, params, returnVal, throwsInfos);
		}
	}
	
	public RegularJavadocTest(){
		super(TestJavadoc.class, TestJavadoc::builder);
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
}
