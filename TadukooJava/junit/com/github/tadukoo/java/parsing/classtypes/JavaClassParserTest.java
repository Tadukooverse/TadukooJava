package com.github.tadukoo.java.parsing.classtypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.comment.EditableJavaMultiLineComment;
import com.github.tadukoo.java.comment.EditableJavaSingleLineComment;
import com.github.tadukoo.java.field.EditableJavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
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

public class JavaClassParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaClass, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForClass,
					JavaClassParser::parseClass
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaClass, String>> parsingData = ListUtil.<Triple<String, JavaClass, String>>createList(
				// Simple
				Triple.of(
						"""
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// With Package Declaration
				Triple.of(
						"""
								package com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// With Import
				Triple.of(
						"""
								import com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", false)
								.className("Test")
								.build(),
						"""
								import com.example;
								
								class Test{
								\t
								}
								"""
				),
				// With Multiple Imports
				Triple.of(
						"""
								import com.example.Something;
								import com.example.SomethingElse;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example.Something", false)
								.importName("com.example.SomethingElse", false)
								.className("Test")
								.build(),
						"""
								import com.example.Something;
								import com.example.SomethingElse;
								
								class Test{
								\t
								}
								"""
				),
				// With Multiple Imports and Line Between
				Triple.of(
						"""
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example.Something", false)
								.importName("org.example.SomethingElse", false)
								.className("Test")
								.build(),
						"""
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								class Test{
								\t
								}
								"""
				),
				// With Static Import
				Triple.of(
						"""
								import static com.example.SomethingStatic;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example.SomethingStatic", true)
								.className("Test")
								.build(),
						"""
								import static com.example.SomethingStatic;
								
								class Test{
								\t
								}
								"""
				),
				// With Multiple Static Imports
				Triple.of(
						"""
								import static com.example.SomethingStatic;
								import static com.example.SomethingElseStatic;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example.SomethingStatic", true)
								.importName("com.example.SomethingElseStatic", true)
								.className("Test")
								.build(),
						"""
								import static com.example.SomethingElseStatic;
								import static com.example.SomethingStatic;
								
								class Test{
								\t
								}
								"""
				),
				// With Multiple Static Imports and Line Between
				Triple.of(
						"""
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example.SomethingStatic", true)
								.importName("org.example.SomethingElseStatic", true)
								.className("Test")
								.build(),
						"""
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								class Test{
								\t
								}
								"""
				),
				// With Javadoc
				Triple.of(
						"""
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								class Test{
								}""",
						EditableJavaClass.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Something here")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.1")
										.build())
								.className("Test")
								.build(),
						"""
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								class Test{
								\t
								}
								"""
				),
				// With Single Annotation
				Triple.of(
						"""
								@Test
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.className("Test")
								.build(),
						"""
								@Test
								class Test{
								\t
								}
								"""
				),
				// With Multiple Annotations
				Triple.of(
						"""
								@Test
								@Derp(type=String.class)
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.className("Test")
								.build(),
						"""
								@Test
								@Derp(type = String.class)
								class Test{
								\t
								}
								"""
				),
				// With Private
				Triple.of(
						"""
								private class Test{
								}
								""",
						EditableJavaClass.builder()
								.visibility(Visibility.PRIVATE)
								.className("Test")
								.build(),
						"""
								private class Test{
								\t
								}
								"""
				),
				// With Protected
				Triple.of(
						"""
								protected class Test{
								}
								""",
						EditableJavaClass.builder()
								.visibility(Visibility.PROTECTED)
								.className("Test")
								.build(),
						"""
								protected class Test{
								\t
								}
								"""
				),
				// With Public
				Triple.of(
						"""
								public class Test{
								}
								""",
						EditableJavaClass.builder()
								.visibility(Visibility.PUBLIC)
								.className("Test")
								.build(),
						"""
								public class Test{
								\t
								}
								"""
				),
				// With Abstract
				Triple.of(
						"""
								abstract class Test{
								}
								""",
						EditableJavaClass.builder()
								.isAbstract()
								.className("Test")
								.build(),
						"""
								abstract class Test{
								\t
								}
								"""
				),
				// With static
				Triple.of(
						"""
								class Derp{
									static class Test{
									}
								}
								""",
						EditableJavaClass.builder()
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.isStatic()
										.className("Test")
										.build())
								.className("Derp")
								.build(),
						"""
								class Derp{
								\t
									static class Test{
									\t
									}
								\t
								}
								"""
				),
				// With Final
				Triple.of(
						"""
								final class Test{
								}
								""",
						EditableJavaClass.builder()
								.isFinal()
								.className("Test")
								.build(),
						"""
								final class Test{
								\t
								}
								"""
				),
				// With Modifiers Reversed
				Triple.of(
						"""
								final private class Derp{
									final static public class Test{
									}
								}
								""",
						EditableJavaClass.builder()
								.visibility(Visibility.PRIVATE)
								.isFinal()
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.visibility(Visibility.PUBLIC)
										.isStatic().isFinal()
										.className("Test")
										.build())
								.className("Derp")
								.build(),
						"""
								private final class Derp{
								\t
									public static final class Test{
									\t
									}
								\t
								}
								"""
				),
				// With Type Parameter on class name
				Triple.of(
						"""
								class Test<T>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<T>")
								.build(),
						"""
								class Test<T>{
								\t
								}
								"""
				),
				// With Type Parameter on class name and space
				Triple.of(
						"""
								class Test <T>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<T>")
								.build(),
						"""
								class Test<T>{
								\t
								}
								"""
				),
				// With Multiple Type Parameters on class name
				Triple.of(
						"""
								class Test<T, S>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<T, S>")
								.build(),
						"""
								class Test<T, S>{
								\t
								}
								"""
				),
				// With Complex Type Parameters on class name
				Triple.of(
						"""
								class Test<Map<String, Object>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<Map<String, Object>>")
								.build(),
						"""
								class Test<Map<String, Object>>{
								\t
								}
								"""
				),
				// With Complex Type Parameters on class name 2
				Triple.of(
						"""
								class Test<Character, Map<Character, ?>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<Character, Map<Character, ?>>")
								.build(),
						"""
								class Test<Character, Map<Character, ?>>{
								\t
								}
								"""
				),
				// With Complex Type Parameters on class name 3
				Triple.of(
						"""
								class Test<? extends List<String>, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test<? extends List<String>, Object>")
								.build(),
						"""
								class Test<? extends List<String>, Object>{
								\t
								}
								"""
				),
				// With Super Class
				Triple.of(
						"""
								class Test extends Derp{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp")
								.build(),
						"""
								class Test extends Derp{
								\t
								}
								"""
				),
				// With Super Class with Type Parameter
				Triple.of(
						"""
								class Test extends Derp<T>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<T>")
								.build(),
						"""
								class Test extends Derp<T>{
								\t
								}
								"""
				),
				// With Super Class with Multiple Type Parameters
				Triple.of(
						"""
								class Test extends Derp<T, S>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<T, S>")
								.build(),
						"""
								class Test extends Derp<T, S>{
								\t
								}
								"""
				),
				// With Super Class with Space Before Type Parameters
				Triple.of(
						"""
								class Test extends Derp <T, S>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<T, S>")
								.build(),
						"""
								class Test extends Derp<T, S>{
								\t
								}
								"""
				),
				// With Super Class with Complex Type
				Triple.of(
						"""
								class Test extends Derp<Map<String, Object>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<Map<String, Object>>")
								.build(),
						"""
								class Test extends Derp<Map<String, Object>>{
								\t
								}
								"""
				),
				// With Super Class with Complex Type 2
				Triple.of(
						"""
								class Test extends Derp<Character, Map<Character, ?>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<Character, Map<Character, ?>>")
								.build(),
						"""
								class Test extends Derp<Character, Map<Character, ?>>{
								\t
								}
								"""
				),
				// With Super Class with Complex Type 3
				Triple.of(
						"""
								class Test extends Derp<? extends List<String>, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.superClassName("Derp<? extends List<String>, Object>")
								.build(),
						"""
								class Test extends Derp<? extends List<String>, Object>{
								\t
								}
								"""
				),
				// With Implements Interface
				Triple.of(
						"""
								class Test implements Derp{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp")
								.build(),
						"""
								class Test implements Derp{
								\t
								}
								"""
				),
				// With Implements Interface With Type Parameter
				Triple.of(
						"""
								class Test implements Derp<String>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<String>")
								.build(),
						"""
								class Test implements Derp<String>{
								\t
								}
								"""
				),
				// With Implements Interface With Multiple Type Parameters
				Triple.of(
						"""
								class Test implements Derp<String, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<String, Object>")
								.build(),
						"""
								class Test implements Derp<String, Object>{
								\t
								}
								"""
				),
				// With Implements Interface With Complex Type
				Triple.of(
						"""
								class Test implements Derp<Map<String, Object>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<Map<String, Object>>")
								.build(),
						"""
								class Test implements Derp<Map<String, Object>>{
								\t
								}
								"""
				),
				// With Implements Interface With Complex Type 2
				Triple.of(
						"""
								class Test implements Derp<Character, Map<Character, ?>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<Character, Map<Character, ?>>")
								.build(),
						"""
								class Test implements Derp<Character, Map<Character, ?>>{
								\t
								}
								"""
				),
				// With Implements Interface With Complex Type 3
				Triple.of(
						"""
								class Test implements Derp<? extends List<String>, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<? extends List<String>, Object>")
								.build(),
						"""
								class Test implements Derp<? extends List<String>, Object>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces
				Triple.of(
						"""
								class Test implements Derp, Blah{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp")
								.implementsInterfaceName("Blah")
								.build(),
						"""
								class Test implements Derp, Blah{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces With Type Parameter
				Triple.of(
						"""
								class Test implements Derp<String>, Blah<String>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<String>")
								.implementsInterfaceName("Blah<String>")
								.build(),
						"""
								class Test implements Derp<String>, Blah<String>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces With Multiple Type Parameters
				Triple.of(
						"""
								class Test implements Derp<String, Object>, Blah<String, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<String, Object>")
								.implementsInterfaceName("Blah<String, Object>")
								.build(),
						"""
								class Test implements Derp<String, Object>, Blah<String, Object>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces With Complex Type
				Triple.of(
						"""
								class Test implements Derp<Map<String, Object>>, Blah<Map<String, Object>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<Map<String, Object>>")
								.implementsInterfaceName("Blah<Map<String, Object>>")
								.build(),
						"""
								class Test implements Derp<Map<String, Object>>, Blah<Map<String, Object>>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces With Complex Type 2
				Triple.of(
						"""
								class Test implements Derp<Character, Map<Character, ?>>, Blah<Character, Map<Character, ?>>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<Character, Map<Character, ?>>")
								.implementsInterfaceName("Blah<Character, Map<Character, ?>>")
								.build(),
						"""
								class Test implements Derp<Character, Map<Character, ?>>, Blah<Character, Map<Character, ?>>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces With Complex Type 3
				Triple.of(
						"""
								class Test implements Derp<? extends List<String>, Object>, Blah<? extends List<String>, Object>{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp<? extends List<String>, Object>")
								.implementsInterfaceName("Blah<? extends List<String>, Object>")
								.build(),
						"""
								class Test implements Derp<? extends List<String>, Object>, Blah<? extends List<String>, Object>{
								\t
								}
								"""
				),
				// With Multiple Implements Interfaces no space around comma
				Triple.of(
						"""
								class Test implements Derp,Blah{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Derp")
								.implementsInterfaceName("Blah")
								.build(),
						"""
								class Test implements Derp, Blah{
								\t
								}
								"""
				),
				// With Static Code Block
				Triple.of(
						"""
								class Test{
									static{
										doSomething();
									}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.staticCodeBlock(ListUtil.createList("doSomething();"))
								.build(),
						"""
								class Test{
								\t
									static{
										doSomething();
									}
								}
								"""
				),
				// With Static Code Block Multiple Lines
				Triple.of(
						"""
								class Test{
									static{
										doSomething();
										doSomethingElse();
										doSomethingEvenMore();
									}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.staticCodeBlock(ListUtil.createList(
										"doSomething();",
										"doSomethingElse();",
										"doSomethingEvenMore();"
								))
								.build(),
						"""
								class Test{
								\t
									static{
										doSomething();
										doSomethingElse();
										doSomethingEvenMore();
									}
								}
								"""
				),
				// With Static Code Block Multiple Lines and indentation
				Triple.of(
						"""
								class Test{
									static{
										doSomething();
										for(int i = 0; i < 5; i++){
											doSomethingElse();
											doSomethingEvenMore();
										}
									}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.staticCodeBlock(ListUtil.createList(
										"doSomething();",
										"for(int i = 0; i < 5; i++){",
										"\tdoSomethingElse();",
										"\tdoSomethingEvenMore();",
										"}"
								))
								.build(),
						"""
								class Test{
								\t
									static{
										doSomething();
										for(int i = 0; i < 5; i++){
											doSomethingElse();
											doSomethingEvenMore();
										}
									}
								}
								"""
				),
				// With Static Code Block No whitespace
				Triple.of(
						"""
								class Test{
									static{doSomething();}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.staticCodeBlock(ListUtil.createList(
										"doSomething();"
								))
								.build(),
						"""
								class Test{
								\t
									static{
										doSomething();
									}
								}
								"""
				),
				// With Single Line Comment
				Triple.of(
						"""
								class Test{
									// some comment
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.singleLineComment("some comment")
								.build(),
						"""
								class Test{
								\t
									// some comment
								}
								"""
				),
				// With Multi Line Comment
				Triple.of(
						"""
								class Test{
									/*
									 * some comment
									 */
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.multiLineComment("some comment")
								.build(),
						"""
								class Test{
								\t
									/*
									 * some comment
									 */
								}
								"""
				),
				// With Inner Class
				Triple.of(
						"""
								class Test{
									class Derp{
									}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Derp")
										.build())
								.build(),
						"""
								class Test{
								\t
									class Derp{
									\t
									}
								\t
								}
								"""
				),
				// With Multiple Inner Classes
				Triple.of(
						"""
								class Test{
									class Derp{
									}
									class Yep{
									}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Derp")
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Yep")
										.build())
								.build(),
						"""
								class Test{
								\t
									class Derp{
									\t
									}
								\t
									class Yep{
									\t
									}
								\t
								}
								"""
				),
				// With Field
				Triple.of(
						"""
								class Test{
									String name;
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("name")
										.build())
								.build(),
						"""
								class Test{
								\t
									String name;
								}
								"""
				),
				// With Multiple Fields
				Triple.of(
						"""
								class Test{
									String name;
									int derp;
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.type("String").name("name")
										.build())
								.field(EditableJavaField.builder()
										.type("int").name("derp")
										.build())
								.build(),
						"""
								class Test{
								\t
									String name;
									int derp;
								}
								"""
				),
				// With Method
				Triple.of(
						"""
								class Test{
									String type(){}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("type")
										.build())
								.build(),
						"""
								class Test{
								\t
									String type(){ }
								}
								"""
				),
				// With Multiple Methods
				Triple.of(
						"""
								class Test{
									String type(){}
									int getVersion(){}
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.returnType("String").name("type")
										.build())
								.method(EditableJavaMethod.builder()
										.returnType("int").name("getVersion")
										.build())
								.build(),
						"""
								class Test{
								\t
									String type(){ }
								\t
									int getVersion(){ }
								}
								"""
				),
				// With Everything
				Triple.of(
						"""
								package com.example;
								
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								@Example
								@Test(true)
								@Yep(type=String.class)
								public final class Test extends Derp implements Blah, Foo{
								\t
									// some comment
									/*
									 * some comment
									 */
									class Derp{
									}
								\t
									class Yep{
									}
								\t
									String name;
									int derp;
								\t
									String type(){}
									int getVersion(){}
								}
								""",
						EditableJavaClass.builder()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("com.example")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example.Something")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("org.example.SomethingElse")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("com.example.SomethingStatic")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("org.example.SomethingElseStatic")
										.build())
								.javadoc(EditableJavadoc.builder()
										.content("Something here")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.1")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Example")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("value", "true")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Yep")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("Test")
								.superClassName("Derp")
								.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Derp")
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.className("Yep")
										.build())
								.field(EditableJavaField.builder()
										.type("String").name("name")
										.build())
								.field(EditableJavaField.builder()
										.type("int").name("derp")
										.build())
								.method(EditableJavaMethod.builder()
										.returnType("String").name("type")
										.build())
								.method(EditableJavaMethod.builder()
										.returnType("int").name("getVersion")
										.build())
								.build(),
						"""
								package com.example;
								
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								@Example
								@Test(true)
								@Yep(type = String.class)
								public final class Test extends Derp implements Blah, Foo{
								\t
									// some comment
									/*
									 * some comment
									 */
								\t
									class Derp{
									\t
									}
								\t
									class Yep{
									\t
									}
								\t
									String name;
									int derp;
								\t
									String type(){ }
								\t
									int getVersion(){ }
								}
								"""
				),
				// With Everything and Inner Classes have Everything Too
				Triple.of(
						"""
								package com.example;
								
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								@Example
								@Test(true)
								@Yep(type=String.class)
								public final class Test extends Derp implements Blah, Foo{
								\t
									// some comment
									/*
									 * some comment
									 */
									/**
									 * Something here
									 *\s
									 * @author Logan Ferree (Tadukoo)
									 * @version Alpha v.0.1
									 */
									@Test
									@Yep(true)
									private static final class Derp extends Blah implements Blah, Foo{
										// some comment
										/*
										 * some comment
										 */
										@Test
										@Derp(type = String.class)
										protected static final String derp = "Blah";
										/** It's something alright */
										@Blah
										protected int something;
									\t
										/**
										 * @return the Version
										 */
										@Blah
										private int getVersion(){return version;}
									\t
										@Test
										@Derp(type=String.class)
										private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
									}
								\t
									/**
									 * Something here
									 *\s
									 * @author Logan Ferree (Tadukoo)
									 * @version Alpha v.0.1
									 */
									@Derp
									@Yep(something="no")
									protected static final class Yep extends Something implements Blah, Foo{
										// some comment
										/*
										 * some comment
										 */
										public static final String someName = "Test";
										/** This is something else */
										private int somethingElse;
									\t
										/**
										 * @return the Version
										 */
										@Blah
										private int getVersion(){return version;}
									\t
										@Test
										@Derp(type=String.class)
										private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
									}
								\t
									@Test
									@Derp(type = String.class)
									private static final String name = "Test";
									@Blah
									public int version;
								\t
									@Blah
									private int getVersion(){return version;}
								\t
									@Test
									@Derp(type=String.class)
									private static String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}
								}
								""",
						EditableJavaClass.builder()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("com.example")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example.Something")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("org.example.SomethingElse")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("com.example.SomethingStatic")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("org.example.SomethingElseStatic")
										.build())
								.javadoc(EditableJavadoc.builder()
										.content("Something here")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.1")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Example")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("value", "true")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Yep")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("Test")
								.superClassName("Derp")
								.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
								.singleLineComment(EditableJavaSingleLineComment.builder()
										.content("some comment")
										.build())
								.multiLineComment(EditableJavaMultiLineComment.builder()
										.content("some comment")
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.javadoc(EditableJavadoc.builder()
												.content("Something here")
												.author("Logan Ferree (Tadukoo)")
												.version("Alpha v.0.1")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Yep")
												.parameter("value", "true")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic().isFinal()
										.className("Derp").superClassName("Blah")
										.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
										.singleLineComment(EditableJavaSingleLineComment.builder()
												.content("some comment")
												.build())
										.multiLineComment(EditableJavaMultiLineComment.builder()
												.content("some comment")
												.build())
										.field(EditableJavaField.builder()
												.annotation(EditableJavaAnnotation.builder()
														.name("Test")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Derp")
														.parameter("type", "String.class")
														.build())
												.visibility(Visibility.PROTECTED)
												.isStatic().isFinal()
												.type("String").name("derp")
												.value("\"Blah\"")
												.build())
										.field(EditableJavaField.builder()
												.javadoc(EditableJavadoc.builder()
														.condensed()
														.content("It's something alright")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Blah")
														.build())
												.visibility(Visibility.PROTECTED)
												.type("int").name("something")
												.build())
										.method(EditableJavaMethod.builder()
												.javadoc(EditableJavadoc.builder()
														.returnVal("the Version")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Blah")
														.build())
												.visibility(Visibility.PRIVATE)
												.returnType("int").name("getVersion")
												.line("return version;")
												.build())
										.method(EditableJavaMethod.builder()
												.annotation(EditableJavaAnnotation.builder()
														.name("Test")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Derp")
														.parameter("type", "String.class")
														.build())
												.visibility(Visibility.PRIVATE)
												.isStatic()
												.returnType("String").name("test")
												.parameter("String type")
												.parameter("int derp")
												.throwType("Exception")
												.throwType("Throwable")
												.line("doSomething();")
												.line("doSomethingElse();")
												.build())
										.build())
								.innerClass(EditableJavaClass.builder()
										.innerClass()
										.javadoc(EditableJavadoc.builder()
												.content("Something here")
												.author("Logan Ferree (Tadukoo)")
												.version("Alpha v.0.1")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Yep")
												.parameter("something", "\"no\"")
												.build())
										.visibility(Visibility.PROTECTED)
										.isStatic().isFinal()
										.className("Yep").superClassName("Something")
										.implementsInterfaceName("Blah").implementsInterfaceName("Foo")
										.singleLineComment(EditableJavaSingleLineComment.builder()
												.content("some comment")
												.build())
										.multiLineComment(EditableJavaMultiLineComment.builder()
												.content("some comment")
												.build())
										.field(EditableJavaField.builder()
												.visibility(Visibility.PUBLIC)
												.isStatic().isFinal()
												.type("String").name("someName")
												.value("\"Test\"")
												.build())
										.field(EditableJavaField.builder()
												.javadoc(EditableJavadoc.builder()
														.condensed()
														.content("This is something else")
														.build())
												.visibility(Visibility.PRIVATE)
												.type("int").name("somethingElse")
												.build())
										.method(EditableJavaMethod.builder()
												.javadoc(EditableJavadoc.builder()
														.returnVal("the Version")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Blah")
														.build())
												.visibility(Visibility.PRIVATE)
												.returnType("int").name("getVersion")
												.line("return version;")
												.build())
										.method(EditableJavaMethod.builder()
												.annotation(EditableJavaAnnotation.builder()
														.name("Test")
														.build())
												.annotation(EditableJavaAnnotation.builder()
														.name("Derp")
														.parameter("type", "String.class")
														.build())
												.visibility(Visibility.PRIVATE)
												.isStatic()
												.returnType("String").name("test")
												.parameter("String type")
												.parameter("int derp")
												.throwType("Exception")
												.throwType("Throwable")
												.line("doSomething();")
												.line("doSomethingElse();")
												.build())
										.build())
								.field(EditableJavaField.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.parameter("type", "String.class")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic().isFinal()
										.type("String").name("name")
										.value("\"Test\"")
										.build())
								.field(EditableJavaField.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Blah")
												.build())
										.visibility(Visibility.PUBLIC)
										.type("int").name("version")
										.build())
								.method(EditableJavaMethod.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Blah")
												.build())
										.visibility(Visibility.PRIVATE)
										.returnType("int").name("getVersion")
										.line("return version;")
										.build())
								.method(EditableJavaMethod.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.parameter("type", "String.class")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic()
										.returnType("String").name("test")
										.parameter("String type")
										.parameter("int derp")
										.throwType("Exception")
										.throwType("Throwable")
										.line("doSomething();")
										.line("doSomethingElse();")
										.build())
								.build(),
						"""
								package com.example;
								
								import com.example.Something;
								
								import org.example.SomethingElse;
								
								import static com.example.SomethingStatic;
								
								import static org.example.SomethingElseStatic;
								
								/**
								 * Something here
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.1
								 */
								@Example
								@Test(true)
								@Yep(type = String.class)
								public final class Test extends Derp implements Blah, Foo{
								\t
									// some comment
									/*
									 * some comment
									 */
								\t
									/**
									 * Something here
									 *\s
									 * @author Logan Ferree (Tadukoo)
									 * @version Alpha v.0.1
									 */
									@Test
									@Yep(true)
									private static final class Derp extends Blah implements Blah, Foo{
									\t
										// some comment
										/*
										 * some comment
										 */
									\t
										@Test
										@Derp(type = String.class)
										protected static final String derp = "Blah";
										/** It's something alright */
										@Blah
										protected int something;
									\t
										/**
										 * @return the Version
										 */
										@Blah
										private int getVersion(){
											return version;
										}
									\t
										@Test
										@Derp(type = String.class)
										private static String test(String type, int derp) throws Exception, Throwable{
											doSomething();
											doSomethingElse();
										}
									}
								\t
									/**
									 * Something here
									 *\s
									 * @author Logan Ferree (Tadukoo)
									 * @version Alpha v.0.1
									 */
									@Derp
									@Yep(something = "no")
									protected static final class Yep extends Something implements Blah, Foo{
									\t
										// some comment
										/*
										 * some comment
										 */
									\t
										public static final String someName = "Test";
										/** This is something else */
										private int somethingElse;
									\t
										/**
										 * @return the Version
										 */
										@Blah
										private int getVersion(){
											return version;
										}
									\t
										@Test
										@Derp(type = String.class)
										private static String test(String type, int derp) throws Exception, Throwable{
											doSomething();
											doSomethingElse();
										}
									}
								\t
									@Test
									@Derp(type = String.class)
									private static final String name = "Test";
									@Blah
									public int version;
								\t
									@Blah
									private int getVersion(){
										return version;
									}
								\t
									@Test
									@Derp(type = String.class)
									private static String test(String type, int derp) throws Exception, Throwable{
										doSomething();
										doSomethingElse();
									}
								}
								"""
				),
				/*
				 * Whitespace Tests
				 */
				// Leading Whitespace
				Triple.of(
						"""
								\t  \t
								\t class Test{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Whitespace after Class
				Triple.of(
						"""
								class \t \t
								  \t \tTest{
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Whitespace after Class Name
				Triple.of(
						"""
								class Test  \t
								\t  {
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Whitespace after Open Brace
				Triple.of(
						"""
								class Test{\t   \t
								
								\t
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Whitespace after Closing Brace
				Triple.of(
						"""
								class Test  \t
								\t  {
								}\t    \t
								\t
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Insane Whitespace Simple Class
				Triple.of(
						"""
								\t  \t
								\t class \t \t
								  \t \tTest{\t   \t
										\s
								\t
								}\t    \t
								\t
								""",
						EditableJavaClass.builder()
								.className("Test")
								.build(),
						"""
								class Test{
								\t
								}
								"""
				),
				// Leading Whitespace Package Declaration
				Triple.of(
						"""
								\t     \t
								\t  package com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Package Declaration before Package Name
				Triple.of(
						"""
								package \t  \t
								\t  com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Package Declaration within Package Name
				Triple.of(
						"""
								package com     \t
								
								\t    .\t \t
								\t     example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Package Declaration after Package Name
				Triple.of(
						"""
								package com.example
								\t   \t;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Trailing Whitespace Package Declaration
				Triple.of(
						"""
								package com.example;\t  \t
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Insane Whitespace Package Declaration
				Triple.of(
						"""
								\t     \t
								\t  package \t  \t
								\t  com     \t
								
								\t    .\t \t
								\t     example
								\t   \t;\t  \t
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.example")
								.className("Test")
								.build(),
						"""
								package com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Leading Whitespace Import Statement
				Triple.of(
						"""
								\t   \t
								\t  import com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", false)
								.className("Test")
								.build(),
						"""
								import com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Import Statement before Import Name
				Triple.of(
						"""
								import \t\s
								\t     com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", false)
								.className("Test")
								.build(),
						"""
								import com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Import Statement within Import Name
				Triple.of(
						"""
								import com     \t
								
								\t    .\t       \s
								\t      example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", false)
								.className("Test")
								.build(),
						"""
								import com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Import Statement after Import Name
				Triple.of(
						"""
								import com.example     \t
								
								\t      ;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", false)
								.className("Test")
								.build(),
						"""
								import com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Import Statement before Static
				Triple.of(
						"""
								import    \t
								 \t     static com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", true)
								.className("Test")
								.build(),
						"""
								import static com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Whitespace Import Statement after Static
				Triple.of(
						"""
								import static   \t
								 \t     com.example;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", true)
								.className("Test")
								.build(),
						"""
								import static com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Insane Whitespace Import Statement
				Triple.of(
						"""
								\t   \t
								\t  import    \t
								 \t     static   \t
								 \t     com     \t
									\s
								\t    .\t       \s
								\t      example     \t
								
								\t      ;
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.importName("com.example", true)
								.className("Test")
								.build(),
						"""
								import static com.example;
								
								class Test{
								\t
								}
								"""
				),
				// Insane Whitespace Annotation
				Triple.of(
						"""
								\t  \t @ \t  \tTest\t  (\t \ttype \t =\s
								  \t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t
								
								class Test{
								}
								""",
						EditableJavaClass.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.className("Test")
								.build(),
						"""
								@Test(type = String.class, defaultValue = "")
								class Test{
								\t
								}
								"""
				),
				// Comma at Start of Interface Name
				Triple.of(
						"""
								class Test implements Test ,Derp{
								}""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Test")
								.implementsInterfaceName("Derp")
								.build(),
						"""
								class Test implements Test, Derp{
								\t
								}
								"""
				),
				// Comma as Separate Token
				Triple.of(
						"""
								class Test implements Test , Derp{
								}""",
						EditableJavaClass.builder()
								.className("Test")
								.implementsInterfaceName("Test")
								.implementsInterfaceName("Derp")
								.build(),
						"""
								class Test implements Test, Derp{
								\t
								}
								"""
				),
				// Insane Field Whitespace
				Triple.of(
						"""
								class Test{
									\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
								\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
								  \t
								 \t    \t@       Derp   \t
								   \t public\t     \t
								   \t static\t     \t
								   \t final\t     \t  \t
								 \tString \t  \t
								 \t  type    \t   \t
								  \t =    \t   \t
								  \t "" \t  \t
								\t;     \t
								 \t  \s
								}
								""",
						EditableJavaClass.builder()
								.className("Test")
								.field(EditableJavaField.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.parameter("type", "String.class")
												.parameter("defaultValue", "\"\"")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isStatic().isFinal()
										.type("String").name("type")
										.value("\"\"")
										.build())
								.build(),
						"""
								class Test{
								\t
									@Test(type = String.class, defaultValue = "")
									@Derp
									public static final String type = "";
								}
								"""
				),
				// Insane Method Whitespace
				Triple.of(
						"""
								class Test{
									\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
									\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
									\t    \t@       Derp   \t
										\t   \t
										\t    private     \t   \t
									\t    static     \t   \t
									\t    String     \t   \t
									\t    test     \t   \t
									\t    (     \t   \t
									\t    String     \t   \t
									\t    type     \t   \t
									\t    ,     \t   \t
									\t    int     \t   \t
									\t    derp     \t   \t
									\t    )     \t   \t
									\t    throws     \t   \t
									\t    Exception     \t   \t
									\t    ,     \t   \t
									\t    Throwable     \t   \t
									\t    {     \t   \t
									\t    doSomething()     \t   \t
									\t    ;     \t   \t
									\t    doSomethingElse()     \t   \t
									\t    ;     \t   \t
									\t    }     \t   \t
									\t    \s
								}""",
						EditableJavaClass.builder()
								.className("Test")
								.method(EditableJavaMethod.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.parameter("type", "String.class")
												.parameter("defaultValue", "\"\"")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic()
										.returnType("String").name("test")
										.parameter("String type")
										.parameter("int derp")
										.throwType("Exception")
										.throwType("Throwable")
										.line("doSomething();")
										.line("doSomethingElse();")
										.build())
								.build(),
						"""
								class Test{
								\t
									@Test(type = String.class, defaultValue = "")
									@Derp
									private static String test(String type, int derp) throws Exception, Throwable{
										doSomething();
										doSomethingElse();
									}
								}
								"""
				),
				// All Insane Whitespace
				Triple.of(
						"""
								\t     \t
								\t  package \t  \t
								\t  com     \t
								
								\t    .\t \t
								\t     example
								\t   \t;\t  \t
								
								\t   \t
								\t  import    \t
								 \t        \t
								 \t     com     \t
									\s
								\t    .\t       \s
								\t      example     \t
								   .Something   \t
								\t      ;
								\t import     com.example.SomethingElse      \t;\t     \t    \t
								
								\t   \t
								\t  import    \t
								 \t     static   \t
								 \t     com     \t
									\s
								\t    .\t       \s
								\t      example     \t
												.SomethingStatic     \t
								\t      ;
								\t  \t import \t      \t static\t  com.example.SomethingElseStatic\t \t  ;\t     \t
								
								\t  \t @ \t  \tTest\t  (\t \ttype \t =\s
								  \t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t
								
								
								public\t  \t
								\t final\t  \t
								\t    class \t \t
								  \t \tTest{\t   \t
										\s
								\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
								\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
								  \t
								 \t    \t@       Derp   \t
								   \t public\t     \t
								   \t static\t     \t
								   \t final\t     \t  \t
								 \tString \t  \t
								 \t  type    \t   \t
								  \t =    \t   \t
								  \t "" \t  \t
								\t;     \t
								 \t  \s
								 \t  \t @ \t  \tTest\t  (\t \ttype \t = \t
									\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
									\t    \t@       Derp   \t
										\t   \t
										\t    private     \t   \t
									\t    static     \t   \t
									\t    String     \t   \t
									\t    test     \t   \t
									\t    (     \t   \t
									\t    String     \t   \t
									\t    type     \t   \t
									\t    ,     \t   \t
									\t    int     \t   \t
									\t    derp     \t   \t
									\t    )     \t   \t
									\t    throws     \t   \t
									\t    Exception     \t   \t
									\t    ,     \t   \t
									\t    Throwable     \t   \t
									\t    {     \t   \t
									\t    doSomething()     \t   \t
									\t    ;     \t   \t
									\t    doSomethingElse()     \t   \t
									\t    ;     \t   \t
									\t    }     \t   \t
									\t    \s
								\t
								}\t    \t
								\t
								""",
						EditableJavaClass.builder()
								.packageDeclaration(EditableJavaPackageDeclaration.builder()
										.packageName("com.example")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example.Something")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.importName("com.example.SomethingElse")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("com.example.SomethingStatic")
										.build())
								.importStatement(EditableJavaImportStatement.builder()
										.isStatic()
										.importName("com.example.SomethingElseStatic")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.visibility(Visibility.PUBLIC)
								.isFinal()
								.className("Test")
								.field(EditableJavaField.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.parameter("type", "String.class")
												.parameter("defaultValue", "\"\"")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PUBLIC)
										.isStatic().isFinal()
										.type("String").name("type")
										.value("\"\"")
										.build())
								.method(EditableJavaMethod.builder()
										.annotation(EditableJavaAnnotation.builder()
												.name("Test")
												.parameter("type", "String.class")
												.parameter("defaultValue", "\"\"")
												.build())
										.annotation(EditableJavaAnnotation.builder()
												.name("Derp")
												.build())
										.visibility(Visibility.PRIVATE)
										.isStatic()
										.returnType("String").name("test")
										.parameter("String type")
										.parameter("int derp")
										.throwType("Exception")
										.throwType("Throwable")
										.line("doSomething();")
										.line("doSomethingElse();")
										.build())
								.build(),
						"""
								package com.example;
								
								import com.example.Something;
								import com.example.SomethingElse;
								
								import static com.example.SomethingElseStatic;
								import static com.example.SomethingStatic;
								
								@Test(type = String.class, defaultValue = "")
								public final class Test{
								\t
									@Test(type = String.class, defaultValue = "")
									@Derp
									public static final String type = "";
								\t
									@Test(type = String.class, defaultValue = "")
									@Derp
									private static String test(String type, int derp) throws Exception, Throwable{
										doSomething();
										doSomethingElse();
									}
								}
								"""
				),
				/*
				 * Weird Issues
				 */
				// Form Field Issue
				/*
				 * This exists because when running the parser code in @UltimatePower, the Javadoc was considered condensed.
				 * It has to do with Windows' /r/n usage for newlines, which the \r was treated as a character before the newline,
				 * meaning the Javadoc content started with it
				 */
				Triple.of(
						"""
								package com.github.tadukoo.view.form.field.string;
								
								import com.github.tadukoo.view.components.TadukooLabel;
								import com.github.tadukoo.view.form.field.FormField;
								import com.github.tadukoo.view.form.field.annotation.FormFieldBuilder;
								
								import javax.swing.JComponent;
								import javax.swing.JLabel;
								
								/**\r
								 * Represents a {@link FormField} for a Label
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.4
								 */
								@FormFieldBuilder(type = String.class, defaultDefaultValue = "\\"\\"")
								public class BetterLabelFormField extends FormField<String>{
								\t
									/** {@inheritDoc} */
									@Override
									public JComponent getJustComponent() throws Throwable{
										return TadukooLabel.builder()
												.text(getDefaultValue())
												.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())
												.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())
												.shapeInfo(getLabelShape()).border(getLabelBorder())
												.fontResourceLoader(getFontResourceLoader())
												.build();
									}
								\t
									/** {@inheritDoc} */
									@Override
									public String getValueFromJustComponent(JComponent component){
										if(component instanceof JLabel label){
											return label.getText();
										}
										return null;
									}
								}
								""",
						EditableJavaClass.builder()
								.packageName("com.github.tadukoo.view.form.field.string")
								.importNames(ListUtil.createList(
										"com.github.tadukoo.view.components.TadukooLabel",
										"com.github.tadukoo.view.form.field.FormField",
										"com.github.tadukoo.view.form.field.annotation.FormFieldBuilder",
										"javax.swing.JComponent",
										"javax.swing.JLabel"
								), false)
								.javadoc(EditableJavadoc.builder()
										.content("Represents a {@link FormField} for a Label")
										.author("Logan Ferree (Tadukoo)")
										.version("Alpha v.0.4")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("FormFieldBuilder")
										.parameter("type", "String.class")
										.parameter("defaultDefaultValue", "\"\\\"\\\"\"")
										.build())
								.visibility(Visibility.PUBLIC).className("BetterLabelFormField").superClassName("FormField<String>")
								.method(EditableJavaMethod.builder()
										.javadoc(EditableJavadoc.builder().condensed().content("{@inheritDoc}").build())
										.annotation(EditableJavaAnnotation.builder().name("Override").build())
										.visibility(Visibility.PUBLIC).returnType("JComponent").name("getJustComponent")
										.throwType("Throwable")
										.line("return TadukooLabel.builder()")
										.line("\t\t.text(getDefaultValue())")
										.line("\t\t.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())")
										.line("\t\t.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())")
										.line("\t\t.shapeInfo(getLabelShape()).border(getLabelBorder())")
										.line("\t\t.fontResourceLoader(getFontResourceLoader())")
										.line("\t\t.build();")
										.build())
								.method(EditableJavaMethod.builder()
										.javadoc(EditableJavadoc.builder().condensed().content("{@inheritDoc}").build())
										.annotation(EditableJavaAnnotation.builder().name("Override").build())
										.visibility(Visibility.PUBLIC).returnType("String").name("getValueFromJustComponent")
										.parameter("JComponent component")
										.line("if(component instanceof JLabel label){")
										.line("\treturn label.getText();")
										.line("}")
										.line("return null;")
										.build())
								.build(),
						"""
								package com.github.tadukoo.view.form.field.string;
								
								import com.github.tadukoo.view.components.TadukooLabel;
								import com.github.tadukoo.view.form.field.FormField;
								import com.github.tadukoo.view.form.field.annotation.FormFieldBuilder;
								
								import javax.swing.JComponent;
								import javax.swing.JLabel;
								
								/**
								 * Represents a {@link FormField} for a Label
								 *\s
								 * @author Logan Ferree (Tadukoo)
								 * @version Alpha v.0.4
								 */
								@FormFieldBuilder(type = String.class, defaultDefaultValue = "\\"\\"")
								public class BetterLabelFormField extends FormField<String>{
								\t
									/** {@inheritDoc} */
									@Override
									public JComponent getJustComponent() throws Throwable{
										return TadukooLabel.builder()
												.text(getDefaultValue())
												.foregroundPaint(getLabelForegroundPaint()).backgroundPaint(getLabelBackgroundPaint())
												.font(getLabelFontFamily(), getLabelFontStyle(), getLabelFontSize())
												.shapeInfo(getLabelShape()).border(getLabelBorder())
												.fontResourceLoader(getFontResourceLoader())
												.build();
									}
								\t
									/** {@inheritDoc} */
									@Override
									public String getValueFromJustComponent(JComponent component){
										if(component instanceof JLabel label){
											return label.getText();
										}
										return null;
									}
								}
								"""
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
			ThrowingFunction<String, JavaClass, JavaParsingException> parseMethod, String textToParse,
			JavaClass expectedClass, String expectedText) throws JavaParsingException{
		JavaClass clazz = parseMethod.apply(textToParse);
		assertNotNull(clazz);
		assertEquals(expectedClass, clazz);
		assertEquals(expectedText, clazz.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// No Class Name
				Pair.of(
						"""
								class""",
						"Failed to find class name!"
				),
				// Extends After Block Open
				Pair.of(
						"""
								class Test{ extends
								}""",
						"found 'extends' after hitting the block open token!"
				),
				// Super Class Name Fail
				Pair.of(
						"""
								class Test extends""",
						"Failed to find super class name after 'extends'!"
				),
				// Implements After Block Open
				Pair.of(
						"""
								class Test{ implements
								}""",
						"found 'implements' after hitting the block open token!"
				),
				// Implements Interface Name Fail
				Pair.of(
						"""
								class Test implements""",
						"Failed to find implements interface name after 'implements' or ','!"
				),
				// Comma Before Implements Interface Name
				Pair.of(
						"""
								class Test implements , {
								}""",
						"Encountered ',' before any interface names!"
				),
				// Comma as Separate Token Interface Names Then No Following Interface Name
				Pair.of(
						"""
								class Test implements Test ,""",
						"Failed to find implements interface name after 'implements' or ','!"
				),
				// Block Open Twice
				Pair.of(
						"""
								class Test{{
								}""",
						"We hit the block open token twice for the same class!"
				),
				// Unknown Inner Type
				Pair.of(
						"""
								class Test{
									something yep it is
								}""",
						"Unable to determine token: 'something'"
				),
				// Hanging Inner Javadoc
				Pair.of(
						"""
								class Test{
									/** {@inheritDoc} */
								}""",
						"Found Javadoc at end of class with nothing to attach it to!"
				),
				// Hanging Inner Annotation
				Pair.of(
						"""
								class Test{
									@Test
								}""",
						"Found annotations at end of class with nothing to attach them to!"
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaClass, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
					error), e.getMessage());
		}
	}
	
	/*
	 * Specific Error Cases
	 */
	
	@Test
	public void testMultiplePackageDeclarations(){
		try{
			JavaClassParser.parseClass("""
					package some.package;
					package something.else;
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Only one package declaration allowed on a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testPackageDeclarationAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					package some.package;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered package declaration after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testImportStatementAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					import some.name;""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered import statement after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleJavadocs(){
		try{
			JavaClassParser.parseClass("""
					/** {@inheritDoc} */
					/** something */
					class Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Only one Javadoc allowed on a class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered Javadoc after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterClass(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered annotation after class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleClasses(){
		try{
			JavaClassParser.parseClass("""
					class Test{
					}
					class Derp{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Encountered multiple classes!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testParsedNotAClass(){
		try{
			JavaClassParser.parseClass("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"Failed to parse an actual class!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testFirstTokenNotClass(){
		try{
			JavaClassParser.parseClass("""
					Test{
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.CLASS,
							"The first token of a class must be 'class'"),
					e.getMessage());
		}
	}
	
	@Test
	public void testStaticCodeBlockWithPrivate(){
		try{
			JavaClassParser.parseClass("""
					class Test{
						private{
							doSomething();
						}
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.STATIC_CODE_BLOCK,
							"Static Code Block can only have 'static' as a modifier"),
					e.getMessage()
			);
		}
	}
	
	@Test
	public void testStaticCodeBlockWithMultipleModifiers(){
		try{
			JavaClassParser.parseClass("""
					class Test{
						private static{
							doSomething();
						}
					}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.STATIC_CODE_BLOCK,
							"Static Code Block can only have 'static' as a modifier"),
					e.getMessage()
			);
		}
	}
}
