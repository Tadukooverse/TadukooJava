package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javaclass.UneditableJavaClass;
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
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(clazz1, clazz2, differences, "Class Name", JavaClass::getClassName);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckStringBlankSuccess(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.superClassName(null)
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.superClassName("")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(clazz1, clazz2, differences, "Super Class Name", JavaClass::getSuperClassName);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckStringFailure(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.superClassName("Something")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.superClassName("Nothing")
				.build();
		List<String> differences = new ArrayList<>();
		checkString(clazz1, clazz2, differences, "Super Class Name", JavaClass::getSuperClassName);
		assertEquals(1, differences.size());
		assertEquals("Super Class Name is different!", differences.get(0));
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
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(clazz1, clazz2, differences, "Implements Interface Names",
				JavaClass::getImplementsInterfaceNames, StringUtil::equals);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckListFailureFirstEmpty(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.implementsInterfaceName("Something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(clazz1, clazz2, differences, "Implements Interface Names",
				JavaClass::getImplementsInterfaceNames, StringUtil::equals);
		assertEquals(1, differences.size());
		assertEquals("Implements Interface Names length is different!", differences.get(0));
	}
	
	@Test
	public void testCheckListSuccessNotEmpty(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.implementsInterfaceName("Something")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.implementsInterfaceName("Something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(clazz1, clazz2, differences, "Implements Interface Names",
				JavaClass::getImplementsInterfaceNames, StringUtil::equals);
		assertTrue(differences.isEmpty());
	}
	
	@Test
	public void testCheckListFailure(){
		JavaClass clazz1 = EditableJavaClass.builder()
				.className("Test")
				.implementsInterfaceName("SomethingElse")
				.build();
		JavaClass clazz2 = EditableJavaClass.builder()
				.className("Test")
				.implementsInterfaceName("Something")
				.build();
		List<String> differences = new ArrayList<>();
		checkList(clazz1, clazz2, differences, "Implements Interface Names",
				JavaClass::getImplementsInterfaceNames, StringUtil::equals);
		assertEquals(1, differences.size());
		assertEquals("Implements Interface Names differs on #1!", differences.get(0));
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
