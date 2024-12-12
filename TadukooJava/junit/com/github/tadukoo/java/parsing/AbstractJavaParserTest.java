package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AbstractJavaParserTest{
	
	@Test
	public void testParseJavaTypeParametersEmpty(){
		List<JavaTypeParameter> params = AbstractJavaParser.parseJavaTypeParameters("");
		assertEquals(new ArrayList<>(), params);
	}
	
	@Test
	public void testParseJavaTypeParametersWhitespace(){
		List<JavaTypeParameter> params = AbstractJavaParser.parseJavaTypeParameters("\t\n  \t");
		assertEquals(new ArrayList<>(), params);
	}
	
	@Test
	public void testParseJavaTypeParametersSimple(){
		List<JavaTypeParameter> params = AbstractJavaParser.parseJavaTypeParameters("String");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType("String")
						.build()
		), params);
	}
	
	@Test
	public void testParseJavaTypeParametersWithExtends(){
		List<JavaTypeParameter> params = AbstractJavaParser.parseJavaTypeParameters("? extends String");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType("?")
						.extendsType("String")
						.build()
		), params);
	}
	
	@Test
	public void testParseJavaTypeParametersWithMultiple(){
		List<JavaTypeParameter> params = AbstractJavaParser.parseJavaTypeParameters(
				"? extends String, ? extends Object");
		assertEquals(ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType("?")
						.extendsType("String")
						.build(),
				JavaTypeParameter.builder()
						.baseType("?")
						.extendsType("Object")
						.build()
		), params);
	}
	
	@Test
	public void testParseJavaTypeParametersNotATypeParameter(){
		try{
			AbstractJavaParser.parseJavaTypeParameters("Something extends Something extends Yes");
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("'Something extends Something extends Yes' is not a valid type parameter", e.getMessage());
		}
	}
	
	@Test
	public void testParseJavaTypeBasic(){
		JavaType type = AbstractJavaParser.parseJavaType("String");
		assertEquals(JavaType.builder()
				.baseType("String")
				.build(), type);
	}
	
	@Test
	public void testParseJavaTypeWithParameters(){
		JavaType type = AbstractJavaParser.parseJavaType("Map<String, Object>");
		assertEquals(
				JavaType.builder()
						.baseType("Map")
						.typeParameter(JavaTypeParameter.builder()
								.baseType("String")
								.build())
						.typeParameter(JavaTypeParameter.builder()
								.baseType("Object")
								.build())
						.build(), type);
	}
	
	@Test
	public void testParseJavaTypeNotAType(){
		try{
			AbstractJavaParser.parseJavaType("Something>Truly Garbage<");
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("'Something>Truly Garbage<' is not a valid type", e.getMessage());
		}
	}
	
	@Test
	public void testParseJavaParameterBasic(){
		JavaParameter parameter = AbstractJavaParser.parseJavaParameter("String text");
		assertEquals(
				JavaParameter.builder()
						.type(JavaType.builder()
								.baseType("String")
								.build())
						.name("text")
						.build(), parameter);
	}
	
	@Test
	public void testParseJavaParameterVararg(){
		JavaParameter parameter = AbstractJavaParser.parseJavaParameter("String ... text");
		assertEquals(
				JavaParameter.builder()
						.type(JavaType.builder()
								.baseType("String")
								.build())
						.vararg()
						.name("text")
						.build(), parameter);
	}
	
	@Test
	public void testParseJavaParameterNotAParameter(){
		try{
			AbstractJavaParser.parseJavaParameter("Something>Truly Garbage< text ...");
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("'Something>Truly Garbage< text ...' is not a valid parameter", e.getMessage());
		}
	}
}
