package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaField;
import com.github.tadukoo.java.JavaFieldBuilder;
import com.github.tadukoo.java.Javadoc;
import com.github.tadukoo.java.Visibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a field in Java that is not modifiable
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.2 (as JavaField), Alpha v.0.4 (as UneditableJavaField)
 */
public class UneditableJavaField extends JavaField{
	
	/**
	 * A builder used to make an {@link UneditableJavaField}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaFieldBuilder
	 */
	public static class UneditableJavaFieldBuilder extends JavaFieldBuilder<UneditableJavaField>{
		
		/** Not allowed to instantiate outside {@link UneditableJavaField} */
		private UneditableJavaFieldBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// javadoc must be uneditable too
			if(javadoc != null && javadoc.isEditable()){
				errors.add("javadoc is not uneditable in this uneditable JavaField");
			}
			
			// annotations must be uneditable too
			for(JavaAnnotation annotation: annotations){
				if(annotation.isEditable()){
					errors.add("some annotations are not uneditable in this uneditable JavaField");
					break;
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavaField constructField(){
			return new UneditableJavaField(sectionComment, javadoc, annotations,
					visibility, isStatic, isFinal,
					type, name, value);
		}
	}
	
	/**
	 * Constructs a Java Field with the given parameters
	 *
	 * @param sectionComment The section comment above the field
	 * @param javadoc The {@link Javadoc} on the field
	 * @param annotations The {@link JavaAnnotation annotations} on the field
	 * @param visibility The {@link Visibility} of the field
	 * @param isStatic Whether the field is static or not
	 * @param isFinal Whether the field is final or not
	 * @param type The type of the field
	 * @param name The name of the field
	 * @param value The value assigned to the field
	 */
	private UneditableJavaField(
			String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal,
			String type, String name, String value){
		super(false, sectionComment, javadoc, annotations,
				visibility, isStatic, isFinal,
				type, name, value);
	}
	
	/**
	 * @return A new {@link UneditableJavaFieldBuilder} to use to build a new {@link UneditableJavaField}
	 */
	public static UneditableJavaFieldBuilder builder(){
		return new UneditableJavaFieldBuilder();
	}
}
