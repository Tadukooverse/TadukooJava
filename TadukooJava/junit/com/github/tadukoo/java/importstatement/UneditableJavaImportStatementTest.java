package com.github.tadukoo.java.importstatement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class UneditableJavaImportStatementTest extends DefaultJavaImportStatementTest<UneditableJavaImportStatement>{
	
	protected UneditableJavaImportStatementTest(){
		super(UneditableJavaImportStatement::builder);
	}
	
	@Test
	public void testIsEditable(){
		assertFalse(importStatement.isEditable());
	}
}
