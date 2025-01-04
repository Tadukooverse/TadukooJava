package com.github.tadukoo.java.testing.util.dictionary;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class US_ASCIIDictionaryTest extends JavaClassParsingTest{
	
	public US_ASCIIDictionaryTest(){
		super("""
				package com.github.tadukoo.util.dictionary;
				
				import java.nio.charset.Charset;
				import java.nio.charset.StandardCharsets;
				import java.util.Map;
				
				/**
				 * Basic {@link Dictionary} class that supports the {@link StandardCharsets#US_ASCII US_ASCII} Charset.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				public class US_ASCIIDictionary extends AbstractDictionary{
				\t
					/**
					 * Constructs a new US_ASCII Dictionary that's empty
					 */
					public US_ASCIIDictionary(){
						super();
					}
				\t
					/**
					 * Constructs a new US_ASCII Dictionary that contains the words in the given Map
					 *\s
					 * @param map The Map containing valid words for this Dictionary
					 */
					public US_ASCIIDictionary(Map<Character, Map<Character, ?>> map){
						super(map);
					}
				\t
					/** {@inheritDoc} */
					@Override
					public Charset supportedCharset(){
						return StandardCharsets.US_ASCII;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.dictionary")
				.importName("java.nio.charset.Charset", false)
				.importName("java.nio.charset.StandardCharsets", false)
				.importName("java.util.Map", false)
				.javadoc(EditableJavadoc.builder()
						.content("Basic {@link Dictionary} class that supports the {@link StandardCharsets#US_ASCII US_ASCII} Charset.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.4")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("US_ASCIIDictionary")
				.superClassName("AbstractDictionary")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new US_ASCII Dictionary that's empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("US_ASCIIDictionary")
						.line("super();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new US_ASCII Dictionary that contains the words in the given Map")
								.param("map", "The Map containing valid words for this Dictionary")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("US_ASCIIDictionary")
						.parameter("Map<Character, Map<Character, ?>> map")
						.line("super(map);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("{@inheritDoc}")
								.build())
						.annotation(EditableJavaAnnotation.builder()
								.name("Override")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("Charset")
						.name("supportedCharset")
						.line("return StandardCharsets.US_ASCII;")
						.build())
				.build());
	}
}
