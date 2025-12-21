package com.github.tadukoo.java.classtype;

import com.github.tadukoo.java.JavaCodeType;
import com.github.tadukoo.java.JavaCodeTypes;
import com.github.tadukoo.java.JavaType;
import com.github.tadukoo.java.Visibility;
import com.github.tadukoo.java.annotation.JavaAnnotation;
import com.github.tadukoo.java.code.staticcodeblock.JavaStaticCodeBlock;
import com.github.tadukoo.java.comment.JavaMultiLineComment;
import com.github.tadukoo.java.comment.JavaSingleLineComment;
import com.github.tadukoo.java.field.JavaField;
import com.github.tadukoo.java.importstatement.EditableJavaImportStatement;
import com.github.tadukoo.java.importstatement.JavaImportStatement;
import com.github.tadukoo.java.javaannotationclass.JavaAnnotationClass;
import com.github.tadukoo.java.javaclass.JavaClass;
import com.github.tadukoo.java.javadoc.Javadoc;
import com.github.tadukoo.java.javaenum.JavaEnum;
import com.github.tadukoo.java.javainterface.JavaInterface;
import com.github.tadukoo.java.javarecord.JavaRecord;
import com.github.tadukoo.java.method.JavaMethod;
import com.github.tadukoo.java.packagedeclaration.EditableJavaPackageDeclaration;
import com.github.tadukoo.java.packagedeclaration.JavaPackageDeclaration;
import com.github.tadukoo.java.parsing.FullJavaParser;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.map.HashMultiMap;
import com.github.tadukoo.util.map.MultiMap;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class JavaClassType implements JavaCodeType{
	
	/** Whether this class type is editable or not */
	protected boolean editable;
	/** Whether this is an inner class type or not */
	protected boolean isInnerType;
	/** The {@link JavaPackageDeclaration package declaration} of the class type */
	protected JavaPackageDeclaration packageDeclaration;
	/** The {@link JavaImportStatement import statements} of the class type */
	protected List<JavaImportStatement> importStatements;
	/** The {@link Javadoc} for the class type */
	protected Javadoc javadoc;
	/** The {@link JavaAnnotation annotations} on the class type */
	protected List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the class type */
	protected Visibility visibility;
	/** Whether this is an abstract class type or not */
	protected boolean isAbstract;
	/** Whether this is a static class type or not */
	protected boolean isStatic;
	/** Whether this is a final class type or not */
	protected boolean isFinal;
	/** The actual class type */
	protected JavaCodeTypes type;
	/** The name (and type parameters in a {@link JavaType}) of the class type */
	protected JavaType name;
	/** {@link JavaType types} that this class type extends */
	protected List<JavaType> extendsTypes;
	/** {@link JavaType types} that this class type implements */
	protected List<JavaType> implementsTypes;
	/** The {@link JavaStaticCodeBlock static code blocks} inside the class type */
	protected List<JavaStaticCodeBlock> staticCodeBlocks;
	/** The {@link JavaSingleLineComment single-line comments} inside the class type */
	protected List<JavaSingleLineComment> singleLineComments;
	/** The {@link JavaMultiLineComment multi-line comments} inside the class type */
	protected List<JavaMultiLineComment> multiLineComments;
	/** Inner {@link JavaClass classes} inside the class type */
	protected List<JavaClass> innerClasses;
	/** Inner {@link JavaEnum enums} inside the class type */
	protected List<JavaEnum> innerEnums;
	/** Inner {@link JavaInterface interfaces} inside the class type */
	protected List<JavaInterface> innerInterfaces;
	/** Inner {@link JavaRecord records} inside the class type */
	protected List<JavaRecord> innerRecords;
	/** Inner {@link JavaAnnotationClass annotation classes} inside the class type */
	protected List<JavaAnnotationClass> innerAnnotationClasses;
	/** The {@link JavaField fields} on the class type */
	protected List<JavaField> fields;
	/** The {@link JavaMethod methods} in the class type */
	protected List<JavaMethod> methods;
	/** The order of the elements inside the class type */
	protected List<Pair<JavaCodeTypes, String>> innerElementsOrder;
	
	protected JavaClassType(
			boolean editable, boolean isInnerType,
			JavaPackageDeclaration packageDeclaration, List<JavaImportStatement> importStatements,
			Javadoc javadoc, List<JavaAnnotation> annotations,
			Visibility visibility, boolean isAbstract, boolean isStatic, boolean isFinal,
			JavaCodeTypes type, JavaType name,
			List<JavaType> extendsTypes, List<JavaType> implementsTypes,
			List<JavaStaticCodeBlock> staticCodeBlocks,
			List<JavaSingleLineComment> singleLineComments, List<JavaMultiLineComment> multiLineComments,
			List<JavaClass> innerClasses, List<JavaEnum> innerEnums, List<JavaInterface> innerInterfaces,
			List<JavaRecord> innerRecords, List<JavaAnnotationClass> innerAnnotationClasses,
			List<JavaField> fields, List<JavaMethod> methods,
			List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		this.editable = editable;
		this.isInnerType = isInnerType;
		this.packageDeclaration = packageDeclaration;
		this.importStatements = importStatements;
		this.javadoc = javadoc;
		this.annotations = annotations;
		this.visibility = visibility;
		this.isAbstract = isAbstract;
		this.isStatic = isStatic;
		this.isFinal = isFinal;
		this.type = type;
		this.name = name;
		this.extendsTypes = extendsTypes;
		this.implementsTypes = implementsTypes;
		this.staticCodeBlocks = staticCodeBlocks;
		this.singleLineComments = singleLineComments;
		this.multiLineComments = multiLineComments;
		this.innerClasses = innerClasses;
		this.innerEnums = innerEnums;
		this.innerInterfaces = innerInterfaces;
		this.innerRecords = innerRecords;
		this.innerAnnotationClasses = innerAnnotationClasses;
		this.fields = fields;
		this.methods = methods;
		this.innerElementsOrder = innerElementsOrder;
	}
	
	/** {@inheritDoc} */
	@Override
	public JavaCodeTypes getJavaCodeType(){
		return type;
	}
	
	/**
	 * @return Whether this class type is editable or not
	 */
	public boolean isEditable(){
		return editable;
	}
	
	/**
	 * @return Whether this class type is an inner type or not
	 */
	public boolean isInnerType(){
		return isInnerType;
	}
	
	/**
	 * @param isInnerType Whether this is an inner class type or not
	 */
	protected void setInnerType(boolean isInnerType){
		this.isInnerType = isInnerType;
	}
	
	/**
	 * @return The {@link JavaPackageDeclaration package declaration} of the class type
	 */
	public JavaPackageDeclaration getPackageDeclaration(){
		return packageDeclaration;
	}
	
	/**
	 * @param packageDeclaration The {@link JavaPackageDeclaration package declaration} of the class type
	 */
	protected void setPackageDeclaration(JavaPackageDeclaration packageDeclaration){
		if(!packageDeclaration.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable package declaration");
		}
		this.packageDeclaration = packageDeclaration;
	}
	
	/**
	 * @param packageName The package name of the class type, which gets put in a {@link JavaPackageDeclaration}
	 */
	protected void setPackageName(String packageName){
		packageDeclaration = EditableJavaPackageDeclaration.builder()
				.packageName(packageName)
				.build();
	}
	
	/**
	 * @return The {@link JavaImportStatement import statements} of the class type
	 */
	public List<JavaImportStatement> getImportStatements(){
		return importStatements;
	}
	
	/**
	 * @param importStatement An {@link JavaImportStatement import statement} of this class type to be added
	 */
	protected void addImportStatement(JavaImportStatement importStatement){
		if(!importStatement.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable import statements");
		}
		importStatements.add(importStatement);
	}
	
	/**
	 * @param importStatements {@link JavaImportStatement import statements} of this class type to be added
	 */
	protected void addImportStatements(List<JavaImportStatement> importStatements){
		for(JavaImportStatement importStatement: importStatements){
			if(!importStatement.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable import statements");
			}
		}
		this.importStatements.addAll(importStatements);
	}
	
	/**
	 * @param importStatements The {@link JavaImportStatement import statements} of this class type
	 */
	protected void setImportStatements(List<JavaImportStatement> importStatements){
		for(JavaImportStatement importStatement: importStatements){
			if(!importStatement.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable import statements");
			}
		}
		this.importStatements = importStatements;
	}
	
	/**
	 * @param importName The name of an import to add to the class type (becomes a {@link JavaImportStatement})
	 * @param isStatic Whether the import is static or not
	 */
	protected void addImportName(String importName, boolean isStatic){
		importStatements.add(EditableJavaImportStatement.builder()
				.isStatic(isStatic).importName(importName)
				.build());
	}
	
	/**
	 * @param importNames The names of imports to add to the class type (become {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 */
	protected void addImportNames(List<String> importNames, boolean isStatic){
		for(String importName: importNames){
			importStatements.add(EditableJavaImportStatement.builder()
					.isStatic(isStatic).importName(importName)
					.build());
		}
	}
	
	/**
	 * @param importNames The import names to set as the only imports on the class type (they become {@link JavaImportStatement import statements})
	 * @param isStatic Whether the imports are static or not
	 */
	protected void setImportNames(List<String> importNames, boolean isStatic){
		importStatements = new ArrayList<>();
		addImportNames(importNames, isStatic);
	}
	
	/**
	 * @return The {@link Javadoc} for the class type
	 */
	public Javadoc getJavadoc(){
		return javadoc;
	}
	
	/**
	 * @param javadoc The {@link Javadoc} for the class type
	 */
	protected void setJavadoc(Javadoc javadoc){
		if(!javadoc.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable Javadoc");
		}
		this.javadoc = javadoc;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the class type
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @param annotation An {@link JavaAnnotation annotation} to be added to the class type - must be editable
	 */
	protected void addAnnotation(JavaAnnotation annotation){
		if(!annotation.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable Java Annotations");
		}
		annotations.add(annotation);
	}
	
	/**
	 * @param annotations {@link JavaAnnotation annotations} to be added to the class type - must be editable
	 */
	protected void addAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Annotations");
			}
		}
		this.annotations.addAll(annotations);
	}
	
	/**
	 * @param annotations The {@link JavaAnnotation annotations} on the class type - must be editable
	 */
	protected void setAnnotations(List<JavaAnnotation> annotations){
		for(JavaAnnotation annotation: annotations){
			if(!annotation.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Annotations");
			}
		}
		this.annotations = annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the class type
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @param visibility The {@link Visibility} of the class type
	 */
	protected void setVisibility(Visibility visibility){
		this.visibility = visibility;
	}
	
	/**
	 * @return Whether this class type is abstract or not
	 */
	protected boolean isAbstract(){
		return isAbstract;
	}
	
	/**
	 * @param isAbstract Whether this is an abstract class type or not
	 */
	protected void setAbstract(boolean isAbstract){
		this.isAbstract = isAbstract;
	}
	
	/**
	 * @return Whether this class type is static or not
	 */
	public boolean isStatic(){
		return isStatic;
	}
	
	/**
	 * @param isStatic Whether this is a static class type or not
	 */
	protected void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	
	/**
	 * @return Whether this class type is final or not
	 */
	protected boolean isFinal(){
		return isFinal;
	}
	
	/**
	 * @param isFinal Whether this is a final class type or not
	 */
	protected void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}
	
	/**
	 * @return The name of the class type, along with type parameters to form a {@link JavaType}
	 */
	public JavaType getName(){
		return name;
	}
	
	/**
	 * @return The simple name of the class type, not including any type parameters
	 */
	public String getSimpleName(){
		return name.getBaseType();
	}
	
	/**
	 * @param name The name of the class type, along with type parameters to form a {@link JavaType}
	 */
	protected void setName(JavaType name){
		this.name = name;
	}
	
	/**
	 * @param nameText The text to parse of the name of the class type,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void setName(String nameText){
		this.name = FullJavaParser.parseJavaType(nameText);
	}
	
	/**
	 * @return The name of the class types this one extends (can be empty), along with type parameters to form {@link JavaType}
	 */
	protected List<JavaType> getExtendsTypes(){
		return extendsTypes;
	}
	
	/**
	 * @param extendsType The name of a class type this one extends to be added,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addExtendsType(JavaType extendsType){
		extendsTypes.add(extendsType);
	}
	
	/**
	 * @param extendsTypes The name of class types this one extends to be added,
	 * along with type parameters to form {@link JavaType}
	 */
	protected void addExtendsTypes(List<JavaType> extendsTypes){
		this.extendsTypes.addAll(extendsTypes);
	}
	
	/**
	 * @param extendsTypes The name of the class types this one extends (can be empty), along with type parameters to form {@link JavaType}
	 */
	protected void setExtendsTypes(List<JavaType> extendsTypes){
		this.extendsTypes = extendsTypes;
	}
	
	/**
	 * @param extendsTypeText The name of a class type this one extends as text to be added,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addExtendsTypeText(String extendsTypeText){
		extendsTypes.add(FullJavaParser.parseJavaType(extendsTypeText));
	}
	
	/**
	 * @param extendsTypeTexts The name of class types this one extends as text to be added,
	 * along with type parameters to form {@link JavaType}
	 */
	protected void addExtendsTypeTexts(List<String> extendsTypeTexts){
		extendsTypes.addAll(extendsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType)
				.toList());
	}
	
	/**
	 * @param extendsTypeTexts The name of the class types this one extends as text (can be empty),
	 * along with type parameters to form {@link JavaType}
	 */
	protected void setExtendsTypeTexts(List<String> extendsTypeTexts){
		extendsTypes = extendsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType)
				.collect(Collectors.toList());
	}
	
	/**
	 * @return The names of class types this class type implements, along with type parameters to form a {@link JavaType}
	 */
	protected List<JavaType> getImplementsTypes(){
		return implementsTypes;
	}
	
	/**
	 * @param implementsType The name of a class type this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addImplementsType(JavaType implementsType){
		implementsTypes.add(implementsType);
	}
	
	/**
	 * @param implementsTypeText The text for the name of a class type this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addImplementsType(String implementsTypeText){
		implementsTypes.add(FullJavaParser.parseJavaType(implementsTypeText));
	}
	
	/**
	 * @param implementsTypes The names of class types this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addImplementsTypes(List<JavaType> implementsTypes){
		this.implementsTypes.addAll(implementsTypes);
	}
	
	/**
	 * @param implementsTypeTexts The text of the names of class type this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void addImplementsTypeTexts(List<String> implementsTypeTexts){
		this.implementsTypes.addAll(implementsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType).toList());
	}
	
	/**
	 * @param implementsTypes The names of class types this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void setImplementsTypes(List<JavaType> implementsTypes){
		this.implementsTypes = implementsTypes;
	}
	
	/**
	 * @param implementsTypeTexts The text for the names of class types this class type implements,
	 * along with type parameters to form a {@link JavaType}
	 */
	protected void setImplementsTypeTexts(List<String> implementsTypeTexts){
		this.implementsTypes = implementsTypeTexts.stream()
				.map(FullJavaParser::parseJavaType).collect(Collectors.toList());
	}
	
	/**
	 * @return The {@link JavaStaticCodeBlock static code blocks} inside this class type
	 */
	protected List<JavaStaticCodeBlock> getStaticCodeBlocks(){
		return staticCodeBlocks;
	}
	
	/**
	 * @param staticCodeBlock A {@link JavaStaticCodeBlock static code block} to be added inside the class type
	 * - must be editable
	 */
	protected void addStaticCodeBlock(JavaStaticCodeBlock staticCodeBlock){
		if(!staticCodeBlock.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable static code blocks");
		}
		staticCodeBlocks.add(staticCodeBlock);
	}
	
	/**
	 * @param staticCodeBlocks {@link JavaStaticCodeBlock static code blocks} to be added inside the class type
	 * - must be editable
	 */
	protected void addStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
			if(!staticCodeBlock.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable static code blocks");
			}
		}
		this.staticCodeBlocks.addAll(staticCodeBlocks);
	}
	
	/**
	 * @param staticCodeBlocks The {@link JavaStaticCodeBlock static code blocks} inside the class type
	 * - must be editable
	 */
	protected void setStaticCodeBlocks(List<JavaStaticCodeBlock> staticCodeBlocks){
		for(JavaStaticCodeBlock staticCodeBlock: staticCodeBlocks){
			if(!staticCodeBlock.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable static code blocks");
			}
		}
		this.staticCodeBlocks = staticCodeBlocks;
	}
	
	/**
	 * @return The {@link JavaSingleLineComment single-line comments} inside this class type
	 */
	public List<JavaSingleLineComment> getSingleLineComments(){
		return singleLineComments;
	}
	
	/**
	 * @param singleLineComment A {@link JavaSingleLineComment single-line comment} to be added inside the class type
	 * - must be editable
	 */
	protected void addSingleLineComment(JavaSingleLineComment singleLineComment){
		if(!singleLineComment.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable single-line comments");
		}
		singleLineComments.add(singleLineComment);
	}
	
	/**
	 * @param singleLineComments {@link JavaSingleLineComment single-line comments} to be added inside the class type
	 * - must be editable
	 */
	protected void addSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		for(JavaSingleLineComment singleLineComment: singleLineComments){
			if(!singleLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable single-line comments");
			}
		}
		this.singleLineComments.addAll(singleLineComments);
	}
	
	/**
	 * @param singleLineComments {@link JavaSingleLineComment single-line comments} inside the class type - must be editable
	 */
	protected void setSingleLineComments(List<JavaSingleLineComment> singleLineComments){
		for(JavaSingleLineComment singleLineComment: singleLineComments){
			if(!singleLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable single-line comments");
			}
		}
		this.singleLineComments = singleLineComments;
	}
	
	/**
	 * @return The {@link JavaMultiLineComment multi-line comments} inside this class type
	 */
	public List<JavaMultiLineComment> getMultiLineComments(){
		return multiLineComments;
	}
	
	/**
	 * @param multiLineComment A {@link JavaMultiLineComment multi-line comment} to be added inside the class type
	 * - must be editable
	 */
	protected void addMultiLineComment(JavaMultiLineComment multiLineComment){
		if(!multiLineComment.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable multi-line comments");
		}
		multiLineComments.add(multiLineComment);
	}
	
	/**
	 * @param multiLineComments {@link JavaMultiLineComment multi-line comments} to be added inside the class type
	 * - must be editable
	 */
	protected void addMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		for(JavaMultiLineComment multiLineComment: multiLineComments){
			if(!multiLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable multi-line comments");
			}
		}
		this.multiLineComments.addAll(multiLineComments);
	}
	
	/**
	 * @param multiLineComments {@link JavaMultiLineComment multi-line comments} inside the class type - must be editable
	 */
	protected void setMultiLineComments(List<JavaMultiLineComment> multiLineComments){
		for(JavaMultiLineComment multiLineComment: multiLineComments){
			if(!multiLineComment.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable multi-line comments");
			}
		}
		this.multiLineComments = multiLineComments;
	}
	
	/**
	 * @param classTypes A List of {@link JavaClassType}s to make a map out of
	 * @return A map of the {@link JavaClassType}s by their simpleName
	 * @param <ClassType> The type of {@link JavaClassType} to make a map out of
	 */
	protected <ClassType extends JavaClassType> Map<String, ClassType> getInnerTypesMap(List<ClassType> classTypes){
		Map<String, ClassType> classTypeMap = new HashMap<>();
		for(ClassType classType: classTypes){
			classTypeMap.put(classType.getSimpleName(), classType);
		}
		return classTypeMap;
	}
	
	/**
	 * @return Inner {@link JavaClass classes} inside this class type
	 */
	public List<JavaClass> getInnerClasses(){
		return innerClasses;
	}
	
	/**
	 * @return The {@link JavaClass classes} inside this class type as a Map by simple name
	 */
	public Map<String, JavaClass> getInnerClassesMap(){
		return getInnerTypesMap(innerClasses);
	}
	
	/**
	 * @param innerClass An inner {@link JavaClass class} to be added inside the class type - must be editable
	 */
	protected void addInnerClass(JavaClass innerClass){
		if(!innerClass.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable inner classes");
		}
		innerClasses.add(innerClass);
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} to be added inside the class type - must be editable
	 */
	protected void addInnerClasses(List<JavaClass> innerClasses){
		for(JavaClass innerClass: innerClasses){
			if(!innerClass.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable inner classes");
			}
		}
		this.innerClasses.addAll(innerClasses);
	}
	
	/**
	 * @param innerClasses Inner {@link JavaClass classes} inside the class type - must be editable
	 */
	protected void setInnerClasses(List<JavaClass> innerClasses){
		for(JavaClass innerClass: innerClasses){
			if(!innerClass.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable inner classes");
			}
		}
		this.innerClasses = innerClasses;
	}
	
	/**
	 * @return Inner {@link JavaEnum enums} inside this class type
	 */
	public List<JavaEnum> getInnerEnums(){
		return innerEnums;
	}
	
	/**
	 * @return The {@link JavaEnum enums} inside this class type as a Map by simple name
	 */
	public Map<String, JavaEnum> getInnerEnumsMap(){
		return getInnerTypesMap(innerEnums);
	}
	
	/**
	 * @return Inner {@link JavaInterface interfaces} inside the class type
	 */
	public List<JavaInterface> getInnerInterfaces(){
		return innerInterfaces;
	}
	
	/**
	 * @return The {@link JavaInterface interfaces} inside this class type as a Map by simple name
	 */
	public Map<String, JavaInterface> getInnerInterfacesMap(){
		return getInnerTypesMap(innerInterfaces);
	}
	
	/**
	 * @return Inner {@link JavaRecord records} inside the class type
	 */
	public List<JavaRecord> getInnerRecords(){
		return innerRecords;
	}
	
	/**
	 * @return The {@link JavaRecord records} inside this class type as a Map by simple name
	 */
	public Map<String, JavaRecord> getInnerRecordsMap(){
		return getInnerTypesMap(innerRecords);
	}
	
	/**
	 * @return Inner {@link JavaAnnotationClass annotation classes} inside the class type
	 */
	public List<JavaAnnotationClass> getInnerAnnotationClasses(){
		return innerAnnotationClasses;
	}
	
	/**
	 * @return The {@link JavaAnnotationClass annotation classes} inside this class type as a Map by simple name
	 */
	public Map<String, JavaAnnotationClass> getInnerAnnotationClassesMap(){
		return getInnerTypesMap(innerAnnotationClasses);
	}
	
	/**
	 * @return The {@link JavaField fields} on the class type
	 */
	protected List<JavaField> getFields(){
		return fields;
	}
	
	/**
	 * @return The {@link JavaField fields} on the class type as a Map by field name
	 */
	protected Map<String, JavaField> getFieldsMap(){
		Map<String, JavaField> fieldMap = new HashMap<>();
		for(JavaField field: fields){
			fieldMap.put(field.getName(), field);
		}
		return fieldMap;
	}
	
	/**
	 * @param field A {@link JavaField field} to be added to the class type - must be editable
	 */
	protected void addField(JavaField field){
		if(!field.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable Java Fields");
		}
		fields.add(field);
	}
	
	/**
	 * @param fields {@link JavaField fields} to be added to the class type - must be editable
	 */
	protected void addFields(List<JavaField> fields){
		for(JavaField field: fields){
			if(!field.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Fields");
			}
		}
		this.fields.addAll(fields);
	}
	
	/**
	 * @param fields The {@link JavaField fields} on the class type - must be editable
	 */
	protected void setFields(List<JavaField> fields){
		for(JavaField field: fields){
			if(!field.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Fields");
			}
		}
		this.fields = fields;
	}
	
	/**
	 * @return The {@link JavaMethod methods} in the class type
	 */
	protected List<JavaMethod> getMethods(){
		return methods;
	}
	
	/**
	 * @return The {@link JavaMethod methods} on the class type as a Map by method name (using {@link JavaMethod#getUniqueName()})
	 */
	protected Map<String, JavaMethod> getMethodsMap(){
		Map<String, JavaMethod> methodMap = new HashMap<>();
		for(JavaMethod method: methods){
			methodMap.put(method.getUniqueName(), method);
		}
		return methodMap;
	}
	
	/**
	 * @param method A {@link JavaMethod method} to add to the class type - must be editable
	 */
	protected void addMethod(JavaMethod method){
		if(!method.isEditable()){
			throw new IllegalArgumentException("editable Java Class Type requires editable Java Methods");
		}
		methods.add(method);
	}
	
	/**
	 * @param methods {@link JavaMethod methods} to add to the class type - must be editable
	 */
	protected void addMethods(List<JavaMethod> methods){
		for(JavaMethod method: methods){
			if(!method.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Methods");
			}
		}
		this.methods.addAll(methods);
	}
	
	/**
	 * @param methods The {@link JavaMethod methods} in the class type - must be editable
	 */
	protected void setMethods(List<JavaMethod> methods){
		for(JavaMethod method: methods){
			if(!method.isEditable()){
				throw new IllegalArgumentException("editable Java Class Type requires editable Java Methods");
			}
		}
		this.methods = methods;
	}
	
	/**
	 * @return The order of elements inside the class type
	 */
	public List<Pair<JavaCodeTypes, String>> getInnerElementsOrder(){
		return innerElementsOrder;
	}
	
	/**
	 * @param innerElementsOrder The order of the elements inside the class type
	 */
	protected void setInnerElementsOrder(List<Pair<JavaCodeTypes, String>> innerElementsOrder){
		this.innerElementsOrder = innerElementsOrder;
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
	
	/**
	 * @return The actual Java code this {@link JavaClassType} represents
	 */
	@Override
	public String toString(){
		// Create a list of the lines of the class type
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
		 * Class Type Declaration
		 */
		// Start with visibility
		StringBuilder declaration = new StringBuilder(visibility.getToken());
		if(!declaration.isEmpty()){
			// If visibility is not NONE, we need a space after it
			declaration.append(' ');
		}
		
		// Optionally add abstract to the class declaration
		if(isAbstract){
			declaration.append(ABSTRACT_MODIFIER).append(' ');
		}
		
		// Optionally add static to the class declaration
		if(isStatic){
			declaration.append(STATIC_MODIFIER).append(' ');
		}
		
		// Optionally add final to the class declaration
		if(isFinal){
			declaration.append(FINAL_MODIFIER).append(' ');
		}
		
		// Append class type token and name to the declaration
		declaration.append(type.getStandardName()).append(' ').append(name);
		
		// Optionally append extends types to the declaration
		if(ListUtil.isNotBlank(extendsTypes)){
			declaration.append(' ').append(EXTENDS_TOKEN);
			for(JavaType extendsType: extendsTypes){
				declaration.append(' ').append(extendsType).append(',');
			}
			// Remove final comma
			declaration.deleteCharAt(declaration.length()-1);
		}
		
		// Optionally append implements types to the declaration
		if(ListUtil.isNotBlank(implementsTypes)){
			declaration.append(' ').append(IMPLEMENTS_TOKEN);
			for(JavaType implementsType: implementsTypes){
				declaration.append(' ').append(implementsType).append(',');
			}
			// Remove final comma
			declaration.deleteCharAt(declaration.length()-1);
		}
		
		// End the declaration by opening the code block
		declaration.append(BLOCK_OPEN_TOKEN);
		content.add(declaration.toString());
		
		// Newline at start of class type
		content.add("\t");
		
		// Handle inner elements
		if(ListUtil.isNotBlank(innerElementsOrder)){
			int staticCodeBlockIndex = 0, singleLineCommentIndex = 0, multiLineCommentIndex = 0;
			Map<String, JavaClass> innerClassesByName = getInnerClassesMap();
			Map<String, JavaEnum> innerEnumsByName = getInnerEnumsMap();
			Map<String, JavaInterface> innerInterfacesByName = getInnerInterfacesMap();
			Map<String, JavaRecord> innerRecordsByName = getInnerRecordsMap();
			Map<String, JavaAnnotationClass> innerAnnotationClassesByName = getInnerAnnotationClassesMap();
			Map<String, JavaField> fieldsByName = getFieldsMap();
			Map<String, JavaMethod> methodsByName = getMethodsMap();
			JavaCodeTypes lastType = null;
			for(Pair<JavaCodeTypes, String> elementInfo: innerElementsOrder){
				switch(elementInfo.getLeft()){
					case STATIC_CODE_BLOCK -> {
						content.add(StringUtil.indentAllLines(
								staticCodeBlocks.get(staticCodeBlockIndex).toString()));
						staticCodeBlockIndex++;
					}
					case SINGLE_LINE_COMMENT -> {
						content.add(StringUtil.indentAllLines(
								singleLineComments.get(singleLineCommentIndex).toString()));
						singleLineCommentIndex++;
					}
					case MULTI_LINE_COMMENT -> {
						content.add(StringUtil.indentAllLines(multiLineComments.get(multiLineCommentIndex).toString()));
						content.add("\t");
						multiLineCommentIndex++;
					}
					case CLASS -> content.add(StringUtil.indentAllLines(
							innerClassesByName.get(elementInfo.getRight()).toString()));
					case ENUM -> content.add(StringUtil.indentAllLines(
							innerEnumsByName.get(elementInfo.getRight()).toString()));
					case INTERFACE -> content.add(StringUtil.indentAllLines(
							innerInterfacesByName.get(elementInfo.getRight()).toString()));
					case RECORD -> content.add(StringUtil.indentAllLines(
							innerRecordsByName.get(elementInfo.getRight()).toString()));
					case ANNOTATION_CLASS -> content.add(StringUtil.indentAllLines(
							innerAnnotationClassesByName.get(elementInfo.getRight()).toString()));
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
			// Default order is inner classes (+ enums, interfaces, records, annotation classes), then fields, then methods
			
			// Inner classes of the class type
			if(ListUtil.isNotBlank(innerClasses)){
				for(JavaClass clazz: innerClasses){
					content.add(StringUtil.indentAllLines(clazz.toString()));
				}
			}
			
			// Inner Enums of the class type
			if(ListUtil.isNotBlank(innerEnums)){
				for(JavaEnum innerEnum: innerEnums){
					content.add(StringUtil.indentAllLines(innerEnum.toString()));
				}
			}
			
			// Inner Interfaces of the class type
			if(ListUtil.isNotBlank(innerInterfaces)){
				for(JavaInterface innerInterface: innerInterfaces){
					content.add(StringUtil.indentAllLines(innerInterface.toString()));
				}
			}
			
			// Inner Records of the class type
			if(ListUtil.isNotBlank(innerRecords)){
				for(JavaRecord innerRecord: innerRecords){
					content.add(StringUtil.indentAllLines(innerRecord.toString()));
				}
			}
			
			// Inner Annotation Classes of the class type
			if(ListUtil.isNotBlank(innerAnnotationClasses)){
				for(JavaAnnotationClass innerAnnotationClass: innerAnnotationClasses){
					content.add(StringUtil.indentAllLines(innerAnnotationClass.toString()));
				}
			}
			
			// Fields on the class
			if(ListUtil.isNotBlank(fields)){
				for(JavaField field: fields){
					// Use indent all lines because Javadoc may make it multiline
					content.add(StringUtil.indentAllLines(field.toString()));
				}
			}
			
			// Methods in the class type
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
		
		// Closing brace at end of class type and empty newline at end of file
		content.add(BLOCK_CLOSE_TOKEN);
		content.add("");
		
		// Build the full string
		return StringUtil.buildStringWithNewLines(content);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toBuilderCode(){
		// Start builder
		StringBuilder codeString = new StringBuilder(this.getClass().getSimpleName()).append(".builder()");
		
		// Add isInnerType if present
		if(isInnerType){
			codeString.append(NEWLINE_WITH_2_TABS).append(".innerType()");
		}
		
		// Add Package Declaration if present
		if(packageDeclaration != null){
			codeString.append(NEWLINE_WITH_2_TABS).append(".packageName(\"").append(packageDeclaration.getPackageName())
					.append("\")");
		}
		
		// Add Import Statements if present
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
							.map(JavaImportStatement::getImportName)
							.sorted().toList();
					for(String importName: alphabetizedImports){
						codeString.append(NEWLINE_WITH_2_TABS).append(".importName(\"")
								.append(importName).append("\", false)");
					}
				}
			}
			
			// Handle static imports if we have them
			if(ListUtil.isNotBlank(staticImports)){
				MultiMap<String, JavaImportStatement> sortedImports = sortImports(staticImports);
				List<String> alphabetizedKeys = sortedImports.keySet().stream().sorted().toList();
				for(String key: alphabetizedKeys){
					List<String> alphabetizedImports = sortedImports.get(key).stream()
							.map(JavaImportStatement::getImportName)
							.sorted().toList();
					for(String importName: alphabetizedImports){
						codeString.append(NEWLINE_WITH_2_TABS).append(".importName(\"")
								.append(importName).append("\", true)");
					}
				}
			}
		}
		
		// Add Javadoc if present
		if(javadoc != null){
			codeString.append(NEWLINE_WITH_2_TABS).append(".javadoc(")
					.append(javadoc.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
					.append(')');
		}
		
		// Add Annotations if present
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				codeString.append(NEWLINE_WITH_2_TABS).append(".annotation(")
						.append(annotation.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
						.append(')');
			}
		}
		
		// Add Visibility
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
		
		// Class Name
		codeString.append(NEWLINE_WITH_2_TABS).append(".name(\"").append(name).append("\")");
		
		// Extends
		if(ListUtil.isNotBlank(extendsTypes)){
			for(JavaType extendsType: extendsTypes){
				codeString.append(NEWLINE_WITH_2_TABS).append(".extendsType(\"").append(extendsType).append("\")");
			}
		}
		
		// Implements
		if(ListUtil.isNotBlank(implementsTypes)){
			for(JavaType implementsType: implementsTypes){
				codeString.append(NEWLINE_WITH_2_TABS).append(".implementsType(\"")
						.append(implementsType).append("\")");
			}
		}
		
		// Other types based on innerElementsOrder
		if(ListUtil.isNotBlank(innerElementsOrder)){
			int staticCodeBlockIndex = 0, singleLineCommentIndex = 0, multiLineCommentIndex = 0;
			Map<String, JavaClass> innerClassesMap = getInnerClassesMap();
			Map<String, JavaField> fieldsMap = getFieldsMap();
			Map<String, JavaMethod> methodsMap = getMethodsMap();
			
			for(Pair<JavaCodeTypes, String> innerElementInfo: innerElementsOrder){
				switch(innerElementInfo.getLeft()){
					case STATIC_CODE_BLOCK -> {
						codeString.append(NEWLINE_WITH_2_TABS).append(".staticCodeBlock(ListUtil.createList(");
						for(String line: staticCodeBlocks.get(staticCodeBlockIndex).getLines()){
							codeString.append(NEWLINE_WITH_4_TABS).append('"').append(line).append('"').append(",");
						}
						codeString.delete(codeString.length()-1, codeString.length());
						codeString.append(NEWLINE_WITH_2_TABS).append("))");
						staticCodeBlockIndex++;
					}
					case SINGLE_LINE_COMMENT -> {
						codeString.append(NEWLINE_WITH_2_TABS).append(".singleLineComment(\"")
								.append(singleLineComments.get(singleLineCommentIndex).getContent())
								.append("\")");
						singleLineCommentIndex++;
					}
					case MULTI_LINE_COMMENT -> {
						codeString.append(NEWLINE_WITH_2_TABS).append(".multiLineComment(");
						List<String> lines = multiLineComments.get(multiLineCommentIndex).getContent();
						for(int lineNum = 0; lineNum < lines.size(); lineNum++){
							String line = lines.get(lineNum);
							if(lineNum == 0){
								codeString.append('"').append(line).append("\", ");
							}else{
								codeString.append(NEWLINE_WITH_4_TABS).append('"').append(line).append("\", ");
							}
						}
						// Remove the last comma
						codeString.delete(codeString.length()-2, codeString.length());
						codeString.append(')');
						multiLineCommentIndex++;
					}
					case CLASS -> codeString.append(NEWLINE_WITH_2_TABS).append(".innerClass(")
							.append(innerClassesMap.get(innerElementInfo.getRight()).toBuilderCode()
									.replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
					case FIELD -> codeString.append(NEWLINE_WITH_2_TABS).append(".field(")
							.append(fieldsMap.get(innerElementInfo.getRight()).toBuilderCode()
									.replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
					case METHOD -> codeString.append(NEWLINE_WITH_2_TABS).append(".method(")
							.append(methodsMap.get(innerElementInfo.getRight()).toBuilderCode()
									.replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
				}
			}
		}else{
			// Inner Classes
			if(ListUtil.isNotBlank(innerClasses)){
				for(JavaClass innerClass: innerClasses){
					codeString.append(NEWLINE_WITH_2_TABS).append(".innerClass(")
							.append(innerClass.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
				}
			}
			
			// Fields
			if(ListUtil.isNotBlank(fields)){
				for(JavaField field: fields){
					codeString.append(NEWLINE_WITH_2_TABS).append(".field(")
							.append(field.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
				}
			}
			
			// Methods
			if(ListUtil.isNotBlank(methods)){
				for(JavaMethod method: methods){
					codeString.append(NEWLINE_WITH_2_TABS).append(".method(")
							.append(method.toBuilderCode().replace(NEWLINE_WITH_2_TABS, NEWLINE_WITH_4_TABS))
							.append(')');
				}
			}
		}
		
		// Finish building
		codeString.append(NEWLINE_WITH_2_TABS).append(".build()");
		
		return codeString.toString();
	}
}
