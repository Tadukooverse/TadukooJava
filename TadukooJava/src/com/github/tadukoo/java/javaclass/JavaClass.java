package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaClassType;
import com.github.tadukoo.java.JavaTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Class is used to represent a class in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 * @since Alpha v.0.2 (as old version that is now more like UneditableJavaClass), Alpha v.0.4 (as newer version)
 */
public abstract class JavaClass implements JavaClassType{
	
	/** Whether this class is editable or not */
	private final boolean editable;
	/** Whether this is an inner class or not */
	protected boolean isInnerClass;
	/** The name of the package the class is in */
	protected String packageName;
	/** The classes imported by the class */
	protected List<String> imports;
	/** The classes imported statically by the class */
	protected List<String> staticImports;
	/** The {@link Javadoc} for the class */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the class */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the class */
	protected Visibility visibility;
	/** Whether this is a static class or not */
	protected boolean isStatic;
	/** The name of the class */
	protected String className;
	/** The name of the class this one extends (may be null) */
	protected String superClassName;
	/** Inner {@link JavaClass classes} inside the class */
	protected List<JavaClass> innerClasses;
	/** The {@link JavaField fields} on the class */
	protected List<JavaField> fields;
	/** The {@link JavaMethod methods} in the class */
	protected List<JavaMethod> methods;
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param editable Whether this class is editable or not
	 * @param isInnerClass Whether this is an inner class or not
	 * @param packageName The name of the package the class is in
	 * @param imports The classes imported by the class
	 * @param staticImports The classes imported statically by the class
	 * @param javadoc The {@link Javadoc} for the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param isStatic Whether this is a static class or not
	 * @param className The name of the class
	 * @param superClassName The name of the class this one extends (may be null)
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 */
	protected JavaClass(
			boolean editable, boolean isInnerClass, String packageName, List<String> imports, List<String> staticImports,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, String className, String superClassName,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods){
		this.editable = editable;
		this.isInnerClass = isInnerClass;
		this.packageName = packageName;
		this.imports = imports;
		this.staticImports = staticImports;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isStatic = isStatic;
		this.className = className;
		this.superClassName = superClassName;
		this.innerClasses = innerClasses;
		this.fields = fields;
		this.methods = methods;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaTypes getType(){
		return JavaTypes.CLASS;
	}
	
	/**
	 * @return Whether this class is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return Whether this is an inner class or not
	 */
	public boolean isInnerClass(){
		return isInnerClass;
	}
	
	/**
	 * @return The name of the package the class is in
	 */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * @return The classes imported by the class
	 */
	public List<String> getImports(){
		return imports;
	}
	
	/**
	 * @return The classes imported statically by the class
	 */
	public List<String> getStaticImports(){
		return staticImports;
	}
	
	/**
	 * @return The {@link Javadoc} for the class
	 */
	public Javadoc getJavadoc(){
		return javadoc;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the class
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the class
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return Whether this class is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @return The name of the class
	 */
	public String getClassName(){
		return className;
	}
	
	/**
	 * @return The name of the class this one extends (may be null)
	 */
	public String getSuperClassName(){
		return superClassName;
	}
	
	/**
	 * @return Inner {@link JavaClass classes} inside this class
	 */
	public List<JavaClass> getInnerClasses(){
		return innerClasses;
	}
	
	/**
	 * @return The {@link JavaField fields} on the class
	 */
	public List<JavaField> getFields(){
		return fields;
	}
	
	/**
	 * @return The {@link JavaMethod methods} in the class
	 */
	public List<JavaMethod> getMethods(){
		return methods;
	}
	
	/**
	 * @return The actual Java code this {@link JavaClass} represents
	 */
	@Override
	public String toString(){
		// Create a list of the lines of the class
		List<String> content = new ArrayList<>();
		
		// Package Declaration
		if(!isInnerClass){
			content.add("package " + packageName + ";");
		}
		
		// Import Statements
		if(ListUtil.isNotBlank(imports)){
			// Newline between package declaration + imports
			content.add("");
			for(String singleImport: imports){
				if(StringUtil.isNotBlank(singleImport)){
					content.add("import " + singleImport + ";");
				}else{
					content.add("");
				}
			}
		}
		
		// Static Import Statements
		if(ListUtil.isNotBlank(staticImports)){
			// Newline between package declaration/imports + static imports
			content.add("");
			for(String staticImport: staticImports){
				if(StringUtil.isNotBlank(staticImport)){
					content.add("import static " + staticImport + ";");
				}else{
					content.add("");
				}
			}
		}
		
		// Newline between package declaration/imports + javadoc/annotations/class declaration
		if(!isInnerClass){
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
		
		// Class Declaration
		content.add(visibility.getToken() + (isStatic?" static":"") + " class " + className +
				(StringUtil.isNotBlank(superClassName)?" extends " + superClassName:"") + "{");
		
		// Newline at start of class
		content.add("\t");
		
		// Inner classes of the class
		if(ListUtil.isNotBlank(innerClasses)){
			for(JavaClass clazz: innerClasses){
				content.add(StringUtil.indentAllLines(clazz.toString()));
			}
		}
		
		// Fields on the class
		if(ListUtil.isNotBlank(fields)){
			for(JavaField field: fields){
				// Use indent all lines because Javadoc may make it multiline
				content.add(StringUtil.indentAllLines(field.toString()) + ";");
			}
		}
		
		// Methods in the class
		if(ListUtil.isNotBlank(methods)){
			// Newline to separate fields from methods
			if(ListUtil.isNotBlank(fields)){
				content.add("\t");
			}
			for(JavaMethod method: methods){
				// Split the method into its lines so we can add it to our lines
				List<String> lines = StringUtil.parseListFromStringWithSeparator(
						method.toString(), "\n", false);
				for(String line: lines){
					content.add("\t" + line);
				}
				content.add("\t");
			}
			// Remove extra newline at the end
			content.remove(content.size()-1);
		}
		
		// Closing brace at end of class and empty newline at end of file
		content.add("}");
		content.add("");
		
		// Build the full string
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean equals(Object otherClass){
		if(otherClass instanceof JavaClass clazz){
			return StringUtil.equals(toString(), clazz.toString());
		}else{
			return false;
		}
	}
}
