package com.github.tadukoo.java.testing.util;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class ExceptionUtilTest extends JavaClassParsingTest{
	
	public ExceptionUtilTest(){
		super("""
				package com.github.tadukoo.util;
				
				import java.io.PrintWriter;
				import java.io.StringWriter;
				
				/**
				 * Util functions for dealing with {@link Throwable Throwables} and {@link Exception Exceptions}.
				 * <br><br>
				 * It is named ExceptionUtil because it's more common to think of the term Exception than Throwable,
				 * though it does work for both.
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Pre-Alpha
				 */
				public final class ExceptionUtil{
				\t
					/** Not allowed to create an ExceptionUtil */
					private ExceptionUtil(){ }
				\t
					/**
					 * Returns a String of the stack trace for the given {@link Throwable}.
					 * The String will be the equivalent of calling {@link Throwable#printStackTrace()}.
					 *\s
					 * @param t The Throwable to get the stack trace of
					 * @return A String of the stack trace
					 */
					public static String getStackTraceAsString(Throwable t){
						// Setup a StringWriter to write the stack trace to
						StringWriter sw = new StringWriter();
						// Create PrintWriter for the StringWriter so it can actually be written to
						PrintWriter pw = new PrintWriter(sw);
						// Print the stack trace to (ultimately) the string writer
						t.printStackTrace(pw);
					\t
						// Return the written stack trace string
						return sw.toString();
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util")
				.importName("java.io.PrintWriter", false)
				.importName("java.io.StringWriter", false)
				.javadoc(EditableJavadoc.builder()
						.content("Util functions for dealing with {@link Throwable Throwables} and {@link Exception Exceptions}.")
						.content("<br><br>")
						.content("It is named ExceptionUtil because it's more common to think of the term Exception than Throwable,")
						.content("though it does work for both.")
						.author("Logan Ferree (Tadukoo)")
						.version("Pre-Alpha")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("ExceptionUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to create an ExceptionUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("ExceptionUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Returns a String of the stack trace for the given {@link Throwable}.")
								.content("The String will be the equivalent of calling {@link Throwable#printStackTrace()}.")
								.param("t", "The Throwable to get the stack trace of")
								.returnVal("A String of the stack trace")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("getStackTraceAsString")
						.parameter("Throwable t")
						.line("// Setup a StringWriter to write the stack trace to")
						.line("StringWriter sw = new StringWriter();")
						.line("// Create PrintWriter for the StringWriter so it can actually be written to")
						.line("PrintWriter pw = new PrintWriter(sw);")
						.line("// Print the stack trace to (ultimately) the string writer")
						.line("t.printStackTrace(pw);")
						.line("")
						.line("// Return the written stack trace string")
						.line("return sw.toString();")
						.build())
				.build());
	}
}
