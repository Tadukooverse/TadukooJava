package com.github.tadukoo.java.testing;

import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.parsing.JavaParsingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * A class used to test a Java Class in full by parsing it and checking it parsed right and checking that
 * we get back the original class code when we make it back into a String.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Beta v.0.5
 */
public abstract class JavaClassParsingTest extends JavaCodeTypeTest{
	
	/** The text of the Java class to be parsed */
	private final String javaClassCode;
	/** The expected {@link JavaClass} after parsing the code */
	private final JavaClass expectedClass;
	/** The actual {@link JavaClass} that gets parsed from the code */
	private JavaClass clazz;
	
	/**
	 * Constructs a new {@link JavaClassParsingTest} using the given class code and expected {@link JavaClass}
	 *
	 * @param javaClassCode The text of the Java class to be parsed
	 * @param expectedClass The expected {@link JavaClass} after parsing the code
	 */
	protected JavaClassParsingTest(String javaClassCode, JavaClass expectedClass){
		this.javaClassCode = javaClassCode;
		this.expectedClass = expectedClass;
	}
	
	/**
	 * Parses the {@code javaClassCode} into a {@link JavaClass} before running unit tests
	 *
	 * @throws JavaParsingException If anything goes wrong during parsing
	 */
	@BeforeEach
	public void setup() throws JavaParsingException{
		clazz = runParserForClass(javaClassCode);
	}
	
	/**
	 * Tests that we get the {@code javaClassCode} back when we use toString
	 * on the {@link JavaClass} we parsed from the code
	 */
	@Test
	public void testRoundTrip(){
		assertEquals(javaClassCode, clazz.toString());
	}
	
	/**
	 * Tests that the {@link JavaClass} we parsed from the code is the {@link JavaClass} we're expecting
	 */
	@Test
	public void testPojos(){
		assertClassEquals(expectedClass, clazz);
	}
}
