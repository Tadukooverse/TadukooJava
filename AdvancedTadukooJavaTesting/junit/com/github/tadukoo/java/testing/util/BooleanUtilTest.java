package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class BooleanUtilTest extends JavaClassParsingTest{
	
	public BooleanUtilTest(){
		super("""
			package com.github.tadukoo.util;
			
			/**
			 * Util functions for dealing with Booleans.
			 *\s
			 * @author Logan Ferree (Tadukoo)
			 * @version 0.1-Alpha-SNAPSHOT
			 */
			public final class BooleanUtil{
			\t
				/** Not allowed to create a BooleanUtil */
				private BooleanUtil(){ }
			\t
				/**
				 * Checks that the given Boolean is true, properly handling cases of
				 * null as false (meaning returning a false result).
				 *\s
				 * @param bool The Boolean to check
				 * @return If the Boolean is true
				 */
				public static boolean isTrue(Boolean bool){
					return bool != null && bool;
				}
			\t
				/**
				 * Checks that the given Boolean is false, properly handling cases of
				 * null as false (meaning returning a false result).
				 *\s
				 * @param bool The Boolean to check
				 * @return If the Boolean is false
				 */
				public static boolean isFalse(Boolean bool){
					return bool != null && !bool;
				}
			\t
				/**
				 * Checks that the given Boolean is not true, meaning that a Boolean
				 * that's false or a null Boolean will both return a true result.
				 *\s
				 * @param bool The Boolean to check
				 * @return If the Boolean is not true
				 */
				public static boolean isNotTrue(Boolean bool){
					return bool == null || !bool;
				}
			\t
				/**
				 * Checks that the given Boolean is not false, meaning that a Boolean
				 * that's true or a null Boolean will both return a true result.
				 *\s
				 * @param bool The Boolean to check
				 * @return If the Boolean is not false
				 */
				public static boolean isNotFalse(Boolean bool){
					return bool == null || bool;
				}
			}
			""",
				EditableJavaClass.builder()
						.packageDeclaration(EditableJavaPackageDeclaration.builder()
								.packageName("com.github.tadukoo.util")
								.build())
						.javadoc(EditableJavadoc.builder()
								.content("Util functions for dealing with Booleans.")
								.author("Logan Ferree (Tadukoo)")
								.version("0.1-Alpha-SNAPSHOT")
								.build())
						.visibility(Visibility.PUBLIC)
						.isFinal()
						.className("BooleanUtil")
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.condensed()
										.content("Not allowed to create a BooleanUtil")
										.build())
								.visibility(Visibility.PRIVATE)
								.returnType("BooleanUtil").name("")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Checks that the given Boolean is true, properly handling cases of")
										.content("null as false (meaning returning a false result).")
										.param("bool", "The Boolean to check")
										.returnVal("If the Boolean is true")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("boolean").name("isTrue")
								.parameter("Boolean", "bool")
								.line("return bool != null && bool;")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Checks that the given Boolean is false, properly handling cases of")
										.content("null as false (meaning returning a false result).")
										.param("bool", "The Boolean to check")
										.returnVal("If the Boolean is false")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("boolean").name("isFalse")
								.parameter("Boolean", "bool")
								.line("return bool != null && !bool;")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Checks that the given Boolean is not true, meaning that a Boolean")
										.content("that's false or a null Boolean will both return a true result.")
										.param("bool", "The Boolean to check")
										.returnVal("If the Boolean is not true")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("boolean").name("isNotTrue")
								.parameter("Boolean" , "bool")
								.line("return bool == null || !bool;")
								.build())
						.method(EditableJavaMethod.builder()
								.javadoc(EditableJavadoc.builder()
										.content("Checks that the given Boolean is not false, meaning that a Boolean")
										.content("that's true or a null Boolean will both return a true result.")
										.param("bool", "The Boolean to check")
										.returnVal("If the Boolean is not false")
										.build())
								.visibility(Visibility.PUBLIC)
								.isStatic()
								.returnType("boolean").name("isNotFalse")
								.parameter("Boolean" , "bool")
								.line("return bool == null || bool;")
								.build())
						.build());
	}
}
