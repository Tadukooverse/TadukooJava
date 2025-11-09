package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.UneditableJavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertPackageDeclarationEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findPackageDeclarationDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestPackageDeclarationTest{
	
	@ParameterizedTest
	@MethodSource("getPackageDeclarationDifferences")
	public void testFindPackageDeclarationDifferences(
			JavaPackageDeclaration expectedPackageDeclaration, JavaPackageDeclaration actualPackageDeclaration,
			List<String> differences){
		assertEquals(differences, findPackageDeclarationDifferences(expectedPackageDeclaration, actualPackageDeclaration));
	}
	
	@ParameterizedTest
	@MethodSource("getPackageDeclarationDifferences")
	public void testAssertPackageDeclarationEquals(
			JavaPackageDeclaration expectedPackageDeclaration, JavaPackageDeclaration actualPackageDeclaration,
			List<String> differences){
		try{
			assertPackageDeclarationEquals(expectedPackageDeclaration, actualPackageDeclaration);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedPackageDeclaration, actualPackageDeclaration)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getPackageDeclarationDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						new ArrayList<>()
				),
				// Both Null
				Arguments.of(
						null, null,
						new ArrayList<>()
				),
				// 1 Null 2 Not
				Arguments.of(
						null,
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						ListUtil.createList("One of the package declarations is null, and the other isn't!")
				),
				// 2 Null 1 Not
				Arguments.of(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						null,
						ListUtil.createList("One of the package declarations is null, and the other isn't!")
				),
				// Editable
				Arguments.of(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						UneditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						ListUtil.createList("Editable is different!")
				),
				// Package Name
				Arguments.of(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						EditableJavaPackageDeclaration.builder()
								.packageName("com.derp")
								.build(),
						ListUtil.createList("Package Name is different!")
				),
				// All Differences
				Arguments.of(
						EditableJavaPackageDeclaration.builder()
								.packageName("com.test")
								.build(),
						UneditableJavaPackageDeclaration.builder()
								.packageName("com.derp")
								.build(),
						ListUtil.createList("Editable is different!", "Package Name is different!")
				)
		);
	}
}
