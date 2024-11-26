package com.github.tadukoo.java.parsing.comment;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.parsing.BaseJavaParserTest;
import com.github.tadukoo.java.parsing.JavaParsingException;
import com.github.tadukoo.util.functional.function.ThrowingFunction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class BaseJavadocParserTest extends BaseJavaParserTest{
	
	private final ThrowingFunction<String, Javadoc, JavaParsingException> parseMethod;
	
	protected BaseJavadocParserTest(ThrowingFunction<String, Javadoc, JavaParsingException> parseMethod){
		this.parseMethod = parseMethod;
	}
	
	/*
	 * Standard Cases
	 */
	
	@Test
	public void testSimpleCondensed() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
	public void testCondensedWithThrows() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/** @throws Exception Because I can */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.throwsInfo("Exception", "Because I can")
						.build(),
				doc);
		assertEquals("""
				/** @throws Exception Because I can */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithThrows() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**
				 * @throws Exception Because I can
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.throwsInfo("Exception", "Because I can")
						.build(),
				doc);
		assertEquals("""
				/**
				 * @throws Exception Because I can
				 */""", doc.toString());
	}
	
	@Test
	public void testCondensedWithEverything() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/** some content
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building
				 * @throws Exception Because I can */""");
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
						.throwsInfo("Exception", "Because I can")
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
				 * @return this, to continue building
				 * @throws Exception Because I can */""", doc.toString());
	}
	
	@Test
	public void testNotCondensedWithEverything() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**
				 * some content
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.5
				 * @since Alpha v.0.1
				 * @param type The type
				 * @param derp The int
				 * @return this, to continue building
				 * @throws Exception Because I can
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
						.throwsInfo("Exception", "Because I can")
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
				 * @throws Exception Because I can
				 */""", doc.toString());
	}
	
	/*
	 * Oddball Cases
	 */
	
	@Test
	public void testUnknownAnnotationCondensed() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
	public void testBlankContentLine() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**
				 * something
				 *\s
				 * something else
				 */""");
		// Note that we ignore the blank line - this is partly because Javadoc will ignore it anyway
		// (because of HTML rules), though we do not mimic Javadoc removing the newline between the 2 lines of content
		assertEquals(
				EditableJavadoc.builder()
						.content("something")
						.content("something else")
						.build(),
				doc);
		assertEquals("""
				/**
				 * something
				 * something else
				 */""", doc.toString());
	}
	
	@Test
	public void testStartTokenInContent() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**
				 * /**
				 */""");
		assertEquals(
				EditableJavadoc.builder()
						.content("/**")
						.build(),
				doc);
		assertEquals("""
				/**
				 * /**
				 */""", doc.toString());
	}
	
	@Test
	public void testInheritDoc() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/** {@inheritDoc} */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("{@inheritDoc}")
						.build(),
				doc);
		assertEquals("/** {@inheritDoc} */", doc.toString());
	}
	
	@Test
	public void testBracedContent() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/** {content} */""");
		assertEquals(
				EditableJavadoc.builder()
						.condensed()
						.content("{content}")
						.build(),
				doc);
		assertEquals("/** {content} */", doc.toString());
	}
	
	/*
	 * Whitespace Cases
	 */
	
	@Test
	public void testContentRightAfterStartToken() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
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
		Javadoc doc = parseMethod.apply("""
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
	public void testLeadingWhitespace() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				\t     \t   \t
				\t    \t  \t
				\t   /**
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
	public void testTrailingWhitespace() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**
				 * some content
				 */   \t
				 \t     \t   \t
				\t    \t  \t
				\t   \s""");
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
	
	/*
	 * Error Cases
	 */
	
	@Test
	public void testDoubleAuthors(){
		try{
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
			parseMethod.apply("""
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
	
	/*
	 * Issue Cases
	 */
	
	/*
	 * This exists because when running the parser code in @UltimatePower, the Javadoc was considered condensed.
	 * It has to do with Windows' /r/n usage for newlines, which the \r was treated as a character before the newline,
	 * meaning the Javadoc content started with it
	 */
	@Test
	public void testLabelFormFieldIssue() throws JavaParsingException{
		Javadoc doc = parseMethod.apply("""
				/**\r
				 * Represents a {@link FormField} for a Label
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				""");
		assertEquals(
				EditableJavadoc.builder()
						.content("Represents a {@link FormField} for a Label")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.4")
						.build(),
				doc);
		assertEquals("""
				/**
				 * Represents a {@link FormField} for a Label
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */""",
				doc.toString());
	}
}
