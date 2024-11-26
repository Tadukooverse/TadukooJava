package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class CharacterUtilTest extends JavaClassParsingTest{
	
	public CharacterUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				/**
				 * Util functions for dealing with Characters.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Alpha v.0.3.1
				 */
				public final class CharacterUtil{
				\t
					/** Not allowed to create a CharacterUtil */
					private CharacterUtil(){ }
				\t
					/**
					 * @param c The character to be checked
					 * @return true if the given character is a capital letter, false otherwise
					 */
					public static boolean isUpperCase(char c){
						return c >= 'A' && c <= 'Z';
					}
				\t
					/**
					 * @param c The character to be checked
					 * @return true if the given character is a lowercase letter, false otherwise
					 */
					public static boolean isLowerCase(char c){
						return c >= 'a' && c <= 'z';
					}
				\t
					/**
					 * @param c The character to be checked
					 * @return true if the given character is a letter, false otherwise
					 */
					public static boolean isLetter(char c){
						return isUpperCase(c) || isLowerCase(c);
					}
				\t
					/**
					 * @param c The character to be checked
					 * @return true if the given character is a number, false otherwise
					 */
					public static boolean isNumber(char c){
						return c >= '0' && c <= '9';
					}
				\t
					/**
					 * @param c The character to be capitalized
					 * @return The capital version of the given character, or the character itself if it's not a letter
					 */
					public static char toUpperCase(char c){
						if(isLowerCase(c)){
							return (char) (c - 32);
						}else{
							return c;
						}
					}
				\t
					/**
					 * @param c The character to change to lowercase
					 * @return The lowercase version of the given character, or the character itself if it's not a letter
					 */
					public static char toLowerCase(char c){
						if(isUpperCase(c)){
							return (char) (c + 32);
						}else{
							return c;
						}
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with Characters.")
						.author("Logan Ferree (Tadukoo)")
						.version("Alpha v.0.3.1")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("CharacterUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create a CharacterUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("CharacterUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to be checked")
								.returnVal("true if the given character is a capital letter, false otherwise")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isUpperCase")
						.parameter("char", "c")
						.line("return c >= 'A' && c <= 'Z';")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to be checked")
								.returnVal("true if the given character is a lowercase letter, false otherwise")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isLowerCase")
						.parameter("char", "c")
						.line("return c >= 'a' && c <= 'z';")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to be checked")
								.returnVal("true if the given character is a letter, false otherwise")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isLetter")
						.parameter("char", "c")
						.line("return isUpperCase(c) || isLowerCase(c);")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to be checked")
								.returnVal("true if the given character is a number, false otherwise")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("boolean")
						.name("isNumber")
						.parameter("char", "c")
						.line("return c >= '0' && c <= '9';")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to be capitalized")
								.returnVal("The capital version of the given character, or the character itself if it's not a letter")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("char")
						.name("toUpperCase")
						.parameter("char", "c")
						.line("if(isLowerCase(c)){")
						.line("\treturn (char) (c - 32);")
						.line("}else{")
						.line("\treturn c;")
						.line("}")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.param("c", "The character to change to lowercase")
								.returnVal("The lowercase version of the given character, or the character itself if it's not a letter")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("char")
						.name("toLowerCase")
						.parameter("char", "c")
						.line("if(isUpperCase(c)){")
						.line("\treturn (char) (c + 32);")
						.line("}else{")
						.line("\treturn c;")
						.line("}")
						.build())
				.build());
	}
}
