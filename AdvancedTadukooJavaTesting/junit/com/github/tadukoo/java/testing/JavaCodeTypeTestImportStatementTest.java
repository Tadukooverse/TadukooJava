package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertImportStatementEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findImportStatementDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestImportStatementTest{
	
	@Test
	public void testFindImportStatementDifferencesNone(){
		assertEquals(new ArrayList<>(), findImportStatementDifferences(
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build()));
	}
	
	@Test
	public void testFindImportStatementDifferencesEditable(){
		assertEquals(ListUtil.createList(
				"Editable is different!"
		), findImportStatementDifferences(
				UneditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build()));
	}
	
	@Test
	public void testFindImportStatementDifferencesStatic(){
		assertEquals(ListUtil.createList(
				"Static is different!"
		), findImportStatementDifferences(
				EditableJavaImportStatement.builder()
						.isStatic(false).importName("com.test")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build()));
	}
	
	@Test
	public void testFindImportStatementDifferencesImportName(){
		assertEquals(ListUtil.createList(
				"Import Name is different!"
		), findImportStatementDifferences(
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.example")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build()));
	}
	
	@Test
	public void testFindImportStatementDifferencesAll(){
		assertEquals(ListUtil.createList(
				"Editable is different!",
				"Static is different!",
				"Import Name is different!"
		), findImportStatementDifferences(
				UneditableJavaImportStatement.builder()
						.isStatic(false).importName("com.example")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build()));
	}
	
	@Test
	public void testAssertImportStatementEqualsNone(){
		assertImportStatementEquals(
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build(),
				EditableJavaImportStatement.builder()
						.isStatic().importName("com.test")
						.build());
	}
	
	@Test
	public void testAssertImportStatementEqualsEditable(){
		JavaImportStatement importStatement1 = UneditableJavaImportStatement.builder()
				.isStatic().importName("com.test")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.isStatic().importName("com.test")
				.build();
		try{
			assertImportStatementEquals(importStatement1, importStatement2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
							buildAssertError(importStatement1, importStatement2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertImportStatementEqualsStatic(){
		JavaImportStatement importStatement1 = EditableJavaImportStatement.builder()
				.isStatic(false).importName("com.test")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.isStatic().importName("com.test")
				.build();
		try{
			assertImportStatementEquals(importStatement1, importStatement2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Static is different!",
							buildAssertError(importStatement1, importStatement2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertImportStatementEqualsImportName(){
		JavaImportStatement importStatement1 = EditableJavaImportStatement.builder()
				.isStatic().importName("com.example")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.isStatic().importName("com.test")
				.build();
		try{
			assertImportStatementEquals(importStatement1, importStatement2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Import Name is different!",
							buildAssertError(importStatement1, importStatement2)),
					e.getMessage());
		}
	}
	
	@Test
	public void testAssertImportStatementEqualsAll(){
		JavaImportStatement importStatement1 = UneditableJavaImportStatement.builder()
				.isStatic(false).importName("com.example")
				.build();
		JavaImportStatement importStatement2 = EditableJavaImportStatement.builder()
				.isStatic().importName("com.test")
				.build();
		try{
			assertImportStatementEquals(importStatement1, importStatement2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Editable is different!
					Static is different!
					Import Name is different!""",
							buildAssertError(importStatement1, importStatement2)),
					e.getMessage());
		}
	}
}
