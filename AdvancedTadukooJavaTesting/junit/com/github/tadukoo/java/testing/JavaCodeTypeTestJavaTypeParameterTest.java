package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertTypeParameterEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findTypeParameterDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestJavaTypeParameterTest{
	
	@ParameterizedTest
	@MethodSource("getTypeParameterDifferences")
	public void testFindTypeParameterDifferences(
			JavaTypeParameter expectedTypeParameter, JavaTypeParameter actualTypeParameter, List<String> differences){
		assertEquals(differences, findTypeParameterDifferences(expectedTypeParameter, actualTypeParameter));
	}
	
	@ParameterizedTest
	@MethodSource("getTypeParameterDifferences")
	public void testAssertTypeParameterEquals(
			JavaTypeParameter expectedTypeParameter, JavaTypeParameter actualTypeParameter, List<String> differences){
		try{
			assertTypeParameterEquals(expectedTypeParameter, actualTypeParameter);
			if(ListUtil.isNotBlank(differences)){
				fail();
			}
		}catch(AssertionFailedError e){
			if(ListUtil.isBlank(differences)){
				throw e;
			}
			assertEquals(buildTwoPartError(StringUtil.buildStringWithNewLines(differences),
					buildAssertError(expectedTypeParameter, actualTypeParameter)), e.getMessage());
		}
	}
	
	public static Stream<Arguments> getTypeParameterDifferences(){
		return Stream.of(
				// None
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						new ArrayList<>()
				),
				// Base Type Different
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						JavaTypeParameter.builder()
								.baseType("int")
								.build(),
						ListUtil.createList("Base Type is different!")
				),
				// Extends Type Different
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.build(),
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("Object")
								.build(),
						ListUtil.createList("Extends Type is different!")
				),
				// Canonical Name Different
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("String")
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaTypeParameter.builder()
								.baseType("String")
								.canonicalName("com.tadukoo.String")
								.build(),
						ListUtil.createList("Canonical Name is different!")
				),
				// All
				Arguments.of(
						JavaTypeParameter.builder()
								.baseType("?")
								.extendsType("String")
								.canonicalName(String.class.getCanonicalName())
								.build(),
						JavaTypeParameter.builder()
								.baseType("String")
								.build(),
						ListUtil.createList("Base Type is different!",
								"Extends Type is different!",
								"Canonical Name is different!")
				)
		);
	}
}
