package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaCodeTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JavaCodeTypesTest{
	
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
	public void testMULTI_LINE_COMMENT(){
		assertEquals("MULTI_LINE_COMMENT", MULTI_LINE_COMMENT.toString());
		assertEquals(JavaMultiLineComment.class, MULTI_LINE_COMMENT.getJavaTypeClass());
	}
	
	@Test
	public void testSINGLE_LINE_COMMENT(){
		assertEquals("SINGLE_LINE_COMMENT", SINGLE_LINE_COMMENT.toString());
		assertEquals(JavaSingleLineComment.class, SINGLE_LINE_COMMENT.getJavaTypeClass());
	}
	
	@Test
	public void testANNOTATION(){
		assertEquals("ANNOTATION", ANNOTATION.toString());
		assertEquals(JavaAnnotation.class, ANNOTATION.getJavaTypeClass());
	}
	
	@Test
	public void testTYPE_WITH_MODIFIERS(){
		assertEquals("TYPE_WITH_MODIFIERS", TYPE_WITH_MODIFIERS.toString());
		assertNull(TYPE_WITH_MODIFIERS.getJavaTypeClass());
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
