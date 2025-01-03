package com.github.tadukoo.java;

import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaCodeUtilTest{
	
	@ParameterizedTest
	@MethodSource("getToJavaStringData")
	public void testConvertToJavaString(Object obj, String expectedText){
		assertEquals(expectedText, JavaCodeUtil.convertToJavaString(obj));
	}
	
	public static Stream<Arguments> getToJavaStringData(){
		return Stream.of(
				// Null
				Arguments.of(null, null),
				// String
				Arguments.of("testing", "testing"),
				// Enum
				Arguments.of(JavaCodeTypes.CLASS, "JavaCodeTypes.CLASS"),
				// Annotation
				Arguments.of(EditableJavaAnnotation.builder()
						.name("Test")
						.build(), "@Test")
		);
	}
	
	@Test
	public void testEscapeString(){
		String theString = "test something \t\n \r\n \t\t\t";
		assertEquals("test something \\t\\n \\r\\n \\t\\t\\t", JavaCodeUtil.escapeString(theString));
	}
}
