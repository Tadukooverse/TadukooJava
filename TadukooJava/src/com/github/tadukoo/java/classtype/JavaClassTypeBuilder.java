package com.github.tadukoo.java.classtype;

import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlockBuilder;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaMultiLineCommentBuilder;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineCommentBuilder;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatementBuilder;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclarationBuilder;
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.SetUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JavaClassTypeBuilder<ClassType extends JavaClassType>{
	/** Whether the class type is editable or not */
	protected boolean isEditable;
	/** Whether the class type is an inner class type or not */
	protected boolean isInnerType = false;
	/** The {@link JavaPackageDeclaration package declaration} of the class type */
	protected JavaPackageDeclaration packageDeclaration = null;
	/** The {@link JavaImportStatement import statements} of the class type */
	protected List<JavaImportStatement> importStatements = new ArrayList<>();
	/** The {@link Javadoc} for the class type */
	protected Javadoc javadoc = null;
	/** The {@link JavaAnnotation annotations} on the class type */
	protected List<JavaAnnotation> annotations = new ArrayList<>();
	/** The {@link Visibility} of the class type */
	protected Visibility visibility = Visibility.NONE;
	/** Whether the class type is abstract or not */
	protected boolean isAbstract = false;
	/** Whether the class type is static or not */
	protected boolean isStatic = false;
	/** Whether the class type is final or not */
	protected boolean isFinal = false;
	/** The name of the class type, along with type parameters to form a {@link JavaType} */
	protected JavaType name = null;
	/** The name of the class types this one extends, along with type parameters to form a {@link JavaType} */
	protected List<JavaType> extendsTypes = new ArrayList<>();
	/** The names of interfaces this class type implements, along with type parameters to form a {@link JavaType} */
	protected List<JavaType> implementsTypes = new ArrayList<>();
	/** The {@link JavaStaticCodeBlock static code blocks} inside the class type */
	protected List<JavaStaticCodeBlock> staticCodeBlocks = new ArrayList<>();
	/** The {@link JavaSingleLineComment single-line comments} inside the class type */
	protected List<JavaSingleLineComment> singleLineComments = new ArrayList<>();
	/** The {@link JavaMultiLineComment multi-line comments} inside the class type */
	protected List<JavaMultiLineComment> multiLineComments = new ArrayList<>();
	/** Inner {@link JavaClass classes} inside the class type */
	protected List<JavaClass> innerClasses = new ArrayList<>();
	/** The {@link JavaField fields} on the class type */
	protected List<JavaField> fields = new ArrayList<>();
	/** The {@link JavaMethod methods} in the class type */
	protected List<JavaMethod> methods = new ArrayList<>();
	/** The order of the elements inside the class type */
	protected List<Pair<JavaCodeTypes, String>> innerElementsOrder = new ArrayList<>();
	
	/** Constructs a new {@link JavaClassTypeBuilder} */
	protected JavaClassTypeBuilder(){ }
	
	/**
	 * Copies the settings from the given {@link JavaClassType class type} to this builder
	 *
	 * @param clazz The {@link JavaClassType class type} to copy settings from
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> copy(JavaClassType clazz){
		this.isInnerType = clazz.isInnerType();
		this.packageDeclaration = clazz.getPackageDeclaration();
		this.importStatements = clazz.getImportStatements();
		this.javadoc = clazz.getJavadoc();
		this.annotations = clazz.getAnnotations();
		this.visibility = clazz.getVisibility();
		this.isAbstract = clazz.isAbstract();
		this.isStatic = clazz.isStatic();
		this.isFinal = clazz.isFinal();
		this.name = clazz.getName();
		this.extendsTypes = clazz.getExtendsTypes();
		this.implementsTypes = clazz.getImplementsTypes();
		this.staticCodeBlocks = clazz.getStaticCodeBlocks();
		this.singleLineComments = clazz.getSingleLineComments();
		this.multiLineComments = clazz.getMultiLineComments();
		this.innerClasses = clazz.getInnerClasses();
		this.fields = clazz.getFields();
		this.methods = clazz.getMethods();
		this.innerElementsOrder = clazz.getInnerElementsOrder();
		return this;
	}
	
	/**
	 * @param isInnerType Whether the class type is an inner class type or not
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isInnerType(boolean isInnerType){
		this.isInnerType = isInnerType;
		return this;
	}
	
	/**
	 * Set the class type as an inner class type
	 *
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> innerType(){
		this.isInnerType = true;
		return this;
	}
	
	/**
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> packageDeclaration(JavaPackageDeclaration packageDeclaration){
		this.packageDeclaration = packageDeclaration;
		return this;
	}
	
	/**
	 * @return A {@link JavaPackageDeclarationBuilder} to use to build a {@link JavaPackageDeclaration}
	 */
	protected abstract JavaPackageDeclarationBuilder<?> getPackageDeclarationBuilder();
	
	/**
	 * @param packageName The package name to use for the class type, which gets put into a {@link JavaPackageDeclaration}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> packageName(String packageName){
		return packageDeclaration(getPackageDeclarationBuilder()
				.packageName(packageName)
				.build());
	}
	
	/**
	 * @param importStatement An {@link JavaImportStatement import statement} of the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> importStatement(JavaImportStatement importStatement){
		importStatements.add(importStatement);
		return this;
	}
	
	/**
	 * @param importStatements The {@link JavaImportStatement import statements} of the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> importStatements(List<JavaImportStatement> importStatements){
		this.importStatements = importStatements;
		return this;
	}
	
	/**
	 * @return A {@link JavaImportStatementBuilder} to use to build a {@link JavaImportStatement}
	 */
	protected abstract JavaImportStatementBuilder<?> getImportStatementBuilder();
	
	/**
	 * @param importName The name of an import for the class type (will be made into a {@link JavaImportStatement})
	 * @param isStatic Whether the import is static or not
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> importName(String importName, boolean isStatic){
		return importStatement(getImportStatementBuilder()
				.isStatic(isStatic).importName(importName)
				.build());
	}
	
	/**
	 * @param importNames The names of imports for the class type (will be made into {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 * @return this, to continue building
	 */
	@SuppressWarnings("unchecked")
	protected JavaClassTypeBuilder<ClassType> importNames(List<String> importNames, boolean isStatic){
		return importStatements((List<JavaImportStatement>) importNames.stream()
				.map(importName -> getImportStatementBuilder()
						.isStatic(isStatic).importName(importName)
						.build())
				.toList());
	}
	
	/**
	 * @param javadoc The {@link Javadoc} for the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> javadoc(Javadoc javadoc){
		this.javadoc = javadoc;
		return this;
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> annotations(List<JavaAnnotation> annotations){
		this.annotations = annotations;
		return this;
	}
	
	/**
	 * @param annotation A single {@link JavaAnnotation annotation} on the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> annotation(JavaAnnotation annotation){
		annotations.add(annotation);
		return this;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> visibility(Visibility visibility){
		this.visibility = visibility;
		return this;
	}
	
	/**
	 * Sets isAbstract to true, defining the class type as an abstract class
	 *
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isAbstract(){
		isAbstract = true;
		return this;
	}
	
	/**
	 * @param isAbstract Whether the class type is abstract or not
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isAbstract(boolean isAbstract){
		this.isAbstract = isAbstract;
		return this;
	}
	
	/**
	 * Sets isStatic to true, defining the class type as a static class
	 *
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isStatic(){
		this.isStatic = true;
		return this;
	}
	
	/**
	 * @param isStatic Whether the class type is static or not
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isStatic(boolean isStatic){
		this.isStatic = isStatic;
		return this;
	}
	
	/**
	 * Sets isFinal to true, defining the class type as a final class
	 *
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isFinal(){
		this.isFinal = true;
		return this;
	}
	
	/**
	 * @param isFinal Whether the class type is final or not
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> isFinal(boolean isFinal){
		this.isFinal = isFinal;
		return this;
	}
	
	/**
	 * @param name The name of the class type, along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> name(JavaType name){
		this.name = name;
		return this;
	}
	
	/**
	 * @param nameText The text to parse for name of the class type, along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> name(String nameText){
		this.name = FullJavaParser.parseJavaType(nameText);
		return this;
	}
	
	/**
	 * @param extendsTypes The name of the class types this one extends, along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> extendsTypes(List<JavaType> extendsTypes){
		this.extendsTypes = extendsTypes;
		return this;
	}
	
	/**
	 * @param extendsTypeTexts The texts to parse for the names of class types this one extends,
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> extendsTypesTexts(List<String> extendsTypeTexts){
		this.extendsTypes = extendsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType)
				.collect(Collectors.toList());
		return this;
	}
	
	/**
	 * @param extendsType The name of a class type this one extends, along with type parameters to form a {@link JavaType},
	 * to add to the List
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> extendsType(JavaType extendsType){
		this.extendsTypes.add(extendsType);
		return this;
	}
	
	/**
	 * @param extendsTypeText The text to parse for the name of a class type this one extends,
	 *  along with type parameters to form a {@link JavaType}, to add to the List
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> extendsType(String extendsTypeText){
		this.extendsTypes.add(FullJavaParser.parseJavaType(extendsTypeText));
		return this;
	}
	
	/**
	 * @param implementsTypes The names of class types this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> implementsTypes(List<JavaType> implementsTypes){
		this.implementsTypes = implementsTypes;
		return this;
	}
	
	/**
	 * @param implementsTypeTexts The text for the names of class types this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> implementsTypeTexts(List<String> implementsTypeTexts){
		this.implementsTypes = implementsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType).collect(Collectors.toList());
		return this;
	}
	
	/**
	 * @param implementsType The name of a class type this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> implementsType(JavaType implementsType){
		implementsTypes.add(implementsType);
		return this;
	}
	
	/**
	 * @param implementsTypeText The text for the name of a class type this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> implementsType(String implementsTypeText){
		implementsTypes.add(FullJavaParser.parseJavaType(implementsTypeText));
		return this;
	}
	
	/**
	 * @param staticCodeBlocks The {@link JavaStaticCodeBlock static code blocks} inside the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> staticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		this.staticCodeBlocks = staticCodeBlocks;
		for(int i = 0; i < staticCodeBlocks.size(); i++){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null));
		}
		return this;
	}
	
	/**
	 * @param staticCodeBlock A {@link JavaStaticCodeBlock static code block} inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> staticCodeBlock(JavaStaticCodeBlock staticCodeBlock){
		staticCodeBlocks.add(staticCodeBlock);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null));
		return this;
	}
	
	/**
	 * @return A {@link JavaStaticCodeBlockBuilder} to use to build a {@link JavaStaticCodeBlock}
	 */
	protected abstract JavaStaticCodeBlockBuilder<?> getStaticCodeBlockBuilder();
	
	/**
	 * @param lines A {@link JavaStaticCodeBlock static code block} as a List of Strings inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> staticCodeBlock(List<String> lines){
		staticCodeBlocks.add(getStaticCodeBlockBuilder()
				.lines(lines)
				.build());
		innerElementsOrder.add(Pair.of(JavaCodeTypes.STATIC_CODE_BLOCK, null));
		return this;
	}
	
	/**
	 * @param singleLineComments The {@link JavaSingleLineComment single-line comments} inside the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> singleLineComments(List<JavaSingleLineComment> singleLineComments){
		this.singleLineComments = singleLineComments;
		for(int i = 0; i < singleLineComments.size(); i++){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		}
		return this;
	}
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> singleLineComment(JavaSingleLineComment singleLineComment){
		singleLineComments.add(singleLineComment);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @return A {@link JavaSingleLineCommentBuilder} to use to build a {@link JavaSingleLineComment}
	 */
	protected abstract JavaSingleLineCommentBuilder<?> getSingleLineCommentBuilder();
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} as a String inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> singleLineComment(String singleLineComment){
		singleLineComments.add(getSingleLineCommentBuilder()
				.content(singleLineComment)
				.build());
		innerElementsOrder.add(Pair.of(JavaCodeTypes.SINGLE_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @param multiLineComments The {@link JavaMultiLineComment multi-line comments} inside the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> multiLineComments(List<JavaMultiLineComment> multiLineComments){
		this.multiLineComments = multiLineComments;
		for(int i = 0; i < multiLineComments.size(); i++){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		}
		return this;
	}
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> multiLineComment(JavaMultiLineComment multiLineComment){
		multiLineComments.add(multiLineComment);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @return A {@link JavaMultiLineCommentBuilder} to use to build a {@link JavaMultiLineComment}
	 */
	protected abstract JavaMultiLineCommentBuilder<?> getMultiLineCommentBuilder();
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} as Strings inside the class type to add to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> multiLineComment(String... multiLineComment){
		multiLineComments.add(getMultiLineCommentBuilder()
				.content(ListUtil.createList(multiLineComment))
				.build());
		innerElementsOrder.add(Pair.of(JavaCodeTypes.MULTI_LINE_COMMENT, null));
		return this;
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} inside the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> innerClasses(List<JavaClass> innerClasses){
		this.innerClasses = innerClasses;
		for(JavaClass innerClass: innerClasses){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.CLASS, innerClass.getSimpleName()));
		}
		return this;
	}
	
	/**
	 * @param innerClass An inner {@link JavaClass class} inside the class type to be added to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> innerClass(JavaClass innerClass){
		this.innerClasses.add(innerClass);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.CLASS, innerClass.getSimpleName()));
		return this;
	}
	
	/**
	 * @param fields The {@link JavaField fields} on the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> fields(List<JavaField> fields){
		this.fields = fields;
		for(JavaField field: fields){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.FIELD, field.getName()));
		}
		return this;
	}
	
	/**
	 * @param field A {@link JavaField field} on the class type, to be added to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> field(JavaField field){
		fields.add(field);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.FIELD, field.getName()));
		return this;
	}
	
	/**
	 * @param methods The {@link JavaMethod methods} in the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> methods(List<JavaMethod> methods){
		this.methods = methods;
		for(JavaMethod method: methods){
			innerElementsOrder.add(Pair.of(JavaCodeTypes.METHOD, method.getUniqueName()));
		}
		return this;
	}
	
	/**
	 * @param method A {@link JavaMethod method} in the class type, to be added to the list
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> method(JavaMethod method){
		methods.add(method);
		innerElementsOrder.add(Pair.of(JavaCodeTypes.METHOD, method.getUniqueName()));
		return this;
	}
	
	/**
	 * @param innerElementsOrder The order of elements inside the class type
	 * @return this, to continue building
	 */
	protected JavaClassTypeBuilder<ClassType> innerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
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
		
		// Name required
		if(name == null){
			errors.add("Must specify name!");
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
		
		// If we have static code blocks or comments, we need innerElementOrder
		if(ListUtil.isBlank(innerElementsOrder) &&
				(ListUtil.isNotBlank(staticCodeBlocks) ||
						ListUtil.isNotBlank(singleLineComments) || ListUtil.isNotBlank(multiLineComments))){
			errors.add("innerElementsOrder is required when static code blocks or comments are present!");
		}
		
		// If innerElementOrder is specified, verify it's valid and includes all inner elements
		if(ListUtil.isNotBlank(innerElementsOrder)){
			// Count static code blocks usage
			int numStaticCodeBlocks = staticCodeBlocks.size();
			// Count comments usage
			int numSingleLineComments = singleLineComments.size(), numMultiLineComments = multiLineComments.size();
			Set<String> innerClassNames = SetUtil.createOrderedSet(innerClasses.stream().map(JavaClass::getSimpleName)
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
					case STATIC_CODE_BLOCK -> {
						numStaticCodeBlocks--;
						if(numStaticCodeBlocks == -1){
							errors.add("Specified more static code blocks in innerElementsOrder than we have!");
						}
					}
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
			// If we didn't use all static code blocks, it's a problem
			if(numStaticCodeBlocks > 0){
				errors.add("Missed " + numStaticCodeBlocks + " static code blocks in innerElementsOrder!");
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
		
		// Inner class types must specify they're inner class types
		if(ListUtil.isNotBlank(innerClasses)){
			for(JavaClass innerClass: innerClasses){
				if(!innerClass.isInnerType()){
					errors.add("Inner class type '" + innerClass.getName() + "' is not an inner type!");
				}
			}
		}
		
		// Inner class type problems
		if(isInnerType){
			if(packageDeclaration != null){
				errors.add("Not allowed to have package declaration for an inner type!");
			}
			
			if(ListUtil.isNotBlank(importStatements)){
				errors.add("Not allowed to have import statements for an inner type!");
			}
		}else{
			// Regular class type problems
			if(isStatic){
				errors.add("Only inner types can be static!");
			}
		}
		
		// Editable in Uneditable
		if(!isEditable){
			// Package Declaration can't be editable
			if(packageDeclaration != null && packageDeclaration.isEditable()){
				errors.add("package declaration is not uneditable in this uneditable JavaClassType");
			}
			
			// Import Statements can't be editable
			for(JavaImportStatement importStatement: importStatements){
				if(importStatement.isEditable()){
					errors.add("some import statements are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Javadoc can't be editable
			if(javadoc != null && javadoc.isEditable()){
				errors.add("javadoc is not uneditable in this uneditable JavaClassType");
			}
			
			// Annotations can't be editable
			for(JavaAnnotation annotation: annotations){
				if(annotation.isEditable()){
					errors.add("some annotations are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Static Code Blocks can't be editable
			for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
				if(staticCodeBlock.isEditable()){
					errors.add("some static code blocks are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Single-Line Comments can't be editable
			for(JavaSingleLineComment singleLineComment: singleLineComments){
				if(singleLineComment.isEditable()){
					errors.add("some single-line comments are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Multi-Line Comments can't be editable
			for(JavaMultiLineComment multiLineComment: multiLineComments){
				if(multiLineComment.isEditable()){
					errors.add("some multi-line comments are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Inner Classes can't be editable
			for(JavaClass clazz: innerClasses){
				if(clazz.isEditable()){
					errors.add("some inner classes are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Fields can't be editable
			for(JavaField field: fields){
				if(field.isEditable()){
					errors.add("some fields are not uneditable in this uneditable JavaClassType");
					break;
				}
			}
			
			// Methods can't be editable
			for(JavaMethod method: methods){
				if(method.isEditable()){
					errors.add("some methods are not uneditable in this uneditable JavaClassType");
				}
			}
		}else{
			// Package Declaration can't be uneditable
			if(packageDeclaration != null && !packageDeclaration.isEditable()){
				errors.add("package declaration is not editable in this editable JavaClassType");
			}
			
			// Import Statements can't be uneditable
			for(JavaImportStatement importStatement: importStatements){
				if(!importStatement.isEditable()){
					errors.add("some import statements are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Javadoc can't be uneditable
			if(javadoc != null && !javadoc.isEditable()){
				errors.add("javadoc is not editable in this editable JavaClassType");
			}
			
			// Annotations can't be uneditable
			for(JavaAnnotation annotation: annotations){
				if(!annotation.isEditable()){
					errors.add("some annotations are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Static Code Blocks can't be uneditable
			for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
				if(!staticCodeBlock.isEditable()){
					errors.add("some static code blocks are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Single-Line Comments can't be uneditable
			for(JavaSingleLineComment singleLineComment: singleLineComments){
				if(!singleLineComment.isEditable()){
					errors.add("some single-line comments are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Multi-Line Comments can't be uneditable
			for(JavaMultiLineComment multiLineComment: multiLineComments){
				if(!multiLineComment.isEditable()){
					errors.add("some multi-line comments are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Inner Classes can't be uneditable
			for(JavaClass clazz: innerClasses){
				if(!clazz.isEditable()){
					errors.add("some inner classes are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Fields can't be uneditable
			for(JavaField field: fields){
				if(!field.isEditable()){
					errors.add("some fields are not editable in this editable JavaClassType");
					break;
				}
			}
			
			// Methods can't be uneditable
			for(JavaMethod method: methods){
				if(!method.isEditable()){
					errors.add("some methods are not editable in this editable JavaClassType");
				}
			}
		}
		
		// Include type specific errors too
		errors.addAll(checkForTypeSpecificErrors());
		
		if(!errors.isEmpty()){
			throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
		}
	}
	
	/**
	 * Checks for any errors in the current parameters for the current type
	 *
	 * @return A List of errors specific to the type
	 */
	protected abstract List<String> checkForTypeSpecificErrors();
	
	/**
	 * Checks for any errors in the current parameters, then builds a new {@link JavaClassType}
	 *
	 * @return A newly built {@link JavaClassType}
	 * @throws IllegalArgumentException if anything is wrong with the current parameters
	 */
	public ClassType build(){
		// Run the error check
		checkForErrors();
		
		// Actually build the Java Class Type
		return constructClass();
	}
	
	/**
	 * Constructs a {@link JavaClassType} using the set parameters
	 *
	 * @return The newly built {@link JavaClassType}
	 */
	protected abstract ClassType constructClass();
}
