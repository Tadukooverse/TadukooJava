package com.github.tadukoo.java.field;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaFieldTest extends DefaultJavaFieldTest<RegularJavaFieldTest.TestJavaField>{
	
	public static class TestJavaField extends JavaField{
		
		protected TestJavaField(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isStatic, boolean isFinal,
				String type, String name, String value){
			super(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
		
		public static TestJavaFieldBuilder builder(){
			return new TestJavaFieldBuilder(false);
		}
	}
	
	public static class TestJavaFieldBuilder extends JavaFieldBuilder<TestJavaField>{
		
		private final boolean editable;
		
		public TestJavaFieldBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaField constructField(){
			return new TestJavaField(editable, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	public RegularJavaFieldTest(){
		super(TestJavaField::builder, UneditableJavadoc::builder, UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(field.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		field = new TestJavaFieldBuilder(true)
				.type(type).name(name)
				.build();
		assertTrue(field.isEditable());
	}
}
