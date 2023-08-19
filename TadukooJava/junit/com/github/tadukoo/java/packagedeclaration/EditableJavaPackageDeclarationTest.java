package com.github.tadukoo.java.packagedeclaration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaPackageDeclarationTest extends DefaultJavaPackageDeclarationTest<EditableJavaPackageDeclaration>{
	
	protected EditableJavaPackageDeclarationTest(){
		super(EditableJavaPackageDeclaration.class, EditableJavaPackageDeclaration::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(packageDeclaration.isEditable());
	}
	
	@Test
	public void testSetPackageName(){
		assertEquals(packageName, packageDeclaration.getPackageName());
		packageDeclaration.setPackageName("org.java.test");
		assertEquals("org.java.test", packageDeclaration.getPackageName());
	}
}
