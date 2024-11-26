package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class AutoCloseableUtilTest extends JavaClassParsingTest{
	
	public AutoCloseableUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				/**
				 * Util functions for dealing with AutoCloseables.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version 0.1-Alpha-SNAPSHOT
				 */
				public final class AutoCloseableUtil{
				\t
					/** Not allowed to create an AutoCloseableUtil */
					private AutoCloseableUtil(){ }
				\t
					/**
					 * Closes the given AutoCloseable while suppressing any error messages
					 *\s
					 * @param autoCloseable The AutoCloseable to close
					 */
					public static void closeQuietly(AutoCloseable autoCloseable){
						if(autoCloseable != null){
							try{
								autoCloseable.close();
							}catch(Exception ignored){
							\t
							}
						}
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with AutoCloseables.")
						.author("Logan Ferree (Tadukoo)")
						.version("0.1-Alpha-SNAPSHOT")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("AutoCloseableUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create an AutoCloseableUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("AutoCloseableUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Closes the given AutoCloseable while suppressing any error messages")
								.param("autoCloseable", "The AutoCloseable to close")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("void")
						.name("closeQuietly")
						.parameter("AutoCloseable", "autoCloseable")
						.line("if(autoCloseable != null){")
						.line("\ttry{")
						.line("\t\tautoCloseable.close();")
						.line("\t}catch(Exception ignored){")
						.line("\t\t")
						.line("\t}")
						.line("}")
						.build())
				.build());
	}
}
