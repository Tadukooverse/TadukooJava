package com.github.tadukoo.java.packagedeclaration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaPackageDeclarationBuilderTest{
	
	private static class TestJavaPackageDeclaration extends JavaPackageDeclaration{
		
		private TestJavaPackageDeclaration(boolean editable, String packageName){
			super(editable, packageName);
		}
	}
	
	private static class TestJavaPackageDeclarationBuilder extends JavaPackageDeclarationBuilder<TestJavaPackageDeclaration>{
		
		@Override
		protected TestJavaPackageDeclaration constructPackageDeclaration(){
			return new TestJavaPackageDeclaration(false, packageName);
		}
	}
	
	private JavaPackageDeclaration packageDeclaration;
	private String packageName;
	
	@BeforeEach
	public void setup(){
		packageName = "com.example";
		packageDeclaration = new TestJavaPackageDeclarationBuilder()
				.packageName(packageName)
				.build();
	}
	
	@Test
	public void testBuilderCopy(){
		JavaPackageDeclaration otherPackageDeclaration = new TestJavaPackageDeclarationBuilder()
				.packageName(packageName)
				.build();
		packageDeclaration = new TestJavaPackageDeclarationBuilder()
				.copy(otherPackageDeclaration)
				.build();
		assertEquals(otherPackageDeclaration, packageDeclaration);
	}
	
	@Test
	public void testBuilderSetPackageName(){
		assertEquals(packageName, packageDeclaration.getPackageName());
	}
	
	@Test
	public void testBuilderErrorNoPackageName(){
		try{
			packageDeclaration = new TestJavaPackageDeclarationBuilder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("packageName is required!", e.getMessage());
		}
	}
}
