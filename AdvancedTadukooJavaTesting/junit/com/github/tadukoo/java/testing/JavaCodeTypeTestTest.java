package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.List;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.checkBoolean;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.checkEnum;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.checkList;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.checkString;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.runParserForClass;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestTest{
	
	@Test
	public void testCheckBooleanSuccess(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkBoolean(clazz1, clazz2, differences, "Editable", JavaClass::isEditable);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckBooleanFailure(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = UneditableJavaClass.builder()
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkBoolean(clazz1, clazz2, differences, "Editable", JavaClass::isEditable);
		assertEquals(1, differences.size());
		assertEquals("Editable is different!", differences.get(0));
	}
	
	@Test
	public void testCheckStringSuccess(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("test")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("test")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(method1, method2, differences, "Name", JavaMethod::getName);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckStringBlankSuccess(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name(null)
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(method1, method2, differences, "Name", JavaMethod::getName);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckStringFailure(){
		JavaMethod method1 = EditableJavaMethod.builder()
				.returnType("String").name("something")
				.build();
		JavaMethod method2 = EditableJavaMethod.builder()
				.returnType("String").name("nothing")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(method1, method2, differences, "Name", JavaMethod::getName);
		assertEquals(1, differences.size());
		assertEquals("Name is different!", differences.get(0));
	}
	
	@Test
	public void testRunParserForClassSuccess() throws JavaParsingException{
		JavaClass clazz = runParserForClass("""
				class Test{ }""");
		assertEquals(clazz, EditableJavaClass.builder()
				.className("Test")
				.build());
	}
	
	@Test
	public void testCheckListSuccessBothEmpty(){
		Javadoc doc1 = EditableJavadoc.builder()
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.build();
		List<String> differences = new ArrayList<>();
		checkList(doc1, doc2, differences, "Content", Javadoc::getContent, StringUtil::equals);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckListFailureFirstEmpty(){
		Javadoc doc1 = EditableJavadoc.builder()
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(doc1, doc2, differences, "Content", Javadoc::getContent, StringUtil::equals);
		assertEquals(1, differences.size());
		assertEquals("Content length is different!", differences.get(0));
	}
	
	@Test
	public void testCheckListSuccessNotEmpty(){
		Javadoc doc1 = EditableJavadoc.builder()
				.content("Something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("Something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(doc1, doc2, differences, "Content", Javadoc::getContent, StringUtil::equals);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckListFailure(){
		Javadoc doc1 = EditableJavadoc.builder()
				.content("SomethingElse")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("Something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(doc1, doc2, differences, "Content", Javadoc::getContent, StringUtil::equals);
		assertEquals(1, differences.size());
		assertEquals("Content differs on #1!", differences.get(0));
	}
	
	@Test
	public void testCheckEnumSuccess(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.visibility(Visibility.PUBLIC)
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.visibility(Visibility.PUBLIC)
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkEnum(clazz1, clazz2, differences, "Visibility", JavaClass::getVisibility);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckEnumFailure(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.visibility(Visibility.PUBLIC)
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.visibility(Visibility.PRIVATE)
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkEnum(clazz1, clazz2, differences, "Visibility", JavaClass::getVisibility);
		assertEquals(1, differences.size());
		assertEquals("Visibility is different!", differences.get(0));
	}
	
	@Test
	public void testRunParserForClassNotAClass() throws JavaParsingException{
		try{
			runParserForClass("""
					String test;""");
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildAssertError(JavaCodeTypes.CLASS, JavaCodeTypes.FIELD), e.getMessage());
		}
	}
}
