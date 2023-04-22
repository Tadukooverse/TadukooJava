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
	
	@Test
	public void testCLASS_TOKEN(){
		assertEquals("class", CLASS_TOKEN);
	}
	
	@Test
	public void testTHROWS_TOKEN(){
		assertEquals("throws", THROWS_TOKEN);
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
	
	@Test
	public void testPARAMETER_OPEN_TOKEN(){
		assertEquals("(", PARAMETER_OPEN_TOKEN);
	}
	
	@Test
	public void testPARAMETER_CLOSE_TOKEN(){
		assertEquals(")", PARAMETER_CLOSE_TOKEN);
	}
	
	@Test
	public void testLIST_SEPARATOR_TOKEN(){
		assertEquals(",", LIST_SEPARATOR_TOKEN);
	}
	
	@Test
	public void testBLOCK_OPEN_TOKEN(){
		assertEquals("{", BLOCK_OPEN_TOKEN);
	}
	
	@Test
	public void testBLOCK_CLOSE_TOKEN(){
		assertEquals("}", BLOCK_CLOSE_TOKEN);
	}
	
	@Test
	public void testASSIGNMENT_OPERATOR_TOKEN(){
		assertEquals("=", ASSIGNMENT_OPERATOR_TOKEN);
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
	public void testFINAL_MODIFIER(){
		assertEquals("final", FINAL_MODIFIER);
	}
	
	@Test
	public void testMODIFIERS(){
		assertEquals(SetUtil.createSet(PRIVATE_MODIFIER, PROTECTED_MODIFIER, PUBLIC_MODIFIER,
				STATIC_MODIFIER, FINAL_MODIFIER),
				MODIFIERS);
	}
}
