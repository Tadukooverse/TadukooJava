package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavadocParserTest extends BaseJavaParserTest{
	
	@Test
	public void testSimpleCondensed() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.build(),
				doc);
		assertEquals("""
				/** */""", doc.toString());
	}
	
	@Test
	public void testSimpleNotCondensed() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.build(),
				doc);
		assertEquals("""
				/**
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithContent() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** some content */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("some content")
						.build(),
				doc);
		assertEquals("""
				/** some content */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithContent() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * some content
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.content("some content")
						.build(),
				doc);
		assertEquals("""
				/**
				 * some content
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithAuthor() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @author Logan Ferree (Tadukoo) */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.author("Logan Ferree (Tadukoo)")
						.build(),
				doc);
		assertEquals("""
				/** @author Logan Ferree (Tadukoo) */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithAuthor() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @author Logan Ferree (Tadukoo)
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.author("Logan Ferree (Tadukoo)")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @author Logan Ferree (Tadukoo)
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithVersion() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @version Beta v.0.5 */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.version("Beta v.0.5")
						.build(),
				doc);
		assertEquals("""
				/** @version Beta v.0.5 */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithVersion() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @version Beta v.0.5
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.version("Beta v.0.5")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @version Beta v.0.5
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithSince() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @since Alpha v.0.1 */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.since("Alpha v.0.1")
						.build(),
				doc);
		assertEquals("""
				/** @since Alpha v.0.1 */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithSince() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @since Alpha v.0.1
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.since("Alpha v.0.1")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @since Alpha v.0.1
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithParam() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @param type The type */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.param("type", "The type")
						.build(),
				doc);
		assertEquals("""
				/** @param type The type */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithParam() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @param type The type
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.param("type", "The type")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @param type The type
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithReturn() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @return this, to continue building */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.returnVal("this, to continue building")
						.build(),
				doc);
		assertEquals("""
				/** @return this, to continue building */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithReturn() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @return this, to continue building
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.returnVal("this, to continue building")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @return this, to continue building
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithEverything() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** some content
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("some content")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.5")
						.since("Alpha v.0.1")
						.param("type", "The type")
						.param("derp", "The int")
						.returnVal("this, to continue building")
						.build(),
				doc);
		assertEquals("""
				/** some content
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 *\s
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithEverything() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * some content
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.content("some content")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.5")
						.since("Alpha v.0.1")
						.param("type", "The type")
						.param("derp", "The int")
						.returnVal("this, to continue building")
						.build(),
				doc);
		assertEquals("""
				/**
				 * some content
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 *\s
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building
				 */""", doc.toString());
	}
	
	@Test
	public void testUnknownAnnotationCondensed() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/** @garbage something */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("@garbage something")
						.build(),
				doc);
		assertEquals("""
				/** @garbage something */""", doc.toString());
	}
	
	@Test
	public void testUnknownAnnotationNotCondensed() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**
				 * @garbage something
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.content("@garbage something")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @garbage something
				 */""", doc.toString());
	}
	
	@Test
	public void testContentRightAfterStartToken() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**some content */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("some content")
						.build(),
				doc);
		assertEquals("""
				/** some content */""", doc.toString());
	}
	
	@Test
	public void testAnnotationRightAfterStartToken() throws JavaParsingException{
		Javadoc doc = runFullParserForJavadoc("""
				/**@author Logan Ferree (Tadukoo) */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.author("Logan Ferree (Tadukoo)")
						.build(),
				doc);
		assertEquals("""
				/** @author Logan Ferree (Tadukoo) */""", doc.toString());
	}
	
	@Test
	public void testDoubleAuthors(){
		try{
			runFullParserForJavadoc("""
					/** @author Logan Ferree (Tadukoo)
					 * @author Shea */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
					"Found multiple author strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTripleAuthors(){
		try{
			runFullParserForJavadoc("""
					/** @author Logan Ferree (Tadukoo)
					 * @author Shea
					 * @author Zack */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple author strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testDoubleVersions(){
		try{
			runFullParserForJavadoc("""
					/** @version Beta v.0.5
					 * @version Beta v.0.4 */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple version strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTripleVersions(){
		try{
			runFullParserForJavadoc("""
					/** @version Beta v.0.5
					 * @version Beta v.0.4
					 * @version Alpha v.0.1 */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple version strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testDoubleSince(){
		try{
			runFullParserForJavadoc("""
					/** @since Beta v.0.5
					 * @since Beta v.0.4 */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple since strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTripleSince(){
		try{
			runFullParserForJavadoc("""
					/** @since Beta v.0.5
					 * @since Beta v.0.4
					 * @since Alpha v.0.1 */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple since strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testDoubleReturns(){
		try{
			runFullParserForJavadoc("""
					/** @return this, to continue building
					 * @return The type */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple return strings"),
					e.getMessage());
		}
	}
	
	@Test
	public void testTripleReturns(){
		try{
			runFullParserForJavadoc("""
					/** @return this, to continue building
					 * @return The type
					 * @return I don't even know */""");
			fail();
		}catch(JavaParsingException e){
			assertEquals(buildJavaParsingExceptionMessage(JavaCodeTypes.JAVADOC,
							"Found multiple return strings"),
					e.getMessage());
		}
	}
}
