package com.github.tadukoo.java.packagedeclaration;

import com.github.tadukoo.java.JavaCodeTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaPackageDeclarationTest{
	
	private static class TestJavaPackageDeclaration extends JavaPackageDeclaration{
		
		private TestJavaPackageDeclaration(boolean editable, String packageName){
			super(editable, packageName);
		}
	}
	
	private static class TestJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<TestJavaPackageDeclaration>{
		
		private final boolean editable;
		
		private TestJavaPackageDeclarationBuilder(boolean editable){
			this.editable = editable;
		}
		
		@Override
		protected TestJavaPackageDeclaration constructPackageDeclaration(){
			return new TestJavaPackageDeclaration(editable, packageName);
		}
	}
	
	private JavaPackageDeclaration packageDeclaration;
	private String packageName;
	
	@BeforeEach
	public void setup(){
		packageName = "com.example";
		packageDeclaration = new TestJavaPackageDeclarationBuilder(false)
				.packageName(packageName)
				.build();
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaCodeTypes.PACKAGE_DECLARATION, packageDeclaration.getJavaCodeType());
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
	
	@Test
	public void testGetPackageName(){
		assertEquals(packageName, packageDeclaration.getPackageName());
	}
	
	@Test
	public void testToString(){
		assertEquals("package com.example;", packageDeclaration.toString());
	}
	
	@Test
	public void testEquals(){
		JavaPackageDeclaration otherPackageDeclaration = new TestJavaPackageDeclarationBuilder(true)
				.packageName(packageName)
				.build();
		assertEquals(packageDeclaration, otherPackageDeclaration);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaPackageDeclaration otherPackageDeclaration = new TestJavaPackageDeclarationBuilder(true)
				.packageName("com.github.tadukoo")
				.build();
		assertNotEquals(packageDeclaration, otherPackageDeclaration);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(packageDeclaration, "testing");
	}
}
