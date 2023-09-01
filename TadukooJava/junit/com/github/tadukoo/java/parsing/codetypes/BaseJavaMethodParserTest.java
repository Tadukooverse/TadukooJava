package com.github.tadukoo.java.parsing.codetypes;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavaMethodParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, JavaMethod, JavaParsingException> parseMethod;
	
	protected BaseJavaMethodParserTest(ThrowingFunction<String, JavaMethod, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Regular Tests
	 */
	
	@Test
	public void testSimpleMethod() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithName() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				String test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("String").name("test")
						.build(),
				method);
		assertEquals("""
				String test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibility() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				private Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					private Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityProtected() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				protected Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					protected Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithVisibilityPublic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				public Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
					public Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithAbstract() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				abstract Test();""");
		assertEquals(
				EditableJavaMethod.builder()
						.isAbstract()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				abstract Test();""", method.toString());
	}
	
	@Test
	public void testMethodWithStatic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				static Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				static Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithFinal() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				final Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				final Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithReversedModifiers() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				final static protected Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.isStatic().isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				protected static final Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithParameter() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(String type){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleParameters() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
				Test(String type, int derp){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithThrows() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test() throws Exception{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{ }""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleThrows() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
				Test() throws Exception, Throwable{ }""", method.toString());
	}
	
	@Test
	public void testMethodWithOneLineContent() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
	public void testMethodWithContent() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
	public void testMethodWithEverything() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
	public void testMethodWithAnnotation() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithMultipleAnnotations() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithJavadoc() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				/** {@inheritDoc} */
				Test(){}""");
		assertEquals(
				EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				/** {@inheritDoc} */
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testMethodWithEverythingAndAnnotations() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				/** {@inheritDoc} */
				@Test
				@Derp(type=String.class)
				private static final String test(String type, int derp) throws Exception, Throwable{doSomething();doSomethingElse();}""");
		assertEquals(
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
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				/** {@inheritDoc} */
				@Test
				@Derp(type = String.class)
				private static final String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	/*
	 * Error Cases
	 */
	
	@Test
	public void testMethodIncomplete(){
		try{
			parseMethod.apply("""
					Test(){""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Didn't complete the method"),
					e.getMessage());
		}
	}
	
	@Test
	public void testParametersIncomplete(){
		try{
			parseMethod.apply("""
					Test(""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(
					buildJavaParsingExceptionMessage(JavaCodeTypes.METHOD,
							"Didn't complete parameters in method" +
									"\nDidn't complete the method"),
					e.getMessage());
		}
	}
	
	/*
	 * Edge Cases for certain branches
	 */
	@Test
	public void testMethodWithLineWithNoSemicolon() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){
					something.builder()
							.build();
				}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("something.builder()")
						.line("\t\t.build();")
						.build(),
				method);
		assertEquals("""
				Test(){
					something.builder()
							.build();
				}""", method.toString());
	}
	
	@Test
	public void testMethodWithBlankSpaceBetweenSemicolons() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){
					for(int i = 0;;){
						doSomething();
					}
				}""");
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.line("for(int i = 0;;){")
						.line("\tdoSomething();")
						.line("}")
						.build(),
				method);
		assertEquals("""
				Test(){
					for(int i = 0;;){
						doSomething();
					}
				}""", method.toString());
	}
	
	/*
	 * The Following Methods came from more extensive testing of Tadukoo Util code
	 * These particular methods caused errors in parsing that I needed to fix
	 */
	
	@Test
	public void testMethodWithBlocksInside() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
		JavaMethod method = parseMethod.apply("""
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
		JavaMethod method = parseMethod.apply("""
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
	
	@Test
	public void testMethodWithThrowsAndInnerBlockOpenToken() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				public static List<File> listAllFiles(Path directoryPath) throws IOException{
					try(Stream<Path> pathStream = Files.walk(directoryPath)){
						return pathStream
								.filter(Files::isRegularFile)
								.map(Path::toFile)
								.collect(Collectors.toList());
					}
				}""");
		assertEquals(EditableJavaMethod.builder()
				.visibility(Visibility.PUBLIC)
				.isStatic()
				.returnType("List<File>").name("listAllFiles")
				.parameter("Path", "directoryPath")
				.throwType("IOException")
				.line("try(Stream<Path> pathStream = Files.walk(directoryPath)){")
				.line("\treturn pathStream")
				.line("\t\t\t.filter(Files::isRegularFile)")
				.line("\t\t\t.map(Path::toFile)")
				.line("\t\t\t.collect(Collectors.toList());")
				.line("}")
				.build(), method);
		assertEquals("""
				public static List<File> listAllFiles(Path directoryPath) throws IOException{
					try(Stream<Path> pathStream = Files.walk(directoryPath)){
						return pathStream
								.filter(Files::isRegularFile)
								.map(Path::toFile)
								.collect(Collectors.toList());
					}
				}""", method.toString());
	}
	
	/*
	 * Whitespace Tests
	 */
	
	@Test
	public void testNoWhitespaceSimpleMethod() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testNoWhitespaceEverythingMethod() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				private static String test(String type, int derp)throws Exception,Throwable{doSomething();doSomethingElse();}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.isStatic()
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
				private static String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
	
	@Test
	public void testLeadingWhitespace() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t   \s
				  \t    Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterReturnType() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test    \t   \s
				  \t   (){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceInEmptyParameters() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test (    \t
				    \t    ){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBetweenParametersAndOpen() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()      \t
				  \t    \t{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceInMethod() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){       \t
				   \t    \t
				    \t }""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testTrailingWhitespace() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(){}     \t
				   \t    \t
				   \t""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforePrivate() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t     \t \s
				  \tprivate Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				private Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeProtected() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t     \t \s
				  \tprotected Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				protected Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforePublic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t     \t \s
				  \tpublic Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				public Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterPrivate() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				private\t     \t \s
				  \tTest(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PRIVATE)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				private Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterProtected() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				protected\t     \t \s
				  \tTest(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PROTECTED)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				protected Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterPublic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				public\t     \t \s
				  \tTest(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.visibility(Visibility.PUBLIC)
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				public Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeStatic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t    \t     \t   \t
				  \t  \tstatic Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				static Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterStatic() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				static      \t     \t
				    \t    \t  Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isStatic()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				static Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeFinal() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				\t    \t     \t   \t
				  \t  \tfinal Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				final Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterFinal() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				final      \t     \t
				    \t    \t  Test(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.isFinal()
						.returnType("Test")
						.build(),
				method);
		assertEquals("""
				final Test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeName() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				String    \t    \t
				   \t   \ttest(){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("String").name("test")
						.build(),
				method);
		assertEquals("""
				String test(){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeParameterType() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(    \t     \t
				    \t   \t
				    String type){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterType() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(String    \t     \t
				    \t   \t
				    type){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterName() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(String type    \t     \t
				    \t   \t
				    \t ){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.build(),
				method);
		assertEquals("""
				Test(String type){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeParameterComma() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(String type    \t
				    \t   \t ,int derp){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.build(),
				method);
		assertEquals("""
				Test(String type, int derp){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterComma() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test(String type,    \t
				    \t   \t int derp){}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.parameter("String", "type")
						.parameter("int", "derp")
						.build(),
				method);
		assertEquals("""
				Test(String type, int derp){ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeThrows() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()     \t   \t
				    \t    throws Exception{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrows() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()throws     \t   \t
				    \t    Exception{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrowType() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()throws Exception     \t   \t
				    \t    {}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception{ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeThrowComma() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()throws Exception     \t   \t
				    \t    ,Throwable{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.throwType("Throwable")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception, Throwable{ }""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrowComma() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
				Test()throws Exception,     \t   \t
				    \t    Throwable{}""");
		assertNotNull(method);
		assertEquals(
				EditableJavaMethod.builder()
						.returnType("Test")
						.throwType("Exception")
						.throwType("Throwable")
						.build(),
				method);
		assertEquals("""
				Test() throws Exception, Throwable{ }""", method.toString());
	}
	
	@Test
	public void testInsaneWhitespace() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
				    \t    \s""");
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
	public void testInsaneWhitespaceWithAnnotations() throws JavaParsingException{
		JavaMethod method = parseMethod.apply("""
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
					\t    \s""");
		assertEquals(
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
						.parameter("String", "type")
						.parameter("int", "derp")
						.throwType("Exception")
						.throwType("Throwable")
						.line("doSomething();")
						.line("doSomethingElse();")
						.build(),
				method);
		assertEquals("""
				@Test(type = String.class, defaultValue = "")
				@Derp
				private static final String test(String type, int derp) throws Exception, Throwable{
					doSomething();
					doSomethingElse();
				}""", method.toString());
	}
}
