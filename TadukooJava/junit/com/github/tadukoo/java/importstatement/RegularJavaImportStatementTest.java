package com.github.tadukoo.java.importstatement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegularJavaImportStatementTest extends DefaultJavaImportStatementTest<RegularJavaImportStatementTest.TestJavaImportStatement>{
	
	public static class TestJavaImportStatement extends JavaImportStatement{
		
		private TestJavaImportStatement(boolean editable, boolean isStatic, String importName){
			super(editable, isStatic, importName);
		}
		
		public static TestJavaImportStatementBuilder builder(){
			return new TestJavaImportStatementBuilder(false);
		}
	}
	
	public static class TestJavaImportStatementBuilder extends JavaImportStatementBuilder<TestJavaImportStatement>{
		
		private final boolean editable;
		
		private TestJavaImportStatementBuilder(boolean editable){
			super();
			this.editable = editable;
		}
		
		@Override
		protected TestJavaImportStatement constructImportStatement(){
			return new TestJavaImportStatement(editable, isStatic, importName);
		}
	}
	
	public RegularJavaImportStatementTest(){
		super(TestJavaImportStatement::builder);
	}
	
	@Test
	public void testEditable(){
		assertFalse(importStatement.isEditable());
	}
	
	@Test
	public void testSetEditable(){
		importStatement = new TestJavaImportStatementBuilder(true)
				.importName(importName)
				.build();
		assertTrue(importStatement.isEditable());
	}
}
