package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaClassType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.map.HashMultiMap;
import com.github.tadukoo.util.map.MultiMap;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	/** The {@link JavaPackageDeclaration package declaration} of the class */
	protected JavaPackageDeclaration packageDeclaration;
	/** The {@link JavaImportStatement import statements} of the class */
	protected List<JavaImportStatement> importStatements;
	/** The {@link Javadoc} for the class */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the class */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the class */
	protected Visibility visibility;
	/** Whether this is a static class or not */
	protected boolean isStatic;
	/** Whether this is a final class or not */
	protected boolean isFinal;
	/** The name of the class */
	protected String className;
	/** The name of the class this one extends (may be null) */
	protected String superClassName;
	/** The names of interfaces this class implements */
	protected List<String> implementsInterfaceNames;
	/** The {@link JavaSingleLineComment single-line comments} inside the class */
	protected List<JavaSingleLineComment> singleLineComments;
	/** The {@link JavaMultiLineComment multi-line comments} inside the class */
	protected List<JavaMultiLineComment> multiLineComments;
	/** Inner {@link JavaClass classes} inside the class */
	protected List<JavaClass> innerClasses;
	/** The {@link JavaField fields} on the class */
	protected List<JavaField> fields;
	/** The {@link JavaMethod methods} in the class */
	protected List<JavaMethod> methods;
	/** The order of the elements inside the class */
	protected List<Pair<JavaCodeTypes, String>> innerElementsOrder;
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param editable Whether this class is editable or not
	 * @param isInnerClass Whether this is an inner class or not
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class
	 * @param importStatements The {@link JavaImportStatement import statements} of the class
	 * @param javadoc The {@link Javadoc} for the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param isStatic Whether this is a static class or not
	 * @param isFinal Whether this is a final class or not
	 * @param className The name of the class
	 * @param superClassName The name of the class this one extends (can be null)
	 * @param implementsInterfaceNames The names of interfaces this class implements
	 * @param singleLineComments The {@link JavaSingleLineComment single-line comments} inside the class
	 * @param multiLineComments The {@link JavaMultiLineComment multi-line comments} inside the class
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 * @param innerElementsOrder The order of the elements inside the class
	 */
	protected JavaClass(
			boolean editable, boolean isInnerClass,
			JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isStatic, boolean isFinal, String className,
			String superClassName, List<String> implementsInterfaceNames,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		this.editable = editable;
		this.isInnerClass = isInnerClass;
		this.packageDeclaration = packageDeclaration;
		this.importStatements = importStatements;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.className = className;
		this.superClassName = superClassName;
		this.implementsInterfaceNames = implementsInterfaceNames;
		this.singleLineComments = singleLineComments;
		this.multiLineComments = multiLineComments;
		this.innerClasses = innerClasses;
		this.fields = fields;
		this.methods = methods;
		this.innerElementsOrder = innerElementsOrder;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return JavaCodeTypes.CLASS;
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
	 * @return The {@link JavaPackageDeclaration package declaration} of the class
	 */
	public JavaPackageDeclaration getPackageDeclaration(){
		return packageDeclaration;
	}
	
	/**
	 * @return The {@link JavaImportStatement import statements} of the class
	 */
	public List<JavaImportStatement> getImportStatements(){
		return importStatements;
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
	 * @return Whether this class is final or not
	 */
	public boolean isFinal(){
		return isFinal;
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
	 * @return The names of interfaces this class implements
	 */
	public List<String> getImplementsInterfaceNames(){
		return implementsInterfaceNames;
	}
	
	/**
	 * @return The {@link JavaSingleLineComment single-line comments} inside this class
	 */
	public List<JavaSingleLineComment> getSingleLineComments(){
		return singleLineComments;
	}
	
	/**
	 * @return The {@link JavaMultiLineComment multi-line comments} inside this class
	 */
	public List<JavaMultiLineComment> getMultiLineComments(){
		return multiLineComments;
	}
	
	/**
	 * @return Inner {@link JavaClass classes} inside this class
	 */
	public List<JavaClass> getInnerClasses(){
		return innerClasses;
	}
	
	/**
	 * @return The {@link JavaClass classes} inside this class as a Map by class name
	 */
	public Map<String, JavaClass> getInnerClassesMap(){
		Map<String, JavaClass> classMap = new HashMap<>();
		for(JavaClass clazz: innerClasses){
			classMap.put(clazz.getClassName(), clazz);
		}
		return classMap;
	}
	
	/**
	 * @return The {@link JavaField fields} on the class
	 */
	public List<JavaField> getFields(){
		return fields;
	}
	
	/**
	 * @return The {@link JavaField fields} on the class as a Map by field name
	 */
	public Map<String, JavaField> getFieldsMap(){
		Map<String, JavaField> fieldMap = new HashMap<>();
		for(JavaField field: fields){
			fieldMap.put(field.getName(), field);
		}
		return fieldMap;
	}
	
	/**
	 * @return The {@link JavaMethod methods} in the class
	 */
	public List<JavaMethod> getMethods(){
		return methods;
	}
	
	/**
	 * @return The {@link JavaMethod methods} on the class as a Map by method name (using {@link JavaMethod#getUniqueName()})
	 */
	public Map<String, JavaMethod> getMethodsMap(){
		Map<String, JavaMethod> methodMap = new HashMap<>();
		for(JavaMethod method: methods){
			methodMap.put(method.getUniqueName(), method);
		}
		return methodMap;
	}
	
	/**
	 * @return The order of elements inside the class
	 */
	public List<Pair<JavaCodeTypes, String>> getInnerElementsOrder(){
		return innerElementsOrder;
	}
	
	/**
	 * @return The actual Java code this {@link JavaClass} represents
	 */
	@Override
	public String toString(){
		// Create a list of the lines of the class
		List<String> content = new ArrayList<>();
		
		// Package Declaration
		if(packageDeclaration != null){
			content.add(packageDeclaration.toString());
			// Newline between package declaration and whatever's next
			content.add("");
		}
		
		// Import Statements
		if(ListUtil.isNotBlank(importStatements)){
			// Separate into regular and static imports
			List<JavaImportStatement> regularImports = new ArrayList<>();
			List<JavaImportStatement> staticImports = new ArrayList<>();
			importStatements.forEach(stmt -> {
				if(stmt.isStatic()){
					staticImports.add(stmt);
				}else{
					regularImports.add(stmt);
				}
			});
			
			// Handle regular imports if we have them
			if(ListUtil.isNotBlank(regularImports)){
				MultiMap<String, JavaImportStatement> sortedImports = sortImports(regularImports);
				List<String> alphabetizedKeys = sortedImports.keySet().stream().sorted().toList();
				for(String key: alphabetizedKeys){
					List<String> alphabetizedImports = sortedImports.get(key).stream()
							.map(JavaImportStatement::toString)
							.sorted().toList();
					content.addAll(alphabetizedImports);
					// Add newline after each section
					content.add("");
				}
			}
			
			// Handle static imports if we have them
			if(ListUtil.isNotBlank(staticImports)){
				MultiMap<String, JavaImportStatement> sortedImports = sortImports(staticImports);
				List<String> alphabetizedKeys = sortedImports.keySet().stream().sorted().toList();
				for(String key: alphabetizedKeys){
					List<String> alphabetizedImports = sortedImports.get(key).stream()
							.map(JavaImportStatement::toString)
							.sorted().toList();
					content.addAll(alphabetizedImports);
					// Add newline after each section
					content.add("");
				}
			}
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
		
		/*
		 * Class Declaration
		 */
		// Start with visibility
		StringBuilder declaration = new StringBuilder(visibility.getToken());
		if(!declaration.isEmpty()){
			// If visibility is not NONE, we need a space after it
			declaration.append(' ');
		}
		
		// Optionally add static to the class declaration
		if(isStatic){
			declaration.append(STATIC_MODIFIER).append(' ');
		}
		
		// Optionally add final to the class declaration
		if(isFinal){
			declaration.append(FINAL_MODIFIER).append(' ');
		}
		
		// Append class token and name to the declaration
		declaration.append(CLASS_TOKEN).append(' ').append(className);
		
		// Optionally append super class name to the declaration
		if(StringUtil.isNotBlank(superClassName)){
			declaration.append(' ').append(EXTENDS_TOKEN).append(' ').append(superClassName);
		}
		
		// Optionally append implemented interfaces to the declaration
		if(ListUtil.isNotBlank(implementsInterfaceNames)){
			declaration.append(' ').append(IMPLEMENTS_TOKEN);
			for(String implementsInterfaceName: implementsInterfaceNames){
				declaration.append(' ').append(implementsInterfaceName).append(',');
			}
			// Remove final comma
			declaration.deleteCharAt(declaration.length()-1);
		}
		
		// End the declaration by opening the code block
		declaration.append(BLOCK_OPEN_TOKEN);
		content.add(declaration.toString());
		
		// Newline at start of class
		content.add("\t");
		
		if(ListUtil.isNotBlank(innerElementsOrder)){
			int singleLineCommentIndex = 0, multiLineCommentIndex = 0;
			Map<String, JavaClass> innerClassesByName = getInnerClassesMap();
			Map<String, JavaField> fieldsByName = getFieldsMap();
			Map<String, JavaMethod> methodsByName = getMethodsMap();
			JavaCodeTypes lastType = null;
			for(Pair<JavaCodeTypes, String> elementInfo: innerElementsOrder){
				switch(elementInfo.getLeft()){
					case SINGLE_LINE_COMMENT -> {
						content.add(StringUtil.indentAllLines(
								singleLineComments.get(singleLineCommentIndex).toString()));
						singleLineCommentIndex++;
					}
					case MULTI_LINE_COMMENT -> {
						content.add(StringUtil.indentAllLines(multiLineComments.get(multiLineCommentIndex).toString()));
						multiLineCommentIndex++;
					}
					case CLASS -> content.add(StringUtil.indentAllLines(
							innerClassesByName.get(elementInfo.getRight()).toString()));
					case FIELD -> content.add(StringUtil.indentAllLines(
							fieldsByName.get(elementInfo.getRight()).toString()));
					case METHOD -> {
						// Add line before method if last item was a field
						if(lastType == JavaCodeTypes.FIELD){
							content.add("\t");
						}
						content.add(StringUtil.indentAllLines(methodsByName.get(elementInfo.getRight()).toString()));
						// Add line after method
						content.add("\t");
					}
				}
				lastType = elementInfo.getLeft();
			}
			// Remove last line if it's just a newline from the method
			if(StringUtil.equals(content.get(content.size() - 1), "\t")){
				content.remove(content.size()-1);
			}
		}else{
			// Default order is inner classes, then fields, then methods
			
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
					content.add(StringUtil.indentAllLines(field.toString()));
				}
			}
			
			// Methods in the class
			if(ListUtil.isNotBlank(methods)){
				// Newline to separate fields from methods
				if(ListUtil.isNotBlank(fields)){
					content.add("\t");
				}
				for(JavaMethod method: methods){
					content.add(StringUtil.indentAllLines(method.toString()));
					content.add("\t");
				}
				// Remove extra newline at the end
				content.remove(content.size() - 1);
			}
		}
		
		// Closing brace at end of class and empty newline at end of file
		content.add(BLOCK_CLOSE_TOKEN);
		content.add("");
		
		// Build the full string
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/**
	 * Sorts the given List of {@link JavaImportStatement import statements} by the first part of their package name
	 *
	 * @param importStatements The {@link JavaImportStatement import statements} to be sorted
	 * @return A MultiMap of starting part of package name to the import statements
	 */
	private MultiMap<String, JavaImportStatement> sortImports(List<JavaImportStatement> importStatements){
		MultiMap<String, JavaImportStatement> mappedStatements = new HashMultiMap<>();
		
		// Iterate over the statements, adding them to the map by their first part of their package
		for(JavaImportStatement importStatement: importStatements){
			String start = importStatement.getImportName().split("\\.")[0];
			mappedStatements.put(start, importStatement);
		}
		
		return mappedStatements;
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
