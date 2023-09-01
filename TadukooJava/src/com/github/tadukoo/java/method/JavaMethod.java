package com.github.tadukoo.java.method;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Method represents a method in a Java class or interface, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaMethod), Alpha v.0.4 (as newer version)
 */
public abstract class JavaMethod implements JavaCodeType{
	
	/** Whether the method is editable or not */
	private final boolean editable;
	/** The {@link Javadoc} on the method */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the method */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the method */
	protected Visibility visibility;
	/** Whether the method is abstract or not */
	protected boolean isAbstract;
	/** Whether the method is static or not */
	protected boolean isStatic;
	/** Whether the method is final or not */
	protected boolean isFinal;
	/** The return type of the method */
	protected String returnType;
	/** The name of the method */
	protected String name;
	/** The parameters used in the method - pairs of type, then name */
	protected List<Pair<String, String>> parameters;
	/** The types that can be thrown by the method */
	protected List<String> throwTypes;
	/** The actual lines of code in the method */
	protected List<String> lines;
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param editable Whether the method is editable or not
	 * @param javadoc The {@link Javadoc} on the method
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @param visibility The {@link Visibility} of the method
	 * @param isAbstract Whether the method is abstract or not
	 * @param isStatic Whether the method is static or not
	 * @param isFinal Whether the method is final or not
	 * @param returnType The return type of the method
	 * @param name The name of the method
	 * @param parameters The parameters used in the method - pairs of type, then name
	 * @param throwTypes The types that can be thrown by the method
	 * @param lines The actual lines of code in the method
	 */
	protected JavaMethod(
			boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
			String returnType, String name,
			List<Pair<String, String>> parameters, List<String> throwTypes, List<String> lines){
		this.editable = editable;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isAbstract = isAbstract;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.throwTypes = throwTypes;
		this.lines = lines;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.METHOD;
	}
	
	/**
	 * @return Whether the method is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The {@link Javadoc} on the method
	 */
	public Javadoc getJavadoc(){
		return javadoc;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the method
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the method
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return Whether the method is abstract or not
	 */
	public boolean isAbstract(){
		return isAbstract;
	}
	
	/**
	 * @return Whether the method is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @return Whether the method is final or not
	 */
	public boolean isFinal(){
		return isFinal;
	}
	
	/**
	 * @return The return type of the method
	 */
	public String getReturnType(){
		return returnType;
	}
	
	/**
	 * @return The name of the method
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Builds and returns a unique name for the method. This is for use when a class has multiple methods with the
	 * same base name, but different parameters.
	 * <br><br>
	 * If a method has no parameters, this will return a name of the form {@code methodName()}
	 * <br><br>
	 * If a method does have parameters, they will appear in the parentheses with a comma and space after each but
	 * the last parameter, in the form {@code methodName(parameter1Type parameter1Name, parameter2Type parameter2Name}
	 * <br><br>
	 * If the method is a constructor, it will appear with the name {@code init}, e.g. {@code init()} or
	 * {@code init(parameter1Type parameter1Name)}
	 *
	 * @return A unique name for the method
	 */
	public String getUniqueName(){
		// Start with base method name and opening parenthesis
		StringBuilder fullName = new StringBuilder(StringUtil.isNotBlank(name)?name:"init").append('(');
		
		// Add any parameters
		if(ListUtil.isNotBlank(parameters)){
			for(Pair<String, String> parameter: parameters){
				fullName.append(parameter.getLeft()).append(' ').append(parameter.getRight()).append(", ");
			}
			// Remove the extra comma and space
			fullName.delete(fullName.length() - 2, fullName.length());
		}
		
		// Finish the parameters
		fullName.append(')');
		
		return fullName.toString();
	}
	
	/**
	 * @return The parameters used in the method - pairs of type, then name
	 */
	public List<Pair<String, String>> getParameters(){
		return parameters;
	}
	
	/**
	 * @return The types that can be thrown by the method
	 */
	public List<String> getThrowTypes(){
		return throwTypes;
	}
	
	/**
	 * @return The actual lines of code in the method
	 */
	public List<String> getLines(){
		return lines;
	}
	
	/**
	 * @return This Java Method as a String, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		List<String> content = new ArrayList<>();
		
		// Javadoc
		if(javadoc != null){
			content.add(javadoc.toString());
		}
		
		// Annotations
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				content.add(annotation.toString());
			}
		}
		
		/*
		 * Declaration
		 */
		// Start with the visibility
		StringBuilder declaration = new StringBuilder(visibility.getToken());
		if(!declaration.isEmpty()){
			// Add a space is visibility is not empty
			declaration.append(' ');
		}
		
		// Optionally add abstract to the declaration
		if(isAbstract){
			declaration.append(ABSTRACT_MODIFIER).append(' ');
		}
		
		// Optionally add static to the declaration
		if(isStatic){
			declaration.append(STATIC_MODIFIER).append(' ');
		}
		
		// Optionally add final to the declaration
		if(isFinal){
			declaration.append(FINAL_MODIFIER).append(' ');
		}
		
		// add return type to the declaration
		declaration.append(returnType);
		
		// Add name to declaration if we have it
		if(StringUtil.isNotBlank(name)){
			declaration.append(' ').append(name);
		}
		
		// Start of parameter section
		declaration.append(PARAMETER_OPEN_TOKEN);
		
		// Add parameters to the declaration
		if(ListUtil.isNotBlank(parameters)){
			for(Pair<String, String> parameter: parameters){
				declaration.append(parameter.getLeft()).append(' ').append(parameter.getRight())
						.append(LIST_SEPARATOR_TOKEN).append(' ');
			}
			// Remove final comma + space
			declaration.setLength(declaration.length()-2);
		}
		
		// If we have throw types, add them to the end of the declaration
		if(ListUtil.isNotBlank(throwTypes)){
			declaration.append(PARAMETER_CLOSE_TOKEN).append(' ').append(THROWS_TOKEN).append(' ');
			for(String throwType: throwTypes){
				declaration.append(throwType).append(LIST_SEPARATOR_TOKEN).append(' ');
			}
			// Remove the final comma and space
			declaration.delete(declaration.length() - 2, declaration.length());
		}else{
			// If no throw types, just end the parameters
			declaration.append(PARAMETER_CLOSE_TOKEN);
		}
		
		if(isAbstract){
			// If abstract, end with a semicolon
			declaration.append(SEMICOLON);
			content.add(declaration.toString());
		}else{
			// open the method
			declaration.append(BLOCK_OPEN_TOKEN);
			
			if(ListUtil.isNotBlank(lines)){
				// Add the declaration to the content
				content.add(declaration.toString());
				
				// Add the lines to the method
				for(String line: lines){
					content.add("\t" + line);
				}
				
				// Closing brace of the method
				content.add(BLOCK_CLOSE_TOKEN);
			}else{
				// When method has no content, open and close on the same line
				declaration.append(' ').append(BLOCK_CLOSE_TOKEN);
				content.add(declaration.toString());
			}
		}
		
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherMethod){
		if(otherMethod instanceof JavaMethod method){
			return StringUtil.equals(this.toString(), method.toString());
		}else{
			return false;
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start the building
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Add javadoc if we have it
		if(javadoc != null){
			codeString.append(NEWLINE_WITH_2_TABS).append(".javadoc(")
					.append(javadoc.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
					.append(')');
		}
		
		// Add annotations if we have them
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				codeString.append(NEWLINE_WITH_2_TABS).append(".annotation(")
						.append(annotation.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
						.append(')');
			}
		}
		
		// Add visibility
		if(visibility != Visibility.NONE){
			codeString.append(NEWLINE_WITH_2_TABS).append(".visibility(Visibility.").append(visibility).append(')');
		}
		
		// Add abstract if we have it
		if(isAbstract){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isAbstract()");
		}
		
		// Add static if we have it
		if(isStatic){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isStatic()");
		}
		
		// Add final if we have it
		if(isFinal){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isFinal()");
		}
		
		// Add return type
		codeString.append(NEWLINE_WITH_2_TABS).append(".returnType(\"").append(returnType).append("\")");
		
		// Add name if we have it
		if(StringUtil.isNotBlank(name)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".name(\"").append(name).append("\")");
		}
		
		// Add parameters if we have them
		if(ListUtil.isNotBlank(parameters)){
			for(Pair<String, String> parameter: parameters){
				codeString.append(NEWLINE_WITH_2_TABS).append(".parameter(\"")
						.append(parameter.getLeft()).append("\", \"")
						.append(parameter.getRight()).append("\")");
			}
		}
		
		// Add throw types if we have them
		if(ListUtil.isNotBlank(throwTypes)){
			for(String throwType: throwTypes){
				codeString.append(NEWLINE_WITH_2_TABS).append(".throwType(\"").append(throwType).append("\")");
			}
		}
		
		// Add lines if we have them
		if(ListUtil.isNotBlank(lines)){
			for(String line: lines){
				codeString.append(NEWLINE_WITH_2_TABS).append(".line(\"").append(escapeQuotes(line)).append("\")");
			}
		}
		
		// Finish the building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
