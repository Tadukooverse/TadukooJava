package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javadoc.UneditableJavadoc;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;

import static com.github.tadukoo.java.testing.JavaCodeTypeTest.assertJavadocEquals;
import static com.github.tadukoo.java.testing.JavaCodeTypeTest.findJavadocDifferences;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildAssertError;
import static com.github.tadukoo.util.junit.AssertionFailedErrors.buildTwoPartError;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaCodeTypeTestJavadocTest{
	
	@Test
	public void testFindJavadocDifferencesNone(){
		assertEquals(new ArrayList<>(), findJavadocDifferences(
				EditableJavadoc.builder()
						.build(),
				EditableJavadoc.builder()
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesBothNull(){
		assertEquals(new ArrayList<>(), findJavadocDifferences(
				null, null
		));
	}
	
	@Test
	public void testFindJavadocDifferencesDoc1NullDoc2Not(){
		assertEquals(ListUtil.createList("One of the Javadocs is null, and the other isn't!"), findJavadocDifferences(
				null,
				EditableJavadoc.builder()
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesDoc2NullDoc1Not(){
		assertEquals(ListUtil.createList("One of the Javadocs is null, and the other isn't!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.build(),
				null));
	}
	
	@Test
	public void testFindJavadocDifferencesEditable(){
		assertEquals(ListUtil.createList("Editable is different!"), findJavadocDifferences(
				UneditableJavadoc.builder()
						.build(),
				EditableJavadoc.builder()
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesCondensed(){
		assertEquals(ListUtil.createList("Condensed is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.condensed()
						.build(),
				EditableJavadoc.builder()
						.condensed(false)
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesContent(){
		assertEquals(ListUtil.createList("Content differs on #1!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.content("something")
						.build(),
				EditableJavadoc.builder()
						.content("yep")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesContentLength(){
		assertEquals(ListUtil.createList("Content length is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.content("something")
						.build(),
				EditableJavadoc.builder()
						.content("something")
						.content("yep")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesContentLengthDoc1Longer(){
		assertEquals(ListUtil.createList("Content length is different!",
				"Content differs on #2!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.content("something")
						.content("yep")
						.build(),
				EditableJavadoc.builder()
						.content("something")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesAuthor(){
		assertEquals(ListUtil.createList("Author is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.author("Logan Ferree (Tadukoo)")
						.build(),
				EditableJavadoc.builder()
						.author("Logan Ferree")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesVersion(){
		assertEquals(ListUtil.createList("Version is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.version("Alpha v.0.1")
						.build(),
				EditableJavadoc.builder()
						.version("Pre-Alpha")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesSince(){
		assertEquals(ListUtil.createList("Since is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.since("Pre-Alpha")
						.build(),
				EditableJavadoc.builder()
						.since("Alpha v.0.1")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesParamsLength(){
		assertEquals(ListUtil.createList("Params length is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.param("String", "something")
						.build(),
				EditableJavadoc.builder()
						.param("String", "something")
						.param("int", "version")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesParamsLengthDoc1Longer(){
		assertEquals(ListUtil.createList("Params length is different!",
				"Params differs on #2!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.param("String", "something")
						.param("int", "version")
						.build(),
				EditableJavadoc.builder()
						.param("String", "something")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesReturnVal(){
		assertEquals(ListUtil.createList("Return is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.returnVal("null")
						.build(),
				EditableJavadoc.builder()
						.returnVal("true if true")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesThrowsLength(){
		assertEquals(ListUtil.createList("Throws length is different!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.throwsInfo("IllegalStateException", "something")
						.build(),
				EditableJavadoc.builder()
						.throwsInfo("IllegalStateException", "something")
						.throwsInfo("IllegalArgumentException", "why not?")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesThrowsLengthDoc1Longer(){
		assertEquals(ListUtil.createList("Throws length is different!",
				"Throws differs on #2!"), findJavadocDifferences(
				EditableJavadoc.builder()
						.throwsInfo("IllegalStateException", "something")
						.throwsInfo("IllegalArgumentException", "why not?")
						.build(),
				EditableJavadoc.builder()
						.throwsInfo("IllegalStateException", "something")
						.build()));
	}
	
	@Test
	public void testFindJavadocDifferencesAll(){
		assertEquals(ListUtil.createList(
				"Editable is different!",
				"Condensed is different!",
				"Content length is different!",
				"Content differs on #1!",
				"Author is different!",
				"Version is different!",
				"Since is different!",
				"Params length is different!",
				"Params differs on #1!",
				"Return is different!",
				"Throws length is different!",
				"Throws differs on #1!"), findJavadocDifferences(
				UneditableJavadoc.builder()
						.condensed()
						.content("something")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.1")
						.since("Pre-Alpha")
						.param("String", "somethingElse")
						.returnVal("null")
						.throwsInfo("IllegalStateException", "something")
						.build(),
				EditableJavadoc.builder()
						.condensed(false)
						.content("yep")
						.content("nope")
						.author("Logan Ferree")
						.version("Pre-Alpha")
						.since("Alpha v.0.1")
						.param("String", "something")
						.param("int", "version")
						.returnVal("true if true")
						.throwsInfo("IllegalStateException", "somethingElse")
						.throwsInfo("IllegalArgumentException", "why not?")
						.build()));
	}
	
	@Test
	public void testAssertJavadocEqualsNone(){
		assertJavadocEquals(
				EditableJavadoc.builder()
						.build(),
				EditableJavadoc.builder()
						.build());
	}
	
	@Test
	public void testAssertJavadocEqualsBothNull(){
		assertJavadocEquals(null, null);
	}
	
	@Test
	public void testAssertJavadocEqualsDoc1NullDoc2Not(){
		Javadoc doc2 = EditableJavadoc.builder()
				.build();
		try{
			assertJavadocEquals(null, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the Javadocs is null, and the other isn't!",
					buildAssertError(null, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsDoc2NullDoc1Not(){
		Javadoc doc1 = EditableJavadoc.builder()
				.build();
		try{
			assertJavadocEquals(doc1, null);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("One of the Javadocs is null, and the other isn't!",
					buildAssertError(doc1, null)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsEditable(){
		Javadoc doc1 = UneditableJavadoc.builder()
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Editable is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsCondensed(){
		Javadoc doc1 = EditableJavadoc.builder()
				.condensed()
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.condensed(false)
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Condensed is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsContent(){
		Javadoc doc1 = EditableJavadoc.builder()
				.content("something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("yep")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content differs on #1!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsContentLength(){
		Javadoc doc1 = EditableJavadoc.builder()
				.content("something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("something")
				.content("yep")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content length is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsContentLengthDoc1Longer(){
		Javadoc doc1 = EditableJavadoc.builder()
				.content("something")
				.content("yep")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.content("something")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Content length is different!\n" +
					"Content differs on #2!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsAuthor(){
		Javadoc doc1 = EditableJavadoc.builder()
				.author("Logan Ferree (Tadukoo)")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.author("Logan Ferree")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Author is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsVersion(){
		Javadoc doc1 = EditableJavadoc.builder()
				.version("Alpha v.0.1")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.version("Pre-Alpha")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Version is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsSince(){
		Javadoc doc1 = EditableJavadoc.builder()
				.since("Pre-Alpha")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.since("Alpha v.0.1")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Since is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsParamsLength(){
		Javadoc doc1 = EditableJavadoc.builder()
				.param("String", "something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.param("String", "something")
				.param("int", "version")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Params length is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsParamsLengthDoc1Longer(){
		Javadoc doc1 = EditableJavadoc.builder()
				.param("String", "something")
				.param("int", "version")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.param("String", "something")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Params length is different!\n" +
					"Params differs on #2!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsReturnVal(){
		Javadoc doc1 = EditableJavadoc.builder()
				.returnVal("null")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.returnVal("true if true")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Return is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsThrowsLength(){
		Javadoc doc1 = EditableJavadoc.builder()
				.throwsInfo("IllegalStateException", "something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.throwsInfo("IllegalStateException", "something")
				.throwsInfo("IllegalArgumentException", "why not?")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Throws length is different!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsThrowsLengthDoc1Longer(){
		Javadoc doc1 = EditableJavadoc.builder()
				.throwsInfo("IllegalStateException", "something")
				.throwsInfo("IllegalArgumentException", "why not?")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.throwsInfo("IllegalStateException", "something")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("Throws length is different!\n" +
					"Throws differs on #2!",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
	
	@Test
	public void testAssertJavadocEqualsAll(){
		Javadoc doc1 = UneditableJavadoc.builder()
				.condensed()
				.content("something")
				.author("Logan Ferree (Tadukoo)")
				.version("Alpha v.0.1")
				.since("Pre-Alpha")
				.param("String", "somethingElse")
				.returnVal("null")
				.throwsInfo("IllegalStateException", "something")
				.build();
		Javadoc doc2 = EditableJavadoc.builder()
				.condensed(false)
				.content("yep")
				.content("nope")
				.author("Logan Ferree")
				.version("Pre-Alpha")
				.since("Alpha v.0.1")
				.param("String", "something")
				.param("int", "version")
				.returnVal("true if true")
				.throwsInfo("IllegalStateException", "somethingElse")
				.throwsInfo("IllegalArgumentException", "why not?")
				.build();
		try{
			assertJavadocEquals(doc1, doc2);
			fail();
		}catch(AssertionFailedError e){
			assertEquals(buildTwoPartError("""
					Editable is different!
					Condensed is different!
					Content length is different!
					Content differs on #1!
					Author is different!
					Version is different!
					Since is different!
					Params length is different!
					Params differs on #1!
					Return is different!
					Throws length is different!
					Throws differs on #1!""",
					buildAssertError(doc1, doc2)), e.getMessage());
		}
	}
}
