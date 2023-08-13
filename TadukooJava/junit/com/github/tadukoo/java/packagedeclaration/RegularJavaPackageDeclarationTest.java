package com.github.tadukoo.java.packagedeclaration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaPackageDeclarationTest extends DefaultJavaPackageDeclarationTest<RegularJavaPackageDeclarationTest.TestJavaPackageDeclaration>{
	
	public static class TestJavaPackageDeclaration extends JavaPackageDeclaration{
		
		private TestJavaPackageDeclaration(boolean editable, String packageName){
			super(editable, packageName);
		}
		
		public static TestJavaPackageDeclarationBuilder builder(){
			return new TestJavaPackageDeclarationBuilder(false);
		}
	}
	
	public static class TestJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<TestJavaPackageDeclaration>{
		
		private final boolean editable;
		
		private TestJavaPackageDeclarationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaPackageDeclaration constructPackageDeclaration(){
			return new TestJavaPackageDeclaration(editable, packageName);
		}
	}
	
	public RegularJavaPackageDeclarationTest(){
		super(TestJavaPackageDeclaration::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(packageDeclaration.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		packageDeclaration = new TestJavaPackageDeclarationBuilder(true)
				.packageName(packageName)
				.build();
		assertTrue(packageDeclaration.isEditable());
	}
}
