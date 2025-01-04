package com.github.tadukoo.java.testing.util.dictionary;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.EditableJavaAnnotation;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class UTF16BEDictionaryTest extends JavaClassParsingTest{
	
	public UTF16BEDictionaryTest(){
		super("""
				package com.github.tadukoo.util.dictionary;
				
				import java.nio.charset.Charset;
				import java.nio.charset.StandardCharsets;
				import java.util.Map;
				
				/**
				 * Basic {@link Dictionary} class that supports the {@link StandardCharsets#UTF_16BE UTF-16BE} Charset.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.4
				 */
				public class UTF16BEDictionary extends AbstractDictionary{
				\t
					/**
					 * Constructs a new UTF-16BE Dictionary that's empty
					 */
					public UTF16BEDictionary(){
						super();
					}
				\t
					/**
					 * Constructs a new UTF-16BE Dictionary that contains the words in the given Map
					 *\s
					 * @param map The Map containing valid words for this Dictionary
					 */
					public UTF16BEDictionary(Map<Character, Map<Character, ?>> map){
						super(map);
					}
				\t
					/** {@inheritDoc} */
					@Override
					public Charset supportedCharset(){
						return StandardCharsets.UTF_16BE;
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.dictionary")
				.importName("java.nio.charset.Charset", false)
				.importName("java.nio.charset.StandardCharsets", false)
				.importName("java.util.Map", false)
				.javadoc(EditableJavadoc.builder()
						.content("Basic {@link Dictionary} class that supports the {@link StandardCharsets#UTF_16BE UTF-16BE} Charset.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.4")
						.build())
				.visibility(Visibility.PUBLIC)
				.className("UTF16BEDictionary")
				.superClassName("AbstractDictionary")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new UTF-16BE Dictionary that's empty")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("UTF16BEDictionary")
						.line("super();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Constructs a new UTF-16BE Dictionary that contains the words in the given Map")
								.param("map", "The Map containing valid words for this Dictionary")
								.build())
						.visibility(Visibility.PUBLIC)
						.returnType("UTF16BEDictionary")
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
						.line("return StandardCharsets.UTF_16BE;")
						.build())
				.build());
	}
}
