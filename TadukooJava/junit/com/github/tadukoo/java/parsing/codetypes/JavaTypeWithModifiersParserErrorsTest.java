package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTypeWithModifiersParserErrorsTest extends BaseJavaParserTest{
	
	public static Stream<Arguments> getParseTypeWithModifiersData(){
		return Stream.of(
				// Does Not Start with Modifier
				Arguments.of(
						ListUtil.createList("String", " ", "type;"),
						JavaCodeTypes.FIELD,
						"First token of type with modifiers must be a modifier"
				),
				// Failed to determine type
				Arguments.of(
						ListUtil.createList("static", " ", "yep"),
						JavaCodeTypes.UNKNOWN,
						"Failed to determine type\n" +
								"Failed to determine result type"
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getParseTypeWithModifiersData")
	public void testParseTypeWithModifiers(List<String> tokens, JavaCodeTypes codeType, String error){
		try{
			JavaTypeWithModifiersParser.parseTypeWithModifiers(tokens, 0);
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(codeType, error), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getParseTypeData(){
		return Stream.of(
				// Duplicate Static
				Arguments.of(
						"""
									static static class Test{
									}
									""",
						JavaCodeTypes.CLASS,
						"Found duplicate modifier: 'static'"
				),
				// Duplicate Static and Final
				Arguments.of(
						"""
									static static final final class Test{
									}
									""",
						JavaCodeTypes.CLASS,
						"Found duplicate modifier: 'static'\n" +
								"Found duplicate modifier: 'final'"
				),
				// Multiple Visibility Modifiers
				Arguments.of(
						"""
									private public class Test{
									}
									""",
						JavaCodeTypes.CLASS,
						"Found multiple visibility modifiers"
				),
				// Failed to determine type
				Arguments.of(
						"""
									private
									""",
						JavaCodeTypes.TYPE_WITH_MODIFIERS,
						"Failed to determine result type"
				),
				// All Errors
				Arguments.of(
						"""
									private public static static final final
									""",
						JavaCodeTypes.TYPE_WITH_MODIFIERS,
						"""
								Found duplicate modifier: 'static'
								Found duplicate modifier: 'final'
								Found multiple visibility modifiers
								Failed to determine result type"""
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getParseTypeData")
	public void testParseTypeError(String parseText, JavaCodeTypes codeType, String error){
		try{
			FullJavaParser.parseType(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(codeType, error), e.getMessage());
		}
	}
}
