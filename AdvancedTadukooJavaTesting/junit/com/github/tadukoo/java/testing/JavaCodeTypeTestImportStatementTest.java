package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.UneditableJavaImportStatement;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertImportStatementEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findImportStatementDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestImportStatementTest{
	
	@ParameterizedTest
	@MethodSource("getImportStatementDifferences")
	public void testFindImportStatementDifferences(
			JavaImportStatement expectedImportStatement, JavaImportStatement actualImportStatement,
			List<String> differences){
		assertEquals(differences, findImportStatementDifferences(expectedImportStatement, actualImportStatement));
	}
	
	@ParameterizedTest
	@MethodSource("getImportStatementDifferences")
	public void testAssertImportStatementEquals(
			JavaImportStatement expectedImportStatement, JavaImportStatement actualImportStatement,
			List<String> differences){
		try{
			assertImportStatementEquals(expectedImportStatement, actualImportStatement);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedImportStatement, actualImportStatement)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getImportStatementDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						new ArrayList<>()
				),
				// Editable
				Arguments.of(
						UneditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Static
				Arguments.of(
						EditableJavaImportStatement.builder()
								.isStatic(false).importName("com.test")
								.build(),
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						ListUtil.createList("Static is different!")
				),
				// Import Name
				Arguments.of(
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.example")
								.build(),
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						ListUtil.createList("Import Name is different!")
				),
				// All
				Arguments.of(
						UneditableJavaImportStatement.builder()
								.isStatic(false).importName("com.example")
								.build(),
						EditableJavaImportStatement.builder()
								.isStatic().importName("com.test")
								.build(),
						ListUtil.createList(
								"Editable is different!",
								"Static is different!",
								"Import Name is different!"
						)
				)
		);
	}
}
