package com.github.tadukoo.java.method;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.annotation.UneditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaMethodTest extends DefaultJavaMethodTest<RegularJavaMethodTest.TestJavaMethod>{
	
	public static class TestJavaMethod extends JavaMethod{
		
		private TestJavaMethod(
				boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
				Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
				String returnType, String name,
				List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
			super(editable, javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal,
					returnType, name,
					parameters, throwTypes, lines);
		}
		
		public static TestJavaMethodBuilder builder(){
			return new TestJavaMethodBuilder(false);
		}
	}
	
	public static class TestJavaMethodBuilder extends JavaMethodBuilder<TestJavaMethod>{
		
		private final boolean editable;
		
		private TestJavaMethodBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected List<String> checkForSpecificErrors(){
			return new ArrayList<>();
		}
		
		@Override
		protected TestJavaMethod constructMethod(){
			return new TestJavaMethod(editable, javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal,
					returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	public RegularJavaMethodTest(){
		super(TestJavaMethod::builder, UneditableJavadoc::builder, UneditableJavaAnnotation::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(method.isEditable());
	}
	
	@Test
	public void testIsEditableTrue(){
		method = new TestJavaMethodBuilder(true)
				.returnType(returnType)
				.build();
		assertTrue(method.isEditable());
	}
}
