package com.github.tadukoo.java.annotation;

import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaAnnotationTest extends DefaultJavaAnnotationTest<RegularJavaAnnotationTest.TestJavaAnnotation>{
	
	public static class TestJavaAnnotation extends JavaAnnotation{
		private TestJavaAnnotation(boolean editable, String name, String canonicalName, List<Pair<String, String>> parameters){
			super(editable, name, canonicalName, parameters);
		}
		
		public static TestJavaAnnotationBuilder builder(){
			return new TestJavaAnnotationBuilder(false);
		}
	}
	
	public static class TestJavaAnnotationBuilder extends JavaAnnotationBuilder<TestJavaAnnotation>{
		
		private final boolean editable;
		
		private TestJavaAnnotationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaAnnotation constructAnnotation(){
			return new TestJavaAnnotation(editable, name, canonicalName, parameters);
		}
	}
	
	public RegularJavaAnnotationTest(){
		super(TestJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(annotation.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		annotation = new TestJavaAnnotationBuilder(true).name(name).build();
		assertTrue(annotation.isEditable());
	}
}
