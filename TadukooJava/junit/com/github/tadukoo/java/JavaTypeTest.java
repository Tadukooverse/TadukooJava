package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTypeTest{
	
	@Test
	public void testBaseType(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.build();
		assertEquals("String", type.getBaseType());
	}
	
	@Test
	public void testTypeParameters(){
		List<JavaTypeParameter> typeParams = ListUtil.createList(
				JavaTypeParameter.builder()
						.baseType("String")
						.build(),
				JavaTypeParameter.builder()
						.baseType("Object")
						.build());
		JavaType type = JavaType.builder()
				.baseType("Map")
				.typeParameters(typeParams)
				.build();
		assertEquals(typeParams, type.getTypeParameters());
	}
	
	@Test
	public void testTypeParameter(){
		JavaTypeParameter stringTypeParam = JavaTypeParameter.builder()
				.baseType("String")
				.build();
		JavaTypeParameter objectTypeParam = JavaTypeParameter.builder()
				.baseType("Object")
				.build();
		JavaType type = JavaType.builder()
				.baseType("Map")
				.typeParameter(stringTypeParam)
				.typeParameter(objectTypeParam)
				.build();
		assertEquals(ListUtil.createList(stringTypeParam, objectTypeParam), type.getTypeParameters());
	}
	
	@Test
	public void testCanonicalName(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), type.getCanonicalName());
	}
	
	@Test
	public void testBaseTypeMissing(){
		try{
			JavaType.builder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("baseType can't be empty!", e.getMessage());
		}
	}
	
	@Test
	public void testSetCanonicalName(){
		JavaType type = JavaType.builder()
				.baseType("String")
				.canonicalName(String.class.getCanonicalName())
				.build();
		assertEquals(String.class.getCanonicalName(), type.getCanonicalName());
		type.setCanonicalName("com.tadukoo.String");
		assertEquals("com.tadukoo.String", type.getCanonicalName());
	}
}
