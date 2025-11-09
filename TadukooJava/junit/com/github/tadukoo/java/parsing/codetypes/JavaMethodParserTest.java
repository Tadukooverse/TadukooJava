package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaMethodParserTest extends BaseJavaParserTest{
	
	private static final List<ThrowingFunction<String, JavaMethod, JavaParsingException>> parseMethods =
			ListUtil.createList(
					BaseJavaParserTest::runFullParserForMethod,
					JavaMethodParser::parseMethod
			);
	
	public static Stream<Arguments> getParsingData(){
		List<Triple<String, JavaMethod, String>> parsingData = ListUtil.<Triple<String, JavaMethod, String>>createList(
				// Simple
				Triple.of(
						"Test(){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"Test(){ }"
				),
				// With Name
				Triple.of(
						"String test(){}",
						EditableJavaMethod.builder()
								.returnType("String").name("test")
								.build(),
						"String test(){ }"
				),
				// With Private
				Triple.of(
						"private Test(){}",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.returnType("Test")
								.build(),
						"private Test(){ }"
				),
				// With Protected
				Triple.of(
						"protected Test(){}",
						EditableJavaMethod.builder()
								.visibility(Visibility.PROTECTED)
								.returnType("Test")
								.build(),
						"protected Test(){ }"
				),
				// With Public
				Triple.of(
						"public Test(){}",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.returnType("Test")
								.build(),
						"public Test(){ }"
				),
				// With Abstract
				Triple.of(
						"abstract Test();",
						EditableJavaMethod.builder()
								.isAbstract()
								.returnType("Test")
								.build(),
						"abstract Test();"
				),
				// With Static
				Triple.of(
						"static Test(){}",
						EditableJavaMethod.builder()
								.isStatic()
								.returnType("Test")
								.build(),
						"static Test(){ }"
				),
				// With Final
				Triple.of(
						"final Test(){}",
						EditableJavaMethod.builder()
								.isFinal()
								.returnType("Test")
								.build(),
						"final Test(){ }"
				),
				// Reversed Modifiers
				Triple.of(
						"final static protected Test(){}",
						EditableJavaMethod.builder()
								.visibility(Visibility.PROTECTED)
								.isStatic().isFinal()
								.returnType("Test")
								.build(),
						"protected static final Test(){ }"
				),
				// With Type Parameter
				Triple.of(
						"<T> Test(){}",
						EditableJavaMethod.builder()
								.addTypeParameters("T")
								.returnType("Test")
								.build(),
						"<T> Test(){ }"
				),
				// With 2 Type Parameters
				Triple.of(
						"<T, U> Test(){}",
						EditableJavaMethod.builder()
								.addTypeParameters("T, U")
								.returnType("Test")
								.build(),
						"<T, U> Test(){ }"
				),
				// With 3 Type Parameters
				Triple.of(
						"<T, U, V> Test(){}",
						EditableJavaMethod.builder()
								.addTypeParameters("T, U, V")
								.returnType("Test")
								.build(),
						"<T, U, V> Test(){ }"
				),
				// With Parameter
				Triple.of(
						"Test(String type){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.build(),
						"Test(String type){ }"
				),
				// With Parameter with Type
				Triple.of(
						"Test(List<String> types){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("List<String> types")
								.build(),
						"Test(List<String> types){ }"
				),
				// With Parameter with Multiple Types
				Triple.of(
						"Test(Map<String, Object> map){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("Map<String, Object> map")
								.build(),
						"Test(Map<String, Object> map){ }"
				),
				// With Parameter with Vararg
				Triple.of(
						"Test(String ... types){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String ... types")
								.build(),
						"Test(String ... types){ }"
				),
				// With Parameter with Everything
				Triple.of(
						"Test(Map<String, Object> ... maps){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("Map<String, Object> ... maps")
								.build(),
						"Test(Map<String, Object> ... maps){ }"
				),
				// With Multiple Parameters
				Triple.of(
						"Test(String type, int derp){}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.parameter("int derp")
								.build(),
						"Test(String type, int derp){ }"
				),
				// With Throws
				Triple.of(
						"Test() throws Exception{}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.build(),
						"Test() throws Exception{ }"
				),
				// With Multiple Throws
				Triple.of(
						"Test() throws Exception, Throwable{}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.throwType("Throwable")
								.build(),
						"Test() throws Exception, Throwable{ }"
				),
				// With Content
				Triple.of(
						"Test(){doSomething();}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.line("doSomething();")
								.build(),
						"""
								Test(){
									doSomething();
								}"""
				),
				// With Multiple Lines Content
				Triple.of(
						"Test(){doSomething();doSomethingElse();}",
						EditableJavaMethod.builder()
								.returnType("Test")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								Test(){
									doSomething();
									doSomethingElse();
								}"""
				),
				// With Everything
				Triple.of(
						"private static final <T, U, V> String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.addTypeParameters("T, U, V")
								.returnType("String").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								private static final <T, U, V> String test(String type, int derp) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
				),
				// With Everything and Complex Parameters
				Triple.of(
						"""
				private static final String test(Map<String, Object> map, int ... derps) throws Exception, Throwable{doSomething();doSomethingElse();}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.returnType("String").name("test")
								.parameter("Map<String, Object> map")
								.parameter("int ... derps")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								private static final String test(Map<String, Object> map, int ... derps) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
				),
				// With Annotation
				Triple.of(
						"""
								@Test
								Test(){}""",
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.returnType("Test")
								.build(),
						"""
								@Test
								Test(){ }"""
				),
				// With Multiple Annotations
				Triple.of(
						"""
								@Test
								@Derp(type=String.class)
								Test(){}""",
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.returnType("Test")
								.build(),
						"""
								@Test
								@Derp(type = String.class)
								Test(){ }"""
				),
				// With Javadoc
				Triple.of(
						"""
								/** {@inheritDoc} */
								Test(){}""",
						EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("{@inheritDoc}")
										.build())
								.returnType("Test")
								.build(),
						"""
								/** {@inheritDoc} */
								Test(){ }"""
				),
				// With Everything and Annotations
				Triple.of(
						"""
								/** {@inheritDoc} */
								@Test
								@Derp(type=String.class)
								private static final String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}""",
						EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("{@inheritDoc}")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.parameter("type", "String.class")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.returnType("String").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								/** {@inheritDoc} */
								@Test
								@Derp(type = String.class)
								private static final String test(String type, int derp) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
				),
				// With Name and type has type parameters
				Triple.of(
						"List<String> test(){}",
						EditableJavaMethod.builder()
								.returnType("List<String>").name("test")
								.build(),
						"List<String> test(){ }"
				),
				// With Name and type has multiple type parameters
				Triple.of(
						"Map<String, Object> test(){}",
						EditableJavaMethod.builder()
								.returnType("Map<String, Object>").name("test")
								.build(),
						"Map<String, Object> test(){ }"
				),
				// With Name and type is complicated
				Triple.of(
						"List<Map<String, Object>> test(){}",
						EditableJavaMethod.builder()
								.returnType("List<Map<String, Object>>").name("test")
								.build(),
						"List<Map<String, Object>> test(){ }"
				),
				// With Name and type is complicated 2
				Triple.of(
						"Map<Character, Map<Character, ?>> test(){}",
						EditableJavaMethod.builder()
								.returnType("Map<Character, Map<Character, ?>>").name("test")
								.build(),
						"Map<Character, Map<Character, ?>> test(){ }"
				),
				// With Name and type is complicated 3
				Triple.of(
						"Map<? extends List<String>, Object> test(){}",
						EditableJavaMethod.builder()
								.returnType("Map<? extends List<String>, Object>").name("test")
								.build(),
						"Map<? extends List<String>, Object> test(){ }"
				),
				/*
				 * Edge Cases
				 */
				// Line with No Semicolon
				Triple.of(
						"""
								Test(){
									something.builder()
											.build();
								}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.line("something.builder()")
								.line("\t\t.build();")
								.build(),
						"""
								Test(){
									something.builder()
											.build();
								}"""
				),
				// Line Blank Space Between Semicolons
				Triple.of(
						"""
								Test(){
									for(int i = 0;;){
										doSomething();
									}
								}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.line("for(int i = 0;;){")
								.line("\tdoSomething();")
								.line("}")
								.build(),
						"""
								Test(){
									for(int i = 0;;){
										doSomething();
									}
								}"""
				),
				// With Blocks Inside
				Triple.of(
						"""
								public static String toHex(byte[] bytes){
									StringBuilder hex = new StringBuilder();
									for(byte bite: bytes){
										hex.append(toHex(bite));
									}
									return hex.toString();
								}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("String").name("toHex")
								.parameter("byte[] bytes")
								.line("StringBuilder hex = new StringBuilder();")
								.line("for(byte bite: bytes){")
								.line("\thex.append(toHex(bite));")
								.line("}")
								.line("return hex.toString();")
								.build(),
						"""
								public static String toHex(byte[] bytes){
									StringBuilder hex = new StringBuilder();
									for(byte bite: bytes){
										hex.append(toHex(bite));
									}
									return hex.toString();
								}"""
				),
				// With More Blocks Inside
				Triple.of(
						"""
								public static byte[] fromHex(String hex){
									int size = hex.length();
								\t
									// Check that the size is even
									if(size % 2 != 0){
										throw new IllegalArgumentException("hex string must be an even length: " + hex);
									}
								\t
									// Create byte array to store the bytes in
									byte[] bites = new byte[size/2];
								\t
									// Iterate over the string, 2 characters at a time
									for(int i = 0; i < size; i+=2){
										int highNibble = hexToInt(hex.charAt(i));
										int lowNibble = hexToInt(hex.charAt(i+1));
										// If either nibble came out -1, we have an illegal hex character
										if(highNibble == -1 || lowNibble == -1){
											throw new IllegalArgumentException("hex string contains an illegal hex character: " + hex);
										}
									\t
										bites[i/2] = (byte) (highNibble*16 + lowNibble);
									}
								\t
									return bites;
								}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("byte[]").name("fromHex")
								.parameter("String hex")
								.line("int size = hex.length();")
								.line("")
								.line("// Check that the size is even")
								.line("if(size % 2 != 0){")
								.line("\tthrow new IllegalArgumentException(\"hex string must be an even length: \" + hex);")
								.line("}")
								.line("")
								.line("// Create byte array to store the bytes in")
								.line("byte[] bites = new byte[size/2];")
								.line("")
								.line("// Iterate over the string, 2 characters at a time")
								.line("for(int i = 0; i < size; i+=2){")
								.line("\tint highNibble = hexToInt(hex.charAt(i));")
								.line("\tint lowNibble = hexToInt(hex.charAt(i+1));")
								.line("\t// If either nibble came out -1, we have an illegal hex character")
								.line("\tif(highNibble == -1 || lowNibble == -1){")
								.line("\t\tthrow new IllegalArgumentException(\"hex string contains an illegal hex character: \" + hex);")
								.line("\t}")
								.line("\t")
								.line("\tbites[i/2] = (byte) (highNibble*16 + lowNibble);")
								.line("}")
								.line("")
								.line("return bites;")
								.build(),
						"""
								public static byte[] fromHex(String hex){
									int size = hex.length();
								\t
									// Check that the size is even
									if(size % 2 != 0){
										throw new IllegalArgumentException("hex string must be an even length: " + hex);
									}
								\t
									// Create byte array to store the bytes in
									byte[] bites = new byte[size/2];
								\t
									// Iterate over the string, 2 characters at a time
									for(int i = 0; i < size; i+=2){
										int highNibble = hexToInt(hex.charAt(i));
										int lowNibble = hexToInt(hex.charAt(i+1));
										// If either nibble came out -1, we have an illegal hex character
										if(highNibble == -1 || lowNibble == -1){
											throw new IllegalArgumentException("hex string contains an illegal hex character: " + hex);
										}
								\t\t
										bites[i/2] = (byte) (highNibble*16 + lowNibble);
									}
								\t
									return bites;
								}"""
				),
				// With If Else-If Blocks
				Triple.of(
						"""
								public static int hexToInt(char hexChar){
									if('0' <= hexChar && hexChar <= '9'){
										return hexChar - '0';
									}else if('A' <= hexChar && hexChar <= 'F'){
										return hexChar - 'A' + 10;
									}else if('a' <= hexChar && hexChar <= 'f'){
										return hexChar - 'a' + 10;
									}else{
										return -1;
									}
								}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("int").name("hexToInt")
								.parameter("char hexChar")
								.line("if('0' <= hexChar && hexChar <= '9'){")
								.line("\treturn hexChar - '0';")
								.line("}else if('A' <= hexChar && hexChar <= 'F'){")
								.line("\treturn hexChar - 'A' + 10;")
								.line("}else if('a' <= hexChar && hexChar <= 'f'){")
								.line("\treturn hexChar - 'a' + 10;")
								.line("}else{")
								.line("\treturn -1;")
								.line("}")
								.build(),
						"""
								public static int hexToInt(char hexChar){
									if('0' <= hexChar && hexChar <= '9'){
										return hexChar - '0';
									}else if('A' <= hexChar && hexChar <= 'F'){
										return hexChar - 'A' + 10;
									}else if('a' <= hexChar && hexChar <= 'f'){
										return hexChar - 'a' + 10;
									}else{
										return -1;
									}
								}"""
				),
				// With Throws and Inner Block Open Token
				Triple.of(
						"""
								public static List<File> listAllFiles(Path directoryPath) throws IOException{
									try(Stream<Path> pathStream = Files.walk(directoryPath)){
										return pathStream
												.filter(Files::isRegularFile)
												.map(Path::toFile)
												.collect(Collectors.toList());
									}
								}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("List<File>").name("listAllFiles")
								.parameter("Path directoryPath")
								.throwType("IOException")
								.line("try(Stream<Path> pathStream = Files.walk(directoryPath)){")
								.line("\treturn pathStream")
								.line("\t\t\t.filter(Files::isRegularFile)")
								.line("\t\t\t.map(Path::toFile)")
								.line("\t\t\t.collect(Collectors.toList());")
								.line("}")
								.build(),
						"""
								public static List<File> listAllFiles(Path directoryPath) throws IOException{
									try(Stream<Path> pathStream = Files.walk(directoryPath)){
										return pathStream
												.filter(Files::isRegularFile)
												.map(Path::toFile)
												.collect(Collectors.toList());
									}
								}"""
				),
				// No Whitespace Simple Method
				Triple.of(
						"""
								Test(){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// No Whitespace Everything Method
				Triple.of(
						"""
								private static String test(String type, int derp)throws Exception,Throwable{doSomething();doSomethingElse();}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.isStatic()
								.returnType("String").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								private static String test(String type, int derp) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
				),
				// Leading Whitespace
				Triple.of(
						"""
								\t   \s
								  \t    Test(){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Whitespace after Return Type
				Triple.of(
						"""
								Test    \t   \s
								  \t   (){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Whitespace in Empty Parameters
				Triple.of(
						"""
								Test (    \t
								    \t    ){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Whitespace between Parameters and Open
				Triple.of(
						"""
								Test()      \t
								  \t    \t{}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Whitespace in Method
				Triple.of(
						"""
								Test(){       \t
								   \t    \t
								    \t }""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Trailing Whitespace
				Triple.of(
						"""
								Test(){}     \t
								   \t    \t
								   \t""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.build(),
						"""
								Test(){ }"""
				),
				// Whitespace before Private
				Triple.of(
						"""
								\t     \t \s
								  \tprivate Test(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.returnType("Test")
								.build(),
						"""
								private Test(){ }"""
				),
				// Whitespace before Protected
				Triple.of(
						"""
								\t     \t \s
								  \tprotected Test(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PROTECTED)
								.returnType("Test")
								.build(),
						"""
								protected Test(){ }"""
				),
				// Whitespace before Public
				Triple.of(
						"""
								\t     \t \s
								  \tpublic Test(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.returnType("Test")
								.build(),
						"""
								public Test(){ }"""
				),
				// Whitespace after Private
				Triple.of(
						"""
								private\t     \t \s
								  \tTest(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.returnType("Test")
								.build(),
						"""
								private Test(){ }"""
				),
				// Whitespace after Protected
				Triple.of(
						"""
								protected\t     \t \s
								  \tTest(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PROTECTED)
								.returnType("Test")
								.build(),
						"""
								protected Test(){ }"""
				),
				// Whitespace after Public
				Triple.of(
						"""
								public\t     \t \s
								  \tTest(){}""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PUBLIC)
								.returnType("Test")
								.build(),
						"""
								public Test(){ }"""
				),
				// Whitespace before Static
				Triple.of(
						"""
								\t    \t     \t   \t
								  \t  \tstatic Test(){}""",
						EditableJavaMethod.builder()
								.isStatic()
								.returnType("Test")
								.build(),
						"""
								static Test(){ }"""
				),
				// Whitespace after Static
				Triple.of(
						"""
								static      \t     \t
								    \t    \t  Test(){}""",
						EditableJavaMethod.builder()
								.isStatic()
								.returnType("Test")
								.build(),
						"""
								static Test(){ }"""
				),
				// Whitespace before Final
				Triple.of(
						"""
								\t    \t     \t   \t
								  \t  \tfinal Test(){}""",
						EditableJavaMethod.builder()
								.isFinal()
								.returnType("Test")
								.build(),
						"""
								final Test(){ }"""
				),
				// Whitespace after Final
				Triple.of(
						"""
								final      \t     \t
								    \t    \t  Test(){}""",
						EditableJavaMethod.builder()
								.isFinal()
								.returnType("Test")
								.build(),
						"""
								final Test(){ }"""
				),
				// Whitespace before Name
				Triple.of(
						"""
								String    \t    \t
								   \t   \ttest(){}""",
						EditableJavaMethod.builder()
								.returnType("String").name("test")
								.build(),
						"""
								String test(){ }"""
				),
				// Whitespace before Parameter Type
				Triple.of(
						"""
								Test(    \t     \t
								    \t   \t
								    String type){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.build(),
						"""
								Test(String type){ }"""
				),
				// Whitespace after Parameter Type
				Triple.of(
						"""
								Test(String    \t     \t
								    \t   \t
								    type){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.build(),
						"""
								Test(String type){ }"""
				),
				// Whitespace after Parameter Name
				Triple.of(
						"""
								Test(String type    \t     \t
								    \t   \t
								    \t ){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.build(),
						"""
								Test(String type){ }"""
				),
				// Whitespace before Parameter Comma
				Triple.of(
						"""
								Test(String type    \t
								    \t   \t ,int derp){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.parameter("int derp")
								.build(),
						"""
								Test(String type, int derp){ }"""
				),
				// Whitespace after Parameter Comma
				Triple.of(
						"""
								Test(String type,    \t
								    \t   \t int derp){}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.parameter("String type")
								.parameter("int derp")
								.build(),
						"""
								Test(String type, int derp){ }"""
				),
				// Whitespace before Throws
				Triple.of(
						"""
								Test()     \t   \t
								    \t    throws Exception{}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.build(),
						"""
								Test() throws Exception{ }"""
				),
				// Whitespace after Throws
				Triple.of(
						"""
								Test()throws     \t   \t
								    \t    Exception{}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.build(),
						"""
								Test() throws Exception{ }"""
				),
				// Whitespace after Throw Type
				Triple.of(
						"""
								Test()throws Exception     \t   \t
								    \t    {}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.build(),
						"""
								Test() throws Exception{ }"""
				),
				// Whitespace before Throw Comma
				Triple.of(
						"""
								Test()throws Exception     \t   \t
								    \t    ,Throwable{}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.throwType("Throwable")
								.build(),
						"""
								Test() throws Exception, Throwable{ }"""
				),
				// Whitespace after Throw Comma
				Triple.of(
						"""
								Test()throws Exception,     \t   \t
								    \t    Throwable{}""",
						EditableJavaMethod.builder()
								.returnType("Test")
								.throwType("Exception")
								.throwType("Throwable")
								.build(),
						"""
								Test() throws Exception, Throwable{ }"""
				),
				// Insane Whitespace
				Triple.of(
						"""
								 \t   \t
								\t    private     \t   \t
								    \t    static     \t   \t
								    \t    final      \t    \t
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
								    \t    \s""",
						EditableJavaMethod.builder()
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.returnType("String").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								private static final String test(String type, int derp) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
				),
				// Insane Whitespace with Annotations
				Triple.of(
						"""
								\t  \t @ \t  \tTest\t  (\t \ttype \t = \t
								\t  \t  String.class\t  , \t  \t defaultValue \t = \t  \t""  \t )    \t\t     \t
								\t    \t@       Derp   \t
									\t   \t
									\t    private     \t   \t
								\t    static     \t   \t
								\t    final     \t   \t
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
								\t    \s""",
						EditableJavaMethod.builder()
								.annotation(EditableJavaAnnotation.builder()
										.name("Test")
										.parameter("type", "String.class")
										.parameter("defaultValue", "\"\"")
										.build())
								.annotation(EditableJavaAnnotation.builder()
										.name("Derp")
										.build())
								.visibility(Visibility.PRIVATE)
								.isStatic().isFinal()
								.returnType("String").name("test")
								.parameter("String type")
								.parameter("int derp")
								.throwType("Exception")
								.throwType("Throwable")
								.line("doSomething();")
								.line("doSomethingElse();")
								.build(),
						"""
								@Test(type = String.class, defaultValue = "")
								@Derp
								private static final String test(String type, int derp) throws Exception, Throwable{
									doSomething();
									doSomethingElse();
								}"""
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
			ThrowingFunction<String, JavaMethod, JavaParsingException> parseMethod, String textToParse,
			JavaMethod expectedMethod, String expectedText) throws JavaParsingException{
		JavaMethod method = parseMethod.apply(textToParse);
		assertNotNull(method);
		assertEquals(expectedMethod, method);
		assertEquals(expectedText, method.toString());
	}
	
	public static Stream<Arguments> getErrorData(){
		List<Pair<String, String>> parsingData = ListUtil.createList(
				// Method Incomplete
				Pair.of(
						"""
								Test(){""",
						"Didn't complete the method"
				),
				// Parameters Incomplete
				Pair.of(
						"""
								Test(""",
						"Didn't complete parameters in method" +
								"\nDidn't complete the method"
				)
		);
		
		return parsingData.stream()
				.flatMap(pair -> Stream.of(0, 1)
						.map(index -> Arguments.of(parseMethods.get(index), pair.getLeft(), pair.getRight())));
	}
	
	@ParameterizedTest
	@MethodSource("getErrorData")
	public void testParsingError(
			ThrowingFunction<String, JavaMethod, JavaParsingException> parseMethod,
			String parseText, String error){
		try{
			parseMethod.apply(parseText);
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
					error), e.getMessage());
		}
	}
	
	/*
	 * Error Cases
	 */
	
	@Test
	public void testTooManyJavadocs(){
		try{
			JavaMethodParser.parseMethod("""
					/** {@inheritDoc} */
					/** some other Javadoc */
					Test(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Only one Javadoc allowed on a method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testJavadocAfterMethod(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					/** {@inheritDoc} */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered Javadoc after method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testAnnotationAfterMethod(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered annotation after method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testMultipleMethods(){
		try{
			JavaMethodParser.parseMethod("""
					Test(){}
					Test(){}""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Encountered multiple methods!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testNotAMethod(){
		try{
			JavaMethodParser.parseMethod("""
					@Test""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Failed to parse an actual method!"),
					e.getMessage());
		}
	}
	
	@Test
	public void testNotAMethodParseJustMethod(){
		assertNull(JavaMethodParser.parseJustMethod("@Test"));
	}
}
