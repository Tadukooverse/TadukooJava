package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
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

public class JavaImportStatementParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaImportStatement, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForImportStatement,
					JavaImportStatementParser::parseImportStatement
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaImportStatement, String>> parsingData = ListUtil.createList(
				// Simple
				Triple.of(
						"import com.example;",
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						"import com.example;"
				),
				// Static Import
				Triple.of(
						"import static com.example;",
						EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build(),
						"import static com.example;"
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t     \t
								   \s
								\t import com.example;""",
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						"import com.example;"
				),
				// Whitespace after Import
				Triple.of(
						"""
								import\t     \t
								   \s
								\t com.example;""",
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						"import com.example;"
				),
				// Whitespace within Import Name
				Triple.of(
						"""
								import com\t    \s
								\t     .   \s
								   \t    example;""",
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						"import com.example;"
				),
				// Whitespace after Import Name
				Triple.of(
						"""
								import com.example   \s
								\t
								   ;""",
						EditableJavaImportStatement.builder()
								.importName("com.example")
								.build(),
						"import com.example;"
				),
				// Whitespace before Static
				Triple.of(
						"""
								import    \s
								\t     \s
								    static com.example;""",
						EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build(),
						"import static com.example;"
				),
				// Whitespace after Static
				Triple.of(
						"""
								import static       \s
								\t    \s
								  com.example;""",
						EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build(),
						"import static com.example;"
				),
				// Insane Whitespace
				Triple.of(
						"""
								\t     \t
								   \s
								\t import\t     \t
								   \s
								\t static       \s
								\t    \s
								  com\t    \s
								\t     .   \s
								   \t    example   \s
								\t
								   ;""",
						EditableJavaImportStatement.builder()
								.isStatic()
								.importName("com.example")
								.build(),
						"import static com.example;"
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
			ThrowingFunction<String, JavaImportStatement, JavaParsingException> parseMethod, String textToParse,
			JavaImportStatement expectedImportStatement, String expectedText) throws JavaParsingException{
		JavaImportStatement importStatement = parseMethod.apply(textToParse);
		assertNotNull(importStatement);
		assertEquals(expectedImportStatement, importStatement);
		assertEquals(expectedText, importStatement.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// No Import Name
				Pair.of(
						"import ;",
						"Failed to find import name in import statement!"
				),
				// No Semicolon
				Pair.of(
						"import com.example",
						"Failed to find semicolon ending import statement!"
				),
				// Both Errors
				Pair.of(
						"import ",
						"""
								Failed to find import name in import statement!
								Failed to find semicolon ending import statement!"""
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaImportStatement, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testMissingImportToken(){
		try{
			JavaImportStatementParser.parseImportStatement("""
					com.example;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.IMPORT_STATEMENT,
							"First token of import statement must be '" + IMPORT_TOKEN + "'"),
					e.getMessage());
		}
	}
}
