package com.github.tadukoo.java.editable;

import com.github.tadukoo.java.JavaAnnotation;
import com.github.tadukoo.java.JavaMethod;
import com.github.tadukoo.java.JavaMethodBuilder;
import com.github.tadukoo.java.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a method in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class EditableJavaMethod extends JavaMethod{
	
	/**
	 * A builder used to make an {@link EditableJavaMethod}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavaMethodBuilder
	 */
	public static class EditableJavaMethodBuilder extends JavaMethodBuilder<EditableJavaMethod>{
		
		/** Not allowed to instantiate outside {@link EditableJavaMethod} */
		private EditableJavaMethodBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		protected List<String> checkForSpecificErrors(){
			List<String> errors = new ArrayList<>();
			
			// javadoc can't be uneditable
			if(javadoc != null && !javadoc.isEditable()){
				errors.add("javadoc is not editable in this editable JavaMethod");
			}
			
			// annotations can't be uneditable
			for(JavaAnnotation annotation: annotations){
				if(!annotation.isEditable()){
					errors.add("some annotations are not editable in this editable JavaMethod");
				}
			}
			
			return errors;
		}
		
		/** {@inheritDoc} */
		@Override
		protected EditableJavaMethod constructMethod(){
			return new EditableJavaMethod(sectionComment, javadoc, annotations,
					visibility, isStatic, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param sectionComment The section comment above the method
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
	private EditableJavaMethod(
			String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, String returnType, String name,
			List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
		super(true, sectionComment, javadoc, annotations,
				visibility, isStatic, returnType, name,
				parameters, throwTypes, lines);
	}
	
	/**
	 * @return A new {@link EditableJavaMethodBuilder} to use to build a new {@link EditableJavaMethod}
	 */
	public static EditableJavaMethodBuilder builder(){
		return new EditableJavaMethodBuilder();
	}
	
	/**
	 * @param sectionComment The section comment above the method
	 */
	public void setSectionComment(String sectionComment){
		this.sectionComment = sectionComment;
	}
	
	/**
	 * @param javadoc The {@link Javadoc} on the method
	 */
	public void setJavadoc(Javadoc javadoc){
		if(!javadoc.isEditable()){
			throw new IllegalArgumentException("editable Java Method requires editable Javadoc");
		}
		this.javadoc = javadoc;
	}
	
	/**
	 * @param annotation An {@link JavaAnnotation annotation} on the method to be added - must be editable
	 */
	public void addAnnotation(JavaAnnotation annotation){
		if(!annotation.isEditable()){
			throw new IllegalArgumentException("editable Java Method requires editable Java Annotations");
		}
		annotations.add(annotation);
	}
	
	/**
	 * @param annotations {@link JavaAnnotation annotations} on the method to be added - must be editable
	 */
	public void addAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Method requires editable Java Annotations");
			}
		}
		this.annotations.addAll(annotations);
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the method - must be editable
	 */
	public void setAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Method requires editable Java Annotations");
			}
		}
		this.annotations = annotations;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the method
	 */
	public void setVisibility(Visibility visibility){
		this.visibility = visibility;
	}
	
	/**
	 * @param isStatic Whether the method is static or not
	 */
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @param returnType The return type of the method
	 */
	public void setReturnType(String returnType){
		this.returnType = returnType;
	}
	
	/**
	 * @param name The name of the method
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @param type The type of the parameter used in the method to be added
	 * @param name The name of the parameter used in the method to be added
	 */
	public void addParameter(String type, String name){
		parameters.add(Pair.of(type, name));
	}
	
	/**
	 * @param parameter A parameter used in the method to be added - a pair of type, then name
	 */
	public void addParameter(Pair<String, String> parameter){
		parameters.add(parameter);
	}
	
	/**
	 * @param parameters Parameters used in the method to be added - pairs of type, then name
	 */
	public void addParameters(List<Pair<String, String>> parameters){
		this.parameters.addAll(parameters);
	}
	
	/**
	 * @param parameters The parameters used in the method - pairs of type, then name
	 */
	public void setParameters(List<Pair<String, String>> parameters){
		this.parameters = parameters;
	}
	
	/**
	 * @param throwType A type that can be thrown to add to the method
	 */
	public void addThrowType(String throwType){
		throwTypes.add(throwType);
	}
	
	/**
	 * @param throwTypes Types that can be thrown to add to the method
	 */
	public void addThrowTypes(List<String> throwTypes){
		this.throwTypes.addAll(throwTypes);
	}
	
	/**
	 * @param throwTypes The types that can be thrown by the method
	 */
	public void setThrowTypes(List<String> throwTypes){
		this.throwTypes = throwTypes;
	}
	
	/**
	 * @param line An actual line of code to add to the method
	 */
	public void addLine(String line){
		lines.add(line);
	}
	
	/**
	 * @param lines Actual lines of code to add to the method
	 */
	public void addLines(List<String> lines){
		this.lines.addAll(lines);
	}
	
	/**
	 * @param lines The actual lines of code in the method
	 */
	public void setLines(List<String> lines){
		this.lines = lines;
	}
}
