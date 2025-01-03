package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import com.github.tadukoo.util.tuple.Pair;
import com.github.tadukoo.util.tuple.Triple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaPackageDeclarationParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaPackageDeclaration, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForPackageDeclaration,
					JavaPackageDeclarationParser::parsePackageDeclaration
	);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaPackageDeclaration, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"package com.example;",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t    \t
								package com.example;""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Whitespace Before Package Name
				Triple.of(
						"""
								package\t  \t
								\tcom.example;""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Whitespace In Package Name
				Triple.of(
						"""
								package com\t \t
								\t.
								\t  example;""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Whitespace After Package Name
				Triple.of(
						"""
								package com.example  \t
								;""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Trailing Whitespace
				Triple.of(
						"""
								package com.example;\t
								  \t""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				),
				// Insane Whitespace
				Triple.of(
						"""
								\t    \tpackage\t  \t
								\tcom\t \t
								\t.
								\t  example  \t
								;\t
								  \t""",
						EditableJavaPackageDeclaration.builder()
								.packageName("com.example")
								.build(),
						"package com.example;"
				)
		);
		
		return parsingData.stream()
				.flatMap(triple -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), triple.getLeft(), triple.getMiddle(),
								triple.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getParsingData")
	public void testParsing(
			ThrowingFunction<String, JavaPackageDeclaration, JavaParsingException> parseMethod, String textToParse,
			JavaPackageDeclaration expectedPackageDeclaration, String expectedText) throws JavaParsingException{
		JavaPackageDeclaration packageDeclaration = parseMethod.apply(textToParse);
		assertNotNull(packageDeclaration);
		assertEquals(expectedPackageDeclaration, packageDeclaration);
		assertEquals(expectedText, packageDeclaration.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// No Package Name
				Pair.of(
						"package ;",
						"Failed to find package name in package declaration!"
				),
				// No Semicolon
				Pair.of(
						"package com.example",
						"Failed to find semicolon ending package declaration!"
				),
				// No Package Name or Semicolon
				Pair.of(
						"package",
						"""
								Failed to find package name in package declaration!
								Failed to find semicolon ending package declaration!"""
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaPackageDeclaration, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testPackageTokenMissing(){
		try{
			JavaPackageDeclarationParser.parsePackageDeclaration("""
					com.example;
					""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.PACKAGE_DECLARATION,
							"First token of package declaration must be '" + PACKAGE_TOKEN + "'"),
					e.getMessage());
		}
	}
}
