package com.github.tadukoo.java;

import com.github.tadukoo.util.SetUtil;
import org.junit.jupiter.api.Test;

import static com.github.tadukoo.java.JavaTokens.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaTokensTest{
	
	@Test
	public void testSEMICOLON(){
		assertEquals(";", SEMICOLON);
	}
	
	/*
	 * Proper "Tokens"
	 */
	
	@Test
	public void testPACKAGE_TOKEN(){
		assertEquals("package", PACKAGE_TOKEN);
	}
	
	@Test
	public void testIMPORT_TOKEN(){
		assertEquals("import", IMPORT_TOKEN);
	}
	
	/*
	 * Not-So-Proper "Tokens"
	 */
	
	@Test
	public void testJAVADOC_START_TOKEN(){
		assertEquals("/**", JAVADOC_START_TOKEN);
	}
	
	@Test
	public void testMULTI_LINE_COMMENT_START_TOKEN(){
		assertEquals("/*", MULTI_LINE_COMMENT_START_TOKEN);
	}
	
	@Test
	public void testSINGLE_LINE_COMMENT_TOKEN(){
		assertEquals("//", SINGLE_LINE_COMMENT_TOKEN);
	}
	
	@Test
	public void testANNOTATION_START_TOKEN(){
		assertEquals("@", ANNOTATION_START_TOKEN);
	}
	
	/*
	 * Visibility "Modifiers"
	 */
	
	@Test
	public void testPRIVATE_MODIFIER(){
		assertEquals("private", PRIVATE_MODIFIER);
	}
	
	@Test
	public void testPROTECTED_MODIFIER(){
		assertEquals("protected", PROTECTED_MODIFIER);
	}
	
	@Test
	public void testPUBLIC_MODIFIER(){
		assertEquals("public", PUBLIC_MODIFIER);
	}
	
	/*
	 * Other "Modifiers"
	 */
	
	@Test
	public void testSTATIC_MODIFIER(){
		assertEquals("static", STATIC_MODIFIER);
	}
	
	@Test
	public void testMODIFIERS(){
		assertEquals(SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER,
				STATIC_MODIFIER),
				MODIFIERS);
	}
}
