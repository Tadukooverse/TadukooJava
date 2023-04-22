package com.github.tadukoo.java.method;

import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a method in Java that is not modifiable
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (as JavaMethod), Alpha v.0.4 (as UneditableJavaMethod)
 */
public class UneditableJavaMethod extends JavaMethod{
	
	/**
	 * A builder used to make an {@link UneditableJavaMethod}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.5
	 * @since Alpha v.0.4
	 * @see JavaMethodBuilder
	 */
	public static class UneditableJavaMethodBuilder extends JavaMethodBuilder<UneditableJavaMethod>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaMethod} */
		private UneditableJavaMethodBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// javadoc can't be editable
			if(javadoc != null && javadoc.isEditable()){
				errors.add("javadoc is not uneditable in this uneditable JavaMethod");
			}
			
			// annotations can't be editable
			for(JavaAnnotation annotation: annotations){
				if(annotation.isEditable()){
					errors.add("some annotations are not uneditable in this uneditable JavaMethod");
					break;
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaMethod constructMethod(){
			return new UneditableJavaMethod(javadoc, annotations,
					visibility, isStatic, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param javadoc The {@link Javadoc} on the method
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @param visibility The {@link Visibility} of the method
	 * @param isStatic Whether the method is static or not
	 * @param returnType The return type of the method
	 * @param name The name of the method
	 * @param parameters The parameters used in the method - pairs of type, then name
	 * @param throwTypes The types that can be thrown by the method
	 * @param lines The actual lines of code in the method
	 */
	private UneditableJavaMethod(
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, String returnType, String name,
			List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
		super(false, javadoc, annotations,
				visibility, isStatic, returnType, name,
				parameters, throwTypes, lines);
	}
	
	/**
	 * @return A new {@link UneditableJavaMethodBuilder} to use to build a new {@link UneditableJavaMethod}
	 */
	public static UneditableJavaMethodBuilder builder(){
		return new UneditableJavaMethodBuilder();
	}
}
