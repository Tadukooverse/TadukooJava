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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaCodeTypesTest{
	
	@ParameterizedTest
	@MethodSource("getJavaCodeTypesData")
	void testToString(JavaCodeTypes type, String expectedToString, Class<?> ignored, String ignored2e){
		assertEquals(expectedToString, type.toString());
	}
	
	@ParameterizedTest
	@MethodSource("getJavaCodeTypesData")
	void testJavaTypeClass(JavaCodeTypes type, String ignored, Class<?> expectedJavaTypeClass, String ignored2){
		assertEquals(expectedJavaTypeClass, type.getJavaTypeClass());
	}
	
	@ParameterizedTest
	@MethodSource("getJavaCodeTypesData")
	public void testStandardName(JavaCodeTypes type, String ignored, Class<?> ignored2, String expectedStandardName){
		assertEquals(expectedStandardName, type.getStandardName());
	}
	
	public static Stream<Arguments> getJavaCodeTypesData(){
		return Stream.of(
				Arguments.of(
						JavaCodeTypes.UNKNOWN, "UNKNOWN", null, "unknown"),
				Arguments.of(
						JavaCodeTypes.TYPE_PARAMETER, "TYPE_PARAMETER", JavaTypeParameter.class, "type parameter"),
				Arguments.of(
						JavaCodeTypes.TYPE, "TYPE", JavaType.class, "type"),
				Arguments.of(
						JavaCodeTypes.PARAMETER, "PARAMETER", JavaParameter.class, "parameter"),
				Arguments.of(
						JavaCodeTypes.PACKAGE_DECLARATION, "PACKAGE_DECLARATION",
						JavaPackageDeclaration.class, "package declaration"),
				Arguments.of(
						JavaCodeTypes.IMPORT_STATEMENT, "IMPORT_STATEMENT",
						JavaImportStatement.class, "import statement"),
				Arguments.of(JavaCodeTypes.JAVADOC, "JAVADOC", Javadoc.class, "Javadoc"),
				Arguments.of(
						JavaCodeTypes.MULTI_LINE_COMMENT, "MULTI_LINE_COMMENT",
						JavaMultiLineComment.class, "multi-line comment"),
				Arguments.of(
						JavaCodeTypes.SINGLE_LINE_COMMENT, "SINGLE_LINE_COMMENT",
						JavaSingleLineComment.class, "single-line comment"),
				Arguments.of(JavaCodeTypes.ANNOTATION, "ANNOTATION", JavaAnnotation.class, "annotation"),
				Arguments.of(
						JavaCodeTypes.TYPE_WITH_MODIFIERS, "TYPE_WITH_MODIFIERS", null, "type with modifiers"),
				Arguments.of(JavaCodeTypes.FIELD, "FIELD", JavaField.class, "field"),
				Arguments.of(JavaCodeTypes.METHOD, "METHOD", JavaMethod.class, "method"),
				Arguments.of(JavaCodeTypes.CLASS, "CLASS", JavaClass.class, "class")
		);
	}
}
