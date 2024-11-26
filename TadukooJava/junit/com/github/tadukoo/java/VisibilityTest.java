package com.github.tadukoo.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VisibilityTest{
	
	@Test
	public void testPublicText(){
		assertEquals("public", Visibility.PUBLIC.getToken());
	}
	
	@Test
	public void testProtectedText(){
		assertEquals("protected", Visibility.PROTECTED.getToken());
	}
	
	@Test
	public void testPrivateText(){
		assertEquals("private", Visibility.PRIVATE.getToken());
	}
	
	@Test
	public void testNoneText(){
		assertEquals("", Visibility.NONE.getToken());
	}
	
	@Test
	public void testFromTextPublic(){
		assertEquals(Visibility.PUBLIC, Visibility.fromToken("pUBlIc"));
	}
	
	@Test
	public void testFromTextProtected(){
		assertEquals(Visibility.PROTECTED, Visibility.fromToken("PROtecTeD"));
	}
	
	@Test
	public void testFromTextPrivate(){
		assertEquals(Visibility.PRIVATE, Visibility.fromToken("PRiVAtE"));
	}
	
	@Test
	public void testFromTextNone(){
		assertEquals(Visibility.NONE, Visibility.fromToken(""));
	}
	
	@Test
	public void testFromTextGarbage(){
		assertNull(Visibility.fromToken("some_garbage_Stuff"));
	}
}
