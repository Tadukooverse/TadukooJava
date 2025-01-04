package com.github.tadukoo.java.field;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Field represents a field in a {@link JavaClass Java class}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaField), Alpha v.0.4 (as newer version)
 */
public abstract class JavaField implements JavaCodeType{
	
	/** Whether the field is editable or not */
	private final boolean editable;
	/** The {@link Javadoc} on the field */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the field */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the field */
	protected Visibility visibility;
	/** Whether the field is static or not */
	protected boolean isStatic;
	/** Whether the field is final or not */
	protected boolean isFinal;
	/** The {@link JavaType type} of the field */
	protected JavaType type;
	/** The name of the field */
	protected String name;
	/** The value assigned to the field */
	protected String value;
	
	/**
	 * Constructs a Java Field with the given parameters
	 *
	 * @param editable Whether the field is editable or not
	 * @param javadoc The {@link Javadoc} on the field
	 * @param annotations The {@link JavaAnnotation annotations} on the field
	 * @param visibility The {@link Visibility} of the field
	 * @param isStatic Whether the field is static or not
	 * @param isFinal Whether the field is final or not
	 * @param type The {@link JavaType type} of the field
	 * @param name The name of the field
	 * @param value The value assigned to the field
	 */
	protected JavaField(
			boolean editable, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal,
			JavaType type, String name, String value){
		this.editable = editable;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.FIELD;
	}
	
	/**
	 * @return Whether the field is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The {@link Javadoc} on the field
	 */
	public Javadoc getJavadoc(){
		return javadoc;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the field
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the field
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return Whether the field is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @return Whether the field is final or not
	 */
	public boolean isFinal(){
		return isFinal;
	}
	
	/**
	 * @return The {@link JavaType type} of the field
	 */
	public JavaType getType(){
		return type;
	}
	
	/**
	 * @return The name of the field
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The value assigned to the field
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * @return A string of the form "{visibility.getText()} {type} {name}", with javadoc and annotations on newlines above
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
		
		// Add field declaration - starting with visibility
		StringBuilder declaration = new StringBuilder(visibility.getToken());
		if(!declaration.isEmpty()){
			// Declaration could be empty if visibility is NONE
			declaration.append(' ');
		}
		
		// Add static to declaration optionally
		if(isStatic){
			declaration.append(STATIC_MODIFIER).append(' ');
		}
		
		// Add final to declaration optionally
		if(isFinal){
			declaration.append(FINAL_MODIFIER).append(' ');
		}
		
		// Add type and name to the declaration
		declaration.append(type).append(' ').append(name);
		
		// Add value to declaration if we have one
		if(StringUtil.isNotBlank(value)){
			declaration.append(' ').append(ASSIGNMENT_OPERATOR_TOKEN).append(' ').append(value);
		}
		// Add semicolon to declaration
		declaration.append(SEMICOLON);
		content.add(declaration.toString());
		
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherField){
		if(otherField instanceof JavaField field){
			return StringUtil.equals(this.toString(), field.toString());
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
		
		// Add static if we have it
		if(isStatic){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isStatic()");
		}
		
		// Add final if we have it
		if(isFinal){
			codeString.append(NEWLINE_WITH_2_TABS).append(".isFinal()");
		}
		
		// Add type and name
		codeString.append(NEWLINE_WITH_2_TABS).append(".type(\"").append(type).append("\").name(\"").append(name).append("\")");
		
		// Add value if we have it
		if(StringUtil.isNotBlank(value)){
			codeString.append(NEWLINE_WITH_2_TABS).append(".value(\"").append(StringUtil.escapeString(value)).append("\")");
		}
		
		// Finish building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		return codeString.toString();
	}
}
