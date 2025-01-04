package com.github.tadukoo.java.testing.util.dictionary;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ISO_LATIN1DictionaryTest extends JavaClassParsingTest{
	
	public ISO_LATIN1DictionaryTest(){
		super("""
				package com.github.tadukoo.util.dictionary;
				
				import java.nio.charset.Charset;
				import java.nio.charset.StandardCharsets;
				import java.util.Map;
				
				/**
				 * Basic {@link Dictionary} class that supports the {@link StandardCharsets#ISO_8859_1 ISO-LATIN-1/ISO_8859_1} Charset.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				public class ISO_LATIN1Dictionary extends AbstractDictionary{
				\t
					/**
					 * Constructs a new ISO_LATIN_1 Dictionary that's empty
					 */
					public ISO_LATIN1Dictionary(){
						super();
					}
				\t
					/**
					 * Constructs a new ISO_LATIN_1 Dictionary that contains the words in the given Map
					 *\s
					 * @param map The Map containing valid words for this Dictionary
					 */
					public ISO_LATIN1Dictionary(Map<Character, Map<Character, ?>> map){
						super(map);
					}
				\t
					/** {@inheritDoc} */
					@Override
					public Charset supportedCharset(){
						return StandardCharsets.ISO_8859_1;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.dictionary")
				.importName("java.nio.charset.Charset", false)
				.importName("java.nio.charset.StandardCharsets", false)
				.importName("java.util.Map", false)
				.javadoc(EditableJavadoc.builder()
						.content("Basic {@link Dictionary} class that supports the {@link StandardCharsets#ISO_8859_1 ISO-LATIN-1/ISO_8859_1} Charset.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.4")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("ISO_LATIN1Dictionary")
				.superClassName("AbstractDictionary")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new ISO_LATIN_1 Dictionary that's empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("ISO_LATIN1Dictionary")
						.line("super();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new ISO_LATIN_1 Dictionary that contains the words in the given Map")
								.param("map", "The Map containing valid words for this Dictionary")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("ISO_LATIN1Dictionary")
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
						.line("return StandardCharsets.ISO_8859_1;")
						.build())
				.build());
	}
}
