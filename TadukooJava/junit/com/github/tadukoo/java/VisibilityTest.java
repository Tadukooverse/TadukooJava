package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VisibilityTest{
	
	@ParameterizedTest
	@MethodSource("getVisibilityData")
	public void testVisibilityToken(String token, Visibility visibility, String ignored){
		assertEquals(token, visibility.getToken());
	}
	
	@ParameterizedTest
	@MethodSource("getVisibilityData")
	public void testVisibilityFromTextToken(String token, Visibility visibility, String ignored){
		assertEquals(visibility, Visibility.fromToken(token));
	}
	
	@ParameterizedTest
	@MethodSource("getVisibilityData")
	public void testVisibilityFromTextCaseInsensitive(String ignored, Visibility visibility, String testString){
		assertEquals(visibility, Visibility.fromToken(testString));
	}
	
	public static Stream<Arguments> getVisibilityData(){
		return Stream.of(
				Arguments.of("public", Visibility.PUBLIC, "pUBlIc"),
				Arguments.of("protected", Visibility.PROTECTED, "PROtecTeD"),
				Arguments.of("private", Visibility.PRIVATE, "PRiVAtE"),
				Arguments.of("", Visibility.NONE, "")
		);
	}
	
	@Test
	public void testFromTextGarbage(){
		assertNull(Visibility.fromToken("some_garbage_Stuff"));
	}
}
