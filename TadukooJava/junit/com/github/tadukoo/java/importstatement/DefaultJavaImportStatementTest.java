package com.github.tadukoo.java.importstatement;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.util.functional.NoException;
import com.github.tadukoo.util.functional.supplier.ThrowingSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class DefaultJavaImportStatementTest<ImportStatementType extends JavaImportStatement>{
	
	private final Class<ImportStatementType> clazz;
	private final ThrowingSupplier<JavaImportStatementBuilder<ImportStatementType>, NoException> builder;
	protected String importName;
	protected ImportStatementType importStatement;
	
	protected DefaultJavaImportStatementTest(
			Class<ImportStatementType> clazz,
			ThrowingSupplier<JavaImportStatementBuilder<ImportStatementType>, NoException> builder){
		this.clazz = clazz;
		this.builder = builder;
	}
	
	@BeforeEach
	public void setup(){
		importName = "com.example";
		importStatement = builder.get()
				.importName(importName)
				.build();
	}
	
	@Test
	public void testGetType(){
		Assertions.assertEquals(JavaCodeTypes.IMPORT_STATEMENT, importStatement.getJavaCodeType());
	}
	
	@Test
	public void testBuilderCopy(){
		JavaImportStatement otherImportStatement = builder.get()
				.isStatic()
				.importName(importName)
				.build();
		importStatement = builder.get()
				.copy(otherImportStatement)
				.build();
		assertEquals(otherImportStatement, importStatement);
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
		importStatement = builder.get()
				.importName(importName)
				.isStatic()
				.build();
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testBuilderSetIsStatic(){
		importStatement = builder.get()
				.importName(importName)
				.isStatic(true)
				.build();
		assertTrue(importStatement.isStatic());
	}
	
	@Test
	public void testBuilderErrorNoImportName(){
		try{
			builder.get()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("importName is required!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("import com.example;", importStatement.toString());
	}
	
	@Test
	public void testToStringStatic(){
		importStatement = builder.get()
				.importName(importName)
				.isStatic()
				.build();
		assertEquals("import static com.example;", importStatement.toString());
	}
	
	@Test
	public void testEquals(){
		JavaImportStatement otherImportStatement = builder.get()
				.importName(importName)
				.build();
		assertEquals(importStatement, otherImportStatement);
	}
	
	@Test
	public void testEqualsNotEqual(){
		JavaImportStatement otherImportStatement = builder.get()
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
	
	@Test
	public void testToBuilderCodeNotStatic(){
		importStatement = builder.get()
				.importName(importName)
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.importName(\"" + importName + "\")\n" +
				"\t\t.build()", importStatement.toBuilderCode());
	}
	
	@Test
	public void testToBuilderCodeStatic(){
		importStatement = builder.get()
				.isStatic()
				.importName(importName)
				.build();
		assertEquals(clazz.getSimpleName() + ".builder()\n" +
				"\t\t.isStatic()\n" +
				"\t\t.importName(\"" + importName + "\")\n" +
				"\t\t.build()", importStatement.toBuilderCode());
	}
}
