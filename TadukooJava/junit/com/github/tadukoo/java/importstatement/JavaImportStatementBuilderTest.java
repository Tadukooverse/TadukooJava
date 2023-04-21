package com.github.tadukoo.java.importstatement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaImportStatementBuilderTest{
	
	private static class TestJavaImportStatement extends JavaImportStatement{
		
		private TestJavaImportStatement(boolean editable, boolean isStatic, String importName){
			super(editable, isStatic, importName);
		}
	}
	
	private static class TestJavaImportStatementBuilder extends JavaImportStatementBuilder<TestJavaImportStatement>{
		
		
		@Override
		protected TestJavaImportStatement constructImportStatement(){
			return new TestJavaImportStatement(false, isStatic, importName);
		}
	}
	
	private String importName;
	private JavaImportStatement importStatement;
	
	@BeforeEach
	public void setup(){
		importName = "com.example";
		importStatement = new TestJavaImportStatementBuilder()
				.importName(importName)
				.build();
	}
	
	@Test
	public void testBuilderSetImportName(){
		assertEquals(importName, importStatement.getImportName());
	}
	
	@Test
	public void testBuilderDefaultIsStatic(){
		assertFalse(importStatement.isStatic());
	}
	
	@Test
	public void testBuilderIsStatic(){
		importStatement = new TestJavaImportStatementBuilder()
				.importName(importName)
				.isStatic()
				.build();
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		importStatement = new TestJavaImportStatementBuilder()
				.importName(importName)
				.isStatic(true)
				.build();
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testBuilderErrorNoImportName(){
		try{
			new TestJavaImportStatementBuilder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("importName is required!", e.getMessage());
		}
	}
}
