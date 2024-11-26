package com.github.tadukoo.java.testing.util.stack;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class StackUtilTest extends JavaClassParsingTest{
	
	public StackUtilTest(){
		super("""
				package com.github.tadukoo.util.stack;
				
				/**
				 * Stack Util is used for figuring out information from the stack
				 *\s
				 * @author Logan Ferree (Tadukoo)
				 * @version Beta v.0.6
				 */
				public final class StackUtil{
				\t
					/** Not allowed to instantiate StackUtil */
					private StackUtil(){ }
				\t
					/**
					 * Figures out the canonical class name of the class that called the method that called this method
					 * (so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called
					 * StackUtilTest's method)
					 *\s
					 * @return The canonical class name of the calling class
					 */
					public static String getCallingClassName(){
						StackTraceElement[] elements = Thread.currentThread().getStackTrace();
						StackTraceElement element = elements[3];
						return element.getClassName();
					}
				\t
					/**
					 * Figures out the class that called the method that called this method
					 * (so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called
					 * StackUtilTest's method)
					 *\s
					 * @return The calling class
					 * @throws ClassNotFoundException If the class is not found
					 */
					public static Class<?> getCallingClass() throws ClassNotFoundException{
						StackTraceElement[] elements = Thread.currentThread().getStackTrace();
						StackTraceElement element = elements[3];
						return StackUtil.class.getClassLoader().loadClass(element.getClassName());
					}
				\t
					/**
					 * Figures out the method name of the class that called the method that called this method
					 * (so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called
					 * StackUtilTest's method)
					 *\s
					 * @return The method name of the calling method
					 */
					public static String getCallingMethodName(){
						StackTraceElement[] elements = Thread.currentThread().getStackTrace();
						StackTraceElement element = elements[3];
						return element.getMethodName();
					}
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.stack")
				.javadoc(EditableJavadoc.builder()
						.content("Stack Util is used for figuring out information from the stack")
						.author("Logan Ferree (Tadukoo)")
						.version("Beta v.0.6")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("StackUtil")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Not allowed to instantiate StackUtil")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("StackUtil")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Figures out the canonical class name of the class that called the method that called this method")
								.content("(so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called")
								.content("StackUtilTest's method)")
								.returnVal("The canonical class name of the calling class")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("getCallingClassName")
						.line("StackTraceElement[] elements = Thread.currentThread().getStackTrace();")
						.line("StackTraceElement element = elements[3];")
						.line("return element.getClassName();")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Figures out the class that called the method that called this method")
								.content("(so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called")
								.content("StackUtilTest's method)")
								.returnVal("The calling class")
								.throwsInfo("ClassNotFoundException", "If the class is not found")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("Class<?>")
						.name("getCallingClass")
						.throwType("ClassNotFoundException")
						.line("StackTraceElement[] elements = Thread.currentThread().getStackTrace();")
						.line("StackTraceElement element = elements[3];")
						.line("return StackUtil.class.getClassLoader().loadClass(element.getClassName());")
						.build())
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.content("Figures out the method name of the class that called the method that called this method")
								.content("(so e.g. if StackUtilTest.testGetCallingClassName calls this method, it'll return whatever called")
								.content("StackUtilTest's method)")
								.returnVal("The method name of the calling method")
								.build())
						.visibility(Visibility.PUBLIC)
						.isStatic()
						.returnType("String")
						.name("getCallingMethodName")
						.line("StackTraceElement[] elements = Thread.currentThread().getStackTrace();")
						.line("StackTraceElement element = elements[3];")
						.line("return element.getMethodName();")
						.build())
				.build());
	}
}
