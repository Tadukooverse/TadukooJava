package com.github.tadukoo.java;

import com.github.tadukoo.util.SetUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static com.github.tadukoo.java.JavaTokens.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTokensTest{
	
	@ParameterizedTest
	@MethodSource("getTokenData")
	public void testToken(String expectedText, String token){
		assertEquals(expectedText, token);
	}
	
	public static Stream<Arguments> getTokenData(){
		return Stream.of(
				Arguments.of(";", SEMICOLON),
				Arguments.of("package", PACKAGE_TOKEN),
				Arguments.of("import", IMPORT_TOKEN),
				Arguments.of("class", CLASS_TOKEN),
				Arguments.of("throws", THROWS_TOKEN),
				Arguments.of("extends", EXTENDS_TOKEN),
				Arguments.of("implements", IMPLEMENTS_TOKEN),
				Arguments.of("/**", JAVADOC_START_TOKEN),
				Arguments.of("*", JAVADOC_LINE_TOKEN),
				Arguments.of("/*", MULTI_LINE_COMMENT_START_TOKEN),
				Arguments.of("*/", MULTI_LINE_COMMENT_CLOSE_TOKEN),
				Arguments.of("//", SINGLE_LINE_COMMENT_TOKEN),
				Arguments.of("@", ANNOTATION_START_TOKEN),
				Arguments.of("(", PARAMETER_OPEN_TOKEN),
				Arguments.of(")", PARAMETER_CLOSE_TOKEN),
				Arguments.of(",", LIST_SEPARATOR_TOKEN),
				Arguments.of("{", BLOCK_OPEN_TOKEN),
				Arguments.of("}", BLOCK_CLOSE_TOKEN),
				Arguments.of("=", ASSIGNMENT_OPERATOR_TOKEN),
				Arguments.of("<", TYPE_PARAMETER_OPEN_TOKEN),
				Arguments.of(">", TYPE_PARAMETER_CLOSE_TOKEN),
				Arguments.of("...", VARARGS_TOKEN),
				Arguments.of("author", JAVADOC_AUTHOR_TOKEN),
				Arguments.of("version", JAVADOC_VERSION_TOKEN),
				Arguments.of("since", JAVADOC_SINCE_TOKEN),
				Arguments.of("param", JAVADOC_PARAM_TOKEN),
				Arguments.of("return", JAVADOC_RETURN_TOKEN),
				Arguments.of("private", PRIVATE_MODIFIER),
				Arguments.of("protected", PROTECTED_MODIFIER),
				Arguments.of("public", PUBLIC_MODIFIER),
				Arguments.of("abstract", ABSTRACT_MODIFIER),
				Arguments.of("static", STATIC_MODIFIER),
				Arguments.of("final", FINAL_MODIFIER)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getTokenSetData")
	public void testTokenSet(Set<String> expectedSet, Set<String> actualSet){
		assertEquals(expectedSet, actualSet);
	}
	
	public static Stream<Arguments> getTokenSetData(){
		return Stream.of(
				Arguments.of(SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER), VISIBILITY_MODIFIERS),
				Arguments.of(SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER,
								ABSTRACT_MODIFIER, STATIC_MODIFIER, FINAL_MODIFIER),
						MODIFIERS)
		);
	}
}
