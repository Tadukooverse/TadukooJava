package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FullJavaParserCombinationErrorsTest extends BaseJavaParserTest{
	
	private static Stream<Arguments> getParseData(){
		return Stream.of(
				// Two Package Declarations
				Arguments.of(
						"""
								package com.example;
								package com.example.other;""",
						"Encountered two package declarations!"
				),
				// Package Declaration after Class
				Arguments.of(
						"""
								class Test{
								}
								package com.example;""",
						"Encountered package declaration after class!"
				),
				// Import Statement after Class
				Arguments.of(
						"""
								class Test{
								}
								import com.example;""",
						"Encountered import statement after class!"
				),
				// Two Javadocs
				Arguments.of(
						"""
								/** {@inheritDoc} */
								/** something */""",
						"Encountered two Javadocs!"
				),
				// Javadoc after Field
				Arguments.of(
						"""
								String type;
								/** {@inheritDoc} */""",
						"Encountered a Javadoc after field!"
				),
				// Javadoc after Method
				Arguments.of(
						"""
								void test(){}
								/** {@inheritDoc} */""",
						"Encountered a Javadoc after method!"
				),
				// Javadoc after Class
				Arguments.of(
						"""
								class Test{}
								/** {@inheritDoc} */""",
						"Encountered a Javadoc after class!"
				),
				// Annotation after Class
				Arguments.of(
						"""
								class Test{
								}
								@Test""",
						"Encountered annotation after class!"
				),
				// 2 Fields
				Arguments.of(
						"""
								String type;
								int derp;""",
						"Encountered two fields!"
				),
				// Class before Field
				Arguments.of(
						"""
								class Test{
								}
								String type;""",
						"Encountered field outside a class!"
				),
				// Package Declaration before Field
				Arguments.of(
						"""
								package some.package;
								String type;""",
						"Encountered package declaration before field!"
				),
				// Import Statement before Field
				Arguments.of(
						"""
								import some.classname;
								String type;""",
						"Encountered import statements before field!"
				),
				// 2 Methods
				Arguments.of(
						"""
								String something(){}
								int somethingElse(){}""",
						"Encountered two methods!"
				),
				// Class before Method
				Arguments.of(
						"""
								class Test{
								}
								String something(){}""",
						"Encountered method outside a class!"
				),
				// Package Declaration before Method
				Arguments.of(
						"""
								package some.package;
								String something(){}""",
						"Encountered package declaration before method!"
				),
				// Import Statement Before Method
				Arguments.of(
						"""
								import some.classname;
								String something(){}""",
						"Encountered import statements before method!"
				),
				// 2 Outer Classes
				Arguments.of(
						"""
								class Test{
								}
								class Test2{
								}""",
						"Encountered two outer level classes!"
				),
				// Field before Class
				Arguments.of(
						"""
								String type;
								class Test{
								}""",
						"Encountered fields outside a class!"
				),
				// Method before Class
				Arguments.of(
						"""
								String something(){}
								class Test{
								}""",
						"Encountered methods outside a class!"
				),
				// Failed to Determine Field or Method
				Arguments.of(
						"""
								String something""",
						"Failed to determine type from token 'String'"
				)
		);
	}
	
	@ParameterizedTest
	@MethodSource("getParseData")
	public void testErrors(String text, String error){
		try{
			FullJavaParser.parseType(text);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.UNKNOWN,
					error), e.getMessage());
		}
	}
}
