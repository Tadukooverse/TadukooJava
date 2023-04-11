package com.github.tadukoo.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Class Builder is used to create a {@link JavaClass}. It has the following parameters:
 *
 * <table>
 *     <caption>Java Class Parameters</caption>
 *     <tr>
 *         <th>Parameter</th>
 *         <th>Description</th>
 *         <th>Default or Required</th>
 *     </tr>
 *     <tr>
 *         <td>isInnerClass</td>
 *         <td>Whether the class is an inner class or not</td>
 *         <td>Defaults to false</td>
 *     </tr>
 *     <tr>
 *         <td>packageName</td>
 *         <td>The name of the package the class is in</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>imports</td>
 *         <td>The classes imported by the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>staticImports</td>
 *         <td>The classes imported statically by the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>javadoc</td>
 *         <td>The {@link Javadoc} for the class</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>annotations</td>
 *         <td>The {@link JavaAnnotation annotations} on the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>visibility</td>
 *         <td>The {@link Visibility} of the class</td>
 *         <td>{@link Visibility#PUBLIC}</td>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>Whether the class is static or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>className</td>
 *         <td>The name of the class</td>
 *         <td>Required</td>
 *     </tr>
 *     <tr>
 *         <td>superClassName</td>
 *         <td>The name of the class this one extends (may be null)</td>
 *         <td>null</td>
 *     </tr>
 *     <tr>
 *         <td>innerClasses</td>
 *         <td>Inner {@link JavaClass classes} inside the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>fields</td>
 *         <td>The {@link JavaField fields} on the class</td>
 *         <td>An empty list</td>
 *     </tr>
 *     <tr>
 *         <td>methods</td>
 *         <td>The {@link JavaMethod methods} in the class</td>
 *         <td>An empty list</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.2 (in JavaClass), Alpha v.0.4 (as a separate class)
 */
public abstract class JavaClassBuilder<ClassType extends JavaClass>{
	
	/** Whether the class is an inner class or not */
	protected boolean isInnerClass = false;
	/** The name of the package the class is in */
	protected String packageName = null;
	/** The classes imported by the class */
	protected List<String> imports = new ArrayList<>();
	/** The classes imported statically by the class */
	protected List<String> staticImports = new ArrayList<>();
	/** The {@link Javadoc} for the class */
	protected Javadoc javadoc = null;
	/** The {@link JavaAnnotation annotations} on the class */
	protected List<JavaAnnotation> annotations = new ArrayList<>();
	/** The {@link Visibility} of the class */
	protected Visibility visibility = Visibility.PUBLIC;
	/** Whether the class is static or not */
	protected boolean isStatic = false;
	/** The name of the class */
	protected String className = null;
	/** The name of the class this one extends (may be null) */
	protected String superClassName = null;
	/** Inner {@link JavaClass classes} inside the class */
	protected List<JavaClass> innerClasses = new ArrayList<>();
	/** The {@link JavaField fields} on the class */
	protected List<JavaField> fields = new ArrayList<>();
	/** The {@link JavaMethod methods} in the class */
	protected List<JavaMethod> methods = new ArrayList<>();
	
	/**
	 * Constructs a new {@link JavaClassBuilder}
	 */
	protected JavaClassBuilder(){ }
	
	/**
	 * @param isInnerClass Whether the class is an inner class or not
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isInnerClass(boolean isInnerClass){
		this.isInnerClass = isInnerClass;
		return this;
	}
	
	/**
	 * Set the class as an inner class
	 *
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerClass(){
		this.isInnerClass = true;
		return this;
	}
	
	/**
	 * @param packageName The name of the package the class is in
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> packageName(String packageName){
		this.packageName = packageName;
		return this;
	}
	
	/**
	 * @param imports The classes imported by the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> imports(List<String> imports){
		this.imports = imports;
		return this;
	}
	
	/**
	 * @param singleImport A single class imported by the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> singleImport(String singleImport){
		imports.add(singleImport);
		return this;
	}
	
	/**
	 * @param staticImports The classes imported statically by the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> staticImports(List<String> staticImports){
		this.staticImports = staticImports;
		return this;
	}
	
	/**
	 * @param staticImport A single class imported statically by the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> staticImport(String staticImport){
		staticImports.add(staticImport);
		return this;
	}
	
	/**
	 * @param javadoc The {@link Javadoc} for the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> javadoc(Javadoc javadoc){
		this.javadoc = javadoc;
		return this;
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> annotations(List<JavaAnnotation> annotations){
		this.annotations = annotations;
		return this;
	}
	
	/**
	 * @param annotation A single {@link JavaAnnotation annotation} on the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> annotation(JavaAnnotation annotation){
		annotations.add(annotation);
		return this;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> visibility(Visibility visibility){
		this.visibility = visibility;
		return this;
	}
	
	/**
	 * @param isStatic Whether the class is static or not
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isStatic(boolean isStatic){
		this.isStatic = isStatic;
		return this;
	}
	
	/**
	 * Sets isStatic to true, defining the class as a static class
	 *
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isStatic(){
		this.isStatic = true;
		return this;
	}
	
	/**
	 * @param className The name of the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> className(String className){
		this.className = className;
		return this;
	}
	
	/**
	 * @param superClassName The name of the class this one extends (may be null)
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> superClassName(String superClassName){
		this.superClassName = superClassName;
		return this;
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerClasses(List<JavaClass> innerClasses){
		this.innerClasses = innerClasses;
		return this;
	}
	
	/**
	 * @param innerClass An inner {@link JavaClass class} inside the class to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerClass(JavaClass innerClass){
		this.innerClasses.add(innerClass);
		return this;
	}
	
	/**
	 * @param fields The {@link JavaField fields} on the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> fields(List<JavaField> fields){
		this.fields = fields;
		return this;
	}
	
	/**
	 * @param field A {@link JavaField field} on the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> field(JavaField field){
		fields.add(field);
		return this;
	}
	
	/**
	 * @param methods The {@link JavaMethod methods} in the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> methods(List<JavaMethod> methods){
		this.methods = methods;
		return this;
	}
	
	/**
	 * @param method A {@link JavaMethod method} in the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> method(JavaMethod method){
		methods.add(method);
		return this;
	}
	
	/**
	 * Checks for any errors in the current parameters
	 *
	 * @throws IllegalArgumentException if anything is wrong
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		// Generic problems
		if(StringUtil.isBlank(className)){
			errors.add("Must specify className!");
		}
		
		if(ListUtil.isNotBlank(innerClasses)){
			for(JavaClass innerClass: innerClasses){
				if(!innerClass.isInnerClass()){
					errors.add("Inner class '" + innerClass.getClassName() + "' is not an inner class!");
				}
			}
		}
		
		// Inner class problems
		if(isInnerClass){
			if(StringUtil.isNotBlank(packageName)){
				errors.add("Not allowed to have packageName for an inner class!");
			}
			
			if(ListUtil.isNotBlank(imports)){
				errors.add("Not allowed to have imports for an inner class!");
			}
			
			if(ListUtil.isNotBlank(staticImports)){
				errors.add("Not allowed to have static imports for an inner class!");
			}
		}else{
			// Regular class problems
			if(StringUtil.isBlank(packageName)){
				errors.add("Must specify packageName when not making an inner class!");
			}
			
			if(isStatic){
				errors.add("Only inner classes can be static!");
			}
		}
		
		// builder subclass errors
		errors.addAll(checkForSpecificErrors());
		
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Checks for errors in the specific subclass and returns them
	 *
	 * @return A list of errors, or an empty list if no errors
	 */
	protected abstract List<String> checkForSpecificErrors();
	
	/**
	 * Checks for any errors in the current parameters, then builds a new {@link JavaClass}
	 *
	 * @return A newly built {@link JavaClass}
	 * @throws IllegalArgumentException if anything is wrong with the current parameters
	 */
	public ClassType build(){
		// Run the error check
		checkForErrors();
		
		// Actually build the Java Class
		return constructClass();
	}
	
	/**
	 * Constructs a {@link JavaClass} using the set parameters
	 *
	 * @return The newly built {@link JavaClass}
	 */
	protected abstract ClassType constructClass();
}
