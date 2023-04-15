package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaClass;
import com.github.tadukoo.java.JavaClassType;
import com.github.tadukoo.java.JavaType;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class BaseJavaParserTest{
	
	protected JavaParser parser;
	
	@BeforeEach
	public void setup(){
		parser = new JavaParser();
	}
	
	protected JavaClass runParserForClass(String content){
		JavaType type = parser.parseJavaType(content);
		assertEquals(JavaClassType.CLASS, type.getType());
		assertTrue(type instanceof JavaClass);
		return (JavaClass) type;
	}
}
