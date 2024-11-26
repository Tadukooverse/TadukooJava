package com.github.tadukoo.java.packagedeclaration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaPackageDeclarationTest extends DefaultJavaPackageDeclarationTest<UneditableJavaPackageDeclaration>{
	protected UneditableJavaPackageDeclarationTest(){
		super(UneditableJavaPackageDeclaration.class, UneditableJavaPackageDeclaration::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(packageDeclaration.isEditable());
	}
}
