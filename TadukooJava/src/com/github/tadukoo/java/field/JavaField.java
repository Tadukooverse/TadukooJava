package com.github.tadukoo.java.field;

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
 * @version Alpha v.0.4
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaField), Alpha v.0.4 (as newer version)
 */
public abstract class JavaField{
	
	/** Whether the field is editable or not */
	private final boolean editable;
	/** The section comment above the field */
	protected String sectionComment;
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
	/** The type of the field */
	protected String type;
	/** The name of the field */
	protected String name;
	/** The value assigned to the field */
	protected String value;
	
	/**
	 * Constructs a Java Field with the given parameters
	 *
	 * @param editable Whether the field is editable or not
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
	protected JavaField(
			boolean editable, String sectionComment, Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal,
			String type, String name, String value){
		this.editable = editable;
		this.sectionComment = sectionComment;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @return Whether the field is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return The section comment above the field
	 */
	public String getSectionComment(){
		return sectionComment;
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
	 * @return The type of the field
	 */
	public String getType(){
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
		
		// Section comment
		if(StringUtil.isNotBlank(sectionComment)){
			content.add("/*");
			content.add(" * " + sectionComment);
			content.add(" */");
			content.add("");
		}
		
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
			declaration.append("static ");
		}
		
		// Add final to declaration optionally
		if(isFinal){
			declaration.append("final ");
		}
		
		// Add type and name to the declaration
		declaration.append(type).append(' ').append(name);
		
		// Add value to declaration if we have one
		if(StringUtil.isNotBlank(value)){
			declaration.append(" = ").append(value);
		}
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
}
