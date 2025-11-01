package com.github.tadukoo.java.method;

import com.github.tadukoo.java.JavaParameter;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.JavaTypeParameter;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.parsing.FullJavaParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a method in Java that can be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Alpha v.0.4
 */
public class EditableJavaMethod extends JavaMethod{
	
	/**
	 * A builder used to make an {@link EditableJavaMethod}
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Beta v.0.6
	 * @since Alpha v.0.4
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
			return new EditableJavaMethod(javadoc, annotations,
					visibility, isAbstract, isStatic, isFinal,
					typeParameters, returnType, name,
					parameters, throwTypes, lines);
		}
	}
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param javadoc The {@link Javadoc} on the method
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @param visibility The {@link Visibility} of the method
	 * @param isAbstract Whether the method is abstract or not
	 * @param isStatic Whether the method is static or not
	 * @param isFinal Whether the method is final or not
	 * @param typeParameters Any {@link JavaTypeParameter type parameters} for the method
	 * @param returnType The return {@link JavaType type} of the method
	 * @param name The name of the method
	 * @param parameters The {@link JavaParameter parameters} used in the method
	 * @param throwTypes The types that can be thrown by the method
	 * @param lines The actual lines of code in the method
	 */
	private EditableJavaMethod(
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
			List<JavaTypeParameter> typeParameters, JavaType returnType, String name,
			List<JavaParameter> parameters, List<String> throwTypes, List<String> lines){
		super(true, javadoc, annotations,
				visibility, isAbstract, isStatic, isFinal,
				typeParameters, returnType, name,
				parameters, throwTypes, lines);
	}
	
	/**
	 * @return A new {@link EditableJavaMethodBuilder} to use to build a new {@link EditableJavaMethod}
	 */
	public static EditableJavaMethodBuilder builder(){
		return new EditableJavaMethodBuilder();
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
	 * @param isAbstract Whether the method is abstract or not
	 */
	public void setAbstract(boolean isAbstract){
		this.isAbstract = isAbstract;
	}
	
	/**
	 * @param isStatic Whether the method is static or not
	 */
	public void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @param isFinal Whether the method is final or not
	 */
	public void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}
	
	/**
	 * @param typeParameters Any {@link JavaTypeParameter type parameters} for the method
	 */
	public void setTypeParameters(List<JavaTypeParameter> typeParameters){
		this.typeParameters = typeParameters;
	}
	
	/**
	 * @param typeParametersString A String representing any {@link JavaTypeParameter type parameters} for the method
	 */
	public void setTypeParameters(String typeParametersString){
		this.typeParameters = FullJavaParser.parseJavaTypeParameters(typeParametersString);
	}
	
	/**
	 * @param typeParameters {@link JavaTypeParameter Type parameters} to be added to the method
	 */
	public void addTypeParameters(List<JavaTypeParameter> typeParameters){
		this.typeParameters.addAll(typeParameters);
	}
	
	/**
	 * @param typeParametersString A String representing {@link JavaTypeParameter type parameters} to be added to the method
	 */
	public void addTypeParameters(String typeParametersString){
		this.typeParameters.addAll(FullJavaParser.parseJavaTypeParameters(typeParametersString));
	}
	
	/**
	 * @param typeParameter A {@link JavaTypeParameter type parameter} to be added to the method
	 */
	public void addTypeParameter(JavaTypeParameter typeParameter){
		typeParameters.add(typeParameter);
	}
	
	/**
	 * @param returnType The return {@link JavaType type} of the method
	 */
	public void setReturnType(JavaType returnType){
		this.returnType = returnType;
	}
	
	/**
	 * @param returnTypeText The text of the return {@link JavaType type} of the method to be parsed
	 */
	public void setReturnType(String returnTypeText){
		this.returnType = FullJavaParser.parseJavaType(returnTypeText);
	}
	
	/**
	 * @param name The name of the method
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * @param parameterText The text of the {@link JavaParameter parameter}, to be parsed and added
	 */
	public void addParameter(String parameterText){
		parameters.add(FullJavaParser.parseJavaParameter(parameterText));
	}
	
	/**
	 * @param parameter A {@link JavaParameter parameter} used in the method to be added
	 */
	public void addParameter(JavaParameter parameter){
		parameters.add(parameter);
	}
	
	/**
	 * @param parameters {@link JavaParameter Parameters} used in the method to be added
	 */
	public void addParameters(List<JavaParameter> parameters){
		this.parameters.addAll(parameters);
	}
	
	/**
	 * @param parameters The {@link JavaParameter parameters} used in the method
	 */
	public void setParameters(List<JavaParameter> parameters){
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
