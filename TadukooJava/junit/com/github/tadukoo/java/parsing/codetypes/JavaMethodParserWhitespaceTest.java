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

public class JavaMethodParserWhitespaceTest extends BaseJavaParserTest{
	
	@Test
	public void testNoWhitespaceSimpleMethod(){
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
	public void testNoWhitespaceSimpleMethodParseType() throws JavaParsingException{
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
	public void testNoWhitespaceEverythingMethod(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
	public void testNoWhitespaceEverythingMethodParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				private static String test(String type, int derp)throws Exception,Throwable{doSomething();doSomethingElse();}""");
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
	public void testLeadingWhitespace(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				\t   \s
				  \t    Test(){}""");
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
	public void testLeadingWhitespaceParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t   \s
				  \t    Test(){}""");
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
	public void testWhitespaceAfterReturnType(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test    \t   \s
				  \t   (){}""");
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
	public void testWhitespaceAfterReturnTypeParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test    \t   \s
				  \t   (){}""");
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
	public void testWhitespaceInEmptyParameters(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test (    \t
				    \t    ){}""");
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
	public void testWhitespaceInEmptyParametersParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test (    \t
				    \t    ){}""");
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
	public void testWhitespaceBetweenParametersAndOpen(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				Test()      \t
				  \t    \t{}""");
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
	public void testWhitespaceBetweenParametersAndOpenParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()      \t
				  \t    \t{}""");
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
	public void testWhitespaceInMethod(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceInMethodParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(){       \t
				   \t    \t
				    \t }""");
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
	public void testTrailingWhitespace(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(){
				}""", method.toString());
	}
	
	@Test
	public void testTrailingWhitespaceParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(){}     \t
				   \t    \t
				   \t""");
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
	public void testWhitespaceBeforePrivate(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				private Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforePrivateParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t     \t \s
				  \tprivate Test(){}""");
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
	public void testWhitespaceBeforeProtected(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				protected Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeProtectedParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t     \t \s
				  \tprotected Test(){}""");
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
	public void testWhitespaceBeforePublic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				public Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforePublicParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t     \t \s
				  \tpublic Test(){}""");
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
	public void testWhitespaceAfterPrivate(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				private Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterPrivateParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				private\t     \t \s
				  \tTest(){}""");
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
	public void testWhitespaceAfterProtected(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				protected Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterProtectedParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				protected\t     \t \s
				  \tTest(){}""");
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
	public void testWhitespaceAfterPublic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				public Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterPublicParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				public\t     \t \s
				  \tTest(){}""");
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
	public void testWhitespaceBeforeStatic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				static Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeStaticParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t    \t     \t   \t
				  \t  \tstatic Test(){}""");
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
	public void testWhitespaceAfterStatic(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				static Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterStaticParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				static      \t     \t
				    \t    \t  Test(){}""");
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
	public void testWhitespaceBeforeFinal(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				final Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeFinalParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				\t    \t     \t   \t
				  \t  \tfinal Test(){}""");
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
	public void testWhitespaceAfterFinal(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				final Test(){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterFinalParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				final      \t     \t
				    \t    \t  Test(){}""");
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
	public void testWhitespaceBeforeName(){
		JavaMethod method = JavaMethodParser.parseMethod("""
				String    \t    \t
				   \t   \ttest(){}""");
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
	public void testWhitespaceBeforeNameParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				String    \t    \t
				   \t   \ttest(){}""");
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
	public void testWhitespaceBeforeParameterType(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(String type){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeParameterTypeParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(    \t     \t
				    \t   \t
				    String type){}""");
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
	public void testWhitespaceAfterParameterType(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(String type){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterTypeParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String    \t     \t
				    \t   \t
				    type){}""");
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
	public void testWhitespaceAfterParameterName(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(String type){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterNameParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String type    \t     \t
				    \t   \t
				    \t ){}""");
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
	public void testWhitespaceBeforeParameterComma(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(String type, int derp){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeParameterCommaParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String type    \t
				    \t   \t ,int derp){}""");
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
	public void testWhitespaceAfterParameterComma(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test(String type, int derp){
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterParameterCommaParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test(String type,    \t
				    \t   \t int derp){}""");
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
	public void testWhitespaceBeforeThrows(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test() throws Exception{
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeThrowsParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()     \t   \t
				    \t    throws Exception{}""");
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
	public void testWhitespaceAfterThrows(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test() throws Exception{
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrowsParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()throws     \t   \t
				    \t    Exception{}""");
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
	public void testWhitespaceAfterThrowType(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test() throws Exception{
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrowTypeParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()throws Exception     \t   \t
				    \t    {}""");
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
	public void testWhitespaceBeforeThrowComma(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test() throws Exception, Throwable{
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceBeforeThrowCommaParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()throws Exception     \t   \t
				    \t    ,Throwable{}""");
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
	public void testWhitespaceAfterThrowComma(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
				Test() throws Exception, Throwable{
				}""", method.toString());
	}
	
	@Test
	public void testWhitespaceAfterThrowCommaParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
				Test()throws Exception,     \t   \t
				    \t    Throwable{}""");
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
	public void testInsaneWhitespace(){
		JavaMethod method = JavaMethodParser.parseMethod("""
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
	public void testInsaneWhitespaceParseType() throws JavaParsingException{
		JavaMethod method = runFullParserForMethod("""
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
		JavaMethod method = runFullParserForMethod("""
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
