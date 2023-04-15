package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaClassType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaClassTypeTest{
	
	@Test
	public void testGetJavaTypeClassCLASS(){
		assertEquals(JavaClass.class, CLASS.getJavaTypeClass());
	}
}
