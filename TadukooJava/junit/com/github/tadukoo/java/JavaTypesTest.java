package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaTypesTest{
	
	@Test
	public void testUNKNOWN(){
		assertEquals("UNKNOWN", UNKNOWN.toString());
		assertNull(UNKNOWN.getJavaTypeClass());
	}
	
	@Test
	public void testPACKAGE_DECLARATION(){
		assertEquals("PACKAGE_DECLARATION", PACKAGE_DECLARATION.toString());
		assertEquals(JavaPackageDeclaration.class, PACKAGE_DECLARATION.getJavaTypeClass());
	}
	
	@Test
	public void testIMPORT_STATEMENT(){
		assertEquals("IMPORT_STATEMENT", IMPORT_STATEMENT.toString());
		assertEquals(JavaImportStatement.class, IMPORT_STATEMENT.getJavaTypeClass());
	}
	
	@Test
	public void testJAVADOC(){
		assertEquals("JAVADOC", JAVADOC.toString());
		assertEquals(Javadoc.class, JAVADOC.getJavaTypeClass());
	}
	
	@Test
	public void testANNOTATION(){
		assertEquals("ANNOTATION", ANNOTATION.toString());
		assertEquals(JavaAnnotation.class, ANNOTATION.getJavaTypeClass());
	}
	
	@Test
	public void testFIELD(){
		assertEquals("FIELD", FIELD.toString());
		assertEquals(JavaField.class, FIELD.getJavaTypeClass());
	}
	
	@Test
	public void testMETHOD(){
		assertEquals("METHOD", METHOD.toString());
		assertEquals(JavaMethod.class, METHOD.getJavaTypeClass());
	}
	
	@Test
	public void testCLASS(){
		assertEquals("CLASS", CLASS.toString());
		assertEquals(JavaClass.class, CLASS.getJavaTypeClass());
	}
}
