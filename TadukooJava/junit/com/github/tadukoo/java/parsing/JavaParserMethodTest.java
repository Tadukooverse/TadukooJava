package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaParserMethodTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleMethod(){
		JavaMethod method = parser.parseMethod("""
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
	public void testSimpleMethodParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithNameParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithVisibilityParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithVisibilityProtectedParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithVisibilityPublicParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithStaticParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithFinalParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithParameterParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithMultipleParametersParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithThrowsParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithMultipleThrowsParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithOneLineContentParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithContentParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = parser.parseMethod("""
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
	public void testMethodWithEverythingParseType() throws JavaParsingException{
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = runParserForMethod("""
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
		JavaMethod method = runParserForMethod("""
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
}
