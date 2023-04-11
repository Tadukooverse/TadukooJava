package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaField;
import com.github.tadukoo.java.JavaFieldBuilder;
import com.github.tadukoo.java.Javadoc;
import com.github.tadukoo.java.Visibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a field in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class EditableJavaField extends JavaField{
	
	/**
	 * A builder used to make an {@link EditableJavaField}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaFieldBuilder
	 */
	public static class EditableJavaFieldBuilder extends JavaFieldBuilder<EditableJavaField>{
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// javadoc must be editable too
			if(javadoc != null && !javadoc.isEditable()){
				errors.add("javadoc is not editable in this editable JavaField");
			}
			
			// annotations must be editable too
			for(JavaAnnotation annotation: annotations){
				if(!annotation.isEditable()){
					errors.add("some annotations are not editable in this editable JavaField");
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaField constructField(){
			return new EditableJavaField(sectionComment, javadoc, annotations,
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
	private EditableJavaField(
			String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal,
			String type, String name, String value){
		super(true, sectionComment, javadoc, annotations,
				visibility, isStatic, isFinal,
				type, name, value);
	}
	
	/**
	 * @return A new {@link EditableJavaFieldBuilder} to use to build a new {@link EditableJavaField}
	 */
	public static EditableJavaFieldBuilder builder(){
		return new EditableJavaFieldBuilder();
	}
	
	/**
	 * @param sectionComment The section comment above the field
	 */
	public void setSectionComment(String sectionComment){
		this.sectionComment = sectionComment;
	}
	
	/**
	 * @param javadoc The {@link Javadoc} on the field
	 */
	public void setJavadoc(Javadoc javadoc){
		if(!javadoc.isEditable()){
			throw new IllegalArgumentException("editable JavaField requires an editable Javadoc");
		}
		this.javadoc = javadoc;
	}
	
	/**
	 * @param annotation An {@link JavaAnnotation annotation} to add to the field (must be editable)
	 */
	public void addAnnotation(JavaAnnotation annotation){
		if(!annotation.isEditable()){
			throw new IllegalArgumentException("editable JavaField requires editable Java Annotations");
		}
		annotations.add(annotation);
	}
	
	/**
	 * @param annotations {@link JavaAnnotation annotations} to add to the field (must be editable)
	 */
	public void addAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable JavaField requires editable Java Annotations");
			}
		}
		this.annotations.addAll(annotations);
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the field (must be editable)
	 */
	public void setAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable JavaField requires editable Java Annotations");
			}
		}
		this.annotations = annotations;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the field
	 */
	public void setVisibility(Visibility visibility){
		this.visibility = visibility;
	}
	
	/**
	 * @param isStatic Whether the field is static or not
	 */
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @param isFinal Whether the field is final or not
	 */
	public void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}
	
	/**
	 * @param type The type of the field
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * @param name The name of the field
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @param value The value assigned to the field
	 */
	public void setValue(String value){
		this.value = value;
	}
}
