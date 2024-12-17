package com.github.tadukoo.java.javaclass;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineCommentBuilder;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineCommentBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.SetUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 *         <td>isAbstract</td>
 *         <td>Whether the class is abstract or not</td>
 *         <td>false</td>
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
 *         <td>implementsInterfaceNames</td>
 *         <td>The names of interfaces this class implements</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>singleLineComments</td>
 *         <td>The {@link JavaSingleLineComment single-line comments} inside the class</td>
 *         <td>An empty List</td>
 *     </tr>
 *     <tr>
 *         <td>multiLineComments</td>
 *         <td>The {@link JavaMultiLineComment multi-line comments} inside the class</td>
 *         <td>An empty List</td>
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
 *     <tr>
 *         <td>innerElementsOrder</td>
 *         <td>The order of the elements inside the class</td>
 *         <td>The order they were added in, Required if there are comments</td>
 *     </tr>
 * </table>
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.6
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
	/** Whether the class is abstract or not */
	protected boolean isAbstract = false;
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
	/** The {@link JavaSingleLineComment single-line comments} inside the class */
	protected List<JavaSingleLineComment> singleLineComments = new ArrayList<>();
	/** The {@link JavaMultiLineComment multi-line comments} inside the class */
	protected List<JavaMultiLineComment> multiLineComments = new ArrayList<>();
	/** Inner {@link JavaClass classes} inside the class */
	protected List<JavaClass> innerClasses = new ArrayList<>();
	/** The {@link JavaField fields} on the class */
	protected List<JavaField> fields = new ArrayList<>();
	/** The {@link JavaMethod methods} in the class */
	protected List<JavaMethod> methods = new ArrayList<>();
	/** The order of the elements inside the class */
	protected List<Pair<JavaCodeTypes, String>> innerElementsOrder = new ArrayList<>();
	
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
		this.isAbstract = clazz.isAbstract();
		this.isStatic = clazz.isStatic();
		this.isFinal = clazz.isFinal();
		this.className = clazz.getClassName();
		this.superClassName = clazz.getSuperClassName();
		this.implementsInterfaceNames = clazz.getImplementsInterfaceNames();
		this.singleLineComments = clazz.getSingleLineComments();
		this.multiLineComments = clazz.getMultiLineComments();
		this.innerClasses = clazz.getInnerClasses();
		this.fields = clazz.getFields();
		this.methods = clazz.getMethods();
		this.innerElementsOrder = clazz.getInnerElementsOrder();
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
	 * Sets isAbstract to true, defining the class as an abstract class
	 *
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isAbstract(){
		isAbstract = true;
		return this;
	}
	
	/**
	 * @param isAbstract Whether the class is abstract or not
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> isAbstract(boolean isAbstract){
		this.isAbstract = isAbstract;
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
	 * @param singleLineComments The {@link JavaSingleLineComment single-line comments} inside the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> singleLineComments(List<JavaSingleLineComment> singleLineComments){
		this.singleLineComments = singleLineComments;
		for(int i = 0; i < singleLineComments.size(); i++){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		}
		return this;
	}
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} inside the class to add to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> singleLineComment(JavaSingleLineComment singleLineComment){
		singleLineComments.add(singleLineComment);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @return A {@link JavaSingleLineCommentBuilder} to use to build a {@link JavaSingleLineComment}
	 */
	protected abstract JavaSingleLineCommentBuilder<?> getSingleLineCommentBuilder();
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} as a String inside the class to add to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> singleLineComment(String singleLineComment){
		singleLineComments.add(getSingleLineCommentBuilder()
				.content(singleLineComment)
				.build());
		innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @param multiLineComments The {@link JavaMultiLineComment multi-line comments} inside the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> multiLineComments(List<JavaMultiLineComment> multiLineComments){
		this.multiLineComments = multiLineComments;
		for(int i = 0; i < multiLineComments.size(); i++){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		}
		return this;
	}
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} inside the class to add to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> multiLineComment(JavaMultiLineComment multiLineComment){
		multiLineComments.add(multiLineComment);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @return A {@link JavaMultiLineCommentBuilder} to use to build a {@link JavaMultiLineComment}
	 */
	protected abstract JavaMultiLineCommentBuilder<?> getMultiLineCommentBuilder();
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} as Strings inside the class to add to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> multiLineComment(String... multiLineComment){
		multiLineComments.add(getMultiLineCommentBuilder()
				.content(ListUtil.createList(multiLineComment))
				.build());
		innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} inside the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerClasses(List<JavaClass> innerClasses){
		this.innerClasses = innerClasses;
		for(JavaClass innerClass: innerClasses){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.CLASS, innerClass.getClassName()));
		}
		return this;
	}
	
	/**
	 * @param innerClass An inner {@link JavaClass class} inside the class to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerClass(JavaClass innerClass){
		this.innerClasses.add(innerClass);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.CLASS, innerClass.getClassName()));
		return this;
	}
	
	/**
	 * @param fields The {@link JavaField fields} on the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> fields(List<JavaField> fields){
		this.fields = fields;
		for(JavaField field: fields){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.FIELD, field.getName()));
		}
		return this;
	}
	
	/**
	 * @param field A {@link JavaField field} on the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> field(JavaField field){
		fields.add(field);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.FIELD, field.getName()));
		return this;
	}
	
	/**
	 * @param methods The {@link JavaMethod methods} in the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> methods(List<JavaMethod> methods){
		this.methods = methods;
		for(JavaMethod method: methods){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.METHOD, method.getUniqueName()));
		}
		return this;
	}
	
	/**
	 * @param method A {@link JavaMethod method} in the class, to be added to the list
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> method(JavaMethod method){
		methods.add(method);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.METHOD, method.getUniqueName()));
		return this;
	}
	
	/**
	 * @param innerElementsOrder The order of elements inside the class
	 * @return this, to continue building
	 */
	public JavaClassBuilder<ClassType> innerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		this.innerElementsOrder = innerElementsOrder;
		return this;
	}
	
	/**
	 * Checks for any errors in the current parameters
	 *
	 * @throws IllegalArgumentException if anything is wrong
	 */
	private void checkForErrors(){
		List<String> errors = new ArrayList<>();
		
		// Visibility required
		if(visibility == null){
			errors.add("Visibility is required!");
		}
		
		// ClassName required
		if(StringUtil.isBlank(className)){
			errors.add("Must specify className!");
		}
		
		// Abstract errors
		if(isAbstract){
			// Can't be abstract + static
			if(isStatic){
				errors.add("Can't be abstract and static!");
			}
			
			// Can't be abstract + final
			if(isFinal){
				errors.add("Can't be abstract and final!");
			}
		}
		
		// If we have comments, we need innerElementOrder
		if(ListUtil.isBlank(innerElementsOrder) &&
				(ListUtil.isNotBlank(singleLineComments) || ListUtil.isNotBlank(multiLineComments))){
			errors.add("innerElementsOrder is required when comments are present!");
		}
		
		// If innerElementOrder is specified, verify it's valid and includes all inner elements
		if(ListUtil.isNotBlank(innerElementsOrder)){
			// Count comments usage
			int numSingleLineComments = singleLineComments.size(), numMultiLineComments = multiLineComments.size();
			Set<String> innerClassNames = SetUtil.createOrderedSet(innerClasses.stream().map(JavaClass::getClassName)
					.toList().toArray(new String[0]));
			Set<String> fieldNames = SetUtil.createOrderedSet(fields.stream().map(JavaField::getName).toList()
					.toArray(new String[0]));
			Set<String> methodNames = SetUtil.createOrderedSet(methods.stream().map(JavaMethod::getUniqueName).toList()
					.toArray(new String[0]));
			Set<String> usedInnerClassNames = new HashSet<>();
			Set<String> usedFieldNames = new HashSet<>();
			Set<String> usedMethodNames = new HashSet<>();
			for(Pair<JavaCodeTypes, String> elementInfo: innerElementsOrder){
				switch(elementInfo.getLeft()){
					case SINGLE_LINE_COMMENT -> {
						numSingleLineComments--;
						if(numSingleLineComments == -1){
							errors.add("Specified more single-line comments in innerElementsOrder than we have!");
						}
					}
					case MULTI_LINE_COMMENT -> {
						numMultiLineComments--;
						if(numMultiLineComments == -1){
							errors.add("Specified more multi-line comments in innerElementsOrder than we have!");
						}
					}
					case CLASS -> {
						// Check we actually have the inner class name
						String innerClassName = elementInfo.getRight();
						if(!innerClassNames.remove(innerClassName)){
							// Check if we already used the class name or not
							if(usedInnerClassNames.contains(innerClassName)){
								errors.add("Already used inner class named: " + innerClassName);
							}else{
								errors.add("Unknown inner class name: " + innerClassName);
							}
						}
						usedInnerClassNames.add(innerClassName);
					}
					case FIELD -> {
						// Check we actually have the field name
						String fieldName = elementInfo.getRight();
						if(!fieldNames.remove(fieldName)){
							// Check if we already used the field name or not
							if(usedFieldNames.contains(fieldName)){
								errors.add("Already used field named: " + fieldName);
							}else{
								errors.add("Unknown field name: " + fieldName);
							}
						}
						usedFieldNames.add(fieldName);
					}
					case METHOD -> {
						// Check we actually have the method name
						String methodName = elementInfo.getRight();
						if(!methodNames.remove(methodName)){
							// Check if we already used the method name or not
							if(usedMethodNames.contains(methodName)){
								errors.add("Already used method named: " + methodName);
							}else{
								errors.add("Unknown method name: " + methodName);
							}
						}
						usedMethodNames.add(methodName);
					}
					default -> errors.add("Unknown inner element type: " + elementInfo.getLeft().getStandardName());
				}
			}
			// If we didn't use all comments, it's a problem
			if(numSingleLineComments > 0){
				errors.add("Missed " + numSingleLineComments + " single-line comments in innerElementsOrder!");
			}
			if(numMultiLineComments > 0){
				errors.add("Missed " + numMultiLineComments + " multi-line comments in innerElementsOrder!");
			}
			// If we didn't use some inner class names, it's a problem
			if(!innerClassNames.isEmpty()){
				errors.add("The following inner classes were not specified in innerElementsOrder: " +
						StringUtil.buildCommaSeparatedString(innerClassNames));
			}
			// If we didn't use some field names, it's a problem
			if(!fieldNames.isEmpty()){
				errors.add("The following fields were not specified in innerElementsOrder: " +
						StringUtil.buildCommaSeparatedString(fieldNames));
			}
			// If we didn't use some method names, it's a problem
			if(!methodNames.isEmpty()){
				errors.add("The following methods were not specified in innerElementsOrder: " +
						StringUtil.buildCommaSeparatedString(methodNames));
			}
		}
		
		// Inner classes must specify they're inner classes
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
