package com.github.tadukoo.java.importstatement;

import com.github.tadukoo.java.JavaTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaImportStatementTest{
	
	private static class TestJavaImportStatement extends JavaImportStatement{
		
		private TestJavaImportStatement(boolean editable, boolean isStatic, String importName){
			super(editable, isStatic, importName);
		}
	}
	
	private static class TestJavaImportStatementBuilder extends JavaImportStatementBuilder<TestJavaImportStatement>{
		
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
	
	private String importName;
	private JavaImportStatement importStatement;
	
	@BeforeEach
	public void setup(){
		importName = "com.example";
		importStatement = new TestJavaImportStatementBuilder(false)
				.importName(importName)
				.build();
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
	
	@Test
	public void testSetImportName(){
		assertEquals(importName, importStatement.getImportName());
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaTypes.IMPORT_STATEMENT, importStatement.getType());
	}
	
	@Test
	public void testToString(){
		assertEquals("import com.example;", importStatement.toString());
	}
	
	@Test
	public void testToStringStatic(){
		importStatement = new TestJavaImportStatementBuilder(false)
				.importName(importName)
				.isStatic()
				.build();
		assertEquals("import static com.example;", importStatement.toString());
	}
	
	@Test
	public void testEquals(){
		JavaImportStatement otherImportStatement = new TestJavaImportStatementBuilder(true)
				.importName(importName)
				.build();
		assertEquals(importStatement, otherImportStatement);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaImportStatement otherImportStatement = new TestJavaImportStatementBuilder(true)
				.importName(importName)
				.isStatic()
				.build();
		assertNotEquals(importStatement, otherImportStatement);
	}
	
	@Test
	public void testEqualsDifferentType(){
		//noinspection AssertBetweenInconvertibleTypes
		assertNotEquals(importStatement, "testing");
	}
}
