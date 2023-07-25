package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
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
 *         <td>packageDeclaration</td>
 *         <td>The {@link JavaPackageDeclaration package declaration} of the class</td>
 *         <td>Defaults to null</td>
 *     </tr>
 *     <tr>
 *         <td>importStatements</td>
 *         <td>The {@link JavaImportStatement import statements} of the class</td>
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
 *         <td>{@link Visibility#NONE}</td>
 *     </tr>
 *     <tr>
 *         <td>isStatic</td>
 *         <td>Whether the class is static or not</td>
 *         <td>false</td>
 *     </tr>
 *     <tr>
 *         <td>isFinal</td>
 *         <td>Whether the class is final or not</td>
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
 * @version Beta v.0.5
 * @since Alpha v.0.2 (in JavaClass), Alpha v.0.4 (as a separate class)
 */
public abstract class JavaClassBuilder<ClassType extends JavaClass>{
	
	/** Whether the class is an inner class or not */
	protected boolean isInnerClass = false;
	/** The {@link JavaPackageDeclaration package declaration} of the class */
	protected JavaPackageDeclaration packageDeclaration = null;
	/** The {@link JavaImportStatement import statements} of the class */
	protected List<JavaImportStatement> importStatements = new ArrayList<>();
	/** The {@link Javadoc} for the class */
	protected Javadoc javadoc = null;
	/** The {@link JavaAnnotation annotations} on the class */
	protected List<JavaAnnotation> annotations = new ArrayList<>();
	/** The {@link Visibility} of the class */
	protected Visibility visibility = Visibility.NONE;
	/** Whether the class is static or not */
	protected boolean isStatic = false;
	/** Whether the class is final or not */
	protected boolean isFinal = false;
	/** The name of the class */
	protected String className = null;
	/** The name of the class this one extends (can be null) */
	protected String superClassName = null;
	/** The names of interfaces the class implements */
	protected List<String> implementsInterfaceNames = new ArrayList<>();
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
	 * Copies the settings from the given {@link JavaClass class} to this builder
	 *
	 * @param clazz The {@link JavaClass class} to copy settings from
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> copy(JavaClass clazz){
		this.isInnerClass = clazz.isInnerClass();
		this.packageDeclaration = clazz.getPackageDeclaration();
		this.importStatements = clazz.getImportStatements();
		this.javadoc = clazz.getJavadoc();
		this.annotations = clazz.getAnnotations();
		this.visibility = clazz.getVisibility();
		this.isStatic = clazz.isStatic();
		this.isFinal = clazz.isFinal();
		this.className = clazz.getClassName();
		this.superClassName = clazz.getSuperClassName();
		this.implementsInterfaceNames = clazz.getImplementsInterfaceNames();
		this.innerClasses = clazz.getInnerClasses();
		this.fields = clazz.getFields();
		this.methods = clazz.getMethods();
		return this;
	}
	
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
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> packageDeclaration(JavaPackageDeclaration packageDeclaration){
		this.packageDeclaration = packageDeclaration;
		return this;
	}
	
	/**
	 * @return A {@link JavaPackageDeclarationBuilder} to use to build a {@link JavaPackageDeclaration}
	 */
	protected abstract JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder();
	
	/**
	 * @param packageName The package name to use for the class, which gets put into a {@link JavaPackageDeclaration}
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> packageName(String packageName){
		return packageDeclaration(getPackageDeclarationBuilder()
				.packageName(packageName)
				.build());
	}
	
	/**
	 * @param importStatement An {@link JavaImportStatement import statement} of the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> importStatement(JavaImportStatement importStatement){
		importStatements.add(importStatement);
		return this;
	}
	
	/**
	 * @param importStatements The {@link JavaImportStatement import statements} of the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> importStatements(List<JavaImportStatement> importStatements){
		this.importStatements = importStatements;
		return this;
	}
	
	/**
	 * @return A {@link JavaImportStatementBuilder} to use to build a {@link JavaImportStatement}
	 */
	protected abstract JavaImportStatementBuilder<?> getImportStatementBuilder();
	
	/**
	 * @param importName The name of an import for the class (will be made into a {@link JavaImportStatement})
	 * @param isStatic Whether the import is static or not
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> importName(String importName, boolean isStatic){
		return importStatement(getImportStatementBuilder()
				.isStatic(isStatic).importName(importName)
				.build());
	}
	
	/**
	 * @param importNames The names of imports for the class (will be made into {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 * @return this, to continue building
	 */
	@SuppressWarnings("unchecked")
	public JavaClassBuilder<ClassType> importNames(List<String> importNames, boolean isStatic){
		return importStatements((List<JavaImportStatement>) importNames.stream()
				.map(importName -> getImportStatementBuilder()
					.isStatic(isStatic).importName(importName)
					.build())
				.toList());
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
	 * Sets isStatic to true, defining the class as a static class
	 *
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isStatic(){
		this.isStatic = true;
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
	 * Sets isFinal to true, definining the class as a final class
	 *
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isFinal(){
		this.isFinal = true;
		return this;
	}
	
	/**
	 * @param isFinal Whether the class is final or not
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isFinal(boolean isFinal){
		this.isFinal = isFinal;
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
	 * @param implementsInterfaceName The name of an interface this class implements
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> implementsInterfaceName(String implementsInterfaceName){
		implementsInterfaceNames.add(implementsInterfaceName);
		return this;
	}
	
	/**
	 * @param implementsInterfaceNames The names of interfaces this class implements
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> implementsInterfaceNames(List<String> implementsInterfaceNames){
		this.implementsInterfaceNames = implementsInterfaceNames;
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
		if(visibility == null){
			errors.add("Visibility is required!");
		}
		
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
			if(packageDeclaration != null){
				errors.add("Not allowed to have package declaration for an inner class!");
			}
			
			if(ListUtil.isNotBlank(importStatements)){
				errors.add("Not allowed to have import statements for an inner class!");
			}
		}else{
			// Regular class problems
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
