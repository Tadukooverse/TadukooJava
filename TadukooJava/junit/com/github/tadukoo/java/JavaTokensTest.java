package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaTokens.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTokensTest{
	
	@Test
	public void testSEMICOLON(){
		assertEquals(";", SEMICOLON);
	}
	
	@Test
	public void testPACKAGE_TOKEN(){
		assertEquals("package", PACKAGE_TOKEN);
	}
	
	@Test
	public void testIMPORT_TOKEN(){
		assertEquals("import", IMPORT_TOKEN);
	}
	
	@Test
	public void testSTATIC_MODIFIER(){
		assertEquals("static", STATIC_MODIFIER);
	}
}
