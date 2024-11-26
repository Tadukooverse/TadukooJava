package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class JavaClassParsingTest extends JavaCodeTypeTest{
	
	private final String javaClassCode;
	private final JavaClass expectedClass;
	private JavaClass clazz;
	
	protected JavaClassParsingTest(String javaClassCode, JavaClass expectedClass){
		this.javaClassCode = javaClassCode;
		this.expectedClass = expectedClass;
	}
	
	@BeforeEach
	public void setup() throws JavaParsingException{
		clazz = runParserForClass(javaClassCode);
	}
	
	@Test
	public void testRoundTrip(){
		assertEquals(javaClassCode, clazz.toString());
	}
	
	@Test
	public void testPojos(){
		assertClassEquals(expectedClass, clazz);
	}
}
