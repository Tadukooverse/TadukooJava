package com.github.tadukoo.java.importstatement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditableJavaImportStatementTest extends DefaultJavaImportStatementTest<EditableJavaImportStatement>{
	
	public EditableJavaImportStatementTest(){
		super(EditableJavaImportStatement::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertTrue(importStatement.isEditable());
	}
	
	@Test
	public void testSetStatic(){
		assertFalse(importStatement.isStatic());
		importStatement.setStatic(false);
		assertFalse(importStatement.isStatic());
		importStatement.setStatic(true);
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testSetImportName(){
		assertEquals(importName, importStatement.getImportName());
		importStatement.setImportName("org.help.me");
		assertEquals("org.help.me", importStatement.getImportName());
	}
}
