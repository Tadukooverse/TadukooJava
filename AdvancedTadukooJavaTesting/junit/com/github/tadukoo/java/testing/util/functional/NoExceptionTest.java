package com.github.tadukoo.java.testing.util.functional;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.javaclass.EditableJavaClass;
import com.github.tadukoo.java.javadoc.EditableJavadoc;
import com.github.tadukoo.java.method.EditableJavaMethod;
import com.github.tadukoo.java.testing.JavaClassParsingTest;

public class NoExceptionTest extends JavaClassParsingTest{
	
	public NoExceptionTest(){
		super("""
				package com.github.tadukoo.util.functional;
				
				/**
				 * To be used as a {@link Throwable} parameter in Throwing Functional Interfaces
				 * that are not meant to throw anything.
				 */
				public final class NoException extends RuntimeException{
				\t
					/** Can't actually create a NoException, as you're not supposed to */
					private NoException(){ }
				}
				""", EditableJavaClass.builder()
				.packageName("com.github.tadukoo.util.functional")
				.javadoc(EditableJavadoc.builder()
						.content("To be used as a {@link Throwable} parameter in Throwing Functional Interfaces")
						.content("that are not meant to throw anything.")
						.build())
				.visibility(Visibility.PUBLIC)
				.isFinal()
				.className("NoException")
				.superClassName("RuntimeException")
				.method(EditableJavaMethod.builder()
						.javadoc(EditableJavadoc.builder()
								.condensed()
								.content("Can't actually create a NoException, as you're not supposed to")
								.build())
						.visibility(Visibility.PRIVATE)
						.returnType("NoException")
						.build())
				.build());
	}
}
