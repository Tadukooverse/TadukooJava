package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaMethodParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleMethod(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					Test(){
					}""", method.toString());
	}
	
	@Test
	public void testSimpleMethodParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithName(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				String test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("String").name("test")
						.build(),
				method);
		assertEquals("""
				String test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithNameParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				String test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("String").name("test")
						.build(),
				method);
		assertEquals("""
				String test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibility(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				private Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					private Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				private Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					private Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityProtected(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				protected Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					protected Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityProtectedParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				protected Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					protected Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityPublic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				public Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					public Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityPublicParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				public Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					public Test(){
					}""", method.toString());
	}
	
	@Test
	public void testMethodWithStatic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				static Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				static Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithStaticParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				static Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				static Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithFinal(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				final Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				final Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithFinalParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				final Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				final Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithParameter(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test(String type){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithParameterParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String type){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleParameters(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test(String type, int derp){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.build(),
				method);
		assertEquals("""
				Test(String type, int derp){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleParametersParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String type, int derp){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.build(),
				method);
		assertEquals("""
				Test(String type, int derp){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithThrows(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test() throws Exception{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithThrowsParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test() throws Exception{}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleThrows(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test() throws Exception, Throwable{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.throwType("Throwable")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception, Throwable{
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleThrowsParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test() throws Exception, Throwable{}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.throwType("Throwable")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception, Throwable{
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithOneLineContent(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test(){doSomething();}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("doSomething();")
						.build(),
				method);
		assertEquals("""
				Test(){
					doSomething();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithOneLineContentParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(){doSomething();}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("doSomething();")
						.build(),
				method);
		assertEquals("""
				Test(){
					doSomething();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithContent(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test(){doSomething();doSomethingElse();}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				Test(){
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithContentParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(){doSomething();doSomethingElse();}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				Test(){
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithEverything(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				private static final String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.returnType("String").name("test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				private static final String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithEverythingParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				private static final String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.isStatic().isFinal()
						.returnType("String").name("test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				private static final String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithAnnotation() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				@Test
				Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.annotation(EditableJavaAnnotation.builder()
								.name("Test")
								.build())
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				@Test
				Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleAnnotations() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				@Test
				@Derp(type=String.class)
				Test(){}""");
		assertEquals(
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
				method);
		assertEquals("""
				@Test
				@Derp(type = String.class)
				Test(){
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithEverythingAndAnnotations() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				@Test
				@Derp(type=String.class)
				private static final String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}""");
		assertEquals(
				EditableJavaMethod.builder()
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
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				@Test
				@Derp(type = String.class)
				private static final String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	/*
	 * The Following Methods came from more extensive testing of Tadukoo Util code
	 * These particular methods caused errors in parsing that I needed to fix
	 */
	
	@Test
	public void testMethodWithBlocksInside() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				public static String toHex(byte[] bytes){
					StringBuilder hex = new StringBuilder();
					for(byte bite: bytes){
						hex.append(toHex(bite));
					}
					return hex.toString();
				}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String").name("toHex")
						.parameter("byte[]", "bytes")
						.line("StringBuilder hex = new StringBuilder();")
						.line("for(byte bite: bytes){")
						.line("\thex.append(toHex(bite));")
						.line("}")
						.line("return hex.toString();")
						.build(),
				method);
		assertEquals("""
				public static String toHex(byte[] bytes){
					StringBuilder hex = new StringBuilder();
					for(byte bite: bytes){
						hex.append(toHex(bite));
					}
					return hex.toString();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithMoreBlocksInside() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				public static byte[] fromHex(String hex){
					int size = hex.length();
					
					// Check that the size is even
					if(size % 2 != 0){
						throw new IllegalArgumentException("hex string must be an even length: " + hex);
					}
					
					// Create byte array to store the bytes in
					byte[] bites = new byte[size/2];
					
					// Iterate over the string, 2 characters at a time
					for(int i = 0; i < size; i+=2){
						int highNibble = hexToInt(hex.charAt(i));
						int lowNibble = hexToInt(hex.charAt(i+1));
						// If either nibble came out -1, we have an illegal hex character
						if(highNibble == -1 || lowNibble == -1){
							throw new IllegalArgumentException("hex string contains an illegal hex character: " + hex);
						}
						
						bites[i/2] = (byte) (highNibble*16 + lowNibble);
					}
					
					return bites;
				}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("byte[]").name("fromHex")
						.parameter("String", "hex")
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
				method);
		assertEquals("""
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
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithIfElseIfBlocks() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
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
				}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("int").name("hexToInt")
						.parameter("char", "hexChar")
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
				method);
		assertEquals("""
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
				}""", method.toString());
	}
}
